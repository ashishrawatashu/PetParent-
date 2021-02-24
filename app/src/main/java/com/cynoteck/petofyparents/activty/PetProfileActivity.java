package com.cynoteck.petofyparents.activty;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.api.ApiClient;
import com.cynoteck.petofyparents.api.ApiResponse;
import com.cynoteck.petofyparents.api.ApiService;
import com.cynoteck.petofyparents.parameter.getPetListRequest.GetPetListParams;
import com.cynoteck.petofyparents.parameter.getPetListRequest.GetPetListRequest;
import com.cynoteck.petofyparents.response.getPetDetailsResponse.GetPetResponse;
import com.cynoteck.petofyparents.response.loginRegisterResponse.UserPermissionMasterList;
import com.cynoteck.petofyparents.utils.Config;
import com.cynoteck.petofyparents.utils.Methods;
import com.cynoteck.petofyparents.utils.Methods;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Response;

public class PetProfileActivity extends AppCompatActivity implements ApiResponse, View.OnClickListener {
    Methods methods;
    String petId="",imagerl="";
    ImageView pet_profile_image_IV, image_one,image_two,image_three,image_four,image_five,edit_image, back_arrow_pp;;
    TextView pet_name_TV, address_head,pet_sex_TV,pet_parent_TV,pet_id_TV,pet_deatils_TV,phone_one,pet_email_id_TV,phone_two,address_line_one_TV,address_line_two_TV;
    GetPetResponse getPetResponse;
    boolean reloadData=false;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_profile);
        methods = new Methods(this);
        Bundle extras = getIntent().getExtras();
        petId = extras.getString("pet_id");

        pet_profile_image_IV=findViewById(R.id.pet_profile_image_IV);

        pet_name_TV=findViewById(R.id.pet_name_TV);
        pet_sex_TV=findViewById(R.id.pet_sex_TV);
        address_head=findViewById(R.id.address_head);
        pet_parent_TV=findViewById(R.id.pet_parent_TV);
        pet_id_TV=findViewById(R.id.pet_id_TV);
        pet_deatils_TV=findViewById(R.id.pet_deatils_TV);
        phone_one=findViewById(R.id.phone_one);
        pet_email_id_TV=findViewById(R.id.pet_email_id_TV);
        phone_two=findViewById(R.id.phone_two);
        address_line_one_TV=findViewById(R.id.address_line_one_TV);
        address_line_two_TV=findViewById(R.id.address_line_two_TV);
        edit_image=findViewById(R.id.edit_image);
        back_arrow_pp=findViewById(R.id.back_arrow_pp);
        edit_image.setOnClickListener(this);
        back_arrow_pp.setOnClickListener(this);

        GetPetListParams getPetListParams = new GetPetListParams();
        getPetListParams.setId(petId);
        GetPetListRequest getPetListRequest = new GetPetListRequest();
        getPetListRequest.setData(getPetListParams);
        sharedPreferences = getSharedPreferences("userdetails", 0);

        if(methods.isInternetOn())
        {
            getPetlistData(getPetListRequest);
        }
        else
        {
            methods.DialogInternet();
        }

    }

    private void getPetlistData(GetPetListRequest getPetListRequest) {
        reloadData=true;
        methods.showCustomProgressBarDialog(this);
        ApiService<GetPetResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getPetDetails(Config.token,getPetListRequest), "GetPetDetail");
        Log.e("DATALOG","check1=> "+getPetListRequest);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit_image:

                Intent intent=new Intent(this, GetPetDetailsActivity.class);
                intent.putExtra("pet_id",petId);
                intent.putExtra("pet_category",getPetResponse.getData().getPetCategory());
                intent.putExtra("pet_name",getPetResponse.getData().getPetName());
                intent.putExtra("pet_sex",getPetResponse.getData().getPetSex());
                intent.putExtra("pet_DOB",getPetResponse.getData().getDateOfBirth());
                intent.putExtra("pet_age",getPetResponse.getData().getPetAge());
                intent.putExtra("pet_size",getPetResponse.getData().getPetSize());
                intent.putExtra("pet_breed",getPetResponse.getData().getPetBreed());
                intent.putExtra("pet_color",getPetResponse.getData().getPetColor());
                intent.putExtra("pet_parent",getPetResponse.getData().getPetParentName());
                intent.putExtra("pet_parent_contact",getPetResponse.getData().getContactNumber());
                intent.putExtra("image_url",getPetResponse.getData().getPetProfileImageUrl());
                startActivityForResult(intent,1);

                break;
            case R.id.back_arrow_pp:
                onBackPressed();
                break;

        }

    }

    @Override
    public void onResponse(Response arg0, String key) {
        methods.customProgressDismiss();
        reloadData=false;
        switch (key) {
            case "GetPetDetail":
                try {
                    methods.customProgressDismiss();
                    Log.d("GetPetDetail", arg0.body().toString());
                    getPetResponse = (GetPetResponse) arg0.body();
                    int responseCode = Integer.parseInt(getPetResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                       // Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                        pet_name_TV.setText(getPetResponse.getData().getPetName());
                        pet_parent_TV.setText(Config.user_name);
                        phone_one.setText(Config.user_phone);
                        pet_email_id_TV.setText(Config.user_emial);
                        pet_sex_TV.setText(getPetResponse.getData().getPetSex());
                        pet_id_TV.setText(getPetResponse.getData().getPetUniqueId());
                        pet_deatils_TV.setText(getPetResponse.getData().getDescription());
                        if (getPetResponse.getData().getAddress().equals("")){
                            address_line_one_TV.setVisibility(View.GONE);
                            address_head.setVisibility(View.GONE);
                        }else {
                            address_line_one_TV.setText(getPetResponse.getData().getAddress());
                        }
                        setImages();

                    } else if (responseCode == 614) {
                        Toast.makeText(this, getPetResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void setImages() {
        if(!getPetResponse.getData().getPetProfileImageUrl().equals(""))
        {
            Glide.with(this)
                    .load(getPetResponse.getData().getPetProfileImageUrl())
                    .into(pet_profile_image_IV);
        }
//        if (getPetResponse.getData().getFirstServiceImageUrl().isEmpty()){
//            image_one.setVisibility(View.INVISIBLE);
//        }else {
//            Glide.with(this)
//                    .load(getPetResponse.getData().getFirstServiceImageUrl())
//                    .into(image_one);
//        }
//        if (getPetResponse.getData().getSecondServiceImageUrl().isEmpty()){
//            image_two.setVisibility(View.INVISIBLE);
//
//        }else {
//            Glide.with(this)
//                    .load(getPetResponse.getData().getSecondServiceImageUrl())
//                    .into(image_two);
//        }if (getPetResponse.getData().getThirdServiceImageUrl().isEmpty()){
//            image_three.setVisibility(View.INVISIBLE);
//
//        }else {
//            Glide.with(this)
//                    .load(getPetResponse.getData().getThirdServiceImageUrl())
//                    .into(image_three);
//        }if (getPetResponse.getData().getFourthServiceImageUrl().isEmpty()){
//            image_four.setVisibility(View.INVISIBLE);
//
//        }else {
//            Glide.with(this)
//                    .load(getPetResponse.getData().getFourthServiceImageUrl())
//                    .into(image_four);
//        }if (getPetResponse.getData().getFifthServiceImageUrl().isEmpty()){
//            image_five.setVisibility(View.INVISIBLE);
//        }else {
//            Glide.with(this)
//                    .load(getPetResponse.getData().getFifthServiceImageUrl())
//                    .into(image_five);
//        }
    }

    @Override
    public void onError(Throwable t, String key) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(reloadData==false)
        {
            GetPetListParams getPetListParams = new GetPetListParams();
            getPetListParams.setId(petId);
            GetPetListRequest getPetListRequest = new GetPetListRequest();
            getPetListRequest.setData(getPetListParams);
            if(methods.isInternetOn())
            {
                getPetlistData(getPetListRequest);
            }
            else
            {
                methods.DialogInternet();
            }
        }

    }
}
