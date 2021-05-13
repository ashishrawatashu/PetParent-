package com.cynoteck.petofyparents.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.response.getCityListWithStateResponse.GetCityListWithData;
import com.cynoteck.petofyparents.response.getPetNamesResponse.GetPetNamesData;
import com.cynoteck.petofyparents.utils.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class PetNamesAdapter extends RecyclerView.Adapter<PetNamesAdapter.MyViewHolder> implements Filterable {
    Context context;
    List<GetPetNamesData> GetPetNamesData;
    List<GetPetNamesData> GetPetNamesDataFilter;

    private OnItemClickListener onProductItemClickListner;

    public PetNamesAdapter(Context context, List<GetPetNamesData> GetPetNamesData, OnItemClickListener onProductItemClickListner) {
        this.context = context;
        this.GetPetNamesData = GetPetNamesData;
        this.onProductItemClickListner = onProductItemClickListner;
        GetPetNamesDataFilter = new ArrayList<>(GetPetNamesData);
    }


    @NonNull
    @Override
    public PetNamesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pet_names_list, parent, false);
        PetNamesAdapter.MyViewHolder vh = new PetNamesAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PetNamesAdapter.MyViewHolder holder, int position) {
        holder.pet_name_TV.setText(GetPetNamesData.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return GetPetNamesData.size();
    }

    @Override
    public Filter getFilter() {
        return filterList;
    }

    private Filter filterList = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<GetPetNamesData> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(GetPetNamesDataFilter);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (GetPetNamesData item : GetPetNamesDataFilter) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
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
            GetPetNamesData.clear();
            GetPetNamesData.addAll((List<GetPetNamesData>) results.values);
            notifyDataSetChanged();

        }
    };


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout pet_name_CL;
        TextView pet_name_TV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pet_name_TV = itemView.findViewById(R.id.pet_name_TV);
            pet_name_CL = itemView.findViewById(R.id.pet_name_CL);
            pet_name_CL.setOnClickListener(new View.OnClickListener() {
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
