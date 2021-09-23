package com.cynoteck.petofy.activity;

import static com.cynoteck.petofy.activity.SelectPetForDonateAndInsuranceActivity.donation_RL;
import static com.cynoteck.petofy.activity.SelectPetForDonateAndInsuranceActivity.donation_cart_icon_IV;
import static com.cynoteck.petofy.activity.SelectPetForDonateAndInsuranceActivity.total_donation_RL;
import static com.cynoteck.petofy.activity.SelectPetForDonateAndInsuranceActivity.total_donation_request_TV;
import static com.cynoteck.petofy.fragments.ProfileFragment.petListHorizontalAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cynoteck.petofy.response.getDonationRequestResponse.GetDonationRequestData;
import com.cynoteck.petofy.response.getDonationRequestResponse.GetDonationRequestResponse;
import com.cynoteck.petofy.utils.PetParentSingleton;
import com.cynoteck.petofy.R;
import com.cynoteck.petofy.adapter.DonationRequestAdapter;
import com.cynoteck.petofy.api.ApiClient;
import com.cynoteck.petofy.api.ApiResponse;
import com.cynoteck.petofy.api.ApiService;
import com.cynoteck.petofy.utils.Config;
import com.cynoteck.petofy.onClicks.OnAdaptionDonationListClickListener;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.JsonObject;

import retrofit2.Response;

