package com.cynoteck.petofy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cynoteck.petofy.R;
import com.cynoteck.petofy.response.getServiceProviderFullDetailsResponse.ProviderRatingList;

import java.util.ArrayList;

public class ProviderReviewsAdapter extends RecyclerView.Adapter<ProviderReviewsAdapter.MyViewHolder> {
    Context context;
    ArrayList<ProviderRatingList> providerRatingLists;
    public ProviderReviewsAdapter(Context context, ArrayList<ProviderRatingList> providerRatingLists) {
        this.context = context;
        this.providerRatingLists = providerRatingLists;
    }

    @NonNull
    @Override
    public ProviderReviewsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews_list, parent, false);
        ProviderReviewsAdapter.MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ProviderReviewsAdapter.MyViewHolder holder, int position){
        if (providerRatingLists.get(position).getFeedback().equals("")){

        }else {
            holder.comment_TV.setText(providerRatingLists.get(position).getFeedback());
            holder.user_name_TV.setText(providerRatingLists.get(position).getUserName());
            holder.user_first_letter_TV.setText(providerRatingLists.get(position).getUserName().substring(0,1));
            Glide.with(context)
                    .load(providerRatingLists.get(position).getProfileImage())
                    .into(holder.user_profile_IV);
            if (providerRatingLists.get(position).getRating()==1){
                holder.star_one.setImageResource(R.drawable.star_with_rate);
                holder.star_two.setImageResource(R.drawable.empty_star_icon);
                holder.star_three.setImageResource(R.drawable.empty_star_icon);
                holder.star_four.setImageResource(R.drawable.empty_star_icon);
                holder.star_five.setImageResource(R.drawable.empty_star_icon);

            }else if (providerRatingLists.get(position).getRating()==2){
                holder.star_one.setImageResource(R.drawable.star_with_rate);
                holder.star_two.setImageResource(R.drawable.star_with_rate);
                holder.star_three.setImageResource(R.drawable.empty_star_icon);
                holder.star_four.setImageResource(R.drawable.empty_star_icon);
                holder.star_five.setImageResource(R.drawable.empty_star_icon);
            }else if (providerRatingLists.get(position).getRating()==3){
                holder.star_one.setImageResource(R.drawable.star_with_rate);
                holder.star_two.setImageResource(R.drawable.star_with_rate);
                holder.star_three.setImageResource(R.drawable.star_with_rate);
                holder.star_four.setImageResource(R.drawable.empty_star_icon);
                holder.star_five.setImageResource(R.drawable.empty_star_icon);
            }else if (providerRatingLists.get(position).getRating()==4){
                holder.star_one.setImageResource(R.drawable.star_with_rate);
                holder.star_two.setImageResource(R.drawable.star_with_rate);
                holder.star_three.setImageResource(R.drawable.star_with_rate);
                holder.star_four.setImageResource(R.drawable.star_with_rate);
                holder.star_five.setImageResource(R.drawable.empty_star_icon);
            }else if (providerRatingLists.get(position).getRating()==5){
                holder.star_one.setImageResource(R.drawable.star_with_rate);
                holder.star_two.setImageResource(R.drawable.star_with_rate);
                holder.star_three.setImageResource(R.drawable.star_with_rate);
                holder.star_four.setImageResource(R.drawable.star_with_rate);
                holder.star_five.setImageResource(R.drawable.star_with_rate);
            }
        }


    }

    @Override
    public int getItemCount() {
        return providerRatingLists.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView user_pic_CV;
        ImageView user_profile_IV,star_one,star_two,star_three,star_four,star_five;
        TextView user_name_TV, comment_TV,user_first_letter_TV;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            user_pic_CV = itemView.findViewById(R.id.user_pic_CV);
            user_profile_IV = itemView.findViewById(R.id.user_profile_IV);
            star_one = itemView.findViewById(R.id.star_one);
            star_two = itemView.findViewById(R.id.star_two);
            star_three = itemView.findViewById(R.id.star_three);
            star_four = itemView.findViewById(R.id.star_four);
            star_five = itemView.findViewById(R.id.star_five);
            user_name_TV = itemView.findViewById(R.id.user_name_TV);
            comment_TV = itemView.findViewById(R.id.comment_TV);
            user_first_letter_TV = itemView.findViewById(R.id.user_first_letter_TV);


        }
    }
}