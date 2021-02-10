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
import com.google.gson.JsonObject;

import retrofit2.Response;

public class LoginOptionActivity extends AppCompatActivity implements View.OnClickListener , ApiResponse {
    ImageView qrCodeScanner_IV;
    EditText enter_phone_ET;
    Button request_code_BT;
    String phoneNumber,vetID="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_option);
        enter_phone_ET=findViewById(R.id.enter_phone_ET);
        qrCodeScanner_IV = findViewById(R.id.qrCodeScanner_IV);
        request_code_BT = findViewById(R.id.request_code_BT);
        qrCodeScanner_IV.setOnClickListener(this);
        enter_phone_ET.requestFocus();
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

                    ApiService<JsonObject> service = new ApiService<>();
                    service.get(this, ApiClient.getApiInterface().sendRegistrationOtp(saveRequest), "SendRegistrationOtp");
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

    }

    @Override
    public void onError(Throwable t, String key) {

    }
}