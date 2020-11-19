package com.cynoteck.petofyparents.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.activty.AdoptionActivity;
import com.cynoteck.petofyparents.activty.DonationActivity;

public class AllStaffFragment extends Fragment implements View.OnClickListener {

    CardView adoption_CV,donation_CV;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_all_staff, container, false);
        init();
        return view;
    }

    private void init()
    {
        adoption_CV=view.findViewById(R.id.adoption_CV);
        donation_CV=view.findViewById(R.id.donation_CV);

        adoption_CV.setOnClickListener(this);
        donation_CV.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.adoption_CV:
                startActivity(new Intent(getActivity(),AdoptionActivity.class));
                break;

            case R.id.donation_CV:
                startActivity(new Intent(getActivity(), DonationActivity.class));
                break;

        }

    }


}