package com.cynoteck.petofy.activity;

import static com.cynoteck.petofy.activity.AdoptPetActivity.cart_icon_IV;
import static com.cynoteck.petofy.activity.AdoptPetActivity.total_RL;
import static com.cynoteck.petofy.activity.AdoptPetActivity.total_adoption_RL;
import static com.cynoteck.petofy.activity.AdoptPetActivity.total_adoption_request_TV;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cynoteck.petofy.response.getAdoptionRequestListResponse.GetAdoptionRequestListData;
import com.cynoteck.petofy.response.getAdoptionRequestListResponse.GetAdoptionRequestListResponse;
import com.cynoteck.petofy.utils.PetParentSingleton;
import com.cynoteck.petofy.R;
import com.cynoteck.petofy.adapter.AdoptionRequestAdapter;
import com.cynoteck.petofy.api.ApiClient;
import com.cynoteck.petofy.api.ApiResponse;
import com.cynoteck.petofy.api.ApiService;
import com.cynoteck.petofy.utils.Config;
import com.cynoteck.petofy.onClicks.OnAdaptionDonationListClickListener;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.JsonObject;

import retrofit2.Response;

public class AdoptionRequestActivity extends AppCompatActivity implements View.OnClickListener, OnAdaptionDonationListClickListener, ApiResponse {
    RecyclerView            adoption_request_RV;
    MaterialCardView        back_arrow_CV;
    AdoptionRequestAdapter  adoptionRequestAdapter;
    int                     adoptionListPosition;
    SwipeRefreshLayout      adoption_SRL;
    boolean                 fromSwipe = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adoption_satus);

        adoption_request_RV     = findViewById(R.id.adoption_request_RV);
        back_arrow_CV           = findViewById(R.id.back_arrow_CV);
        adoption_SRL            = findViewById(R.id.adoption_SRL);

        back_arrow_CV.setOnClickListener(this);

        adoption_request_RV.setLayoutManager(new LinearLayoutManager(this));
        adoptionRequestAdapter = new AdoptionRequestAdapter(this, PetParentSingleton.getInstance().getGetAdoptionRequestListData(), this);
        adoption_request_RV.setAdapter(adoptionRequestAdapter);

        adoption_SRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onRefresh() {
                fromSwipe = true;
                PetParentSingleton.getInstance().getGetAdoptionRequestListData().clear();
                adoptionRequestAdapter.notifyDataSetChanged();
                getAdoptionRequest();

            }
        });
    }

    private void getAdoptionRequest() {
        ApiService<GetAdoptionRequestListResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getAdoptionRequest(Config.token,"social-service/get-adoption-request-list/0"), "AdoptionRequest");

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrow_CV:
                onBackPressed();
                break;

        }
    }

    @Override
    public void onCancelRequestClickListener(int position) {
        adoptionListPosition = position;
        String adoptionId = String.valueOf(PetParentSingleton.getInstance().getGetAdoptionRequestListData().get(position).getId());
        String realId = adoptionId.substring(0,adoptionId.length()-2);

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("");
        alertDialog.setMessage("Do you want to cancel this request?");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteAdoptionRequest(realId);
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

    private void deleteAdoptionRequest(String realId) {
        ApiService<JsonObject> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().deleteAdoptionRequest(Config.token, "social-service/cancel-adoption-request/"+realId), "CancelRequest");
        //Log.d("DIOLOG====>",  "social-service/cancel-adoption-request/"+realId);
    }

    @Override
    public void onPetDetailsClickListener(int position) {
        Intent intent = new Intent(this, AdoptionPetDetailsActivity.class);
        intent.putExtra("image", String.valueOf(PetParentSingleton.getInstance().getGetAdoptionRequestListData().get(position).getPet().getPetImageList().get(0).getPetImageUrl()));
        intent.putExtra("pet_id", PetParentSingleton.getInstance().getGetAdoptionRequestListData().get(position).getId());
        intent.putExtra("pet_name", PetParentSingleton.getInstance().getGetAdoptionRequestListData().get(position).getPet().getPetName());
        intent.putExtra("pet_gender", PetParentSingleton.getInstance().getGetAdoptionRequestListData().get(position).getPet().getCategory());
        intent.putExtra("pet_age", PetParentSingleton.getInstance().getGetAdoptionRequestListData().get(position).getPet().getPetAge());
        intent.putExtra("pet_breed", PetParentSingleton.getInstance().getGetAdoptionRequestListData().get(position).getPet().getPetBreed());
        intent.putExtra("pet_color", PetParentSingleton.getInstance().getGetAdoptionRequestListData().get(position).getPet().getPetColor());
        intent.putExtra("pet_size", PetParentSingleton.getInstance().getGetAdoptionRequestListData().get(position).getPet().getPetSize());
        intent.putExtra("donar_name", PetParentSingleton.getInstance().getGetAdoptionRequestListData().get(position).getPet().getDonarName());
        intent.putExtra("donar_phone", String.valueOf(PetParentSingleton.getInstance().getGetAdoptionRequestListData().get(position).getPet().getPhoneNumber()));
        intent.putExtra("donar_mail", "");
        intent.putExtra("donar_address", PetParentSingleton.getInstance().getGetAdoptionRequestListData().get(position).getPet().getAddress());
        intent.putExtra("from", "AdoptionRequestActivity");

        startActivity(intent);
    }

    @Override
    public void onResponse(Response arg0, String key) {
        switch (key){
            case "AdoptionRequest":
                try {
                    fromSwipe = false;
                    PetParentSingleton.getInstance().getGetAdoptionRequestListData().clear();
                    GetAdoptionRequestListResponse getAdoptionRequestListResponse = (GetAdoptionRequestListResponse) arg0.body();
                    int responseCode = Integer.parseInt(getAdoptionRequestListResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        if (getAdoptionRequestListResponse.getData().size() > 0) {
                            for (int i = 0; i < getAdoptionRequestListResponse.getData().size(); i++) {
                                GetAdoptionRequestListData getAdoptionRequestListData = new GetAdoptionRequestListData();
                                getAdoptionRequestListData.setId(getAdoptionRequestListResponse.getData().get(i).getId());
                                getAdoptionRequestListData.setUserId(getAdoptionRequestListResponse.getData().get(i).getUserId());
                                getAdoptionRequestListData.setRequestDate(getAdoptionRequestListResponse.getData().get(i).getRequestDate());
                                getAdoptionRequestListData.setRequesterName(getAdoptionRequestListResponse.getData().get(i).getRequesterName());
                                getAdoptionRequestListData.setRequestCurrentStatus(getAdoptionRequestListResponse.getData().get(i).getRequestCurrentStatus());
                                getAdoptionRequestListData.setRequestUpdateDate(getAdoptionRequestListResponse.getData().get(i).getRequestUpdateDate());
                                getAdoptionRequestListData.setPet(getAdoptionRequestListResponse.getData().get(i).getPet());
                                getAdoptionRequestListData.setPetImageList(getAdoptionRequestListResponse.getData().get(i).getPetImageList());

                                PetParentSingleton.getInstance().getGetAdoptionRequestListData().add(getAdoptionRequestListData);
                            }
                            if (PetParentSingleton.getInstance().getGetAdoptionRequestListData().isEmpty()){
                                total_adoption_RL.setEnabled(false);
                                cart_icon_IV.setImageResource(R.drawable.cart_inactive);
                                total_RL.setVisibility(View.GONE);
                            }else {
                                total_adoption_RL.setEnabled(true);
                                total_adoption_request_TV.setText(String.valueOf(PetParentSingleton.getInstance().getGetAdoptionRequestListData().size()));
                                cart_icon_IV.setImageResource(R.drawable.cart_icon);
                                total_RL.setVisibility(View.VISIBLE);
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
                    Log.d("Cancel", adoptionResponse.toString());
                    JsonObject response = adoptionResponse.getAsJsonObject("response");
                    Log.d("hhshshhs", "" + response);
                    int responseCode = Integer.parseInt(String.valueOf(response.get("responseCode")));
                    if (responseCode == 109) {
                        Toast.makeText(this, "Request cancel Successfully..", Toast.LENGTH_SHORT).show();
                        PetParentSingleton.getInstance().getGetAdoptionRequestListData().remove(adoptionListPosition);
                        adoptionRequestAdapter.notifyDataSetChanged();
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
            adoption_SRL.setRefreshing(false);
        }
    }
}