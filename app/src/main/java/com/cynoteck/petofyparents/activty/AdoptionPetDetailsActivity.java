package com.cynoteck.petofyparents.activty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.adapter.AdoptionListAdopter;
import com.cynoteck.petofyparents.api.ApiClient;
import com.cynoteck.petofyparents.api.ApiResponse;
import com.cynoteck.petofyparents.api.ApiService;
import com.cynoteck.petofyparents.parameter.adoptionRequest.AdoptionRequest;
import com.cynoteck.petofyparents.parameter.adoptionRequest.AdoptionRequestParameter;
import com.cynoteck.petofyparents.response.adoptionListResponse.AdoptionListResponse;
import com.cynoteck.petofyparents.response.adoptionResponse.AdoptionResponse;
import com.cynoteck.petofyparents.response.donationResponse.PetImageList;
import com.cynoteck.petofyparents.utils.Config;
import com.cynoteck.petofyparents.utils.Methods;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class AdoptionPetDetailsActivity extends AppCompatActivity implements ApiResponse, View.OnClickListener {

    Methods methods;
    String PetId="",petName="",petGender="",petAge="",petBreed="",petColor="",image="",
            petSize="",donarName="",donarPhone="",donarMail="",donarAddress="";

    RecyclerView images;
    ImageView fronImage;
    TextView petNameGender,updateDetails,petAgeTV,petBreedTV,petColorTV,petSizeTV,donarNameTV,donarContactTV,donrEmailTV,donarAdressTV;
    Button sendRequestTV,cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adoption_pet_details);
        methods=new Methods(this);
        initialize();
    }

    private void initialize()
    {

        images=findViewById(R.id.images);
        fronImage=findViewById(R.id.fronImage);
        petNameGender=findViewById(R.id.petNameGender);
        updateDetails=findViewById(R.id.updateDetails);
        petAgeTV=findViewById(R.id.petAge);
        petBreedTV=findViewById(R.id.petBreed);
        petColorTV=findViewById(R.id.petColor);
        petSizeTV=findViewById(R.id.petSize);
        donarNameTV=findViewById(R.id.donarName);
        donarContactTV=findViewById(R.id.donarContact);
        donrEmailTV=findViewById(R.id.donrEmail);
        donarAdressTV=findViewById(R.id.donarAdress);

        sendRequestTV=findViewById(R.id.sendRequestTV);
        cancel=findViewById(R.id.cancel);

        sendRequestTV.setOnClickListener(this);
        cancel.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();

        if(extras!=null)
        {

            image = extras.getString("image");

            Glide.with(this)
                    .load(image)
                    .placeholder(R.drawable.pet_image)
                    .into(fronImage);
            PetId=extras.getString("pet_id");
            petName=extras.getString("pet_name");
            petGender=extras.getString("pet_gender");
            petNameGender.setText(petName+" ( "+petGender+" ) ");
            petAge=extras.getString("pet_age");
            petAgeTV.setText(petAge);
            petBreed=extras.getString("pet_breed");
            petBreedTV.setText(petBreed);
            petColor=extras.getString("pet_color");
            petColorTV.setText(petColor);
            petSize=extras.getString("pet_size");
            petSizeTV.setText(petSize);
            donarName=extras.getString("donar_name");
            donarNameTV.setText(donarName);
            donarPhone=extras.getString("donar_phone");
            donarContactTV.setText(donarPhone);
            donarMail=extras.getString("donar_mail");
            donarAddress=extras.getString("donar_address");
            donarAdressTV.setText(donarAddress);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.cancel:
                onBackPressed();
                break;

            case R.id.sendRequestTV:
                if(methods.isInternetOn())
                    adoptionRequest();
                else
                    methods.isInternetOn();
                break;
        }

    }

    public void adoptionRequest()
    {
        methods.showCustomProgressBarDialog(this);
        AdoptionRequestParameter adoptionRequestParameter=new AdoptionRequestParameter();
        adoptionRequestParameter.setId(PetId.substring(0,PetId.length()-2));

        AdoptionRequest adoptionRequest=new AdoptionRequest();
        adoptionRequest.setData(adoptionRequestParameter);

        ApiService<JsonObject> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().sendAdoptionRequest(Config.token,adoptionRequest),"sendAdoptionRequest");
        Log.e("DIOLOG====>",""+adoptionRequest);
    }

    @Override
    public void onResponse(Response arg0, String key) {
        switch (key)
        {
            case "sendAdoptionRequest":
                try {
                    methods.customProgressDismiss();
                    JsonObject adoptionResponse = (JsonObject) arg0.body();
                    Log.d("sendAdoptionRequest",adoptionResponse.toString());
                    JsonObject response = adoptionResponse.getAsJsonObject("response");
                    Log.d("hhshshhs",""+response);

                    int responseCode = Integer.parseInt(String.valueOf(response.get("responseCode")));
                    if(responseCode==109)
                    {
                        methods.customProgressDismiss();
                        Toast.makeText(this, "Request Send Successfully..", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        methods.customProgressDismiss();
                        Toast.makeText(this, "Try Again!!", Toast.LENGTH_SHORT).show();
                    }

                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    @Override
    public void onError(Throwable t, String key) { }

}