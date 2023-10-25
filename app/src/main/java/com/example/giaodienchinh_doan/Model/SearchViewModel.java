package com.example.giaodienchinh_doan.Model;

import java.io.Serializable;

public class SearchViewModel implements Serializable {
    private String name;
    private String imgUrl;
    private String description;
    private String rating;
    private int price;

    public SearchViewModel(){}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public SearchViewModel(String name, String imgUrl, String description, String rating, int price) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.description = description;
        this.rating = rating;
        this.price=price;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
