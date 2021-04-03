package com.example.ibet.model.User;

public class User {

    String email;
    String userName;

    public User(String email,String username) {
        this.email = email;
        this.userName = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
