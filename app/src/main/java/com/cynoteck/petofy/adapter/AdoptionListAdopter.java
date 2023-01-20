package com.cynoteck.petofy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cynoteck.petofy.R;
import com.cynoteck.petofy.response.adoptionListResponse.PetDonationList;
import com.cynoteck.petofy.onClicks.AdoptionListOnClick;

import java.util.List;

public class AdoptionListAdopter extends RecyclerView.Adapter<AdoptionListAdopter.MyViewHolder> {

    List<PetDonationList>   petDonationLists;
    AdoptionListOnClick     adoptionListOnClick;
    Context                 context;


    public AdoptionListAdopter(Context context, List<PetDonationList> petDonationLists, AdoptionListOnClick adoptionListOnClick) {
        this.petDonationLists       = petDonationLists;
        this.adoptionListOnClick    = adoptionListOnClick;
        this.context                = context;

    }

    @NonNull
    @Override
    public AdoptionListAdopter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pet_breeds_list, parent, false);
        AdoptionListAdopter.MyViewHolder vh = new AdoptionListAdopter.MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull AdoptionListAdopter.MyViewHolder holder, final int position) {
        Glide.with(context)
                .load(petDonationLists.get(position).getPetImageList().get(0).getPetImageUrl())
                .placeholder(R.drawable.empty_pet_image)
                .into(holder.pet_breed_IV);

        holder.pet_breed_name_TV.setText(petDonationLists.get(position).getPetName());

    }

    @Override
    public int getItemCount() {
        return petDonationLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView pet_breed_name_TV;
        ImageView pet_breed_IV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pet_breed_name_TV   = itemView.findViewById(R.id.pet_breed_name_TV);
            pet_breed_IV        = itemView.findViewById(R.id.pet_breed_IV);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adoptionListOnClick.onItemClick(getAdapterPosition());
                }
            });

        }
    }

}
