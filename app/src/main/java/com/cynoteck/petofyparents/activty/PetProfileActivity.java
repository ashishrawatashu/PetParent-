package com.cynoteck.petofyparents.activty;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.cynoteck.petofyparents.PetParentSingleton;
import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.api.ApiClient;
import com.cynoteck.petofyparents.api.ApiResponse;
import com.cynoteck.petofyparents.api.ApiService;
import com.cynoteck.petofyparents.parameter.getPetListRequest.GetPetListParams;
import com.cynoteck.petofyparents.parameter.getPetListRequest.GetPetListRequest;
import com.cynoteck.petofyparents.response.getPetDetailsResponse.GetPetResponse;
import com.cynoteck.petofyparents.response.getPetReportsResponse.getPetListResponse.PetList;
import com.cynoteck.petofyparents.utils.Config;
import com.cynoteck.petofyparents.utils.Methods;
import com.google.android.material.card.MaterialCardView;

import retrofit2.Response;

import static com.cynoteck.petofyparents.fragments.PetRegisterFragment.registerPetAdapter;
import static com.cynoteck.petofyparents.fragments.ProfileFragment.petListHorizontalAdapter;

public class PetProfileActivity extends AppCompatActivity implements ApiResponse, View.OnClickListener {
    Methods methods;
    String pet_list_position="",petId = "", pet_unique_id = "", pet_image_url = "", pet_breed = "", pet_age = "", pet_sex = "", pet_name = "",pet_category = "",pet_DOB = "",pet_color = "";
    ImageView pet_profile_image_IV;
    TextView pet_name_TV, pet_dob_TV, pet_reg__id_TV, pet_breed_TV, pet_gender_TV;
    GetPetResponse getPetResponse;
    boolean reloadData = false;
    String permissionId = "";
    //    ConstraintLayout pet_profile_details_CL;
    MaterialCardView back_arrow_CV, image_edit_CV;
    SharedPreferences sharedPreferences;
    RelativeLayout edit_profile_RL;
    ScrollView pet_full_details_SV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_profile_activity);
        methods = new Methods(this);
        Intent extras = getIntent();
        pet_list_position = extras.getStringExtra("pet_list_position");
        petId = extras.getStringExtra("pet_id");
        pet_unique_id = extras.getStringExtra("pet_unique_id");
        pet_image_url = extras.getStringExtra("pet_image_url");
        pet_breed = extras.getStringExtra("pet_breed");
        pet_age = extras.getStringExtra("pet_age");
        pet_sex = extras.getStringExtra("pet_sex");
        pet_name = extras.getStringExtra("pet_name");
        pet_category = extras.getStringExtra("pet_category");
        pet_DOB = extras.getStringExtra("pet_DOB");
        pet_color = extras.getStringExtra("pet_color");

        pet_full_details_SV = findViewById(R.id.pet_full_details_SV);
        back_arrow_CV = findViewById(R.id.back_arrow_CV);

        pet_profile_image_IV = findViewById(R.id.pet_profile_image_IV);
        image_edit_CV = findViewById(R.id.image_edit_CV);


        pet_name_TV = findViewById(R.id.pet_name_TV);
        pet_dob_TV = findViewById(R.id.pet_age_TV);
        pet_reg__id_TV = findViewById(R.id.pet_reg__id_TV);
        pet_breed_TV = findViewById(R.id.pet_breed_TV);
        pet_gender_TV = findViewById(R.id.pet_gender_TV);

        edit_profile_RL = findViewById(R.id.edit_profile_RL);

        edit_profile_RL.setOnClickListener(this);

        edit_profile_RL.setOnClickListener(this);
        back_arrow_CV.setOnClickListener(this);

        setViewDetails();

        GetPetListParams getPetListParams = new GetPetListParams();
        getPetListParams.setId(petId);
        GetPetListRequest getPetListRequest = new GetPetListRequest();
        getPetListRequest.setData(getPetListParams);
        sharedPreferences = getSharedPreferences("userdetails", 0);

