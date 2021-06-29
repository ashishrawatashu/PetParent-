package com.cynoteck.petofyparents.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.adapter.AToZAlphabetsAdapter;
import com.cynoteck.petofyparents.adapter.PetNamesAdapter;
import com.cynoteck.petofyparents.api.ApiClient;
import com.cynoteck.petofyparents.api.ApiResponse;
import com.cynoteck.petofyparents.api.ApiService;
import com.cynoteck.petofyparents.response.getPetNamesResponse.GetPetNamesData;
import com.cynoteck.petofyparents.response.getPetNamesResponse.GetPetNamesResponse;
import com.cynoteck.petofyparents.utils.Methods;
import com.cynoteck.petofyparents.onClicks.OnAlphabetClickListener;
import com.cynoteck.petofyparents.onClicks.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class PetNamesActivity extends AppCompatActivity implements View.OnClickListener, ApiResponse, OnAlphabetClickListener, OnItemClickListener {

    ImageView back_arrow_IV;
    EditText search_names_ET;
    RelativeLayout all_pet_RL;
    TextView all_TV;

    RecyclerView a_to_z_RV;

    LinearLayout dog_select_LL,cat_select_LL;

    ImageView cat_check_IV,dog_check_IV;
    TextView dog_TV,cat_TV;
    LinearLayout male_select_LL,female_select_LL;
    ImageView female_check_IV,male_check_IV;
    TextView male_TV,female_TV;
    RecyclerView pet_names_RV;

    Methods methods;
    List<String> aToZList = new ArrayList<>();
    List<GetPetNamesData> getPetNamesDataList;
    AToZAlphabetsAdapter aToZAlphabetsAdapter;
    GetPetNamesResponse getPetNamesResponse;
    PetNamesAdapter petNamesAdapter;
    String petCategoryId ="1",selectedAlphabet="All",petGenderId="1";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_names);
        methods = new Methods(this);

        initialization();
        getPetNames(petCategoryId,petGenderId);

        setRefreshAlphabetsList();
        searchingPetNames();




    }

    private void getPetNames(String petCategoryId, String petGenderId) {
        search_names_ET.setEnabled(false);
        ApiService<GetPetNamesResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getNamesList("pet/getpetnames/"+petCategoryId+"/"+petGenderId), "GetPetNamesList");
        Log.e("GET_NAMES","pet/getpetnames/"+petCategoryId+"/"+petGenderId);
    }

    private void searchingPetNames() {
        search_names_ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                petNamesAdapter.getFilter().filter(s);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setRefreshAlphabetsList() {
        for(char c = 'A'; c <= 'Z'; ++c) {
            aToZList.add(String.valueOf(c));
        }
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        a_to_z_RV.setLayoutManager(horizontalLayoutManagaer);
        aToZAlphabetsAdapter = new AToZAlphabetsAdapter(this, aToZList, this);
        a_to_z_RV.setAdapter(aToZAlphabetsAdapter);
        aToZAlphabetsAdapter.notifyDataSetChanged();
    }

    private void initialization() {
        back_arrow_IV = findViewById(R.id.back_arrow_IV);
        search_names_ET = findViewById(R.id.search_names_ET);
        all_TV = findViewById(R.id.all_TV);
        all_pet_RL = findViewById(R.id.all_pet_RL);
        dog_select_LL = findViewById(R.id.dog_select_LL);
        cat_select_LL = findViewById(R.id.cat_select_LL);
        cat_check_IV = findViewById(R.id.cat_check_IV);
        dog_check_IV = findViewById(R.id.dog_check_IV);
        dog_TV = findViewById(R.id.dog_TV);
        cat_TV = findViewById(R.id.cat_TV);
        pet_names_RV = findViewById(R.id.pet_names_RV);
        a_to_z_RV = findViewById(R.id.a_to_z_RV);

        male_select_LL = findViewById(R.id.male_select_LL);
        female_select_LL = findViewById(R.id.female_select_LL);
        male_TV = findViewById(R.id.male_TV);
        female_TV = findViewById(R.id.female_TV);
        male_check_IV = findViewById(R.id.male_check_IV);
        female_check_IV = findViewById(R.id.female_check_IV);


        back_arrow_IV.setOnClickListener(this);
        cat_select_LL.setOnClickListener(this);
        dog_select_LL.setOnClickListener(this);
        all_pet_RL.setOnClickListener(this);
        male_select_LL.setOnClickListener(this);
        female_select_LL.setOnClickListener(this);
        search_names_ET.setEnabled(false);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_arrow_IV:
                onBackPressed();
                break;

            case R.id.cat_select_LL:
                petCategoryId = "2";
                cat_check_IV.setVisibility(View.VISIBLE);
                dog_check_IV.setVisibility(View.GONE);
                dog_TV.setTextColor(this.getResources().getColor(R.color.gray_1));
                cat_TV.setTextColor(this.getResources().getColor(R.color.whiteColor));
                cat_select_LL.setBackgroundResource(R.drawable.pet_breed_active_state);
                dog_select_LL.setBackgroundResource(R.drawable.pet_breed_inactive_state);
                search_names_ET.setEnabled(false);
                getPetNames(petCategoryId, petGenderId);
                break;

            case R.id.dog_select_LL:
                petCategoryId = "1";
                cat_check_IV.setVisibility(View.GONE);
                dog_check_IV.setVisibility(View.VISIBLE);
                dog_TV.setTextColor(this.getResources().getColor(R.color.whiteColor));
                cat_TV.setTextColor(this.getResources().getColor(R.color.gray_1));
                cat_select_LL.setBackgroundResource(R.drawable.pet_breed_inactive_state);
                dog_select_LL.setBackgroundResource(R.drawable.pet_breed_active_state);
                search_names_ET.setEnabled(false);
                getPetNames(petCategoryId, petGenderId);
                break;

            case R.id.male_select_LL:
                petGenderId ="1";
                male_check_IV.setVisibility(View.VISIBLE);
                female_check_IV.setVisibility(View.GONE);
                female_TV.setTextColor(this.getResources().getColor(R.color.gray_1));
                male_TV.setTextColor(this.getResources().getColor(R.color.whiteColor));
                male_select_LL.setBackgroundResource(R.drawable.pet_breed_active_state);
                female_select_LL.setBackgroundResource(R.drawable.pet_breed_inactive_state);
                search_names_ET.setEnabled(false);
                getPetNames(petCategoryId, petGenderId);

                break;


            case R.id.female_select_LL:
                petGenderId = "2";
                female_check_IV.setVisibility(View.VISIBLE);
                male_check_IV.setVisibility(View.GONE);
                male_TV.setTextColor(this.getResources().getColor(R.color.gray_1));
                female_TV.setTextColor(this.getResources().getColor(R.color.whiteColor));
                female_select_LL.setBackgroundResource(R.drawable.pet_breed_active_state);
                male_select_LL.setBackgroundResource(R.drawable.pet_breed_inactive_state);
                search_names_ET.setEnabled(false);
                getPetNames(petCategoryId, petGenderId);

                break;

            case R.id.all_pet_RL:
                search_names_ET.setEnabled(false);
                selectedAlphabet = "All";
                all_pet_RL.setBackgroundResource(R.drawable.pet_breed_active_state);
                all_TV.setTextColor(this.getResources().getColor(R.color.whiteColor));
                setRefreshAlphabetsList();
                getPetNames(petCategoryId,petGenderId);

                break;
        }
    }

    @Override
    public void onResponse(Response arg0, String key) {
        switch (key){
            case "GetPetNamesList":
                try {
                    getPetNamesDataList = new ArrayList<>();
                    getPetNamesResponse = (GetPetNamesResponse) arg0.body();
                    search_names_ET.setEnabled(true);
                    if (getPetNamesResponse.getResponse().getResponseCode().equals("109")){
                        for (int i = 0; i<getPetNamesResponse.getData().size();i++){
                            GetPetNamesData getPetNamesData = new GetPetNamesData();
                            if (selectedAlphabet.equals("All")){
                                getPetNamesData.setName(getPetNamesResponse.getData().get(i).getName());
                                getPetNamesDataList.add(getPetNamesData);
                            }
                            else if (getPetNamesResponse.getData().get(i).getName().substring(0,1).equals(selectedAlphabet)){
                                getPetNamesData.setName(getPetNamesResponse.getData().get(i).getName());

                                getPetNamesDataList.add(getPetNamesData);
                            }
                        }
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                        pet_names_RV.setLayoutManager(linearLayoutManager);
                        petNamesAdapter = new PetNamesAdapter(this, getPetNamesDataList, this);
                        pet_names_RV.setAdapter(petNamesAdapter);
                        petNamesAdapter.notifyDataSetChanged();
                        pet_names_RV.setEnabled(true);

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }



                break;
        }
    }

    @Override
    public void onError(Throwable t, String key) {

    }

    @Override
    public void onAlphabetClickListener(int position) {
        search_names_ET.setEnabled(false);
        selectedAlphabet=(aToZList.get(position));
        all_pet_RL.setBackgroundResource(R.drawable.white_active_state_bg);
        all_TV.setTextColor(this.getResources().getColor(R.color.colorPrimary));
        getPetNames(petCategoryId, petGenderId);
    }

    @Override
    public void onViewDetailsClick(int position) {

    }
}