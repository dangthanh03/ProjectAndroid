package com.example.giaodienchinh_doan.Fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giaodienchinh_doan.AdapterView.ShowAllAdapter;
import com.example.giaodienchinh_doan.Model.BrandsModel;
import com.example.giaodienchinh_doan.Model.PopularProductsModel;
import com.example.giaodienchinh_doan.Model.ShowAllModel;
import com.example.giaodienchinh_doan.R;
import com.example.giaodienchinh_doan.AdapterView.BrandsAdapter;
import com.example.giaodienchinh_doan.AdapterView.NewProductsAdapter;
import com.example.giaodienchinh_doan.Model.NewProductsModel;
import com.example.giaodienchinh_doan.Activity.ShowAllActivity;
import com.example.giaodienchinh_doan.AdapterView.PopularProductsAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShopFragment extends Fragment {
    RecyclerView brandRecycleview, newProductRecycleview, popularRecycleView, showAllRecycleview;
    TextView newProductsShowAll, popularShowAll;
    BrandsAdapter brandsAdapter;
    BrandsModel brandsModel;
    List<BrandsModel> brandsModelList;
    NewProductsAdapter newProductsAdapter;
    List<NewProductsModel> newProductsModelList;
    PopularProductsAdapter popularProductsAdapter;
    List<PopularProductsModel> popularProductsModelList;
//    List<ShowAllModel> newProductsModelList;
    ShowAllAdapter showAllAdapter;
    List<ShowAllModel> showAllModelsList;
    FirebaseFirestore db;
    ImageView searchIcon;

    public ShopFragment() {
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_shop, container, false);
        db = FirebaseFirestore.getInstance();

        brandRecycleview=root.findViewById(R.id.rec_brand);
        newProductRecycleview = root.findViewById(R.id.new_product_rec);
        //popularRecycleView = root.findViewById(R.id.popular_rec);
        showAllRecycleview=root.findViewById(R.id.popular_rec);
        newProductsShowAll=root.findViewById(R.id.newProducts_see_all);
        popularShowAll=root.findViewById(R.id.popular_see_all);

//        searchIcon = root.findViewById(R.id.search_icon);
//        searchIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ShopFragment.this.requireContext(), SearchViewActivity.class);
//                startActivity(intent);
//            }
//        });


        newProductsShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });

        popularShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });
        //Brands
        brandRecycleview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        brandsModelList = new ArrayList<>();
        brandsAdapter = new BrandsAdapter(getContext(), brandsModelList);
        brandRecycleview.setAdapter(brandsAdapter);
        db.collection("Brands")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                BrandsModel brandsModel=document.toObject(BrandsModel.class);
                                brandsModelList.add(brandsModel);
                                brandsAdapter.notifyDataSetChanged();
                            }

                        } else {
                            Toast.makeText(getActivity(),""+task.getException(), Toast.LENGTH_SHORT).show();


                        }
                    }
                });


        //New Products
        newProductRecycleview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
//        newProductsModelList=new ArrayList<>();
//        newProductRecycleview.setAdapter(showAllAdapter);
        newProductsModelList = new ArrayList<>();
        newProductsAdapter = new NewProductsAdapter(getContext(),newProductsModelList);
        newProductRecycleview.setAdapter(newProductsAdapter);


//        db.collection("ShowAll").whereEqualTo("status","new")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if(task.isSuccessful()){
//                            for(DocumentSnapshot doc :task.getResult().getDocuments()){
//                                ShowAllModel showAllModel=doc.toObject(ShowAllModel.class);
//                                newProductsModelList.add(showAllModel);
//                                showAllAdapter.notifyDataSetChanged();
//                            }
//                        }
//                    }
//                });

        db.collection("NewProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                NewProductsModel newProductsModel=document.toObject(NewProductsModel.class);
                                newProductsModelList.add(newProductsModel);
                                newProductsAdapter.notifyDataSetChanged();
                            }

                        } else {
                            Toast.makeText(getActivity(),""+task.getException(), Toast.LENGTH_SHORT).show();


                        }
                    }
                });

        //Popular Products
//        popularRecycleView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
//        popularProductsModelList = new ArrayList<>();
//        popularProductsAdapter = new PopularProductsAdapter(getContext(), popularProductsModelList);
//        popularRecycleView.setAdapter(popularProductsAdapter);


        showAllRecycleview.setLayoutManager(new GridLayoutManager(getActivity(),2));
        showAllModelsList = new ArrayList<>();
        showAllAdapter =new ShowAllAdapter(getContext(), showAllModelsList);
        showAllRecycleview.setAdapter(showAllAdapter);




        db.collection("ShowAll")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                ShowAllModel showAllModel=document.toObject(ShowAllModel.class);
                                showAllModelsList.add(showAllModel);
                                showAllAdapter.notifyDataSetChanged();
                            }

                        } else {
                            Toast.makeText(getActivity(),""+task.getException(), Toast.LENGTH_SHORT).show();


                        }
                    }
                });

        return root;

    }
}