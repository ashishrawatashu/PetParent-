package com.cynoteck.petofyparents.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.response.getSearchKeywordResponse.SearchKeywordData;
import com.cynoteck.petofyparents.onClicks.OnItemClickListener;

import java.util.List;

public class SearchKeywordAdapter extends RecyclerView.Adapter<SearchKeywordAdapter.MyViewHolder> {
    Context context;
    List<SearchKeywordData> searchKeywordDataList;
    OnItemClickListener onItemClickListener;
    public SearchKeywordAdapter(Context context, List<SearchKeywordData> searchKeywordDataList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.searchKeywordDataList = searchKeywordDataList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public SearchKeywordAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_keyword_list, parent, false);
        SearchKeywordAdapter.MyViewHolder vh = new SearchKeywordAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchKeywordAdapter.MyViewHolder holder, int position) {
       holder.keyword_TV.setText(searchKeywordDataList.get(position).getSearchKeyWord());
    }

    @Override
    public int getItemCount() {
        return searchKeywordDataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView keyword_TV;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            keyword_TV = itemView.findViewById(R.id.keyword_TV);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (onItemClickListener != null) {
                        onItemClickListener.onViewDetailsClick(getAdapterPosition());
                    }

                }
            });
        }
    }
}