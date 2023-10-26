package com.example.giaodienchinh_doan.AdapterView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giaodienchinh_doan.AnotherNav.InboxAdapter;
import com.example.giaodienchinh_doan.DetailedActivity;
import com.example.giaodienchinh_doan.Model.SearchViewModel;
import com.example.giaodienchinh_doan.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder>  implements Filterable {
    Context context;
    ArrayList<SearchViewModel>list;
    ArrayList<SearchViewModel>listOld;

    public SearchAdapter(Context context, ArrayList<SearchViewModel> list) {
        this.context = context;
        this.list = list;
        this.listOld=list;
    }

    @NonNull
    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_search_list,parent,false);
        return new SearchViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.SearchViewHolder holder, int position) {
        SearchViewModel item = list.get(position);
        String imgUrl = item.getImg_url();
        Picasso.get().load(imgUrl).into(holder.iv_search);
        holder.tv_nameSearch.setText(item.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailedActivity.class);
                intent.putExtra("detailed",list.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String search = charSequence.toString();
                if (search.isEmpty()){
                    list = listOld;
                }
                else{
                    ArrayList<SearchViewModel>list1 = new ArrayList<>();
                    for (SearchViewModel item: listOld){
                        if(item.getName().toLowerCase().contains(search.toLowerCase())){
                            list1.add(item);
                        }
                    }
                    list=list1;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list = (ArrayList<SearchViewModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder{

        TextView tv_nameSearch;
        ImageView iv_search;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nameSearch = itemView.findViewById(R.id.tv_nameSearch);
            iv_search = itemView.findViewById(R.id.iv_search);

        }
    }
}
