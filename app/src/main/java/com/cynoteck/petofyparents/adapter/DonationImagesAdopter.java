package com.cynoteck.petofyparents.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.response.donationResponse.Datum;
import com.cynoteck.petofyparents.response.donationResponse.PetImageList;
import com.cynoteck.petofyparents.utils.DonationClickListener;
import com.cynoteck.petofyparents.utils.ImagesOnClick;

import java.util.List;

public class DonationImagesAdopter extends RecyclerView.Adapter<DonationImagesAdopter.MyViewHolder>  {
    List<PetImageList> images;
    Context context;
    ImagesOnClick imagesOnClick;

    public DonationImagesAdopter(Context context,List<PetImageList> images, ImagesOnClick imagesOnClick) {
        this.images = images;
        this.context = context;
        this.imagesOnClick = imagesOnClick;

    }

    @NonNull
    @Override
    public DonationImagesAdopter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.donation_adopter, parent, false);
        DonationImagesAdopter.MyViewHolder vh = new DonationImagesAdopter.MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull DonationImagesAdopter.MyViewHolder holder, final int position)
    {
        Glide.with(context)
                .load(images.get(position).getPetImageUrl())
                .into(holder.imageOne);

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

       ImageView imageOne;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageOne = itemView.findViewById(R.id.imageOne);

            imageOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imagesOnClick.onImgeClick(getAdapterPosition());
                }
            });

        }
    }

}
