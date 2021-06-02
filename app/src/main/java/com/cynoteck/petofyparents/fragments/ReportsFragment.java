package com.cynoteck.petofyparents.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Response;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.activity.SelectPetReportsActivity;
import com.cynoteck.petofyparents.adapter.ReportsAdapter;
import com.cynoteck.petofyparents.api.ApiClient;
import com.cynoteck.petofyparents.api.ApiResponse;
import com.cynoteck.petofyparents.api.ApiService;
import com.cynoteck.petofyparents.parameter.petReportsRequest.PetDataParams;
import com.cynoteck.petofyparents.parameter.petReportsRequest.PetDataRequest;
import com.cynoteck.petofyparents.response.getPetReportsResponse.getPetListResponse.GetPetListResponse;
import com.cynoteck.petofyparents.response.getPetReportsResponse.getPetListResponse.PetList;
import com.cynoteck.petofyparents.utils.Config;
import com.cynoteck.petofyparents.utils.Methods;
import com.cynoteck.petofyparents.utils.RegisterRecyclerViewClickListener;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;


public class ReportsFragment extends Fragment implements ApiResponse, RegisterRecyclerViewClickListener, View.OnClickListener, TextWatcher {
    View view;
    Methods methods;
    CardView materialCardView;
    RecyclerView petList_RV;
    ReportsAdapter reportsAdapter;
    private ArrayList<PetList> profileList ;
    private ShimmerFrameLayout mShimmerViewContainer;
    TextView reports_headline_TV;
    AutoCompleteTextView search_box;
    ImageView empty_IV, search_IV, back_arrow_IV;
    RelativeLayout search_boxRL;
    NestedScrollView nested_scroll_view;
    int page = 1, pagelimit = 10;
    ProgressBar progressBar;

