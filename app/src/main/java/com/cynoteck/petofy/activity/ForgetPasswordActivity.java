package com.cynoteck.petofy.activity;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cynoteck.petofy.R;
import com.cynoteck.petofy.api.ApiClient;
import com.cynoteck.petofy.api.ApiResponse;
import com.cynoteck.petofy.api.ApiService;
import com.cynoteck.petofy.parameter.forgetPassRequest.ForgetPassDataParams;
import com.cynoteck.petofy.parameter.forgetPassRequest.ForgetPassRequest;
import com.cynoteck.petofy.response.forgetAndChangePassResponse.PasswordResponse;
import com.cynoteck.petofy.utils.Methods;

public class ForgetPasswordActivity extends AppCompatActivity implements ApiResponse, View.OnClickListener {

    EditText        email_TIET;
    Button          submit_BT;
    String          mailString;
    String          emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Methods         methods;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        methods     = new Methods(this);
        email_TIET  = findViewById(R.id.email_TIET);
        submit_BT   = findViewById(R.id.submitMailBT);
        submit_BT.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submitMailBT:
                mailString = email_TIET.getText().toString().trim();
                if ( mailString.isEmpty()){
                    email_TIET.setError("Email is empty");
                }else if (!mailString.matches(emailPattern)) {
                    email_TIET.setError("Invalid Email");
                }else{
                    email_TIET.setError(null);
                    ForgetPassDataParams forgetPassDataParams = new ForgetPassDataParams();
                    forgetPassDataParams.setEmail(mailString);
                    ForgetPassRequest forgetPassRequest = new ForgetPassRequest();
                    forgetPassRequest.setData(forgetPassDataParams);
                    if(methods.isInternetOn())
                    {
                        forgetPassword(forgetPassRequest);
                    }
                    else
                    {
                        methods.DialogInternet();
                    }

                }

                break;

        }

    }

    private void forgetPassword(ForgetPassRequest forgetPassRequest) {
        methods.showCustomProgressBarDialog(this);
        ApiService<PasswordResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getPasswordResponse(forgetPassRequest), "ForgetPassword");
        Log.e("DATALOG","check1=> "+forgetPassRequest);

    }

    @Override
    public void onResponse(Response response, String key) {
        methods.customProgressDismiss();
        switch (key)
        {
            case "ForgetPassword":

                try {
                    Log.d("DATALOG",response.body().toString());
                    PasswordResponse passwordResponse = (PasswordResponse) response.body();
                    int responseCode = Integer.parseInt(passwordResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        email_TIET.getText().clear();

                        Intent intent = new Intent(this,DashBoardActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
                    }else if (responseCode==614){
                        Toast.makeText(this, passwordResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }

                }
                catch(Exception e) {


                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onError(Throwable t, String key) {
        methods.customProgressDismiss();


    }
}