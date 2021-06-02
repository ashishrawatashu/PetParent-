package com.cynoteck.petofyparents.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.gson.JsonObject;

import retrofit2.Response;

import static com.cynoteck.petofyparents.fragments.PetRegisterFragment.registerPetAdapter;
import static com.cynoteck.petofyparents.fragments.ProfileFragment.petListHorizontalAdapter;

public class PetProfileActivity extends AppCompatActivity implements ApiResponse, View.OnClickListener {
    Methods methods;
    String petId = "", pet_unique_id = "", pet_image_url = "", pet_breed = "", pet_age = "", pet_sex = "", pet_name = "",pet_category = "",pet_DOB = "",pet_color = "";
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
    int pet_list_position;

    LinearLayout pet_reports_LL, consultation_LL, donate_pet_LL,hostels_LL,grooming_LL,pet_shops_LL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_profile_activity);
        methods = new Methods(this);
        Intent extras = getIntent();
        pet_list_position = Integer.parseInt(extras.getStringExtra("pet_list_position"));
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
        consultation_LL = findViewById(R.id.consultation_LL);
        donate_pet_LL = findViewById(R.id.donate_pet_LL);
        grooming_LL = findViewById(R.id.grooming_LL);
        hostels_LL = findViewById(R.id.hostels_LL);
        pet_reports_LL = findViewById(R.id.pet_reports_LL);
        pet_shops_LL = findViewById(R.id.pet_shops_LL);


        consultation_LL.setOnClickListener(this);
        donate_pet_LL.setOnClickListener(this);
        grooming_LL.setOnClickListener(this);
        hostels_LL.setOnClickListener(this);
        pet_reports_LL.setOnClickListener(this);
        pet_shops_LL.setOnClickListener(this);


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
        sharedPreferences = getSharedPreferences("userDetails", 0);

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
        Glide.with(this).load(pet_image_url).placeholder(R.drawable.empty_pet_image).into(pet_profile_image_IV);
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
                
            case R.id.pet_reports_LL:
                Intent selectReportsIntent = new Intent(this, SelectPetReportsActivity.class);
                Bundle data = new Bundle();
                data.putString("pet_id", PetParentSingleton.getInstance().getArrayList().get(pet_list_position).getId().substring(0, PetParentSingleton.getInstance().getArrayList().get(pet_list_position).getId().length() - 2));
                data.putString("pet_name", PetParentSingleton.getInstance().getArrayList().get(pet_list_position).getPetName());
                data.putString("pet_unique_id", PetParentSingleton.getInstance().getArrayList().get(pet_list_position).getPetUniqueId());
                data.putString("pet_sex", PetParentSingleton.getInstance().getArrayList().get(pet_list_position).getPetSex());
                data.putString("pet_owner_name", PetParentSingleton.getInstance().getArrayList().get(pet_list_position).getPetParentName());
                data.putString("pet_owner_contact", PetParentSingleton.getInstance().getArrayList().get(pet_list_position).getContactNumber());
                data.putString("pet_DOB", PetParentSingleton.getInstance().getArrayList().get(pet_list_position).getDateOfBirth());
                data.putString("pet_encrypted_id", PetParentSingleton.getInstance().getArrayList().get(pet_list_position).getEncryptedId());
                data.putString("pet_age", PetParentSingleton.getInstance().getArrayList().get(pet_list_position).getPetAge());
                data.putString("pet_image_url", PetParentSingleton.getInstance().getArrayList().get(pet_list_position).getPetProfileImageUrl());
                selectReportsIntent.putExtras(data);
                startActivity(selectReportsIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                
                break;

            case R.id.consultation_LL:
                Intent consultationIntent = new Intent(this, ConsultationListActivity.class);
                startActivity(consultationIntent);
                break;

            case R.id.donate_pet_LL:
            {
                String realId = PetParentSingleton.getInstance().getArrayList().get(pet_list_position).getId().substring(0,PetParentSingleton.getInstance().getArrayList().get(pet_list_position).getId().length()-2);
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("");
                alertDialog.setMessage("Do you want to donate this pet?");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                donatePetById(realId);
                                dialog.dismiss();

                            }
                        });

                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dialogInterface.dismiss();

                            }

                        });
                alertDialog.show();





            }
                break;

            case R.id.pet_shops_LL:

            case R.id.grooming_LL:

            case R.id.hostels_LL:

                Toast.makeText(this, "Coming soon !", Toast.LENGTH_SHORT).show();
                break;



        }

    }

    private void donatePetById(String realId) {
        JsonObject jsonObjectParams = new JsonObject();
        jsonObjectParams.addProperty("id", realId);

        JsonObject jsonObjectRequest = new JsonObject();
        jsonObjectRequest.add("data", jsonObjectParams);

        ApiService<JsonObject> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().donatePetById(Config.token, jsonObjectRequest), "DonatePetById");
        Log.e("DATALOG", "check1=> " + jsonObjectRequest);
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

            case "DonatePetById":
                try {
                    JsonObject jsonObject = (JsonObject) arg0.body();
                    JsonObject response = jsonObject.getAsJsonObject("response");
                    int responseCode = Integer.parseInt(String.valueOf(response.get("responseCode")));
                    if (responseCode == 109) {
                        Toast.makeText(this, "Successfully Donate", Toast.LENGTH_SHORT).show();
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
                PetParentSingleton.getInstance().getArrayList().set((pet_list_position),petList);

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
