package com.cynoteck.petofyparents.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.response.getPetReportsResponse.GetReportsTypeData;
import com.cynoteck.petofyparents.utils.RegisterRecyclerViewClickListener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VisitTypesAdapter extends RecyclerView.Adapter<VisitTypesAdapter.MyViewHolder> {
    Context context;
    List<GetReportsTypeData> getReportsTypeDataList;
    RegisterRecyclerViewClickListener onProductItemClickListener;

    public VisitTypesAdapter(Context context, List<GetReportsTypeData> getReportsTypeDataList, RegisterRecyclerViewClickListener onProductItemClickListner) {
        this.context = context;
        this.getReportsTypeDataList = getReportsTypeDataList;
        this.onProductItemClickListener = onProductItemClickListner;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.visit_type_list, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.reports_type_TV.setText(getReportsTypeDataList.get(position).getNature());
    }

    @Override
    public int getItemCount() {
        return getReportsTypeDataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView reports_type_TV;
        ImageView view_reports_arrow;
        RelativeLayout clinic_parent_layout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            reports_type_TV =itemView.findViewById(R.id.report_type_TV);
            view_reports_arrow = itemView.findViewById(R.id.view_reports_arrow);
            clinic_parent_layout = itemView.findViewById(R.id.clinic_parent_layout);

            clinic_parent_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onProductItemClickListener!=null){
                        onProductItemClickListener.onProductClick(getAdapterPosition());
                    }
                }
            });

        }
    }
}
