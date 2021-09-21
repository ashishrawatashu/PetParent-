package com.cynoteck.petofy.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cynoteck.petofy.R;
import com.cynoteck.petofy.api.ApiClient;
import com.cynoteck.petofy.api.ApiResponse;
import com.cynoteck.petofy.api.ApiService;
import com.cynoteck.petofy.parameter.updatePArentDeatils.UpdateParentDeatilsParams;
import com.cynoteck.petofy.parameter.updatePArentDeatils.UpdateParentDetailsRequest;
import com.cynoteck.petofy.response.loginResponse.LoginRegisterResponse;
import com.cynoteck.petofy.response.resendOTPResposne.ResendOTPResponse;
import com.cynoteck.petofy.response.updatepetparentprofile.UpdatePetParentProfile;
import com.cynoteck.petofy.utils.Config;
import com.cynoteck.petofy.utils.Methods;
import com.cynoteck.petofy.utils.SmsBroadcastReceiver;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Response;

public class UpdateProfileActivity extends AppCompatActivity implements View.OnClickListener, ApiResponse, TextWatcher {

    ImageView                   back_arrow_IV;
    TextInputEditText           address_TIET, firstName_TIET, lastName_TIET, email_TIET, number_TIET;
    String                      firstNameStr = "", lastNameStr = "", emailStr = "", numberStr = "", addressStr = "";
    Button                      update_BT;
    private TextInputLayout     firstname_TIL, lastName_TIL, email_TIL, phoneNumber_TIL, address_TIL;
    String                      emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Methods                     methods;
    SharedPreferences           sharedPreferences;
    SharedPreferences.Editor    login_editor;
    BottomSheetDialog           otp_verification_dialog;
    Button                      submit_otp_BT;
    EditText                    editText_one, editText_two, editText_three, editText_four ;
    TextView                    we_send_sms_TV;

    SmsBroadcastReceiver        smsBroadcastReceiver;
    private static final int    REQ_USER_CONSENT = 200;
    String                      otpString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        methods = new Methods(this);
        initView();
        startSmsUserConsent();

        back_arrow_IV.setOnClickListener(this);
        update_BT.setOnClickListener(this);

//        number_TIET.setEnabled(false);
        email_TIET.setEnabled(false);


        firstName_TIET.setText(Config.first_name);
        lastName_TIET.setText(Config.last_name);
        number_TIET.setText(Config.user_phone);
        email_TIET.setText(Config.user_emial);
        address_TIET.setText(Config.user_address);


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

    private void initView() {
        back_arrow_IV           = findViewById(R.id.back_arrow_IV);
        firstName_TIET          = findViewById(R.id.firstName_TIET);
        lastName_TIET           = findViewById(R.id.lastName_TIET);
        email_TIET              = findViewById(R.id.email_TIET);
        number_TIET             = findViewById(R.id.number_TIET);
        firstname_TIL           = findViewById(R.id.firstName_TIL);
        lastName_TIL            = findViewById(R.id.lastName_TIL);
        email_TIL               = findViewById(R.id.email_TIL);
        address_TIL             = findViewById(R.id.address_TIL);
        phoneNumber_TIL         = findViewById(R.id.number_TIL);
        address_TIET            = findViewById(R.id.address_TIET);
        update_BT               = findViewById(R.id.update_BT);


    }

    private void showOtpDialog() {
        otp_verification_dialog = new BottomSheetDialog(this);
        otp_verification_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        otp_verification_dialog.setCancelable(true);
        otp_verification_dialog.setCanceledOnTouchOutside(false);
        otp_verification_dialog.setContentView(R.layout.buttom_sheet_otp_layout);

        we_send_sms_TV  = otp_verification_dialog.findViewById(R.id.we_send_sms_TV);
        editText_one    = otp_verification_dialog.findViewById(R.id.editTextone);
        editText_two    = otp_verification_dialog.findViewById(R.id.editTexttwo);
        editText_three  = otp_verification_dialog.findViewById(R.id.editTextthree);
        editText_four   = otp_verification_dialog.findViewById(R.id.editTextfour);
        submit_otp_BT   = otp_verification_dialog.findViewById(R.id.submit_otp_BT);

        submit_otp_BT.setOnClickListener(this);
        we_send_sms_TV.setText("We sent 4-digit code to +91"+numberStr);

        otp_verification_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        otp_verification_dialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = otp_verification_dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);


    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrow_IV:

