package com.cynoteck.petofy.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cynoteck.petofy.R;
import com.cynoteck.petofy.activity.AddPetRegister;
import com.cynoteck.petofy.activity.AdoptionDonationActivity;
import com.cynoteck.petofy.activity.AfterScanScreenActivity;
import com.cynoteck.petofy.activity.ConsultationListActivity;
import com.cynoteck.petofy.activity.InsuranceActivity;
import com.cynoteck.petofy.activity.PetBreedsActivity;
import com.cynoteck.petofy.activity.PetNamesActivity;
import com.cynoteck.petofy.activity.ScannerQR;
import com.cynoteck.petofy.activity.SearchKeywordActivity;
import com.cynoteck.petofy.adapter.CityListAdapter;
import com.cynoteck.petofy.adapter.SliderPagerAdapter;
import com.cynoteck.petofy.api.ApiClient;
import com.cynoteck.petofy.api.ApiResponse;
import com.cynoteck.petofy.api.ApiService;
import com.cynoteck.petofy.onClicks.OnSliderClickListener;
import com.cynoteck.petofy.response.getCityListWithStateResponse.GetCityListWithStateResponse;
import com.cynoteck.petofy.response.getPetReportsResponse.getPetListResponse.PetList;
import com.cynoteck.petofy.utils.Config;
import com.cynoteck.petofy.utils.Methods;
import com.cynoteck.petofy.onClicks.OnItemClickListener;
import com.cynoteck.petofy.utils.PetParentSingleton;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.cynoteck.petofy.fragments.ProfileFragment.petListHorizontalAdapter;
import static com.cynoteck.petofy.fragments.PetRegisterFragment.registerPetAdapter;
import static com.cynoteck.petofy.fragments.PetRegisterFragment.total_pets_TV;
public class ParentHomeFragment extends Fragment implements View.OnClickListener, ApiResponse, OnItemClickListener, OnSliderClickListener {
    View                                view;
    private ViewPager                   vp_slider;
    private LinearLayout                ll_dots;
    SliderPagerAdapter                  sliderPagerAdapter;
    ArrayList<Integer>                  slider_image_list;
    private ImageView[]                 dots;
    int                                 page_position = 0;
    LinearLayout                        location_LL,search_layout_LL;
    Methods                             methods;
    TextView                            location_TV;
    LinearLayout                        pet_breed_LL, pet_names_LL,cosultation_LL,insurances_LL,adoption_donation_LL,pet_shops_LL,grooming_LL,hostels_LL,training_LL;


    //location Dialog.........
    Dialog                              location_dialog;
    MaterialCardView                    cancel_CV;
    LinearLayout                        current_location_LL;
    GetCityListWithStateResponse        getCityListWithStateResponse;
    CityListAdapter                     cityListAdapter;
    RecyclerView                        city_list_RV;
    ProgressBar                         progressBar;
    EditText                            search_location_ET;
    ImageView                           qr_code_IV;
    private static final int            QR_CODE_SCANNER = 100;
    private final int                   ADD_PET = 2;

    SharedPreferences                   sharedPreferences;
    SharedPreferences.Editor            login_editor;
    boolean                             showCrossButton = false;
    public ParentHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view    =   inflater.inflate(R.layout.fragment_parent_home, container, false);
        methods =   new Methods(getContext());

        init();
        if (Config.cityId.equals("")){
            showLocationDialog();
            ApiService<GetCityListWithStateResponse> service = new ApiService<>();
            service.get(this, ApiClient.getApiInterface().getCityListWithState(Config.token), "GetCityListWithState");
        }

