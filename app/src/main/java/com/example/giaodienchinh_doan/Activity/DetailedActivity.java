package com.example.giaodienchinh_doan.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.giaodienchinh_doan.Model.MyCartModel;
import com.example.giaodienchinh_doan.Model.NewProductsModel;
import com.example.giaodienchinh_doan.Model.PopularProductsModel;
import com.example.giaodienchinh_doan.Model.ShowAllModel;
import com.example.giaodienchinh_doan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {

    ImageView detailedImg;
    TextView rating, name, description, price, quantity;
    Button addToCart, favourite;
    ImageView addItems, removeItems;
    String url="";

    //New products
    NewProductsModel newProductsModel=null;
    //Popular products
    PopularProductsModel popularProductsModel=null;
    MyCartModel myCartModel=null;
    //Show all
    ShowAllModel showAllModel=null;

    FirebaseAuth auth;
    private FirebaseFirestore firestore;
    int num;
    int totalquantity=1;
    int totalPrice=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

//        toolbar=findViewById(R.id.detailed_toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();



        final Object obj = getIntent().getSerializableExtra("detailed");
        if (obj instanceof NewProductsModel) {
            newProductsModel = (NewProductsModel) obj;
        }else if (obj instanceof PopularProductsModel){
            popularProductsModel=(PopularProductsModel) obj;
        }else if(obj instanceof ShowAllModel){
            showAllModel=(ShowAllModel) obj;
        }else if (obj instanceof MyCartModel){
            myCartModel=(MyCartModel) obj;
        }

        detailedImg=findViewById(R.id.detailed_img);
        quantity=findViewById(R.id.quantity);
        rating=findViewById(R.id.my_rating);
        name=findViewById(R.id.detailed_name);
        description=findViewById(R.id.detailed_desc);
        price=findViewById(R.id.detailed_price);
        addToCart=findViewById(R.id.add_to_cart);
        removeItems=findViewById(R.id.remove_item);
        addItems=findViewById(R.id.add_item);

        //New Products
        if(newProductsModel !=null){
            Glide.with(getApplicationContext()).load(newProductsModel.getImg_url()).into(detailedImg);
            url=newProductsModel.getImg_url();
            name.setText(newProductsModel.getName());
            rating.setText(newProductsModel.getRating());
            description.setText(newProductsModel.getDescription());
            price.setText(String.valueOf(newProductsModel.getPrice()));
            totalPrice=newProductsModel.getPrice() * totalquantity;
        }
//        Popular Products
//        if(popularProductsModel !=null){
//            Glide.with(getApplicationContext()).load(popularProductsModel.getImg_url()).into(detailedImg);
//            url=popularProductsModel.getImg_url();
//            name.setText(popularProductsModel.getName());
//            rating.setText(popularProductsModel.getRating());
//            description.setText(popularProductsModel.getDescription());
//            price.setText(String.valueOf(popularProductsModel.getPrice()));
//
//            totalPrice=popularProductsModel.getPrice() * totalquantity;
//        }
        //Show all
        if(showAllModel !=null){
            Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(detailedImg);
            url=showAllModel.getImg_url();
            name.setText(showAllModel.getName());
            rating.setText(showAllModel.getRating());
            description.setText(showAllModel.getDescription());
            price.setText(String.valueOf(showAllModel.getPrice()));

            totalPrice=showAllModel.getPrice() * totalquantity;
        }
        //My Cart
        if(myCartModel !=null) {
            Glide.with(getApplicationContext()).load(myCartModel.getImg_url()).into(detailedImg);
            url = myCartModel.getImg_url();
            name.setText(myCartModel.getProductName());
            price.setText(String.valueOf(myCartModel.getProductPrice()));
            quantity.setText(myCartModel.getQuantity());
            description.setText(myCartModel.getDescription());
            rating.setText(myCartModel.getRating());
        }

        //Button Add to Cart

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart(quantity);
            }

            private void addToCart(TextView totalquantity) {
                String saveCurrentTime, saveCurrentDate;
                Calendar calForDate = Calendar.getInstance();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate = new SimpleDateFormat("dd.MM.yyyy");
                saveCurrentDate = currentDate.format(calForDate.getTime());

                @SuppressLint("SimpleDateFormat") SimpleDateFormat currentTime =new SimpleDateFormat("HH:mm:ss a");
                saveCurrentTime=currentTime.format(calForDate.getTime());

                final HashMap<String, Object> cartMap = new HashMap<>();

                cartMap.put("productName", name.getText().toString());
                cartMap.put("productPrice", price.getText().toString());
                cartMap.put("quantity", quantity.getText().toString());
                cartMap.put("totalprice",totalPrice);
                cartMap.put("img_url",url);
                cartMap.put("description",description.getText().toString());
                cartMap.put("rating",rating.getText().toString());
                //cartMap.put("currentTime", saveCurrentTime);
                cartMap.put("currentDate", saveCurrentDate);

                firestore.collection("AddtoCart").document(auth.getCurrentUser().getUid())
                        .collection("User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if (task.isSuccessful()) {
                                    DocumentReference documentReference = task.getResult();
                                    String id = documentReference.getId();
                                    documentReference.update("id", id);
                                    Toast.makeText(DetailedActivity.this, "Add to Cart", Toast.LENGTH_SHORT).show();
                                    addToCart.setText("Added to Cart");
                                    addToCart.setTextColor(Color.GRAY);
                                    addToCart.setEnabled(false);
                                }
                            }
                        });
            }
        });


        addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalquantity<10){
                    totalquantity++;
                    quantity.setText(String.valueOf(totalquantity));
                }
                if(newProductsModel!=null){
                    totalPrice=newProductsModel.getPrice() * totalquantity;
                }
//                if(popularProductsModel!=null){
//                    totalPrice=popularProductsModel.getPrice() * totalquantity;
//                }
                if(showAllModel!=null){
                    totalPrice=showAllModel.getPrice() * totalquantity;
                }
            }

        });
        removeItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalquantity>1){
                    totalquantity--;
                    quantity.setText(String.valueOf(totalquantity));
                }
                if(newProductsModel!=null){
                    totalPrice=newProductsModel.getPrice() * totalquantity;
                }
                if(popularProductsModel!=null){
                    totalPrice=popularProductsModel.getPrice() * totalquantity;
                }
                if(showAllModel!=null){
                    totalPrice=showAllModel.getPrice() * totalquantity;
                }
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar_detail);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}