                onBackPressed();
                break;

            case R.id.update_BT:
                firstNameStr = firstName_TIET.getText().toString().trim();
                lastNameStr = lastName_TIET.getText().toString().trim();
                emailStr = email_TIET.getText().toString().trim();
                numberStr = number_TIET.getText().toString().trim();
                addressStr = address_TIET.getText().toString().trim();

                if (firstNameStr.isEmpty()) {
                    firstName_TIET.setError("Name is empty");
                    lastName_TIL.setError(null);
                    email_TIL.setError(null);
                    phoneNumber_TIL.setError(null);
                    address_TIL.setError(null);
                } else if (lastNameStr.isEmpty()) {
                    lastName_TIL.setError("Last Name is empty");
                    firstname_TIL.setError(null);
                    email_TIL.setError(null);
                    phoneNumber_TIL.setError(null);
                    address_TIL.setError(null);
                } else if (emailStr.isEmpty()) {
                    lastName_TIL.setError(null);
                    firstname_TIL.setError(null);
                    email_TIL.setError("Enter email");
                    phoneNumber_TIL.setError(null);
                    address_TIL.setError(null);
                } else if (!emailStr.isEmpty() && !emailStr.matches(emailPattern)) {
                    email_TIL.setError("Invalid Email");
                    firstname_TIL.setError(null);
                    lastName_TIL.setError(null);
                    phoneNumber_TIL.setError(null);
                    address_TIL.setError(null);
                } else if (numberStr.isEmpty()) {
                    phoneNumber_TIL.setError("Phone Number is empty");
                    firstname_TIL.setError(null);
                    lastName_TIL.setError(null);
                    email_TIL.setError(null);
                    address_TIL.setError(null);
                } else if (numberStr.length() < 10) {
                    phoneNumber_TIL.setError("Invalid Number !");
                    firstname_TIL.setError(null);
                    lastName_TIL.setError(null);
                    email_TIL.setError(null);
                    address_TIL.setError(null);
                }  else if (!addressStr.isEmpty() && addressStr.length() < 6) {
                    phoneNumber_TIL.setError(null);
                    firstname_TIL.setError(null);
                    lastName_TIL.setError(null);
                    email_TIL.setError(null);
                    address_TIL.setError("Address to short !");
                } else {
                    phoneNumber_TIL.setError(null);
                    firstname_TIL.setError(null);
                    lastName_TIL.setError(null);
                    email_TIL.setError(null);
                    address_TIL.setError(null);
                    if (!Config.user_phone.equals(numberStr)){
                        showOtpDialog();
                        sendSmsToParentNumber(numberStr);
                    }else {
                        updateParentProfile();
                    }


                }


                break;

            case R.id.submit_otp_BT:

                String otp1, otp2, otp3, otp4, otp5, otp6,otp;
                otp1=editText_one.getText().toString();
                otp2=editText_two.getText().toString();
                otp3=editText_three.getText().toString();
                otp4=editText_four.getText().toString();

