package com.cynoteck.petofy.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
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
import com.cynoteck.petofy.parameter.loginParameter.LoginRequest;
import com.cynoteck.petofy.parameter.loginParameter.Loginparams;
import com.cynoteck.petofy.response.loginRegisterResponse.LoginWithEmailRegisterResponse;
import com.cynoteck.petofy.utils.Config;
import com.cynoteck.petofy.utils.Methods;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import retrofit2.Response;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.ACCESS_WIFI_STATE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class LoginActivity extends FragmentActivity implements View.OnClickListener, ApiResponse {
    MaterialCardView                login_back_arrow_CV;
    private static final int        PERMISSION_REQUEST_CODE = 200;
    LoginWithEmailRegisterResponse  responseLogin;
    SharedPreferences               sharedPreferences;
    SharedPreferences.Editor        login_editor;
    private TextInputLayout         email_TIL, password_TIL;
    private TextInputEditText       email_TIET, password_TIET;
    private Button                  login_BT;
    private String                  emailString = "", passwordString = "";
    private TextView                signUp_TV, forgetPass_TV,login_with_phone_TV;
    String                          emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Methods                         methods;
    ImageView                       logoVet;
    TelephonyManager                telephonyManager;
    String                          imeiNumber = "", token = "";
//    public static final String channel_id = "channel_id";
//    private static final String channel_name = "channel_name";
//    private static final String channel_desc = "channel_desc";
    private static final String     TAG = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        methods = new Methods(this);
        init();
        if (checkPermission()) {
        } else {
            if (!checkPermission()) {
                requestPermission();
            } else {
                Toast.makeText(this, "Permission already granted.", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private boolean checkPermission() {

        int result  = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_PHONE_STATE);
        int result3 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int result4 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result5 = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_WIFI_STATE);
        int result6 = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_NETWORK_STATE);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED && result3 == PackageManager.PERMISSION_GRANTED && result4 == PackageManager.PERMISSION_GRANTED && result5 == PackageManager.PERMISSION_GRANTED && result6 == PackageManager.PERMISSION_GRANTED;

    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, CAMERA, READ_PHONE_STATE, READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE, ACCESS_WIFI_STATE, ACCESS_NETWORK_STATE}, PERMISSION_REQUEST_CODE);

    }

    private void init() {
        login_back_arrow_CV     = findViewById(R.id.login_back_arrow_CV);
        login_with_phone_TV     = findViewById(R.id.login_with_phone_TV);
        logoVet                 = findViewById(R.id.logoVet);
        email_TIET              = findViewById(R.id.email_TIET);
        password_TIET           = findViewById(R.id.password_TIET);
        email_TIL               = findViewById(R.id.email_TIL);
        password_TIL            = findViewById(R.id.password_TIL);
        login_BT                = findViewById(R.id.login_BT);
        signUp_TV               = findViewById(R.id.signUp_TV);
        forgetPass_TV           = findViewById(R.id.forgetPass_TV);

        signUp_TV.setOnClickListener(this);
        forgetPass_TV.setOnClickListener(this);
        login_BT.setOnClickListener(this);
        login_back_arrow_CV.setOnClickListener(this);
        login_with_phone_TV.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.login_with_phone_TV:
                Intent sendPhoneNumber_intent = new Intent(this, SendPhoneNumber.class);
                startActivity(sendPhoneNumber_intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);

                break;

            case R.id.login_back_arrow_CV:
                if (Config.logoutFromAccount == false) {
                    onBackPressed();
                } else {
                    finishAffinity();
//                    System.exit(0);
                }
                break;

            case R.id.login_BT:
                getDeviceId();
                emailString = email_TIET.getText().toString().trim();
                passwordString = password_TIET.getText().toString().trim();
                if (emailString.isEmpty()) {
                    email_TIL.setError("Email is empty");
                    password_TIL.setError(null);
                } else if (!emailString.matches(emailPattern)) {
                    email_TIL.setError("Invalid Email");
                    password_TIL.setError(null);
                } else if (passwordString.isEmpty()) {
                    email_TIL.setError(null);
                    password_TIL.setError("Password is empty");
                }
                /*else if (imeiNumber.isEmpty()) {
                    if (checkPermission()) {
                        getDeviceId();
                    } else {
                        if (!checkPermission()) {
                            requestPermission();
                        } else {
                            Toast.makeText(this, "Permission already granted.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }*/
                else {
                    email_TIL.setError(null);
                    password_TIL.setError(null);
                    Loginparams loginparams = new Loginparams();
                    LoginRequest data = new LoginRequest();
                    data.setEmail(emailString);
                    data.setDeviceId(imeiNumber);
                    data.setDeviceIp(Config.IpAddress);
                    data.setPassword(passwordString);
                    loginparams.setLoginData(data);
                    if (methods.isInternetOn()) {
                        loginUser(loginparams);
                    } else {
                        methods.DialogInternet();
                    }

                }
                break;

            case R.id.signUp_TV:
                Intent signUP_intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(signUP_intent, 1);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                break;

            case R.id.forgetPass_TV:
                Intent forgetPass_intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(forgetPass_intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);

                break;

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setTitle("Verify your Email");
                builder1.setMessage("Please click on the link that has just been sent to your registered email id to verify your account and continue the registration process.");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });


                AlertDialog alert11 = builder1.create();
                alert11.show();

            }
        }
    }

    private void loginUser(Loginparams loginparams) {
        methods.showCustomProgressBarDialog(this);
        ApiService<LoginWithEmailRegisterResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().loginApi(loginparams), "Login");
        //Log.d"DATALOG", "check1=> " + loginparams);

    }

    @Override
    public void onResponse(Response response, String key) {
        methods.customProgressDismiss();
        switch (key) {
            case "Login":
                try {
                    //Log.d"DATALOG", response.body().toString());
                    responseLogin = (LoginWithEmailRegisterResponse) response.body();
                    int responseCode = Integer.parseInt(responseLogin.getResponseLogin().getResponseCode());
                    if (responseCode == 109) {
                        token = responseLogin.getResponseLogin().getToken();
                        loginSucess();
                    } else if (responseCode == 614) {
                        Toast.makeText(LoginActivity.this, responseLogin.getResponseLogin().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
    private void loginSucess() {
        email_TIET.getText().clear();
        password_TIET.getText().clear();
        sharedPreferences = getSharedPreferences("userDetails", 0);
        login_editor = sharedPreferences.edit();
        login_editor.putString("encryptedId", responseLogin.getData().getEncryptedId());
        login_editor.putString("email", responseLogin.getData().getEmail());
        login_editor.putString("userId", responseLogin.getData().getUserId());
        login_editor.putString("firstName", responseLogin.getData().getFirstName());
        login_editor.putString("lastName", responseLogin.getData().getLastName());
        login_editor.putString("phoneNumber", responseLogin.getData().getPhoneNumber());
        login_editor.putString("address", responseLogin.getData().getAddress());
        login_editor.putString("token", responseLogin.getResponseLogin().getToken());
        login_editor.putString("profilePic", responseLogin.getData().getProfileImageUrl());
        login_editor.putString("study", responseLogin.getData().getVetQualification());
        login_editor.putString("vetid", responseLogin.getData().getVetRegistrationNumber());
        login_editor.putString("onlineAppoint", responseLogin.getData().getOnlineAppointmentStatus());
        login_editor.putString("twoFactAuth", responseLogin.getData().getEnableTwoStepVerification());
        Config.token = responseLogin.getResponseLogin().getToken();
        login_editor.putString("loggedIn", "loggedIn");
        //Log.d"TOKEN",responseLogin.getResponseLogin().getToken());
        login_editor.apply();
        Intent intent = new Intent(this, DashBoardActivity.class);
        intent.putExtra("from","OTP_ACTIVITY");
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);    }



    @Override
    public void onError(Throwable t, String key) {
        methods.customProgressDismiss();
        //Log.d"error", Objects.requireNonNull(t.getMessage()));
        //Log.d"errrrr", Objects.requireNonNull(t.getLocalizedMessage()));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    private void getDeviceId() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                if (telephonyManager != null) {
                    try {
                        imeiNumber = Settings.Secure.getString(
                                this.getContentResolver(),
                                Settings.Secure.ANDROID_ID);
                    } catch (Exception e) {
                        e.printStackTrace();
                        imeiNumber = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
                    }
                }
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1010);
            }
        } else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                if (telephonyManager != null) {
                    imeiNumber = telephonyManager.getDeviceId();
                } else {
                    imeiNumber = Settings.Secure.getString(
                            this.getContentResolver(),
                            Settings.Secure.ANDROID_ID);
                }
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1010);
            }
        }
    }

}
