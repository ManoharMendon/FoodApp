package com.example.foodapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.foodapp.Interface.ProductDao;
import com.example.foodapp.Product;

@Database(entities = {Product.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDao ProductDao();
}
