package com.cynoteck.petofy.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cynoteck.petofy.R;
import com.cynoteck.petofy.adapter.AToZAlphabetsAdapter;
import com.cynoteck.petofy.adapter.PetBreedsAdapter;
import com.cynoteck.petofy.api.ApiClient;
import com.cynoteck.petofy.api.ApiResponse;
import com.cynoteck.petofy.api.ApiService;
import com.cynoteck.petofy.response.getpetbreedsResponse.GetPetBreedsResponse;
import com.cynoteck.petofy.response.getpetbreedsResponse.GetPetbreedsData;
import com.cynoteck.petofy.utils.Methods;
import com.cynoteck.petofy.onClicks.OnAlphabetClickListener;
import com.cynoteck.petofy.onClicks.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class PetBreedsActivity extends AppCompatActivity implements View.OnClickListener, ApiResponse, OnAlphabetClickListener, OnItemClickListener {
    ImageView back_arrow_IV;
    EditText search_breed_ET;
    RelativeLayout all_pet_RL;
    TextView all_TV;

    RecyclerView a_to_z_RV;

    LinearLayout dog_select_LL,cat_select_LL;
    ImageView cat_check_IV,dog_check_IV;
    TextView dog_TV,cat_TV;

    RecyclerView pet_breeds_RV;

    Methods methods;
    List<String> aToZList = new ArrayList<>();
    List<GetPetbreedsData> getPetbreedsDataList;

    AToZAlphabetsAdapter aToZAlphabetsAdapter;
    GetPetBreedsResponse getPetBreedsResponse;

    PetBreedsAdapter petBreedsAdapter;

    String petCategory ="1",selectedAlphabet="All";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_breeds);
        methods = new Methods(this);

        initialization();

        getPetBreed(petCategory);
        setRefreshAlphabetsList();
        searchingBreed();

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

    private void searchingBreed() {
        search_breed_ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                petBreedsAdapter.getFilter().filter(s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getPetBreed(String apiUrl) {
        ApiService<GetPetBreedsResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getPetBreedsList("services/getpetbreeds/"+apiUrl), "GetBreedsList");

    }

    private void initialization() {
        back_arrow_IV = findViewById(R.id.back_arrow_IV);
        search_breed_ET = findViewById(R.id.search_breed_ET);
        all_TV = findViewById(R.id.all_TV);
        all_pet_RL = findViewById(R.id.all_pet_RL);
        dog_select_LL = findViewById(R.id.dog_select_LL);
        cat_select_LL = findViewById(R.id.cat_select_LL);
        cat_check_IV = findViewById(R.id.cat_check_IV);
        dog_check_IV = findViewById(R.id.dog_check_IV);
        dog_TV = findViewById(R.id.dog_TV);
        cat_TV = findViewById(R.id.cat_TV);
        pet_breeds_RV = findViewById(R.id.pet_breeds_RV);
        a_to_z_RV = findViewById(R.id.a_to_z_RV);

        back_arrow_IV.setOnClickListener(this);
        cat_select_LL.setOnClickListener(this);
        dog_select_LL.setOnClickListener(this);
        all_pet_RL.setOnClickListener(this);
        search_breed_ET.setEnabled(false);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_arrow_IV:
                onBackPressed();
                break;

            case R.id.cat_select_LL:
                petCategory = "2";
                cat_check_IV.setVisibility(View.VISIBLE);
                dog_check_IV.setVisibility(View.GONE);
                dog_TV.setTextColor(this.getResources().getColor(R.color.gray_1));
                cat_TV.setTextColor(this.getResources().getColor(R.color.whiteColor));
                cat_select_LL.setBackgroundResource(R.drawable.pet_breed_active_state);
                dog_select_LL.setBackgroundResource(R.drawable.pet_breed_inactive_state);
                search_breed_ET.setEnabled(false);
                getPetBreed(petCategory);

                break;

            case R.id.dog_select_LL:
                petCategory = "1";
                cat_check_IV.setVisibility(View.GONE);
                dog_check_IV.setVisibility(View.VISIBLE);
                dog_TV.setTextColor(this.getResources().getColor(R.color.whiteColor));
                cat_TV.setTextColor(this.getResources().getColor(R.color.gray_1));
                cat_select_LL.setBackgroundResource(R.drawable.pet_breed_inactive_state);
                dog_select_LL.setBackgroundResource(R.drawable.pet_breed_active_state);
                search_breed_ET.setEnabled(false);
                getPetBreed(petCategory);

                break;

            case R.id.all_pet_RL:
                search_breed_ET.setEnabled(false);
                selectedAlphabet = "All";
                all_pet_RL.setBackgroundResource(R.drawable.pet_breed_active_state);
                all_TV.setTextColor(this.getResources().getColor(R.color.whiteColor));
                setRefreshAlphabetsList();
                getPetBreed(petCategory);

                break;
        }
    }

    @Override
    public void onResponse(Response arg0, String key) {
        switch (key){
            case "GetBreedsList":
                try {
                    getPetbreedsDataList = new ArrayList<>();
                    getPetBreedsResponse = (GetPetBreedsResponse) arg0.body();
                    if (getPetBreedsResponse.getResponse().getResponseCode().equals("109")){
                        for (int i = 0; i<getPetBreedsResponse.getData().size();i++){
                            GetPetbreedsData getPetbreedsData = new GetPetbreedsData();
                            if (selectedAlphabet.equals("All")){
                                getPetbreedsData.setBreedName(getPetBreedsResponse.getData().get(i).getBreedName());
                                getPetbreedsData.setImageUrl(getPetBreedsResponse.getData().get(i).getImageUrl());
                                getPetbreedsData.setSize(getPetBreedsResponse.getData().get(i).getSize());
                                getPetbreedsData.setLifeExpectancy(getPetBreedsResponse.getData().get(i).getLifeExpectancy());
                                getPetbreedsData.setWeight(getPetBreedsResponse.getData().get(i).getWeight());
                                getPetbreedsData.setHeight(getPetBreedsResponse.getData().get(i).getHeight());
                                getPetbreedsData.setColor(getPetBreedsResponse.getData().get(i).getColor());
                                getPetbreedsData.setDescription(getPetBreedsResponse.getData().get(i).getDescription());
                                getPetbreedsData.setCountryOrigion(getPetBreedsResponse.getData().get(i).getCountryOrigion());

                                getPetbreedsDataList.add(getPetbreedsData);
                            }
                            else if (getPetBreedsResponse.getData().get(i).getBreedName().substring(0,1).equals(selectedAlphabet)){
                                getPetbreedsData.setBreedName(getPetBreedsResponse.getData().get(i).getBreedName());
                                getPetbreedsData.setImageUrl(getPetBreedsResponse.getData().get(i).getImageUrl());
                                getPetbreedsData.setSize(getPetBreedsResponse.getData().get(i).getSize());
                                getPetbreedsData.setLifeExpectancy(getPetBreedsResponse.getData().get(i).getLifeExpectancy());
                                getPetbreedsData.setWeight(getPetBreedsResponse.getData().get(i).getWeight());
                                getPetbreedsData.setHeight(getPetBreedsResponse.getData().get(i).getHeight());
                                getPetbreedsData.setColor(getPetBreedsResponse.getData().get(i).getColor());
                                getPetbreedsData.setDescription(getPetBreedsResponse.getData().get(i).getDescription());
                                getPetbreedsData.setCountryOrigion(getPetBreedsResponse.getData().get(i).getCountryOrigion());

                                getPetbreedsDataList.add(getPetbreedsData);
                            }
                        }
                        GridLayoutManager linearLayoutManager = new GridLayoutManager(this,2);
                        pet_breeds_RV.setLayoutManager(linearLayoutManager);
                        petBreedsAdapter = new PetBreedsAdapter(this, getPetbreedsDataList, this);
                        pet_breeds_RV.setAdapter(petBreedsAdapter);
                        petBreedsAdapter.notifyDataSetChanged();
                        search_breed_ET.setEnabled(true);

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
        search_breed_ET.setEnabled(false);
        selectedAlphabet=(aToZList.get(position));
        all_pet_RL.setBackgroundResource(R.drawable.white_active_state_bg);
        all_TV.setTextColor(this.getResources().getColor(R.color.colorPrimary));
        getPetBreed(petCategory);
    }

    @Override
    public void onViewDetailsClick(int position) {

        Intent petBreedDetailsIntent = new Intent(this,PetBreedDetailsActivity.class);
        petBreedDetailsIntent.putExtra("breed_image_url",getPetbreedsDataList.get(position).getImageUrl());
        petBreedDetailsIntent.putExtra("breed_name",getPetbreedsDataList.get(position).getBreedName());
        petBreedDetailsIntent.putExtra("breed_size",getPetbreedsDataList.get(position).getSize());
        petBreedDetailsIntent.putExtra("breed_life",getPetbreedsDataList.get(position).getLifeExpectancy());
        petBreedDetailsIntent.putExtra("breed_weight",getPetbreedsDataList.get(position).getWeight());
        petBreedDetailsIntent.putExtra("breed_height",getPetbreedsDataList.get(position).getHeight());
        petBreedDetailsIntent.putExtra("breed_color",getPetbreedsDataList.get(position).getColor());
        petBreedDetailsIntent.putExtra("breed_origin",getPetbreedsDataList.get(position).getCountryOrigion());
        petBreedDetailsIntent.putExtra("breed_description",getPetbreedsDataList.get(position).getDescription());

        startActivity(petBreedDetailsIntent);

    }
}