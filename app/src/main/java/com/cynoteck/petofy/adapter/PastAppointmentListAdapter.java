package com.cynoteck.petofy.adapter;

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
import com.cynoteck.petofy.R;
import com.cynoteck.petofy.response.appointmentResponse.AppointmentList;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PastAppointmentListAdapter extends RecyclerView.Adapter<PastAppointmentListAdapter.MyViewHolder> {

    ArrayList<AppointmentList> appointmentList;
    Context context;

    public PastAppointmentListAdapter(Context context, ArrayList<AppointmentList> appointmentList) {
        this.appointmentList = appointmentList;
        this.context = context;

    }

    @NonNull
    @Override
    public PastAppointmentListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointments_list, parent, false);
        PastAppointmentListAdapter.MyViewHolder vh = new PastAppointmentListAdapter.MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull PastAppointmentListAdapter.MyViewHolder holder, final int position) {
        holder.vet_name_TV.setText(appointmentList.get(position).getVetName());
        holder.pet_name_TV.setText(appointmentList.get(position).getPetName());
        Glide.with(context)
                .load(appointmentList.get(position).getVetProfileImage())
                .placeholder(R.drawable.doctor_dummy_image)
                .into(holder.parent_profile_CIV);

        if (appointmentList.get(position).getMeetingUrl().equals("")){
            holder.address_TV.setVisibility(View.VISIBLE);
            holder.dr_address_TV.setVisibility(View.VISIBLE);
            holder.location_IV.setVisibility(View.VISIBLE);

            holder.appointment_type_IV.setImageResource(R.drawable.appointment_type);
            holder.parent_appointment_type_TV.setText("Clinic Visit");
            if (appointmentList.get(position).getIsApproved().equals("true")){
                holder.join_appointment_BT.setVisibility(View.GONE);
                holder.payment_BT.setVisibility(View.GONE);
                holder.cancel_appointment_BT.setVisibility(View.GONE);
                holder.appointment_time_TV.setText(appointmentList.get(position).getAppointmentDate()+" "+appointmentList.get(position).getStartDateString()+"-"+appointmentList.get(position).getEndDateString());
            }else {
                holder.appoint_time_IV.setImageResource(R.drawable.appointment_time);

                holder.appointment_type_IV.setVisibility(View.GONE);
                holder.appointment_type_TV.setVisibility(View.GONE);
                holder.parent_appointment_type_TV.setVisibility(View.GONE);

                holder.location_IV.setVisibility(View.GONE);
                holder.address_TV.setVisibility(View.GONE);
                holder.dr_address_TV.setVisibility(View.GONE);

                holder.join_appointment_BT.setVisibility(View.GONE);
                holder.payment_BT.setVisibility(View.GONE);

                holder.cancel_appointment_BT.setVisibility(View.GONE);
                holder.appointment_time_TV.setText("Not Confirmed yet!");
                holder.appointment_time_TV.setTextColor(context.getResources().getColor(R.color.deactivate_red));
            }

        }else {
            holder.appointment_type_IV.setImageResource(R.drawable.online_appointment);
            holder.parent_appointment_type_TV.setText("Online consultation");

            holder.address_TV.setVisibility(View.GONE);
            holder.dr_address_TV.setVisibility(View.GONE);
            holder.location_IV.setVisibility(View.GONE);
            if (appointmentList.get(position).getIsApproved().equals("true")){
                if (appointmentList.get(position).getPaymentStatus().equals("true")){
                    holder.join_appointment_BT.setVisibility(View.GONE);
                    holder.payment_BT.setVisibility(View.GONE);
                    holder.cancel_appointment_BT.setVisibility(View.GONE);
                    holder.appointment_time_TV.setText(appointmentList.get(position).getAppointmentDate()+" "+appointmentList.get(position).getStartDateString()+"-"+appointmentList.get(position).getEndDateString());
                }else if (appointmentList.get(position).getPaymentStatus().equals("false")){
                    holder.join_appointment_BT.setVisibility(View.GONE);
                    holder.payment_BT.setVisibility(View.GONE);
                    holder.cancel_appointment_BT.setVisibility(View.GONE);
                    holder.appointment_time_TV.setText(appointmentList.get(position).getAppointmentDate()+" "+appointmentList.get(position).getStartDateString()+"-"+appointmentList.get(position).getEndDateString());
                }
            }else {


                holder.appoint_time_IV.setImageResource(R.drawable.appointment_time);

                holder.appointment_type_IV.setVisibility(View.GONE);
                holder.appointment_type_TV.setVisibility(View.GONE);
                holder.parent_appointment_type_TV.setVisibility(View.GONE);

                holder.location_IV.setVisibility(View.GONE);
                holder.address_TV.setVisibility(View.GONE);
                holder.dr_address_TV.setVisibility(View.GONE);

                holder.join_appointment_BT.setVisibility(View.GONE);
                holder.payment_BT.setVisibility(View.GONE);

                holder.cancel_appointment_BT.setVisibility(View.GONE);
                holder.appointment_time_TV.setText("Not Confirmed yet!");
                holder.appointment_time_TV.setTextColor(context.getResources().getColor(R.color.deactivate_red));

            }
        }



    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView parent_profile_CIV;
        TextView vet_name_TV,appointment_time_TV,parent_appointment_type_TV,pet_name_TV,dr_address_TV,appointment_type_TV,address_TV;
        Button join_appointment_BT,cancel_appointment_BT,payment_BT;
        ImageView appointment_type_IV,location_IV,appoint_time_IV;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            parent_profile_CIV = itemView.findViewById(R.id.parent_profile_CIV);
            vet_name_TV = itemView.findViewById(R.id.vet_name_TV);
            appointment_time_TV = itemView.findViewById(R.id.appointment_time_TV);
            parent_appointment_type_TV = itemView.findViewById(R.id.parent_appointment_type_TV);
            pet_name_TV = itemView.findViewById(R.id.pet_name_TV);
            dr_address_TV = itemView.findViewById(R.id.dr_address_TV);
            appointment_type_TV = itemView.findViewById(R.id.appointment_type_TV);
            address_TV = itemView.findViewById(R.id.address_TV);
            join_appointment_BT = itemView.findViewById(R.id.join_appointment_BT);
            cancel_appointment_BT = itemView.findViewById(R.id.cancel_appointment_BT);
            appointment_type_IV = itemView.findViewById(R.id.appointment_type_IV);
            location_IV = itemView.findViewById(R.id.location_IV);
            appoint_time_IV = itemView.findViewById(R.id.appoint_time_IV);
            payment_BT = itemView.findViewById(R.id.payment_BT);

        }
    }
}
