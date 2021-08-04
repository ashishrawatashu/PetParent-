package com.cynoteck.petofy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import com.cynoteck.petofy.R;
import com.cynoteck.petofy.onClicks.DiseasesListClickListener;
import com.cynoteck.petofy.onClicks.InsurancePlanClick;
import com.cynoteck.petofy.response.getInsuranceMasterResponse.DiseasesListModel;
import com.cynoteck.petofy.response.getInsuranceMasterResponse.InsurancePlanModel;

import java.util.ArrayList;
import java.util.List;

public class DiseasesListAdapter extends RecyclerView.Adapter<DiseasesListAdapter.MyViewHolder> {

    ArrayList<DiseasesListModel> diseasesListModels;
    DiseasesListClickListener   onDiseasesListClickListener;
    Context context;

    public DiseasesListAdapter( Context context, ArrayList<DiseasesListModel> diseasesListModels, DiseasesListClickListener onDiseasesListClickListener) {
        this.diseasesListModels            = diseasesListModels;
        this.onDiseasesListClickListener    = onDiseasesListClickListener;
        this.context                        = context;
    }

    @NonNull
    @Override
    public DiseasesListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_box_layout, parent, false);
        DiseasesListAdapter.MyViewHolder vh = new DiseasesListAdapter.MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull DiseasesListAdapter.MyViewHolder holder, final int position) {
        holder.check_box_id.setText(diseasesListModels.get(position).getName());

        if (diseasesListModels.get(position).isStatus()){
            holder.check_box_id.setChecked(true);
        }else {
            holder.check_box_id.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return diseasesListModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        AppCompatCheckBox check_box_id;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            check_box_id   = itemView.findViewById(R.id.check_box_id);
            check_box_id.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onDiseasesListClickListener!=null){
                        if (check_box_id.isChecked()){
                            diseasesListModels.get(getAdapterPosition()).setStatus(true);
                            onDiseasesListClickListener.onDiseasesListClickListener(getAdapterPosition(),true,diseasesListModels.get(getAdapterPosition()).getId());
                        }else {
                            diseasesListModels.get(getAdapterPosition()).setStatus(false);
                            onDiseasesListClickListener.onDiseasesListClickListener(getAdapterPosition(),false,diseasesListModels.get(getAdapterPosition()).getId());
                        }
                    }
                }
            });


        }
    }
}
