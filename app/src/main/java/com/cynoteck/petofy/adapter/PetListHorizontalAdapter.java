package com.cynoteck.petofy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cynoteck.petofy.R;
import com.cynoteck.petofy.response.getPetReportsResponse.getPetListResponse.PetList;
import com.cynoteck.petofy.onClicks.OnItemClickListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PetListHorizontalAdapter extends RecyclerView.Adapter<PetListHorizontalAdapter.MyViewHolder>  {

    Context                     context;
    ArrayList<PetList>          profileList;
    private OnItemClickListener onProductItemClickListner;
    boolean                     isClicked = false;
    private int                 selectedItem;
    String                      from;
    public PetListHorizontalAdapter(Context context, String from,ArrayList<PetList> profileList, OnItemClickListener onProductItemClickListner) {
        this.context = context;
        this.from = from;
        this.profileList = profileList;
        this.onProductItemClickListner=onProductItemClickListner;
        selectedItem = 0;

    }

    @NonNull
    @Override
    public PetListHorizontalAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.register_pet_list, parent, false);
        PetListHorizontalAdapter.MyViewHolder vh = new PetListHorizontalAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PetListHorizontalAdapter.MyViewHolder holder, int position) {
        if (from.equals("ProfileFragment")){
            holder.pet_select_RB.setVisibility(View.GONE);
        }else {
            holder.pet_select_RB.setVisibility(View.VISIBLE);
        }
        holder.pet_name_TV.setText(profileList.get(position).getPetName().substring(0, 1).toUpperCase() + profileList.get(position).getPetName().substring(1));
        Glide.with(context)
                .load(profileList.get(position).getPetProfileImageUrl())
                .placeholder(R.drawable.empty_pet_image)
                .into(holder.pet_image_CIV);
        if (selectedItem == position&&isClicked==true) {
            holder.pet_select_RB.setChecked(true);
        }
        else
        {
            holder.pet_select_RB.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        return profileList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView pet_image_CIV;
        TextView        pet_name_TV;
        RadioButton     pet_select_RB;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pet_image_CIV   = itemView.findViewById(R.id.pet_image_CIV);
            pet_name_TV     = itemView.findViewById(R.id.pet_name_TV);
            pet_select_RB   = itemView.findViewById(R.id.pet_select_RB);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isClicked           = true;
                    int previousItem    = selectedItem;
                    selectedItem        = getAdapterPosition();
                    notifyItemChanged(previousItem);
                    notifyItemChanged(getAdapterPosition());
                    if (onProductItemClickListner != null) {
                        onProductItemClickListner.onViewDetailsClick(getAdapterPosition());
                    }

                }
            });

            pet_select_RB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isClicked           = true;
                    int previousItem    = selectedItem;
                    selectedItem        = getAdapterPosition();
                    notifyItemChanged(previousItem);
                    notifyItemChanged(getAdapterPosition());
                    if (onProductItemClickListner != null) {
                        onProductItemClickListner.onViewDetailsClick(getAdapterPosition());
                    }

                }
            });
        }

    }
}