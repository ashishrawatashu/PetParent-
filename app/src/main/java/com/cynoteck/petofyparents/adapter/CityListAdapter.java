package com.cynoteck.petofyparents.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.response.getCityListWithStateResponse.GetCityListWithData;
import com.cynoteck.petofyparents.utils.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.MyViewHolder> implements Filterable {
    Context context;
    List<GetCityListWithData> cityListWithData;
    List<GetCityListWithData> cityListWithDataFilter;

    private OnItemClickListener onProductItemClickListner;

    public CityListAdapter(Context context, List<GetCityListWithData> cityListWithData, OnItemClickListener onProductItemClickListner) {
        this.context = context;
        this.cityListWithData = cityListWithData;
        this.onProductItemClickListner = onProductItemClickListner;
        cityListWithDataFilter = new ArrayList<>(cityListWithData);

        //filterProfileList = new ArrayList<>(profileList);
    }

    @NonNull
    @Override
    public CityListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_list_layout, parent, false);
        CityListAdapter.MyViewHolder vh = new CityListAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CityListAdapter.MyViewHolder holder, int position) {
        holder.city_name_TV.setText(cityListWithData.get(position).getCityName());
    }

    @Override
    public int getItemCount() {
        return cityListWithData.size();
    }

    @Override
    public Filter getFilter() {
        return filterList;
    }

    private Filter filterList = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<GetCityListWithData> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(cityListWithDataFilter);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (GetCityListWithData item : cityListWithDataFilter) {
                    if (item.getCity1().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            cityListWithData.clear();
            cityListWithData.addAll((List<GetCityListWithData>) results.values);
            notifyDataSetChanged();

        }
    };


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView city_name_TV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            city_name_TV = itemView.findViewById(R.id.city_name_TV);

            city_name_TV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onProductItemClickListner != null) {
                        onProductItemClickListner.onViewDetailsClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}
