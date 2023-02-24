package com.yhlin.bean;

import java.util.List;

public class Item {
    private Integer id;

    private String itemSn;

    private String name;

    private String description;

    private Integer volume;

    private List<Tag> tags;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItemSn() {
        return itemSn;
    }

    public void setItemSn(String itemSn) {
        this.itemSn = itemSn == null ? null : itemSn.trim();
    }

    public String getName() {
        return name;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }


    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", itemSn='" + itemSn + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", volume=" + volume +
                '}';
    }
}