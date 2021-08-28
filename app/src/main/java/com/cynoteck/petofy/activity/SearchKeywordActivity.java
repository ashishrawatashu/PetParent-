package com.cynoteck.petofy.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cynoteck.petofy.R;
import com.cynoteck.petofy.adapter.SearchAdapter;
import com.cynoteck.petofy.adapter.SearchKeywordAdapter;
import com.cynoteck.petofy.adapter.SearchResultsAdapter;
import com.cynoteck.petofy.adapter.VetListAdapter;
import com.cynoteck.petofy.api.ApiClient;
import com.cynoteck.petofy.api.ApiResponse;
import com.cynoteck.petofy.api.ApiService;
import com.cynoteck.petofy.parameter.searchKeywordParams.SearchKeywordParams;
import com.cynoteck.petofy.parameter.searchKeywordParams.SearchKeywordRequest;
import com.cynoteck.petofy.parameter.searchProviderRequest.SearchProviderParameters;
import com.cynoteck.petofy.parameter.searchProviderRequest.SearchProviderRequest;
import com.cynoteck.petofy.response.getSearchKeywordResponse.SearchKeywordData;
import com.cynoteck.petofy.response.getSearchKeywordResponse.SearchKeywordResponse;
import com.cynoteck.petofy.response.getSearchResultsResponse.GetSearchResultsResponse;
import com.cynoteck.petofy.response.getSearchResultsResponse.Provider;
import com.cynoteck.petofy.response.getVetListResponse.GetVetListResponse;
import com.cynoteck.petofy.response.getVetListResponse.ProviderList;
import com.cynoteck.petofy.utils.Config;
import com.cynoteck.petofy.utils.Methods;
import com.cynoteck.petofy.onClicks.OnItemClickListener;
import com.cynoteck.petofy.onClicks.RegisterRecyclerViewClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Response;

public class SearchKeywordActivity extends AppCompatActivity implements ApiResponse, View.OnClickListener,RegisterRecyclerViewClickListener {

    EditText                        search_keyword_ET;
    ImageView                       back_arrow_IV, cancel_IV,search_icon_IV;
    Methods                         methods;
    ProgressBar                     progressBar,progress_bar_below;
    RecyclerView                    search_keyword_RV,search_keyword_result_RV;
    SearchResultsAdapter            searchListAdapter;
    ArrayList<Provider>             providerLists;
    LinearLayout                    all_provider_LL,cosultation_LL,pet_shops_LL,grooming_LL,hostels_LL,training_LL;
    TextView                        search_headline_TV;
    NestedScrollView                nested_scroll_view;
    Integer                         page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_keyword);
        methods = new Methods(this);
        providerLists = new ArrayList<>();
        search_keyword_ET           = findViewById(R.id.search_keyword_ET);
        back_arrow_IV               = findViewById(R.id.back_arrow_IV);
        cancel_IV                   = findViewById(R.id.cancel_IV);
        search_keyword_RV           = findViewById(R.id.search_keyword_RV);
        search_keyword_result_RV    = findViewById(R.id.search_keyword_result_RV);
        progressBar                 = findViewById(R.id.progressBar);
        search_icon_IV              = findViewById(R.id.search_icon_IV);
        cosultation_LL              = findViewById(R.id.cosultation_LL);
        pet_shops_LL                = findViewById(R.id.pet_shops_LL);
        grooming_LL                 = findViewById(R.id.grooming_LL);
        hostels_LL                  = findViewById(R.id.hostels_LL);
        training_LL                 = findViewById(R.id.training_LL);
        all_provider_LL             = findViewById(R.id.all_provider_LL);
        search_headline_TV          = findViewById(R.id.search_headline_TV);
        progress_bar_below          = findViewById(R.id.progress_bar_below);
        nested_scroll_view          = findViewById(R.id.nested_scroll_view);

        back_arrow_IV.setOnClickListener(this);
        search_icon_IV.setOnClickListener(this);
        cancel_IV.setOnClickListener(this);
        hostels_LL.setOnClickListener(this);
        grooming_LL.setOnClickListener(this);
        training_LL.setOnClickListener(this);
        pet_shops_LL.setOnClickListener(this);
        cosultation_LL.setOnClickListener(this);
        search_keyword_ET.requestFocus();

        search_keyword_ET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String searchText = search_keyword_ET.getText().toString();
                    if (searchText.equals("")){
                        search_keyword_ET.setFocusable(true);
                    }else {

                        getSearchResults(searchText,0);
                    }
                    return true;
                }
                return false;
            }
        });

        nested_scroll_view.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    page++;
                    String searchText = search_keyword_ET.getText().toString();
                    progress_bar_below.setVisibility(View.VISIBLE);
                    getSearchResultsScroll(searchText,page);

                }
            }
        });
    }

    private void getSearchResultsScroll(String searchText, Integer page) {
        search_keyword_RV.setVisibility(View.GONE);
        search_keyword_result_RV.setVisibility(View.VISIBLE);
        SearchProviderParameters searchProviderParameters = new SearchProviderParameters();
        searchProviderParameters.setPage(page);
        searchProviderParameters.setCityId(Integer.valueOf(Config.cityId));
        searchProviderParameters.setSearchText(searchText);

        SearchProviderRequest searchProviderRequest = new SearchProviderRequest();
        searchProviderRequest.setData(searchProviderParameters);

        ApiService<GetSearchResultsResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getSearchProviderResults(Config.token, searchProviderRequest), "GetProviderBySearchScroll");
