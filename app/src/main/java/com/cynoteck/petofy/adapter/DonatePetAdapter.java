package com.cynoteck.petofy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cynoteck.petofy.R;
import com.cynoteck.petofy.response.getPetReportsResponse.getPetListResponse.PetList;
import com.cynoteck.petofy.onClicks.OnItemClickListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DonatePetAdapter extends RecyclerView.Adapter<DonatePetAdapter.MyViewHolder> {

    ArrayList<PetList> petLists;
    OnItemClickListener onClickListener;
    Context context;

    public DonatePetAdapter( Context context, ArrayList<PetList> petLists, OnItemClickListener onClickListener) {
        this.petLists = petLists;
        this.onClickListener = onClickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public DonatePetAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.donate_pet_list, parent, false);
        DonatePetAdapter.MyViewHolder vh = new DonatePetAdapter.MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull DonatePetAdapter.MyViewHolder holder, final int position) {
        Glide.with(context)
                .load(petLists.get(position).getPetProfileImageUrl())
                .placeholder(R.drawable.empty_pet_image)
                .into(holder.pet_image_CIV);
        holder.pet_name_TV.setText(petLists.get(position).getPetName());

    }

    @Override
    public int getItemCount() {
        return petLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView pet_image_CIV;
        TextView pet_name_TV;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            pet_image_CIV = itemView.findViewById(R.id.pet_image_CIV);
            pet_name_TV = itemView.findViewById(R.id.pet_name_TV);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener!=null){
                        onClickListener.onViewDetailsClick(getAdapterPosition());
                    }
                }
            });


        }
    }
}