package com.cynoteck.petofyparents.activty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cynoteck.petofyparents.PetParentSingleton;
import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.adapter.AdoptionRequestAdapter;
import com.cynoteck.petofyparents.utils.OnAdaptionDonationListClickListener;
import com.google.android.material.card.MaterialCardView;

public class AdoptionRequestActivity extends AppCompatActivity implements View.OnClickListener, OnAdaptionDonationListClickListener {
    RecyclerView adoption_request_RV;
    MaterialCardView back_arrow_CV;
    AdoptionRequestAdapter adoptionRequestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adoption_satus);

        adoption_request_RV = findViewById(R.id.adoption_request_RV);
        back_arrow_CV = findViewById(R.id.back_arrow_CV);

        back_arrow_CV.setOnClickListener(this);

        adoption_request_RV.setLayoutManager(new LinearLayoutManager(this));
        adoptionRequestAdapter = new AdoptionRequestAdapter(this, PetParentSingleton.getInstance().getGetAdoptionRequestListData(), this);
        adoption_request_RV.setAdapter(adoptionRequestAdapter);


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

    }

    @Override
    public void onPetDetailsClickListener(int position) {

    }
}