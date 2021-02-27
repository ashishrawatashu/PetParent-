package com.cynoteck.petofyparents.activty;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.api.ApiClient;
import com.cynoteck.petofyparents.api.ApiResponse;
import com.cynoteck.petofyparents.api.ApiService;
import com.cynoteck.petofyparents.utils.Methods;
import com.google.gson.JsonObject;

import java.io.IOException;

import retrofit2.Response;

public class SendPhoneNumber extends AppCompatActivity implements View.OnClickListener, ApiResponse, TextWatcher {

    EditText enter_phone_ET;
    Button next_BT;
    String phoneNumber,vetID="";
    ImageView qrCodeScanner_IV,cross_IV,back_arrow_IV;
    Methods methods;
    Drawable drawableRight;
    private static final int REQUEST_CAMERA_PERMISSION = 201;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_phone_number);
        methods = new Methods(this);
        enter_phone_ET=findViewById(R.id.enter_phone_ET);

        next_BT=findViewById(R.id.next_BT);
        cross_IV=findViewById(R.id.cross_IV);
        back_arrow_IV=findViewById(R.id.back_arrow_IV);
        back_arrow_IV.setOnClickListener(this);
        enter_phone_ET.addTextChangedListener(this);
//        qrCodeScanner_IV.setOnClickListener(this);


        next_BT.setOnClickListener(this);
        cross_IV.setOnClickListener(this);
        Intent intent = getIntent();
        next_BT.setEnabled(false);
        vetID = intent.getStringExtra("vetID");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(this, new
                    String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CAMERA_PERMISSION && grantResults.length>0){
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, "Allow Permission for QR Scanner.", Toast.LENGTH_SHORT).show();
                finish();
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                } else {
                    ActivityCompat.requestPermissions(this, new
                            String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                }
            }
            else{
                Log.e("NOPERMISION", "no");
            }
        }else
            finish();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.next_BT:
                phoneNumber=enter_phone_ET.getText().toString().trim();
                if (phoneNumber.isEmpty()){
                    Toast.makeText(this, "Enter Phone number !", Toast.LENGTH_SHORT).show();
                }else if (phoneNumber.length()<10){
                    Toast.makeText(this, "Invalid Phone number !", Toast.LENGTH_SHORT).show();

                }else {
                    JsonObject sendParentPhone = new JsonObject();
                    sendParentPhone.addProperty("phoneNumber",phoneNumber);
                    JsonObject saveRequest = new JsonObject();
                    saveRequest.add("data",sendParentPhone);
                    methods.showCustomProgressBarDialog(this);
                    ApiService<JsonObject> service = new ApiService<>();
                    service.get(SendPhoneNumber.this, ApiClient.getApiInterface().sendRegistrationOtp(saveRequest), "SendRegistrationOtp");
                    Log.e("SendRegistrationOtp",""+saveRequest);

                }
                break;

            case R.id.cross_IV:
                enter_phone_ET.getText().clear();

                break;
            case R.id.back_arrow_IV:
               onBackPressed();
                break;
//            case R.id.qrCodeScanner_IV:
//
//                Intent qrIntent = new Intent(this,ScannerQR.class);
//                startActivityForResult(qrIntent,1);
//                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1){
            if (resultCode==RESULT_OK){
                qrCodeScanner_IV.setVisibility(View.INVISIBLE);
                enter_phone_ET.requestFocus();
                vetID = data.getStringExtra("vetID");
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                Toast.makeText(this, "Enter Your Phone Number", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResponse(Response arg0, String key) {
        switch (key){
            case "SendRegistrationOtp":
                try {
                    methods.customProgressDismiss();
                    Log.e("SendRegistrationOtp",arg0.body().toString());
                    JsonObject sendRegistrationOtp = (JsonObject) arg0.body();
                    JsonObject response = sendRegistrationOtp.getAsJsonObject("response");
                    int responseCode = Integer.parseInt(String.valueOf(response.get("responseCode")));
                    if (responseCode==109){
                        enter_phone_ET.getText().clear();
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                        JsonObject data = sendRegistrationOtp.getAsJsonObject("data");
                        String otp = String.valueOf(data.get("otp"));
                        Intent otpVerifyIntent = new Intent(this,OTPVerifyActivity.class);

                        otpVerifyIntent.putExtra("OTP",otp);
                        otpVerifyIntent.putExtra("phoneNumber",phoneNumber);
                        otpVerifyIntent.putExtra("vetID",vetID);
                        startActivity(otpVerifyIntent);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }


    }

    @Override
    public void onError(Throwable t, String key) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.toString().length()>0){
            cross_IV.setVisibility(View.VISIBLE);
        }else if (s.toString().length()==0){
            cross_IV.setVisibility(View.INVISIBLE);

        }

        if (s.toString().length()==10){
            next_BT.setEnabled(true);
            next_BT.setBackgroundResource(R.drawable.next_button_green_bg);
        }else if (s.toString().length()<10){
            next_BT.setEnabled(false);
            next_BT.setBackgroundResource(R.drawable.next_button_grey_bg);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}