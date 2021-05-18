package com.cynoteck.petofyparents.activty;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.api.ApiResponse;

import retrofit2.Response;

public class SearchProviderResultActivity extends AppCompatActivity implements View.OnClickListener, ApiResponse {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_provider_result);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onResponse(Response arg0, String key) {

    }

    @Override
    public void onError(Throwable t, String key) {

    }
}