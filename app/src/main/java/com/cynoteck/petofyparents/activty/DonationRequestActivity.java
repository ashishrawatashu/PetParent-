package com.cynoteck.petofyparents.activty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.cynoteck.petofyparents.PetParentSingleton;
import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.adapter.AdoptionRequestAdapter;
import com.cynoteck.petofyparents.adapter.DonationRequestAdapter;
import com.cynoteck.petofyparents.utils.OnAdaptionDonationListClickListener;
import com.google.android.material.card.MaterialCardView;

public class DonationRequestActivity extends AppCompatActivity implements OnAdaptionDonationListClickListener , View.OnClickListener {
    RecyclerView donation_request_RV;
    MaterialCardView back_arrow_CV;
    DonationRequestAdapter donationRequestAdapter;
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_request);


        donation_request_RV = findViewById(R.id.donation_request_RV);
        back_arrow_CV = findViewById(R.id.back_arrow_CV);

        back_arrow_CV.setOnClickListener(this);
        donation_request_RV.setLayoutManager(new LinearLayoutManager(this));
        donationRequestAdapter = new DonationRequestAdapter(this, PetParentSingleton.getInstance().getGetDonationRequestListData(),this);
        donation_request_RV.setAdapter(donationRequestAdapter);
    }

    @Override
    public void onCancelRequestClickListener(int position) {

    }

    @Override
    public void onPetDetailsClickListener(int position) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrow_CV:
                onBackPressed();
                break;

        }
    }
}