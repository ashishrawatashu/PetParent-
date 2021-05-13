package com.cynoteck.petofyparents.fragments;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.activty.AddPetWithQRCodeActivity;
import com.cynoteck.petofyparents.activty.PetProfileActivity;
import com.cynoteck.petofyparents.activty.SearchActivity;
import com.cynoteck.petofyparents.adapter.RegisterPetAdapter;
import com.cynoteck.petofyparents.adapter.ScanPetListAdapter;
import com.cynoteck.petofyparents.api.ApiClient;
import com.cynoteck.petofyparents.api.ApiResponse;
import com.cynoteck.petofyparents.api.ApiService;
import com.cynoteck.petofyparents.parameter.addPetToVetRegisterUsingQRCodeRequest.AddPetToVetRegisterUsingQRCodeParams;
import com.cynoteck.petofyparents.parameter.addPetToVetRegisterUsingQRCodeRequest.AddPetToVetRegisterUsingQRRequest;
import com.cynoteck.petofyparents.parameter.petReportsRequest.PetDataParams;
import com.cynoteck.petofyparents.parameter.petReportsRequest.PetDataRequest;
import com.cynoteck.petofyparents.response.getPetReportsResponse.getPetListResponse.GetPetListResponse;
import com.cynoteck.petofyparents.response.getPetReportsResponse.getPetListResponse.PetList;
import com.cynoteck.petofyparents.response.registerParentWithQRResponse.RegisterParentWithQRResponse;
import com.cynoteck.petofyparents.utils.Config;
import com.cynoteck.petofyparents.utils.Methods;
import com.cynoteck.petofyparents.utils.ViewDeatilsAndIdCardClick;

import java.util.ArrayList;
import java.util.StringTokenizer;

import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.CLIPBOARD_SERVICE;

public class AfterScanScreenFragment extends Fragment implements TextWatcher, View.OnClickListener, ApiResponse, ViewDeatilsAndIdCardClick {

