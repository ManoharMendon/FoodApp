package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.foodapp.Interface.ProductDao;
import com.example.foodapp.ViewHolder.FoodViewHolder;
import com.example.foodapp.database.AppDatabase;

public class PlaceOrder extends AppCompatActivity {
    ImageView back_image2;
    Button checkout;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        back_image2 = findViewById(R.id.back_image2);
        checkout = findViewById(R.id.checkout);

        back_image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlaceOrder.this,Cart.class);
                startActivity(intent);
            }
        });

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlaceOrder.this, Food_App_Home.class);
                AppDatabase db= Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"cart_db").allowMainThreadQueries().build();
                ProductDao p = db.ProductDao();
                 p.deleteEverything();
                Toast.makeText(PlaceOrder.this, "order placed", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

    }
}