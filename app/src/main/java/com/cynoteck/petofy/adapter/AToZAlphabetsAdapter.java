package com.cynoteck.petofy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cynoteck.petofy.R;
import com.cynoteck.petofy.onClicks.OnAlphabetClickListener;

import java.util.List;

public class AToZAlphabetsAdapter extends RecyclerView.Adapter<AToZAlphabetsAdapter.MyViewHolder> {
    Context context;
    List<String> aToZList;
    private OnAlphabetClickListener onAlphabetClickListener;
    private static int lastClickedPosition = -1;
    boolean isClicked = false;
    private int selectedItem;
    public AToZAlphabetsAdapter(Context context, List<String> aToZList, OnAlphabetClickListener onAlphabetClickListener) {
        this.context = context;
        this.aToZList = aToZList;
        this.onAlphabetClickListener = onAlphabetClickListener;
        selectedItem = 0;
    }

    @NonNull
    @Override
    public AToZAlphabetsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.a_to_z_list, parent, false);
        AToZAlphabetsAdapter.MyViewHolder vh = new AToZAlphabetsAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AToZAlphabetsAdapter.MyViewHolder holder, int position) {
        holder.alphabet_TV.setText(aToZList.get(position));
        if (selectedItem == position&&isClicked==true) {
            holder.alphabet_LL.setBackgroundResource(R.drawable.blue_cirlce_bg);
            holder.alphabet_TV.setTextColor(context.getResources().getColor(R.color.whiteColor));
        }
        else
        {
            holder.alphabet_LL.setBackgroundResource(R.drawable.white_circle_bg);
            holder.alphabet_TV.setTextColor(context.getResources().getColor(R.color.colorPrimary));

        }

    }

    @Override
    public int getItemCount() {
        return aToZList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView alphabet_TV;
        LinearLayout alphabet_LL;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            alphabet_TV = itemView.findViewById(R.id.alphabet_TV);
            alphabet_LL = itemView.findViewById(R.id.alphabet_LL);

            alphabet_TV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isClicked = true;
                    int previousItem = selectedItem;
                    selectedItem = getAdapterPosition();

                    notifyItemChanged(previousItem);
                    notifyItemChanged(getAdapterPosition());
                    if (onAlphabetClickListener != null) {
                        onAlphabetClickListener.onAlphabetClickListener(getAdapterPosition());
                    }

                }
            });
        }
    }
}
