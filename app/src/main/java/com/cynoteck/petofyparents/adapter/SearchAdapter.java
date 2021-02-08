package com.cynoteck.petofyparents.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.response.getPetReportsResponse.getPetListResponse.PetList;
import com.cynoteck.petofyparents.utils.SearchInterface;
import com.cynoteck.petofyparents.utils.ViewDeatilsAndIdCardClick;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {
    Context context;
    ArrayList<PetList> profileList;
    private SearchInterface onProductItemClickListner;
    public SearchAdapter(Context context, ArrayList<PetList> profileList, SearchInterface onProductItemClickListner) {
        this.context = context;
        this.profileList = profileList;
        this.onProductItemClickListner=onProductItemClickListner;
        //filterProfileList = new ArrayList<>(profileList);
    }
    @NonNull
    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list, parent, false);
        SearchAdapter.MyViewHolder vh = new SearchAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.MyViewHolder holder, int position) {
   holder.search_text.setText(profileList.get(position).getPetUniqueId() + ":- "
           + profileList.get(position).getPetName() +
           "(" + profileList.get(position).getPetSex() + ","
           + profileList.get(position).getPetParentName() + ")");
    }

    @Override
    public int getItemCount() {
        return profileList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView search_text;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            search_text = itemView.findViewById(R.id.search_text);




        }
    }
}
