package com.cynoteck.petofyparents.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.activty.AddPetRegister;
import com.cynoteck.petofyparents.activty.PetDetailsActivity;
import com.cynoteck.petofyparents.activty.PetIdCardActivity;
import com.cynoteck.petofyparents.activty.PetProfileActivity;
import com.cynoteck.petofyparents.adapter.RegisterPetAdapter;
import com.cynoteck.petofyparents.api.ApiClient;
import com.cynoteck.petofyparents.api.ApiResponse;
import com.cynoteck.petofyparents.api.ApiService;
import com.cynoteck.petofyparents.parameter.petReportsRequest.PetDataParams;
import com.cynoteck.petofyparents.parameter.petReportsRequest.PetDataRequest;
import com.cynoteck.petofyparents.response.getPetReportsResponse.getPetListResponse.GetPetListResponse;
import com.cynoteck.petofyparents.response.getPetReportsResponse.getPetListResponse.PetList;
import com.cynoteck.petofyparents.response.loginRegisterResponse.UserPermissionMasterList;
import com.cynoteck.petofyparents.utils.Config;
import com.cynoteck.petofyparents.utils.Methods;
import com.cynoteck.petofyparents.utils.ViewDeatilsAndIdCardClick;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.StringTokenizer;

import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PetRegisterFragment extends Fragment implements  ApiResponse, ViewDeatilsAndIdCardClick,View.OnClickListener,TextWatcher{
    View view;
    Context context;
    Methods methods;
    CardView materialCardView;
    RecyclerView register_pet_RV;
    ImageView search_register_pet,back_arrow_IV;
    RegisterPetAdapter registerPetAdapter;
    private ShimmerFrameLayout mShimmerViewContainer;
    RelativeLayout search_boxRL;
    AutoCompleteTextView search_box;
    TextView regiter_pet_headline_TV,register_add_TV;
    NestedScrollView nestedScrollView;
    ProgressBar progressBar;
    int page=1, pagelimit=10;
    ArrayList<PetList> profileList;

    SharedPreferences sharedPreferences;
    String userTYpe="";
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
        sharedPreferences = getActivity().getSharedPreferences("userdetails", 0);

        init();

        if (methods.isInternetOn()){
            getPetList(page,pagelimit);

        }else {

            methods.DialogInternet();
        }

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY==v.getChildAt(0).getMeasuredHeight()-v.getMeasuredHeight())
                {
                    page++;
                    progressBar.setVisibility(View.VISIBLE);
                    getPetList(page,pagelimit);
                }
            }
        });

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

        nestedScrollView=view.findViewById(R.id.nested_scroll_view);
        progressBar=view.findViewById(R.id.progressBar);

        methods = new Methods(getContext());
        register_add_TV.setOnClickListener(this);
        search_register_pet.setOnClickListener(this);
        back_arrow_IV.setOnClickListener(this);
        search_box.addTextChangedListener(this);

        profileList=new ArrayList<>();

        getPet();
    }

    private void getPet()
    {
        search_box.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("dataChange","afterTextChanged"+new String(editable.toString()));
                String value=editable.toString();
                petSearchDependsOnPrefix(value);
            }
        });


        search_box.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value=search_box.getText().toString();
                String[] city_array = value.split("\\(");

                search_box.setText(city_array[0]);
            }
        });

    }

    private void petSearchDependsOnPrefix(String prefix)
    {
        PetDataParams getPetDataParams = new PetDataParams();
        getPetDataParams.setPageNumber(0);//0
        getPetDataParams.setPageSize(10);//0
        getPetDataParams.setSearch_Data(prefix);
        PetDataRequest getPetDataRequest = new PetDataRequest();
        getPetDataRequest.setData(getPetDataParams);

        ApiService<GetPetListResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getPetList(Config.token,getPetDataRequest), "GetPetListBySearch");
        Log.e("DATALOG","check1=> "+getPetDataRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_add_TV:
                userTYpe = sharedPreferences.getString("user_type", "");
                if (userTYpe.equals("Vet Staff")){
                    Gson gson = new Gson();
                    String json = sharedPreferences.getString("userPermission", null);
                    Type type = new TypeToken<ArrayList<UserPermissionMasterList>>() {}.getType();
                    ArrayList<UserPermissionMasterList> arrayList = gson.fromJson(json, type);
                    Log.e("ArrayList",arrayList.toString());
                    Log.d("UserType",userTYpe);
                    if (arrayList.get(0).getIsSelected().equals("true")) {
                        startActivity(new Intent(getActivity(),AddPetRegister.class));

                    }else {
                        Toast.makeText(context, "Permission not allowed!!", Toast.LENGTH_SHORT).show();
                    }
                }else if (userTYpe.equals("Veterinarian")){
                    startActivity(new Intent(getActivity(),AddPetRegister.class));

                }

                break;
            case R.id.search_register_pet:

                search_boxRL.setVisibility(View.VISIBLE);
                search_box.requestFocus();
                search_register_pet.setVisibility(View.GONE);
                back_arrow_IV.setVisibility(View.VISIBLE);
                regiter_pet_headline_TV.setVisibility(View.GONE);
                register_add_TV.setVisibility(View.GONE);
                break;

            case R.id.back_arrow_IV:
                search_register_pet.setVisibility(View.VISIBLE);
                clearSearch();
                break;
        }

    }

    private void getPetList(int page,int pageLimit) {
        //       methods.showCustomProgressBarDialog(getContext());
        PetDataParams getPetDataParams = new PetDataParams();
        getPetDataParams.setPageNumber(page);//0
        getPetDataParams.setPageSize(pageLimit);//0
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

                    if (responseCode== 109){
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        register_pet_RV.setLayoutManager(linearLayoutManager);
                        if(getPetListResponse.getData().getPetList().size()>0)
                        {
                            Log.d("DATALOG", String.valueOf(getPetListResponse.getData().getPetList().get(0).getPetUniqueId()));

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
                                profileList.add(petList);
                            }
                            progressBar.setVisibility(View.GONE);
                            registerPetAdapter  = new RegisterPetAdapter(getContext(),profileList,this);
                            register_pet_RV.setAdapter(registerPetAdapter);
                            registerPetAdapter.notifyDataSetChanged();

                            mShimmerViewContainer.stopShimmer();
                            search_register_pet.setVisibility(View.VISIBLE);
                            mShimmerViewContainer.setVisibility(View.GONE);
                        }
                        else
                        {
                            progressBar.setVisibility(View.GONE);
                            mShimmerViewContainer.setVisibility(View.GONE);
                            Toast.makeText(context, "Data Not found", Toast.LENGTH_SHORT).show();
                        }


                    }

                }
                catch(Exception e) {
                    e.printStackTrace();
                }

                break;
            case "GetPetListBySearch":
                try {
                    GetPetListResponse getPetListResponse = (GetPetListResponse) response.body();
                    Log.d("GetPetListBySearch", getPetListResponse.toString());
                    int responseCode = Integer.parseInt(getPetListResponse.getResponse().getResponseCode());

                    if (responseCode== 109){
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        register_pet_RV.setLayoutManager(linearLayoutManager);
                        if(getPetListResponse.getData().getPetList().size()>0)
                        {
                            profileList.clear();
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

                                profileList.add(petList);
                            }
                            progressBar.setVisibility(View.GONE);
                            registerPetAdapter  = new RegisterPetAdapter(getContext(),profileList,this);
                            register_pet_RV.setAdapter(registerPetAdapter);
                            registerPetAdapter.notifyDataSetChanged();
                            mShimmerViewContainer.stopShimmer();
                            search_register_pet.setVisibility(View.GONE);
                            mShimmerViewContainer.setVisibility(View.GONE);
                        }
                        else
                        {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(context, "Data Not found", Toast.LENGTH_SHORT).show();
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
        //methods.customProgressDismiss();

    }


    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmer();
        if (Config.backCall.equals("Added")) {
            Config.backCall ="";
            getPetList(page,pagelimit);
        }if (Config.backCall.equals("hit")) {
            Config.backCall ="";
            getPetList(page,pagelimit);
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
        Log.d("pet_id",""+profileList.get(position).getId());

        StringTokenizer tokens = new StringTokenizer(profileList.get(position).getId(), ".");
        String first = tokens.nextToken();

        Intent intent=new Intent(getActivity(), PetProfileActivity.class);
        intent.putExtra("pet_id",first);
//        intent.putExtra("pet_category",profileList.get(position).getPetCategory());
//        intent.putExtra("pet_name",profileList.get(position).getPetName());
//        intent.putExtra("pet_sex",profileList.get(position).getPetSex());
//        intent.putExtra("pet_DOB",profileList.get(position).getPetTestsAndXrey());
//        intent.putExtra("pet_age",profileList.get(position).getPetAge());
//        intent.putExtra("pet_size",profileList.get(position).getPetSize());
//        intent.putExtra("pet_breed",profileList.get(position).getPetBreed());
//        intent.putExtra("pet_color",profileList.get(position).getPetColor());
//        intent.putExtra("pet_parent",profileList.get(position).getPetParentName());
//        intent.putExtra("pet_parent_contact",profileList.get(position).getContactNumber());
        startActivity(intent);

    }

    @Override
    public void onIdCardClick(int position) {
        Log.d("positionssss",""+position);
        Intent intent = new Intent(getContext(),PetIdCardActivity.class);
        Bundle idBundle = new Bundle();
        idBundle.putString("id",profileList.get(position).getId());
        intent.putExtras(idBundle);
        startActivity(intent);
    }

    @Override
    public void onIdAddClinicClick(int position)
    {
        Log.e("ID",profileList.get(position).getId());
        Log.e("E_ID",profileList.get(position).getEncryptedId());
        Log.e("PET_NAME",profileList.get(position).getPetName());
        Log.e("PET_PARENT",profileList.get(position).getPetParentName());

        Intent petDetailsIntent = new Intent(getActivity().getApplication(), PetDetailsActivity.class);
        Bundle data = new Bundle();
        data.putString("pet_id",profileList.get(position).getId());
        data.putString("pet_name",profileList.get(position).getPetName());
        data.putString("pet_parent",profileList.get(position).getPetParentName());
        data.putString("pet_sex",profileList.get(position).getPetSex());
        data.putString("pet_age",profileList.get(position).getPetAge());
        data.putString("pet_unique_id",profileList.get(position).getPetUniqueId());
        data.putString("pet_DOB",profileList.get(position).getDateOfBirth());
        data.putString("pet_encrypted_id",profileList.get(position).getEncryptedId());
        petDetailsIntent.putExtras(data);
        startActivity(petDetailsIntent);
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
       // registerPetAdapter.getFilter().filter(charSequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }


}