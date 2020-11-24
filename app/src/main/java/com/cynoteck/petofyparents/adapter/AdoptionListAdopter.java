package com.cynoteck.petofyparents.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.response.adoptionListResponse.PetDonationList;
import com.cynoteck.petofyparents.response.adoptionResponse.AdoptionData;
import com.cynoteck.petofyparents.utils.AdoptionListOnClick;
import com.cynoteck.petofyparents.utils.DonationClickListener;

import java.util.List;

public class AdoptionListAdopter extends RecyclerView.Adapter<AdoptionListAdopter.MyViewHolder> {

    List<PetDonationList> petDonationLists;
    AdoptionListOnClick adoptionListOnClick;
    Context context;

    public AdoptionListAdopter(Context context, List<PetDonationList> petDonationLists,  AdoptionListOnClick adoptionListOnClick) {
        this.petDonationLists = petDonationLists;
        this.adoptionListOnClick = adoptionListOnClick;
        this.context = context;

    }

    @NonNull
    @Override
    public AdoptionListAdopter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adoption_list, parent, false);
        AdoptionListAdopter.MyViewHolder vh = new AdoptionListAdopter.MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull AdoptionListAdopter.MyViewHolder holder, final int position)
    {
        Glide.with(context)
                .load(petDonationLists.get(position).getPetImageList().get(0).getPetImageUrl())
                .placeholder(R.drawable.pet_image)
                .into(holder.image);

        holder.image_name.setText(petDonationLists.get(position).getPetName());

    }

    @Override
    public int getItemCount() {
        return petDonationLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout main_LL;
        TextView image_name;
        ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image_name = itemView.findViewById(R.id.image_name);
            image = itemView.findViewById(R.id.image);
            main_LL = itemView.findViewById(R.id.main_LL);

            main_LL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adoptionListOnClick.onItemClick(getAdapterPosition());
                }
            });

        }
    }

}
