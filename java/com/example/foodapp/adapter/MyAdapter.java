package com.example.foodapp.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.foodapp.Model.Food;
import com.example.foodapp.Product;
import com.example.foodapp.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.myviewholder> {
    List<Product> products;

    TextView subtotal_price,delivery_price,discount_price,total_price;



    public MyAdapter(List<Product> products, TextView subtotal_price, TextView delivery_price, TextView discount_price, TextView total_price) {
        this.products = products;
        this.subtotal_price = subtotal_price;
        this.delivery_price = delivery_price;
        this.discount_price = discount_price;
        this.total_price = total_price;
    }

    @NonNull
    @Override
    public MyAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_card,parent,false);
        return new myviewholder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyAdapter.myviewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.order_menu_name.setText(String.valueOf(products.get(position).getPname()));
        holder.order_res_name.setText(String.valueOf(products.get(position).getRes_name()));
        holder.order_price.setText("₹" + String.valueOf(products.get(position).getPrice()));
        holder.recqnt.setText(String.valueOf(products.get(position).getQnt()));

        Glide.with(holder.foodMenu_img.getContext()).load(products.get(position).getPimage()).into(holder.foodMenu_img);
        holder.incbtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                int qnt=products.get(position).getQnt();
                qnt++;
                products.get(position).setQnt(qnt);
                notifyDataSetChanged();
                updateprice();
            }
        });

        holder.decbtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                int qnt=products.get(position).getQnt();
                qnt--;
                products.get(position).setQnt(qnt);
                notifyDataSetChanged();
                updateprice();
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        TextView order_menu_name,order_res_name,order_price, recqnt;
        ImageView foodMenu_img,decbtn,incbtn;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            order_menu_name = itemView.findViewById(R.id.order_menu_name);
            order_res_name = itemView.findViewById(R.id.order_res_name);
            order_price = itemView.findViewById(R.id.order_price);

            foodMenu_img = itemView.findViewById(R.id.foodMenu_img);
            recqnt=itemView.findViewById(R.id.recqnt);


            incbtn=itemView.findViewById(R.id.incbtn);
            decbtn=itemView.findViewById(R.id.decbtn);
        }
    }

    @SuppressLint("SetTextI18n")
    public void updateprice()
    {
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
}
