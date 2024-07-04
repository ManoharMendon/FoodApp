package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;

import com.example.foodapp.Interface.ProductDao;
import com.example.foodapp.Model.Food;
import com.example.foodapp.ViewHolder.FoodViewHolder;
import com.example.foodapp.ViewHolder.RestaurantViewHandler;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapp.Model.Restaurant;

import com.example.foodapp.database.AppDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class Food_App_Home extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference restaurant,foods;
    RecyclerView recycler_res, recycler_food;

    RecyclerView.LayoutManager res_layout,menu_layout;
    FirebaseRecyclerAdapter<Restaurant,RestaurantViewHandler> adapter;
    FirebaseRecyclerAdapter<Food,FoodViewHolder> adapter2;
    TextView menu_view_more;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_app_home);

        database = FirebaseDatabase.getInstance();
        restaurant = database.getReference().child("Restaurant");

        //Load Restaurant
//        AppDatabase db= Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"cart_db").allowMainThreadQueries().build();
//        ProductDao p = db.ProductDao();
//        p.deleteEverything();




        recycler_res =  findViewById(R.id.restaurant_recyclerview);
        recycler_res.setHasFixedSize(false);
        res_layout = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recycler_res.setLayoutManager(res_layout);

        loadRes();

        //Load Food
        foods = database.getReference().child("Foods");

        recycler_food = findViewById(R.id.food_recyclerview);
        recycler_food.setHasFixedSize(false);
        menu_layout = new LinearLayoutManager(this);
        recycler_food.setLayoutManager(menu_layout);

        loadFood();

//        final LinearLayout homelayout =  findViewById(R.id.HomeLayout);
        final LinearLayout foodcartlayout = findViewById(R.id.FoodCartLayout);
//        final LinearLayout profilelayout = findViewById(R.id.ProfileLayout);
//        final LinearLayout foodhistorylayout = findViewById(R.id.FoodHistoryLayout);

//        final ImageView homeimage = (ImageView) findViewById(R.id.homeimage);
//        final ImageView profileimage = (ImageView) findViewById(R.id.profileimage);
//        final ImageView cartimage = (ImageView) findViewById(R.id.cartimage);
//        final ImageView hiatoryimage = (ImageView) findViewById(R.id.historyimage);
//
//        final TextView hometext = (TextView) findViewById(R.id.hometext);
//        final TextView profiletext = (TextView) findViewById(R.id.profiletext);
//        final TextView carttext = (TextView) findViewById(R.id.carttext);
//        final TextView historytext = (TextView) findViewById(R.id.historytext);

        menu_view_more = findViewById(R.id.menu_view_more);
        menu_view_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Food_App_Home.this,ViewMore.class);
                startActivity(intent);
            }
        });

        foodcartlayout.setOnClickListener(v -> {
            Intent intent = new Intent(Food_App_Home.this, Cart.class);
            startActivity(intent);

//                Toast.makeText(Food_App_Home.this, "cart clicked", Toast.LENGTH_SHORT).show();
        });

    }

    private void loadFood() {
        FirebaseRecyclerOptions<Food> options = new FirebaseRecyclerOptions.Builder<Food>()
                .setQuery(foods,Food.class)
                .build();

        adapter2 = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(options) {

            @NonNull
            @Override
            public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_list,parent,false);
                return new FoodViewHolder(v);
            }

            @SuppressLint("SetTextI18n")
            @Override
            protected void onBindViewHolder(@NonNull FoodViewHolder holder, int position, @NonNull Food model) {
                holder.menu_name.setText(model.getMenu_name());
                holder.delivery_time.setText(model.getDelivery_time());
                holder.price.setText("₹"+model.getPrice());
                holder.star.setText(model.getStar());
                final String[] resname = new String[1];
                final String[] delivry = new String[1];
                DatabaseReference restaurant1 = database.getReference().child("Restaurant");

                restaurant1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Restaurant r = snapshot.child(model.getRes_id()).getValue(Restaurant.class);

//                        Log.v("Taggy",""+model.getFood_id());


                        assert r != null;
                        resname[0] = r.getRes_name();
                        delivry[0] = r.getDelivery();
                        holder.menu_res_name.setText(resname[0]);
                        holder.delivery.setText(delivry[0]);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Food_App_Home.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
                    }
                });

                //res_id, menu_res_name  --> fetch restaurant

                Glide.with(holder.foodMenu_img.getContext()).load(model.getFoodMenu_img()).into(holder.foodMenu_img);

                holder.add_to_cart.setOnClickListener(v -> {

                    AppDatabase db= Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"cart_db").allowMainThreadQueries().build();
                    ProductDao productDao=db.ProductDao();

                    DatabaseReference foods1 = database.getReference().child("Restaurant");
                    foods1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Restaurant r = snapshot.child(model.getRes_id()).getValue(Restaurant.class);

                            assert r != null;
                            resname[0] = r.getRes_name();
                            delivry[0] = r.getDelivery();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    Boolean check = productDao.is_exist(Integer.parseInt(model.getFood_id().substring(model.getFood_id().length() - 4)));

                    if(!check)
                    {
                        String foodid = model.getFood_id();
                        int pid = Integer.parseInt(foodid.substring(foodid.length() - 4));
                        String pname = model.getMenu_name();
                        String res_name = resname[0];
                        int delivery_cost;
                        if(delivry[0].equals("Free"))
                            delivery_cost=0;
                        else
                            delivery_cost = Integer.parseInt(delivry[0]);


                        String pimage = model.getFoodMenu_img();

                        int price = Integer.parseInt(model.getPrice());
                        int qnt = 1;
                        productDao.insertrecord(new Product(pid,pname,res_name,pimage,foodid,price,qnt,delivery_cost));
                    }

                });


            }
        };
        recycler_food.setAdapter(adapter2);
    }

    private void loadRes() {
        FirebaseRecyclerOptions<Restaurant> options =
                new FirebaseRecyclerOptions.Builder<Restaurant>()
                        .setQuery(restaurant, Restaurant.class)
                        .build();
        adapter = new FirebaseRecyclerAdapter<Restaurant, RestaurantViewHandler>(options) {
            @NonNull
            @Override
            public RestaurantViewHandler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restuarant_list,parent,false);
                return new RestaurantViewHandler(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull RestaurantViewHandler holder, int position, @NonNull Restaurant model) {
                holder.res_name.setText(model.getRes_name());

//               Picasso.get().load(model.getRes_img()).into(holder.res_img);
                Glide.with(holder.res_img.getContext()).load(model.getRes_img()).into(holder.res_img);
                holder.res_name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       Intent intent = new Intent(Food_App_Home.this, RestaurantMenus.class);
                       intent.putExtra("res_id",model.getRes_id());
                       intent.putExtra("res_name", model.getRes_name());
                       intent.putExtra("delivery", model.getDelivery());
                       intent.putExtra("res_image",model.getRes_img());
                       intent.putExtra("res_star",model.getRating());

                       startActivity(intent);
                    }
                });

            }
        };
        recycler_res.setAdapter(adapter);
    }
    @Override
    public void onStart(){
        super.onStart();
        adapter.startListening();
        adapter2.startListening();
    }
    @Override
    public void onStop(){
        super.onStop();
        adapter.stopListening();
        adapter2.stopListening();
    }


}