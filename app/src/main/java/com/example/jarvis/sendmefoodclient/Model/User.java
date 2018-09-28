package com.example.jarvis.sendmefoodclient.Model;

public class User {
    private String Name;
    private String Password;
    private String Number;
    private String IsStaff;

    public User() {

    }

    public User(String name, String psswd, String number) {
        this.Name = name;
        this.Password = psswd;
        this.Number = number;
    }

    public String getIsStaff() {
        return IsStaff;
    }

    public void setIsStaff(String isStaff) {
        IsStaff = isStaff;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }
}
