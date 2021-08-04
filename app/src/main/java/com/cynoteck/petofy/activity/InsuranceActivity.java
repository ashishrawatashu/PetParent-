package com.cynoteck.petofy.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import com.cynoteck.petofy.R;
import com.cynoteck.petofy.response.getPetReportsResponse.getPetListResponse.PetList;
import com.cynoteck.petofy.utils.Config;
import com.cynoteck.petofy.utils.PetParentSingleton;
import com.google.android.material.card.MaterialCardView;

import static com.cynoteck.petofy.fragments.PetRegisterFragment.registerPetAdapter;
import static com.cynoteck.petofy.fragments.PetRegisterFragment.total_pets_TV;
import static com.cynoteck.petofy.fragments.ProfileFragment.petListHorizontalAdapter;

public class InsuranceActivity extends AppCompatActivity implements View.OnClickListener {

    Button              buy_now_BT;
    MaterialCardView    back_arrow_CV;
    Dialog              insurance_successfully_dialog;
    LinearLayout        insurance_card_LL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance);

        initView();
    }

    private void initView() {
        buy_now_BT          = findViewById(R.id.buy_now_BT);
        back_arrow_CV       = findViewById(R.id.back_arrow_CV);
        insurance_card_LL   = findViewById(R.id.insurance_card_LL);

        back_arrow_CV.setOnClickListener(this);
        buy_now_BT.setOnClickListener(this);
        insurance_card_LL.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.insurance_card_LL:

            case R.id.buy_now_BT:
                startActivityForResult(new Intent(this,BuyInsuranceActivity.class),1);
                break;

            case R.id.back_arrow_CV:
                finish();
                break;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                showAppointmentSuccessfully();
            }
        }
    }
}