package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.google.firebase.database.ValueEventListener;

public class ViewMore extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference restaurant,foods;
    RecyclerView recycler_food2;
    RecyclerView.LayoutManager menu_layout;
    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter2;
    ImageView back_image3;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_more);
        database = FirebaseDatabase.getInstance();
        foods = database.getReference().child("Foods");

        back_image3 = findViewById(R.id.back_image3);
        back_image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewMore.this,Food_App_Home.class);
                startActivity(intent);
            }
        });

        recycler_food2 = findViewById(R.id.recycler_food2);
        recycler_food2.setHasFixedSize(false);
        menu_layout = new LinearLayoutManager(this);
        recycler_food2.setLayoutManager(menu_layout);

        load_food();
    }

    private void load_food() {
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
                        Toast.makeText(ViewMore.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
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
        recycler_food2.setAdapter(adapter2);
    }
    public void onStart(){
        super.onStart();

        adapter2.startListening();
    }
    @Override
    public void onStop(){
        super.onStop();
        adapter2.stopListening();
    }
}