public class DonationRequestActivity extends AppCompatActivity implements ApiResponse ,OnAdaptionDonationListClickListener , View.OnClickListener {
    RecyclerView            donation_request_RV;
    MaterialCardView        back_arrow_CV;
    DonationRequestAdapter  donationRequestAdapter;
    String                  type;
    int                     donationListPosition;
    SwipeRefreshLayout      donation_list_SWL;
    boolean                 fromSwipe = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_request);


        donation_request_RV     = findViewById(R.id.donation_request_RV);
        back_arrow_CV           = findViewById(R.id.back_arrow_CV);
        donation_list_SWL       = findViewById(R.id.donation_list_SWL);

        donation_list_SWL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fromSwipe = true;
                PetParentSingleton.getInstance().getGetDonationRequestListData().clear();
                donationRequestAdapter.notifyDataSetChanged();
                getDonationRequestData();

            }
        });
        back_arrow_CV.setOnClickListener(this);
        donation_request_RV.setLayoutManager(new LinearLayoutManager(this));
        donationRequestAdapter = new DonationRequestAdapter(this, PetParentSingleton.getInstance().getGetDonationRequestListData(),this);
        donation_request_RV.setAdapter(donationRequestAdapter);
    }

    private void getDonationRequestData() {
        ApiService<GetDonationRequestResponse> service1 = new ApiService<>();
        service1.get(this, ApiClient.getApiInterface().getDonationRequest(Config.token,"social-service/get-donation-request-list/0"), "DonationRequest");

    }

    @Override
    public void onCancelRequestClickListener(int position) {
        donationListPosition = position;
        String donationId = String.valueOf(PetParentSingleton.getInstance().getGetDonationRequestListData().get(position).getId());
        String realId = donationId.substring(0,donationId.length()-2);

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("");
        alertDialog.setMessage("Do you want to cancel this request?");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteDonationRequest(realId);
                        dialog.dismiss();

                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();

                    }

                });
        alertDialog.show();
    }

    private void deleteDonationRequest(String realId) {
        ApiService<JsonObject> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().deleteDonationRequest(Config.token, "social-service/cancel-donation-request/"+realId), "CancelRequest");
        //Log.d"DIOLOG====>",  "social-service/cancel-adoption-request/"+realId);
    }

    @Override
    public void onPetDetailsClickListener(int position) {
        Intent intent = new Intent(this, AdoptionPetDetailsActivity.class);
        intent.putExtra("image", String.valueOf(PetParentSingleton.getInstance().getGetDonationRequestListData().get(position)));
        intent.putExtra("pet_id", PetParentSingleton.getInstance().getGetDonationRequestListData().get(position).getId());
        intent.putExtra("pet_name", PetParentSingleton.getInstance().getGetDonationRequestListData().get(position).getPetName());
        intent.putExtra("pet_gender", PetParentSingleton.getInstance().getGetDonationRequestListData().get(position).getCategory());
        intent.putExtra("pet_age", PetParentSingleton.getInstance().getGetDonationRequestListData().get(position).getPetAge());
        intent.putExtra("pet_breed", PetParentSingleton.getInstance().getGetDonationRequestListData().get(position).getPetBreed());
        intent.putExtra("pet_color", PetParentSingleton.getInstance().getGetDonationRequestListData().get(position).getPetColor());
        intent.putExtra("pet_size", PetParentSingleton.getInstance().getGetDonationRequestListData().get(position).getPetSize());
        intent.putExtra("donar_name", PetParentSingleton.getInstance().getGetDonationRequestListData().get(position).getDonarName());
        intent.putExtra("donar_phone", String.valueOf(PetParentSingleton.getInstance().getGetDonationRequestListData().get(position).getPhoneNumber()));
        intent.putExtra("donar_mail", "");
        intent.putExtra("donar_address", PetParentSingleton.getInstance().getGetDonationRequestListData().get(position).getAddress());
        intent.putExtra("from", "AdoptionRequestActivity");

        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrow_CV:
                onBackPressed();
                break;

        }
    }

    @Override
    public void onResponse(Response arg0, String key) {
        switch (key){
            case "DonationRequest":
                try {
                    donation_list_SWL.setRefreshing(false);

                    PetParentSingleton.getInstance().getGetDonationRequestListData().clear();
                    GetDonationRequestResponse getDonationRequestResponse = (GetDonationRequestResponse) arg0.body();
                    int responseCode = Integer.parseInt(getDonationRequestResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        if (getDonationRequestResponse.getData().size() > 0) {
                            for (int i = 0; i < getDonationRequestResponse.getData().size(); i++) {
                                GetDonationRequestData getDonationRequestData = new GetDonationRequestData();
                                getDonationRequestData.setId(getDonationRequestResponse.getData().get(i).getId());
                                getDonationRequestData.setUserId(getDonationRequestResponse.getData().get(i).getUserId());
                                getDonationRequestData.setRequestDate(getDonationRequestResponse.getData().get(i).getRequestDate());
                                getDonationRequestData.setStatus(getDonationRequestResponse.getData().get(i).getStatus());
                                getDonationRequestData.setRequestUpdateDate(getDonationRequestResponse.getData().get(i).getRequestUpdateDate());
                                getDonationRequestData.setPetName(getDonationRequestResponse.getData().get(i).getPetName());
                                getDonationRequestData.setPetBreed(getDonationRequestResponse.getData().get(i).getPetBreed());

                                PetParentSingleton.getInstance().getGetDonationRequestListData().add(getDonationRequestData);
                            }

                            if (PetParentSingleton.getInstance().getGetDonationRequestListData().isEmpty()){
                                total_donation_RL.setEnabled(false);
                                donation_cart_icon_IV.setImageResource(R.drawable.cart_inactive);
                                donation_RL.setVisibility(View.GONE);
                            }else {
                                total_donation_RL.setEnabled(true);
                                total_donation_request_TV.setText(String.valueOf(PetParentSingleton.getInstance().getGetDonationRequestListData().size()));
                                donation_cart_icon_IV.setImageResource(R.drawable.cart_icon);
                                donation_RL.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

                break;

            case "CancelRequest":
                try {
                    JsonObject adoptionResponse = (JsonObject) arg0.body();
//                    Log.d("Cancel", adoptionResponse.toString());
                    JsonObject response = adoptionResponse.getAsJsonObject("response");
                    int responseCode = Integer.parseInt(String.valueOf(response.get("responseCode")));
                    if (responseCode == 109) {
                        Toast.makeText(this, "Request cancel Successfully..", Toast.LENGTH_SHORT).show();
                        PetParentSingleton.getInstance().getGetDonationRequestListData().remove(donationListPosition);
                        donationRequestAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "Try Again!!", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onError(Throwable t, String key) {
        if (fromSwipe){
            donation_list_SWL.setRefreshing(false);
        }
    }
}