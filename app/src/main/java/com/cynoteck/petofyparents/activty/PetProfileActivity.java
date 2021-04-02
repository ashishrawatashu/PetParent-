package com.cynoteck.petofyparents.activty;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.facebook.shimmer.ShimmerFrameLayout;

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
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.card.MaterialCardView;
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
    TextView pet_name_TV,pet_dob_TV,pet_reg__id_TV,pet_breed_TV,pet_gender_TV,pet_parent_name_TV,parent_phone_TV,parent_address_TV;
    GetPetResponse getPetResponse;
    boolean reloadData=false;
    Button add_Clinic_BT;
    ShimmerFrameLayout pet_profile_shimmer;
    String permissionId="";
//    ConstraintLayout pet_profile_details_CL;
MaterialCardView back_arrow_CV,image_edit_CV;
    SharedPreferences sharedPreferences;
    RelativeLayout edit_profile_RL;
    ScrollView pet_full_details_SV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_profile_activity);
        methods = new Methods(this);
        Bundle extras = getIntent().getExtras();
        petId = extras.getString("pet_id");
        pet_full_details_SV=findViewById(R.id.pet_full_details_SV);
        back_arrow_CV = findViewById(R.id.back_arrow_CV);

        pet_profile_image_IV=findViewById(R.id.pet_profile_image_IV);
        image_edit_CV=findViewById(R.id.image_edit_CV);


        pet_name_TV=findViewById(R.id.pet_name_TV);
        pet_dob_TV=findViewById(R.id.pet_dob_TV);
        pet_reg__id_TV=findViewById(R.id.pet_reg__id_TV);
        pet_breed_TV=findViewById(R.id.pet_breed_TV);
        pet_gender_TV=findViewById(R.id.pet_gender_TV);
        pet_parent_name_TV=findViewById(R.id.pet_parent_name_TV);
        parent_phone_TV=findViewById(R.id.parent_phone_TV);
        parent_address_TV=findViewById(R.id.parent_address_TV);
        add_Clinic_BT=findViewById(R.id.add_Clinic_BT);
//        pet_profile_details_CL=findViewById(R.id.pet_profile_details_CL);
        pet_profile_shimmer=findViewById(R.id.pet_profile_shimmer);


        edit_profile_RL=findViewById(R.id.edit_profile_RL);

        edit_profile_RL.setOnClickListener(this);
        add_Clinic_BT.setOnClickListener(this);

        edit_profile_RL.setOnClickListener(this);
        add_Clinic_BT.setOnClickListener(this);
        back_arrow_CV.setOnClickListener(this);


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
        add_Clinic_BT.setEnabled(false);
        pet_full_details_SV.setVisibility(View.GONE);
        edit_profile_RL.setVisibility(View.INVISIBLE);
        image_edit_CV.setVisibility(View.GONE);
        pet_profile_shimmer.setVisibility(View.VISIBLE);
        pet_profile_shimmer.startShimmer();
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
            case R.id.back_arrow_CV:
                onBackPressed();
                break;

            case R.id.edit_profile_RL:
              intent=new Intent(this, GetPetDetailsActivity.class);
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
            case R.id.add_Clinic_BT:
                Intent petDetailsIntent = new Intent(this, PetDetailsActivity.class);
                Bundle data = new Bundle();
                data.putString("pet_id", getPetResponse.getData().getId());
                data.putString("pet_name", getPetResponse.getData().getPetName());
                data.putString("pet_parent", getPetResponse.getData().getPetParentName());
                data.putString("pet_sex", getPetResponse.getData().getPetSex());
                data.putString("pet_age", getPetResponse.getData().getPetAge());
                data.putString("pet_unique_id", getPetResponse.getData().getPetUniqueId());
                data.putString("pet_DOB", getPetResponse.getData().getDateOfBirth());
                data.putString("pet_encrypted_id", getPetResponse.getData().getEncryptedId());
                data.putString("pet_cat_id", getPetResponse.getData().getPetCategoryId());
                data.putString("lastVisitEncryptedId","");
                data.putString("pet_image_url", getPetResponse.getData().getPetProfileImageUrl());

                petDetailsIntent.putExtras(data);
                startActivity(petDetailsIntent);


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
                    add_Clinic_BT.setEnabled(true);
                    pet_profile_shimmer.setVisibility(View.GONE);
                    pet_full_details_SV.setVisibility(View.VISIBLE);
                    pet_profile_shimmer.stopShimmer();
                    edit_profile_RL.setVisibility(View.VISIBLE);
                    image_edit_CV.setVisibility(View.VISIBLE);
                    methods.customProgressDismiss();
                    Log.d("GetPetDetail", arg0.body().toString());
                    getPetResponse = (GetPetResponse) arg0.body();
                    int responseCode = Integer.parseInt(getPetResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                       // Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                        pet_name_TV.setText(getPetResponse.getData().getPetName());
                        pet_parent_name_TV.setText(getPetResponse.getData().getPetParentName());
                        parent_phone_TV.setText(getPetResponse.getData().getContactNumber());
                        pet_gender_TV.setText(getPetResponse.getData().getPetSex());
                        pet_breed_TV.setText(getPetResponse.getData().getPetBreed());
                        pet_reg__id_TV.setText(getPetResponse.getData().getPetUniqueId());
                        pet_dob_TV.setText(getPetResponse.getData().getDateOfBirth());
                        if (getPetResponse.getData().getAddress()==null){
                            parent_address_TV.setVisibility(View.GONE);
                        }else {
//                            parent_address_TV.setText(getPetResponse.getData().getAddress());
                            parent_address_TV.setText(getPetResponse.getData().getAddress());
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
                    .placeholder(R.drawable.pet_image)
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
