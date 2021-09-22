package com.cynoteck.petofy.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cynoteck.petofy.R;
import com.cynoteck.petofy.adapter.CityListAdapter;
import com.cynoteck.petofy.adapter.VetListAdapter;
import com.cynoteck.petofy.api.ApiClient;
import com.cynoteck.petofy.api.ApiResponse;
import com.cynoteck.petofy.api.ApiService;
import com.cynoteck.petofy.parameter.getServiceProvidersListRequest.GetServiceProviderListParams;
import com.cynoteck.petofy.parameter.getServiceProvidersListRequest.GetServiceProviderListRequest;
import com.cynoteck.petofy.parameter.getVetListParams.GetVetListParams;
import com.cynoteck.petofy.parameter.getVetListParams.GetVetListRequest;
import com.cynoteck.petofy.response.getCityListWithStateResponse.GetCityListWithStateResponse;
import com.cynoteck.petofy.response.getVetListResponse.GetVetListResponse;
import com.cynoteck.petofy.response.getVetListResponse.ProviderList;
import com.cynoteck.petofy.utils.Config;
import com.cynoteck.petofy.utils.Methods;
import com.cynoteck.petofy.onClicks.OnItemClickListener;
import com.cynoteck.petofy.onClicks.RegisterRecyclerViewClickListener;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

import retrofit2.Response;

public class ConsultationListActivity extends AppCompatActivity implements View.OnClickListener, ApiResponse, OnItemClickListener, RegisterRecyclerViewClickListener {

    MaterialCardView                back_arrow_CV;
    LinearLayout                    location_LL;
    EditText                        search_vet_ET;
    RecyclerView                    vet_list_RV;
    Methods                         methods;
    TextView                        location_TV, consultation_TV,heading_one_TV,heading_two_TV;
    VetListAdapter                  vetListAdapter;
    LinearLayout                    search_bar_LL;
    ImageView                       empty_IV;

