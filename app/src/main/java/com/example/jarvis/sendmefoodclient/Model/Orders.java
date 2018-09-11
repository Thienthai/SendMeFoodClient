package com.example.jarvis.sendmefoodclient.Model;

public class Orders {
    private String ProdId;
    private String ProdName;
    private String Qntity;
    private String Price;
    private String Discnt;

    public Orders() {
    }

    public Orders(String prodId, String prodName, String qntity, String price, String discnt) {
        ProdId = prodId;
        ProdName = prodName;
        Qntity = qntity;
        Price = price;
        Discnt = discnt;
    }

    public String getProdId() {
        return ProdId;
    }

    public void setProdId(String prodId) {
        ProdId = prodId;
    }

    public String getProdName() {
        return ProdName;
    }

    public void setProdName(String prodName) {
        ProdName = prodName;
    }

    public String getQntity() {
        return Qntity;
    }

    public void setQntity(String qntity) {
        Qntity = qntity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDiscnt() {
        return Discnt;
    }

    public void setDiscnt(String discnt) {
        Discnt = discnt;
    }
}
