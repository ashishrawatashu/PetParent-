package com.cynoteck.petofyparents.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.response.getPetReportsResponse.getPetListResponse.PetList;
import com.cynoteck.petofyparents.utils.Config;
import com.cynoteck.petofyparents.utils.ViewDeatilsAndIdCardClick;;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RegisterPetAdapter extends RecyclerView.Adapter<RegisterPetAdapter.MyViewHolder>  {

    Context context;
    ArrayList<PetList> profileList;
    private ViewDeatilsAndIdCardClick onProductItemClickListner;

    public RegisterPetAdapter(Context context, ArrayList<PetList> profileList, ViewDeatilsAndIdCardClick onProductItemClickListner) {
        this.context = context;
        this.profileList = profileList;
        this.onProductItemClickListner=onProductItemClickListner;
    }

    @NonNull
    @Override
    public RegisterPetAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pet_register_list_layout, parent, false);
        RegisterPetAdapter.MyViewHolder vh = new RegisterPetAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RegisterPetAdapter.MyViewHolder holder, int position) {
        holder.pet_reg__id_TV.setText(profileList.get(position).getPetUniqueId());
        holder.pet_reg_date_of_birth_TV.setText("DOB- "+profileList.get(position).getDateOfBirth());
        holder.pet_reg_name_TV.setText(profileList.get(position).getPetName().substring(0, 1).toUpperCase() + profileList.get(position).getPetName().substring(1));
        holder.parent_name_TV.setText(profileList.get(position).getPetBreed());

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
        TextView pet_reg__id_TV,pet_reg_date_of_birth_TV,pet_reg_name_TV,pet_reg_gender_TV,parent_name_TV;
        Button view_reg_pet_details_BT,view_reports_BT;
        LinearLayout view_details_LL;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            petRegImage_IV = itemView.findViewById(R.id.petRegImage_IV);

            parent_name_TV = itemView.findViewById(R.id.parent_name_TV);
            pet_reg__id_TV = itemView.findViewById(R.id.pet_reg__id_TV);
            pet_reg_date_of_birth_TV = itemView.findViewById(R.id.pet_reg_date_of_birth_TV);
            pet_reg_name_TV = itemView.findViewById(R.id.pet_reg_name_TV);
            view_reg_pet_details_BT = itemView.findViewById(R.id.view_reg_pet_details_BT);
            view_reports_BT=itemView.findViewById(R.id.view_reports_BT);
            view_details_LL = itemView.findViewById(R.id.view_details_LL);
            view_details_LL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onProductItemClickListner!=null){
                        onProductItemClickListner.onViewDetailsClick(getAdapterPosition());
                    }
                }
            });

            view_reports_BT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onProductItemClickListner!=null){
                        onProductItemClickListner.onViewReportsClick(getAdapterPosition());
                    }
                }
            });

        }

    }
}
