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
        holder.vet_qualification_TV.setText(providerLists.get(position).getVetQualifications());
        holder.vet_location_TV.setText(providerLists.get(position).getAddress());
        if (providerLists.get(position).getOnlineConsultationCharges().equals("0")){
            holder.vet_charges_tv.setVisibility(View.GONE);

        }else {
            holder.vet_charges_tv.setVisibility(View.VISIBLE);
            holder.vet_charges_tv.setText("₹ "+ providerLists.get(position).getOnlineConsultationCharges());
        }

        Glide.with(context)
                .load(providerLists.get(position).getProfileImageURL())
                .placeholder(R.drawable.doctor_dummy_image)
                .into(holder.vet_image_IV);

    }

    @Override
    public int getItemCount() {
        return providerLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView vet_image_IV,star_one,star_two,star_three,star_fourstar_five;
        TextView vet_name_TV, vet_qualification_TV,vet_charges_tv,vet_location_TV;
        LinearLayout view_more_LL;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            vet_image_IV = itemView.findViewById(R.id.vet_image_IV);
            vet_name_TV = itemView.findViewById(R.id.vet_name_TV);
            vet_qualification_TV = itemView.findViewById(R.id.vet_qualification_TV);
            vet_charges_tv = itemView.findViewById(R.id.vet_charges_tv);
            vet_location_TV = itemView.findViewById(R.id.vet_location_TV);
            view_more_LL = itemView.findViewById(R.id.view_more_LL);

            view_more_LL.setOnClickListener(new View.OnClickListener() {
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
