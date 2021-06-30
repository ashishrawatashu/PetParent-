package com.cynoteck.petofyparents.activity;

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

import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.adapter.SearchKeywordAdapter;
import com.cynoteck.petofyparents.adapter.VetListAdapter;
import com.cynoteck.petofyparents.api.ApiClient;
import com.cynoteck.petofyparents.api.ApiResponse;
import com.cynoteck.petofyparents.api.ApiService;
import com.cynoteck.petofyparents.parameter.searchKeywordParams.SearchKeywordParams;
import com.cynoteck.petofyparents.parameter.searchKeywordParams.SearchKeywordRequest;
import com.cynoteck.petofyparents.parameter.searchProviderRequest.SearchProviderParameters;
import com.cynoteck.petofyparents.parameter.searchProviderRequest.SearchProviderRequest;
import com.cynoteck.petofyparents.response.getSearchKeywordResponse.SearchKeywordData;
import com.cynoteck.petofyparents.response.getSearchKeywordResponse.SearchKeywordResponse;
import com.cynoteck.petofyparents.response.getVetListResponse.GetVetListResponse;
import com.cynoteck.petofyparents.response.getVetListResponse.ProviderList;
import com.cynoteck.petofyparents.utils.Config;
import com.cynoteck.petofyparents.utils.Methods;
import com.cynoteck.petofyparents.onClicks.OnItemClickListener;
import com.cynoteck.petofyparents.onClicks.RegisterRecyclerViewClickListener;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Response;

public class SearchKeywordActivity extends AppCompatActivity implements ApiResponse, View.OnClickListener, OnItemClickListener, RegisterRecyclerViewClickListener {

