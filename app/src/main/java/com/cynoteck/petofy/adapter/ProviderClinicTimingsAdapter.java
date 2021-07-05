package com.cynoteck.petofy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cynoteck.petofy.R;
import com.cynoteck.petofy.response.getServiceProviderFullDetailsResponse.ServiceProviderDetailOperatingHour;

import java.util.List;

public class ProviderClinicTimingsAdapter extends RecyclerView.Adapter<ProviderClinicTimingsAdapter.MyViewHolder> {
    Context context;
    List<ServiceProviderDetailOperatingHour> serviceProviderDetailOperatingHours;
    public ProviderClinicTimingsAdapter(Context context, List<ServiceProviderDetailOperatingHour> serviceProviderDetailOperatingHours) {
        this.context = context;
        this.serviceProviderDetailOperatingHours = serviceProviderDetailOperatingHours;
    }

    @NonNull
    @Override
    public ProviderClinicTimingsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.clinic_timing_list, parent, false);
        ProviderClinicTimingsAdapter.MyViewHolder vh = new ProviderClinicTimingsAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ProviderClinicTimingsAdapter.MyViewHolder holder, int position) {
        holder.day_TV.setText(serviceProviderDetailOperatingHours.get(position).getDay().getDayName());
        holder.timing_TV.setText(serviceProviderDetailOperatingHours.get(position).getStartTime() +" " +serviceProviderDetailOperatingHours.get(position).getEndTime());

    }

    @Override
    public int getItemCount() {
        return serviceProviderDetailOperatingHours.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView day_TV,timing_TV;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            timing_TV = itemView.findViewById(R.id.timing_TV);
            day_TV = itemView.findViewById(R.id.day_TV);

        }
    }
}
