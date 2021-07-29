package com.cynoteck.petofy.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cynoteck.petofy.utils.PetParentSingleton;
import com.cynoteck.petofy.R;
import com.cynoteck.petofy.adapter.AdoptionListAdopter;
import com.cynoteck.petofy.api.ApiClient;
import com.cynoteck.petofy.api.ApiResponse;
import com.cynoteck.petofy.api.ApiService;
import com.cynoteck.petofy.parameter.adoptionListRequest.AdoptionListHeader;
import com.cynoteck.petofy.parameter.adoptionListRequest.AdoptionListParameter;
import com.cynoteck.petofy.parameter.adoptionListRequest.AdoptionListRequestModel;
import com.cynoteck.petofy.response.adoptionListResponse.AdoptionListResponse;
import com.cynoteck.petofy.response.adoptionListResponse.PetDonationList;
import com.cynoteck.petofy.onClicks.AdoptionListOnClick;
import com.cynoteck.petofy.utils.Config;
import com.cynoteck.petofy.utils.Methods;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

import retrofit2.Response;

@SuppressLint("StaticFieldLeak")
public class AdoptPetActivity extends AppCompatActivity implements View.OnClickListener, ApiResponse, AdoptionListOnClick {
    RecyclerView                    adoption_RV;
    MaterialCardView                back_arrow_CV;
    Methods                         methods;
    AdoptionListAdopter             adoptionListAdopter;
    List<PetDonationList>           petDonationLists;
    NestedScrollView                nestedSV;
    ProgressBar                     progressBar;
    RadioButton                     cats_RB,dog_RB;
    public static RelativeLayout    total_adoption_RL,total_RL;
    public static TextView          total_adoption_request_TV;
    public static ImageView         cart_icon_IV;
    int                             pageNumber = 1, pageSize = 10;
    String                          getStrSpnerItemPetNmId = "2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_you_want_adopt);
        init();
    }

    private void init() {
        methods                     = new Methods(this);
        progressBar                 = findViewById(R.id.progressBar);
        adoption_RV                 = findViewById(R.id.adoption_RV);
        nestedSV                    = findViewById(R.id.nestedScrollView);
        dog_RB                      = findViewById(R.id.dog_RB);
        cats_RB                     = findViewById(R.id.cats_RB);
        total_RL                    = findViewById(R.id.total_RL);
        back_arrow_CV               = findViewById(R.id.back_arrow_CV);
        total_adoption_RL           = findViewById(R.id.total_adoption_RL);
        total_adoption_request_TV   = findViewById(R.id.total_adoption_request_TV);
        cart_icon_IV                = findViewById(R.id.cart_icon_IV);


        total_adoption_RL.setOnClickListener(this);
        cats_RB.setOnClickListener(this);
        dog_RB.setOnClickListener(this);
        back_arrow_CV.setOnClickListener(this);


        if (methods.isInternetOn()) {
            getAdoptionList(getStrSpnerItemPetNmId);
        } else {
            methods.isInternetOn();
        }

        nestedSV.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    pageSize++;
                    progressBar.setVisibility(View.VISIBLE);
                    getAdoptionList(getStrSpnerItemPetNmId);
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!PetParentSingleton.getInstance().getGetAdoptionRequestListData().isEmpty()){
            total_adoption_request_TV.setText(String.valueOf(PetParentSingleton.getInstance().getGetAdoptionRequestListData().size()));
            total_adoption_RL.setEnabled(true);
            total_RL.setVisibility(View.VISIBLE);
            cart_icon_IV.setImageResource(R.drawable.cart_icon);
        }else {
            total_adoption_RL.setEnabled(false);
            cart_icon_IV.setImageResource(R.drawable.cart_inactive);
            total_RL.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.back_arrow_CV:

                onBackPressed();

                break;
            case R.id.cats_RB:

                getStrSpnerItemPetNmId ="2";
                cats_RB.setChecked(true);
                dog_RB.setChecked(false);
                getAdoptionList(getStrSpnerItemPetNmId);

                break;


            case R.id.dog_RB:

                getStrSpnerItemPetNmId ="1";
                cats_RB.setChecked(false);
                dog_RB.setChecked(true);
                getAdoptionList(getStrSpnerItemPetNmId);

                break;

            case R.id.total_adoption_RL:

                Intent adoptionRequestActivityIntent = new Intent(this, AdoptionRequestActivity.class);
                startActivity(adoptionRequestActivityIntent);
                break;
        }

    }


    private void getAdoptionList(String petCategoryId) {
        AdoptionListParameter adoptionListParameter = new AdoptionListParameter();
        adoptionListParameter.setPetCategoryId(petCategoryId);
        adoptionListParameter.setPetSexId("0.0");
        adoptionListParameter.setPetAgeId("0.0");
        adoptionListParameter.setPetSizeId("0.0");
        adoptionListParameter.setPetColorId("0.0");
        adoptionListParameter.setPetBreedId("0.0");

        AdoptionListHeader adoptionListHeader = new AdoptionListHeader();
        adoptionListHeader.setPageNumber(pageNumber);
        adoptionListHeader.setPageSize(pageSize);
        adoptionListHeader.setSearchData("");

        AdoptionListRequestModel adoptionListRequestModel = new AdoptionListRequestModel();
        adoptionListRequestModel.setData(adoptionListParameter);
        adoptionListRequestModel.setHeader(adoptionListHeader);

        ApiService<AdoptionListResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getAdoptionList(Config.token, adoptionListRequestModel), "getAdoptionList");
        Log.e("DIOLOG====>", "" + adoptionListRequestModel);
    }


    @Override
    public void onResponse(Response arg0, String key) {
        switch (key) {
            case "getAdoptionList":
                try {
                    progressBar.setVisibility(View.GONE);
                    Log.d("getAdoptionList", arg0.body().toString());
                    AdoptionListResponse adoptionListResponse = (AdoptionListResponse) arg0.body();
                    int responseCode = Integer.parseInt(adoptionListResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        if (adoptionListResponse.getData().getPetDonationList().isEmpty()){
                            Toast.makeText(this, "No pet found !", Toast.LENGTH_SHORT).show();
                        }else {
                            adoption_RV.setLayoutManager(new GridLayoutManager(this, 2));
                            adoptionListAdopter = new AdoptionListAdopter(this, adoptionListResponse.getData().getPetDonationList(), this);
                            adoption_RV.setAdapter(adoptionListAdopter);
                            adoptionListAdopter.notifyDataSetChanged();
                            petDonationLists = adoptionListResponse.getData().getPetDonationList();
                        }



                    } else if (responseCode == 614) {
                        Toast.makeText(this, adoptionListResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
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
    public void onItemClick(int position) {
        Intent intent = new Intent(AdoptPetActivity.this, AdoptionPetDetailsActivity.class);
        intent.putExtra("image", petDonationLists.get(position).getPetImageList().get(0).getPetImageUrl());
        intent.putExtra("pet_id", petDonationLists.get(position).getId());
        intent.putExtra("pet_name", petDonationLists.get(position).getPetName());
        intent.putExtra("pet_gender", petDonationLists.get(position).getPetCategory());
        intent.putExtra("pet_age", petDonationLists.get(position).getPetAge());
        intent.putExtra("pet_breed", petDonationLists.get(position).getPetBreed());
        intent.putExtra("pet_color", petDonationLists.get(position).getPetColor());
        intent.putExtra("pet_size", petDonationLists.get(position).getPetSize());
        intent.putExtra("donar_name", petDonationLists.get(position).getDonarName());
        intent.putExtra("donar_phone", petDonationLists.get(position).getPhoneNumber());
        intent.putExtra("donar_mail", "");
        intent.putExtra("donar_address", petDonationLists.get(position).getAddress());
        intent.putExtra("from", "AdoptPetActivity");

        startActivity(intent);

    }
}