        setupPagerIndidcatorDots();
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            public void run() {
                if (page_position == slider_image_list.size()) {
                    page_position = 0;
                } else {
                    page_position = page_position + 1;
                }
                vp_slider.setCurrentItem(page_position, true);
            }
        };

        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 3000, 3000);
        return view;
    }

    private void init() {
        search_layout_LL        = view.findViewById(R.id.search_layout_LL);
        qr_code_IV              = view.findViewById(R.id.qr_code_IV);
        location_TV             = view.findViewById(R.id.location_TV);
        location_LL             = view.findViewById(R.id.location_LL);
        pet_names_LL            = view.findViewById(R.id.pet_names_LL);
        cosultation_LL          = view.findViewById(R.id.cosultation_LL);
        insurances_LL           = view.findViewById(R.id.insurances_LL);
        vp_slider               = view.findViewById(R.id.pager);
        adoption_donation_LL    = view.findViewById(R.id.adoption_donation_LL);
        ll_dots                 = view.findViewById(R.id.ll_dots);
        pet_breed_LL            = view.findViewById(R.id.pet_breed_LL);
        pet_shops_LL            = view.findViewById(R.id.pet_shops_LL);
        grooming_LL             = view.findViewById(R.id.grooming_LL);
        hostels_LL              = view.findViewById(R.id.hostels_LL);
        training_LL             = view.findViewById(R.id.training_LL);

        slider_image_list       = new ArrayList<Integer>();

        slider_image_list.add(R.drawable.slider_one);
        slider_image_list.add(R.drawable.slider_two);
        slider_image_list.add(R.drawable.slider_three);


        hostels_LL.setOnClickListener(this);
        grooming_LL.setOnClickListener(this);
        training_LL.setOnClickListener(this);
        pet_shops_LL.setOnClickListener(this);
        pet_breed_LL.setOnClickListener(this);
        cosultation_LL.setOnClickListener(this);
        insurances_LL.setOnClickListener(this);
        qr_code_IV.setOnClickListener(this);
        adoption_donation_LL.setOnClickListener(this);
        pet_names_LL.setOnClickListener(this);


        location_LL.setOnClickListener(this);
        search_layout_LL.setOnClickListener(this);

        location_TV.setText(Config.cityFullName);
        sliderPagerAdapter = new SliderPagerAdapter(getActivity(), slider_image_list,this);
        vp_slider.setAdapter(sliderPagerAdapter);
        vp_slider.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //  addBottomDots(position);

                for (int i = 0; i < slider_image_list.size(); i++) {
                    dots[i].setImageResource(R.drawable.inactive_dot);
                }
                dots[position].setImageResource(R.drawable.active_dot);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setupPagerIndidcatorDots() {
        dots = new ImageView[slider_image_list.size()];
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(5, 0, 5, 0);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.inactive_dot);
            ll_dots.addView(dots[i]);
            ll_dots.bringToFront();
            dots[0].setImageResource(R.drawable.active_dot);

        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_layout_LL:
                startActivity(new Intent(getContext(), SearchKeywordActivity.class));
                break;

            case R.id.qr_code_IV:
                Intent qr_code_intent = new Intent(getContext(), ScannerQR.class);
                startActivityForResult(qr_code_intent,QR_CODE_SCANNER);
                break;

            case R.id.location_LL:
                showCrossButton = true;
                showLocationDialog();
                ApiService<GetCityListWithStateResponse> service = new ApiService<>();
                service.get(this, ApiClient.getApiInterface().getCityListWithState(Config.token), "GetCityListWithState");
                break;

            case R.id.cancel_CV:
                showCrossButton = false;
                location_dialog.dismiss();
                break;

            case R.id.current_location_LL:

                break;

            case R.id.pet_breed_LL:
                Intent breedActivityIntent = new Intent(getContext(), PetBreedsActivity.class);
                startActivity(breedActivityIntent);
                break;

            case R.id.pet_names_LL:
                Intent namesActivityIntent = new Intent(getContext(), PetNamesActivity.class);
                startActivity(namesActivityIntent);
                break;

            case R.id.adoption_donation_LL:
                Intent adoptionDonationIntent = new Intent(getContext(), AdoptionDonationActivity.class);
                startActivity(adoptionDonationIntent);
                break;

            case R.id.insurances_LL:
                Intent insurancesIntent = new Intent(getContext(), InsuranceActivity.class);
                startActivity(insurancesIntent);
                break;

            case R.id.cosultation_LL:
                Intent consultationIntent = new Intent(getContext(), ConsultationListActivity.class);
                consultationIntent.putExtra("serviceTypeId","1");
                startActivity(consultationIntent);
                break;

            case R.id.hostels_LL:
                Intent hostelsIntent = new Intent(getContext(), ConsultationListActivity.class);
                hostelsIntent.putExtra("serviceTypeId","3");
                startActivity(hostelsIntent);
                break;

            case R.id.grooming_LL:
                Intent groomingIntent = new Intent(getContext(), ConsultationListActivity.class);
                groomingIntent.putExtra("serviceTypeId","2");
                startActivity(groomingIntent);
                break;

            case R.id.pet_shops_LL:
                Intent pet_shopsIntent = new Intent(getContext(), ConsultationListActivity.class);
                pet_shopsIntent.putExtra("serviceTypeId","11");
                startActivity(pet_shopsIntent);
                break;

            case R.id.training_LL:
                Intent trainingIntent = new Intent(getContext(), ConsultationListActivity.class);
                trainingIntent.putExtra("serviceTypeId","6");
                startActivity(trainingIntent);
                break;

        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==QR_CODE_SCANNER){
            if (resultCode==RESULT_OK) {
                String IsInsurance = data.getStringExtra("IsInsurance");
                if (IsInsurance==null){
                    String veterinarianUserId = data.getStringExtra("veterinarianUserId");
                    String veterinarianName = data.getStringExtra("veterinarianName");
                    String clinicName = data.getStringExtra("clinicName");
                    String Rating = data.getStringExtra("Rating");
                    String profileImageUrl = data.getStringExtra("profileImageUrl");
                    Log.e("veterinarianUserId", veterinarianUserId);
                    Intent scanAfterIntent = new Intent(getContext(), AfterScanScreenActivity.class);
                    scanAfterIntent.putExtra("veterinarianUserId", veterinarianUserId);
                    scanAfterIntent.putExtra("veterinarianName", veterinarianName);
                    scanAfterIntent.putExtra("clinicName", clinicName);
                    scanAfterIntent.putExtra("Rating", Rating);
                    scanAfterIntent.putExtra("profileImageUrl", profileImageUrl);
                    startActivity(scanAfterIntent);
                } else if (IsInsurance.equals("true")){
                    String InsuranceUrl = data.getStringExtra("InsuranceUrl");
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(InsuranceUrl));
                    startActivity(browserIntent);
                }

            }

        }else if (requestCode == ADD_PET) {
                if (resultCode == RESULT_OK) {
                    PetList petList = new PetList();
                    petList.setId(data.getStringExtra("pet_id"));
                    petList.setPetUniqueId(data.getStringExtra("pet_unique_id"));
                    petList.setPetProfileImageUrl(data.getStringExtra("pet_image_url"));
                    petList.setPetBreed(data.getStringExtra("pet_breed"));
                    petList.setPetAge(data.getStringExtra("pet_age"));
                    petList.setPetSex(data.getStringExtra("pet_sex"));
                    petList.setPetName(data.getStringExtra("pet_name"));
                    petList.setPetParentName(data.getStringExtra("pet_parent"));
                    petList.setPetCategory(data.getStringExtra("pet_category"));
                    petList.setDateOfBirth(data.getStringExtra("pet_date_of_birth"));
                    petList.setPetColor(data.getStringExtra("pet_color"));
                    PetParentSingleton.getInstance().getArrayList().add(0, petList);
                    registerPetAdapter.notifyDataSetChanged();
                    total_pets_TV.setText("You have " + PetParentSingleton.getInstance().getArrayList().size() + " pets registered ");
                    petListHorizontalAdapter.notifyDataSetChanged();
                }
            }

    }

    private void showLocationDialog() {
        location_dialog = new Dialog(getContext());
        location_dialog.setCancelable(false);
        location_dialog.setContentView(R.layout.location_dialog);
        cancel_CV = location_dialog.findViewById(R.id.cancel_CV);
        city_list_RV = location_dialog.findViewById(R.id.city_list_RV);
        search_location_ET = location_dialog.findViewById(R.id.search_location_ET);
        current_location_LL = location_dialog.findViewById(R.id.current_location_LL);
        progressBar = location_dialog.findViewById(R.id.progressBar);
        current_location_LL.setOnClickListener(this);
//        cancel_CV.setOnClickListener(this);
        search_location_ET.setEnabled(false);

        if (showCrossButton){
            cancel_CV.setVisibility(View.VISIBLE);
            cancel_CV.setOnClickListener(this);
        }else {
            cancel_CV.setVisibility(View.INVISIBLE);
        }

        search_location_ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                cityListAdapter.getFilter().filter(s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = location_dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        location_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        location_dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        location_dialog.show();
    }

    @Override
    public void onResponse(Response arg0, String key) {
        switch (key) {
            case "GetCityListWithState":
                try {
                    progressBar.setVisibility(View.GONE);
                    Log.e("rer", methods.getRequestJson(arg0.body()));
                    getCityListWithStateResponse = (GetCityListWithStateResponse) arg0.body();
                    if (getCityListWithStateResponse.getResponse().getResponseCode().equals("109")){
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        city_list_RV.setLayoutManager(linearLayoutManager);
                        cityListAdapter = new CityListAdapter(getContext(), getCityListWithStateResponse.getData(), this);
                        city_list_RV.setAdapter(cityListAdapter);
                        cityListAdapter.notifyDataSetChanged();
                        search_location_ET.setEnabled(true);

                    }
                }catch (Exception e){
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
        showCrossButton = false;
        location_TV.setText(getCityListWithStateResponse.getData().get(position).getCityName());
        sharedPreferences = getContext().getSharedPreferences("userDetails", 0);
        login_editor = sharedPreferences.edit();
        login_editor.putString("CityId", getCityListWithStateResponse.getData().get(position).getId());
        login_editor.putString("cityName", getCityListWithStateResponse.getData().get(position).getCity1());
        login_editor.putString("CityFullName", getCityListWithStateResponse.getData().get(position).getCityName());
        login_editor.apply();
        Config.latitude = sharedPreferences.getString("userLatitude", "");
        Config.longitude = sharedPreferences.getString("userLongitude", "");
        Config.cityId = sharedPreferences.getString("CityId", "");
        Config.cityName = sharedPreferences.getString("cityName", "");
        Config.cityFullName = sharedPreferences.getString("CityFullName", "");
        location_dialog.dismiss();
    }

    @Override
    public void onSliderClickListener(int position) {
        if (position==0){
            Intent consultationIntent = new Intent(getContext(), ConsultationListActivity.class);
            consultationIntent.putExtra("serviceTypeId","1");
            startActivity(consultationIntent);
        }else if (position==1){
            Intent adNewIntent = new Intent(getActivity(), AddPetRegister.class);
            adNewIntent.putExtra("intent_from", "add");
            startActivityForResult(adNewIntent, ADD_PET);
        }

    }
}