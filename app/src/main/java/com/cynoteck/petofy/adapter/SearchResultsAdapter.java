package com.cynoteck.petofy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cynoteck.petofy.R;
import com.cynoteck.petofy.onClicks.RegisterRecyclerViewClickListener;
import com.cynoteck.petofy.response.getSearchResultsResponse.Provider;
import com.cynoteck.petofy.response.getVetListResponse.ProviderList;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.MyViewHolder> {
    Context context;
    ArrayList<Provider> providerLists;
    RegisterRecyclerViewClickListener onProductItemClickListner;

    public SearchResultsAdapter(Context context, ArrayList<Provider> providerLists, RegisterRecyclerViewClickListener onProductItemClickListner) {
        this.context = context;
        this.providerLists = providerLists;
        this.onProductItemClickListner = onProductItemClickListner;
    }

    @NonNull
    @Override
    public SearchResultsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.vet_list, parent, false);
        SearchResultsAdapter.MyViewHolder vh = new SearchResultsAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultsAdapter.MyViewHolder holder, int position) {
        holder.vet_name_TV.setText(providerLists.get(position).getName());
        holder.vet_qualification_TV.setText(providerLists.get(position).getCompany());
        holder.vet_location_TV.setText(providerLists.get(position).getAddress());
        if (providerLists.get(position).getOnlineConsultationCharges().equals("0")) {
            holder.vet_charges_tv.setVisibility(View.GONE);

        } else {
            holder.vet_charges_tv.setVisibility(View.VISIBLE);
            holder.vet_charges_tv.setText("₹ " + providerLists.get(position).getOnlineConsultationCharges());
        }

        Glide.with(context)
                .load(providerLists.get(position).getProfileImageURL())
                .placeholder(R.drawable.doctor_dummy_image)
                .into(holder.vet_image_IV);

        if (providerLists.get(position).getRating().equals("1")) {
            holder.star_one.setImageResource(R.drawable.star_with_rate);
            holder.star_two.setImageResource(R.drawable.empty_star_icon);
            holder.star_three.setImageResource(R.drawable.empty_star_icon);
            holder.star_four.setImageResource(R.drawable.empty_star_icon);
            holder.star_five.setImageResource(R.drawable.empty_star_icon);

        } else if (providerLists.get(position).getRating().equals("2")) {
            holder.star_one.setImageResource(R.drawable.star_with_rate);
            holder.star_two.setImageResource(R.drawable.star_with_rate);
            holder.star_three.setImageResource(R.drawable.empty_star_icon);
            holder.star_four.setImageResource(R.drawable.empty_star_icon);
            holder.star_five.setImageResource(R.drawable.empty_star_icon);
        } else if (providerLists.get(position).getRating().equals("3")) {
            holder.star_one.setImageResource(R.drawable.star_with_rate);
            holder.star_two.setImageResource(R.drawable.star_with_rate);
            holder.star_three.setImageResource(R.drawable.star_with_rate);
            holder.star_four.setImageResource(R.drawable.empty_star_icon);
            holder.star_five.setImageResource(R.drawable.empty_star_icon);
        } else if (providerLists.get(position).getRating().equals("4")) {
            holder.star_one.setImageResource(R.drawable.star_with_rate);
            holder.star_two.setImageResource(R.drawable.star_with_rate);
            holder.star_three.setImageResource(R.drawable.star_with_rate);
            holder.star_four.setImageResource(R.drawable.star_with_rate);
            holder.star_five.setImageResource(R.drawable.empty_star_icon);
        } else if (providerLists.get(position).getRating().equals("5")) {
            holder.star_one.setImageResource(R.drawable.star_with_rate);
            holder.star_two.setImageResource(R.drawable.star_with_rate);
            holder.star_three.setImageResource(R.drawable.star_with_rate);
            holder.star_four.setImageResource(R.drawable.star_with_rate);
            holder.star_five.setImageResource(R.drawable.star_with_rate);
        }

    }

    @Override
    public int getItemCount() {
        return providerLists.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView vet_image_IV, star_one, star_two, star_three, star_four, star_five;
        TextView vet_name_TV, vet_qualification_TV, vet_charges_tv, vet_location_TV;
        LinearLayout view_more_LL;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            vet_image_IV            = itemView.findViewById(R.id.vet_image_IV);
            vet_name_TV             = itemView.findViewById(R.id.vet_name_TV);
            vet_qualification_TV    = itemView.findViewById(R.id.vet_qualification_TV);
            vet_charges_tv          = itemView.findViewById(R.id.vet_charges_tv);
            vet_location_TV         = itemView.findViewById(R.id.vet_location_TV);
            view_more_LL            = itemView.findViewById(R.id.view_more_LL);
            star_one                = itemView.findViewById(R.id.star_one);
            star_two                = itemView.findViewById(R.id.star_two);
            star_three              = itemView.findViewById(R.id.star_three);
            star_four               = itemView.findViewById(R.id.star_four);
            star_five               = itemView.findViewById(R.id.star_five);
            view_more_LL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onProductItemClickListner != null) {
                        onProductItemClickListner.onProductClick(getAdapterPosition());
                    }
                }
            });


        }
    }
}
