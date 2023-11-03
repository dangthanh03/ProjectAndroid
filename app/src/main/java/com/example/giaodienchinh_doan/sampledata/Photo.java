package com.example.giaodienchinh_doan.sampledata;

public class Photo {
    private int id;
    private String photoTitle;
    private String description;
    private String imgSrc;

    public Photo(int id, String photoTitle, String description, String imgSrc) {
        this.id = id;
        this.photoTitle = photoTitle;
        this.description = description;
        this.imgSrc = imgSrc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhotoTitle() {
        return photoTitle;
    }

    public void setPhotoTitle(String photoTitle) {
        this.photoTitle = photoTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }
}
