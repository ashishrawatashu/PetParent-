package com.cynoteck.petofyparents.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cynoteck.petofyparents.utils.PetParentSingleton;
import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.adapter.ScanPetListAdapter;
import com.cynoteck.petofyparents.api.ApiClient;
import com.cynoteck.petofyparents.api.ApiResponse;
import com.cynoteck.petofyparents.api.ApiService;
import com.cynoteck.petofyparents.parameter.addPetToVetRegisterUsingQRCodeRequest.AddPetToVetRegisterUsingQRCodeParams;
import com.cynoteck.petofyparents.parameter.addPetToVetRegisterUsingQRCodeRequest.AddPetToVetRegisterUsingQRRequest;
import com.cynoteck.petofyparents.parameter.petReportsRequest.PetDataParams;
import com.cynoteck.petofyparents.parameter.petReportsRequest.PetDataRequest;
import com.cynoteck.petofyparents.response.getPetReportsResponse.getPetListResponse.GetPetListResponse;
import com.cynoteck.petofyparents.response.getPetReportsResponse.getPetListResponse.PetList;
import com.cynoteck.petofyparents.response.registerParentWithQRResponse.RegisterParentWithQRResponse;
import com.cynoteck.petofyparents.utils.Config;
import com.cynoteck.petofyparents.utils.Methods;
import com.cynoteck.petofyparents.onClicks.ViewDeatilsAndIdCardClick;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

import retrofit2.Response;

import static com.cynoteck.petofyparents.fragments.ProfileFragment.petListHorizontalAdapter;
import static com.cynoteck.petofyparents.fragments.PetRegisterFragment.registerPetAdapter;
import static com.cynoteck.petofyparents.fragments.PetRegisterFragment.total_pets_TV;

public class AfterScanScreenActivity extends AppCompatActivity implements TextWatcher, View.OnClickListener, ApiResponse, ViewDeatilsAndIdCardClick {

    RecyclerView register_pet_RV;
    public ScanPetListAdapter scanPetListAdapter;
    ArrayList<PetList> profileList;
    ImageView empty_IV, vet_profile_pic;
    EditText search_box_add_new;
    TextView clinic_name_TV, vet_name_TV;
    ImageView star_one, star_two, star_three, star_four, star_five;
    RelativeLayout add_pet_RL;
    String veterinarianUserId, veterinarianName, clinicName, Rating, profileImageUrl;
    Methods methods;
    int page = 1, pagelimit = 100;
    ProgressBar progressBar;
    private static final int ADD_PET_WITH_QR_CODE = 200;
    NestedScrollView nested_scroll_view;
    TextView scan_layout_total_pets_TV;
    LinearLayout something_wrong_LL;
    ProgressBar progressBarFirst;
    MaterialCardView back_arrow_CV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_after_scan_screen);
        // Inflate the layout for this fragment
        methods = new Methods(this);
