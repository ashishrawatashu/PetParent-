package com.cynoteck.petofy.adapter;

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

import com.cynoteck.petofy.R;
import com.cynoteck.petofy.response.getDonationRequestResponse.GetDonationRequestData;
import com.cynoteck.petofy.onClicks.OnAdaptionDonationListClickListener;

import java.util.ArrayList;

public class DonationRequestAdapter extends RecyclerView.Adapter<DonationRequestAdapter.MyViewHolder> {
    Context                                     context;
    ArrayList<GetDonationRequestData>           getDonationRequestData;
    private OnAdaptionDonationListClickListener onAdaptionDonationListClickListener;
    public DonationRequestAdapter(Context context, ArrayList<GetDonationRequestData> getDonationRequestData, OnAdaptionDonationListClickListener onAdaptionDonationListClickListener) {
        this.context                                = context;
        this.getDonationRequestData                 = getDonationRequestData;
        this.onAdaptionDonationListClickListener    = onAdaptionDonationListClickListener;
    }

    @NonNull
    @Override
    public DonationRequestAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adopt_request_list, parent, false);
        DonationRequestAdapter.MyViewHolder vh = new DonationRequestAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull DonationRequestAdapter.MyViewHolder holder, int position) {
        holder.pet_name_TV.setText(getDonationRequestData.get(position).getPetName());
        holder.pet_breed_TV.setText(getDonationRequestData.get(position).getPetBreed());
        if (getDonationRequestData.get(position).getStatus().equals("Adopted")){
            holder.ngo_status_IV.setImageResource(R.drawable.accepted_by_ngo);
            holder.ngo_status_TV.setTextColor(context.getResources().getColor(R.color.whiteColor));
            holder.ngo_status_LL.setBackgroundResource(R.drawable.blue_approve_request_bg);
            holder.third_step.setBackgroundResource(R.color.colorPrimary);
            holder.approve_reject_IV.setImageResource(R.drawable.request_approve);
            holder.approve_reject_LL.setBackgroundResource(R.drawable.blue_approve_request_bg);
            holder.approve_reject_TV.setTextColor(context.getResources().getColor(R.color.whiteColor));
        }else {
            holder.ngo_status_IV.setImageResource(R.drawable.accepted_by_ngo_pending);
            holder.ngo_status_TV.setTextColor(context.getResources().getColor(R.color.gray_1));
            holder.ngo_status_LL.setBackgroundResource(R.drawable.peding_adpotion_status_bg);
            holder.third_step.setBackgroundResource(R.color.gray_5);
            holder.approve_reject_IV.setImageResource(R.drawable.request_approve_pending);
            holder.approve_reject_LL.setBackgroundResource(R.drawable.peding_adpotion_status_bg);
            holder.approve_reject_TV.setTextColor(context.getResources().getColor(R.color.gray_1));
        }

    }

    @Override
    public int getItemCount() {
        return getDonationRequestData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView       pet_profile_IV;
        Button          cancel_request_BT,pet_details_BT;
        ImageView       send_request_demo_IV,verify_request_demo_IV,ngo_status_IV,approve_reject_IV;
        View            first_step, second_step, third_step;
        LinearLayout    send_request_LL,verify_request_LL,ngo_status_LL,approve_reject_LL;
        TextView        send_request_TV,verify_request_TV,ngo_status_TV,approve_reject_TV;
        TextView        pet_name_TV,pet_breed_TV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            pet_profile_IV          = itemView.findViewById(R.id.pet_profile_IV);
            cancel_request_BT       = itemView.findViewById(R.id.cancel_request_BT);
            pet_details_BT          = itemView.findViewById(R.id.pet_details_BT);
            send_request_demo_IV    = itemView.findViewById(R.id.send_request_demo_IV);
            verify_request_demo_IV  = itemView.findViewById(R.id.verify_request_demo_IV);
            ngo_status_IV           = itemView.findViewById(R.id.ngo_status_IV);
            approve_reject_IV       = itemView.findViewById(R.id.approve_reject_IV);
            first_step              = itemView.findViewById(R.id.first_step);
            second_step             = itemView.findViewById(R.id.second_step);
            third_step              = itemView.findViewById(R.id.third_step);
            send_request_LL         = itemView.findViewById(R.id.send_request_LL);
            verify_request_LL       = itemView.findViewById(R.id.verify_request_LL);
            approve_reject_LL       = itemView.findViewById(R.id.approve_reject_LL);
            send_request_TV         = itemView.findViewById(R.id.send_request_TV);
            verify_request_TV       = itemView.findViewById(R.id.verify_request_TV);
            ngo_status_TV           = itemView.findViewById(R.id.ngo_status_TV);
            ngo_status_LL           = itemView.findViewById(R.id.ngo_status_LL);
            pet_breed_TV            = itemView.findViewById(R.id.pet_breed_TV);
            approve_reject_TV       = itemView.findViewById(R.id.approve_reject_TV);
            pet_name_TV             = itemView.findViewById(R.id.pet_name_TV);

            pet_details_BT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onAdaptionDonationListClickListener != null) {
                        onAdaptionDonationListClickListener.onPetDetailsClickListener(getAdapterPosition());
                    }
                }
            });

            cancel_request_BT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onAdaptionDonationListClickListener != null) {
                        onAdaptionDonationListClickListener.onCancelRequestClickListener(getAdapterPosition());
                    }
                }
            });
        }
    }
}