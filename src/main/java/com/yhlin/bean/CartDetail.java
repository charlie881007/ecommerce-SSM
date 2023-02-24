package com.yhlin.bean;

public class CartDetail {
    private Integer id;

    private Listing listing;

    private Integer quantity;

    private Cart cart;

    public CartDetail() {
    }

    public CartDetail(Listing listing, Integer quantity, Cart cart) {
        this.listing = listing;
        this.quantity = quantity;
        this.cart = cart;
    }

    public Integer getId() {
        return id;
    }

    public Listing getListing() {
        return listing;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Cart getCart() {
        return cart;
    }

    public int getSubtotal() {
        int price = listing.getDiscountPrice() == null ? getListing().getOriginalPrice() : getListing().getDiscountPrice();
        return price * quantity;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public String toString() {
        return "CartDetail{" +
                "id=" + id +
                ", listing=" + listing +
                ", quantity=" + quantity +
                ", cart=" + cart +
                '}';
    }
}