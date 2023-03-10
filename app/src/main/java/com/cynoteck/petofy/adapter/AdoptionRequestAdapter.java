package com.cynoteck.petofy.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cynoteck.petofy.R;
import com.cynoteck.petofy.response.getAdoptionRequestListResponse.GetAdoptionRequestListData;
import com.cynoteck.petofy.onClicks.OnAdaptionDonationListClickListener;
import com.cynoteck.petofy.utils.Methods;
import com.cynoteck.petofy.utils.PetParentSingleton;

import java.util.ArrayList;

public class AdoptionRequestAdapter  extends RecyclerView.Adapter<AdoptionRequestAdapter.MyViewHolder> {
    Context                                             context;
    ArrayList<GetAdoptionRequestListData>               getAdoptionRequestListData;
    private final OnAdaptionDonationListClickListener   onAdaptionDonationListClickListener;
    Methods methods;
    public AdoptionRequestAdapter(Context context, ArrayList<GetAdoptionRequestListData> getAdoptionRequestListData, OnAdaptionDonationListClickListener onAdaptionDonationListClickListener) {
        this.context                                = context;
        this.getAdoptionRequestListData             = getAdoptionRequestListData;
        this.onAdaptionDonationListClickListener    = onAdaptionDonationListClickListener;
    }

    @NonNull
    @Override
    public AdoptionRequestAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adopt_request_list, parent, false);
        AdoptionRequestAdapter.MyViewHolder vh = new AdoptionRequestAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdoptionRequestAdapter.MyViewHolder holder, int position) {
        methods = new Methods(context.getApplicationContext());
        holder.pet_name_TV.setText(getAdoptionRequestListData.get(position).getPet().getPetName());
        holder.pet_breed_TV.setText(getAdoptionRequestListData.get(position).getPet().getPetBreed());
//        Log.d("Adoption_response",methods.getRequestJson(getAdoptionRequestListData));
//        Log.d("Adoption_response",methods.getRequestJson(getAdoptionRequestListData.get(position).getPet().getPetImageList()));
//        Log.d("SIZE_PET_IAMGE",""+getAdoptionRequestListData.get(position).getPetImageList().size());

        if (!getAdoptionRequestListData.get(position).getPet().getPetImageList().isEmpty()){
            Glide.with(context).load(getAdoptionRequestListData.get(position).getPet().getPetImageList().get(0).getPetImageUrl()).into(holder.pet_profile_IV);
        }else {
            holder.pet_profile_IV.setImageResource(R.drawable.empty_pet_image);
        }

        if (getAdoptionRequestListData.get(position).getRequestCurrentStatus().equals("Adopted")){
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
        return getAdoptionRequestListData.size();
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

            pet_profile_IV              = itemView.findViewById(R.id.pet_profile_IV);
            cancel_request_BT           = itemView.findViewById(R.id.cancel_request_BT);
            pet_details_BT              = itemView.findViewById(R.id.pet_details_BT);
            send_request_demo_IV        = itemView.findViewById(R.id.send_request_demo_IV);
            verify_request_demo_IV      = itemView.findViewById(R.id.verify_request_demo_IV);
            ngo_status_IV               = itemView.findViewById(R.id.ngo_status_IV);
            approve_reject_IV           = itemView.findViewById(R.id.approve_reject_IV);
            first_step                  = itemView.findViewById(R.id.first_step);
            second_step                 = itemView.findViewById(R.id.second_step);
            third_step                  = itemView.findViewById(R.id.third_step);
            send_request_LL             = itemView.findViewById(R.id.send_request_LL);
            verify_request_LL           = itemView.findViewById(R.id.verify_request_LL);
            approve_reject_LL           = itemView.findViewById(R.id.approve_reject_LL);
            send_request_TV             = itemView.findViewById(R.id.send_request_TV);
            verify_request_TV           = itemView.findViewById(R.id.verify_request_TV);
            ngo_status_TV               = itemView.findViewById(R.id.ngo_status_TV);
            ngo_status_LL               = itemView.findViewById(R.id.ngo_status_LL);
            pet_breed_TV                = itemView.findViewById(R.id.pet_breed_TV);
            approve_reject_TV           = itemView.findViewById(R.id.approve_reject_TV);
            pet_name_TV                 = itemView.findViewById(R.id.pet_name_TV);

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
