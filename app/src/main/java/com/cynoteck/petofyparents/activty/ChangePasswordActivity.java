package com.cynoteck.petofyparents.activty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.api.ApiClient;
import com.cynoteck.petofyparents.api.ApiResponse;
import com.cynoteck.petofyparents.api.ApiService;
import com.cynoteck.petofyparents.parameter.changePassRequest.ChangePassParams;
import com.cynoteck.petofyparents.parameter.changePassRequest.ChangePassRequest;
import com.cynoteck.petofyparents.response.forgetAndChangePassResponse.PasswordResponse;
import com.cynoteck.petofyparents.utils.Config;
import com.cynoteck.petofyparents.utils.Methods;

import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity implements ApiResponse, View.OnClickListener {
    EditText current_password_ET ,new_password_ET ,confirm_password_ET;
    Button change_password_BT;
    Methods methods;
    String current_password="", new_password="", confirm_password="";
    ImageView back_arrow_IV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        methods = new Methods(this);

        current_password_ET = findViewById(R.id.current_password_ET);
        new_password_ET = findViewById(R.id.new_password_ET);
        back_arrow_IV=findViewById(R.id.back_arrow_IV);
        confirm_password_ET = findViewById(R.id.confirm_password_ET);
        change_password_BT = findViewById(R.id.change_password_BT);
        change_password_BT.setOnClickListener(this);
        back_arrow_IV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.change_password_BT:
                current_password = current_password_ET.getText().toString().trim();
                new_password = new_password_ET.getText().toString().trim();
                confirm_password = confirm_password_ET.getText().toString().trim();

                if (current_password.isEmpty()){
                    current_password_ET.setError("Empty");
                    current_password_ET.setError(null);
                    confirm_password_ET.setError(null);

                }else if (new_password.isEmpty()){
                    current_password_ET.setError(null);
                    new_password_ET.setError("Empty");
                    confirm_password_ET.setError(null);

                }else if (new_password.length()<6){
                    current_password_ET.setError(null);
                    new_password_ET.setError("Password length to short!");
                    confirm_password_ET.setError(null);

                }else if (confirm_password.isEmpty()){
                    current_password_ET.setError(null);
                    current_password_ET.setError(null);
                    confirm_password_ET.setError("Empty");

                }else if (!new_password_ET.getText().toString().equals(confirm_password_ET.getText().toString())){
                    current_password_ET.setError(null);
                    current_password_ET.setError(null);
                    confirm_password_ET.setError("Password do not match!");
                }else {
                    current_password_ET.setError(null);
                    current_password_ET.setError(null);
                    confirm_password_ET.setError(null);

                    ChangePassParams changePassParams = new ChangePassParams();
                    changePassParams.setOldPassword(current_password);
                    changePassParams.setNewPassword(new_password);
                    changePassParams.setConfirmPassword(confirm_password);
                    ChangePassRequest changePassRequest = new ChangePassRequest();
                    changePassRequest.setData(changePassParams);
                    if(methods.isInternetOn())
                    {
                        chnagePassword(changePassRequest);
                    }
                    else
                    {
                        methods.DialogInternet();
                    }



                }

                break;

            case R.id.back_arrow_IV:
                onBackPressed();
                break;
        }

    }

    private void chnagePassword(ChangePassRequest changePassRequest) {
        methods.showCustomProgressBarDialog(this);
        ApiService<PasswordResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getPasswordResponse(Config.token,changePassRequest), "ChangePassword");
        Log.e("DATALOG","check1=> "+changePassRequest);

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
                        current_password_ET.getText().clear();
                        confirm_password_ET.getText().clear();
                        new_password_ET.getText().clear();

                        Intent intent = new Intent(this,LoginActivity.class);
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

    }
}