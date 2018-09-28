package com.example.jarvis.sendmefoodclient.Model;

import java.util.List;

public class RqData {
    private String numbers;
    private String address;
    private String sum;
    private List<Orders> orders;
    private String status;
    private String name;
    private String owner;
    private String key;

    public RqData() {
    }

    public RqData(String numbers, String address, String sum, List<Orders> orders, String name,String status,String owner) {
        this.numbers = numbers;
        this.address = address;
        this.sum = sum;
        this.orders = orders;
        this.name = name;
        this.status = status;
        this.owner = owner;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
