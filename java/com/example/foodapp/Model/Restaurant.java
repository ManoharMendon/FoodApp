package com.example.foodapp.Model;

public class Restaurant {
    private String res_name;
    private  String res_img;
    public String description,rating,delivery,res_id;

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getRes_id() {
        return res_id;
    }

    public Restaurant(String res_name, String res_img, String description, String rating, String delivery, String res_id) {
        this.res_name = res_name;
        this.res_img = res_img;
        this.description = description;
        this.rating = rating;
        this.delivery = delivery;
        this.res_id = res_id;
    }

    public void setRes_id(String res_id) {
        this.res_id = res_id;
    }

    public Restaurant(){

    }

    public String getRes_name() {
        return res_name;
    }

    public void setRes_name(String res_name) {
        this.res_name = res_name;
    }

    public String getRes_img() {
        return res_img;
    }

    public void setRes_img(String res_img) {
        this.res_img = res_img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDelivery_charge() {
        return delivery;
    }

    public void setDelivery_charge(String delivery) {
        this.delivery = delivery;
    }


}
