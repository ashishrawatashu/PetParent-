package com.cynoteck.petofyparents.activty;

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

import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.adapter.RegisterPetAdapter;
import com.cynoteck.petofyparents.adapter.SearchAdapter;
import com.cynoteck.petofyparents.api.ApiClient;
import com.cynoteck.petofyparents.api.ApiResponse;
import com.cynoteck.petofyparents.api.ApiService;
import com.cynoteck.petofyparents.parameter.petReportsRequest.PetDataParams;
import com.cynoteck.petofyparents.parameter.petReportsRequest.PetDataRequest;
import com.cynoteck.petofyparents.response.getPetReportsResponse.getPetListResponse.GetPetListResponse;
import com.cynoteck.petofyparents.response.getPetReportsResponse.getPetListResponse.PetList;
import com.cynoteck.petofyparents.utils.Config;
import com.cynoteck.petofyparents.utils.SearchInterface;
import com.cynoteck.petofyparents.utils.ViewDeatilsAndIdCardClick;

import java.util.ArrayList;

import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements TextWatcher, ApiResponse, SearchInterface {
EditText searchpet;
ImageView backarrowimageview;
    ArrayList<PetList> profileList = new ArrayList<>();
    RecyclerView register_pet_RV;
    SearchAdapter SearchAdapter;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchpet=(EditText)findViewById(R.id.search_pet);
        backarrowimageview=(ImageView)findViewById(R.id.back_arrow);
        register_pet_RV=(RecyclerView) findViewById(R.id.register_pet_RV);
        progressBar=findViewById(R.id.progressBar);
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
                String value=s.toString();
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
        service.get( SearchActivity.this, ApiClient.getApiInterface().getPetList(Config.token,getPetDataRequest), "GetPetListBySearch");
        Log.e("DATALOG","check1=> "+getPetDataRequest);
    }

    @Override
    public void onResponse(Response arg0, String key) {
switch(key){
    case "GetPetListBySearch":
        try {
            GetPetListResponse getPetListResponse = (GetPetListResponse) arg0.body();
            Log.d("GetPetListBySearch", getPetListResponse.toString());
            int responseCode = Integer.parseInt(getPetListResponse.getResponse().getResponseCode());
            profileList.clear();
            if (responseCode== 109){
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchActivity.this);
                register_pet_RV.setLayoutManager(linearLayoutManager);
                if(getPetListResponse.getData().getPetList().size()>0)
                {

                    for(int i=0; i<getPetListResponse.getData().getPetList().size();i++)
                    {
                        PetList petList=new PetList();
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

                        profileList.add(petList);
                    }

                    SearchAdapter = new SearchAdapter(SearchActivity.this,profileList,this);
                    register_pet_RV.setAdapter(SearchAdapter);
                    SearchAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);


                }
                else
                {
                    Log.e("No_DATA","NO_DATA");
//                    Toast.makeText(SearchActivity.this, "Data Not found", Toast.LENGTH_SHORT).show();
                }


            }

        }
        catch(Exception e) {
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
        data.putString("pet_id",profileList.get(position).getId());
        data.putString("pet_name",profileList.get(position).getPetName());
        data.putString("pet_unique_id",profileList.get(position).getPetUniqueId());
        data.putString("pet_sex",profileList.get(position).getPetSex());
        data.putString("pet_owner_name",profileList.get(position).getPetParentName());
        data.putString("pet_owner_contact",profileList.get(position).getContactNumber());
        data.putString("pet_encryt_id",profileList.get(position).getEncryptedId());
        data.putString("pet_age",profileList.get(position).getPetAge());
        data.putString("pet_DOB",profileList.get(position).getDateOfBirth());
        data.putString("pet_encrypted_id",profileList.get(position).getEncryptedId());

        selectReportsIntent.putExtras(data);
        startActivity(selectReportsIntent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);

    }
}