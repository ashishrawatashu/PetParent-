package com.cynoteck.petofy.activity;

import static com.cynoteck.petofy.fragments.PetRegisterFragment.registerPetAdapter;
import static com.cynoteck.petofy.fragments.PetRegisterFragment.total_pets_TV;
import static com.cynoteck.petofy.fragments.ProfileFragment.petListHorizontalAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cynoteck.petofy.R;
import com.cynoteck.petofy.adapter.ProviderClinicTimingsAdapter;
import com.cynoteck.petofy.adapter.ProviderReviewsAdapter;
import com.cynoteck.petofy.adapter.SliderPagerAdapter;
import com.cynoteck.petofy.api.ApiClient;
import com.cynoteck.petofy.api.ApiResponse;
import com.cynoteck.petofy.api.ApiService;
import com.cynoteck.petofy.onClicks.OnSliderClickListener;
import com.cynoteck.petofy.parameter.saveFeedbackRequest.SaveFeedbackParams;
import com.cynoteck.petofy.parameter.saveFeedbackRequest.SaveFeedbackRequest;
import com.cynoteck.petofy.parameter.serviceProviderDetailRequest.SearchProviderFullDetailData;
import com.cynoteck.petofy.parameter.serviceProviderDetailRequest.SearchProviderFullDetailRequest;
import com.cynoteck.petofy.response.getPetReportsResponse.getPetListResponse.PetList;
import com.cynoteck.petofy.response.getSaveFeedbackResponse.GetSaveFeedbackResponse;
import com.cynoteck.petofy.response.getServiceProviderFullDetailsResponse.ProviderRatingList;
import com.cynoteck.petofy.response.getServiceProviderFullDetailsResponse.SearchProviderFullDetailResponse;
import com.cynoteck.petofy.response.getServiceProviderFullDetailsResponse.ServiceProviderDetailOperatingHour;
import com.cynoteck.petofy.utils.Config;
import com.cynoteck.petofy.utils.Methods;
import com.cynoteck.petofy.utils.PetParentSingleton;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Response;

public class VetFullProfileActivity extends AppCompatActivity implements ApiResponse, View.OnClickListener, OnSliderClickListener {
    String                                      serviceTypeId, EncryptId, vet_fee, vet_study , vet_rating , vet_address ,appointment_duration, vet_name , vetUserId ,vet_image_url , petId , id , type ,petParentString ;
    TextView                                    rating_TV, vet_name_TV,vet_study_TV,vet_address_TV,vet_chargers_TV,clinic_name_TV,vet_full_address_TV,write_reviews_TV;
    RecyclerView                                clinic_timings_RV,reviews_RV;
    Button                                      contact_clinic_BT,book_appointment_BT;
    MaterialCardView                            back_arrow_CV;
    ImageView                                   vet_profile_pic;
    ProviderReviewsAdapter                      providerReviewsAdapter;
    ProviderClinicTimingsAdapter                providerClinicTimingsAdapter;
    SearchProviderFullDetailResponse            searchProviderFullDetailResponse;
    Methods                                     methods;
    ScrollView                                  scroll_view_vet_profile;
    ArrayList<ProviderRatingList>               providerRatingLists = new ArrayList<>();
    List<ServiceProviderDetailOperatingHour>    serviceProviderDetailOperatingHours = new ArrayList<>();
    ProgressBar                                 progressBar,reviews_PB;
    ViewPager                                   pager;
    LinearLayout                                ll_dots;
    private ImageView[]                         dots;
    ArrayList<Integer>                          slider_image_list;
    SliderPagerAdapter                          sliderPagerAdapter;
    BottomSheetDialog                           reviewDialog;
    EditText                                    dialog_give_reviews_ET;
    Button                                      dialog_submit_feedback_BT;
    ImageView                                   rate_one_IV, rate_two_IV,rate_three_IV,rate_four_IV,rate_five_IV;
    int                                         rate = 0,providerId;
    int                                         page_position = 0;
    String                                      phone = "";
    private final int                           ADD_PET = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vet_full_profile);

        intentData();
        init();
        slider_image_list = new ArrayList<Integer>();
        slider_image_list.add(R.drawable.slider_one);
        slider_image_list.add(R.drawable.slider_two);
        slider_image_list.add(R.drawable.slider_three);
        setupPagerIndidcatorDots();
        autoSlider();
        SetViewPager();
        methods = new Methods(this);
        getServiceProviderAllDetails();
    }

    private void getServiceProviderAllDetails() {
        SearchProviderFullDetailData searchProviderFullDetailData = new SearchProviderFullDetailData();
        searchProviderFullDetailData.setEncryptedId(EncryptId);
        searchProviderFullDetailData.setLatitude(Config.latitude);
        searchProviderFullDetailData.setLongitude(Config.longitude);

        SearchProviderFullDetailRequest searchProviderFullDetailRequest = new SearchProviderFullDetailRequest();
        searchProviderFullDetailRequest.setData(searchProviderFullDetailData);

        ApiService<SearchProviderFullDetailResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getProviderDetail(Config.token, searchProviderFullDetailRequest), "GetProviderFullDetails");
