package com.cynoteck.petofyparents.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.api.ApiClient;
import com.cynoteck.petofyparents.api.ApiResponse;
import com.cynoteck.petofyparents.api.ApiService;
import com.cynoteck.petofyparents.parameter.updatePArentDeatils.UpdateParentDeatilsParams;
import com.cynoteck.petofyparents.parameter.updatePArentDeatils.UpdateParentDetailsRequest;
import com.cynoteck.petofyparents.response.updatepetparentprofile.UpdatePetParentProfile;
import com.cynoteck.petofyparents.utils.Config;
import com.cynoteck.petofyparents.utils.Methods;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Response;

public class UpdateProfileActivity extends AppCompatActivity implements View.OnClickListener, ApiResponse {

    ImageView back_arrow_IV;
    TextInputEditText address_TIET, firstName_TIET, lastName_TIET, email_TIET, number_TIET;
    String firstNameStr = "", lastNameStr = "", emailStr = "", numberStr = "", addressStr = "";
    Button update_BT;
    private TextInputLayout firstname_TIL, lastName_TIL, email_TIL, phoneNumber_TIL, address_TIL;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Methods methods;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor login_editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        methods = new Methods(this);
        back_arrow_IV = findViewById(R.id.back_arrow_IV);
        firstName_TIET = findViewById(R.id.firstName_TIET);
        lastName_TIET = findViewById(R.id.lastName_TIET);
        email_TIET = findViewById(R.id.email_TIET);
        number_TIET = findViewById(R.id.number_TIET);
        firstname_TIL = findViewById(R.id.firstName_TIL);
        lastName_TIL = findViewById(R.id.lastName_TIL);
        email_TIL = findViewById(R.id.email_TIL);
        address_TIL = findViewById(R.id.address_TIL);
        phoneNumber_TIL = findViewById(R.id.number_TIL);
        address_TIET = findViewById(R.id.address_TIET);
        update_BT = findViewById(R.id.update_BT);


        back_arrow_IV.setOnClickListener(this);
        update_BT.setOnClickListener(this);


        firstName_TIET.setText(Config.first_name);
        lastName_TIET.setText(Config.last_name);
        number_TIET.setText(Config.user_phone);
        email_TIET.setText(Config.user_emial);
        address_TIET.setText(Config.user_address);


    }

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
                } else if (!addressStr.isEmpty() && addressStr.length() < 6) {
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
                    Log.e("ValidatePetParentOtp", methods.getRequestJson(updateParentDetailsRequest));
                    Log.e("ValidatePetParentOtp", Config.token);

                }


                break;


        }
    }

    @Override
    public void onResponse(Response response, String key) {
        switch (key) {
            case "UpdateParent":
                try {
                    methods.customProgressDismiss();
                    Log.d("DATALOG", "" + response.body());
                    UpdatePetParentProfile loginRegisterResponse = (UpdatePetParentProfile) response.body();
                    Log.d("DATALOG", "" + loginRegisterResponse.getResponse().getResponseCode());
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
                        login_editor.commit();
                        setResult(RESULT_OK);
                        finish();

                    } else if (responseCode == 615) {
                        Toast.makeText(this, loginRegisterResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("eeeeeee", e.getLocalizedMessage());
                }
                break;

        }

    }

    @Override
    public void onError(Throwable t, String key) {
        methods.customProgressDismiss();
        Toast.makeText(this, "Please try again !", Toast.LENGTH_SHORT).show();
        Log.e("Error", t.getLocalizedMessage());
    }
}