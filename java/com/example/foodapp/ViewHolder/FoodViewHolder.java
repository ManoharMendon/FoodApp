package com.example.foodapp.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Interface.ItemClickListener;
import com.example.foodapp.R;

public class FoodViewHolder extends RecyclerView.ViewHolder{
    public TextView menu_name,menu_res_name,star,delivery_time,price;
    //restaurant
    public TextView delivery;

    public Button add_to_cart;

    public ImageView foodMenu_img;

    public CardView cardformenu;


    public FoodViewHolder(@NonNull View itemView) {
        super(itemView);

        menu_name = (TextView) itemView.findViewById(R.id.menu_name);
        menu_res_name = (TextView) itemView.findViewById(R.id.menu_res_name);
        star = (TextView) itemView.findViewById(R.id.star);
        delivery_time = (TextView) itemView.findViewById(R.id.delivery_time);
        price = (TextView) itemView.findViewById(R.id.price);
        //restaurant
        delivery = (TextView) itemView.findViewById(R.id.delivery);

        foodMenu_img = (ImageView) itemView.findViewById(R.id.foodMenu_img);

        add_to_cart = (Button) itemView.findViewById(R.id.add_to_cart);
        cardformenu = itemView.findViewById(R.id.cardformenu);
//        add_to_cart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

}
