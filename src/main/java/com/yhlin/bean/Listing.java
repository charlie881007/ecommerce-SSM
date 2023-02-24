package com.yhlin.bean;

import com.yhlin.enums.ListingStatus;

public class Listing {
    private Integer id;

    private Item item;

    private Integer originalPrice;

    private Integer discountPrice;

    private Integer volume;

    private ListingStatus status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Integer originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Integer getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Integer discountPrice) {
        this.discountPrice = discountPrice;
    }

    public int getPrice() {
        return discountPrice == null ? originalPrice : discountPrice;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public ListingStatus getStatus() {
        return status;
    }

    public void setStatus(ListingStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Listing{" +
                "id=" + id +
                ", item=" + item +
                ", originalPrice=" + originalPrice +
                ", discountPrice=" + discountPrice +
                ", volume=" + volume +
                ", status='" + status + '\'' +
                '}';
    }
}