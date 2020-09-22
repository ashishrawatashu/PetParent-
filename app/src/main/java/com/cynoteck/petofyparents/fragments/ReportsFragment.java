package com.cynoteck.petofyparents.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.activty.SelectPetReportsActivity;
import com.cynoteck.petofyparents.adapter.HospitalizationReportsAdapter;
import com.cynoteck.petofyparents.adapter.LabTestReportsAdapter;
import com.cynoteck.petofyparents.adapter.ReportsAdapter;
import com.cynoteck.petofyparents.adapter.ReportsTypeAdapter;
import com.cynoteck.petofyparents.adapter.TestAndXRayAdpater;
import com.cynoteck.petofyparents.adapter.UpdateClinicVisitAdapter;
import com.cynoteck.petofyparents.adapter.UpdateHospitalizationAdapter;
import com.cynoteck.petofyparents.adapter.UpdateLabTestAdpater;
import com.cynoteck.petofyparents.adapter.UpdateXRayAdpater;
import com.cynoteck.petofyparents.api.ApiClient;
import com.cynoteck.petofyparents.api.ApiResponse;
import com.cynoteck.petofyparents.api.ApiService;
import com.cynoteck.petofyparents.parameter.petReportsRequest.PetDataParams;
import com.cynoteck.petofyparents.parameter.petReportsRequest.PetDataRequest;
import com.cynoteck.petofyparents.response.getLabTestReportResponse.getPetLabWorkListResponse.PetLabWorkList;
import com.cynoteck.petofyparents.response.getPetHospitalizationResponse.getHospitalizationListResponse.PetHospitalizationsList;
import com.cynoteck.petofyparents.response.getPetReportsResponse.getPetClinicVisitsListsResponse.PetClinicVisitList;
import com.cynoteck.petofyparents.response.getPetReportsResponse.getPetListResponse.GetPetListResponse;
import com.cynoteck.petofyparents.response.getPetReportsResponse.getPetListResponse.PetList;
import com.cynoteck.petofyparents.response.getXRayReports.getPetTestAndXRayResponse.PetTestsAndXrayList;
import com.cynoteck.petofyparents.utils.Config;
import com.cynoteck.petofyparents.utils.Methods;
import com.cynoteck.petofyparents.utils.RegisterRecyclerViewClickListener;
import com.cynoteck.petofyparents.utils.ViewAndUpdateClickListener;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;


public class ReportsFragment extends Fragment implements ApiResponse, RegisterRecyclerViewClickListener, View.OnClickListener, TextWatcher {
    View view;
    Methods methods;
    CardView materialCardView;
    RecyclerView petList_RV;
    ReportsAdapter reportsAdapter;
    private ArrayList<PetList> categoryRecordArrayList;
    private ShimmerFrameLayout mShimmerViewContainer;
    TextView reports_headline_TV;
    EditText search_box;
    ImageView search_IV, back_arrow_IV;
    RelativeLayout search_boxRL;

    public ReportsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_reports, container, false);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);

        materialCardView = view.findViewById(R.id.toolbar);
        petList_RV=view.findViewById(R.id.petList_RV);
        reports_headline_TV = view.findViewById(R.id.reports_headline_TV);
        search_box = view.findViewById(R.id.search_box);
        search_IV = view.findViewById(R.id.search_IV);
        back_arrow_IV = view.findViewById(R.id.back_arrow_IV);
        search_boxRL = view.findViewById(R.id.search_boxRL);
        search_IV.setOnClickListener(this);
        back_arrow_IV.setOnClickListener(this);
        search_box.addTextChangedListener(this);
        methods = new Methods(getContext());

        if (methods.isInternetOn()){
            getPetList();

        }else {

            methods.DialogInternet();
        }

        return view;
    }

    private void getPetList() {
        PetDataParams getPetDataParams = new PetDataParams();
        getPetDataParams.setPageNumber("1");
        getPetDataParams.setPageSize("5");
        getPetDataParams.setSearch_Data("0");
        PetDataRequest getPetDataRequest = new PetDataRequest();
        getPetDataRequest.setData(getPetDataParams);

        ApiService<GetPetListResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getPetList(Config.token,getPetDataRequest), "GetPetList");
        Log.e("DATALOG","check1=> "+getPetDataRequest);
    }

    @Override
    public void onResponse(Response response, String key) {
        Log.e("DATALOG","=> "+response.body());

        switch (key){
            case "GetPetList":
                try {
                    GetPetListResponse getPetListResponse = (GetPetListResponse) response.body();
                    Log.d("DATALOG", getPetListResponse.toString());
                    int responseCode = Integer.parseInt(getPetListResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        search_IV.setVisibility(View.VISIBLE);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        petList_RV.setLayoutManager(linearLayoutManager);
                        reportsAdapter  = new ReportsAdapter(getContext(),getPetListResponse.getData().getPetList(),this);
                        categoryRecordArrayList = getPetListResponse.getData().getPetList();
                        petList_RV.setAdapter(reportsAdapter);
                        reportsAdapter.notifyDataSetChanged();
                        mShimmerViewContainer.setVisibility(View.GONE);
                        mShimmerViewContainer.stopShimmer();

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

        Log.d("error",t.getLocalizedMessage());

    }


    @Override
    public void onProductClick(int position) {
        categoryRecordArrayList.get(position).getPetUniqueId();
        Intent selectReportsIntent = new Intent(getActivity().getApplication(), SelectPetReportsActivity.class);
        Bundle data = new Bundle();
        Toast.makeText(getContext(), ""+categoryRecordArrayList.get(position).getId(), Toast.LENGTH_SHORT).show();
        data.putString("pet_id",categoryRecordArrayList.get(position).getId());
        data.putString("pet_name",categoryRecordArrayList.get(position).getPetName());
        data.putString("pet_unique_id",categoryRecordArrayList.get(position).getPetUniqueId());
        data.putString("pet_sex",categoryRecordArrayList.get(position).getPetSex());
        data.putString("pet_owner_name",categoryRecordArrayList.get(position).getPetParentName());
        data.putString("pet_owner_contact",categoryRecordArrayList.get(position).getContactNumber());

        selectReportsIntent.putExtras(data);
        startActivity(selectReportsIntent);
        getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
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
        switch (v.getId()){
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

        reportsAdapter.getFilter().filter(s.toString());

    }

    @Override
    public void afterTextChanged(Editable s) {

    }




}