package com.cynoteck.petofyparents.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

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

import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.activty.AdoptionDonationActivity;
import com.cynoteck.petofyparents.activty.ConsultationListActivity;
import com.cynoteck.petofyparents.activty.OTPVerifyActivity;
import com.cynoteck.petofyparents.activty.PetBreedsActivity;
import com.cynoteck.petofyparents.activty.PetInsuranceActivity;
import com.cynoteck.petofyparents.activty.PetNamesActivity;
import com.cynoteck.petofyparents.activty.SearchKeywordActivity;
import com.cynoteck.petofyparents.adapter.CityListAdapter;
import com.cynoteck.petofyparents.adapter.SliderPagerAdapter;
import com.cynoteck.petofyparents.api.ApiClient;
import com.cynoteck.petofyparents.api.ApiResponse;
import com.cynoteck.petofyparents.api.ApiService;
import com.cynoteck.petofyparents.response.getCityListWithStateResponse.GetCityListWithStateResponse;
import com.cynoteck.petofyparents.utils.Config;
import com.cynoteck.petofyparents.utils.Methods;
import com.cynoteck.petofyparents.utils.OnItemClickListener;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Response;

public class ParentHomeFragment extends Fragment implements View.OnClickListener, ApiResponse, OnItemClickListener {
    View view;
    private ViewPager vp_slider;
    private LinearLayout ll_dots;
    SliderPagerAdapter sliderPagerAdapter;
    ArrayList<Integer> slider_image_list;
    private ImageView[] dots;
    int page_position = 0;
    LinearLayout location_LL,pet_names_LL,cosultation_LL,insurances_LL,adoption_donation_LL;
    Methods methods;
    TextView location_TV;
    ImageView search_bar_IV;

    LinearLayout pet_breed_LL;


    //location Dialog.........
    Dialog location_dialog;
    MaterialCardView cancel_CV;
    LinearLayout current_location_LL;
    GetCityListWithStateResponse getCityListWithStateResponse;
    CityListAdapter cityListAdapter;
    RecyclerView city_list_RV;
    ProgressBar progressBar;
    EditText search_location_ET;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor login_editor;
    public ParentHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_parent_home, container, false);
        methods = new Methods(getContext());

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
        search_bar_IV=view.findViewById(R.id.search_bar_IV);
        location_TV = view.findViewById(R.id.location_TV);
        location_LL = view.findViewById(R.id.location_LL);
        pet_names_LL = view.findViewById(R.id.pet_names_LL);
        cosultation_LL=view.findViewById(R.id.cosultation_LL);
        insurances_LL=view.findViewById(R.id.insurances_LL);
        vp_slider = (ViewPager) view.findViewById(R.id.pager);
        adoption_donation_LL=view.findViewById(R.id.adoption_donation_LL);
        ll_dots = (LinearLayout) view.findViewById(R.id.ll_dots);
        slider_image_list = new ArrayList<Integer>();
        slider_image_list.add(R.drawable.slider_one);
        slider_image_list.add(R.drawable.slider_two);
        slider_image_list.add(R.drawable.slider_three);
        pet_breed_LL = view.findViewById(R.id.pet_breed_LL);

        location_LL.setOnClickListener(this);
        pet_breed_LL.setOnClickListener(this);
        search_bar_IV.setOnClickListener(this);
        pet_names_LL.setOnClickListener(this);
        cosultation_LL.setOnClickListener(this);
        insurances_LL.setOnClickListener(this);
        adoption_donation_LL.setOnClickListener(this);

        location_TV.setText(Config.cityFullName);
        sliderPagerAdapter = new SliderPagerAdapter(getActivity(), slider_image_list);
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
            //ivArrayDotsPager[i].setAlpha(0.4f);
//            dots[i].setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    view.setAlpha(1);
//                }
//            });
            ll_dots.addView(dots[i]);
            ll_dots.bringToFront();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_bar_IV:
                startActivity(new Intent(getContext(), SearchKeywordActivity.class));
                break;

            case R.id.location_LL:
                showLocationDialog();
                ApiService<GetCityListWithStateResponse> service = new ApiService<>();
                service.get(this, ApiClient.getApiInterface().getCityListWithState(Config.token), "GetCityListWithState");
                break;

            case R.id.cancel_CV:
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

            case R.id.cosultation_LL:
                Intent consultationIntent = new Intent(getContext(), ConsultationListActivity.class);
                startActivity(consultationIntent);
                break;

            case R.id.insurances_LL:
                Intent insurancesIntent = new Intent(getContext(), PetInsuranceActivity.class);
                startActivity(insurancesIntent);
                break;

            case R.id.adoption_donation_LL:
                Intent adoptionDonationIntent = new Intent(getContext(), AdoptionDonationActivity.class);
                startActivity(adoptionDonationIntent);
                break;


        }
    }

    private void showLocationDialog() {
        location_dialog = new Dialog(getContext());
        location_dialog.setContentView(R.layout.location_dialog);
        cancel_CV = location_dialog.findViewById(R.id.cancel_CV);
        city_list_RV = location_dialog.findViewById(R.id.city_list_RV);
        search_location_ET = location_dialog.findViewById(R.id.search_location_ET);
        current_location_LL = location_dialog.findViewById(R.id.current_location_LL);
        progressBar = location_dialog.findViewById(R.id.progressBar);
        current_location_LL.setOnClickListener(this);
        cancel_CV.setOnClickListener(this);
        search_location_ET.setEnabled(false);

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
        location_TV.setText(getCityListWithStateResponse.getData().get(position).getCityName());
        sharedPreferences = getContext().getSharedPreferences("userDetails", 0);
        login_editor = sharedPreferences.edit();
        login_editor.putString("CityId", getCityListWithStateResponse.getData().get(position).getId());
        login_editor.putString("cityName", getCityListWithStateResponse.getData().get(position).getCity1());
        login_editor.putString("CityFullName", getCityListWithStateResponse.getData().get(position).getCityName());
        login_editor.commit();
        Config.latitude = sharedPreferences.getString("userLatitude", "");
        Config.longitude = sharedPreferences.getString("userLongitude", "");
        Config.cityId = sharedPreferences.getString("CityId", "");
        Config.cityName = sharedPreferences.getString("cityName", "");
        Config.cityFullName = sharedPreferences.getString("CityFullName", "");
        location_dialog.dismiss();
    }
}