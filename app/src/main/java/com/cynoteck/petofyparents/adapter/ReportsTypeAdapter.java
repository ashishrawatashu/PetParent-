package com.cynoteck.petofyparents.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.response.getPetReportsResponse.getPetClinicVisitsListsResponse.PetClinicVisitList;
import com.cynoteck.petofyparents.utils.ViewAndUpdateClickListener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class ReportsTypeAdapter extends RecyclerView.Adapter<ReportsTypeAdapter.MyViewHolder> {

    Context context;
    List<PetClinicVisitList> petClinicVisitLists;
    ViewAndUpdateClickListener onProductItemClickListner;

    public ReportsTypeAdapter(Context context, List<PetClinicVisitList> petClinicVisitLists, ViewAndUpdateClickListener onProductItemClickListner) {
        this.context = context;
        this.petClinicVisitLists = petClinicVisitLists;
        this.onProductItemClickListner = onProductItemClickListner;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reports_type_list, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.vet_name_TV.setText(petClinicVisitLists.get(position).getVeterinarian());
        holder.visit_date_TV.setText(petClinicVisitLists.get(position).getVisitDate());
        if (petClinicVisitLists.get(position).getFollowUpDate().equals("")) {
            holder.follow_date_LL.setVisibility(View.GONE);
        } else {
            holder.follow_date_LL.setVisibility(View.VISIBLE);
            holder.followUp_date_TV.setText(petClinicVisitLists.get(position).getFollowUpDate());
        }
        if (petClinicVisitLists.get(position).getDescription().equals("")) {
            holder.reson_of_visit_LL.setVisibility(View.GONE);
        } else {
            holder.reson_of_visit_LL.setVisibility(View.VISIBLE);
            holder.reson_of_visit_TV.setText(petClinicVisitLists.get(position).getDescription());
        }



    }

    @Override
    public int getItemCount() {
        return petClinicVisitLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView vet_name_TV, visit_date_TV, reson_of_visit_TV, followUp_date_TV, view_TV;
        LinearLayout follow_date_LL, reson_of_visit_LL;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            vet_name_TV = itemView.findViewById(R.id.vet_name_TV);
            visit_date_TV = itemView.findViewById(R.id.visit_date_TV);
            reson_of_visit_TV = itemView.findViewById(R.id.reson_of_visit_TV);
            followUp_date_TV = itemView.findViewById(R.id.followUp_date_TV);
            view_TV = itemView.findViewById(R.id.view_TV);
            follow_date_LL = itemView.findViewById(R.id.follow_date_LL);
            reson_of_visit_LL = itemView.findViewById(R.id.reson_of_visit_LL);

            view_TV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onProductItemClickListner != null) {
                        onProductItemClickListner.onViewClick(getAdapterPosition());
                    }
                }
            });


        }
    }
}
