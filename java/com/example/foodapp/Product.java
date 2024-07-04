package com.example.foodapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Product {
    @PrimaryKey(autoGenerate = true)
    public int pid;

    @ColumnInfo(name = "pname")
    public String pname;

    @ColumnInfo(name = "foodid")
    public String foodid;
    @ColumnInfo(name = "res_name")
    public String res_name;

    @ColumnInfo(name = "pimage")
    public String pimage;

    @ColumnInfo(name = "price")
    public int price;

    @ColumnInfo(name = "qnt")
    public int qnt;

    @ColumnInfo(name = "delivery_cost")
    public int delivery_cost;

    public Product(int pid, String pname,String res_name,String pimage,String foodid, int price, int qnt, int delivery_cost) {
        //static storage id
        this.pid = pid;

        //firebase search key
        this.foodid = foodid;

        //firebase data stored statically
        this.res_name = res_name;
        this.pimage = pimage;
        this.pname = pname;
        this.price = price;
        this.qnt = qnt;
        this.delivery_cost = delivery_cost;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQnt() {
        return qnt;
    }

    public void setQnt(int qnt) {
        this.qnt = qnt;
    }

    public String getRes_name() {
        return res_name;
    }

    public void setRes_name(String res_name) {
        this.res_name = res_name;
    }

    public String getPimage() {
        return pimage;
    }

    public void setPimage(String pimage) {
        this.pimage = pimage;
    }

    public String getFoodid() {
        return foodid;
    }

    public void setFoodid(String foodid) {
        this.foodid = foodid;
    }

    public int getDelivery_cost() {
        return delivery_cost;
    }

    public void setDelivery_cost(int delivery_cost) {
        this.delivery_cost = delivery_cost;
    }
}