                otp = otp1+""+otp2+""+otp3+""+otp4;
                if (otp1.isEmpty()&&otp2.isEmpty()&&otp3.isEmpty()&&otp4.isEmpty()){
                    Log.e("OTP1","OTP"+otp+"==>"+otpString);

                    Toast.makeText(this, "Invalid OTP!", Toast.LENGTH_SHORT).show();

                }else if (otpString.replace("\"", "").equals(otp)){
                    if (methods.isInternetOn()){
                        otp_verification_dialog.dismiss();
                        updateParentProfile();
                    }else {
                        methods.DialogInternet();
                    }


                }else {
                    Log.e("OTP","OTP1==>"+otpString);
                    Log.e("OTP","OTP2==>"+otp);
                    Toast.makeText(this, "Invalid OTP!", Toast.LENGTH_SHORT).show();

                }
                break;


        }
    }

    private void updateParentProfile() {
        UpdateParentDeatilsParams updateParentDeatilsParams = new UpdateParentDeatilsParams();
        updateParentDeatilsParams.setuserId(Config.user_id);
        updateParentDeatilsParams.setFirstName(firstNameStr);
        updateParentDeatilsParams.setLastName(lastNameStr);
        updateParentDeatilsParams.setEmail(emailStr.trim());
        updateParentDeatilsParams.setPhoneNumber(numberStr);
        updateParentDeatilsParams.setAddress(addressStr);

        UpdateParentDetailsRequest updateParentDetailsRequest = new UpdateParentDetailsRequest();
        updateParentDetailsRequest.setData(updateParentDeatilsParams);

        methods.showCustomProgressBarDialog(this);
        ApiService<UpdatePetParentProfile> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().updatePetParent(Config.token, updateParentDetailsRequest), "UpdateParent");
        //Log.d"ValidatePetParentOtp", methods.getRequestJson(updateParentDetailsRequest));
        //Log.d"ValidatePetParentOtp", Config.token);

    }

    private void sendSmsToParentNumber(String phoneNumber) {
        JsonObject sendParentPhone = new JsonObject();
        sendParentPhone.addProperty("phoneNumber",phoneNumber);
        JsonObject saveRequest = new JsonObject();
        saveRequest.add("data",sendParentPhone);
        if (methods.isInternetOn()){
            methods.showCustomProgressBarDialog(this);
            ApiService<JsonObject> service = new ApiService<>();
            service.get(UpdateProfileActivity.this, ApiClient.getApiInterface().sendRegistrationOtp(saveRequest), "SendRegistrationOtp");
            //Log.d"SendRegistrationOtp",""+saveRequest);
        }else {
            methods.DialogInternet();
        }

    }

    @Override
    public void onResponse(Response response, String key) {
        switch (key) {
            case "SendRegistrationOtp":
                try {
                    methods.customProgressDismiss();
                    Log.d("SendRegistrationOtp",response.body().toString());
                    JsonObject sendRegistrationOtp = (JsonObject) response.body();
                    JsonObject sendRegistrationOtpResponse = sendRegistrationOtp.getAsJsonObject("response");
                    int responseCode = Integer.parseInt(String.valueOf(sendRegistrationOtpResponse.get("responseCode")));
                    if (responseCode==109){
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                        JsonObject data = sendRegistrationOtp.getAsJsonObject("data");
                        otpString = String.valueOf(data.get("otp"));
//
                    }else {
                        Toast.makeText(this, "Please try again !", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;


            case "UpdateParent":
                try {
                    methods.customProgressDismiss();
                    //Log.d"DATALOG", "" + response.body());
                    UpdatePetParentProfile loginRegisterResponse = (UpdatePetParentProfile) response.body();
                    //Log.d"DATALOG", "" + loginRegisterResponse.getResponse().getResponseCode());
                    int responseCode = Integer.parseInt(loginRegisterResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        Config.first_name = loginRegisterResponse.getData().getFirstName();
                        Config.last_name = loginRegisterResponse.getData().getLastName();
                        Config.user_phone = loginRegisterResponse.getData().getPhoneNumber();
                        Config.user_address = loginRegisterResponse.getData().getAddress();
                        Config.user_emial = loginRegisterResponse.getData().getEmail();
                        Toast.makeText(this, "Update Successfully", Toast.LENGTH_SHORT).show();
                        sharedPreferences = this.getSharedPreferences("userDetails", 0);
                        login_editor = sharedPreferences.edit();
                        login_editor.putString("email", loginRegisterResponse.getData().getEmail());
                        login_editor.putString("firstName", loginRegisterResponse.getData().getFirstName());
                        login_editor.putString("lastName", loginRegisterResponse.getData().getLastName());
                        login_editor.putString("phoneNumber", loginRegisterResponse.getData().getPhoneNumber());
                        login_editor.putString("address", loginRegisterResponse.getData().getAddress());
                        login_editor.apply();
                        Config.first_name                       = sharedPreferences.getString("firstName", "");
                        Config.last_name                        = sharedPreferences.getString("lastName", "");
                        setResult(RESULT_OK);
                        finish();

                    } else if (responseCode == 615) {
                        Toast.makeText(this, loginRegisterResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    //Log.d"eeeeeee", e.getLocalizedMessage());
                }
                break;

            case "ResendOTP":
                try {
                    methods.customProgressDismiss();
                    Log.e("ResendOTP",response.body().toString());
                    ResendOTPResponse resendOTPResponse = (ResendOTPResponse) response.body();
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
        methods.customProgressDismiss();
        Toast.makeText(this, "Please try again !", Toast.LENGTH_SHORT).show();
        //Log.d"Error", t.getLocalizedMessage());
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
}