package com.cynoteck.petofyparents.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.response.appointmentResponse.AppointmentList;
import com.cynoteck.petofyparents.response.donationResponse.Datum;
import com.cynoteck.petofyparents.utils.AppointmentsClickListner;
import com.cynoteck.petofyparents.utils.DonationClickListener;

import java.util.ArrayList;
import java.util.List;

public class DonationAdopter extends RecyclerView.Adapter<com.cynoteck.petofyparents.adapter.DonationAdopter.MyViewHolder> {

    List<Datum> data;
    DonationClickListener donationClickListener;
    Context context;

    public DonationAdopter(Context context, List<Datum> data, DonationClickListener donationClickListener) {
        this.data = data;
        this.donationClickListener = donationClickListener;
        this.context = context;

    }

    @NonNull
    @Override
    public com.cynoteck.petofyparents.adapter.DonationAdopter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adoption_list_adopter, parent, false);
        com.cynoteck.petofyparents.adapter.DonationAdopter.MyViewHolder vh = new com.cynoteck.petofyparents.adapter.DonationAdopter.MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull com.cynoteck.petofyparents.adapter.DonationAdopter.MyViewHolder holder, final int position)
    {
        holder.pet_name_TV.setText(data.get(position).getPetName());
        holder.pet_gender_TV.setText(data.get(position).getPetSex());
        holder.pet_color_TV.setText(data.get(position).getPetColor());
        holder.pet_Breed_TV.setText(data.get(position).getPetBreed());

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