    EditText search_keyword_ET;
    ImageView back_arrow_IV, cancel_IV;
    Methods methods;
    ProgressBar progressBar;
    RecyclerView search_keyword_RV,search_keyword_result_RV;
    ArrayList<SearchKeywordData> searchKeywordDataArrayList = new ArrayList<>();
    SearchKeywordAdapter searchKeywordAdapter;
    boolean stopShowPetList = true;
    VetListAdapter vetListAdapter;
    GetVetListResponse getVetListResponse;
    private ArrayList<ProviderList> providerLists = new ArrayList<>();
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_keyword);
        methods = new Methods(this);

        search_keyword_ET = findViewById(R.id.search_keyword_ET);
        back_arrow_IV = findViewById(R.id.back_arrow_IV);
        cancel_IV = findViewById(R.id.cancel_IV);
        search_keyword_RV = findViewById(R.id.search_keyword_RV);
        search_keyword_result_RV = findViewById(R.id.search_keyword_result_RV);
        progressBar = findViewById(R.id.progressBar);
        back_arrow_IV.setOnClickListener(this);
        cancel_IV.setOnClickListener(this);
        search_keyword_ET.requestFocus();
        searchKeywordAdapter = new SearchKeywordAdapter(this, searchKeywordDataArrayList, this);
        search_keyword_RV.setAdapter(searchKeywordAdapter);
        searchKeywordAdapter.notifyDataSetChanged();



        search_keyword_ET.addTextChangedListener(new TextWatcher() {
            long lastChange = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
//                if (charSequence.length() > 1) {
//                    new Handler().postDelayed(new Runnable() {
//                        public void run() {
//                            if (System.currentTimeMillis() - lastChange >= 300) {
//                                if (methods.isInternetOn()) {
//                                    search_keyword_RV.setVisibility(View.GONE);
//                                    if (!search_keyword_ET.getText().toString().equals("")) {
//                                        searchKeywordDataArrayList.clear();
//                                        searchKeywordAdapter.notifyDataSetChanged();
//                                        search_keyword_result_RV.setVisibility(View.GONE);
//                                        search_keyword_RV.setVisibility(View.GONE);
//                                        SearchKeywordParams searchKeywordParams = new SearchKeywordParams();
//                                        searchKeywordParams.setCityId(1);
//                                        searchKeywordParams.setSearchkeyword(search_keyword_ET.getText().toString());
//                                        SearchKeywordRequest searchKeywordRequest = new SearchKeywordRequest();
//                                        searchKeywordRequest.setData(searchKeywordParams);
//                                        getSearchData(searchKeywordRequest);
//
//                                    }
//                                } else {
//                                    methods.DialogInternet();
//                                }
//                            }
//                        }
//                    }, 300);
//                    lastChange = System.currentTimeMillis();
//
//                }



            }

            @Override
            public void afterTextChanged(Editable s) {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        // do your actual work here
                        SearchKeywordActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.VISIBLE);
                                search_keyword_RV.setVisibility(View.GONE);
                            }
                        });

                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        SearchKeywordActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.VISIBLE);
                                SearchKeywordParams searchKeywordParams = new SearchKeywordParams();
                                searchKeywordParams.setCityId(1);
                                searchKeywordParams.setSearchkeyword(search_keyword_ET.getText().toString());
                                SearchKeywordRequest searchKeywordRequest = new SearchKeywordRequest();
                                searchKeywordRequest.setData(searchKeywordParams);
                                getSearchData(searchKeywordRequest);
                            }
                        });

                        // hide keyboard as well?
                        // InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        // in.hideSoftInputFromWindow(searchText.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }, 600);
            }
        });


    }

    private void getSearchData(SearchKeywordRequest searchKeywordRequest) {
        ApiService<SearchKeywordResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().searchKeyWords(Config.token, searchKeywordRequest), "SearchKeyword");
        Log.e("DATALOG", "check1=> " + searchKeywordRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrow_IV:
                onBackPressed();
                break;

            case R.id.cancel_IV:
                search_keyword_ET.requestFocus();
                progressBar.setVisibility(View.GONE);
                search_keyword_ET.getText().clear();
                providerLists.clear();
                search_keyword_result_RV.setVisibility(View.GONE);
                searchKeywordDataArrayList.clear();
                cancel_IV.setVisibility(View.GONE);
                searchKeywordAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onResponse(Response arg0, String key) {

        switch (key) {
            case "SearchKeyword":
                try {
                    progressBar.setVisibility(View.GONE);
                    SearchKeywordResponse searchKeywordResponse = (SearchKeywordResponse) arg0.body();
                    int responseCode = Integer.parseInt(searchKeywordResponse.getResponse().getResponseCode());
                    Log.e("Response",searchKeywordResponse.getData().toString());
                    searchKeywordDataArrayList.clear();
                    if (responseCode == 109) {
                        if (searchKeywordResponse.getData().isEmpty()) {
//                            Toast.makeText(this, "Pet Not Exist !", Toast.LENGTH_SHORT).show();
                        }
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                        search_keyword_RV.setLayoutManager(linearLayoutManager);
                        if (searchKeywordResponse.getData().size() > 0) {cancel_IV.setVisibility(View.VISIBLE);
                            for (int i = 0; i < searchKeywordResponse.getData().size(); i++) {
                                SearchKeywordData searchKeywordData = new SearchKeywordData();
                                searchKeywordData.setId(searchKeywordResponse.getData().get(i).getId());
                                searchKeywordData.setSearchKeyWord(searchKeywordResponse.getData().get(i).getSearchKeyWord());
                                searchKeywordData.setSearchedOn(searchKeywordResponse.getData().get(i).getSearchedOn());

                                searchKeywordDataArrayList.add(searchKeywordData);
                            }
                            Log.e("List",methods.getRequestJson(searchKeywordDataArrayList));
                            if (stopShowPetList == true) {
                                search_keyword_RV.setVisibility(View.VISIBLE);
                            } else {
                                search_keyword_RV.setVisibility(View.GONE);
                            }
                            searchKeywordAdapter = new SearchKeywordAdapter(this, searchKeywordDataArrayList, this);
                            search_keyword_RV.setAdapter(searchKeywordAdapter);
                            searchKeywordAdapter.notifyDataSetChanged();

                        } else {
                            Log.e("No_DATA", "NO_DATA");
                        }


                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
                break;

            case "GetProviderBySearch":
                try {
                    progressBar.setVisibility(View.GONE);
                    providerLists.clear();
                    getVetListResponse = (GetVetListResponse) arg0.body();
                    Log.d("DATALOG", getVetListResponse.toString());
                    int responseCode = Integer.parseInt(getVetListResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        if (getVetListResponse.getData().getProviderList().isEmpty()) { } else {
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                            search_keyword_result_RV.setLayoutManager(linearLayoutManager);
                            providerLists = getVetListResponse.getData().getProviderList();
                            vetListAdapter = new VetListAdapter(this, providerLists, this);
                            search_keyword_result_RV.setAdapter(vetListAdapter);
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

    @Override
    public void onViewDetailsClick(int position) {
        progressBar.setVisibility(View.VISIBLE);
        search_keyword_RV.setVisibility(View.GONE);
        search_keyword_result_RV.setVisibility(View.VISIBLE);
        search_keyword_ET.setFocusable(false);
        SearchProviderParameters searchProviderParameters = new SearchProviderParameters();
        searchProviderParameters.setCityId(Integer.valueOf(Config.cityId));
        searchProviderParameters.setLatitude(Config.latitude);
        searchProviderParameters.setLongitude(Config.longitude);
        searchProviderParameters.setSearchkeyword(searchKeywordDataArrayList.get(position).getSearchKeyWord());

        SearchProviderRequest searchProviderRequest = new SearchProviderRequest();
        searchProviderRequest.setData(searchProviderParameters);

        ApiService<GetVetListResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getSearchProviderResults(Config.token, searchProviderRequest), "GetProviderBySearch");
        Log.e("DATALOG", "check1=> " + searchProviderRequest);
    }

    @Override
    public void onProductClick(int position) {
        Intent viewVetDetailsIntent = new Intent(this, VetFullProfileActivity.class);
        viewVetDetailsIntent.putExtra("EncryptId", getVetListResponse.getData().getProviderList().get(position).getEncryptedId());
        viewVetDetailsIntent.putExtra("vetUserId",getVetListResponse.getData().getProviderList().get(position).getId());
        viewVetDetailsIntent.putExtra("vet_fee",getVetListResponse.getData().getProviderList().get(position).getOnlineConsultationCharges());
        viewVetDetailsIntent.putExtra("vet_image_url",getVetListResponse.getData().getProviderList().get(position).getProfileImageURL());
        viewVetDetailsIntent.putExtra("vet_study",getVetListResponse.getData().getProviderList().get(position).getVetQualifications());
        viewVetDetailsIntent.putExtra("vet_rating",getVetListResponse.getData().getProviderList().get(position).getRating());
        viewVetDetailsIntent.putExtra("vet_address",getVetListResponse.getData().getProviderList().get(position).getAddress());
        viewVetDetailsIntent.putExtra("vet_name",getVetListResponse.getData().getProviderList().get(position).getName());
        viewVetDetailsIntent.putExtra("serviceTypeId", "0");
        viewVetDetailsIntent.putExtra("type", "add");
        viewVetDetailsIntent.putExtra("id", "");
        viewVetDetailsIntent.putExtra("pet_id", "");
        startActivity(viewVetDetailsIntent);
    }
}