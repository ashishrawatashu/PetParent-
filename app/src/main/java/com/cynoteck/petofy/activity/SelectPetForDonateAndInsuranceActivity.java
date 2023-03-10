package com.cynoteck.petofy.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cynoteck.petofy.adapter.RegisterPetAdapter;
import com.cynoteck.petofy.response.getPetReportsResponse.getPetListResponse.GetPetListResponse;
import com.cynoteck.petofy.utils.Methods;
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
import static com.cynoteck.petofy.fragments.ProfileFragment.pet_list_LL;

@SuppressLint("StaticFieldLeak")
public class SelectPetForDonateAndInsuranceActivity extends AppCompatActivity implements View.OnClickListener, ApiResponse, OnItemClickListener {
    private final int               ADD_PET = 4;
    RecyclerView                    pet_list_RV;
    MaterialCardView                back_arrow_CV;
    public static RelativeLayout    total_donation_RL, add_pet_RL,donation_RL;
    public static TextView          total_donation_request_TV,create_headline_TV;
    public static DonatePetAdapter  donatePetAdapter;
    public static ImageView         donation_cart_icon_IV;
    public static ProgressBar       donation_insurance_PB;
    String                          intentFrom;
    Dialog                          insurance_successfully_dialog;
    Methods                         methods;
    TextView                        select_pet_TV;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);
        methods = new Methods(this);

        intentFrom                 =  getIntent().getStringExtra("from");
        initView();
        pet_list_RV.setLayoutManager(new LinearLayoutManager(this));
        donatePetAdapter = new DonatePetAdapter(this, PetParentSingleton.getInstance().getArrayList(), this);
        pet_list_RV.setAdapter(donatePetAdapter);

        //check from which activity user is coming
            if (intentFrom.equals("insurance")) {
                create_headline_TV.setText("PET INSURANCE");
                select_pet_TV.setText("Select pet for insurance");
                total_donation_RL.setVisibility(View.GONE);

            } else {
                total_donation_RL.setVisibility(View.VISIBLE);

            }
            if (PetParentSingleton.getInstance().getArrayList().isEmpty()){
                donation_insurance_PB.setVisibility(View.VISIBLE);
            }else {
                donation_insurance_PB.setVisibility(View.GONE);
                donatePetAdapter.notifyDataSetChanged();
            }





    }

    private void initView() {
        select_pet_TV               = findViewById(R.id.select_pet_TV);
        donation_insurance_PB       = findViewById(R.id.donation_insurance_PB);
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!PetParentSingleton.getInstance().getGetDonationRequestListData().isEmpty()) {
            Log.d("DONATION_LIST",methods.getRequestJson(PetParentSingleton.getInstance().getGetDonationRequestListData().isEmpty()));
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
                    methods.customProgressDismiss();
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
                petList.setIsDonated("false");
                petList.setIsAdopted("false");
                //                profileList.add(0, petList);
                PetParentSingleton.getInstance().getArrayList().add(0, petList);
                total_pets_TV.setText("You have " + PetParentSingleton.getInstance().getArrayList().size() + " pets registered ");
                registerPetAdapter.notifyDataSetChanged();
                petListHorizontalAdapter.notifyDataSetChanged();
                donatePetAdapter.notifyDataSetChanged();
                donation_insurance_PB.setVisibility(View.GONE);
            }
        }
        else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                showAppointmentSuccessfully();
            }
        }
        return;
    }
    private void showAppointmentSuccessfully() {
        insurance_successfully_dialog = new Dialog(this);
        insurance_successfully_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        insurance_successfully_dialog.setCancelable(false);
        insurance_successfully_dialog.setContentView(R.layout.insurance_submitted_dilog);
        insurance_successfully_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        TextView back_to_appointments_TV = insurance_successfully_dialog.findViewById(R.id.back_to_appointments_TV);
        back_to_appointments_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insurance_successfully_dialog.dismiss();
            }
        });

        insurance_successfully_dialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = insurance_successfully_dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    @Override
    public void onViewDetailsClick(int position) {
        String realId = PetParentSingleton.getInstance().getArrayList().get(position).getId().substring(0, PetParentSingleton.getInstance().getArrayList().get(position).getId().length() - 2);

         if(intentFrom.equals("insurance")) {
            if (PetParentSingleton.getInstance().getArrayList().get(position).getPetCategory().equals("Dog")){
                Intent insuranceIntent = new Intent(this, BuyInsuranceActivity.class);
                insuranceIntent.putExtra("petId", realId);
                insuranceIntent.putExtra("afterLogin", "yes");
                insuranceIntent.putExtra("pet_breed", PetParentSingleton.getInstance().getArrayList().get(position).getPetBreed());
                insuranceIntent.putExtra("pet_color", PetParentSingleton.getInstance().getArrayList().get(position).getPetColor());
                startActivityForResult(insuranceIntent, 2);
            }else {
                Toast.makeText(this, "Insurance is only for dog !", Toast.LENGTH_SHORT).show();
            }

        } else {
            if(PetParentSingleton.getInstance().getArrayList().get(position).getIsDonated().equals("true")) {
                Toast.makeText(this,"Pet is donated !",Toast.LENGTH_SHORT).show();


            } else {
//                String realId = PetParentSingleton.getInstance().getArrayList().get(position).getId().substring(0, PetParentSingleton.getInstance().getArrayList().get(position).getId().length() - 2);
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("");
                alertDialog.setMessage("Do you want to donate this pet?");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                donatePetById(realId);
                                PetParentSingleton.getInstance().getArrayList().get(position).setIsDonated("true");
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
        }

    }

    private void donatePetById(String realId) {
        methods.showCustomProgressBarDialog(this);
        JsonObject jsonObjectParams = new JsonObject();
        jsonObjectParams.addProperty("id", realId);
        JsonObject jsonObjectRequest = new JsonObject();
        jsonObjectRequest.add("data", jsonObjectParams);

        ApiService<JsonObject> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().donatePetById(Config.token, jsonObjectRequest), "DonatePetById");
        //Log.d("DATALOG", "check1=> " + jsonObjectRequest);
    }
}