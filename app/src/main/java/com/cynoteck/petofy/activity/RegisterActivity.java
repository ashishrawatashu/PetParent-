package com.cynoteck.petofy.activity;

import androidx.fragment.app.FragmentActivity;

import retrofit2.Response;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cynoteck.petofy.R;
import com.cynoteck.petofy.api.ApiClient;
import com.cynoteck.petofy.api.ApiResponse;
import com.cynoteck.petofy.api.ApiService;
import com.cynoteck.petofy.parameter.registerRequest.RegisterRequest;
import com.cynoteck.petofy.parameter.registerRequest.Registerparams;
import com.cynoteck.petofy.response.loginResponse.LoginRegisterResponse;
import com.cynoteck.petofy.utils.Config;
import com.cynoteck.petofy.utils.Methods;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends FragmentActivity implements ApiResponse, View.OnClickListener {

    Methods                         methods;
    ImageView                       back_arrow_IV;
    private TextInputLayout         firstname_TIL, lastName_TIL, email_TIL, phoneNumber_TIL, password_TIL, confirmPassword_TIL;
    private TextInputEditText       firstname_TIET, lastName_TIET, email_TIET, phoneNumber_TIET, password_TIET, confirmPassword_TIET;
    private String                  phoneNumber = "";
    String                          emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    SharedPreferences               sharedPreferences;
    SharedPreferences.Editor        login_editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        methods         = new Methods(this);
        Intent intent   = getIntent();
        phoneNumber     = intent.getStringExtra("phoneNumber");
        initView();

    }

    private void initView() {
        firstname_TIL           = findViewById(R.id.firstName_TIL);
        lastName_TIL            = findViewById(R.id.lastName_TIL);
        email_TIL               = findViewById(R.id.email_TIL);
        phoneNumber_TIL         = findViewById(R.id.number_TIL);
        password_TIL            = findViewById(R.id.password_TIL);
        confirmPassword_TIL     = findViewById(R.id.cPassword_TIL);
        back_arrow_IV           = findViewById(R.id.back_arrow_IV);


        firstname_TIET          = findViewById(R.id.firstName_TIET);
        lastName_TIET           = findViewById(R.id.lastName_TIET);
        email_TIET              = findViewById(R.id.email_TIET);
        phoneNumber_TIET        = findViewById(R.id.number_TIET);
        password_TIET           = findViewById(R.id.password_TIET);
        confirmPassword_TIET    = findViewById(R.id.cPassword_TIET);

        Button signUp_BT = findViewById(R.id.signUp_BT);
        phoneNumber_TIET.setEnabled(false);
        signUp_BT.setOnClickListener(this);
        back_arrow_IV.setOnClickListener(this);
        phoneNumber_TIET.setText(phoneNumber);


    }

    private void registerUser(Registerparams registerparams) {
        methods.showCustomProgressBarDialog(this);
        ApiService<LoginRegisterResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().registerApi(registerparams), "Register");
        Log.d("DATALOG", "check1=> " + methods.getRequestJson(registerparams));
    }

    @Override
    public void onResponse(Response response, String key) {
        methods.customProgressDismiss();
        switch (key) {
            case "Register":
                try {
                    //Log.d"DATALOG", "" + response.body().toString());
                    LoginRegisterResponse loginRegisterResponse = (LoginRegisterResponse) response.body();
                    int responseCode = Integer.parseInt(loginRegisterResponse.getResponseLogin().getResponseCode());
                    if (responseCode == 109) {

                        sharedPreferences = this.getSharedPreferences("userDetails", 0);
                        login_editor = sharedPreferences.edit();
                        login_editor.putString("email", loginRegisterResponse.getData().getEmail());
                        login_editor.putString("userId", loginRegisterResponse.getData().getUserId());
                        login_editor.putString("firstName", loginRegisterResponse.getData().getFirstName());
                        login_editor.putString("lastName", loginRegisterResponse.getData().getLastName());
                        login_editor.putString("phoneNumber", loginRegisterResponse.getData().getPhoneNumber());
                        login_editor.putString("address", loginRegisterResponse.getData().getAddress());
                        login_editor.putString("token", loginRegisterResponse.getResponseLogin().getToken());
                        login_editor.putString("profilePic", loginRegisterResponse.getData().getProfileImageUrl());
                        login_editor.putString("study", loginRegisterResponse.getData().getVetRQualification());
                        login_editor.putString("onlineAppoint", loginRegisterResponse.getData().getOnlineAppointmentStatus());
                        login_editor.putString("twoFactAuth", loginRegisterResponse.getData().getEnableTwoStepVerification());
                        Config.token = loginRegisterResponse.getResponseLogin().getToken();
                        login_editor.putString("loggedIn", "loggedIn");
                        //Log.d"TOKEN", loginRegisterResponse.getResponseLogin().getToken());
                        login_editor.apply();
                        Intent intent = new Intent(this, DashBoardActivity.class);
                        intent.putExtra("from", "REGISTER");
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                        Toast.makeText(this, "Welcome to Petofy", Toast.LENGTH_SHORT).show();
                    } else if (responseCode == 615) {
                        Toast.makeText(this, loginRegisterResponse.getResponseLogin().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    //Log.d"eeeeeee", e.getLocalizedMessage());
                }
                break;
        }
    }

    @Override
    public void onError(Throwable t, String key) {
        methods.customProgressDismiss();
        //Log.d"ERROR", t.getLocalizedMessage());

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.back_arrow_IV:
                onBackPressed();
                break;

            case R.id.signUp_BT:
                String firstName        = firstname_TIET.getText().toString().trim();
                String lastName         = lastName_TIET.getText().toString().trim();
                String email            = email_TIET.getText().toString().trim();
                String password         = password_TIET.getText().toString().trim();
                String confirmPassword  = confirmPassword_TIET.getText().toString().trim();
                phoneNumber             = phoneNumber_TIET.getText().toString().trim();

                if (firstName.isEmpty()) {
                    firstname_TIL.setError("Name is empty");
                    lastName_TIL.setError(null);
                    email_TIL.setError(null);
                    phoneNumber_TIL.setError(null);
                    password_TIL.setError(null);
                    confirmPassword_TIL.setError(null);
                } else if (lastName.isEmpty()) {
                    lastName_TIL.setError("Last Name is empty");
                    firstname_TIL.setError(null);
                    email_TIL.setError(null);
                    phoneNumber_TIL.setError(null);
                    password_TIL.setError(null);
                    confirmPassword_TIL.setError(null);
                } else if (!email.isEmpty() && !email.matches(emailPattern)) {
                    email_TIL.setError("Invalid Email");
                    firstname_TIL.setError(null);
                    lastName_TIL.setError(null);
                    phoneNumber_TIL.setError(null);
                    password_TIL.setError(null);
                    confirmPassword_TIL.setError(null);

                }else if (phoneNumber.isEmpty()){
                    phoneNumber_TIL.setError("Phone Number is empty");
                    firstname_TIL.setError(null);
                    lastName_TIL.setError(null);
                    email_TIL.setError(null);
                    password_TIL.setError(null);
                    confirmPassword_TIL.setError(null);
                }
//                else if (password.isEmpty()){
//                    password_TIL.setError("Password is empty");
//                    firstname_TIL.setError(null);
//                    lastName_TIL.setError(null);
//                    email_TIL.setError(null);
//                    phoneNumber_TIL.setError(null);
//                    confirmPassword_TIL.setError(null);
//                }else if (confirmPassword.isEmpty()){
//                    confirmPassword_TIL.setError("Password is empty");
//                    firstname_TIL.setError(null);
//                    lastName_TIL.setError(null);
//                    email_TIL.setError(null);
//                    phoneNumber_TIL.setError(null);
//                    password_TIL.setError(null);
//                }else if (!password_TIET.getText().toString().equals(confirmPassword_TIET.getText().toString())){
//                    confirmPassword_TIL.setError("Password is not matched ");
//                    firstname_TIL.setError(null);
//                    lastName_TIL.setError(null);
//                    email_TIL.setError(null);
//                    phoneNumber_TIL.setError(null);
//                    password_TIL.setError(null);
//                }

                else {
                    firstname_TIL.setError(null);
                    lastName_TIL.setError(null);
                    email_TIL.setError(null);
                    phoneNumber_TIL.setError(null);
                    password_TIL.setError(null);
                    confirmPassword_TIL.setError(null);
                    Registerparams registerparams = new Registerparams();
                    RegisterRequest data = new RegisterRequest();
                    if (!email.isEmpty()) {
                        data.setEmail(email);
                    }
                    data.setPassword(password);
                    data.setConfirmPassword(confirmPassword);
                    data.setFirstName(firstName);
                    data.setLastName(lastName);
                    data.setPhoneNumber(phoneNumber);
                    data.setRoleName("Customer");
                    registerparams.setData(data);
                    if (methods.isInternetOn()) {
                        registerUser(registerparams);
                    } else {
                        methods.DialogInternet();
                    }


                }

                break;


        }

    }
}