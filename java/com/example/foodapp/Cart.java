package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.foodapp.Interface.ProductDao;
import com.example.foodapp.adapter.MyAdapter;
import com.example.foodapp.database.AppDatabase;

import java.util.List;
import java.util.Objects;

public class Cart extends AppCompatActivity {
    RecyclerView recyclerorders;
    TextView subtotal_price,delivery_price,discount_price,total_price;
    Button btn_placeorder, cart_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        getSupportActionBar().hide();

        subtotal_price = findViewById(R.id.subtotal_price);
        delivery_price = findViewById(R.id.delivery_price);
        discount_price = findViewById(R.id.discount_price);
        total_price = findViewById(R.id.total_price);
        btn_placeorder = findViewById(R.id.btn_placeorder);
        cart_back = findViewById(R.id.cart_back);
        getroomdata();

        cart_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cart.this, Food_App_Home.class);
                startActivity(intent);
                finish();
            }
        });
        btn_placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cart.this,PlaceOrder.class);
                startActivity(intent);
            }
        });
    }


    private void getroomdata() {

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "cart_db").allowMainThreadQueries().build();
        ProductDao productDao = db.ProductDao();

        recyclerorders = findViewById(R.id.recyclerorders);
        recyclerorders.setLayoutManager(new LinearLayoutManager(this));

        List<Product> products=productDao.getallproduct();

        MyAdapter adapter=new MyAdapter(products,subtotal_price,delivery_price,discount_price,total_price);
        recyclerorders.setAdapter(adapter);


        int sum=0,i,delivery_cost=0,discount,total;
        for(i=0;i< products.size();i++){
            sum=sum+(products.get(i).getPrice()*products.get(i).getQnt());
            delivery_cost += products.get(i).getDelivery_cost();
        }
        discount = ((sum * 5)/100);

        total = sum + delivery_cost - discount;
        subtotal_price.setText("₹" + sum);
        delivery_price.setText("₹" + delivery_cost);
        discount_price.setText("₹" + discount);
        total_price.setText("₹" + total);


    }


    private class R {
    }
}