//        //Log.d"DATALOG", "check1=> " + methods.getRequestJson(searchProviderRequest));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrow_IV:
                onBackPressed();
                break;

            case R.id.search_icon_IV:
                String searchText = search_keyword_ET.getText().toString();
                if (searchText.equals("")){
                    search_keyword_ET.setFocusable(true);
                }else {
                    getSearchResults(searchText,0);
                }
                break;

            case R.id.cancel_IV:
                search_keyword_ET.requestFocus();
                progressBar.setVisibility(View.GONE);
                search_keyword_ET.getText().clear();
                providerLists.clear();
                cancel_IV.setVisibility(View.GONE);
                break;

            case R.id.cosultation_LL:
                search_keyword_ET.setText("Pet Consultation");
                getSearchResults("Pet Consultation",0);
                search_keyword_ET.setSelection(search_keyword_ET.getText().length());

                break;

            case R.id.hostels_LL:
                search_keyword_ET.setText("Pet Hostel");
                getSearchResults("Pet Hostel",0);
                search_keyword_ET.setSelection(search_keyword_ET.getText().length());

                break;

            case R.id.grooming_LL:
                search_keyword_ET.setText("Pet Grooming");
                getSearchResults("Pet Grooming",0);
                search_keyword_ET.setSelection(search_keyword_ET.getText().length());

                break;

            case R.id.pet_shops_LL:
                search_keyword_ET.setText("Pet Shop");
                getSearchResults("Pet Shop",0);
                search_keyword_ET.setSelection(search_keyword_ET.getText().length());

                break;

            case R.id.training_LL:
                search_keyword_ET.setText("Pet Training");
                getSearchResults("Pet Training",0);
                search_keyword_ET.setSelection(search_keyword_ET.getText().length());
                break;
        }
    }

    private void getSearchResults(String searchText ,Integer page) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Hide:
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        providerLists.clear();
        progressBar.setVisibility(View.VISIBLE);
        search_keyword_RV.setVisibility(View.GONE);
        search_keyword_result_RV.setVisibility(View.VISIBLE);
        SearchProviderParameters searchProviderParameters = new SearchProviderParameters();
        searchProviderParameters.setPage(page);
        searchProviderParameters.setCityId(Integer.valueOf(Config.cityId));
        searchProviderParameters.setSearchText(searchText);

        SearchProviderRequest searchProviderRequest = new SearchProviderRequest();
        searchProviderRequest.setData(searchProviderParameters);

        ApiService<GetSearchResultsResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getSearchProviderResults(Config.token, searchProviderRequest), "GetProviderBySearch");
