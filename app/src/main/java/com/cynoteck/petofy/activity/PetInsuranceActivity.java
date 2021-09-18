package com.cynoteck.petofy.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cynoteck.petofy.R;
import com.google.android.material.card.MaterialCardView;

public class PetInsuranceActivity extends AppCompatActivity implements View.OnClickListener {
    MaterialCardView    back_arrow_CV;
    Button              know_more_BT;
    LinearLayout        insurance_card_LL;
    Dialog              insurance_successfully_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_insurance);

        back_arrow_CV   = findViewById(R.id.back_arrow_CV);
        know_more_BT    = findViewById(R.id.know_more_BT);
        insurance_card_LL = findViewById(R.id.insurance_card_LL);

        know_more_BT.setOnClickListener(this);
        back_arrow_CV.setOnClickListener(this);
        insurance_card_LL.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.back_arrow_CV:
                onBackPressed();
                break;

            case R.id.know_more_BT:
                Intent insurancesIntent = new Intent(this, InsuranceActivity.class);
                startActivity(insurancesIntent);
                break;

            case R.id.insurance_card_LL:
                Intent insuranceIntent = new Intent(this, BuyInsuranceActivity.class);
                insuranceIntent.putExtra("afterLogin","no");
//                startActivity(insuranceIntent);
                startActivityForResult(insuranceIntent,1);
                break;

        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                showAppointmentSuccessfully();
            }
        }
    }
    private void showAppointmentSuccessfully() {
        insurance_successfully_dialog = new Dialog(this);
        insurance_successfully_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        insurance_successfully_dialog.setCancelable(false);
        insurance_successfully_dialog.setContentView(R.layout.insurance_submitted_dilog);
        insurance_successfully_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        TextView back_to_appointments_TV = insurance_successfully_dialog.findViewById(R.id.back_to_appointments_TV);
        back_to_appointments_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insurance_successfully_dialog.dismiss();
            }
        });

        insurance_successfully_dialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = insurance_successfully_dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }
}