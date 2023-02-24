package com.yhlin.controller;

import com.yhlin.bean.*;
import com.yhlin.exception.*;
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

    @GetMapping("/checkRegistered")
    public void checkRegistered(String email, HttpServletResponse response) {
        boolean isUsed = userService.checkEmailUsed(email);
        if (isUsed) {
            response.setStatus(400);
        } else {
            response.setStatus(200);
        }
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
            response.setStatus(400);
        }
    }

    // ajax請求
    @PostMapping(value = "/cart/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseModel addItemToCart(@SessionAttribute("user") User user, @RequestParam Integer listingId, @RequestParam Integer quantity, HttpServletResponse response) {
        // 取得商品
        Listing listing = listingService.getById(listingId);
        ResponseModel responseModel = new ResponseModel();

        // 新增到購物車
        try {
            userService.addItemToCart(user, listing, quantity);
            responseModel.setMsg("【成功】新增成功");


        } catch (DuplicateItemException e) {
            responseModel.setMsg("【失敗】此商品已經在購物車裡了");
            response.setStatus(400);
        } catch (ClosedListingException e) {
            responseModel.setMsg("【失敗】此商品已經下架了");
            response.setStatus(400);
        } catch (InsufficientItemException e) {
            responseModel.setMsg("【失敗】商品數量不足");
            response.setStatus(400);
        } catch (CartException e) {
            responseModel.setMsg("【失敗】伺服器錯誤");
            response.setStatus(500);
        }

        return responseModel;
    }

    // ajax請求
    @PostMapping(value = "/cart/remove", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseModel removeFromCart(@SessionAttribute("user") User user, Integer listingId, HttpServletResponse response) {
        // 取得商品
        Listing listing = listingService.getById(listingId);
        ResponseModel responseModel = new ResponseModel();

        // 新增到購物車
        try {
            userService.removeFromCart(user, listing);
            responseModel.setMsg("【成功】移除成功");


        } catch (CartException e) {
            responseModel.setMsg("【失敗】購物車裡沒有此商品");
            response.setStatus(400);
        } catch (ServerError e) {
            responseModel.setMsg("伺服器錯誤");
            response.setStatus(500);
        }

        return responseModel;
    }

    @PostMapping(value = "/cart/removeClosed")
    public void removeClosedListingFromCart(@SessionAttribute("user") User user, HttpServletResponse response) {
        try {
            userService.removeClosedListingsFromCart(user);
        } catch (CartRemoveException e) {
            response.setStatus(500);
        }
    }

    @PostMapping(value = "/cart/revise", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseModel reviseCart(@SessionAttribute("user") User user, @RequestParam Integer listingId, @RequestParam Integer quantity, HttpServletResponse response) {
        ResponseModel responseModel = new ResponseModel();
        try {
            userService.reviseCart(user, listingId, quantity);
            responseModel.setMsg("修改成功");
        } catch (CartException e) {
            responseModel.setMsg("修改失敗");
            response.setStatus(500);
        }

        return responseModel;
    }

    @GetMapping(value = "/cart")
    public String viewCart() {
        return "cart";
    }

    @PostMapping("/cart/checkout")
    public ModelAndView checkout(@SessionAttribute("user") User user, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:/");
        try {
            userService.checkout(user);
        } catch (EmptyCartException e) {
            String msg = "抱歉，您的購物車是空的";
            modelAndView.addObject("msg", msg);
            modelAndView.setViewName("cart");

        } catch (ClosedListingException e) {
            String msg = "抱歉，購物車內含有已下架的產品";
            modelAndView.addObject("msg", msg);
            modelAndView.setViewName("cart");

        } catch (InsufficientItemException e) {
            String msg = "抱歉，庫存數量不夠";
            modelAndView.addObject("msg", msg);
            modelAndView.setViewName("cart");
        } catch (DatabaseException e) {
            String msg = "抱歉，伺服器遭遇問題";
            modelAndView.addObject("msg", msg);
            modelAndView.setViewName("cart");
        }

        return modelAndView;
    }

    @GetMapping("/orders")
    public String viewOrders(@SessionAttribute("user") User user) {
        return "orders";
    }

    @GetMapping("/orders/{orderId}")
    public ModelAndView viewOrder(@SessionAttribute("user") User user, @PathVariable("orderId") Integer orderId) {
        ModelAndView modelAndView = new ModelAndView();

        Order order = user.getOrder(orderId);

        String viewName;
        if (order != null) {
            viewName = "order";
            modelAndView.addObject("order", order);
        } else {
            viewName = "error";
        }

        modelAndView.setViewName(viewName);
        return modelAndView;
    }

    @PostMapping("/orders/{orderId}/cancel")
    public void cancelOrder(@SessionAttribute("user") User user, @PathVariable("orderId") Integer orderId, HttpServletResponse response) {
        try {
            userService.cancelOrder(user, orderId);
        } catch (DatabaseException e) {
            response.setStatus(500);
        }
        response.setStatus(200);
    }
}
