package com.yhlin.controller;

import com.github.pagehelper.PageInfo;
import com.yhlin.bean.Listing;
import com.yhlin.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.ResourceBundle;

@Controller
public class ListingController {

    static {
        ResourceBundle bundle = ResourceBundle.getBundle("conf/listings");
        defaultPageSize = Integer.parseInt(bundle.getString("pageSize"));

    }

    private final static int defaultPageSize;

    @Autowired
    private ListingService listingService;

    @GetMapping({"listings", "/"})
    public ModelAndView getListings(Integer page, Integer pageSize, ModelAndView modelAndView) {
        int pageParam = 1;
        int pageSizeParam = defaultPageSize;


        // 檢查使用者有沒有指定參數
        if (page != null) {
            pageParam = page;
        }
        if (pageSize != null) {
            pageSizeParam = pageSize;
        }

        // 調用listingsService返回listings
        List<Listing> listings = listingService.getByPage(pageParam, pageSizeParam);

        // 獲取分頁訊息並放入Model
        PageInfo<Listing> listingPageInfo = new PageInfo<>(listings, 3);
        modelAndView.addObject("pageInfo", listingPageInfo);
        System.out.println(listingPageInfo);

        // 將商品資訊放入Model
        modelAndView.addObject("listings", listings);

        // 設定viewName
        modelAndView.setViewName("listings");

        return modelAndView;
    }

    @GetMapping("listings/{listingId}")
    public ModelAndView getListing(@PathVariable("listingId") Integer listingId, ModelAndView modelAndView) {

        // 調用listingsService返回商品資訊
        Listing listing = listingService.getById(listingId);

        // 設定viewName
        modelAndView.setViewName("listing");

        // 如果找不到該項商品，轉至錯誤頁面
        if (listing == null) {
            modelAndView.setViewName("error");
        }

        // 將商品資訊放入model
        modelAndView.addObject("listing", listing);

        return modelAndView;
    }

}
