package com.cynoteck.petofyparents.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.response.getPetHospitalizationResponse.getHospitalizationListResponse.PetHospitalizationsList;
import com.cynoteck.petofyparents.utils.ViewAndUpdateClickListener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HospitalizationReportsAdapter extends RecyclerView.Adapter<HospitalizationReportsAdapter.MyViewHolder> {

    Context context;
    List<PetHospitalizationsList> petHospitalizationsLists;
    ViewAndUpdateClickListener onProductItemClickListner;

    public HospitalizationReportsAdapter(Context context, List<PetHospitalizationsList> petClinicVisitLists, ViewAndUpdateClickListener onProductItemClickListner) {
        this.context = context;
        this.petHospitalizationsLists = petClinicVisitLists;
        this.onProductItemClickListner = onProductItemClickListner;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hospitalization_reports_list, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.requesting_Vet_TV.setText(petHospitalizationsLists.get(position).getRequestingVeterinarian());
        holder.vet_phone_TV.setText(petHospitalizationsLists.get(position).getVeterinarianPhone());
        holder.hospital_type_TV.setText(petHospitalizationsLists.get(position).getHospitalizationType().getHospitalization());
        holder.hospital_name_TV.setText(petHospitalizationsLists.get(position).getHospitalName());
        holder.admission_date_TV.setText(petHospitalizationsLists.get(position).getAdmissionDate());

    }

    @Override
    public int getItemCount() {
        return petHospitalizationsLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView requesting_Vet_TV,vet_phone_TV,hospital_type_TV,hospital_name_TV,admission_date_TV,view_TV;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            requesting_Vet_TV = itemView.findViewById(R.id.requesting_Vet_TV);
            vet_phone_TV = itemView.findViewById(R.id.vet_phone_TV);
            hospital_type_TV = itemView.findViewById(R.id.hospital_type_TV);
            hospital_name_TV = itemView.findViewById(R.id.hospital_name_TV);
            admission_date_TV = itemView.findViewById(R.id.admission_date_TV);
            view_TV=itemView.findViewById(R.id.view_TV);

            view_TV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onProductItemClickListner!=null){
                        onProductItemClickListner.onViewHospitalizationClick(getAdapterPosition());
                    }
                }
            });




        }
    }
}
