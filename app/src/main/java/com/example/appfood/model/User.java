package com.example.appfood.model;

public class User {
    private int accountID;
    private byte[] anhUser;
    private String fullName;
    private String ngaySinh;
    private String email;
    private String password;
    private String phone;
    private String address;

    public User() {
    }

    public User(int accountID, int roleID, byte[] anhUser, String fullName, String ngaySinh, String email, String password, String phone, String address) {
        this.accountID = accountID;
        this.anhUser = anhUser;
        this.fullName = fullName;
        this.ngaySinh = ngaySinh;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public byte[] getAnhUser() {
        return anhUser;
    }

    public void setAnhUser(byte[] anhUser) {
        this.anhUser = anhUser;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
