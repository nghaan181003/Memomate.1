package com.example.memomate.Models;

public class User {
    private String avatar;
    private String userName;
    private String email;
    private String passWord;

    public User() {
    }

    public User(String avatar, String userName, String email, String passWord) {
        this.avatar = avatar;
        this.userName = userName;
        this.email = email;
        this.passWord = passWord;
    }


    public User(String userName, String email, String passWord) {
        this.userName = userName;
        this.email = email;
        this.passWord = passWord;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}



