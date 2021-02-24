package com.cynoteck.petofyparents.adapter;

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
import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.response.getPetReportsResponse.getPetListResponse.PetList;
import com.cynoteck.petofyparents.response.getVetListResponse.ProviderList;
import com.cynoteck.petofyparents.utils.RegisterRecyclerViewClickListener;

import java.util.List;

public class VetListAdapter extends RecyclerView.Adapter<VetListAdapter.MyViewHolder> {
    Context context;
    List<ProviderList> providerLists;
    RegisterRecyclerViewClickListener onProductItemClickListner;

    public VetListAdapter(Context context, List<ProviderList> providerLists, RegisterRecyclerViewClickListener onProductItemClickListner) {
        this.context = context;
        this.providerLists = providerLists;
        this.onProductItemClickListner = onProductItemClickListner;
    }

    @NonNull
    @Override
    public VetListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.vet_list, parent, false);
        VetListAdapter.MyViewHolder vh = new VetListAdapter.MyViewHolder(v);
        return vh;    }

    @Override
    public void onBindViewHolder(@NonNull VetListAdapter.MyViewHolder holder, int position) {
        holder.vet_name_TV.setText(providerLists.get(position).getName()+" "+"("+providerLists.get(position).getVetQualifications()+")");
        holder.distance_TV.setText(providerLists.get(position).getDistance());
        holder.address_TV.setText(providerLists.get(position).getAddress());
        holder.vet_email_TV.setText(providerLists.get(position).getEmail());

        Glide.with(context)
                .load(providerLists.get(position).getProfileImageURL())
                .placeholder(R.drawable.doctor_dummy_image)
                .into(holder.vet_profile_IV);

    }

    @Override
    public int getItemCount() {
        return providerLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView vet_profile_IV;
        TextView vet_name_TV, distance_TV,address_TV,vet_officeName_TV,vet_email_TV;
        Button book_appointment_BT;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            vet_profile_IV = itemView.findViewById(R.id.vetImage_IV);
            vet_name_TV = itemView.findViewById(R.id.vet_name_TV);
            distance_TV = itemView.findViewById(R.id.distance_TV);
            address_TV = itemView.findViewById(R.id.address_TV);
            vet_officeName_TV = itemView.findViewById(R.id.vet_officeName_TV);
            vet_email_TV = itemView.findViewById(R.id.vet_email_TV);
            book_appointment_BT = itemView.findViewById(R.id.book_appointment_BT);

            book_appointment_BT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onProductItemClickListner!=null){
                        onProductItemClickListner.onProductClick(getAdapterPosition());
                    }
                }
            });


        }
    }
}