//        //Log.d"DATALOG", "check1=> " + methods.getRequestJson(searchProviderRequest));
    }


    @Override
    public void onResponse(Response arg0, String key) {

        switch (key) {
            case "GetProviderBySearch":
                try {
                    progress_bar_below.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    providerLists.clear();
                    GetSearchResultsResponse getVetListResponse = (GetSearchResultsResponse) arg0.body();
                    //Log.d"DATALOG", methods.getRequestJson(getVetListResponse));
                    int responseCode = Integer.parseInt(getVetListResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        if (getVetListResponse.getData().getProviderList().isEmpty()) { } else {
                            for (int i = 0; i < getVetListResponse.getData().getProviderList().size(); i++) {
                                Provider providerList = new Provider();
                                providerList.setId(getVetListResponse.getData().getProviderList().get(i).getId());
                                providerList.setName(getVetListResponse.getData().getProviderList().get(i).getName());
                                providerList.setCompany(getVetListResponse.getData().getProviderList().get(i).getCompany());

                                providerList.setVetQualifications(getVetListResponse.getData().getProviderList().get(i).getVetQualifications());
                                providerList.setAddress(getVetListResponse.getData().getProviderList().get(i).getAddress());
                                providerList.setOnlineConsultationCharges(getVetListResponse.getData().getProviderList().get(i).getOnlineConsultationCharges());
                                providerList.setProfileImageURL(getVetListResponse.getData().getProviderList().get(i).getProfileImageURL());
                                providerList.setRating(getVetListResponse.getData().getProviderList().get(i).getRating());
                                providerList.setEncryptedId(getVetListResponse.getData().getProviderList().get(i).getEncryptedId());
                                providerLists.add(providerList);
                            }
//                            //Log.d"LIST_SIZE",providerLists.size()+"");
                            all_provider_LL.setVisibility(View.GONE);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                            search_keyword_result_RV.setLayoutManager(linearLayoutManager);
                            searchListAdapter = new SearchResultsAdapter(this, providerLists, this);
                            search_keyword_result_RV.setAdapter(searchListAdapter);
                            searchListAdapter.notifyDataSetChanged();
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case "GetProviderBySearchScroll":
                try {
                    progress_bar_below.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    GetSearchResultsResponse getVetListResponse = (GetSearchResultsResponse) arg0.body();
                    //Log.d"DATALOG_scrool", methods.getRequestJson(getVetListResponse));
                    int responseCode = Integer.parseInt(getVetListResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        if (getVetListResponse.getData().getProviderList().isEmpty()) { } else {
                            for (int i = 0; i < getVetListResponse.getData().getProviderList().size(); i++) {
                                Provider providerList = new Provider();
                                providerList.setId(getVetListResponse.getData().getProviderList().get(i).getId());
                                providerList.setCompany(getVetListResponse.getData().getProviderList().get(i).getCompany());
                                providerList.setName(getVetListResponse.getData().getProviderList().get(i).getName());
                                providerList.setVetQualifications(getVetListResponse.getData().getProviderList().get(i).getVetQualifications());
                                providerList.setAddress(getVetListResponse.getData().getProviderList().get(i).getAddress());
                                providerList.setOnlineConsultationCharges(getVetListResponse.getData().getProviderList().get(i).getOnlineConsultationCharges());
                                providerList.setProfileImageURL(getVetListResponse.getData().getProviderList().get(i).getProfileImageURL());
                                providerList.setRating(getVetListResponse.getData().getProviderList().get(i).getRating());
                                providerList.setEncryptedId(getVetListResponse.getData().getProviderList().get(i).getEncryptedId());
                                providerLists.add(providerLists.size()-1,providerList);
                            }
//                          Log.d"LIST_SIZE_SCROOL",providerLists.size()+"");
                            all_provider_LL.setVisibility(View.GONE);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                            search_keyword_result_RV.setLayoutManager(linearLayoutManager);
                            searchListAdapter = new SearchResultsAdapter(this, providerLists, this);
                            search_keyword_result_RV.setAdapter(searchListAdapter);
                            searchListAdapter.notifyDataSetChanged();
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

        //Log.d"ERROR",t.getLocalizedMessage());
    }

    @Override
    public void onProductClick(int position) {
        Intent viewVetDetailsIntent = new Intent(this, VetFullProfileActivity.class);
        viewVetDetailsIntent.putExtra("EncryptId", providerLists.get(position).getEncryptedId());
        viewVetDetailsIntent.putExtra("vetUserId",providerLists.get(position).getId());
        viewVetDetailsIntent.putExtra("vet_fee",providerLists.get(position).getOnlineConsultationCharges());
        viewVetDetailsIntent.putExtra("vet_image_url",providerLists.get(position).getProfileImageURL());
        viewVetDetailsIntent.putExtra("vet_study",providerLists.get(position).getVetQualifications());
        viewVetDetailsIntent.putExtra("vet_rating",providerLists.get(position).getRating());
        viewVetDetailsIntent.putExtra("vet_address",providerLists.get(position).getAddress());
        viewVetDetailsIntent.putExtra("vet_name",providerLists.get(position).getName());
        viewVetDetailsIntent.putExtra("serviceTypeId", "1");
        viewVetDetailsIntent.putExtra("type", "add");
        viewVetDetailsIntent.putExtra("id", "");
        viewVetDetailsIntent.putExtra("pet_id", "");
        startActivity(viewVetDetailsIntent);



    }
}