//        ClipboardManager mCbm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
//        mCbm = ClipData.newPlainText("text", text);
//        mCbm.clearPrimaryClip();
        init();
        empty_IV.setVisibility(View.GONE);
        Intent intent = getIntent();
        veterinarianUserId = intent.getStringExtra("veterinarianUserId");
        veterinarianName = intent.getStringExtra("veterinarianName");
        clinicName = intent.getStringExtra("clinicName");
        Rating = intent.getStringExtra("Rating");


        profileImageUrl = intent.getStringExtra("profileImageUrl");
        Glide.with(this).load(profileImageUrl).into(vet_profile_pic);
        clinic_name_TV.setText(clinicName);
        vet_name_TV.setText(veterinarianName);

        setRating();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        register_pet_RV.setLayoutManager(linearLayoutManager);
        scanPetListAdapter = new ScanPetListAdapter(this, PetParentSingleton.getInstance().getArrayList(), this);

        if (!PetParentSingleton.getInstance().getArrayList().isEmpty()) {
            nested_scroll_view.setVisibility(View.VISIBLE);
            progressBarFirst.setVisibility(View.GONE);
            scan_layout_total_pets_TV.setText("You have " + PetParentSingleton.getInstance().getArrayList().size() + " pets registered ");
            register_pet_RV.setAdapter(scanPetListAdapter);
            scanPetListAdapter.notifyDataSetChanged();
        } else {
            if (methods.isInternetOn()) {
                getPetList(page, pagelimit);
            } else {

                methods.DialogInternet();
            }

        }



    }

    private void getPetList(int page, int pagelimit) {
        //methods.showCustomProgressBarDialog(this);
        progressBarFirst.setVisibility(View.VISIBLE);
        PetDataParams getPetDataParams = new PetDataParams();
        getPetDataParams.setPageNumber(page);//0
        getPetDataParams.setPageSize(pagelimit);//0
        getPetDataParams.setSearch_Data("");
        PetDataRequest getPetDataRequest = new PetDataRequest();
        getPetDataRequest.setData(getPetDataParams);


        ApiService<GetPetListResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getPetList(Config.token, getPetDataRequest), "GetPetList");
        Log.e("DATALOG", "check1=> " + getPetDataRequest);

    }

    private void setRating() {
        int totalRating = Integer.parseInt(Rating);
        int totalRate = Math.round(totalRating);
        if (totalRate == 0) {
            star_one.setImageResource(R.drawable.star_without_rate);
            star_two.setImageResource(R.drawable.star_without_rate);
            star_three.setImageResource(R.drawable.star_without_rate);
            star_four.setImageResource(R.drawable.star_without_rate);
            star_five.setImageResource(R.drawable.star_without_rate);
        } else if (totalRate == 1) {
            star_one.setImageResource(R.drawable.star_with_rate);
            star_two.setImageResource(R.drawable.star_without_rate);
            star_three.setImageResource(R.drawable.star_without_rate);
            star_four.setImageResource(R.drawable.star_without_rate);
            star_five.setImageResource(R.drawable.star_without_rate);
        } else if (totalRate == 2) {
            star_one.setImageResource(R.drawable.star_with_rate);
            star_two.setImageResource(R.drawable.star_with_rate);
            star_three.setImageResource(R.drawable.star_without_rate);
            star_four.setImageResource(R.drawable.star_without_rate);
            star_five.setImageResource(R.drawable.star_without_rate);
        } else if (totalRate == 3) {
            star_one.setImageResource(R.drawable.star_with_rate);
            star_two.setImageResource(R.drawable.star_with_rate);
            star_three.setImageResource(R.drawable.star_with_rate);
            star_four.setImageResource(R.drawable.star_without_rate);
            star_five.setImageResource(R.drawable.star_without_rate);
        } else if (totalRate == 4) {
            star_one.setImageResource(R.drawable.star_with_rate);
            star_two.setImageResource(R.drawable.star_with_rate);
            star_three.setImageResource(R.drawable.star_with_rate);
            star_four.setImageResource(R.drawable.star_with_rate);
            star_five.setImageResource(R.drawable.star_without_rate);
        } else if (totalRate == 5) {
            star_one.setImageResource(R.drawable.star_with_rate);
            star_two.setImageResource(R.drawable.star_with_rate);
            star_three.setImageResource(R.drawable.star_with_rate);
            star_four.setImageResource(R.drawable.star_with_rate);
            star_five.setImageResource(R.drawable.star_with_rate);
        }
    }

    private void init() {
        back_arrow_CV = findViewById(R.id.back_arrow_CV);
        progressBarFirst = findViewById(R.id.progressBarFirst);
        something_wrong_LL = findViewById(R.id.something_wrong_LL);
        scan_layout_total_pets_TV = findViewById(R.id.total_pets_TV);
        add_pet_RL = findViewById(R.id.add_pet_RL);
        empty_IV = findViewById(R.id.empty_IV);
        nested_scroll_view = findViewById(R.id.nested_scroll_view);
        vet_profile_pic = findViewById(R.id.vet_profile_pic);
        clinic_name_TV = findViewById(R.id.clinic_name_TV);
        vet_name_TV = findViewById(R.id.vet_name_TV);
        search_box_add_new = findViewById(R.id.search_box_add_new);
        vet_profile_pic = findViewById(R.id.vet_profile_pic);
        star_one = findViewById(R.id.star_one);
        star_two = findViewById(R.id.star_two);
        star_three = findViewById(R.id.star_three);
        star_four = findViewById(R.id.star_four);
        star_five = findViewById(R.id.star_five);
        register_pet_RV = findViewById(R.id.register_pet_RV);
        progressBar = findViewById(R.id.progressBar);
        profileList = new ArrayList<>();
        something_wrong_LL.setOnClickListener(this);
        add_pet_RL.setOnClickListener(this);
        back_arrow_CV.setOnClickListener(this);


    }


    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_PET_WITH_QR_CODE) {
            if (resultCode == RESULT_OK) {
                PetList petList = new PetList();
                petList.setId(data.getStringExtra("pet_id"));
                petList.setPetUniqueId(data.getStringExtra("pet_unique_id"));
                petList.setPetProfileImageUrl(data.getStringExtra("pet_image_url"));
                petList.setPetBreed(data.getStringExtra("pet_breed"));
                petList.setPetAge(data.getStringExtra("pet_age"));
                petList.setPetSex(data.getStringExtra("pet_sex"));
                petList.setPetName(data.getStringExtra("pet_name"));
                petList.setPetParentName(data.getStringExtra("pet_parent"));
                petList.setPetCategory(data.getStringExtra("pet_category"));
                petList.setDateOfBirth(data.getStringExtra("pet_date_of_birth"));
                petList.setPetColor(data.getStringExtra("pet_color"));
                PetParentSingleton.getInstance().getArrayList().add(0, petList);
                scanPetListAdapter.notifyDataSetChanged();
                registerPetAdapter.notifyDataSetChanged();
                total_pets_TV.setText("You have " + PetParentSingleton.getInstance().getArrayList().size() + " pets registered ");
                scan_layout_total_pets_TV.setText("You have " + PetParentSingleton.getInstance().getArrayList().size() + " pets registered ");
                petListHorizontalAdapter.notifyDataSetChanged();

                methods.showCustomProgressBarDialog(this);
                AddPetToVetRegisterUsingQRCodeParams addPetToVetRegisterUsingQRCodeParams = new AddPetToVetRegisterUsingQRCodeParams();
                addPetToVetRegisterUsingQRCodeParams.setVeterinarianUserId(veterinarianUserId);
                addPetToVetRegisterUsingQRCodeParams.setPetUniqueId(data.getStringExtra("pet_unique_id"));
                AddPetToVetRegisterUsingQRRequest addPetToVetRegisterUsingQRRequest = new AddPetToVetRegisterUsingQRRequest();
                addPetToVetRegisterUsingQRRequest.setData(addPetToVetRegisterUsingQRCodeParams);
                ApiService<RegisterParentWithQRResponse> service = new ApiService<>();
                service.get(this, ApiClient.getApiInterface().addPetToVetRegisterUsingQRCode(Config.token, addPetToVetRegisterUsingQRRequest), "AddPetWithQR");
                Log.e("AddPetWithQR", "AddPetWithQR=> " + addPetToVetRegisterUsingQRRequest);


            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_pet_RL:
//                Intent add_pet_with_QR_intent = new Intent(this, AddPetWithQRCodeActivity.class);
//                add_pet_with_QR_intent.putExtra("veterinarianUserId", veterinarianUserId);
//                startActivityForResult(add_pet_with_QR_intent, ADD_PET_WITH_QR_CODE);
                Intent adNewIntent = new Intent(this, AddPetRegister.class);
                adNewIntent.putExtra("intent_from", "AfterScanScreenActivity");
                startActivityForResult(adNewIntent, ADD_PET_WITH_QR_CODE);
                break;

            case R.id.back_arrow_CV:
                finish();
                break;

        }
    }

    @Override
    public void onResponse(Response response, String key) {
        switch (key) {
            case "GetPetList":
                try {
                    GetPetListResponse getPetListResponse = (GetPetListResponse) response.body();
                    int responseCode = Integer.parseInt(getPetListResponse.getResponse().getResponseCode());
                    something_wrong_LL.setVisibility(View.GONE);
                    nested_scroll_view.setVisibility(View.VISIBLE);
                    progressBarFirst.setVisibility(View.GONE);
                    if (responseCode == 109) {
                        if (getPetListResponse.getData().getPetList().isEmpty()) {
                            empty_IV.setVisibility(View.VISIBLE);
                            total_pets_TV.setText("No pet registered ! ");
                        } else {
                            PetParentSingleton.getInstance().getArrayList().clear();
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                            register_pet_RV.setLayoutManager(linearLayoutManager);
                            if (getPetListResponse.getData().getPetList().size() > 0) {
                                for (int i = 0; i < getPetListResponse.getData().getPetList().size(); i++) {
                                    PetList petList = new PetList();
                                    petList.setPetUniqueId(getPetListResponse.getData().getPetList().get(i).getPetUniqueId());
                                    petList.setDateOfBirth(getPetListResponse.getData().getPetList().get(i).getDateOfBirth());
                                    petList.setPetName(getPetListResponse.getData().getPetList().get(i).getPetName());
                                    petList.setPetSex(getPetListResponse.getData().getPetList().get(i).getPetSex());
                                    petList.setPetParentName(getPetListResponse.getData().getPetList().get(i).getPetParentName());
                                    petList.setPetProfileImageUrl(getPetListResponse.getData().getPetList().get(i).getPetProfileImageUrl());
                                    petList.setEncryptedId(getPetListResponse.getData().getPetList().get(i).getEncryptedId());
                                    petList.setId(getPetListResponse.getData().getPetList().get(i).getId());
                                    petList.setPetAge(getPetListResponse.getData().getPetList().get(i).getPetAge());
                                    petList.setPetCategoryId(getPetListResponse.getData().getPetList().get(i).getPetCategoryId());
                                    petList.setLastVisitEncryptedId(getPetListResponse.getData().getPetList().get(i).getLastVisitEncryptedId());
                                    petList.setPetBreed(getPetListResponse.getData().getPetList().get(i).getPetBreed());
                                    petList.setPetCategory(getPetListResponse.getData().getPetList().get(i).getPetCategory());
                                    petList.setPetColor(getPetListResponse.getData().getPetList().get(i).getPetColor());
                                    PetParentSingleton.getInstance().getArrayList().add(petList);
                                }

                                scan_layout_total_pets_TV.setText("You have " + PetParentSingleton.getInstance().getArrayList().size() + " pets registered ");

                                total_pets_TV.setText("You have " + PetParentSingleton.getInstance().getArrayList().size() + " pets registered ");
                                scanPetListAdapter.notifyDataSetChanged();
                                register_pet_RV.setVisibility(View.VISIBLE);
                                registerPetAdapter.notifyDataSetChanged();
                                petListHorizontalAdapter.notifyDataSetChanged();
                            }
                        }


                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case "AddPetWithQR":
                try {
                    methods.customProgressDismiss();
                    RegisterParentWithQRResponse registerParentWithQRResponse = (RegisterParentWithQRResponse) response.body();
                    Log.e("RegistrationQR", registerParentWithQRResponse.toString());

                    if (registerParentWithQRResponse.getResponse().getResponseCode().equals("109")) {

                        setResult(RESULT_OK);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                        Toast.makeText(this, "Pet Added in Vet Register", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try again !", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    public void onError(Throwable t, String key) {

    }

    @Override
    public void onViewDetailsClick(int position) {

        Intent intent = new Intent(this, PetProfileActivity.class);
        intent.putExtra("pet_list_position", String.valueOf(position));
        intent.putExtra("pet_id", PetParentSingleton.getInstance().getArrayList().get(position).getId().substring(0, PetParentSingleton.getInstance().getArrayList().get(position).getId().length() - 2));
        intent.putExtra("pet_unique_id", PetParentSingleton.getInstance().getArrayList().get(position).getPetUniqueId());
        intent.putExtra("pet_image_url", PetParentSingleton.getInstance().getArrayList().get(position).getPetProfileImageUrl());
        intent.putExtra("pet_breed", PetParentSingleton.getInstance().getArrayList().get(position).getPetBreed());
        intent.putExtra("pet_age", PetParentSingleton.getInstance().getArrayList().get(position).getPetAge());
        intent.putExtra("pet_sex", PetParentSingleton.getInstance().getArrayList().get(position).getPetSex());
        intent.putExtra("pet_name", PetParentSingleton.getInstance().getArrayList().get(position).getPetName());
        intent.putExtra("pet_category", PetParentSingleton.getInstance().getArrayList().get(position).getPetCategory());
        intent.putExtra("pet_DOB", PetParentSingleton.getInstance().getArrayList().get(position).getDateOfBirth());
        intent.putExtra("pet_color", PetParentSingleton.getInstance().getArrayList().get(position).getPetColor());

        startActivity(intent);
    }

    @Override
    public void onViewReportsClick(int position) {
        methods.showCustomProgressBarDialog(this);
        AddPetToVetRegisterUsingQRCodeParams addPetToVetRegisterUsingQRCodeParams = new AddPetToVetRegisterUsingQRCodeParams();
        addPetToVetRegisterUsingQRCodeParams.setVeterinarianUserId(veterinarianUserId);
        addPetToVetRegisterUsingQRCodeParams.setPetUniqueId(PetParentSingleton.getInstance().getArrayList().get(position).getPetUniqueId());
        AddPetToVetRegisterUsingQRRequest addPetToVetRegisterUsingQRRequest = new AddPetToVetRegisterUsingQRRequest();
        addPetToVetRegisterUsingQRRequest.setData(addPetToVetRegisterUsingQRCodeParams);
        ApiService<RegisterParentWithQRResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().addPetToVetRegisterUsingQRCode(Config.token, addPetToVetRegisterUsingQRRequest), "AddPetWithQR");
        Log.e("AddPetWithQR", "AddPetWithQR=> " + addPetToVetRegisterUsingQRRequest);


    }

    @Override
    public void onIdAddClinicClick(int position) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}