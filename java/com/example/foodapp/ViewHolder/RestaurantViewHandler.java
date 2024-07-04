package com.example.foodapp.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Interface.ItemClickListener;
import com.example.foodapp.R;

public class RestaurantViewHandler extends RecyclerView.ViewHolder implements ItemClickListener{
    public TextView res_name;
    public ImageView res_img;

    private ItemClickListener itemClickListener;
    public RestaurantViewHandler(@NonNull View itemView) {
        super(itemView);

        res_name = (TextView) itemView.findViewById(R.id.res_name);
        res_img = (ImageView) itemView.findViewById(R.id.res_img);

//        itemView.setOnClickListener((View.OnClickListener) this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }


    @Override
    public void onClick(View view, int position, boolean isLongClick) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
