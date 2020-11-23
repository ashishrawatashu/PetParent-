package com.cynoteck.petofyparents.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.api.ApiClient;
import com.cynoteck.petofyparents.api.ApiResponse;
import com.cynoteck.petofyparents.api.ApiService;
import com.cynoteck.petofyparents.response.adoptionResponse.AdoptionResponse;
import com.cynoteck.petofyparents.utils.Config;

import retrofit2.Response;

public class PendingAdoptionFragments extends Fragment implements ApiResponse {
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_pending_adoption_fragments, container, false);
        getData();
        return view;
    }

    private void getData()
    {
        ApiService<AdoptionResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getAdoptionRequestListPending(Config.token), "getAdoptionRequestListPending");

    }

    @Override
    public void onResponse(Response arg0, String key) {
        switch (key)
        {
            case "getAdoptionRequestListPending":
                AdoptionResponse adoptionResponse = (AdoptionResponse) arg0.body();
                Log.d("getAdoptionRequestListPending",""+adoptionResponse.toString());
                break;
        }

    }

    @Override
    public void onError(Throwable t, String key) {

    }
}