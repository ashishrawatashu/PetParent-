package com.cynoteck.petofy.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cynoteck.petofy.R;
import com.cynoteck.petofy.adapter.SearchAdapter;
import com.cynoteck.petofy.api.ApiClient;
import com.cynoteck.petofy.api.ApiResponse;
import com.cynoteck.petofy.api.ApiService;
import com.cynoteck.petofy.parameter.petReportsRequest.PetDataParams;
import com.cynoteck.petofy.parameter.petReportsRequest.PetDataRequest;
import com.cynoteck.petofy.response.getPetReportsResponse.getPetListResponse.GetPetListResponse;
import com.cynoteck.petofy.response.getPetReportsResponse.getPetListResponse.PetList;
import com.cynoteck.petofy.utils.Config;
import com.cynoteck.petofy.onClicks.OnItemClickListener;

import java.util.ArrayList;

import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements TextWatcher, ApiResponse, OnItemClickListener,View.OnClickListener{
    EditText                searchpet;
    ImageView               back_arrow;
    ArrayList<PetList>      profileList = new ArrayList<>();
    RecyclerView            register_pet_RV;
    SearchAdapter           SearchAdapter;
    ProgressBar             progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchpet       =  findViewById(R.id.search_pet);
        back_arrow      =  findViewById(R.id.back_arrow);
        register_pet_RV =  findViewById(R.id.register_pet_RV);

        back_arrow.setOnClickListener(this);
        progressBar = findViewById(R.id.progressBar);
        searchpet.requestFocus();
        searchpet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String value = s.toString();
                petSearchDependsOnPrefix(value);
            }
        });

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

    private void petSearchDependsOnPrefix(String prefix) {
        progressBar.setVisibility(View.VISIBLE);
        PetDataParams getPetDataParams = new PetDataParams();
        getPetDataParams.setPageNumber(0);//0
        getPetDataParams.setPageSize(10);//0
        getPetDataParams.setSearch_Data(prefix);
        PetDataRequest getPetDataRequest = new PetDataRequest();
        getPetDataRequest.setData(getPetDataParams);

        ApiService<GetPetListResponse> service = new ApiService<>();
        service.get(SearchActivity.this, ApiClient.getApiInterface().getPetList(Config.token, getPetDataRequest), "GetPetListBySearch");
        //Log.d"DATALOG", "check1=> " + getPetDataRequest);
    }

    @Override
    public void onResponse(Response arg0, String key) {
        switch (key) {
            case "GetPetListBySearch":
                try {
                    progressBar.setVisibility(View.INVISIBLE);
                    GetPetListResponse getPetListResponse = (GetPetListResponse) arg0.body();
                    int responseCode = Integer.parseInt(getPetListResponse.getResponse().getResponseCode());
                    profileList.clear();
                    if (responseCode == 109) {

                        if (getPetListResponse.getData().getPetList().isEmpty()){
                            Toast.makeText(this, "Pet Not Exist !", Toast.LENGTH_SHORT).show();
                        }else {
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchActivity.this);
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
                                    petList.setLastVisitEncryptedId(getPetListResponse.getData().getPetList().get(i).getLastVisitEncryptedId());
                                    petList.setIsAdopted(getPetListResponse.getData().getPetList().get(i).getIsAdopted());
                                    petList.setIsDonated(getPetListResponse.getData().getPetList().get(i).getIsDonated());


                                    profileList.add(petList);
                                }

                                SearchAdapter = new SearchAdapter(SearchActivity.this, profileList, this);
                                register_pet_RV.setAdapter(SearchAdapter);
                                SearchAdapter.notifyDataSetChanged();


                            } else {
                                //Log.d"No_DATA", "NO_DATA");
                            }
                        }


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

    @Override
    public void onViewDetailsClick(int position) {

        profileList.get(position).getPetUniqueId();
        Intent selectReportsIntent = new Intent(this, SelectPetReportsActivity.class);
        Bundle data = new Bundle();
        data.putString("pet_id", profileList.get(position).getId());
        data.putString("pet_name", profileList.get(position).getPetName());
        data.putString("pet_unique_id", profileList.get(position).getPetUniqueId());
        data.putString("pet_sex", profileList.get(position).getPetSex());
        data.putString("pet_owner_name", profileList.get(position).getPetParentName());
        data.putString("pet_owner_contact", profileList.get(position).getContactNumber());
        data.putString("pet_DOB", profileList.get(position).getDateOfBirth());
        data.putString("pet_encrypted_id", profileList.get(position).getEncryptedId());
        data.putString("pet_age", profileList.get(position).getPetAge());
        data.putString("pet_image_url", profileList.get(position).getPetProfileImageUrl());
        selectReportsIntent.putExtras(data);
        startActivity(selectReportsIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_arrow:
                onBackPressed();
                break;
        }
    }
}