package com.yhlin.bean;

import com.yhlin.enums.OrderStatus;

import java.util.Date;

public class Order {
    private Integer id;

    private User user;

    private Cart cart;

    private OrderStatus status;

    private Date createTime;

    private Integer amount;

    public Order() {
    }

    public Order(User user, Cart cart) {
        this.user = user;
        this.cart = cart;
        this.status = OrderStatus.CREATED;

        // 計算整車價格
        calculateTotalPrice();

        // 設定結帳時間
        setCreateTime(new Date());
    }

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Cart getCart() {
        return cart;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    private void calculateTotalPrice() {
        int total = 0;
        for (CartDetail cartDetail : cart.getCartDetails()) {
            Listing item = cartDetail.getListing();
            int quantity = cartDetail.getQuantity();

            total += quantity * item.getPrice();
        }

        setAmount(total);
    }
}