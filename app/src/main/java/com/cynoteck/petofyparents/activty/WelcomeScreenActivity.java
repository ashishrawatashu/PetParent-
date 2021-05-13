package com.cynoteck.petofyparents.activty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cynoteck.petofyparents.R;

public class WelcomeScreenActivity extends AppCompatActivity {

    Button continue_BT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        continue_BT = findViewById(R.id.continue_BT);

        continue_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendPhoneNumber_intent = new Intent(WelcomeScreenActivity.this, SendPhoneNumber.class);
                startActivity(sendPhoneNumber_intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}