package com.example.giaodienchinh_doan.AnotherNav;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giaodienchinh_doan.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.MyViewHolder> {

    Context context;
    ArrayList<InboxItem>list;

    public InboxAdapter(Context context, ArrayList<InboxItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.message,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        InboxItem item = list.get(position);
        holder.inbox_text_title.setText(item.getTitle());
        holder.inbox_text_content.setText(item.getContent());
        String imgUrl = item.getImgUrl();
        Picasso.get().load(imgUrl).into(holder.inbox_img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView inbox_text_title,inbox_text_content;
        ImageView inbox_img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            inbox_text_title = itemView.findViewById(R.id.inbox_text_title);
            inbox_text_content = itemView.findViewById(R.id.inbox_text_content);
            inbox_img = itemView.findViewById(R.id.inbox_img);
        }
    }
}
