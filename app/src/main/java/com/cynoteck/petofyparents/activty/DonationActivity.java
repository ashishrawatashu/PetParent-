package com.cynoteck.petofyparents.activty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cynoteck.petofyparents.PetParentSingleton;
import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.adapter.DonatePetAdapter;
import com.cynoteck.petofyparents.api.ApiClient;
import com.cynoteck.petofyparents.api.ApiResponse;
import com.cynoteck.petofyparents.api.ApiService;
import com.cynoteck.petofyparents.utils.Config;
import com.cynoteck.petofyparents.utils.OnItemClickListener;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.JsonObject;

import retrofit2.Response;

public class DonationActivity extends AppCompatActivity implements View.OnClickListener, ApiResponse, OnItemClickListener {

    RecyclerView pet_list_RV;
    MaterialCardView back_arrow_CV;
    public static RelativeLayout total_donation_RL, add_pet_RL;
    public static TextView total_donation_request_TV;
    DonatePetAdapter donatePetAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);

        pet_list_RV = findViewById(R.id.pet_list_RV);
        back_arrow_CV = findViewById(R.id.back_arrow_CV);
        total_donation_RL = findViewById(R.id.total_donation_RL);
        add_pet_RL = findViewById(R.id.add_pet_RL);
        total_donation_request_TV = findViewById(R.id.total_donation_request_TV);

        add_pet_RL.setOnClickListener(this);
        total_donation_RL.setOnClickListener(this);
        back_arrow_CV.setOnClickListener(this);

        if (!PetParentSingleton.getInstance().getGetDonationRequestListData().isEmpty()) {
            total_donation_request_TV.setText(String.valueOf(PetParentSingleton.getInstance().getGetDonationRequestListData().size()));
            total_donation_RL.setEnabled(true);
        } else {
            total_donation_RL.setEnabled(false);
        }


        pet_list_RV.setLayoutManager(new LinearLayoutManager(this));
        donatePetAdapter = new DonatePetAdapter(this, PetParentSingleton.getInstance().getArrayList(), this);
        pet_list_RV.setAdapter(donatePetAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.total_donation_RL:
                Intent donationRequestActivityIntent = new Intent(this, DonationRequestActivity.class);
                startActivity(donationRequestActivityIntent);
                break;

            case R.id.add_pet_RL:

                break;

            case R.id.back_arrow_CV:
                onBackPressed();
                break;


        }
    }

    @Override
    public void onResponse(Response arg0, String key) {
        switch (key) {
            case "DonatePetById":
                try {
                    JsonObject jsonObject = (JsonObject) arg0.body();
                    JsonObject response = jsonObject.getAsJsonObject("response");
                    int responseCode = Integer.parseInt(String.valueOf(response.get("responseCode")));
                    if (responseCode == 109) {
                        Toast.makeText(this, "Successfully Donate", Toast.LENGTH_SHORT).show();
                        finish();
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
    public void onViewDetailsClick(int position) {

        JsonObject jsonObjectParams = new JsonObject();
        jsonObjectParams.addProperty("id", PetParentSingleton.getInstance().getArrayList().get(position).getId().substring(0,PetParentSingleton.getInstance().getArrayList().get(position).getId().length()-2));

        JsonObject jsonObjectRequest = new JsonObject();
        jsonObjectRequest.add("data", jsonObjectParams);

        ApiService<JsonObject> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().donatePetById(Config.token, jsonObjectRequest), "DonatePetById");
        Log.e("DATALOG", "check1=> " + jsonObjectRequest);


    }
}