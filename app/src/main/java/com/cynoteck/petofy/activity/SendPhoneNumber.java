package com.cynoteck.petofy.activity;

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
import androidx.core.content.ContextCompat;


import com.cynoteck.petofy.R;
import com.cynoteck.petofy.api.ApiClient;
import com.cynoteck.petofy.api.ApiResponse;
import com.cynoteck.petofy.api.ApiService;
import com.cynoteck.petofy.utils.Methods;
import com.google.gson.JsonObject;

import retrofit2.Response;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.ACCESS_WIFI_STATE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.cynoteck.petofy.activity.DashBoardActivity.MY_PERMISSIONS_REQUEST_LOCATION;

public class SendPhoneNumber extends AppCompatActivity implements View.OnClickListener, ApiResponse, TextWatcher {

    EditText                    enter_phone_ET;
    Button                      next_BT;
    String                      phoneNumber,vetID="";
    ImageView                   qrCodeScanner_IV,cross_IV,back_arrow_IV;
    Methods                     methods;
    Drawable                    drawableRight;
    private static final int    REQUEST_CAMERA_PERMISSION = 201, PERMISSION_REQUEST_CODE=400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_phone_number);
        methods = new Methods(this);
        enter_phone_ET      =   findViewById(R.id.enter_phone_ET);
        next_BT             =   findViewById(R.id.next_BT);
        cross_IV            =   findViewById(R.id.cross_IV);
        back_arrow_IV       =   findViewById(R.id.back_arrow_IV);

        back_arrow_IV.setOnClickListener(this);
        enter_phone_ET.addTextChangedListener(this);
//        qrCodeScanner_IV.setOnClickListener(this);

        if (checkPermission()) {
        } else {
            if (!checkPermission()) {
                requestPermission();
            } else {
                Toast.makeText(this, "Permission already granted.", Toast.LENGTH_SHORT).show();
            }
        }

        next_BT.setOnClickListener(this);
        cross_IV.setOnClickListener(this);
        Intent intent = getIntent();
        next_BT.setEnabled(false);
        vetID = intent.getStringExtra("vetID");
    }


    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION,CAMERA,READ_PHONE_STATE,READ_EXTERNAL_STORAGE,WRITE_EXTERNAL_STORAGE,ACCESS_WIFI_STATE,ACCESS_NETWORK_STATE}, PERMISSION_REQUEST_CODE);

    }
    private boolean checkPermission() {

        int result      = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int result1     = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int result2     = ContextCompat.checkSelfPermission(getApplicationContext(), READ_PHONE_STATE);
        int result3     = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int result4     = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result5     = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_WIFI_STATE);
        int result6     = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_NETWORK_STATE);
        return result   == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED &&  result2 == PackageManager.PERMISSION_GRANTED  && result3 == PackageManager.PERMISSION_GRANTED &&  result4 == PackageManager.PERMISSION_GRANTED && result5 == PackageManager.PERMISSION_GRANTED &&  result6 == PackageManager.PERMISSION_GRANTED;

    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CAMERA_PERMISSION && grantResults.length>0){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }else if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                checkLocationPermission();
            }
        }
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
                    if (methods.isInternetOn()){
                        methods.showCustomProgressBarDialog(this);
                        ApiService<JsonObject> service = new ApiService<>();
                        service.get(SendPhoneNumber.this, ApiClient.getApiInterface().sendRegistrationOtp(saveRequest), "SendRegistrationOtp");
                        //Log.d"SendRegistrationOtp",""+saveRequest);
                    }else {
                        methods.DialogInternet();
                    }

                }
                break;

            case R.id.cross_IV:
                enter_phone_ET.getText().clear();

                break;
            case R.id.back_arrow_IV:
                finishAffinity();
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
                    Log.d("SendRegistrationOtp",arg0.body().toString());
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
                    }else {
                        Toast.makeText(this, "Please try again !", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }


    }

    @Override
    public void onError(Throwable t, String key) {
        methods.customProgressDismiss();
        Toast.makeText(this, "Something went wrong !", Toast.LENGTH_SHORT).show();

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();

    }
}