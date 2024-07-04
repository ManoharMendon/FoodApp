package com.example.foodapp.Model;

public class Food {
    String menu_name,star,delivery_time,foodMenu_img,res_id,price,food_id;

    public Food()
    {

    }

    public String getFood_id() {
        return food_id;
    }

    public void setFood_id(String food_id) {
        this.food_id = food_id;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }



    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
    }

    public String getFoodMenu_img() {
        return foodMenu_img;
    }

    public void setFoodMenu_img(String foodMenu_img) {
        this.foodMenu_img = foodMenu_img;
    }

    public String getRes_id() {
        return res_id.toString();
    }

    public void setRes_id(String res_id) {
        this.res_id = res_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Food(String menu_name, String star, String delivery_time, String foodMenu_img, String res_id, String price, String food_id) {
        this.menu_name = menu_name;
        this.star = star;
        this.delivery_time = delivery_time;
        this.foodMenu_img = foodMenu_img;
        this.res_id = res_id;
        this.price = price;
        this.food_id = food_id;
    }
}
