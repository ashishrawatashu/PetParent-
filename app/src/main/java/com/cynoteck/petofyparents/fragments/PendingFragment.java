package com.cynoteck.petofyparents.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.activty.DoYouWantAdoptActivity;
import com.cynoteck.petofyparents.activty.PaymentScreenActivity;
import com.cynoteck.petofyparents.adapter.AppointmentListAdapter;
import com.cynoteck.petofyparents.adapter.DateListAdapter;
import com.cynoteck.petofyparents.adapter.DonationAdopter;
import com.cynoteck.petofyparents.api.ApiClient;
import com.cynoteck.petofyparents.api.ApiInterface;
import com.cynoteck.petofyparents.api.ApiResponse;
import com.cynoteck.petofyparents.api.ApiService;
import com.cynoteck.petofyparents.response.appointmentResponse.AppointmentList;
import com.cynoteck.petofyparents.response.appointmentResponse.GetAppointmentResponse;
import com.cynoteck.petofyparents.response.donationResponse.Datum;
import com.cynoteck.petofyparents.response.donationResponse.DonationResponseList;
import com.cynoteck.petofyparents.utils.AppointmentsClickListner;
import com.cynoteck.petofyparents.utils.Config;
import com.cynoteck.petofyparents.utils.DonationClickListener;
import com.cynoteck.petofyparents.utils.Methods;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class PendingFragment extends Fragment implements ApiResponse, DonationClickListener {

    View view;
    RecyclerView donation_pending_RV;
    ShimmerFrameLayout shimmer_view_container;
    Methods methods;
    DonationAdopter donationAdopter;
    List<Datum> data;
    TextView no_record_fpond;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_pending, container, false);
        initialize();
        return view;
    }

    public void initialize()
    {
        methods=new Methods(getActivity());
        donation_pending_RV=view.findViewById(R.id.donation_pending_RV);
        shimmer_view_container=view.findViewById(R.id.shimmer_view_container);
        no_record_fpond=view.findViewById(R.id.no_record_fpond);

        if(methods.isInternetOn())
        {
            getPendingList();
        }
        else
        {
            methods.isInternetOn();
        }

    }


    private void getPendingList()
    {
        shimmer_view_container.startShimmer();
        ApiService<DonationResponseList> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().viewPetVaccinationPending(Config.token), "get-donation-request-list/1");
    }

    @Override
    public void onResponse(Response arg0, String key) {
        switch (key) {
            case "get-donation-request-list/1":
            try {
                DonationResponseList donationResponseList = (DonationResponseList) arg0.body();
                Log.d("PendingDonation",""+donationResponseList.toString());
                if(donationResponseList.getData().size()>0)
                {
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    donation_pending_RV.setLayoutManager(linearLayoutManager);
                    donationAdopter  = new DonationAdopter(getContext(),donationResponseList.getData(),this);
                    donation_pending_RV.setAdapter(donationAdopter);
                    donationAdopter.notifyDataSetChanged();
                    data = donationResponseList.getData();
                    shimmer_view_container.stopShimmer();
                    shimmer_view_container.setVisibility(View.GONE);
                    no_record_fpond.setVisibility(View.GONE);
                }
                else
                {
                    shimmer_view_container.setVisibility(View.GONE);
                    donation_pending_RV.setVisibility(View.GONE);
                    no_record_fpond.setVisibility(View.VISIBLE);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            break;
        }

    }

    @Override
    public void onError(Throwable t, String key) {

    }

    @Override
    public void onItemClickView(int position) {

    }

    @Override
    public void onItemClickEdit(int position) {
        Intent intent=new Intent(getActivity(), DoYouWantAdoptActivity.class);
        intent.putExtra("id",data.get(position).getId());
        intent.putExtra("petCategory",data.get(position).getCategory());
        intent.putExtra("petSex",data.get(position).getPetSex());
        intent.putExtra("petAge",data.get(position).getPetAge());
        intent.putExtra("petSize",data.get(position).getPetSize());
        intent.putExtra("petColor",data.get(position).getPetColor());
        intent.putExtra("petBreed",data.get(position).getPetBreed());
        intent.putExtra("petName",data.get(position).getPetName());
        intent.putExtra("petDescription",data.get(position).getDescription());
        intent.putExtra("petAddress",data.get(position).getAddress());
        intent.putExtra("state",data.get(position).getState());
        intent.putExtra("city",data.get(position).getCity());
        intent.putExtra("donarName",data.get(position).getDonarName());
        intent.putExtra("phoneNumber",data.get(position).getPhoneNumber());
        intent.putExtra("encryptedId",data.get(position).getEncryptedId());
        intent.putExtra("imageOne",data.get(position).getFirstServiceImageUrl());
        intent.putExtra("imageTwo",data.get(position).getSecondServiceImageUrl());
        intent.putExtra("imageThree",data.get(position).getThirdServiceImageUrl());
        intent.putExtra("imageFour",data.get(position).getFourthServiceImageUrl());
        intent.putExtra("imageFive",data.get(position).getFifthServiceImageUrl());
        intent.putExtra("type","update");
        startActivity(intent);
    }
}