    View view;
    RecyclerView register_pet_RV;
    ScanPetListAdapter scanPetListAdapter;
    ArrayList<PetList> profileList;
    LinearLayout add_pet_LL;
    ImageView empty_IV, vet_profile_pic;
    EditText search_box_add_new;
    TextView clinic_name_TV, vet_name_TV;
    ImageView star_one, star_two, star_three, star_four, star_five;
    RelativeLayout search_boxRL;
    String veterinarianUserId, veterinarianName, clinicName, Rating, profileImageUrl;
    Methods methods;
    int page = 1, pagelimit = 10;
    ProgressBar progressBar, progressBarForSearchBar;
    private static final int ADD_PET_WITH_QR_CODE = 200;
    String api_type="";
    NestedScrollView nestedScrollView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_after_scan_screen, container, false);
        methods = new Methods(getActivity());
        ClipboardManager mCbm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        mCbm.clearPrimaryClip();
        init();
        empty_IV.setVisibility(View.GONE);
        Bundle extras = this.getArguments();
        veterinarianUserId = extras.getString("veterinarianUserId");
        veterinarianName = extras.getString("veterinarianName");
        clinicName = extras.getString("clinicName");
        Rating = extras.getString("Rating");


        profileImageUrl = extras.getString("profileImageUrl");
        Glide.with(getContext())
                .load(profileImageUrl)
                .into(vet_profile_pic);
        clinic_name_TV.setText(clinicName);
        vet_name_TV.setText(veterinarianName);

        setRating();


        if (methods.isInternetOn()) {
            getPetList(page, pagelimit);

        } else {

            methods.DialogInternet();
        }
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    page++;
                    progressBar.setVisibility(View.VISIBLE);
                    getFromScroll(page, pagelimit);
                }
            }
        });

        return view;
    }

    private void getFromScroll(int page, int pagelimit) {
        PetDataParams getPetDataParams = new PetDataParams();
        getPetDataParams.setPageNumber(page);//0
        getPetDataParams.setPageSize(pagelimit);//0
        getPetDataParams.setSearch_Data("");
        PetDataRequest getPetDataRequest = new PetDataRequest();
        getPetDataRequest.setData(getPetDataParams);

        ApiService<GetPetListResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getPetList(Config.token, getPetDataRequest), "GetFromScroll");
        Log.e("DATALOG", "check1=> " + getPetDataRequest);

    }

    private void getPetList(int page, int pagelimit) {
        //       methods.showCustomProgressBarDialog(getContext());
        PetDataParams getPetDataParams = new PetDataParams();
        getPetDataParams.setPageNumber(page);//0
        getPetDataParams.setPageSize(pagelimit);//0
        getPetDataParams.setSearch_Data("");
        PetDataRequest getPetDataRequest = new PetDataRequest();
        getPetDataRequest.setData(getPetDataParams);

        ApiService<GetPetListResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getPetList(Config.token, getPetDataRequest), "GetPetList");
        Log.e("DATALOG", "check1=> " + getPetDataRequest);


    }

    private void setRating() {
        int totalRating = Integer.parseInt(Rating);
        int totalRate = Math.round(totalRating);
        if (totalRate == 0) {
            star_one.setImageResource(R.drawable.star_without_rate);
            star_two.setImageResource(R.drawable.star_without_rate);
            star_three.setImageResource(R.drawable.star_without_rate);
            star_four.setImageResource(R.drawable.star_without_rate);
            star_five.setImageResource(R.drawable.star_without_rate);
        } else if (totalRate == 1) {
            star_one.setImageResource(R.drawable.star_with_rate);
            star_two.setImageResource(R.drawable.star_without_rate);
            star_three.setImageResource(R.drawable.star_without_rate);
            star_four.setImageResource(R.drawable.star_without_rate);
            star_five.setImageResource(R.drawable.star_without_rate);
        } else if (totalRate == 2) {
            star_one.setImageResource(R.drawable.star_with_rate);
            star_two.setImageResource(R.drawable.star_with_rate);
            star_three.setImageResource(R.drawable.star_without_rate);
            star_four.setImageResource(R.drawable.star_without_rate);
            star_five.setImageResource(R.drawable.star_without_rate);
        } else if (totalRate == 3) {
            star_one.setImageResource(R.drawable.star_with_rate);
            star_two.setImageResource(R.drawable.star_with_rate);
            star_three.setImageResource(R.drawable.star_with_rate);
            star_four.setImageResource(R.drawable.star_without_rate);
            star_five.setImageResource(R.drawable.star_without_rate);
        } else if (totalRate == 4) {
            star_one.setImageResource(R.drawable.star_with_rate);
            star_two.setImageResource(R.drawable.star_with_rate);
            star_three.setImageResource(R.drawable.star_with_rate);
            star_four.setImageResource(R.drawable.star_with_rate);
            star_five.setImageResource(R.drawable.star_without_rate);
        } else if (totalRate == 5) {
            star_one.setImageResource(R.drawable.star_with_rate);
            star_two.setImageResource(R.drawable.star_with_rate);
            star_three.setImageResource(R.drawable.star_with_rate);
            star_four.setImageResource(R.drawable.star_with_rate);
            star_five.setImageResource(R.drawable.star_with_rate);
        }
    }

    private void init() {
        search_boxRL=view.findViewById(R.id.search_boxRL);
        empty_IV =view.findViewById(R.id.empty_IV);
        nestedScrollView=view.findViewById(R.id.nested_scroll_view);
        vet_profile_pic = view.findViewById(R.id.vet_profile_pic);
        clinic_name_TV = view.findViewById(R.id.clinic_name_TV);
        vet_name_TV = view.findViewById(R.id.vet_name_TV);
        search_box_add_new = view.findViewById(R.id.search_box_add_new);
        add_pet_LL = view.findViewById(R.id.add_pet_LL);
        vet_profile_pic = view.findViewById(R.id.vet_profile_pic);
        star_one = view.findViewById(R.id.star_one);
        star_two = view.findViewById(R.id.star_two);
        star_three = view.findViewById(R.id.star_three);
        star_four = view.findViewById(R.id.star_four);
        star_five = view.findViewById(R.id.star_five);
        register_pet_RV = view.findViewById(R.id.register_pet_RV);
        progressBar = view.findViewById(R.id.progressBar);
        progressBarForSearchBar = view.findViewById(R.id.progressBarForSearchBar);

        add_pet_LL.setOnClickListener(this);
        profileList = new ArrayList<>();

        search_box_add_new.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String value = s.toString();
                petSearchDependsOnPrefix(value);
            }
        });

    }

    private void petSearchDependsOnPrefix(String prefix) {
        progressBarForSearchBar.setVisibility(View.VISIBLE);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==ADD_PET_WITH_QR_CODE){
            if (resultCode==RESULT_OK){
                profileList.clear();
                empty_IV.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                if (methods.isInternetOn()) {
                    getPetList(page, pagelimit);

                } else {

                    methods.DialogInternet();
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_pet_LL:

                Intent add_pet_with_QR_intent = new Intent(getContext(), AddPetWithQRCodeActivity.class);
                add_pet_with_QR_intent.putExtra("veterinarianUserId", veterinarianUserId);
                startActivityForResult(add_pet_with_QR_intent, ADD_PET_WITH_QR_CODE);

                break;

        }
    }

    @Override
    public void onResponse(Response response, String key) {
        switch (key) {
            case "GetPetList":
                try {
                    progressBar.setVisibility(View.GONE);
                    GetPetListResponse getPetListResponse = (GetPetListResponse) response.body();
                    Log.d("GetPetList", getPetListResponse.toString());
                    int responseCode = Integer.parseInt(getPetListResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {

                        if (getPetListResponse.getData().getPetList().isEmpty()){
                            empty_IV.setVisibility(View.VISIBLE);
                            search_boxRL.setVisibility(View.GONE);
                        }else {
                            empty_IV.setVisibility(View.GONE);
                            search_boxRL.setVisibility(View.VISIBLE);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                            register_pet_RV.setLayoutManager(linearLayoutManager);
                            if (getPetListResponse.getData().getPetList().size() > 0) {
                                Log.d("DATALOG", String.valueOf(getPetListResponse.getData().getPetList().get(0).getPetUniqueId()));
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
                                    petList.setPetAge(getPetListResponse.getData().getPetList().get(i).getPetAge());
                                    profileList.add(petList);
                                }
                                progressBar.setVisibility(View.GONE);
                                scanPetListAdapter = new ScanPetListAdapter(getContext(), profileList, this);
                                register_pet_RV.setAdapter(scanPetListAdapter);
                                scanPetListAdapter.notifyDataSetChanged();
                                register_pet_RV.setVisibility(View.VISIBLE);

                            }
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;


            case "GetFromScroll":
                try {
                    GetPetListResponse getPetListResponse = (GetPetListResponse) response.body();
                    Log.d("GetPetList", getPetListResponse.toString());
                    int responseCode = Integer.parseInt(getPetListResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        register_pet_RV.setLayoutManager(linearLayoutManager);
                        if (getPetListResponse.getData().getPetList().size() > 0) {
                            Log.d("DATALOG", String.valueOf(getPetListResponse.getData().getPetList().get(0).getPetUniqueId()));
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
                                petList.setPetAge(getPetListResponse.getData().getPetList().get(i).getPetAge());
                                profileList.add(petList);
                            }
                            progressBar.setVisibility(View.GONE);
                            scanPetListAdapter = new ScanPetListAdapter(getContext(), profileList, this);
                            register_pet_RV.setAdapter(scanPetListAdapter);
                            scanPetListAdapter.notifyDataSetChanged();
                            register_pet_RV.setVisibility(View.VISIBLE);

                        } else {
                            progressBar.setVisibility(View.GONE);
//                            Toast.makeText(getContext(), "Data Not found", Toast.LENGTH_SHORT).show();
                        }


                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            case "AddPetWithQR":
                try {
                    methods.customProgressDismiss();
                    RegisterParentWithQRResponse registerParentWithQRResponse = (RegisterParentWithQRResponse) response.body();
                    Log.e("RegistrationQR", registerParentWithQRResponse.toString());

                    if (registerParentWithQRResponse.getResponse().getResponseCode().equals("109")) {

                        getActivity().setResult(RESULT_OK);
                        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                        Toast.makeText(getContext(), "Pet Added in Vet Register", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Please Try again !", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            case "GetPetListBySearch":
                try {
                    GetPetListResponse getPetListResponse = (GetPetListResponse) response.body();
                    Log.d("GetPetListBySearch", getPetListResponse.toString());
                    int responseCode = Integer.parseInt(getPetListResponse.getResponse().getResponseCode());

                    if (responseCode == 109) {
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        register_pet_RV.setLayoutManager(linearLayoutManager);
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
                                petList.setPetAge(getPetListResponse.getData().getPetList().get(i).getPetAge());

                                profileList.add(petList);
                            }
                            progressBarForSearchBar.setVisibility(View.GONE);
                            scanPetListAdapter = new ScanPetListAdapter(getContext(), profileList, this);
                            register_pet_RV.setAdapter(scanPetListAdapter);
                            scanPetListAdapter.notifyDataSetChanged();
                        } else {
                            progressBarForSearchBar.setVisibility(View.GONE);
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

        StringTokenizer tokens = new StringTokenizer(profileList.get(position).getId(), ".");
        String first = tokens.nextToken();

        Intent intent = new Intent(getActivity(), PetProfileActivity.class);
        intent.putExtra("pet_id", first);
        startActivity(intent);
    }

    @Override
    public void onViewReportsClick(int position) {
        methods.showCustomProgressBarDialog(getContext());
        AddPetToVetRegisterUsingQRCodeParams addPetToVetRegisterUsingQRCodeParams = new AddPetToVetRegisterUsingQRCodeParams();
        addPetToVetRegisterUsingQRCodeParams.setVeterinarianUserId(veterinarianUserId);
        addPetToVetRegisterUsingQRCodeParams.setPetUniqueId(profileList.get(position).getPetUniqueId());
        AddPetToVetRegisterUsingQRRequest addPetToVetRegisterUsingQRRequest = new AddPetToVetRegisterUsingQRRequest();
        addPetToVetRegisterUsingQRRequest.setData(addPetToVetRegisterUsingQRCodeParams);
        ApiService<RegisterParentWithQRResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().addPetToVetRegisterUsingQRCode(Config.token, addPetToVetRegisterUsingQRRequest), "AddPetWithQR");
        Log.e("AddPetWithQR", "AddPetWithQR=> " + addPetToVetRegisterUsingQRRequest);


    }

    @Override
    public void onIdAddClinicClick(int position) {

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