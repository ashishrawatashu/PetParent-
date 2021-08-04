package com.cynoteck.petofy.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;
import com.cynoteck.petofy.R;
import com.cynoteck.petofy.onClicks.InsurancePlanClick;
import com.cynoteck.petofy.response.getInsuranceMasterResponse.InsurancePlanModel;
import java.util.ArrayList;
import java.util.List;


public class InsurancePlansAdapter extends RecyclerView.Adapter<InsurancePlansAdapter.MyViewHolder> {

    ArrayList<InsurancePlanModel> insurancePlanModels;
    InsurancePlanClick            insurancePlanClick;
    Context context;

    public InsurancePlansAdapter( Context context, ArrayList<InsurancePlanModel> insurancePlanModels, InsurancePlanClick insurancePlanClick) {
        this.insurancePlanModels           = insurancePlanModels;
        this.insurancePlanClick    = insurancePlanClick;
        this.context            = context;
    }

    @NonNull
    @Override
    public InsurancePlansAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_box_layout, parent, false);
        InsurancePlansAdapter.MyViewHolder vh = new InsurancePlansAdapter.MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull InsurancePlansAdapter.MyViewHolder holder, final int position) {
        holder.check_box_id.setText(insurancePlanModels.get(position).getName());
        if (insurancePlanModels.get(position).getId()==1.0){
            holder.check_box_id.setChecked(true);
            holder.check_box_id.setClickable(false);
            holder.check_box_id.setFocusable(false);
            insurancePlanModels.get(position).setStatus(true);
        }
        if (insurancePlanModels.get(position).isStatus()){
            holder.check_box_id.setChecked(true);
        }else {
            holder.check_box_id.setChecked(false);
        }


    }

    @Override
    public int getItemCount() {
        return insurancePlanModels.size();
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
                    if (insurancePlanClick!=null){
                        if (check_box_id.isChecked()){
                            insurancePlanModels.get(getAdapterPosition()).setStatus(true);
                            insurancePlanClick.onInsurancePlanClick(getAdapterPosition(),true);
                        }else {
                            insurancePlanModels.get(getAdapterPosition()).setStatus(false);
                            insurancePlanClick.onInsurancePlanClick(getAdapterPosition(),false);
                        }
                    }
                }
            });


        }
    }
}
