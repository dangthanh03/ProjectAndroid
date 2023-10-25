package com.example.giaodienchinh_doan;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.giaodienchinh_doan.Model.NewProductsModel;
import com.example.giaodienchinh_doan.Model.PopularProductsModel;
import com.example.giaodienchinh_doan.Model.SearchViewModel;
import com.example.giaodienchinh_doan.Model.ShowAllModel;
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
    TextView rating, name, description, price;
    Button addToCart, buyNow;
    ImageView addItems, removeItems;

    //New products
    NewProductsModel newProductsModel=null;
    //Show all
    ShowAllModel showAllModel=null;
    PopularProductsModel productsModel = null;
    SearchViewModel searchViewModel = null;
    FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();

        final Object obj = getIntent().getSerializableExtra("detailed");
        if (obj instanceof NewProductsModel){
            newProductsModel=(NewProductsModel) obj;
        }else if(obj instanceof ShowAllModel){
            showAllModel=(ShowAllModel) obj;
        }else if (obj instanceof PopularProductsModel){
            productsModel = (PopularProductsModel) obj;
        }else if (obj instanceof SearchViewModel){
            searchViewModel=(SearchViewModel) obj;
        }

        detailedImg=findViewById(R.id.detailed_img);
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
        }
        //Show all
        if(showAllModel !=null){
            Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(detailedImg);
            name.setText(showAllModel.getName());
            rating.setText(showAllModel.getRating());
            description.setText(showAllModel.getDescription());
            price.setText(String.valueOf(showAllModel.getPrice()));

        }
        //Popular Products
        if(productsModel !=null){
            Glide.with(getApplicationContext()).load(productsModel.getImg_url()).into(detailedImg);
            name.setText(productsModel.getName());
            rating.setText(productsModel.getRating());
            description.setText(productsModel.getDescription());
            price.setText(String.valueOf(productsModel.getPrice()));
        }
        //Search
        if(searchViewModel !=null){
            Glide.with(getApplicationContext()).load(searchViewModel.getImgUrl()).into(detailedImg);
            name.setText(searchViewModel.getName());
            rating.setText(searchViewModel.getRating());
            description.setText(searchViewModel.getDescription());
            price.setText(String.valueOf(searchViewModel.getPrice()));
        }
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });
    }

    private void addToCart() {
        String saveCurrentTime = null, saveCurrentDate;
        Calendar calForDate= Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("dd,mm,yyyy");
        saveCurrentDate=currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentDate=currentTime.format(calForDate.getTime());

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("productName", name.getText().toString());
        cartMap.put("productPrice", price.getText().toString());
        cartMap.put("currentTime", saveCurrentTime);
        cartMap.put("currentDate", saveCurrentDate);

        firestore.collection("AddToCart").document()
                .collection("User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(DetailedActivity.this, "Added to a Cart", Toast.LENGTH_SHORT).show();
                        finish();

                    }
                });


    }
}