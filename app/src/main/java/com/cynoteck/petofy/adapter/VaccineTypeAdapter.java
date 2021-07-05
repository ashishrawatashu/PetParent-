package com.cynoteck.petofy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cynoteck.petofy.R;
import com.cynoteck.petofy.response.getVaccinationResponse.GetVaccineResponseModel;
import com.cynoteck.petofy.onClicks.ImmunizationOnclickListener;

import java.util.ArrayList;

public class VaccineTypeAdapter extends RecyclerView.Adapter<VaccineTypeAdapter.MyViewHolder> {
    Context context;
    ArrayList<GetVaccineResponseModel> getVaccineResponseModels;
    private ImmunizationOnclickListener immunizationOnclickListener;

    public VaccineTypeAdapter(Context context, ArrayList<GetVaccineResponseModel> getVaccineResponseModels, ImmunizationOnclickListener immunizationOnclickListener) {
        this.context = context;
        this.getVaccineResponseModels = getVaccineResponseModels;
        this.immunizationOnclickListener = immunizationOnclickListener;
    }
    @NonNull
    @Override
    public VaccineTypeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.vaccine_type_list, parent, false);
        VaccineTypeAdapter.MyViewHolder vh = new VaccineTypeAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final VaccineTypeAdapter.MyViewHolder holder, int position) {
     String min_age=getVaccineResponseModels.get(position).getVaccinationSchedule().getMinimunAge().substring(0,getVaccineResponseModels.get(position).getVaccinationSchedule().getMinimunAge().length()-3);
     String max_age=getVaccineResponseModels.get(position).getVaccinationSchedule().getMaximunAge().substring(0,getVaccineResponseModels.get(position).getVaccinationSchedule().getMaximunAge().length()-3);

     String boosterOne=getVaccineResponseModels.get(position).getVaccinationSchedule().getBoosterOne();
     String boosterTwo=getVaccineResponseModels.get(position).getVaccinationSchedule().getBoosterTwo();

     if(boosterOne.equals("true"))
     {
         holder.booster_one.setVisibility(View.VISIBLE);
     }
     if(boosterTwo.equals("true"))
     {
         holder.booster_two.setVisibility(View.VISIBLE);
     }


     holder.age_group.setText(min_age+" - "+max_age+" Days");
     holder.vaccine_name.setText(getVaccineResponseModels.get(position).getVaccinationSchedule().getPrimaryVaccine());
     holder.down_IV.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             holder.up_IV.setVisibility(View.VISIBLE);
             holder.down_IV.setVisibility(View.GONE);
             holder.vaccine_type_expand_LL.setVisibility(View.VISIBLE);

         }
     });

     holder.up_IV.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             holder.up_IV.setVisibility(View.GONE);
             holder.down_IV.setVisibility(View.VISIBLE);
             holder.vaccine_type_expand_LL.setVisibility(View.GONE);
         }
     });

    }

    @Override
    public int getItemCount() {
        return getVaccineResponseModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView age_group,vaccine_name;
        CheckBox Primary,booster_one,booster_two;
        LinearLayout vaccine_type_expand_LL;
        ImageView up_IV, down_IV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            age_group =itemView.findViewById(R.id.age_group);
            vaccine_name = itemView.findViewById(R.id.vaccine_name);
            Primary = itemView.findViewById(R.id.Primary);
            booster_one = itemView.findViewById(R.id.booster_one);
            booster_two = itemView.findViewById(R.id.booster_two);
            down_IV = itemView.findViewById(R.id.down_IV);
            up_IV = itemView.findViewById(R.id.up_IV);
            vaccine_type_expand_LL = itemView.findViewById(R.id.vaccine_type_expand_LL);

            Primary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    immunizationOnclickListener.onItemClickImmunizationPrimary(getAdapterPosition());
                }
            });

            booster_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    immunizationOnclickListener.onItemClickImmunizationBoosterOne(getAdapterPosition());
                }
            });

            booster_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    immunizationOnclickListener.onItemClickImmunizationBoosterTwo(getAdapterPosition());
                }
            });

        }
    }
}
