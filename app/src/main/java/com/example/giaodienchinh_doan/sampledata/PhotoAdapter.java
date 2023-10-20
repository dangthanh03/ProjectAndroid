package com.example.giaodienchinh_doan.sampledata;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.giaodienchinh_doan.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PhotoAdapter extends BaseAdapter {
    private ArrayList<Photo>photo_list;
    private Context context;

    public PhotoAdapter(ArrayList<Photo> photo_list, Context context) {
        this.photo_list = photo_list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return photo_list.size();
    }

    @Override
    public Object getItem(int i) {
        return photo_list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return photo_list.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final MyView dataView;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            dataView = new MyView();
            view = inflater.inflate(R.layout.photo_display_tpl,null);
            dataView.iv_photo = view.findViewById(R.id.imv_photo);
            dataView.tv_caption = view.findViewById(R.id.tv_title);
            view.setTag(dataView);
        }
        else {
            dataView = (MyView)view.getTag();
        }
        Picasso.get().load(photo_list.get(i).getImgSrc()).resize(300,400).centerCrop().into(dataView.iv_photo);
        dataView.tv_caption.setText(photo_list.get(i).getPhotoTitle());
        return view;
    }
    private static class MyView{
        ImageView iv_photo;
        TextView tv_caption;
    }
}