    //location Dialog.........
    Dialog                          location_dialog;
    MaterialCardView                cancel_CV;
    LinearLayout                    current_location_LL;
    GetCityListWithStateResponse    getCityListWithStateResponse;
    CityListAdapter                 cityListAdapter;
    RecyclerView                    city_list_RV;
    ProgressBar                     dialog_progressBar, progressBar;
    EditText                        search_location_ET;
    GetVetListResponse              getVetListResponse;
    private ArrayList<ProviderList> providerLists;
    String                          seeMore = "true", cityId = "0";
    SharedPreferences               sharedPreferences;
    SharedPreferences.Editor        login_editor;
    String                          serviceTypeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation_list);
        methods         = new Methods(this);
        Intent intent   = getIntent();
        serviceTypeId   = intent.getStringExtra("serviceTypeId");

        init();

        if (Config.cityId.equals("")) {
            progressBar.setVisibility(View.GONE);
            showLocationDialog();
            ApiService<GetCityListWithStateResponse> service = new ApiService<>();
            service.get(this, ApiClient.getApiInterface().getCityListWithState(Config.token), "GetCityListWithState");
        } else {
//            if (serviceTypeId.equals("1")){
//                getVetList();
//
//            }else {
                getServiceProviderList(serviceTypeId);
//            }
        }

        search_vet_ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                vetListAdapter.getFilter().filter(s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void getServiceProviderList(String serviceTypeId) {
        search_vet_ET.setEnabled(false);
        search_bar_LL.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        vet_list_RV.setVisibility(View.GONE);
        empty_IV.setVisibility(View.GONE);
        GetServiceProviderListParams getServiceProviderListParams = new GetServiceProviderListParams();
        getServiceProviderListParams.setCityId(Config.cityId);
        getServiceProviderListParams.setLattitude(Config.latitude);
        getServiceProviderListParams.setLongitude(Config.longitude);
        getServiceProviderListParams.setServiceTypeId(serviceTypeId);

        GetServiceProviderListRequest getServiceProviderListRequest = new GetServiceProviderListRequest();
        getServiceProviderListRequest.setData(getServiceProviderListParams);

        ApiService<GetVetListResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getServiceProvidersListByServiceAndCity(Config.token, getServiceProviderListRequest), "GetVetList");
        Log.d("DATALOG", "check1=> " + methods.getRequestJson(getServiceProviderListRequest));

    }

    @SuppressLint("SetTextI18n")
    private void init() {
        empty_IV        =   findViewById(R.id.empty_IV);
        back_arrow_CV   =   findViewById(R.id.back_arrow_CV);
        location_LL     =   findViewById(R.id.location_LL);
        search_vet_ET   =   findViewById(R.id.search_vet_ET);
        vet_list_RV     =   findViewById(R.id.vet_list_RV);
        location_TV     =   findViewById(R.id.location_TV);
        progressBar     =   findViewById(R.id.progressBar);
        consultation_TV =   findViewById(R.id.consultation_TV);
        heading_one_TV  =   findViewById(R.id.heading_one_TV);
        heading_two_TV  =   findViewById(R.id.heading_two_TV);
        search_bar_LL   =   findViewById(R.id.search_bar_LL);


        search_vet_ET.setEnabled(false);
        back_arrow_CV.setOnClickListener(this);
        location_LL.setOnClickListener(this);

        location_TV.setText(Config.cityFullName);

        if (serviceTypeId.equals("1")){
            consultation_TV.setText("CONSULTATIONS");
            search_vet_ET.setHint("Search Veterinarian by name");
        }else if (serviceTypeId.equals("3")){
            consultation_TV.setText("HOSTELS");
            heading_one_TV.setText("Select Hostels Near you");
            heading_two_TV.setText("Find the best hostel for your pet with Petofy");
            search_vet_ET.setHint("Search Hostels by name");
        }else if (serviceTypeId.equals("2")){
            consultation_TV.setText("GROOMING");
            heading_one_TV.setText("Select Your Desired Groomer");
            heading_two_TV.setText("Find the best groomers for your pet with Petofy");
            search_vet_ET.setHint("Search Groomer by name");

        }else if (serviceTypeId.equals("11")){
            consultation_TV.setText("PET SHOPS");
            heading_one_TV.setText("Select Your Desired Pet Shop");
            heading_two_TV.setText("Find the best shop for your pet with Petofy");
            search_vet_ET.setHint("Search Pet Shop by name");

        }else if (serviceTypeId.equals("6")){
            consultation_TV.setText("TRAINING");
            heading_one_TV.setText("Select Your Desired Trainer");
            search_vet_ET.setHint("Search Trainer by name");
            heading_two_TV.setText("Find the best trainers for your pet with Petofy");

        }


    }


    private void getVetList() {
        GetVetListParams getVetListParams = new GetVetListParams();
        getVetListParams.setLattitude(Config.latitude);
        getVetListParams.setLongitude(Config.longitude);
        getVetListParams.setViewMore(seeMore);
        getVetListParams.setCityId(Config.cityId);

        GetVetListRequest getVetListRequest = new GetVetListRequest();
        getVetListRequest.setData(getVetListParams);

        ApiService<GetVetListResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getVetList(Config.token, getVetListRequest), "GetVetList");
        Log.d("DATALOG", "check1=> " + methods.getRequestJson(getVetListRequest));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.location_LL:
                showLocationDialog();
                ApiService<GetCityListWithStateResponse> service = new ApiService<>();
                service.get(this, ApiClient.getApiInterface().getCityListWithState(Config.token), "GetCityListWithState");
                break;

            case R.id.back_arrow_CV:
                onBackPressed();
                break;

            case R.id.cancel_CV:
                location_dialog.dismiss();
                break;

        }
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onResponse(Response arg0, String key) {
        switch (key) {
            case "GetCityListWithState":
                try {
                    dialog_progressBar.setVisibility(View.GONE);
                    //Log.d"rer", methods.getRequestJson(arg0.body()));
                    getCityListWithStateResponse = (GetCityListWithStateResponse) arg0.body();
                    if (getCityListWithStateResponse.getResponse().getResponseCode().equals("109")) {
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                        city_list_RV.setLayoutManager(linearLayoutManager);
                        cityListAdapter = new CityListAdapter(this, getCityListWithStateResponse.getData(), this);
                        city_list_RV.setAdapter(cityListAdapter);
                        cityListAdapter.notifyDataSetChanged();
                        search_location_ET.setEnabled(true);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            case "GetVetList":
                try {
                    progressBar.setVisibility(View.GONE);
                    getVetListResponse = (GetVetListResponse) arg0.body();
                    search_vet_ET.setEnabled(true);
//                    Log.d("DATALOG", getVetListResponse.toString());
                    int responseCode = Integer.parseInt(getVetListResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        if (getVetListResponse.getData().getProviderList().isEmpty()) {
                            empty_IV.setVisibility(View.VISIBLE);
                            Toast.makeText(this, "No data found in your city !", Toast.LENGTH_SHORT).show();
                        } else {
                            search_bar_LL.setVisibility(View.VISIBLE);
                            vet_list_RV.setVisibility(View.VISIBLE);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                            vet_list_RV.setLayoutManager(linearLayoutManager);
                            vetListAdapter = new VetListAdapter(this, getVetListResponse.getData().getProviderList(), this);
                            providerLists = getVetListResponse.getData().getProviderList();
                            vet_list_RV.setAdapter(vetListAdapter);
                            vetListAdapter.notifyDataSetChanged();
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

    private void showLocationDialog() {
        location_dialog         = new Dialog(this);
        location_dialog.setContentView(R.layout.location_dialog);
        cancel_CV               = location_dialog.findViewById(R.id.cancel_CV);
        city_list_RV            = location_dialog.findViewById(R.id.city_list_RV);
        search_location_ET      = location_dialog.findViewById(R.id.search_location_ET);
        current_location_LL     = location_dialog.findViewById(R.id.current_location_LL);
        dialog_progressBar      = location_dialog.findViewById(R.id.progressBar);

        current_location_LL.setOnClickListener(this);
        cancel_CV.setOnClickListener(this);
        search_location_ET.setEnabled(false);

        search_location_ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cityListAdapter.getFilter().filter(s);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        WindowManager.LayoutParams lp   = new WindowManager.LayoutParams();
        Window window                   = location_dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width                        = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height                       = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        location_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        location_dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        location_dialog.show();
    }

    @Override
    public void onViewDetailsClick(int position) {
        location_TV.setText(getCityListWithStateResponse.getData().get(position).getCityName());
        sharedPreferences = this.getSharedPreferences("userDetails", 0);
        login_editor = sharedPreferences.edit();
        login_editor.putString("CityId", getCityListWithStateResponse.getData().get(position).getId());
        login_editor.putString("cityName", getCityListWithStateResponse.getData().get(position).getCity1());
        login_editor.putString("CityFullName", getCityListWithStateResponse.getData().get(position).getCityName());
        login_editor.apply();
        Config.latitude     = sharedPreferences.getString("userLatitude", "");
        Config.longitude    = sharedPreferences.getString("userLongitude", "");
        Config.cityId       = sharedPreferences.getString("CityId", "");
        Config.cityName     = sharedPreferences.getString("cityName", "");
        Config.cityFullName = sharedPreferences.getString("CityFullName", "");
        location_dialog.dismiss();
//        getVetList();
        getServiceProviderList(serviceTypeId);
    }

    @Override
    public void onProductClick(int position) {

        Intent viewVetDetailsIntent = new Intent(this, VetFullProfileActivity.class);
        viewVetDetailsIntent.putExtra("EncryptId", getVetListResponse.getData().getProviderList().get(position).getEncryptedId());
        viewVetDetailsIntent.putExtra("vetUserId", getVetListResponse.getData().getProviderList().get(position).getId());
        viewVetDetailsIntent.putExtra("vet_fee", getVetListResponse.getData().getProviderList().get(position).getOnlineConsultationCharges());
        viewVetDetailsIntent.putExtra("vet_image_url", getVetListResponse.getData().getProviderList().get(position).getProfileImageURL());
        viewVetDetailsIntent.putExtra("vet_study", getVetListResponse.getData().getProviderList().get(position).getVetQualifications());
        viewVetDetailsIntent.putExtra("vet_rating", getVetListResponse.getData().getProviderList().get(position).getRating());
        viewVetDetailsIntent.putExtra("vet_address", getVetListResponse.getData().getProviderList().get(position).getAddress());
        viewVetDetailsIntent.putExtra("vet_name", getVetListResponse.getData().getProviderList().get(position).getName());
        viewVetDetailsIntent.putExtra("serviceTypeId", serviceTypeId);
        viewVetDetailsIntent.putExtra("type", "add");
        viewVetDetailsIntent.putExtra("id", "");
        viewVetDetailsIntent.putExtra("pet_id", "");
        startActivity(viewVetDetailsIntent);


    }
}