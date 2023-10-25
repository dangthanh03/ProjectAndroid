package com.example.giaodienchinh_doan.sampledata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.giaodienchinh_doan.R;
import com.example.giaodienchinh_doan.Fragment.ShopFragment;
import com.squareup.picasso.Picasso;

public class ViewPhotoDetail extends AppCompatActivity {
    ImageView imageView;
    TextView tv_detail_title, tv_detail_description, shop_now_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photo_detail);
        imageView=findViewById(R.id.iv_detail);
        tv_detail_title=findViewById(R.id.tv_detail_title);
        tv_detail_description=findViewById(R.id.tv_detail_description);
        shop_now_btn = findViewById(R.id.shop_now_btn);
        int id = (int) getIntent().getLongExtra("id",0);
        Picasso.get().load((PhotoData.getPhotoFromId(id).getImgSrc())).resize(400,500).centerCrop().into(imageView);
        tv_detail_title.setText((PhotoData.getPhotoFromId(id)).getPhotoTitle());
        tv_detail_description.setText((PhotoData.getPhotoFromId(id)).getDescription());
        Fragment shopFragment = new ShopFragment();
        shop_now_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.shopFragment,shopFragment);
                transaction.commit();
            }
        });
    }
}