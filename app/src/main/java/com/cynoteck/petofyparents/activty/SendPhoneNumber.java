package com.cynoteck.petofyparents.activty;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.api.ApiClient;
import com.cynoteck.petofyparents.api.ApiResponse;
import com.cynoteck.petofyparents.api.ApiService;
import com.cynoteck.petofyparents.utils.Methods;
import com.google.gson.JsonObject;

import retrofit2.Response;

public class SendPhoneNumber extends AppCompatActivity implements View.OnClickListener, ApiResponse {

    EditText enter_phone_ET;
    Button request_code_BT;
    String phoneNumber,vetID="";
    ImageView qrCodeScanner_IV;
    Methods methods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_phone_number);
        methods = new Methods(this);
        enter_phone_ET=findViewById(R.id.enter_phone_ET);
        request_code_BT=findViewById(R.id.request_code_BT);
        qrCodeScanner_IV=findViewById(R.id.qrCodeScanner_IV);
        qrCodeScanner_IV.setOnClickListener(this);
        request_code_BT.setOnClickListener(this);
        Intent intent = getIntent();
        vetID = intent.getStringExtra("vetID");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.request_code_BT:
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

            case R.id.qrCodeScanner_IV:

                startActivity(new Intent(this,ScannerQR.class));

                break;


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
}