//        if (methods.isInternetOn()) {
//            getPetlistData(getPetListRequest);
//        } else {
//            methods.DialogInternet();
//        }

    }

    private void setViewDetails() {
        pet_name_TV.setText(pet_name);
        pet_gender_TV.setText(pet_sex);
        pet_breed_TV.setText(pet_breed);
        pet_reg__id_TV.setText(pet_unique_id);
        pet_dob_TV.setText(pet_age);
        Glide.with(this)
                .load(pet_image_url)
                .placeholder(R.drawable.empty_pet_image)
                .into(pet_profile_image_IV);
    }

    private void getPetlistData(GetPetListRequest getPetListRequest) {
        reloadData = true;
        pet_full_details_SV.setVisibility(View.GONE);
        edit_profile_RL.setVisibility(View.INVISIBLE);
        image_edit_CV.setVisibility(View.GONE);
        ApiService<GetPetResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getPetDetails(Config.token, getPetListRequest), "GetPetDetail");
        Log.e("DATALOG", "check1=> " + getPetListRequest);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.back_arrow_CV:
                onBackPressed();
                break;

            case R.id.edit_profile_RL:
                Intent intent = new Intent(this, UpdatePetProfileActivity.class);
                intent.putExtra("pet_list_position", pet_list_position);
                intent.putExtra("pet_id", petId);
                intent.putExtra("pet_category", pet_category);
                intent.putExtra("pet_name", pet_name);
                intent.putExtra("pet_sex", pet_sex);
                intent.putExtra("pet_DOB", pet_DOB);
                intent.putExtra("pet_age", pet_age);
                intent.putExtra("pet_size", "");
                intent.putExtra("pet_breed", pet_breed);
                intent.putExtra("pet_color", pet_color);
                intent.putExtra("pet_parent", Config.user_name);
                intent.putExtra("pet_parent_contact", Config.user_phone);
                intent.putExtra("image_url", pet_image_url);
                startActivityForResult(intent, 1);
                break;

        }

    }

    @Override
    public void onResponse(Response arg0, String key) {
        reloadData = false;
        switch (key) {
            case "GetPetDetail":
                try {
                    pet_full_details_SV.setVisibility(View.VISIBLE);
                    edit_profile_RL.setVisibility(View.VISIBLE);
                    image_edit_CV.setVisibility(View.VISIBLE);
                    Log.d("GetPetDetail", arg0.body().toString());
                    getPetResponse = (GetPetResponse) arg0.body();
                    int responseCode = Integer.parseInt(getPetResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        // Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                        pet_name_TV.setText(getPetResponse.getData().getPetName());
                        pet_gender_TV.setText(getPetResponse.getData().getPetSex());
                        pet_breed_TV.setText(getPetResponse.getData().getPetBreed());
                        pet_reg__id_TV.setText(getPetResponse.getData().getPetUniqueId());
                        pet_dob_TV.setText(getPetResponse.getData().getDateOfBirth());
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
        if (!getPetResponse.getData().getPetProfileImageUrl().equals("")) {
            Glide.with(this)
                    .load(getPetResponse.getData().getPetProfileImageUrl())
                    .placeholder(R.drawable.empty_pet_image)
                    .into(pet_profile_image_IV);
        }
    }

    @Override
    public void onError(Throwable t, String key) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                PetList petList = new PetList();
                petList.setId(data.getStringExtra("pet_id"));
                petList.setPetUniqueId(data.getStringExtra("pet_unique_id"));
                petList.setPetProfileImageUrl(data.getStringExtra("pet_image_url"));
                petList.setPetBreed(data.getStringExtra("pet_breed"));
                petList.setPetAge(data.getStringExtra("pet_age"));
                petList.setPetSex(data.getStringExtra("pet_sex"));
                petList.setPetName(data.getStringExtra("pet_name"));
                petList.setPetCategory(data.getStringExtra("pet_category"));
                petList.setDateOfBirth(data.getStringExtra("pet_date_of_birth"));
                petList.setPetColor(data.getStringExtra("pet_color"));
                PetParentSingleton.getInstance().getArrayList().set(Integer.parseInt(pet_list_position),petList);

                petId = data.getStringExtra("pet_id");
                pet_unique_id = data.getStringExtra("pet_unique_id");
                pet_image_url = data.getStringExtra("pet_image_url");
                pet_breed = data.getStringExtra("pet_breed");
                pet_age = data.getStringExtra("pet_age");
                pet_sex = data.getStringExtra("pet_sex");
                pet_name = data.getStringExtra("pet_name");
                pet_category = data.getStringExtra("pet_category");
                pet_DOB = data.getStringExtra("pet_date_of_birth");
                pet_color = data.getStringExtra("pet_color");
                registerPetAdapter.notifyDataSetChanged();
                petListHorizontalAdapter.notifyDataSetChanged();
                setViewDetails();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
