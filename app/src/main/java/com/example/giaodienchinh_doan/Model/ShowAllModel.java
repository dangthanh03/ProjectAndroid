package com.example.giaodienchinh_doan.Model;

import com.google.firebase.firestore.DocumentReference;

import java.io.Serializable;
import java.util.List;

public class ShowAllModel implements Serializable {

    String id ;
    String description;
    String name;
    String rating;
    String img_url;
    int price;
    String brand;
    String status;
    List<String> size;

    public ShowAllModel() {
    }



    public ShowAllModel(String id ,String description, String name, String rating, String img_url, int price, String brand, String status, List<String> size) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.rating = rating;
        this.img_url = img_url;
        this.price = price;
        this.brand = brand;
        this.status = status;
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

//

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getSize() {
        return size;
    }

    public void setSize(List<String> size) {
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
