package com.cynoteck.petofy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cynoteck.petofy.R;
import com.cynoteck.petofy.response.getPetReportsResponse.getPetListResponse.PetList;
import com.cynoteck.petofy.onClicks.ViewDeatilsAndIdCardClick;

import java.util.ArrayList;

public class ScanPetListAdapter extends RecyclerView.Adapter<ScanPetListAdapter.MyViewHolder> {

    Context context;
    ArrayList<PetList> profileList;
    private ViewDeatilsAndIdCardClick onProductItemClickListner;

    public ScanPetListAdapter(Context context, ArrayList<PetList> profileList, ViewDeatilsAndIdCardClick onProductItemClickListner) {
        this.context = context;
        this.profileList = profileList;
        this.onProductItemClickListner = onProductItemClickListner;
    }

    @NonNull
    @Override
    public ScanPetListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.scan_list_layout, parent, false);
        ScanPetListAdapter.MyViewHolder vh = new ScanPetListAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ScanPetListAdapter.MyViewHolder holder, int position) {
        holder.pet_reg_name_TV.setText(profileList.get(position).getPetName().substring(0, 1).toUpperCase() + profileList.get(position).getPetName().substring(1));
        Glide.with(context)
                .load(profileList.get(position).getPetProfileImageUrl())
                .placeholder(R.drawable.empty_pet_image)
                .into(holder.petRegImage_IV);
    }

    @Override
    public int getItemCount() {
        return profileList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView petRegImage_IV;
        TextView pet_reg_name_TV;
        Button view_profile_BT, add_clinic_visit_BT;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            petRegImage_IV = itemView.findViewById(R.id.petRegImage_IV);
            pet_reg_name_TV = itemView.findViewById(R.id.pet_reg_name_TV);
            add_clinic_visit_BT = itemView.findViewById(R.id.add_clinic_visit_BT);
            view_profile_BT = itemView.findViewById(R.id.view_profile_BT);

            view_profile_BT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onProductItemClickListner != null) {
                        onProductItemClickListner.onViewDetailsClick(getAdapterPosition());
                    }
                }
            });

            add_clinic_visit_BT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onProductItemClickListner != null) {
                        onProductItemClickListner.onViewReportsClick(getAdapterPosition());
                    }
                }
            });

        }

    }
}