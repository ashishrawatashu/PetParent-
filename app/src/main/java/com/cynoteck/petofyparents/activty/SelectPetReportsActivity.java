package com.cynoteck.petofyparents.activty;

import android.os.Bundle;
import android.view.View;

import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.api.ApiResponse;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Response;

public class SelectPetReportsActivity extends AppCompatActivity implements View.OnClickListener, ApiResponse {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_pet_reports);

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onResponse(Response arg0, String key) {

    }

    @Override
    public void onError(Throwable t, String key) {

    }
}