//        Log.d("DATALOG", "check1=> " + methods.getRequestJson(searchProviderFullDetailRequest));

    }

    private void intentData() {
        Intent intent = getIntent();
        EncryptId       = intent.getStringExtra("EncryptId");
        type            = intent.getStringExtra("type");
        id              = intent.getStringExtra("id");
        petId           = intent.getStringExtra("pet_id");
        petParentString = intent.getStringExtra("petParent");
        vet_study       = intent.getStringExtra("vet_study");
        vet_rating      = intent.getStringExtra("vet_rating");
        vet_address     = intent.getStringExtra("vet_address");
        vet_name        = intent.getStringExtra("vet_name");
        vet_image_url   = intent.getStringExtra("vet_image_url");
        vetUserId       = intent.getStringExtra("vetUserId");
        vet_fee         = intent.getStringExtra("vet_fee");
        serviceTypeId   = intent.getStringExtra("serviceTypeId");
    }

    private void init() {
        progressBar             = findViewById(R.id.progressBar);
        scroll_view_vet_profile = findViewById(R.id.scroll_view_vet_profile);
        clinic_name_TV          = findViewById(R.id.clinic_name_TV);
        rating_TV               = findViewById(R.id.rating_TV);
        vet_name_TV             = findViewById(R.id.vet_name_TV);
        vet_study_TV            = findViewById(R.id.vet_study_TV);
        vet_address_TV          = findViewById(R.id.vet_address_TV);
        vet_chargers_TV         = findViewById(R.id.vet_chargers_TV);
        vet_full_address_TV     = findViewById(R.id.vet_full_address_TV);
        pager                   = findViewById(R.id.pager);
        write_reviews_TV        = findViewById(R.id.write_reviews_TV);
        ll_dots                 = findViewById(R.id.ll_dots);
        clinic_timings_RV       = findViewById(R.id.clinic_timings_RV);
        reviews_RV              = findViewById(R.id.reviews_RV);
        contact_clinic_BT       = findViewById(R.id.contact_clinic_BT);
        vet_profile_pic         = findViewById(R.id.vet_profile_pic);
        back_arrow_CV           = findViewById(R.id.back_arrow_CV);
        book_appointment_BT     = findViewById(R.id.book_appointment_BT);
        reviews_PB              = findViewById(R.id.reviews_PB);


        book_appointment_BT.setOnClickListener(this);
        back_arrow_CV.setOnClickListener(this);
        contact_clinic_BT.setOnClickListener(this);
        write_reviews_TV.setOnClickListener(this);
        rating_TV.setText(vet_rating);
        if (vet_fee.equals("0")){
            vet_chargers_TV.setText("");
        }else {
            vet_chargers_TV.setText("₹"+vet_fee+ " (Clinic Fees)");
        }



    }

    @SuppressLint({"NonConstantResourceId", "NotifyDataSetChanged"})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_arrow_CV:
                onBackPressed();
                break;

            case R.id.contact_clinic_BT:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
                break;

            case R.id.write_reviews_TV:
                showReviewDialog();
                break;
            case R.id.book_appointment_BT:
                Intent viewVetDetailsIntent = new Intent(this, AddUpdateAppointmentActivity.class);
                viewVetDetailsIntent.putExtra("vetUserId",vetUserId);
                viewVetDetailsIntent.putExtra("vet_fee",vet_fee);
                viewVetDetailsIntent.putExtra("vet_image_url",vet_image_url);
                viewVetDetailsIntent.putExtra("vet_study",vet_study);
                viewVetDetailsIntent.putExtra("vet_rating",vet_rating);
                viewVetDetailsIntent.putExtra("vet_address",vet_address);
                viewVetDetailsIntent.putExtra("vet_name",vet_name);
                viewVetDetailsIntent.putExtra("type", "add");
                viewVetDetailsIntent.putExtra("id", "");
                viewVetDetailsIntent.putExtra("pet_id", "");
                viewVetDetailsIntent.putExtra("appointment_duration", appointment_duration);

                startActivity(viewVetDetailsIntent);
                break;

            case R.id.dialog_submit_feedback_BT:
                String feedback = dialog_give_reviews_ET.getText().toString();
                Log.d("RATE",""+rate);
                if (rate == 0){
                    Toast.makeText(this, "Give rating to " +vet_name, Toast.LENGTH_SHORT).show();
                }else {
                    reviewDialog.dismiss();
                    reviews_PB.setVisibility(View.VISIBLE);
                    providerRatingLists.clear();
                    providerReviewsAdapter.notifyDataSetChanged();
                    Toast.makeText(this, "Thank you for giving you feedback !", Toast.LENGTH_SHORT).show();

                    SaveFeedbackParams saveFeedbackParams = new SaveFeedbackParams();
                    saveFeedbackParams.setFeedback(feedback);
                    saveFeedbackParams.setRating(rate);
                    saveFeedbackParams.setFeedbackDate(methods.getDate());
                    saveFeedbackParams.setProviderId(providerId);
                    saveFeedbackParams.setStatus(1);

                    SaveFeedbackRequest saveFeedbackRequest = new SaveFeedbackRequest();
                    saveFeedbackRequest.setData(saveFeedbackParams);

                    ApiService<GetSaveFeedbackResponse> service = new ApiService<>();
                    service.get(this, ApiClient.getApiInterface().giveFeedbackToProvider(Config.token, saveFeedbackRequest), "GiveFeedbackToProvider");
                    //Log.d"DATALOG", "check1=> " + methods.getRequestJson(saveFeedbackRequest));


                }



                break;


            case R.id.rate_one_IV:
                rate = 1;
                rate_one_IV.setImageResource(R.drawable.rate_one_active);
                rate_two_IV.setImageResource(R.drawable.rate_two_inactive);
                rate_three_IV.setImageResource(R.drawable.rate_three_inactive);
                rate_four_IV.setImageResource(R.drawable.rate_four_inactive);
                rate_five_IV.setImageResource(R.drawable.rate_five_inactive);
                dialog_give_reviews_ET.setVisibility(View.VISIBLE);

                break;

            case R.id.rate_two_IV:
                rate = 2;

                rate_one_IV.setImageResource(R.drawable.rate_one_inactive);
                rate_two_IV.setImageResource(R.drawable.rate_two_active);
                rate_three_IV.setImageResource(R.drawable.rate_three_inactive);
                rate_four_IV.setImageResource(R.drawable.rate_four_inactive);
                rate_five_IV.setImageResource(R.drawable.rate_five_inactive);
                dialog_give_reviews_ET.setVisibility(View.VISIBLE);

                break;

            case R.id.rate_three_IV:
                rate = 3;

                rate_one_IV.setImageResource(R.drawable.rate_one_inactive);
                rate_two_IV.setImageResource(R.drawable.rate_two_inactive);
                rate_three_IV.setImageResource(R.drawable.rate_three_active);
                rate_four_IV.setImageResource(R.drawable.rate_four_inactive);
                rate_five_IV.setImageResource(R.drawable.rate_five_inactive);
                dialog_give_reviews_ET.setVisibility(View.VISIBLE);


                break;

            case R.id.rate_four_IV:
                rate = 4;
                dialog_give_reviews_ET.setVisibility(View.VISIBLE);

                rate_one_IV.setImageResource(R.drawable.rate_one_inactive);
                rate_two_IV.setImageResource(R.drawable.rate_two_inactive);
                rate_three_IV.setImageResource(R.drawable.rate_three_inactive);
                rate_four_IV.setImageResource(R.drawable.rate_four_active);
                rate_five_IV.setImageResource(R.drawable.rate_five_inactive);

                break;

            case R.id.rate_five_IV:
                rate = 5;
                dialog_give_reviews_ET.setVisibility(View.VISIBLE);
                rate_one_IV.setImageResource(R.drawable.rate_one_inactive);
                rate_two_IV.setImageResource(R.drawable.rate_two_inactive);
                rate_three_IV.setImageResource(R.drawable.rate_three_inactive);
                rate_four_IV.setImageResource(R.drawable.rate_four_inactive);
                rate_five_IV.setImageResource(R.drawable.rate_five_active);

                break;
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onResponse(Response arg0, String key) {
        switch (key){
            case "GetProviderFullDetails":
                try {
                    reviews_PB.setVisibility(View.GONE);
                    searchProviderFullDetailResponse = (SearchProviderFullDetailResponse) arg0.body();
                    int responseCode = Integer.parseInt(searchProviderFullDetailResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        progressBar.setVisibility(View.GONE);
                        scroll_view_vet_profile.setVisibility(View.VISIBLE);
                        if (serviceTypeId.equals("1")&&searchProviderFullDetailResponse.getData().getIsVeterinarian()&&searchProviderFullDetailResponse.getData().getEnableOnlineAppointments()){
                            book_appointment_BT.setVisibility(View.VISIBLE);
                            book_appointment_BT.setEnabled(true);
                        }else {
                            book_appointment_BT.setVisibility(View.GONE);
                            book_appointment_BT.setEnabled(false);
                            book_appointment_BT.setText("");
                            book_appointment_BT.setBackgroundResource(R.drawable.next_button_grey_bg);
                        }
                        LinearLayoutManager reviewsHorizontalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                        reviews_RV.setLayoutManager(reviewsHorizontalLayoutManager);
                        LinearLayoutManager clinic_timings_horizontalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                        clinic_timings_RV.setLayoutManager(clinic_timings_horizontalLayoutManager);
                        providerId = (searchProviderFullDetailResponse.getData().getId());
                        providerRatingLists = searchProviderFullDetailResponse.getData().getProviderRatingList();
                        serviceProviderDetailOperatingHours = searchProviderFullDetailResponse.getData().getOperatingHourList();
                        Glide.with(this).load(searchProviderFullDetailResponse.getData().getProfileImageUrl()).placeholder(R.drawable.doctor_dummy_image).into(vet_profile_pic);
                        vet_name_TV.setText(searchProviderFullDetailResponse.getData().getName());
                        vet_study_TV.setText(searchProviderFullDetailResponse.getData().getVetQualifications());
                        vet_address_TV.setText(searchProviderFullDetailResponse.getData().getAddress());
                        clinic_name_TV.setText(searchProviderFullDetailResponse.getData().getCompany());
                        vet_full_address_TV.setText(searchProviderFullDetailResponse.getData().getAddress());
                        appointment_duration = searchProviderFullDetailResponse.getData().getAppointmentDuration();


                        providerClinicTimingsAdapter = new ProviderClinicTimingsAdapter(this, serviceProviderDetailOperatingHours);
                        clinic_timings_RV.setAdapter(providerClinicTimingsAdapter);
                        providerClinicTimingsAdapter.notifyDataSetChanged();

                        providerReviewsAdapter = new ProviderReviewsAdapter(this, providerRatingLists);
                        reviews_RV.setAdapter(providerReviewsAdapter);
                        providerReviewsAdapter.notifyDataSetChanged();

                        phone = searchProviderFullDetailResponse.getData().getPhone();
                        Log.d("rarting",searchProviderFullDetailResponse.getData().getRating());
                        rating_TV.setText(searchProviderFullDetailResponse.getData().getRating());


                    }
                }catch (Exception e){
                    e.printStackTrace();
                }


                break;

            case "GiveFeedbackToProvider":
                try {
                    GetSaveFeedbackResponse getSaveFeedbackResponse = (GetSaveFeedbackResponse) arg0.body();
                    int responseCode = Integer.parseInt(getSaveFeedbackResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        getServiceProviderAllDetails();
//                        Toast.makeText(this, "Feedback Uploaded Successfully ", Toast.LENGTH_SHORT).show();
//                        reviewDialog.dismiss();
                    }
                    }catch (Exception e){
                    e.printStackTrace();
                }


                break;
        }
    }

    private void autoSlider() {
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            public void run() {
                if (page_position == slider_image_list.size()) {
                    page_position = 0;
                } else {
                    page_position = page_position + 1;
                }
                pager.setCurrentItem(page_position, true);
            }
        };

        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 3000, 3000);
    }

    private void SetViewPager() {

        sliderPagerAdapter = new SliderPagerAdapter(this, slider_image_list,this);
        pager.setAdapter(sliderPagerAdapter);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

    @Override
    public void onError(Throwable t, String key) {

    }

    private void setupPagerIndidcatorDots() {
        dots = new ImageView[slider_image_list.size()];
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);
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
            dots[0].setImageResource(R.drawable.active_dot);

        }
    }

    private void showReviewDialog() {
        reviewDialog = new BottomSheetDialog(this);
        reviewDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reviewDialog.setCancelable(true);
        reviewDialog.setCanceledOnTouchOutside(false);
        reviewDialog.setContentView(R.layout.reviews_dilog_layout);
        TextView reviews_headline_TV = reviewDialog.findViewById(R.id.reviews_headline_TV);
        dialog_give_reviews_ET = reviewDialog.findViewById(R.id.dialog_give_reviews_ET);
        dialog_submit_feedback_BT = reviewDialog.findViewById(R.id.dialog_submit_feedback_BT);
        rate_one_IV = reviewDialog.findViewById(R.id.rate_one_IV);
        rate_two_IV = reviewDialog.findViewById(R.id.rate_two_IV);
        rate_three_IV = reviewDialog.findViewById(R.id.rate_three_IV);
        rate_four_IV = reviewDialog.findViewById(R.id.rate_four_IV);
        rate_five_IV = reviewDialog.findViewById(R.id.rate_five_IV);

        reviews_headline_TV.setText("How was your experience with Dr. "+ vet_name);

        dialog_submit_feedback_BT.setOnClickListener(this);
        rate_one_IV.setOnClickListener(this);
        rate_two_IV.setOnClickListener(this);
        rate_three_IV.setOnClickListener(this);
        rate_four_IV.setOnClickListener(this);
        rate_five_IV.setOnClickListener(this);

        reviewDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        reviewDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        reviewDialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = reviewDialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);


    }

    @Override
    public void onSliderClickListener(int position) {

        if (position==0){
            Intent consultationIntent = new Intent(this, ConsultationListActivity.class);
            consultationIntent.putExtra("serviceTypeId","1");
            startActivity(consultationIntent);
        }else if (position==1){
            Intent adNewIntent = new Intent(this, AddPetRegister.class);
            adNewIntent.putExtra("intent_from", "add");
            startActivityForResult(adNewIntent, ADD_PET);
        }else {
            Intent insurancesIntent = new Intent(this, PetInsuranceActivity.class);
            startActivity(insurancesIntent);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         if (requestCode == ADD_PET) {
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
}