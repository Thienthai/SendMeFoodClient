package com.example.jarvis.sendmefoodclient.Model;

public class Categories {
    private String title;
    private String Img;

    public Categories() {
    }

    public Categories(String title, String img) {
        this.title = title;
        this.Img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        this.Img = img;
    }
}
