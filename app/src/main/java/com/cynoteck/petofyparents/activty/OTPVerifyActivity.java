package com.cynoteck.petofyparents.activty;

import android.content.ClipboardManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.api.ApiClient;
import com.cynoteck.petofyparents.api.ApiResponse;
import com.cynoteck.petofyparents.api.ApiService;
import com.cynoteck.petofyparents.response.loginResponse.LoginRegisterResponse;
import com.cynoteck.petofyparents.response.resendOTPResposne.ResendOTPResponse;
import com.cynoteck.petofyparents.response.validationOtpResponse.ValidationOtpResponse;
import com.cynoteck.petofyparents.utils.Config;
import com.cynoteck.petofyparents.utils.Methods;
import com.cynoteck.petofyparents.utils.SmsBroadcastReceiver;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.JsonObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Response;

public class OTPVerifyActivity extends AppCompatActivity implements TextWatcher, View.OnClickListener, ApiResponse {

    EditText editText_one, editText_two, editText_three, editText_four ;
    private static final int REQ_USER_CONSENT = 200;
    SmsBroadcastReceiver smsBroadcastReceiver;
    Button verify_BT;
    String otpString,phoneNumber="",vetID="";
    TextView headine1TV,resend_code_TV;
    ImageView back_arrow_IV;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor login_editor;
    Methods methods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p_verify);

        methods = new Methods(this);
        back_arrow_IV = findViewById(R.id.back_arrow_IV);
        editText_one = findViewById(R.id.editTextone);
        editText_two = findViewById(R.id.editTexttwo);
        editText_three = findViewById(R.id.editTextthree);
        editText_four = findViewById(R.id.editTextfour);
        verify_BT = findViewById(R.id.verify_BT);
        resend_code_TV=findViewById(R.id.resend_code_TV);
        headine1TV=findViewById(R.id.headine1TV);
        resend_code_TV.setOnClickListener(this);
        verify_BT.setOnClickListener(this);
        back_arrow_IV.setOnClickListener(this);
        editText_one.requestFocus();
        editText_one.addTextChangedListener(this);
        editText_two.addTextChangedListener(this);
        editText_three.addTextChangedListener(this);
        editText_four.addTextChangedListener(this);
        startSmsUserConsent();

        Intent intent = getIntent();
        vetID = intent.getStringExtra("vetID");
        phoneNumber = intent.getStringExtra("phoneNumber");
        otpString = intent.getStringExtra("OTP");

        headine1TV.setText("Please wait we will auto verify the OTP sent to"+" +91"+phoneNumber);


    }

    private void startSmsUserConsent() {
        SmsRetrieverClient client = SmsRetriever.getClient(this);
        //We can add sender phone number or leave it blank
        // I'm adding null here
        client.startSmsUserConsent(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
//                Toast.makeText(getApplicationContext(), "On Success", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getApplicationContext(), "On OnFailure", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.length() == 1) {
            if (editText_one.length() == 1) {
                editText_two.requestFocus();
            }

            if (editText_two.length() == 1) {
                editText_three.requestFocus();
            }
            if (editText_three.length() == 1) {
                editText_four.requestFocus();
            }
        } else if (editable.length() == 0) {
            if (editText_four.length() == 0) {
                editText_three.requestFocus();
            }
            if (editText_three.length() == 0) {
                editText_two.requestFocus();
            }
            if (editText_two.length() == 0) {
                editText_one.requestFocus();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_USER_CONSENT) {
            if ((resultCode == RESULT_OK) && (data != null)) {
                //That gives all message to us.
                // We need to get the code from inside with regex
                String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
//                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
//                textViewMessage.setText(
//                        String.format("%s - %s", getString(R.string.received_message), message));
                getOtpFromMessage(message);
            }
        }
    }
    private void registerBroadcastReceiver() {
        smsBroadcastReceiver = new SmsBroadcastReceiver();
        smsBroadcastReceiver.smsBroadcastReceiverListener =
                new SmsBroadcastReceiver.SmsBroadcastReceiverListener() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, REQ_USER_CONSENT);
                    }
                    @Override
                    public void onFailure() {
                    }
                };
        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        registerReceiver(smsBroadcastReceiver, intentFilter);
    }
    private void getOtpFromMessage(String message) {
        // This will match any 6 digit number in the message
        Pattern pattern = Pattern.compile("(|^)\\d{4}");
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
//            Toast.makeText(this, ""+matcher.group(0), Toast.LENGTH_SHORT).show();
            String otp = matcher.group(0);
            editText_one.setText(otp.substring(0,otp.length()-3));
            editText_two.setText(otp.substring(1,otp.length()-2));
            editText_three.setText(otp.substring(2,otp.length()-1));
            editText_four.setText(otp.substring(3,otp.length()-0));
            editText_four.clearFocus();

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerBroadcastReceiver();
    }
    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(smsBroadcastReceiver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.resend_code_TV:

                JsonObject resendOtpParams = new JsonObject();
                resendOtpParams.addProperty("phoneNumber",phoneNumber);
                JsonObject resendOtpRequest = new JsonObject();
                resendOtpRequest.add("data",resendOtpParams);

                methods.showCustomProgressBarDialog(this);
                ApiService<ResendOTPResponse> resendOtpservice = new ApiService<>();
                resendOtpservice.get(OTPVerifyActivity.this, ApiClient.getApiInterface().resendOTP(resendOtpRequest), "ResendOTP");
                Log.e("ResendOTP",""+resendOtpRequest);


                break;


            case R.id.back_arrow_IV:
                onBackPressed();
                break;

            case R.id.verify_BT:
                String otp1, otp2, otp3, otp4, otp5, otp6,otp;
                otp1=editText_one.getText().toString();
                otp2=editText_two.getText().toString();
                otp3=editText_three.getText().toString();
                otp4=editText_four.getText().toString();

                otp = otp1+""+otp2+""+otp3+""+otp4;
//                Toast.makeText(this, otp, Toast.LENGTH_SHORT).show();

                if (otp1.isEmpty()&&otp2.isEmpty()&&otp3.isEmpty()&&otp4.isEmpty()){
                    Log.e("OTP1","OTP"+otp+"==>"+otpString);

                    Toast.makeText(this, "Invalid OTP!", Toast.LENGTH_SHORT).show();

                }else if (otpString.replace("\"", "").equals(otp)){
//                    Log.e("OTP","OTP1"+otp+"==>"+otpString);
//                    Toast.makeText(this, "Invalid OTP!", Toast.LENGTH_SHORT).show();

                    JsonObject sendParentPhone = new JsonObject();
                    sendParentPhone.addProperty("phoneNumber",phoneNumber);
                    JsonObject saveRequest = new JsonObject();
                    saveRequest.add("data",sendParentPhone);

                    methods.showCustomProgressBarDialog(this);
                    ApiService<LoginRegisterResponse> service = new ApiService<>();
                    service.get(OTPVerifyActivity.this, ApiClient.getApiInterface().validatepetParentOtp(saveRequest), "ValidatePetParentOtp");
                    Log.e("ValidatePetParentOtp",""+saveRequest);

                }else {
                    Log.e("OTP","OTP1==>"+otpString);
                    Log.e("OTP","OTP2==>"+otp);

                    Toast.makeText(this, "Invalid OTP!", Toast.LENGTH_SHORT).show();

                }



                break;

        }
    }

    @Override
    public void onResponse(Response arg0, String key) {

        switch (key){
            case "ValidatePetParentOtp":
                try {
                    methods.customProgressDismiss();
                    Log.e("ValidatePetParentOtp",arg0.body().toString());
                    LoginRegisterResponse loginRegisterResponse = (LoginRegisterResponse) arg0.body();
                    int responseCode = Integer.parseInt(String.valueOf(loginRegisterResponse.getResponseLogin().getResponseCode()));
                    if (responseCode==117){
                        if (vetID == null){
                            Intent addUserWithoutPet = new Intent(this,RegisterActivity.class);
                            addUserWithoutPet.putExtra("phoneNumber",phoneNumber);
                            startActivity(addUserWithoutPet);
                        }else {
                            Intent addUserWithPet = new Intent(this,AddPetWithQRCodeActivity.class);
                            addUserWithPet.putExtra("phoneNumber",phoneNumber);
                            addUserWithPet.putExtra("vetID",vetID);
                            startActivity(addUserWithPet);
                        }
                    }else if (responseCode==116){
                        sharedPreferences = OTPVerifyActivity.this.getSharedPreferences("userdetails", 0);
                        login_editor = sharedPreferences.edit();
                        login_editor.putString("encryptedId", loginRegisterResponse.getData().getEncryptedId());
                        login_editor.putString("email", loginRegisterResponse.getData().getEmail());
                        login_editor.putString("userId", loginRegisterResponse.getData().getUserId());
                        login_editor.putString("firstName", loginRegisterResponse.getData().getFirstName());
                        login_editor.putString("lastName", loginRegisterResponse.getData().getLastName());
                        login_editor.putString("phoneNumber", loginRegisterResponse.getData().getPhoneNumber());
                        login_editor.putString("address", loginRegisterResponse.getData().getAddress());
                        login_editor.putString("token", loginRegisterResponse.getResponseLogin().getToken());
                        login_editor.putString("profilePic", loginRegisterResponse.getData().getProfileImageUrl());
                        login_editor.putString("study", loginRegisterResponse.getData().getVetRQualification());
                        login_editor.putString("vetid", loginRegisterResponse.getData().getVetRegistrationNumber());
                        login_editor.putString("onlineAppoint", loginRegisterResponse.getData().getOnlineAppointmentStatus());
                        login_editor.putString("twoFactAuth", loginRegisterResponse.getData().getEnableTwoStepVerification());
                        Config.token = loginRegisterResponse.getResponseLogin().getToken();
                        login_editor.putString("loggedIn", "loggedIn");
                        Log.e("TOKEN",loginRegisterResponse.getResponseLogin().getToken());
                        login_editor.commit();
                        Intent intent = new Intent(this, DashBoardActivity.class);
                        intent.putExtra("from","OTP_ACTIVITY");
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                    }else {
                        Toast.makeText(this, "Please try again", Toast.LENGTH_SHORT).show();
                    }


                }catch (Exception e) {
                    e.printStackTrace();
                }

                break;


            case "ResendOTP":
                try {
                    methods.customProgressDismiss();
                    Log.e("ResendOTP",arg0.body().toString());
                    ResendOTPResponse resendOTPResponse = (ResendOTPResponse) arg0.body();
                    int responseCode = Integer.parseInt(String.valueOf(resendOTPResponse.getResponse().getResponseCode()));
                    if (responseCode==109){
                        editText_one.getText().clear();
                        editText_two.getText().clear();
                        editText_three.getText().clear();
                        editText_four.getText().clear();
                        editText_one.requestFocus();
                        otpString = String.valueOf(resendOTPResponse.getData().getOtp());
                        registerBroadcastReceiver();


                    }else {
                        Toast.makeText(this, "Please try again", Toast.LENGTH_SHORT).show();
                    }


                }catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }

    }

    @Override
    public void onError(Throwable t, String key) {
        Log.e("ERROR",t.getLocalizedMessage());

    }
}