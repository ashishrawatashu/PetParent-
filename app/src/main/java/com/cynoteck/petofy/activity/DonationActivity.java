package com.cynoteck.petofy.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cynoteck.petofy.utils.PetParentSingleton;
import com.cynoteck.petofy.R;
import com.cynoteck.petofy.adapter.DonatePetAdapter;
import com.cynoteck.petofy.api.ApiClient;
import com.cynoteck.petofy.api.ApiResponse;
import com.cynoteck.petofy.api.ApiService;
import com.cynoteck.petofy.response.getPetReportsResponse.getPetListResponse.PetList;
import com.cynoteck.petofy.utils.Config;
import com.cynoteck.petofy.onClicks.OnItemClickListener;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.JsonObject;

import retrofit2.Response;
import static com.cynoteck.petofy.fragments.ProfileFragment.petListHorizontalAdapter;
import static com.cynoteck.petofy.fragments.PetRegisterFragment.registerPetAdapter;
import static com.cynoteck.petofy.fragments.PetRegisterFragment.total_pets_TV;
@SuppressLint("StaticFieldLeak")
public class DonationActivity extends AppCompatActivity implements View.OnClickListener, ApiResponse, OnItemClickListener {
    private final int               ADD_PET = 4;
    RecyclerView                    pet_list_RV;
    MaterialCardView                back_arrow_CV;
    @SuppressLint("StaticFieldLeak")
    public static RelativeLayout    total_donation_RL, add_pet_RL,donation_RL;
    public static TextView          total_donation_request_TV,create_headline_TV;
    public static DonatePetAdapter  donatePetAdapter;
    public static ImageView         donation_cart_icon_IV;
    String intentFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);












        intentFrom                 =getIntent().getStringExtra("from");

        pet_list_RV                 = findViewById(R.id.pet_list_RV);
        back_arrow_CV               = findViewById(R.id.back_arrow_CV);
        total_donation_RL           = findViewById(R.id.total_donation_RL);
        add_pet_RL                  = findViewById(R.id.add_pet_RL);
        total_donation_request_TV   = findViewById(R.id.total_donation_request_TV);
        donation_cart_icon_IV       = findViewById(R.id.donation_cart_icon_IV);
        donation_RL                 = findViewById(R.id.donation_RL);
        create_headline_TV=findViewById(R.id.create_headline_TV);

        add_pet_RL.setOnClickListener(this);
        total_donation_RL.setOnClickListener(this);
        back_arrow_CV.setOnClickListener(this);

            //check from which activity user is coming
//            if(intentFrom.equals("insurance"))
//            {
//                create_headline_TV.setText("INSURANCED YOUR PET");
//                total_donation_RL.setVisibility(View.GONE);
//                pet_list_RV.setLayoutManager(new LinearLayoutManager(this));
//                donatePetAdapter = new DonatePetAdapter(this, PetParentSingleton.getInstance().getArrayList(), this);
//                pet_list_RV.setAdapter(donatePetAdapter);
//                donatePetAdapter.notifyDataSetChanged();
//
//
//
//
//                Toast.makeText(this,"this if from insurance",Toast.LENGTH_SHORT).show();
//            }
//            else {
////                if(getIntent().getStringExtra("from").equals("donation"))
//                Toast.makeText(this,"this else statement from insurance",Toast.LENGTH_SHORT).show();
//
//        pet_list_RV.setLayoutManager(new LinearLayoutManager(this));
//        donatePetAdapter = new DonatePetAdapter(this, PetParentSingleton.getInstance().getArrayList(), this);
//        pet_list_RV.setAdapter(donatePetAdapter);
//        donatePetAdapter.notifyDataSetChanged();
//            }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!PetParentSingleton.getInstance().getGetDonationRequestListData().isEmpty()) {
            total_donation_request_TV.setText(String.valueOf(PetParentSingleton.getInstance().getGetDonationRequestListData().size()));
            total_donation_RL.setEnabled(true);
            donation_cart_icon_IV.setImageResource(R.drawable.cart_icon);
            donation_RL.setVisibility(View.VISIBLE);
        } else {
            total_donation_RL.setEnabled(false);
            donation_cart_icon_IV.setImageResource(R.drawable.cart_inactive);
            donation_RL.setVisibility(View.GONE);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.total_donation_RL:
                Intent donationRequestActivityIntent = new Intent(this, DonationRequestActivity.class);
                startActivity(donationRequestActivityIntent);
                break;

            case R.id.add_pet_RL:
                Intent adNewIntent = new Intent(this, AddPetRegister.class);
                adNewIntent.putExtra("intent_from", "add");
                startActivityForResult(adNewIntent, ADD_PET);
                break;

            case R.id.back_arrow_CV:
                onBackPressed();
                break;


        }
    }

    @Override
    public void onResponse(Response arg0, String key) {
        switch (key) {
            case "DonatePetById":
                try {
                    JsonObject jsonObject = (JsonObject) arg0.body();
                    JsonObject response = jsonObject.getAsJsonObject("response");
                    int responseCode = Integer.parseInt(String.valueOf(response.get("responseCode")));
                    if (responseCode == 109) {
                        Toast.makeText(this, "Successfully Donate", Toast.LENGTH_SHORT).show();
                        finish();
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

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_PET) {
            if (resultCode == RESULT_OK) {
                PetList petList = new PetList();
                petList.setId(data.getStringExtra("pet_id"));
                petList.setPetUniqueId(data.getStringExtra("pet_unique_id"));
                petList.setPetProfileImageUrl(data.getStringExtra("pet_image_url"));
                petList.setPetBreed(data.getStringExtra("pet_breed"));
                petList.setPetAge(data.getStringExtra("pet_age"));
                petList.setPetParentName(data.getStringExtra("pet_parent"));
                petList.setPetSex(data.getStringExtra("pet_sex"));
                petList.setPetName(data.getStringExtra("pet_name"));
                petList.setPetCategory(data.getStringExtra("pet_category"));
                petList.setDateOfBirth(data.getStringExtra("pet_date_of_birth"));
                petList.setPetColor(data.getStringExtra("pet_color"));
//                profileList.add(0, petList);
                PetParentSingleton.getInstance().getArrayList().add(0, petList);
                total_pets_TV.setText("You have " + PetParentSingleton.getInstance().getArrayList().size() + " pets registered ");
                registerPetAdapter.notifyDataSetChanged();
                petListHorizontalAdapter.notifyDataSetChanged();
                donatePetAdapter.notifyDataSetChanged();
            }
        }
        return;
    }

    @Override
    public void onViewDetailsClick(int position) {
        String realId = PetParentSingleton.getInstance().getArrayList().get(position).getId().substring(0, PetParentSingleton.getInstance().getArrayList().get(position).getId().length() - 2);
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

    private void donatePetById(String realId) {
        JsonObject jsonObjectParams = new JsonObject();
        jsonObjectParams.addProperty("id", realId);

        JsonObject jsonObjectRequest = new JsonObject();
        jsonObjectRequest.add("data", jsonObjectParams);

        ApiService<JsonObject> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().donatePetById(Config.token, jsonObjectRequest), "DonatePetById");
        //Log.d("DATALOG", "check1=> " + jsonObjectRequest);
    }
}