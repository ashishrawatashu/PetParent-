package com.cynoteck.petofy.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cynoteck.petofy.R;
import com.cynoteck.petofy.api.ApiClient;
import com.cynoteck.petofy.api.ApiResponse;
import com.cynoteck.petofy.api.ApiService;
import com.cynoteck.petofy.parameter.adoptionRequest.AdoptionRequest;
import com.cynoteck.petofy.parameter.adoptionRequest.AdoptionRequestParameter;
import com.cynoteck.petofy.utils.Config;
import com.cynoteck.petofy.utils.Methods;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.JsonObject;

import retrofit2.Response;

public class AdoptionPetDetailsActivity extends AppCompatActivity implements ApiResponse, View.OnClickListener {

    Methods             methods;
    String              from="", PetId = "", petName = "", petGender = "", petAge = "", petBreed = "", petColor = "", image = "", petSize = "", donarName = "", donarPhone = "", donarMail = "", donarAddress = "";
    ImageView           pet_profile_image_IV;
    TextView            pet_name_TV, pet_breed_TV, pet_Color_TV, pet_weight_TV, pet_parent_name_TV, parent_phone_TV, parent_email_TV, parent_address_TV;
    LinearLayout        send_adoption_request_LL;
    MaterialCardView    back_arrow_CV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adoption_pet_details);
        methods = new Methods(this);
        initialize();
    }

    private void initialize() {

        pet_name_TV                 = findViewById(R.id.pet_name_TV);
        pet_breed_TV                = findViewById(R.id.pet_breed_TV);
        pet_Color_TV                = findViewById(R.id.pet_Color_TV);
        pet_weight_TV               = findViewById(R.id.pet_weight_TV);
        pet_parent_name_TV          = findViewById(R.id.pet_parent_name_TV);
        parent_phone_TV             = findViewById(R.id.parent_phone_TV);
        parent_address_TV           = findViewById(R.id.parent_address_TV);
        parent_email_TV             = findViewById(R.id.parent_email_TV);
        pet_profile_image_IV        = findViewById(R.id.pet_profile_image_IV);
        back_arrow_CV               = findViewById(R.id.back_arrow_CV);
        send_adoption_request_LL    = findViewById(R.id.send_adoption_request_LL);

        send_adoption_request_LL.setOnClickListener(this);
        back_arrow_CV.setOnClickListener(this);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            from = extras.getString("from");
            if (from.equals("AdoptionRequestActivity")){
                send_adoption_request_LL.setVisibility(View.GONE);
            }else {
                send_adoption_request_LL.setVisibility(View.VISIBLE);

            }
            image = extras.getString("image");

            Glide.with(this)
                    .load(image)
                    .placeholder(R.drawable.empty_pet_image)
                    .into(pet_profile_image_IV);
            PetId = extras.getString("pet_id");
            petName = extras.getString("pet_name");
            petGender = extras.getString("pet_gender");
            pet_name_TV.setText(petName);
            petAge = extras.getString("pet_age");
            petBreed = extras.getString("pet_breed");
            pet_breed_TV.setText(petBreed);
            petColor = extras.getString("pet_color");
            pet_Color_TV.setText(petColor);
            petSize = extras.getString("pet_size");
            pet_weight_TV.setText(petSize);
            donarName = extras.getString("donar_name");
            pet_parent_name_TV.setText(donarName);
            donarPhone = extras.getString("donar_phone");
            parent_phone_TV.setText(donarPhone);
            donarMail = extras.getString("donar_mail");
            parent_email_TV.setText(donarMail);
            donarAddress = extras.getString("donar_address");
            parent_address_TV.setText(donarAddress);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrow_CV:
                onBackPressed();
                break;

            case R.id.send_adoption_request_LL:
                if (methods.isInternetOn())
                    adoptionRequest();
                else
                    methods.isInternetOn();
                break;
        }

    }

    public void adoptionRequest() {
        methods.showCustomProgressBarDialog(this);
        AdoptionRequestParameter adoptionRequestParameter = new AdoptionRequestParameter();
        adoptionRequestParameter.setId(PetId.substring(0, PetId.length() - 2));

        AdoptionRequest adoptionRequest = new AdoptionRequest();
        adoptionRequest.setData(adoptionRequestParameter);

        ApiService<JsonObject> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().sendAdoptionRequest(Config.token, adoptionRequest), "sendAdoptionRequest");
        Log.e("DIOLOG====>", "" + adoptionRequest);
    }

    @Override
    public void onResponse(Response arg0, String key) {
        switch (key) {
            case "sendAdoptionRequest":
                try {
                    methods.customProgressDismiss();
                    JsonObject adoptionResponse = (JsonObject) arg0.body();
                    Log.d("sendAdoptionRequest", adoptionResponse.toString());
                    JsonObject response = adoptionResponse.getAsJsonObject("response");
                    Log.d("hhshshhs", "" + response);

                    int responseCode = Integer.parseInt(String.valueOf(response.get("responseCode")));
                    if (responseCode == 109) {
                        methods.customProgressDismiss();
                        Toast.makeText(this, "Request Send Successfully..", Toast.LENGTH_SHORT).show();
                    } else {
                        methods.customProgressDismiss();
                        Toast.makeText(this, "Try Again!!", Toast.LENGTH_SHORT).show();
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