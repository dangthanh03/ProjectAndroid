
package com.example.giaodienchinh_doan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.giaodienchinh_doan.Model.MyCartModel;
import com.example.giaodienchinh_doan.Model.NewProductsModel;
import com.example.giaodienchinh_doan.Model.PopularProductsModel;
import com.example.giaodienchinh_doan.Model.SearchViewModel;
import com.example.giaodienchinh_doan.Model.ShowAllModel;
import com.example.giaodienchinh_doan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

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
    SearchViewModel searchViewModel = null;

    FirebaseAuth auth;
    Spinner sizeSpinner;
    private FirebaseFirestore firestore;
    int num;
    int totalquantity=1;
    Float totalPrice= (float) 0;
    private boolean isFavourite = false;
    private String productname;
    private SharedPreferences sharedPreferences;

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
        }else if (obj instanceof SearchViewModel){
            searchViewModel = (SearchViewModel) obj;
        }
        sizeSpinner = findViewById(R.id.spinner);
        detailedImg=findViewById(R.id.detailed_img);
        quantity=findViewById(R.id.quantity);
        rating=findViewById(R.id.my_rating);
        name=findViewById(R.id.detailed_name);
        description=findViewById(R.id.detailed_desc);
        price=findViewById(R.id.detailed_price);
        favourite=findViewById(R.id.favourite);
        addToCart=findViewById(R.id.add_to_cart);
        removeItems=findViewById(R.id.remove_item);
        addItems=findViewById(R.id.add_item);
        String[] shoeSizes = {"38", "39", "40", "41", "42", "43"};

        // Tạo một ArrayAdapter để liên kết mảng kích cỡ giày với Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, shoeSizes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Gán ArrayAdapter cho Spinner
        sizeSpinner.setAdapter(adapter);

        // Xử lý sự kiện khi người dùng chọn một mục trong Spinner
        sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedSize = parent.getItemAtPosition(position).toString();

                Toast.makeText(DetailedActivity.this, "Bạn đã chọn kích cỡ: " + selectedSize, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Không làm gì khi không có mục được chọn
            }
        });
        //New Products
        if(newProductsModel !=null){
            Glide.with(getApplicationContext()).load(newProductsModel.getImg_url()).into(detailedImg);
            url=newProductsModel.getImg_url();
            name.setText(newProductsModel.getName());
            rating.setText(newProductsModel.getRating());
            description.setText(newProductsModel.getDescription());
            price.setText(String.valueOf(newProductsModel.getPrice()));
            totalPrice=newProductsModel.getPrice() * totalquantity;
            totalPrice=Math.round(totalPrice*100)/100f;
            //Button favourite
            sharedPreferences = getSharedPreferences("my_fav_shared_preferences", MODE_PRIVATE);
            if (savedInstanceState != null) {
                String name = savedInstanceState.getString("productname");
            } else {
                String name = getIntent().getStringExtra("productname");
            }
            if (savedInstanceState != null) {
                isFavourite = savedInstanceState.getBoolean("productname" + name.getText() + "btnFavouriteClick");
            } else {
                isFavourite = sharedPreferences.getBoolean("productname" + name.getText() + "btnFavouriteClick", false);
            }

            if (isFavourite) {
                favourite.setBackgroundColor(Color.parseColor("#FF9999"));
                favourite.setText("Added to Favourite");
            } else {
                favourite.setBackgroundColor(Color.WHITE);
                favourite.setText("Favourite");
            }
            favourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    boolean isFavourite = sharedPreferences.getBoolean("btnFavouriteClick", false);
                    isFavourite = !isFavourite;
                    sharedPreferences.edit().putBoolean("productname" + name.getText() + "btnFavouriteClick", isFavourite).apply();
                    if(isFavourite){
                        favourite.setBackgroundColor(Color.parseColor("#FF9999"));
                        favourite.setText("Added to Favourite");
                        Toast.makeText(DetailedActivity.this, "Add to Favourite", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        favourite.setBackgroundColor(Color.WHITE);
                        favourite.setText("Favourite");
                    }
                }
            });
            if (savedInstanceState != null) {
                isFavourite = savedInstanceState.getBoolean("btnFavouriteClick");
            } else {
                boolean favourite = sharedPreferences.getBoolean("btnFavouriteClick", false);
            }

            if(isFavourite) favourite.setBackgroundColor(Color.parseColor("#FF9999"));
            else favourite.setBackgroundColor(Color.WHITE);
        }
