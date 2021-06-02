package com.cynoteck.petofyparents.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.adapter.VetListAdapter;
import com.cynoteck.petofyparents.api.ApiClient;
import com.cynoteck.petofyparents.api.ApiResponse;
import com.cynoteck.petofyparents.api.ApiService;
import com.cynoteck.petofyparents.parameter.getVetListParams.GetVetListParams;
import com.cynoteck.petofyparents.parameter.getVetListParams.GetVetListRequest;
import com.cynoteck.petofyparents.response.getVetListResponse.GetVetListResponse;
import com.cynoteck.petofyparents.response.getVetListResponse.ProviderList;
import com.cynoteck.petofyparents.utils.Config;
import com.cynoteck.petofyparents.utils.Methods;
import com.cynoteck.petofyparents.utils.RegisterRecyclerViewClickListener;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

import retrofit2.Response;

public class VetListActivity extends AppCompatActivity implements ApiResponse, RegisterRecyclerViewClickListener, View.OnClickListener {
    Methods methods;
    CardView materialCardView;
    RecyclerView vetList_RV;
    VetListAdapter vetListAdapter;
    private ArrayList<ProviderList> providerLists;
    private ShimmerFrameLayout mShimmerViewContainer;
    TextView reports_headline_TV;
    EditText search_box;
    ImageView search_IV, back_arrow_IV;
    RelativeLayout search_boxRL;
    GetVetListResponse getVetListResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vet_list);
        methods = new Methods(this);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        vetList_RV = findViewById(R.id.vetList_RV);
        back_arrow_IV = findViewById(R.id.back_arrow_IV);
        back_arrow_IV.setOnClickListener(this);


        if (methods.isInternetOn()) {
            getPetList();
        } else {
            methods.DialogInternet();
        }


    }

    private void getPetList() {
        GetVetListParams getVetListParams = new GetVetListParams();
        getVetListParams.setLattitude("30.29075786");
        getVetListParams.setLongitude("77.97655438");
        getVetListParams.setViewMore("true");

        GetVetListRequest getVetListRequest = new GetVetListRequest();
        getVetListRequest.setData(getVetListParams);

        ApiService<GetVetListResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getVetList(Config.token, getVetListRequest), "GetVetList");
        Log.e("DATALOG", "check1=> " + getVetListRequest);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrow_IV:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onResponse(Response response, String key) {

        Log.e("DATALOG", "=> " + response.body());

        switch (key) {
            case "GetVetList":
                try {
                    getVetListResponse = (GetVetListResponse) response.body();
                    Log.d("DATALOG", getVetListResponse.toString());
                    int responseCode = Integer.parseInt(getVetListResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        if (getVetListResponse.getData().getProviderList().isEmpty()) {
                            mShimmerViewContainer.setVisibility(View.GONE);
                            mShimmerViewContainer.stopShimmer();
                            Toast.makeText(this, "No Data Found!", Toast.LENGTH_SHORT).show();
                        } else {
//                            search_IV.setVisibility(View.VISIBLE);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                            vetList_RV.setLayoutManager(linearLayoutManager);
                            vetListAdapter = new VetListAdapter(this, getVetListResponse.getData().getProviderList(), this);
                            providerLists = getVetListResponse.getData().getProviderList();
                            vetList_RV.setAdapter(vetListAdapter);
                            vetListAdapter.notifyDataSetChanged();
                            mShimmerViewContainer.setVisibility(View.GONE);
                            mShimmerViewContainer.stopShimmer();
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
    public void onProductClick(int position) {
//        Intent intent = new Intent(this, AddUpdateAppointmentActivity.class);
//        intent.putExtra("type", "add");
//        intent.putExtra("id", "");
//        intent.putExtra("pet_id", "");
//        intent.putExtra("vetUserId", getVetListResponse.getData().getProviderList().get(position).getId());
//        startActivity(intent);

        startActivity(new Intent(this,VetFullProfileActivity.class));


    }
}