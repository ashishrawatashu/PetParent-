package com.cynoteck.petofy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cynoteck.petofy.R;
import com.cynoteck.petofy.response.getpetbreedsResponse.GetPetbreedsData;
import com.cynoteck.petofy.onClicks.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class PetBreedsAdapter extends RecyclerView.Adapter<PetBreedsAdapter.MyViewHolder> implements Filterable {
    Context                     context;
    List<GetPetbreedsData>      getPetbreedsData;
    List<GetPetbreedsData>      getPetbreedsDataFilter;

    private OnItemClickListener onProductItemClickListner;

    public PetBreedsAdapter(Context context, List<GetPetbreedsData> getPetbreedsData, OnItemClickListener onProductItemClickListner) {
        this.context = context;
        this.getPetbreedsData = getPetbreedsData;
        this.onProductItemClickListner = onProductItemClickListner;
        getPetbreedsDataFilter = new ArrayList<>(getPetbreedsData);
    }


    @NonNull
    @Override
    public PetBreedsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pet_breeds_list, parent, false);
        PetBreedsAdapter.MyViewHolder vh = new PetBreedsAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PetBreedsAdapter.MyViewHolder holder, int position) {


        holder.pet_breed_name_TV.setText(getPetbreedsData.get(position).getBreedName());
        Glide.with(context)
                .load(getPetbreedsData.get(position).getImageUrl())
                .placeholder(R.drawable.image_empty_state)
                .into(holder.pet_breed_IV);


    }

    @Override
    public int getItemCount() {
        return getPetbreedsData.size();
    }

    @Override
    public Filter getFilter() {
        return filterList;
    }

    private Filter filterList = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<GetPetbreedsData> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(getPetbreedsDataFilter);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (GetPetbreedsData item : getPetbreedsDataFilter) {
                    if (item.getBreedName().toLowerCase().contains(filterPattern)) {
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
            getPetbreedsData.clear();
            getPetbreedsData.addAll((List<GetPetbreedsData>) results.values);
            notifyDataSetChanged();

        }
    };


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView   pet_breed_IV;
        TextView    pet_breed_name_TV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pet_breed_name_TV   = itemView.findViewById(R.id.pet_breed_name_TV);
            pet_breed_IV        = itemView.findViewById(R.id.pet_breed_IV);

            itemView.setOnClickListener(new View.OnClickListener() {
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
