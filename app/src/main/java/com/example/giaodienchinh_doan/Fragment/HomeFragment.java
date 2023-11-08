package com.example.giaodienchinh_doan.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.giaodienchinh_doan.Model.BrandsModel;
import com.example.giaodienchinh_doan.R;
import com.example.giaodienchinh_doan.sampledata.Photo;
import com.example.giaodienchinh_doan.sampledata.PhotoAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }
    RecyclerView recyclerView;
    ArrayList<Photo>photoModellist;
    PhotoAdapter photoAdapter;
    FirebaseFirestore fb;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home, container, false);
        View view = inflater.inflate(R.layout.fragment_home, container,false);
        recyclerView = view.findViewById(R.id.list_view_news);
        photoModellist = new ArrayList<Photo>();
        photoAdapter = new PhotoAdapter(getContext(),photoModellist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fb = FirebaseFirestore.getInstance();
        recyclerView.setAdapter(photoAdapter);
        fb.collection("News")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Photo photoModel=document.toObject(Photo.class);
                                photoModellist.add(photoModel);
                                photoAdapter.notifyDataSetChanged();
                            }

                        } else {
                            Toast.makeText(getActivity(),""+task.getException(), Toast.LENGTH_SHORT).show();


                        }
                    }
                });
        return view;
    }

}