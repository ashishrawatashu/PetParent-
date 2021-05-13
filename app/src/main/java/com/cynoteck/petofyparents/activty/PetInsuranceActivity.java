package com.cynoteck.petofyparents.activty;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cynoteck.petofyparents.R;
import com.google.android.material.card.MaterialCardView;

public class PetInsuranceActivity extends AppCompatActivity implements View.OnClickListener {
    MaterialCardView back_arrow_CV;
    Button know_more_BT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_insurance);

        back_arrow_CV = findViewById(R.id.back_arrow_CV);
        know_more_BT=findViewById(R.id.know_more_BT);

        know_more_BT.setOnClickListener(this);
        back_arrow_CV.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.back_arrow_CV:
                onBackPressed();
                break;

            case R.id.know_more_BT:
                Toast.makeText(this, "Know More", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}