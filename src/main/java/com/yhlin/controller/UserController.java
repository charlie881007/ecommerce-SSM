package com.yhlin.controller;

import com.yhlin.bean.*;
import com.yhlin.exception.*;
import com.yhlin.response.EmailCheckResponse;
import com.yhlin.response.OperationResponse;
import com.yhlin.service.ListingService;
import com.yhlin.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    ListingService listingService;

    @GetMapping("/login")
    public String getLoginPage(@ModelAttribute("loginForm") LoginForm loginForm, @RequestParam(value = "redirect", required = false) String redirectURL, Model model) {
        model.addAttribute("redirectURL", redirectURL);
        return "login";
    }

    // AJAX請求，回傳{canUse:true|false}
    @GetMapping(value = "/checkRegistered", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody()
    public EmailCheckResponse checkRegistered(@RequestParam("email") String email, HttpServletResponse response) {
        boolean isUsed = userService.checkEmailUsed(email);

        EmailCheckResponse emailCheckResponse = new EmailCheckResponse();
        emailCheckResponse.setCanUse(!isUsed);

        return emailCheckResponse;
    }

    @PostMapping("/login")
    public String processLogin(@ModelAttribute("loginForm") LoginForm loginForm, @RequestParam(value = "redirect", required = false) String redirectURL, Model model, HttpSession session) {
        // 交由UserService處理
        User user;
        user = userService.login(loginForm);

        // 登入成功的話，將user放入session，並重新導向至首頁或指定頁面
        if (user != null) {
            session.setAttribute("user", user);
            System.out.println(user.getCart());

            if (redirectURL != null) {
                return "redirect:" + redirectURL;
            }
            return "redirect:/";
        }

        // 失敗的話，重新導向至登入頁
        model.addAttribute("errorMsg", "信箱或密碼錯誤");
        if (redirectURL != null) {
            model.addAttribute("redirectURL", redirectURL);
        }

        return "login";
    }

    @GetMapping("/logout")
    public String processLogout(HttpSession session) {
        // 將User物件從Session中移除
        session.removeAttribute("user");

        // 重新導向至首頁
        return "redirect:/";
    }


    @GetMapping("/register")
    public String getRegisterPage(@ModelAttribute("registerForm") RegisterForm registerForm) {
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@Valid @ModelAttribute("registerForm") RegisterForm registerForm, BindingResult bindingResult, Model model, HttpSession session) {
        // 資料檢查
        if (bindingResult.hasErrors()) {
            return "register";
        }

        // 重複註冊檢查
        boolean isRegistered = userService.checkEmailUsed(registerForm.getEmail());
        if (isRegistered) {
            model.addAttribute("emailMsg", "您已經註冊過了");
            return "register";
        }

        // 驗證碼檢查
        String email = (String) session.getAttribute("email");
        String code = (String) session.getAttribute("code");
        if (!registerForm.getEmail().equals(email) || !registerForm.getCode().equals(code)) {
            model.addAttribute("codeMsg", "驗證碼錯誤");
            return "register";
        }


        // 使用者輸入檢查完畢
        // 交由UserService處理
        try {
            userService.registerUser(registerForm);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        // 重導向至首頁
        return "redirect:login";
    }

    @PostMapping("/verify/resend")
    public void resendVerificationMail(@RequestParam("email") String email, HttpSession session, HttpServletResponse response) {
        try {
            String code = userService.sendConfirmMail(email);
            session.setAttribute("code", code);
            session.setAttribute("email", email);
            response.setStatus(200);
        } catch (MessagingException e) {
            response.setStatus(500);
        }
    }

    // ajax請求，回傳{success: true|false, msg:""}
    @PostMapping(value = "/cart/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public OperationResponse addItemToCart(@SessionAttribute("user") User user, @RequestParam Integer listingId, @RequestParam Integer quantity, HttpServletResponse response) {
        OperationResponse operationResponse = new OperationResponse();
        boolean isSuccess = false;
        String msg = "";

        // 取得商品
        Listing listing = listingService.getById(listingId);
        if (listing == null) {
            msg = "no item found";
            response.setStatus(400);
        } else {
            // 新增到購物車
            try {
                userService.addItemToCart(user, listing, quantity);
                isSuccess = true;

            } catch (DuplicateItemException e) {
                msg = "duplicate item";
            } catch (ClosedListingException e) {
                msg = "closed item";
            } catch (InsufficientItemException e) {
                msg = "insufficient item";
            } catch (DatabaseException e) {
                response.setStatus(500);
                msg = "server error";
            }
        }

        operationResponse.setSuccess(isSuccess);
        operationResponse.setMsg(msg);

        return operationResponse;
    }

    // ajax請求，回傳{success: true|false, msg:""}
    @PostMapping(value = "/cart/remove", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public OperationResponse removeFromCart(@SessionAttribute("user") User user, Integer listingId, HttpServletResponse response) {
        OperationResponse operationResponse = new OperationResponse();
        boolean isSuccess = false;
        String msg = "";

        // 取得商品
        Listing listing = listingService.getById(listingId);
        if (listing == null) {
            msg = "no item found";
            response.setStatus(400);
        } else {
            // 從購物車移除
            try {
                userService.removeFromCart(user, listing);
                isSuccess = true;
            } catch (NotInCartException e) {
                msg = "not in cart";
            } catch (DatabaseException e) {
                response.setStatus(500);
                msg = "server error";
            }
        }

        operationResponse.setSuccess(isSuccess);
        operationResponse.setMsg(msg);

        return operationResponse;
    }

    // ajax請求，回傳{success: true|false, msg:""}
    @PostMapping(value = "/cart/removeClosed")
    @ResponseBody
    public OperationResponse removeClosedListingFromCart(@SessionAttribute("user") User user, HttpServletResponse response) {
        OperationResponse operationResponse = new OperationResponse();
        boolean isSuccess = false;
        String msg = "";

        try {
            userService.removeClosedListingsFromCart(user);
            isSuccess = true;
        } catch (DatabaseException e) {
            response.setStatus(500);
            msg = "server error";
        }

        operationResponse.setSuccess(isSuccess);
        operationResponse.setMsg(msg);

        return operationResponse;
    }

    // ajax請求，回傳{success: true|false, msg:""}
    @PostMapping(value = "/cart/revise", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public OperationResponse reviseCart(@SessionAttribute("user") User user, @RequestParam Integer listingId, @RequestParam Integer quantity, HttpServletResponse response) {
        OperationResponse operationResponse = new OperationResponse();
        boolean isSuccess = false;
        String msg = "";

        // 從購物車修改
        try {
            userService.reviseCart(user, listingId, quantity);
            isSuccess = true;
        } catch (NotInCartException e) {
            msg = "not in cart";
        } catch (DatabaseException e) {
            response.setStatus(500);
            msg = "server error";
        }

        operationResponse.setSuccess(isSuccess);
        operationResponse.setMsg(msg);

        return operationResponse;
    }

    @GetMapping(value = "/cart")
    public String viewCart() {
        return "cart";
    }

    @PostMapping("/cart/checkout")
    public ModelAndView checkout(@SessionAttribute("user") User user, ModelAndView modelAndView) {
        modelAndView.setViewName("cart");
        String msg = "";
        try {
            userService.checkout(user);
            modelAndView.setViewName("redirect:/");
        } catch (EmptyCartException e) {
            msg = "抱歉，您的購物車是空的";
        } catch (ClosedListingException e) {
            msg = "抱歉，購物車內含有已下架的產品";
        } catch (InsufficientItemException e) {
            msg = "抱歉，庫存數量不夠";
        } catch (DatabaseException e) {
            msg = "抱歉，伺服器遭遇問題";
        }

        modelAndView.addObject("msg", msg);
        return modelAndView;
    }

    @GetMapping("/orders")
    public String viewOrders(@SessionAttribute("user") User user) {
        return "orders";
    }

    @GetMapping("/orders/{orderId}")
    public ModelAndView viewOrder(@SessionAttribute("user") User user, @PathVariable("orderId") Integer orderId) {
        ModelAndView modelAndView = new ModelAndView();

        try {
            Order order = userService.viewOrder(user, orderId);
            modelAndView.addObject("order", order);
            modelAndView.setViewName("order");
        } catch (NoOrderFound e) {
            modelAndView.setViewName("orderNotFound");
        }

        return modelAndView;
    }

    // ajax請求，回傳{success: true|false, msg:""}
    @ResponseBody
    @PostMapping(value = "/orders/{orderId}/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    public OperationResponse cancelOrder(@SessionAttribute("user") User user, @PathVariable("orderId") Integer orderId, HttpServletResponse response) {
        OperationResponse operationResponse = new OperationResponse();
        boolean isSuccess = false;
        String msg = "";

        try {
            userService.cancelOrder(user, orderId);
            isSuccess = true;
        } catch (NoOrderFound e) {
            msg = "no order found";
            response.setStatus(400);
        } catch (UncancellableException e) {
            msg = "too late to cancel";
        } catch (DatabaseException e) {
            msg = "server error";
            response.setStatus(500);
        }

        operationResponse.setSuccess(isSuccess);
        operationResponse.setMsg(msg);
        return operationResponse;
    }
}
