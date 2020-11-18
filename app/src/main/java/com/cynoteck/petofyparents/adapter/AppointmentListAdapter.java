package com.cynoteck.petofyparents.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.response.appointmentResponse.AppointmentList;
import com.cynoteck.petofyparents.utils.AppointmentsClickListner;

import java.util.ArrayList;

public class AppointmentListAdapter extends RecyclerView.Adapter<com.cynoteck.petofyparents.adapter.AppointmentListAdapter.MyViewHolder> {

    ArrayList<AppointmentList> appointmentList;
    AppointmentsClickListner appointmentsClickListner;
    Context context;

    public AppointmentListAdapter(Context context, ArrayList<AppointmentList> appointmentList, AppointmentsClickListner appointmentsClickListner) {
        this.appointmentList = appointmentList;
        this.appointmentsClickListner = appointmentsClickListner;
        this.context = context;

    }

    @NonNull
    @Override
    public com.cynoteck.petofyparents.adapter.AppointmentListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list, parent, false);
        com.cynoteck.petofyparents.adapter.AppointmentListAdapter.MyViewHolder vh = new com.cynoteck.petofyparents.adapter.AppointmentListAdapter.MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull com.cynoteck.petofyparents.adapter.AppointmentListAdapter.MyViewHolder holder, final int position)
    {

/*        if (appointmentList.get(position).getIsApproved().equals("true")&&appointmentList.get(position).getPaymentStatus().equals("true")){
            holder.join_BT.setVisibility(View.VISIBLE);
            holder.payment_ststus_TV.setVisibility(View.GONE);
        }else if (appointmentList.get(position).getIsApproved().equals("true")&&appointmentList.get(position).getPaymentStatus().equals("false")){
            holder.join_BT.setVisibility(View.GONE);
            holder.payment_ststus_TV.setVisibility(View.GONE);
            holder.payment_BT.setVisibility(View.VISIBLE);

        }else {
            holder.payment_BT.setVisibility(View.VISIBLE);
            holder.join_BT.setVisibility(View.GONE);
            holder.payment_ststus_TV.setVisibility(View.GONE);
        }*/

//        if (appointmentList.get(position).getIsApproved().equals("true")){
//            holder.join_BT.setVisibility(View.GONE);
//            holder.payment_ststus_TV.setVisibility(View.GONE);
//            if (appointmentList.get(position).getPaymentStatus().equals("true")){
//                holder.join_BT.setVisibility(View.VISIBLE);
//                holder.payment_ststus_TV.setVisibility(View.GONE);
//            }else {
//                holder.join_BT.setVisibility(View.GONE);
//                holder.payment_ststus_TV.setVisibility(View.VISIBLE);
//            }
//        }else {
//            holder.payment_BT.setVisibility(View.VISIBLE);
//            holder.join_BT.setVisibility(View.GONE);
//            holder.payment_ststus_TV.setVisibility(View.GONE);
//        }

        if (appointmentList.get(position).getIsApproved().equals("true")){
            if (appointmentList.get(position).getPaymentStatus().equals("true")){
                holder.join_BT.setVisibility(View.VISIBLE);
                holder.payment_BT.setVisibility(View.GONE);
                holder.payment_ststus_TV.setVisibility(View.GONE);
                holder.cancel_BT.setVisibility(View.GONE);
            }else if (appointmentList.get(position).getPaymentStatus().equals("false")){
                holder.join_BT.setVisibility(View.GONE);
                holder.payment_BT.setVisibility(View.VISIBLE);
                holder.payment_ststus_TV.setVisibility(View.GONE);
                holder.cancel_BT.setVisibility(View.GONE);
            }
        }else {
            holder.join_BT.setVisibility(View.GONE);
            holder.payment_BT.setVisibility(View.GONE);
            holder.payment_ststus_TV.setVisibility(View.GONE);
            holder.cancel_BT.setVisibility(View.VISIBLE);
        }


        holder.appoint_subject_TV.setText(appointmentList.get(position).getSubject());
        holder.timing_TV.setText(appointmentList.get(position).getStartDateString()+"-"+appointmentList.get(position).getEndDateString());
        holder.pet_parent_TV.setText(appointmentList.get(position).getPetParentName());

    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView appoint_subject_TV,timing_TV,pet_parent_TV,payment_ststus_TV;
        Button payment_BT,join_BT,cancel_BT;
        CardView appointment_CV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            appoint_subject_TV = itemView.findViewById(R.id.appoint_subject_TV);
            timing_TV = itemView.findViewById(R.id.timing_TV);
            pet_parent_TV = itemView.findViewById(R.id.pet_parent_TV);
            payment_BT = itemView.findViewById(R.id.payment_BT);
            join_BT = itemView.findViewById(R.id.join_BT);
            appointment_CV=itemView.findViewById(R.id.appointment_CV);
            payment_ststus_TV=itemView.findViewById(R.id.payment_ststus_TV);
            cancel_BT = itemView.findViewById(R.id.cancel_BT);

            appointment_CV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (appointmentsClickListner!=null){
                        appointmentsClickListner.onItemClick(getAdapterPosition(),appointmentList);
                    }
                }
            });

            join_BT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (appointmentsClickListner!=null){
                        appointmentsClickListner.onJoinClick(getAdapterPosition(),appointmentList,join_BT);
                    }
                }
            });

            payment_BT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (appointmentsClickListner!=null){
                        appointmentsClickListner.onApproveClick(getAdapterPosition(),appointmentList,payment_BT);
                    }
                }
            });

            cancel_BT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (appointmentsClickListner!=null){
                        appointmentsClickListner.onCancelClick(getAdapterPosition(),appointmentList,cancel_BT);
                    }
                }

            });

        }
    }
}