//        Popular Products
        if(popularProductsModel !=null){
            Glide.with(getApplicationContext()).load(popularProductsModel.getImg_url()).into(detailedImg);
            url=popularProductsModel.getImg_url();
            name.setText(popularProductsModel.getName());
            rating.setText(popularProductsModel.getRating());
            description.setText(popularProductsModel.getDescription());
            price.setText(String.valueOf(popularProductsModel.getPrice()));
            totalPrice=popularProductsModel.getPrice() * totalquantity;
            totalPrice=Math.round(totalPrice*100)/100f;
            //Button favourite
            sharedPreferences = getSharedPreferences("my_fav_shared_preferences", MODE_PRIVATE);
            if (savedInstanceState != null) {
                String name = savedInstanceState.getString("productname");
            } else {
                String name = getIntent().getStringExtra("productname");
            }
            if (savedInstanceState != null) {
                isFavourite = savedInstanceState.getBoolean("productname" + name.getText() + "btnFavouriteClick");
            } else {
                isFavourite = sharedPreferences.getBoolean("productname" + name.getText() + "btnFavouriteClick", false);
            }

            if (isFavourite) {
                favourite.setBackgroundColor(Color.parseColor("#FF9999"));
                favourite.setText("Added to Favourite");
            } else {
                favourite.setBackgroundColor(Color.WHITE);
                favourite.setText("Favourite");
            }
            favourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    boolean isFavourite = sharedPreferences.getBoolean("btnFavouriteClick", false);
                    isFavourite = !isFavourite;
                    sharedPreferences.edit().putBoolean("productname" + name.getText() + "btnFavouriteClick", isFavourite).apply();
                    if(isFavourite){
                        favourite.setBackgroundColor(Color.parseColor("#FF9999"));
                        favourite.setText("Added to Favourite");
                        Toast.makeText(DetailedActivity.this, "Add to Favourite", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        favourite.setBackgroundColor(Color.WHITE);
                        favourite.setText("Favourite");
                    }
                }
            });
            if (savedInstanceState != null) {
                isFavourite = savedInstanceState.getBoolean("btnFavouriteClick");
            } else {
                boolean favourite = sharedPreferences.getBoolean("btnFavouriteClick", false);
            }

            if(isFavourite) favourite.setBackgroundColor(Color.parseColor("#FF9999"));
            else favourite.setBackgroundColor(Color.WHITE);
        }
        //Show all
        if(showAllModel !=null){
            Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(detailedImg);
            url=showAllModel.getImg_url();
            name.setText(showAllModel.getName());
            rating.setText(showAllModel.getRating());
            description.setText(showAllModel.getDescription());
            price.setText(String.valueOf(showAllModel.getPrice()));
            totalPrice=showAllModel.getPrice() * totalquantity;
            totalPrice=Math.round(totalPrice*100)/100f;
            //Button favourite
            sharedPreferences = getSharedPreferences("my_fav_shared_preferences", MODE_PRIVATE);
            if (savedInstanceState != null) {
                String name = savedInstanceState.getString("productname");
            } else {
                String name = getIntent().getStringExtra("productname");
            }
            if (savedInstanceState != null) {
                isFavourite = savedInstanceState.getBoolean("productname" + name.getText() + "btnFavouriteClick");
            } else {
                isFavourite = sharedPreferences.getBoolean("productname" + name.getText() + "btnFavouriteClick", false);
            }

            if (isFavourite) {
                favourite.setBackgroundColor(Color.parseColor("#FF9999"));
                favourite.setText("Added to Favourite");
            } else {
                favourite.setBackgroundColor(Color.WHITE);
                favourite.setText("Favourite");
            }
            favourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    boolean isFavourite = sharedPreferences.getBoolean("btnFavouriteClick", false);
                    isFavourite = !isFavourite;
                    sharedPreferences.edit().putBoolean("productname" + name.getText() + "btnFavouriteClick", isFavourite).apply();
                    if(isFavourite){
                        favourite.setBackgroundColor(Color.parseColor("#FF9999"));
                        favourite.setText("Added to Favourite");
                        Toast.makeText(DetailedActivity.this, "Add to Favourite", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        favourite.setBackgroundColor(Color.WHITE);
                        favourite.setText("Favourite");
                    }
                }
            });
            if (savedInstanceState != null) {
                isFavourite = savedInstanceState.getBoolean("btnFavouriteClick");
            } else {
                boolean favourite = sharedPreferences.getBoolean("btnFavouriteClick", false);
            }

            if(isFavourite) favourite.setBackgroundColor(Color.parseColor("#FF9999"));
            else favourite.setBackgroundColor(Color.WHITE);
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
        //Search
        if (searchViewModel !=null){
            Glide.with(getApplicationContext()).load(searchViewModel.getImg_url()).into(detailedImg);
            url=searchViewModel.getImg_url();
            name.setText(searchViewModel.getName());
            rating.setText(searchViewModel.getRating());
            description.setText(searchViewModel.getDescription());
            price.setText(String.valueOf(searchViewModel.getPrice()));
            totalPrice=searchViewModel.getPrice() * totalquantity;
            totalPrice=Math.round(totalPrice*100)/100f;
            //Button favourite
            sharedPreferences = getSharedPreferences("my_fav_shared_preferences", MODE_PRIVATE);
            if (savedInstanceState != null) {
                String name = savedInstanceState.getString("productname");
            } else {
                String name = getIntent().getStringExtra("productname");
            }
            if (savedInstanceState != null) {
                isFavourite = savedInstanceState.getBoolean("productname" + name.getText() + "btnFavouriteClick");
            } else {
                isFavourite = sharedPreferences.getBoolean("productname" + name.getText() + "btnFavouriteClick", false);
            }

            if (isFavourite) {
                favourite.setBackgroundColor(Color.parseColor("#FF9999"));
                favourite.setText("Added to Favourite");
            } else {
                favourite.setBackgroundColor(Color.WHITE);
                favourite.setText("Favourite");
            }
            favourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    boolean isFavourite = sharedPreferences.getBoolean("btnFavouriteClick", false);
                    isFavourite = !isFavourite;
                    sharedPreferences.edit().putBoolean("productname" + name.getText() + "btnFavouriteClick", isFavourite).apply();
                    if(isFavourite){
                        favourite.setBackgroundColor(Color.parseColor("#FF9999"));
                        favourite.setText("Added to Favourite");
                        Toast.makeText(DetailedActivity.this, "Add to Favourite", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        favourite.setBackgroundColor(Color.WHITE);
                        favourite.setText("Favourite");
                    }
                }
            });
            if (savedInstanceState != null) {
                isFavourite = savedInstanceState.getBoolean("btnFavouriteClick");
            } else {
                boolean favourite = sharedPreferences.getBoolean("btnFavouriteClick", false);
            }

            if(isFavourite) favourite.setBackgroundColor(Color.parseColor("#FF9999"));
            else favourite.setBackgroundColor(Color.WHITE);
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
                String selectedItem = sizeSpinner.getSelectedItem().toString();
                final HashMap<String, Object> cartMap = new HashMap<>();

                cartMap.put("productName", name.getText().toString());
                cartMap.put("productPrice", price.getText().toString());
                cartMap.put("quantity", quantity.getText().toString());
                cartMap.put("totalprice",totalPrice);
                cartMap.put("img_url",url);
                cartMap.put("description",description.getText().toString());
                cartMap.put("rating",rating.getText().toString());
                cartMap.put("size",selectedItem);
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
                                    //addToCart.setTextColor(Color.GRAY);
                                    addToCart.setBackgroundColor(Color.GRAY);
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
                    totalPrice=Math.round(totalPrice*100)/100f;
                }
                if(popularProductsModel!=null){
                    totalPrice=popularProductsModel.getPrice() * totalquantity;
                    totalPrice=Math.round(totalPrice*100)/100f;
                }
                if(showAllModel!=null){
                    totalPrice=showAllModel.getPrice() * totalquantity;
                    totalPrice=Math.round(totalPrice*100)/100f;
                }
                if (searchViewModel!=null){
                    totalPrice = searchViewModel.getPrice()*totalquantity;
                    totalPrice=Math.round(totalPrice*100)/100f;
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
                    totalPrice=Math.round(totalPrice*100)/100f;
                }
                if(popularProductsModel!=null){
                    totalPrice=popularProductsModel.getPrice() * totalquantity;
                    totalPrice=Math.round(totalPrice*100)/100f;
                }
                if(showAllModel!=null){
                    totalPrice=showAllModel.getPrice() * totalquantity;
                    totalPrice=Math.round(totalPrice*100)/100f;
                }
                if (searchViewModel!=null){
                    totalPrice=searchViewModel.getPrice()*totalquantity;
                    totalPrice=Math.round(totalPrice*100)/100f;
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
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("productname", productname);
        outState.putBoolean("productname" + name.getText() + "btnFavouriteClick", isFavourite);
    }
}