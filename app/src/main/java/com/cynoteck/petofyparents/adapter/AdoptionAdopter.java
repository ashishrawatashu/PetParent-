package com.cynoteck.petofyparents.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.response.adoptionResponse.AdoptionData;
import com.cynoteck.petofyparents.response.donationResponse.Datum;
import com.cynoteck.petofyparents.utils.DonationClickListener;

import java.util.List;

public class AdoptionAdopter extends RecyclerView.Adapter<AdoptionAdopter.MyViewHolder> {

    List<AdoptionData> data;
    DonationClickListener donationClickListener;
    Context context;

    public AdoptionAdopter(Context context, List<AdoptionData> data, DonationClickListener donationClickListener) {
        this.data = data;
        this.donationClickListener = donationClickListener;
        this.context = context;

    }

    @NonNull
    @Override
    public AdoptionAdopter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.donation_list_adopter, parent, false);
        AdoptionAdopter.MyViewHolder vh = new AdoptionAdopter.MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull AdoptionAdopter.MyViewHolder holder, final int position)
    {
        holder.pet_name_TV.setText(data.get(position).getPet().getPetName());
        holder.pet_gender_TV.setText(data.get(position).getPet().getPetSex());
        holder.pet_color_TV.setText(data.get(position).getPet().getPetColor());
        holder.pet_Breed_TV.setText(data.get(position).getPet().getPetBreed());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView pet_name_TV,pet_gender_TV,pet_color_TV,pet_Breed_TV;
        Button edit_BT,view_details_BT;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pet_name_TV = itemView.findViewById(R.id.pet_name_TV);
            pet_gender_TV = itemView.findViewById(R.id.pet_gender_TV);
            pet_color_TV = itemView.findViewById(R.id.pet_color_TV);
            pet_Breed_TV = itemView.findViewById(R.id.pet_Breed_TV);
            edit_BT = itemView.findViewById(R.id.edit_BT);
            view_details_BT=itemView.findViewById(R.id.view_details_BT);

            edit_BT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (donationClickListener!=null){
                        donationClickListener.onItemClickEdit(getAdapterPosition());
                    }
                }
            });

            view_details_BT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (donationClickListener!=null){
                        donationClickListener.onItemClickView(getAdapterPosition());
                    }
                }
            });
        }
    }
}
