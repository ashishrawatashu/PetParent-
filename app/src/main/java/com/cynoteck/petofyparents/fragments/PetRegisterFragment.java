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

import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.activty.AddPetRegister;
import com.cynoteck.petofyparents.activty.GetPetDetailsActivity;
import com.cynoteck.petofyparents.activty.PetIdCardActivity;
import com.cynoteck.petofyparents.adapter.RegisterPetAdapter;
import com.cynoteck.petofyparents.api.ApiClient;
import com.cynoteck.petofyparents.api.ApiResponse;
import com.cynoteck.petofyparents.api.ApiService;
import com.cynoteck.petofyparents.parameter.petReportsRequest.PetDataParams;
import com.cynoteck.petofyparents.parameter.petReportsRequest.PetDataRequest;
import com.cynoteck.petofyparents.response.getPetReportsResponse.getPetListResponse.GetPetListResponse;
import com.cynoteck.petofyparents.response.getPetReportsResponse.getPetListResponse.PetList;
import com.cynoteck.petofyparents.utils.Config;
import com.cynoteck.petofyparents.utils.Methods;
import com.cynoteck.petofyparents.utils.ViewDeatilsAndIdCardClick;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class PetRegisterFragment extends Fragment implements ApiResponse, ViewDeatilsAndIdCardClick,View.OnClickListener, TextWatcher {

    View view;
    Context context;
    Methods methods;
    CardView materialCardView;
    RecyclerView register_pet_RV;
    ImageView register_add_TV,search_register_pet,back_arrow_IV;
    private ArrayList<PetList> categoryRecordArrayList;
    RegisterPetAdapter registerPetAdapter;
    private ShimmerFrameLayout mShimmerViewContainer;
    RelativeLayout search_boxRL;
    EditText search_box;
    TextView regiter_pet_headline_TV;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public PetRegisterFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pet_register, container, false);
        init();
        getPetList();
        return view;
    }

    private void init() {
        methods = new Methods(context);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);

        materialCardView = view.findViewById(R.id.toolbar);
        register_pet_RV=view.findViewById(R.id.register_pet_RV);
        register_add_TV=view.findViewById(R.id.register_add_TV);
        search_register_pet=view.findViewById(R.id.search_register_pet);
        back_arrow_IV=view.findViewById(R.id.back_arrow_IV);
        search_boxRL=view.findViewById(R.id.search_boxRL);
        search_box=view.findViewById(R.id.search_box);
        regiter_pet_headline_TV=view.findViewById(R.id.regiter_pet_headline_TV);
        methods = new Methods(getContext());
        register_add_TV.setOnClickListener(this);
        search_register_pet.setOnClickListener(this);
        back_arrow_IV.setOnClickListener(this);
        search_box.addTextChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_add_TV:
                startActivity(new Intent(getActivity(), AddPetRegister.class));
                break;
            case R.id.search_register_pet:
                search_boxRL.setVisibility(View.VISIBLE);
                search_box.requestFocus();
                InputMethodManager imm1 = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm1.showSoftInput(search_box, InputMethodManager.SHOW_FORCED);
                search_register_pet.setVisibility(View.GONE);
                back_arrow_IV.setVisibility(View.VISIBLE);
                regiter_pet_headline_TV.setVisibility(View.GONE);
                register_add_TV.setVisibility(View.GONE);
                break;
            case R.id.back_arrow_IV:
                clearSearch();
                break;
        }

    }

    private void getPetList() {
        //       methods.showCustomProgressBarDialog(getContext());
        PetDataParams getPetDataParams = new PetDataParams();
        getPetDataParams.setPageNumber("1");//0
        getPetDataParams.setPageSize("2");//0
        getPetDataParams.setSearch_Data("");
        PetDataRequest getPetDataRequest = new PetDataRequest();
        getPetDataRequest.setData(getPetDataParams);

        ApiService<GetPetListResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getPetList(Config.token,getPetDataRequest), "GetPetList");
        Log.e("DATALOG","check1=> "+getPetDataRequest);


    }


    @Override
    public void onResponse(Response response, String key) {
//        methods.customProgressDismiss();
        switch (key){
            case "GetPetList":
                try {
                    GetPetListResponse getPetListResponse = (GetPetListResponse) response.body();
                    Log.d("GetPetList", getPetListResponse.toString());
                    int responseCode = Integer.parseInt(getPetListResponse.getResponse().getResponseCode());
                    Log.d("DATALOG", String.valueOf(getPetListResponse.getData().getPetList().get(0).getPetUniqueId()));
                    Log.d("DATALOG", String.valueOf(getPetListResponse.getData().getPetList().get(1).getPetUniqueId()));
                    Log.d("DATALOG", String.valueOf(getPetListResponse.getData().getPetList().get(2).getPetUniqueId()));
                    Log.d("DATALOG", String.valueOf(getPetListResponse.getData().getPetList().get(3).getPetUniqueId()));


                    if (responseCode== 109){
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        register_pet_RV.setLayoutManager(linearLayoutManager);
                        registerPetAdapter  = new RegisterPetAdapter(getContext(),getPetListResponse.getData().getPetList(),this);
                        register_pet_RV.setAdapter(registerPetAdapter);
                        registerPetAdapter.notifyDataSetChanged();
                        categoryRecordArrayList = getPetListResponse.getData().getPetList();
                        mShimmerViewContainer.stopShimmer();
                        search_register_pet.setVisibility(View.VISIBLE);
                        mShimmerViewContainer.setVisibility(View.GONE);

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
        //methods.customProgressDismiss();

    }


    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        mShimmerViewContainer.startShimmer();
        if (Config.backCall.equals("Added")) {
            Config.backCall ="";
            getPetList();
        }if (Config.backCall.equals("hit")) {
            Config.backCall ="";
            getPetList();
        }
    }

    @Override
    public void onPause() {
        /*mShimmerViewContainer.stopShimmerAnimation();*/
        super.onPause();
    }

    @Override
    public void onViewDetailsClick(int position) {
        Log.d("positionssss",""+position);
        Log.d("pet_id",""+categoryRecordArrayList.get(position).getId());

        StringTokenizer tokens = new StringTokenizer(categoryRecordArrayList.get(position).getId(), ".");
        String first = tokens.nextToken();// this will contain "Fruit"

        Intent intent=new Intent(getActivity(), GetPetDetailsActivity.class);
        intent.putExtra("pet_id",first);
        intent.putExtra("pet_category",categoryRecordArrayList.get(position).getPetCategory());
        intent.putExtra("pet_name",categoryRecordArrayList.get(position).getPetName());
        intent.putExtra("pet_sex",categoryRecordArrayList.get(position).getPetSex());
        intent.putExtra("pet_DOB",categoryRecordArrayList.get(position).getPetTestsAndXrey());
        intent.putExtra("pet_age",categoryRecordArrayList.get(position).getPetAge());
        intent.putExtra("pet_size",categoryRecordArrayList.get(position).getPetSize());
        intent.putExtra("pet_breed",categoryRecordArrayList.get(position).getPetBreed());
        intent.putExtra("pet_color",categoryRecordArrayList.get(position).getPetColor());
        intent.putExtra("pet_parent",categoryRecordArrayList.get(position).getPetParentName());
        intent.putExtra("pet_parent_contact",categoryRecordArrayList.get(position).getContactNumber());
        startActivity(intent);

    }

    @Override
    public void onIdCardClick(int position) {
        Log.d("positionssss",""+position);
        Intent intent = new Intent(getContext(), PetIdCardActivity.class);
        Bundle idBundle = new Bundle();
        idBundle.putString("id",categoryRecordArrayList.get(position).getId());
        intent.putExtras(idBundle);
        startActivity(intent);
    }

    private void clearSearch() {
        search_box.getText().clear();
        search_register_pet.setVisibility(View.VISIBLE);
        search_boxRL.setVisibility(View.GONE);
        back_arrow_IV.setVisibility(View.GONE);
        regiter_pet_headline_TV.setVisibility(View.VISIBLE);
        register_add_TV.setVisibility(View.VISIBLE);
        InputMethodManager imm1 = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm1.hideSoftInputFromWindow(search_box.getWindowToken(), 0);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        registerPetAdapter.getFilter().filter(charSequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}