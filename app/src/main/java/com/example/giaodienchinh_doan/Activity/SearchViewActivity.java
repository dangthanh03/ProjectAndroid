package com.example.giaodienchinh_doan.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import com.example.giaodienchinh_doan.AdapterView.SearchAdapter;
import com.example.giaodienchinh_doan.Model.SearchViewModel;
import com.example.giaodienchinh_doan.R;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SearchViewActivity extends AppCompatActivity {
    RecyclerView search_list_results;
    ArrayList<SearchViewModel>list;
    SearchAdapter searchAdapter;
    FirebaseFirestore dbFirestore;
    AlertDialog progressDialog;
    SearchView search_view_field;
    Toolbar toolbar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);

        toolbar=findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextAppearance(this, R.style.TitleTextAppearance_Bold);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        search_list_results = findViewById(R.id.search_list_results);
        search_list_results.hasFixedSize();
        search_list_results.setLayoutManager(new LinearLayoutManager(this));
        search_list_results.setVisibility(View.GONE);
        ProgressBar progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(progressBar);
        builder.setMessage("Searching...");

        progressDialog = builder.create();
        progressDialog.setCancelable(false);
        progressDialog.show();


        dbFirestore = FirebaseFirestore.getInstance();
        list = new ArrayList<SearchViewModel>();
        searchAdapter = new SearchAdapter(SearchViewActivity.this,list);
        EventChangeListener();
        search_list_results.setAdapter(searchAdapter);
        search_view_field = findViewById(R.id.search_view_field);
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        search_view_field.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        search_view_field.setMaxWidth(Integer.MAX_VALUE);
        search_view_field.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchAdapter.getFilter().filter(s);
                return false;
            }

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    search_list_results.setVisibility(View.GONE);
                }
            }
            @Override
            public boolean onQueryTextChange(String s) {
                searchAdapter.getFilter().filter(s);
                search_list_results.setVisibility(View.VISIBLE);
                return false;
            }
        });
    }

    private void EventChangeListener() {
        dbFirestore.collection("ShowAll").orderBy("name", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            if (progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                            Log.e("Firestore Error",error.getMessage());
                            return;
                        }
                        for (DocumentChange dc: value.getDocumentChanges()){
                            if (dc.getType()== DocumentChange.Type.ADDED){
                                list.add(dc.getDocument().toObject(SearchViewModel.class));
                            }
                            searchAdapter.notifyDataSetChanged();
                            if (progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                        }
                    }
                });
    }

}