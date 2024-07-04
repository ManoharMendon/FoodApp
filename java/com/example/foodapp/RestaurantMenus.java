package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.foodapp.Interface.ProductDao;
import com.example.foodapp.Model.Food;
import com.example.foodapp.Model.Restaurant;
import com.example.foodapp.ViewHolder.FoodViewHolder;
import com.example.foodapp.database.AppDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.Timer;

public class RestaurantMenus extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference restaurant,foods;
    RecyclerView recycle_food;
    LinearLayout layout;

    TextView res_name1,statusbar,res_rating;
    ImageView res_image_view,back_image;
    CardView cardView;
     Intent intent;
     boolean val;
    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;

    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_menus);

        database = FirebaseDatabase.getInstance();
        foods = database.getReference().child("Foods");

        intent = getIntent();

        float star = Float.parseFloat(intent.getStringExtra("res_star"));

        cardView = findViewById(R.id.cardformenu);


        res_name1 = findViewById(R.id.res_name1);
        statusbar = findViewById(R.id.statusbar);
        res_rating = findViewById(R.id.res_rating);
        res_image_view = findViewById(R.id.res_image_view);
        back_image = findViewById(R.id.back_image);
        layout = findViewById(R.id.layout);

        recycle_food = findViewById(R.id.recycle_food);
        recycle_food.setHasFixedSize(true);

        LinearLayoutManager menu_layout = new LinearLayoutManager(this);
        recycle_food.setLayoutManager(menu_layout);

        res_name1.setText(intent.getStringExtra("res_name"));
        res_rating.setText(intent.getStringExtra("res_star"));
        Glide.with(res_image_view.getContext()).load(intent.getStringExtra("res_image")).into(res_image_view);
        if(star>= 4.5)
            statusbar.setText("Famous");
        else if(star>=4.0 && star<4.5)
        {
            statusbar.setText("Popular");
        }
        else{
            statusbar.setVisibility(View.INVISIBLE);
        }

        loadfoods();

        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(RestaurantMenus.this,Food_App_Home.class);
                startActivity(intent1);
            }
        });



    }

    private void loadfoods() {

        String res_id = intent.getStringExtra("res_id");
        Query query = FirebaseDatabase.getInstance().getReference("Foods").orderByChild("res_id");

        FirebaseRecyclerOptions<Food> options = new FirebaseRecyclerOptions.Builder<Food>()
                .setQuery(query,Food.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(options) {
            @NonNull
            @Override
            public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_list,parent,false);
                return new FoodViewHolder(v);
            }
//            @Override
//            public void onDataChanged() {
//                if(val)
//                    layout.setVisibility(View.VISIBLE);
//                else
//                    layout.setVisibility(View.GONE);
//            }


            @Override
            protected void onBindViewHolder(@NonNull FoodViewHolder holder, int position, @NonNull Food model) {
                Log.i("taggy"+res_id, model.getRes_id());
                if(Objects.equals(model.getRes_id(), res_id)) {

                    Log.i("tag2", model.getRes_id());
                    holder.menu_name.setText(model.getMenu_name());
                    holder.delivery_time.setText(model.getDelivery_time());
                    holder.price.setText("₹" + model.getPrice());
                    holder.star.setText(model.getStar());
                    holder.menu_res_name.setText(intent.getStringExtra("res_name"));
                    holder.delivery.setText(intent.getStringExtra("delivery"));

                    Glide.with(holder.foodMenu_img.getContext()).load(model.getFoodMenu_img()).into(holder.foodMenu_img);
                    holder.add_to_cart.setOnClickListener(v -> {

                        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "cart_db").allowMainThreadQueries().build();
                        ProductDao productDao = db.ProductDao();

                        Boolean check = productDao.is_exist(Integer.parseInt(model.getFood_id().substring(model.getFood_id().length() - 4)));

                        if (!check) {
                            String foodid = model.getFood_id();
                            int pid = Integer.parseInt(foodid.substring(foodid.length() - 4));
                            String pname = model.getMenu_name();
                            String res_name = intent.getStringExtra("res_name");
                            int delivery_cost;
                            if (intent.getStringExtra("delivery").equals("Free"))
                                delivery_cost = 0;
                            else
                                delivery_cost = Integer.parseInt(intent.getStringExtra("delivery"));


                            String pimage = model.getFoodMenu_img();

                            int price = Integer.parseInt(model.getPrice());
                            int qnt = 1;
                            productDao.insertrecord(new Product(pid, pname, res_name, pimage, foodid, price, qnt, delivery_cost));
                        }


                    });
                }
                else {
//                    int height = holder.cardformenu.getLayoutParams().height;

                    ViewGroup.LayoutParams params = holder.cardformenu.getLayoutParams();
                    params.height = 0;
                    params.width = 0;
                    holder.cardformenu.setLayoutParams(params);
                    holder.cardformenu.setVisibility(View.INVISIBLE);
                }
            }

        };
        recycle_food.setAdapter(adapter);
    }

    @Override
    public void onStart(){
        super.onStart();
        adapter.startListening();

    }
    @Override
    public void onStop(){
        super.onStop();
        adapter.stopListening();

    }
}