    public ReportsFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_reports, container, false);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        nested_scroll_view = view.findViewById(R.id.nested_scroll_view);
        materialCardView = view.findViewById(R.id.toolbar);
        progressBar = view.findViewById(R.id.progressBar);
        petList_RV = view.findViewById(R.id.register_pet_RV);
        reports_headline_TV = view.findViewById(R.id.reports_headline_TV);
        search_box = view.findViewById(R.id.search_box);
        search_IV = view.findViewById(R.id.search_IV);
        back_arrow_IV = view.findViewById(R.id.back_arrow_IV);
        search_boxRL = view.findViewById(R.id.search_boxRL);
        empty_IV = view.findViewById(R.id.empty_IV);
        search_IV.setOnClickListener(this);
        back_arrow_IV.setOnClickListener(this);
        search_box.addTextChangedListener(this);
        methods = new Methods(getContext());
        profileList = new ArrayList<>();

        if (methods.isInternetOn()) {
            getPetList();

        } else {

            methods.DialogInternet();
        }
        nested_scroll_view.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    page++;
                    progressBar.setVisibility(View.VISIBLE);
                    getFromScroll(page, pagelimit);
                }
            }
        });
        getPet();
        return view;
    }
    private void getPet() {
        search_box.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("dataChange", "afterTextChanged" + new String(editable.toString()));
                String value = editable.toString();
                petSearchDependsOnPrefix(value);
            }
        });


        search_box.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = search_box.getText().toString();
                String[] city_array = value.split("\\(");

                search_box.setText(city_array[0]);
            }
        });

    }

    private void petSearchDependsOnPrefix(String prefix) {
        PetDataParams getPetDataParams = new PetDataParams();
        getPetDataParams.setPageNumber(0);//0
        getPetDataParams.setPageSize(10);//0
        getPetDataParams.setSearch_Data(prefix);
        PetDataRequest getPetDataRequest = new PetDataRequest();
        getPetDataRequest.setData(getPetDataParams);

        ApiService<GetPetListResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getPetList(Config.token, getPetDataRequest), "GetPetListBySearch");
        Log.e("DATALOG", "check1=> " + getPetDataRequest);
    }

    private void getFromScroll(int page, int pageLimit) {
        PetDataParams getPetDataParams = new PetDataParams();
        getPetDataParams.setPageNumber(page);//0
        getPetDataParams.setPageSize(pageLimit);//0
        getPetDataParams.setSearch_Data("");
        PetDataRequest getPetDataRequest = new PetDataRequest();
        getPetDataRequest.setData(getPetDataParams);

        ApiService<GetPetListResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getPetList(Config.token, getPetDataRequest), "GetFromScroll");
        Log.e("DATALOG", "check1=> " + getPetDataRequest);

    }

    private void getPetList() {
        PetDataParams getPetDataParams = new PetDataParams();
        getPetDataParams.setPageNumber(1);
        getPetDataParams.setPageSize(10);
        getPetDataParams.setSearch_Data("0");
        PetDataRequest getPetDataRequest = new PetDataRequest();
        getPetDataRequest.setData(getPetDataParams);
        ApiService<GetPetListResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getPetList(Config.token, getPetDataRequest), "GetPetList");
    }

    @Override
    public void onResponse(Response response, String key) {
        switch (key) {
            case "GetPetList":
                try {
                    mShimmerViewContainer.setVisibility(View.GONE);
                    mShimmerViewContainer.stopShimmer();
                    GetPetListResponse getPetListResponse = (GetPetListResponse) response.body();
                    int responseCode = Integer.parseInt(getPetListResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        if (getPetListResponse.getData().getPetList().isEmpty()) {
                            empty_IV.setVisibility(View.VISIBLE);
                            search_IV.setVisibility(View.INVISIBLE);
                        } else {
                            search_IV.setVisibility(View.VISIBLE);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                            petList_RV.setLayoutManager(linearLayoutManager);
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
                                    petList.setPetColor(getPetListResponse.getData().getPetList().get(i).getPetColor());
                                    petList.setPetAge(getPetListResponse.getData().getPetList().get(i).getPetAge());
                                    profileList.add(petList);
                                }
                                reportsAdapter = new ReportsAdapter(getContext(), profileList, this);
                                petList_RV.setAdapter(reportsAdapter);
                                reportsAdapter.notifyDataSetChanged();
                                search_IV.setVisibility(View.VISIBLE);
                            }

                        }


                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            case "GetFromScroll":
                try {
                    progressBar.setVisibility(View.GONE);
                    GetPetListResponse getPetListResponse = (GetPetListResponse) response.body();
                    int responseCode = Integer.parseInt(getPetListResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        petList_RV.setLayoutManager(linearLayoutManager);
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
                                petList.setPetColor(getPetListResponse.getData().getPetList().get(i).getPetColor());
                                petList.setPetAge(getPetListResponse.getData().getPetList().get(i).getPetAge());
                                profileList.add(petList);
                            }
                            reportsAdapter = new ReportsAdapter (getContext(), profileList, this);
                            petList_RV.setAdapter(reportsAdapter);
                            reportsAdapter.notifyDataSetChanged();
                            search_IV.setVisibility(View.VISIBLE);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            case "GetPetListBySearch":
                try {
                    GetPetListResponse getPetListResponse = (GetPetListResponse) response.body();
                    int responseCode = Integer.parseInt(getPetListResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        petList_RV.setLayoutManager(linearLayoutManager);
                        if (getPetListResponse.getData().getPetList().size() > 0) {
                            profileList.clear();
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
                                petList.setPetColor(getPetListResponse.getData().getPetList().get(i).getPetColor());
                                petList.setPetAge(getPetListResponse.getData().getPetList().get(i).getPetAge());
                                profileList.add(petList);
                            }
                            progressBar.setVisibility(View.GONE);
                            reportsAdapter = new ReportsAdapter(getContext(), profileList, this);
                            petList_RV.setAdapter(reportsAdapter);
                            reportsAdapter.notifyDataSetChanged();
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
        Log.d("error", t.getLocalizedMessage());
    }


    @Override
    public void onProductClick(int position) {
        profileList.get(position).getPetUniqueId();
        Intent selectReportsIntent = new Intent(getActivity().getApplication(), SelectPetReportsActivity.class);
        Bundle data = new Bundle();
        data.putString("pet_id", profileList.get(position).getId());
        data.putString("pet_name", profileList.get(position).getPetName());
        data.putString("pet_unique_id", profileList.get(position).getPetUniqueId());
        data.putString("pet_sex", profileList.get(position).getPetSex());
        data.putString("pet_owner_name", profileList.get(position).getPetParentName());
        data.putString("pet_owner_contact", profileList.get(position).getContactNumber());
        data.putString("pet_encryt_id", profileList.get(position).getEncryptedId());
        data.putString("pet_age", profileList.get(position).getPetAge());
        data.putString("pet_DOB", profileList.get(position).getDateOfBirth());
        data.putString("pet_encrypted_id", profileList.get(position).getEncryptedId());

        selectReportsIntent.putExtras(data);
        startActivity(selectReportsIntent);
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        clearSearch();

    }

    private void clearSearch() {
        search_box.getText().clear();
        search_boxRL.setVisibility(View.GONE);
        back_arrow_IV.setVisibility(View.GONE);
        reports_headline_TV.setVisibility(View.VISIBLE);
        InputMethodManager imm1 = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm1.hideSoftInputFromWindow(search_box.getWindowToken(), 0);
    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmer();

    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmer();
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_box:

                break;

            case R.id.back_arrow_IV:
                clearSearch();
                break;

            case R.id.search_IV:
                search_boxRL.setVisibility(View.VISIBLE);
                search_box.requestFocus();
                InputMethodManager imm1 = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm1.showSoftInput(search_box, InputMethodManager.SHOW_FORCED);
                back_arrow_IV.setVisibility(View.VISIBLE);
                reports_headline_TV.setVisibility(View.GONE);
                break;
        }
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