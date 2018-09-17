package com.example.jarvis.sendmefoodclient.Model;

public class MyFood {
    private String Name,Image,Description,MenuId,Price,Discount,Owner;

    public MyFood() {

    }

    public MyFood(String name, String image, String description, String menuId,String price,String discount,String owner) {
        Name = name;
        Image = image;
        Description = description;
        MenuId = menuId;
        Price = price;
        Discount = discount;
        Owner = owner;
    }

    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }
}
