package com.example.giaodienchinh_doan.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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
    Button addToCart, buyNow;
    ImageView addItems, removeItems;

    //New products
    NewProductsModel newProductsModel=null;
    //Popular products
    PopularProductsModel popularProductsModel=null;
    //Show all
    ShowAllModel showAllModel=null;

    FirebaseAuth auth;
    private FirebaseFirestore firestore;
    int totalquantity=1;
    int totalPrice=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();

        final Object obj = getIntent().getSerializableExtra("detailed");
        if (obj instanceof NewProductsModel) {
            newProductsModel = (NewProductsModel) obj;
        }else if (obj instanceof PopularProductsModel){
            popularProductsModel=(PopularProductsModel) obj;
        }else if(obj instanceof ShowAllModel){
            showAllModel=(ShowAllModel) obj;
        }

        detailedImg=findViewById(R.id.detailed_img);
        quantity=findViewById(R.id.quantity);
        rating=findViewById(R.id.my_rating);
        name=findViewById(R.id.detailed_name);
        description=findViewById(R.id.detailed_desc);
        price=findViewById(R.id.detailed_price);
        addToCart=findViewById(R.id.add_to_cart);
        buyNow=findViewById(R.id.buy_now);
        removeItems=findViewById(R.id.remove_item);
        addItems=findViewById(R.id.add_item);

        //New Products
        if(newProductsModel !=null){
            Glide.with(getApplicationContext()).load(newProductsModel.getImg_url()).into(detailedImg);
            name.setText(newProductsModel.getName());
            rating.setText(newProductsModel.getRating());
            description.setText(newProductsModel.getDescription());
            price.setText(String.valueOf(newProductsModel.getPrice()));
            totalPrice=newProductsModel.getPrice() * totalquantity;
        }
        //Popular Products
        if(popularProductsModel !=null){
            Glide.with(getApplicationContext()).load(popularProductsModel.getImg_url()).into(detailedImg);
            name.setText(popularProductsModel.getName());
            rating.setText(popularProductsModel.getRating());
            description.setText(popularProductsModel.getDescription());
            price.setText(String.valueOf(popularProductsModel.getPrice()));

            totalPrice=popularProductsModel.getPrice() * totalquantity;
        }
        //Show all
        if(showAllModel !=null){
            Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(detailedImg);
            name.setText(showAllModel.getName());
            rating.setText(showAllModel.getRating());
            description.setText(showAllModel.getDescription());
            price.setText(String.valueOf(showAllModel.getPrice()));

            totalPrice=showAllModel.getPrice() * totalquantity;
        }

        addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalquantity<10){
                    totalquantity++;
                    quantity.setText(String.valueOf(totalquantity));
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

            }
        });
        removeItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalquantity>1){
                    totalquantity--;
                    quantity.setText(String.valueOf(totalquantity));
                }
            }
        });



    }
}