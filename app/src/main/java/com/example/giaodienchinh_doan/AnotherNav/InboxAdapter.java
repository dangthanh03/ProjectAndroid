package com.example.giaodienchinh_doan.AnotherNav;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giaodienchinh_doan.Listener.UserListener;
import com.example.giaodienchinh_doan.Model.User;
import com.example.giaodienchinh_doan.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.MyViewHolder> {

    Context context;
    ArrayList<User>list;
    UserListener userListener;

    public InboxAdapter(Context context, ArrayList<User> list,UserListener userListener) {
        this.context = context;
        this.list = list;
        this.userListener=userListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.message,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User item = list.get(position);
        holder.inbox_text_title.setText(item.displayName);
        holder.inbox_text_content.setText(item.email);
        // In thông tin từng phần tử trong danh sách
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Gọi phương thức trong UserListener khi view được nhấn
                userListener.onUserClicked(item);
            }
        });

        Picasso.get().load("https://staticg.sportskeeda.com/editor/2023/07/93f89-16886348509045-1920.jpg?w=840").into(holder.inbox_img);
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
