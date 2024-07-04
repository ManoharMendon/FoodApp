package com.example.foodapp;

public class Validator {
    String email, username, password;

    public Validator(){

    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public Validator(String username, String email, String password) {

        this.email = email;
        this.username = username;
        this.password = password;
    }


}
