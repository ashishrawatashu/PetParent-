package com.cynoteck.petofy.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cynoteck.petofy.R;
import com.cynoteck.petofy.adapter.DiseasesListAdapter;
import com.cynoteck.petofy.adapter.InsurancePlansAdapter;
import com.cynoteck.petofy.api.ApiClient;
import com.cynoteck.petofy.api.ApiResponse;
import com.cynoteck.petofy.api.ApiService;
import com.cynoteck.petofy.onClicks.DiseasesListClickListener;
import com.cynoteck.petofy.onClicks.InsurancePlanClick;
import com.cynoteck.petofy.parameter.getpetAgeRequest.GetPetAgeParameter;
import com.cynoteck.petofy.parameter.getpetAgeRequest.GetPetAgeRequestData;
import com.cynoteck.petofy.parameter.petBreedRequest.BreedParams;
import com.cynoteck.petofy.parameter.petBreedRequest.BreedRequest;
import com.cynoteck.petofy.parameter.punchingPolicyPetDocumentsRequest.PunchingPolicyPetDocumentsParams;
import com.cynoteck.petofy.parameter.punchingPolicyPetDocumentsRequest.PunchingPolicyPetDocumentsRequest;
import com.cynoteck.petofy.parameter.punchingPolicyPetRequest.PunchingPolicyPetParams;
import com.cynoteck.petofy.parameter.punchingPolicyPetRequest.PunchingPolicyPetRequest;
import com.cynoteck.petofy.parameter.punchingPolicyRequest.PunchingPolicyParams;
import com.cynoteck.petofy.parameter.punchingPolicyRequest.PunchingPolicyRequest;
import com.cynoteck.petofy.response.addPet.breedResponse.BreedCatRespose;
import com.cynoteck.petofy.response.addPet.imageUpload.ImageResponse;
import com.cynoteck.petofy.response.addPet.petColorResponse.PetColorValueResponse;
import com.cynoteck.petofy.response.dateOfBirthResponse.DateOfBirthResponse;
import com.cynoteck.petofy.response.getInsuranceMasterResponse.DiseasesListModel;
import com.cynoteck.petofy.response.getInsuranceMasterResponse.InsuranceMastersResponse;
import com.cynoteck.petofy.response.getInsuranceMasterResponse.InsurancePlanModel;
import com.cynoteck.petofy.response.getPetAgeResponse.GetPetAgeresponseData;
import com.cynoteck.petofy.response.insuranceAfterLoginResponses.getAllDetailsAfterLogin.GetAllDetailAfterLoginResponse;
import com.cynoteck.petofy.response.petAgeUnitResponse.PetAgeUnitResponseData;
import com.cynoteck.petofy.response.stateResponse.StateResponse;
import com.cynoteck.petofy.response.updateProfileResponse.CityResponse;
import com.cynoteck.petofy.response.updateProfileResponse.PetTypeResponse;
import com.cynoteck.petofy.utils.Config;
import com.cynoteck.petofy.utils.MediaUtils;
import com.cynoteck.petofy.utils.Methods;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import in.gauriinfotech.commons.Commons;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class BuyInsuranceActivity extends AppCompatActivity implements MediaUtils.GetImg, View.OnClickListener , ApiResponse, InsurancePlanClick , DiseasesListClickListener {
    Button                  next_BT,privious_BT;
    FrameLayout             pet_parent_details_layout_frame, insurance_pet_details_layout_frame,insurance_images_layout_frame;
    int                     countSteps = 1;
    ProgressBar             circular_PB,loading_PB;
    MaterialCardView        back_arrow_CV;
    TextView                step_name_TV ,next_step_TV,step_count_TV;

    //pet parent details
    DatePickerDialog        picker;
    TextView                calenderTextView_dialog;
    EditText                parent_first_name_ET, parent_last_name_ET, parent_email_ET, parent_phone_ET,parent_address_ET, parent_pinCode_ET, parent_referralCode_ET;

    //pet details Layout
    RadioButton             maleRB,femaleRB;
    CheckBox                pedigree_lineage_CB,kic_CB,is_vaccinated_CB,select_all_plans_CB,do_you_have_microchip_CB,castrated_neutered_CB,convert_yr_to_age;
    EditText                pet_features_ET,pet_name_ET,pet_microchip_no_ET,castrated_neutered_ET,age_neumeric;
    TextView                pet_choosePlan,pet_declaration,ageViewTv,pet_layout_calenderTextView_dialog;
    BottomSheetDialog       plans_BSD, declaration_BSD;
    AppCompatSpinner        add_pet_color,add_pet_breed,parent_state_spinner,parent_city_spinner,age_wise,add_pet_type;
    InsurancePlansAdapter   insurancePlansAdapter;
    DiseasesListAdapter     diseasesListAdapter;
    LinearLayout            day_and_age_layout;
    InsuranceMastersResponse insuranceMastersResponse;



    //document details layout
    LinearLayout            upload_five_images_LL, pet_rfid_LL, document_LL;
    TextView                front_image_TV,back_image_TV,left_image_TV,right_image_TV,top_image_TV,rfid_image_TV,purchase_proof_image_TV,vaccination_card_image_TV,pedigree_cert_image_TV;
    ConstraintLayout        pedigree_cert_image_CL;
    ProgressBar             front_image_progress_bar,back_image_progress_bar,left_image_progress_bar,right_image_progress_bar,top_image_progress_bar,rfid_image_progress_bar,purchase_proof_image_progress_bar,pedigree_cert_image_progress_bar,vaccination_card_image_progress_bar;
    ImageView               front_image_upload_IV,back_image_upload_IV,left_image_upload_IV,right_image_upload_IV,top_image_upload_IV,rfid_image_upload_IV,purchase_proof_image_upload_IV,vaccination_card_image_upload_IV,pedigree_cert_image_upload_IV;
    ImageView               front_image_delete_IV,back_image_delete_IV,left_image_delete_IV,right_image_delete_IV,top_image_delete_IV,rfid_image_delete_IV,purchase_proof_image_delete_IV,vaccination_card_image_delete_IV,pedigree_cert_image_delete_IV;
    CircleImageView         front_image_CIV,back_image_CIV,left_image_CIV,right_image_CIV,top_image_CIV,rfid_image_CIV;


    ArrayList<String> state;
    ArrayList<String> city;
    ArrayList<String> petAgeType;
    ArrayList<String> petType;
    ArrayList<String> petBreed;
    ArrayList<String> petAge;
    ArrayList<String> petColor;
    GetAllDetailAfterLoginResponse getAllDetailAfterLoginResponse;
    HashMap<String, String> petTypeHashMap = new HashMap<>();
    HashMap<String, String> petBreedHashMap = new HashMap<>();
    HashMap<String, String> petAgeHashMap = new HashMap<>();
    HashMap<String, String> petColorHashMap = new HashMap<>();
    HashMap<String, String> petAgeUnitHash = new HashMap<>();

    ArrayList<InsurancePlanModel> insurancePlanModels = new ArrayList<>();
    ArrayList<DiseasesListModel>  diseasesListModels = new ArrayList<>();
    HashMap<String, String> stateHasmap             = new HashMap<>();
    HashMap<String, String> cityHasmap              = new HashMap<>();
    HashMap<String, String> insuranceHashMap        = new HashMap<>();
    HashMap<String, String> diseasesListHashMap     = new HashMap<>();

    Dialog          dialog;
    MediaUtils      mediaUtils;
    Methods         methods;


    String                  selectedDiseasesId="",selectedInsurancePlans = "",strSpnrSexId="",strSpnrColorId, strSpnrColor,strSpnrBreedId, strSpnrBreed,getStrSpnerItemPetNmId,strSpnerItemPetNm,strAgeCount,strStateSpnr, strStateId,strCitySpnr,strStringCityId, tokenForInsurance;
    boolean                 isVaccinated = false, isYourPet_KIC_register=false,is_pedigree_lineage=false;
    boolean                 isFrontImage =false,isBackImage=false,isTopImage=false,isLeftImage=false,isRightImage=false,isRfidImage=false,isPurchaseProof=false;


    int front_status            = 0;
    int back_status             = 0;
    int top_status              = 0;
    int left_status             = 0;
    int right_status            = 0;
    int rfid_status             = 0;
    int purchase_proof_status   = 0;
    int pedigree_status         = 0;
    int vaccination_card        = 0;


    private int                 DOC_UPLOAD=105;
    String                      imageTypeName="", petId, backImageUrl=null, topImageUrl=null,leftImageUrl=null,rightImageUrl=null,rfidImageUrl=null,pedigreeDocumentUrl=null,vaccinationCardUrl=null,purchaseProofUrl=null,frontImageUrl=null;

    String[] mimeTypes = {"image/*",
            "application/pdf",
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "text/plain"
    };

    String afterLogin = "no";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_insurance);

        Intent intent = getIntent();
        afterLogin = intent.getStringExtra("afterLogin");
        methods         = new Methods(this);
        mediaUtils      = new MediaUtils(this);
        initAllSectionView();
        getState();
        getInsuranceMasters();
        getPetAgeUnit();
        getPetType();

        if (afterLogin.equals("yes")){
            petId      = intent.getStringExtra("petId");
            strSpnrBreed = intent.getStringExtra("pet_breed");
            strSpnrColor = intent.getStringExtra("pet_color");
            String  url = "pet-punching-policy/"+petId;
            Log.e("Url",url);
            methods.showCustomProgressBarDialog(this);
            ApiService<GetAllDetailAfterLoginResponse> service = new ApiService<>();
            service.get(this, ApiClient.getApiInterface().getAllDetailAfterLogin(Config.token,url), "GetAllDetailOfPet");


        }
    }

    private void initAllSectionView() {
        initTopSectionView();
        initViewPetParentDeatils();
        initViewPetDeatils();
        initViewDocumentSection();
    }

    private void initTopSectionView() {

        next_BT                             = findViewById(R.id.next_BT);
        privious_BT                         = findViewById(R.id.privious_BT);
        back_arrow_CV                       = findViewById(R.id.back_arrow_CV);
        circular_PB                         = findViewById(R.id.circular_PB);
        loading_PB                          = findViewById(R.id.loading_PB);
        step_name_TV                        = findViewById(R.id.step_name_TV);
        next_step_TV                        = findViewById(R.id.next_step_TV);
        step_count_TV                       = findViewById(R.id.step_count_TV);
        pet_parent_details_layout_frame     = findViewById(R.id.pet_parent_details_layout_frame);
        insurance_pet_details_layout_frame  = findViewById(R.id.insurance_pet_details_layout_frame);
        insurance_images_layout_frame       = findViewById(R.id.insurance_images_layout_frame);



        next_BT.setOnClickListener(this);
        privious_BT.setOnClickListener(this);
        back_arrow_CV.setOnClickListener(this);



        if (countSteps==1){
            privious_BT.setVisibility(View.GONE);
            pet_parent_details_layout_frame.setVisibility(View.VISIBLE);
            insurance_pet_details_layout_frame.setVisibility(View.GONE);
            insurance_images_layout_frame.setVisibility(View.GONE);
            step_name_TV.setText("Pet Parent Details");
            next_step_TV.setText("Next: Pet Details");
            step_count_TV.setText("1 of 3");

        }


    }

    private void initViewPetParentDeatils() {
        //pet parent deatils
        parent_state_spinner                = findViewById(R.id.parent_state_spinner);
        parent_city_spinner                 = findViewById(R.id.parent_city_spinner);
        calenderTextView_dialog             = findViewById(R.id.calenderTextView_dialog);
        parent_first_name_ET                = findViewById(R.id.parent_first_name_ET);
        parent_last_name_ET                 = findViewById(R.id.parent_last_name_ET);
        parent_email_ET                     = findViewById(R.id.parent_email_ET);
        parent_phone_ET                     = findViewById(R.id.parent_phone_ET);
        parent_address_ET                   = findViewById(R.id.parent_address_ET);
        parent_pinCode_ET                   = findViewById(R.id.parent_pinCode_ET);
        parent_referralCode_ET              = findViewById(R.id.parent_referralCode_ET);

        calenderTextView_dialog.setOnClickListener(this);

    }

    private void initViewPetDeatils() {
        //pet details layout

        add_pet_type                        = findViewById(R.id.add_pet_type);
        maleRB                              = findViewById(R.id.maleRB);
        femaleRB                            = findViewById(R.id.femaleRB);
        pet_name_ET                         = findViewById(R.id.pet_name_ET);
        pet_features_ET                     = findViewById(R.id.pet_features_ET);
        add_pet_breed                       = findViewById(R.id.add_pet_breed_dialog);
        add_pet_color                       = findViewById(R.id.add_pet_color_dialog);
        do_you_have_microchip_CB            = findViewById(R.id.do_you_have_microchip_CB);
        pet_microchip_no_ET                 = findViewById(R.id.pet_microchip_no_ET);
        castrated_neutered_CB               = findViewById(R.id.castrated_neutered_CB);
        castrated_neutered_ET               = findViewById(R.id.castrated_neutered_ET);
        pet_declaration                     = findViewById(R.id.pet_declaration);
        pet_choosePlan                      = findViewById(R.id.pet_choosePlan);
        age_wise                            = findViewById(R.id.age_wise);
        convert_yr_to_age                   = findViewById(R.id.convert_yr_to_age);
        age_neumeric                        = findViewById(R.id.age_neumeric);
        day_and_age_layout                  = findViewById(R.id.day_and_age_layout);
        ageViewTv                           = findViewById(R.id.ageViewTv);
        pet_layout_calenderTextView_dialog  = findViewById(R.id.pet_layout_calenderTextView_dialog);
        is_vaccinated_CB                    = findViewById(R.id.is_vaccinated_CB);
        pedigree_lineage_CB                 = findViewById(R.id.pedigree_lineage_CB);
        kic_CB                              = findViewById(R.id.kic_CB);
        ageViewTv.setText("Age:- 0 Days");
        add_pet_type.setClickable(false);
        add_pet_type.setEnabled(false);
        age_neumeric.addTextChangedListener(new TextWatcher() {
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
                if (methods.isInternetOn()) {
                    if (age_neumeric.isFocused()) {
                        Rect outRect = new Rect();
                        age_neumeric.getGlobalVisibleRect(outRect);
                        if (age_neumeric.getText().toString().isEmpty()) {

                        } else {
                            if (strAgeCount.equals("Day")) {
                                getPetDateofBirthDependsOnDays(age_neumeric.getText().toString());
                            } else if (strAgeCount.equals("Week")) {
                                int weekToDays = Integer.parseInt(age_neumeric.getText().toString());
                                int days = weekToDays * 7;
                                getPetDateofBirthDependsOnDays(String.valueOf(days));
                            } else if (strAgeCount.equals("Month")) {
                                int monthToDays = Integer.parseInt(age_neumeric.getText().toString());
                                int days = monthToDays * 30;
                                getPetDateofBirthDependsOnDays(String.valueOf(days));
                            } else {
                                int yearsToDays = Integer.parseInt(age_neumeric.getText().toString());
                                int days = yearsToDays * 365;
                                getPetDateofBirthDependsOnDays(String.valueOf(days));
                            }

                        }
                    }

                } else {
                    methods.DialogInternet();
                }
            }
        });


        //pet details cliks

        do_you_have_microchip_CB.setOnClickListener(this);
        castrated_neutered_CB.setOnClickListener(this);
        pet_choosePlan.setOnClickListener(this);
        pet_declaration.setOnClickListener(this);
        convert_yr_to_age.setOnClickListener(this);
        pet_layout_calenderTextView_dialog.setOnClickListener(this);
        is_vaccinated_CB.setOnClickListener(this);
        pedigree_lineage_CB.setOnClickListener(this);
        kic_CB.setOnClickListener(this);

    }

    private void initViewDocumentSection() {
        //document details layout
        upload_five_images_LL               = findViewById(R.id.upload_five_images_LL);
        pet_rfid_LL                         = findViewById(R.id.pet_rfid_LL);
        document_LL                         = findViewById(R.id.document_LL);
        pedigree_cert_image_CL              = findViewById(R.id.pedigree_cert_image_CL);

        //textView
        front_image_TV                      = findViewById(R.id.front_image_TV);
        back_image_TV                       = findViewById(R.id.back_image_TV);
        left_image_TV                       = findViewById(R.id.left_image_TV);
        right_image_TV                      = findViewById(R.id.right_image_TV);
        top_image_TV                        = findViewById(R.id.top_image_TV);
        rfid_image_TV                       = findViewById(R.id.rfid_image_TV);
        purchase_proof_image_TV             = findViewById(R.id.purchase_proof_image_TV);
        vaccination_card_image_TV           = findViewById(R.id.vaccination_card_image_TV);
        pedigree_cert_image_TV              = findViewById(R.id.pedigree_cert_image_TV);


        //circularImage view
        front_image_CIV                     = findViewById(R.id.front_image_CIV);
        back_image_CIV                      = findViewById(R.id.back_image_CIV);
        left_image_CIV                      = findViewById(R.id.left_image_CIV);
        right_image_CIV                     = findViewById(R.id.right_image_CIV);
        top_image_CIV                       = findViewById(R.id.top_image_CIV);
        rfid_image_CIV                      = findViewById(R.id.rfid_image_CIV);


        //progress bar
        vaccination_card_image_progress_bar = findViewById(R.id.vaccination_card_image_progress_bar);
        pedigree_cert_image_progress_bar    = findViewById(R.id.pedigree_cert_image_progress_bar);
        purchase_proof_image_progress_bar   = findViewById(R.id.purchase_proof_image_progress_bar);
        rfid_image_progress_bar             = findViewById(R.id.rfid_image_progress_bar);
        top_image_progress_bar              = findViewById(R.id.top_image_progress_bar);
        right_image_progress_bar            = findViewById(R.id.right_image_progress_bar);
        left_image_progress_bar             = findViewById(R.id.left_image_progress_bar);
        back_image_progress_bar             = findViewById(R.id.back_image_progress_bar);
        front_image_progress_bar            = findViewById(R.id.front_image_progress_bar);

        //upload icon
        front_image_upload_IV               = findViewById(R.id.front_image_upload_IV);
        back_image_upload_IV                = findViewById(R.id.back_image_upload_IV);
        left_image_upload_IV                = findViewById(R.id.left_image_upload_IV);
        right_image_upload_IV               = findViewById(R.id.right_image_upload_IV);
        top_image_upload_IV                 = findViewById(R.id.top_image_upload_IV);
        rfid_image_upload_IV                = findViewById(R.id.rfid_image_upload_IV);
        purchase_proof_image_upload_IV      = findViewById(R.id.purchase_proof_image_upload_IV);
        vaccination_card_image_upload_IV    = findViewById(R.id.vaccination_card_image_upload_IV);
        pedigree_cert_image_upload_IV       = findViewById(R.id.pedigree_cert_image_upload_IV);

        //delete icon

        front_image_delete_IV               = findViewById(R.id.front_image_delete_IV);
        back_image_delete_IV                = findViewById(R.id.back_image_delete_IV);
        left_image_delete_IV                = findViewById(R.id.left_image_delete_IV);
        right_image_delete_IV               = findViewById(R.id.right_image_delete_IV);
        top_image_delete_IV                 = findViewById(R.id.top_image_delete_IV);
        rfid_image_delete_IV                = findViewById(R.id.rfid_image_delete_IV);
        purchase_proof_image_delete_IV      = findViewById(R.id.purchase_proof_image_delete_IV);
        vaccination_card_image_delete_IV    = findViewById(R.id.vaccination_card_image_delete_IV);
        pedigree_cert_image_delete_IV       = findViewById(R.id.pedigree_cert_image_delete_IV);

        //delete click
        front_image_delete_IV.setOnClickListener(this);
        back_image_delete_IV.setOnClickListener(this);
        left_image_delete_IV.setOnClickListener(this);
        right_image_delete_IV.setOnClickListener(this);
        top_image_delete_IV.setOnClickListener(this);
        rfid_image_delete_IV.setOnClickListener(this);
        purchase_proof_image_delete_IV.setOnClickListener(this);
        vaccination_card_image_delete_IV.setOnClickListener(this);
        pedigree_cert_image_delete_IV.setOnClickListener(this);


        //upload clicks
        front_image_upload_IV.setOnClickListener(this);
        back_image_upload_IV.setOnClickListener(this);
        left_image_upload_IV.setOnClickListener(this);
        right_image_upload_IV.setOnClickListener(this);
        top_image_upload_IV.setOnClickListener(this);
        rfid_image_upload_IV.setOnClickListener(this);
        purchase_proof_image_upload_IV.setOnClickListener(this);
        vaccination_card_image_upload_IV.setOnClickListener(this);
        pedigree_cert_image_upload_IV.setOnClickListener(this);



    }



    private void getPetType() {
        ApiService<PetTypeResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().petTypeApi(), "GetPetTypes");
    }

    private void getPetAgeUnit() {
        ApiService<PetAgeUnitResponseData> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getPetAgeUnit(), "GetPetAgeUnit");
    }

    private void getInsuranceMasters() {
        ApiService<InsuranceMastersResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().insuranceMasters(), "InsuranceMasters");
    }

    private void getState() {
        ApiService<StateResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getState(), "GetState");

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.front_image_upload_IV:
                imageTypeName = "front_image";
                showPictureDialog();
                break;

            case R.id.back_image_upload_IV:
                imageTypeName = "back_image";
                showPictureDialog();
                break;

            case R.id.left_image_upload_IV:
                imageTypeName = "left_image";
                showPictureDialog();
                break;

            case R.id.right_image_upload_IV:
                imageTypeName = "right_image";
                showPictureDialog();
                break;

            case R.id.top_image_upload_IV:
                imageTypeName = "top_image";
                showPictureDialog();
                break;

            case R.id.rfid_image_upload_IV:
                imageTypeName = "rfid_image";
                showPictureDialog();
                break;

            case R.id.purchase_proof_image_upload_IV:
                imageTypeName = "purchase_proof_image";
                Intent intent1 = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent1.addCategory(Intent.CATEGORY_OPENABLE);
                intent1.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                intent1.setType("*application/pdf||*application/doc");
                startActivityForResult(Intent.createChooser(intent1, "Select a file"), DOC_UPLOAD);
                break;

            case R.id.pedigree_cert_image_upload_IV:
                imageTypeName = "pedigree_proof_image";
                Intent intent2 = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent2.addCategory(Intent.CATEGORY_OPENABLE);
                intent2.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                intent2.setType("*application/pdf||*application/doc");
                startActivityForResult(Intent.createChooser(intent2, "Select a file"), DOC_UPLOAD);
                break;

            case R.id.vaccination_card_image_upload_IV:
                imageTypeName = "vaccination_card";
                Intent intent3 = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent3.addCategory(Intent.CATEGORY_OPENABLE);
                intent3.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                intent3.setType("*application/pdf||*application/doc");
                startActivityForResult(Intent.createChooser(intent3, "Select a file"), DOC_UPLOAD);
                break;

            case R.id.is_vaccinated_CB:
                if (is_vaccinated_CB.isChecked()){
                    isVaccinated = true;
                }else {
                    isVaccinated = false;
                }
                break;

            case R.id.kic_CB:
                if (kic_CB.isChecked()){
                    isYourPet_KIC_register = true;
                }else {
                    isYourPet_KIC_register = false;
                }
                break;

            case R.id.pedigree_lineage_CB:
                if (pedigree_lineage_CB.isChecked()){
                    is_pedigree_lineage = true;
                }else {
                    is_pedigree_lineage = false;
                }
                break;
            case R.id.select_all_plans_CB:
                if (select_all_plans_CB.isChecked()){
                    for (int i =0; i<insurancePlanModels.size();i++){
                        insurancePlanModels.get(i).setStatus(true);
                    }
                }else {
                    for (int i =0; i<insurancePlanModels.size();i++){
                        insurancePlanModels.get(i).setStatus(false);
                    }
                }
                insurancePlansAdapter.notifyDataSetChanged();
                checkBoxSelectAllCheck();
                break;

            case R.id.convert_yr_to_age:
                if (((CompoundButton) v).isChecked()) {
                    day_and_age_layout.setVisibility(View.VISIBLE);
                    pet_layout_calenderTextView_dialog.setVisibility(View.GONE);
                } else {
                    if (age_neumeric.getText().toString().isEmpty()) {

                    } else {
                        if (strAgeCount.equals("Day")) {
                            getPetDateofBirthDependsOnDays(age_neumeric.getText().toString());
                        } else if (strAgeCount.equals("Week")) {
                            int weekToDays = Integer.parseInt(age_neumeric.getText().toString());
                            int days = weekToDays * 7;
                            getPetDateofBirthDependsOnDays(String.valueOf(days));
                        } else if (strAgeCount.equals("Month")) {
                            int monthToDays = Integer.parseInt(age_neumeric.getText().toString());
                            int days = monthToDays * 30;
                            getPetDateofBirthDependsOnDays(String.valueOf(days));
                        } else {
                            int yearsToDays = Integer.parseInt(age_neumeric.getText().toString());
                            int days = yearsToDays * 365;
                            getPetDateofBirthDependsOnDays(String.valueOf(days));
                        }

                    }
                    day_and_age_layout.setVisibility(View.GONE);
                    pet_layout_calenderTextView_dialog.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.calenderTextView_dialog:

                final Calendar cldr     = Calendar.getInstance();
                int day                 = cldr.get(Calendar.DAY_OF_MONTH);
                int month               = cldr.get(Calendar.MONTH);
                int year                = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                calenderTextView_dialog.setText(Config.changeDateFormat(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year) );
                            }
                        }, year, month, day);
                picker.getDatePicker().setMaxDate(System.currentTimeMillis());
                picker.show();

                break;


            case R.id.pet_layout_calenderTextView_dialog:
                final Calendar pet_layout_cldr = Calendar.getInstance();
                int pet_layout_day             = pet_layout_cldr.get(Calendar.DAY_OF_MONTH);
                int pet_layout_month           = pet_layout_cldr.get(Calendar.MONTH);
                int pet_layout_year            = pet_layout_cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        pet_layout_calenderTextView_dialog.setText(Config.changeDateFormat(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year));
                        String DoB          = dayOfMonth + " " + (monthOfYear + 1) + " " + year;
                        String age          = String.valueOf(methods.getDays(DoB, methods.getDate()));
                        String DoBforage    = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        age                 = age.substring(0, age.length() - 2);
                        getPetAgeString(DoBforage);
                        age_neumeric.setText(age);
                    }
                }, pet_layout_year, pet_layout_month, pet_layout_day);
                picker.getDatePicker().setMaxDate(System.currentTimeMillis());
                picker.show();
                break;


            case R.id.next_BT:

                if (countSteps==1){
                    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                    String pet_parent_first_name, pet_parent_last_name, pet_parent_email, pet_parent_phone_no, pet_parent_dob, pet_parent_address, pet_parent_pinCode, pet_parent_rf_code;
                    pet_parent_first_name       = parent_first_name_ET.getText().toString().trim();
                    pet_parent_last_name        = parent_last_name_ET.getText().toString().trim();
                    pet_parent_email            = parent_email_ET.getText().toString().trim();
                    pet_parent_phone_no         = parent_phone_ET.getText().toString().trim();
                    pet_parent_dob              = calenderTextView_dialog.getText().toString().trim();
                    pet_parent_address          = parent_address_ET.getText().toString().trim();
                    pet_parent_pinCode          = parent_pinCode_ET.getText().toString().trim();
                    pet_parent_rf_code          = parent_referralCode_ET.getText().toString().trim();

                    if (pet_parent_first_name.isEmpty()){
                        parent_first_name_ET.setError("Enter first name !");
                        break;
                    }if (pet_parent_last_name.isEmpty()){
                        parent_last_name_ET.setError("Enter last name !");
                        break;
                    }if (pet_parent_email.isEmpty()){
                        parent_email_ET.setError("Enter email address !");
                        break;
                    }if (!pet_parent_email.matches(emailPattern)) {
                        parent_email_ET.setError("Invalid email !");
                        break;
                    }if (pet_parent_phone_no.isEmpty()){
                        parent_phone_ET.setError("Enter phone no !");
                        break;
                    }if (pet_parent_dob.isEmpty()){
                        Toast.makeText(this, "Enter date of birth !", Toast.LENGTH_SHORT).show();
                        break;
                    }if (pet_parent_address.isEmpty()){
                        parent_address_ET.setError("Enter your address");
                        break;
                    }if (strStateSpnr.equals("Select State")){
                        Toast.makeText(this, "Select State", Toast.LENGTH_SHORT).show();
                        break;
                    }if (strCitySpnr.equals("Select City")){
                        Toast.makeText(this, "Select City", Toast.LENGTH_SHORT).show();
                        break;
                    }if (pet_parent_pinCode.isEmpty()){
                        parent_pinCode_ET.setError("Enter pin code");
                        break;
                    }if (pet_parent_pinCode.length()!=6){
                        parent_pinCode_ET.setError("Invalid pin code !");
                        break;
                    }

                    PunchingPolicyParams    punchingPolicyParams = new PunchingPolicyParams();

                    punchingPolicyParams.setFirstName(pet_parent_first_name);
                    punchingPolicyParams.setLastName(pet_parent_last_name);
                    punchingPolicyParams.setEmail(pet_parent_email);
                    punchingPolicyParams.setMobileNumber(pet_parent_phone_no);
                    punchingPolicyParams.setOwnerDob(pet_parent_dob);
                    punchingPolicyParams.setAddress(pet_parent_address);
                    punchingPolicyParams.setStateId(strStateId);
                    punchingPolicyParams.setCityId(strStringCityId);
                    punchingPolicyParams.setPinCode(pet_parent_pinCode);
                    punchingPolicyParams.setReferralCode(pet_parent_rf_code);

                    PunchingPolicyRequest punchingPolicyRequest = new PunchingPolicyRequest();
                    punchingPolicyRequest.setData(punchingPolicyParams);

                    loading_PB.setVisibility(View.VISIBLE);
                    circular_PB.setVisibility(View.GONE);
                    next_BT.setEnabled(false);
                    privious_BT.setEnabled(false);
                    if (afterLogin.equals("yes")){
                        ApiService<JsonObject> service = new ApiService<>();
                        service.get(this, ApiClient.getApiInterface().parentAfterLoginPunchingPolicy(Config.token,punchingPolicyRequest), "PunchingPolicyRequest");
                        Log.e("PunchingPolicyRequest"+"AfterLogin", "Request=> " +methods.getRequestJson(punchingPolicyRequest));
                    }else {
                        ApiService<JsonObject> service1 = new ApiService<>();
                        service1.get(this, ApiClient.getApiInterface().punchingPolicy(tokenForInsurance,punchingPolicyRequest), "PunchingPolicyRequest");
//                        Log.e("PunchingPolicyRequest", "Request=> " +methods.getRequestJson(punchingPolicyRequest));
                    }

                }
                else if (countSteps==2){
                    if (do_you_have_microchip_CB.isChecked()){
                        upload_five_images_LL.setVisibility(View.GONE);
                        pet_rfid_LL.setVisibility(View.VISIBLE);
                    }else {
                        upload_five_images_LL.setVisibility(View.VISIBLE);
                        pet_rfid_LL.setVisibility(View.GONE);
                    }

                    if (maleRB.isChecked()) {
                        strSpnrSexId = "1";
                    } else if (femaleRB.isChecked()) {
                        strSpnrSexId = "2";
                    }


                    String     strPetName          = pet_name_ET.getText().toString().trim();
                    String     strPetBirthDay      = pet_layout_calenderTextView_dialog.getText().toString().trim();
                    String     strFeatures         = pet_features_ET.getText().toString().trim();
                    String     strMicroChipNo      = pet_microchip_no_ET.getText().toString();
                    String     strCastrated        = castrated_neutered_ET.getText().toString();
                    boolean    isCastrated         = false;


                    if (strSpnrSexId.equals("")){
                        Toast.makeText(this, "Select Gender !", Toast.LENGTH_SHORT).show();
                        break;
                    }if (strSpnrBreed.isEmpty() || (strSpnrBreed.equals("Pet Breed"))) {
                        Toast.makeText(this, "Select Breed!!", Toast.LENGTH_SHORT).show();
                        break;
                    }if (strPetName.isEmpty()) {
                        Toast.makeText(this, "Enter Pet Name", Toast.LENGTH_SHORT).show();
                        pet_name_ET.setError("Enter Pet Name");
                        break;
                    }if (strPetBirthDay.isEmpty()) {
                        Toast.makeText(this, "Pet YOB", Toast.LENGTH_SHORT).show();
                        break;
                    }if ((strSpnrColor.isEmpty()) || (strSpnrColor.equals("Pet Color"))) {
                        Toast.makeText(this, "Select Pet Color!!", Toast.LENGTH_SHORT).show();
                        break;
                    }if (pet_choosePlan.getText().toString().equals("Choose plan")){
                        Toast.makeText(this, "Choose your plan!", Toast.LENGTH_SHORT).show();
                        showPlansDialog();
                        break;
                    }if (pet_declaration.getText().toString().equals("Select declaration")){
                        Toast.makeText(this, "Select declaration !", Toast.LENGTH_SHORT).show();
                        showDeclaration();
                        break;
                    }if (do_you_have_microchip_CB.isChecked()&&strMicroChipNo.equals("")){
                        pet_microchip_no_ET.setError("Enter microchip no ");
                        Toast.makeText(this, "Enter microchip no", Toast.LENGTH_SHORT).show();
                        break;
                    }if (castrated_neutered_CB.isChecked()&&strCastrated.equals("")){
                        castrated_neutered_ET.setError("Enter Castrated/Neutered");
                        Toast.makeText(this, "Enter Castrated/Neutered", Toast.LENGTH_SHORT).show();
                        break;
                    }if (!is_vaccinated_CB.isChecked()){
                        Toast.makeText(this, "Is Your Pet Vaccinated", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    PunchingPolicyPetParams punchingPolicyPetParams = new PunchingPolicyPetParams();

                    punchingPolicyPetParams.setPetCategoryId(getStrSpnerItemPetNmId);
                    punchingPolicyPetParams.setPetName(strPetName);
                    punchingPolicyPetParams.setPetSexId(strSpnrSexId);
                    punchingPolicyPetParams.setPetDateofBirth(strPetBirthDay);
                    punchingPolicyPetParams.setPetBreedId(strSpnrBreedId);
                    punchingPolicyPetParams.setPetColorId(strSpnrColorId);
                    punchingPolicyPetParams.setPlans(selectedInsurancePlans.substring(1));
                    punchingPolicyPetParams.setMicrochipNumber(strMicroChipNo);
                    punchingPolicyPetParams.setDeclaration(selectedDiseasesId.substring(1));
                    punchingPolicyPetParams.setRegistrationPet(isYourPet_KIC_register);
                    punchingPolicyPetParams.setVaccinated(isVaccinated);
                    punchingPolicyPetParams.setCastrated(isCastrated);
                    punchingPolicyPetParams.setCastratedReason(strCastrated);
                    punchingPolicyPetParams.setFeatures(strFeatures);


                    loading_PB.setVisibility(View.VISIBLE);
                    circular_PB.setVisibility(View.GONE);
                    next_BT.setEnabled(false);
                    privious_BT.setEnabled(false);
                    if (afterLogin.equals("yes")){
                        punchingPolicyPetParams.setPetId(petId);
                        PunchingPolicyPetRequest punchingPolicyPetRequest = new PunchingPolicyPetRequest();
                        punchingPolicyPetRequest.setData(punchingPolicyPetParams);
                        ApiService<JsonObject> service1 = new ApiService<>();
                        service1.get(this, ApiClient.getApiInterface().petAfterLoginPunchingPolicyPet(Config.token,punchingPolicyPetRequest), "PunchingPolicyPetRequest");
//                        Log.e("PunchingPolicyPetRequest", "Request=> " +methods.getRequestJson(punchingPolicyPetRequest));
                    }else {
                        PunchingPolicyPetRequest punchingPolicyPetRequest = new PunchingPolicyPetRequest();
                        punchingPolicyPetRequest.setData(punchingPolicyPetParams);
                        ApiService<JsonObject> service1 = new ApiService<>();
                        service1.get(this, ApiClient.getApiInterface().punchingPolicyPet(tokenForInsurance,punchingPolicyPetRequest), "PunchingPolicyPetRequest");
//                        Log.e("PunchingPolicyPetRequest", "Request=> " +methods.getRequestJson(punchingPolicyPetRequest));
                    }


                }
                else if(countSteps==3){
                    if (do_you_have_microchip_CB.isChecked()&&rfidImageUrl==null){
                        Toast.makeText(this, "Please upload RFID image", Toast.LENGTH_SHORT).show();
                    }else {
                        if (!do_you_have_microchip_CB.isChecked()&&frontImageUrl==null){
                            Toast.makeText(this, "Please upload pet front image", Toast.LENGTH_SHORT).show();
                            break;
                        }if (!do_you_have_microchip_CB.isChecked()&&backImageUrl==null){
                            Toast.makeText(this, "Please upload pet back image", Toast.LENGTH_SHORT).show();
                            break;
                        }if (!do_you_have_microchip_CB.isChecked()&&leftImageUrl==null){
                            Toast.makeText(this, "Please upload pet left image", Toast.LENGTH_SHORT).show();
                            break;
                        }if (!do_you_have_microchip_CB.isChecked()&&rightImageUrl==null){
                            Toast.makeText(this, "Please upload pet right image", Toast.LENGTH_SHORT).show();
                            break;
                        }if (!do_you_have_microchip_CB.isChecked()&&topImageUrl==null){
                            Toast.makeText(this, "Please upload pet top image", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }


                    if (pedigree_lineage_CB.isChecked()&&pedigreeDocumentUrl==null){
                        Toast.makeText(this, "Please upload pet pedigree certificate", Toast.LENGTH_SHORT).show();
                        break;
                    }if (vaccinationCardUrl==null){
                        Toast.makeText(this, "Please upload pet vaccination card", Toast.LENGTH_SHORT).show();
                        break;
                    }

                    loading_PB.setVisibility(View.VISIBLE);
                    circular_PB.setVisibility(View.GONE);
                    PunchingPolicyPetDocumentsParams punchingPolicyPetDocumentsParams = new PunchingPolicyPetDocumentsParams();
                    punchingPolicyPetDocumentsParams.setPetId(petId);
                    punchingPolicyPetDocumentsParams.setPetImageUrl(frontImageUrl);
                    punchingPolicyPetDocumentsParams.setPetImageUrl2(backImageUrl);
                    punchingPolicyPetDocumentsParams.setPetImageUrl3(leftImageUrl);
                    punchingPolicyPetDocumentsParams.setPetImageUrl4(rightImageUrl);
                    punchingPolicyPetDocumentsParams.setPetImageUrl5(topImageUrl);
                    punchingPolicyPetDocumentsParams.setPetImageUrl6(rfidImageUrl);
                    punchingPolicyPetDocumentsParams.setPurchaseProofUrl(purchaseProofUrl);
                    punchingPolicyPetDocumentsParams.setPedigreeCertificateUrl(pedigreeDocumentUrl);
                    punchingPolicyPetDocumentsParams.setVaccinationUrl(vaccinationCardUrl);

                    PunchingPolicyPetDocumentsRequest punchingPolicyPetDocumentsRequest = new PunchingPolicyPetDocumentsRequest();
                    punchingPolicyPetDocumentsRequest.setData(punchingPolicyPetDocumentsParams);
                    privious_BT.setEnabled(false);
                    next_BT.setEnabled(false);
                    if (afterLogin.equals("yes")){
                        ApiService<JsonObject> service = new ApiService<>();
                        service.get(this, ApiClient.getApiInterface().userPunchingPolicyDocumentsUpload(Config.token,punchingPolicyPetDocumentsRequest), "PunchingPolicyPetDocuments");
//                        Log.e("PunchingPolicyPetRequest", "Request=> " +methods.getRequestJson(punchingPolicyPetDocumentsRequest));

                    }else {
                        ApiService<JsonObject> service1 = new ApiService<>();
                        service1.get(this, ApiClient.getApiInterface().punchingPolicyDocumentsUpload(tokenForInsurance,punchingPolicyPetDocumentsRequest), "PunchingPolicyPetDocuments");
//                        Log.e("PunchingPolicyPetRequest", "Request=> " +methods.getRequestJson(punchingPolicyPetDocumentsRequest));

                    }

                }

                break;

            case R.id.privious_BT:

            case R.id.back_arrow_CV:

                if (countSteps==3){
                    step_count_TV.setText("2 of 3");
                    step_name_TV.setText("Pet Details");
                    next_step_TV.setVisibility(View.VISIBLE);
                    next_step_TV.setText("Next: Document Section");
                    pet_parent_details_layout_frame.setVisibility(View.GONE);
                    insurance_pet_details_layout_frame.setVisibility(View.VISIBLE);
                    insurance_images_layout_frame.setVisibility(View.GONE);
                    privious_BT.setVisibility(View.VISIBLE);
                    circular_PB.setProgress(50);
                    countSteps = 2;
                }else if (countSteps==2){
                    step_count_TV.setText("1 of 3");
                    step_name_TV.setText("Pet Parent Details");
                    next_step_TV.setVisibility(View.VISIBLE);
                    next_step_TV.setText("Next: Pet Details");
                    pet_parent_details_layout_frame.setVisibility(View.VISIBLE);
                    insurance_pet_details_layout_frame.setVisibility(View.GONE);
                    insurance_images_layout_frame.setVisibility(View.GONE);
                    privious_BT.setVisibility(View.GONE);
                    circular_PB.setProgress(10);
                    countSteps = 1;
                }else {
                    finish();
                }

                break;



            case R.id.do_you_have_microchip_CB:
                if (do_you_have_microchip_CB.isChecked()){
                    pet_microchip_no_ET.setVisibility(View.VISIBLE);
                }else {
                    pet_microchip_no_ET.setVisibility(View.GONE);
                }
                break;

            case R.id.castrated_neutered_CB:
                if (castrated_neutered_CB.isChecked()){
                    castrated_neutered_ET.setVisibility(View.VISIBLE);
                }else {
                    castrated_neutered_ET.setVisibility(View.GONE);
                }
                break;

            case R.id.pet_choosePlan:
                    showPlansDialog();

                break;

            case R.id.pet_declaration:
                    showDeclaration();

                break;

            case R.id.front_image_delete_IV:
                frontImageUrl = "";
                front_image_progress_bar.setProgress(0);
                Glide.with(this)
                        .load(R.drawable.empty_pet_image)
                        .into(front_image_CIV);
                front_image_upload_IV.setVisibility(View.VISIBLE);
                front_image_delete_IV.setVisibility(View.GONE);

                break;

            case R.id.back_image_delete_IV:
                backImageUrl = "";
                Glide.with(this)
                        .load(R.drawable.empty_pet_image)
                        .into(back_image_CIV);
                back_image_progress_bar.setProgress(0);
                back_image_upload_IV.setVisibility(View.VISIBLE);
                back_image_delete_IV.setVisibility(View.GONE);
                break;

            case R.id.top_image_delete_IV:
                topImageUrl = "";
                Glide.with(this)
                        .load(R.drawable.empty_pet_image)
                        .into(top_image_CIV);
                top_image_progress_bar.setProgress(0);
                top_image_upload_IV.setVisibility(View.VISIBLE);
                top_image_delete_IV.setVisibility(View.GONE);
                break;

            case R.id.left_image_delete_IV:
                leftImageUrl = "";
                Glide.with(this)
                        .load(R.drawable.empty_pet_image)
                        .into(left_image_CIV);
                left_image_progress_bar.setProgress(0);
                left_image_upload_IV.setVisibility(View.VISIBLE);
                left_image_delete_IV.setVisibility(View.GONE);
                break;

            case R.id.right_image_delete_IV:
                rightImageUrl = "";
                Glide.with(this).load(R.drawable.empty_pet_image).into(right_image_CIV);
                right_image_progress_bar.setProgress(0);
                right_image_upload_IV.setVisibility(View.VISIBLE);
                right_image_delete_IV.setVisibility(View.GONE);
                break;

            case R.id.rfid_image_delete_IV:
                rfidImageUrl = "";
                Glide.with(this).load(R.drawable.empty_pet_image).into(rfid_image_CIV);
                rfid_image_progress_bar.setProgress(0);
                rfid_image_upload_IV.setVisibility(View.VISIBLE);
                rfid_image_delete_IV.setVisibility(View.GONE);
                break;

            case R.id.purchase_proof_image_delete_IV:
                purchaseProofUrl = "";
                purchase_proof_image_progress_bar.setProgress(0);
                purchase_proof_image_upload_IV.setVisibility(View.VISIBLE);
                purchase_proof_image_delete_IV.setVisibility(View.GONE);
                break;

            case R.id.pedigree_cert_image_delete_IV:
                pedigreeDocumentUrl = "";
                pedigree_cert_image_progress_bar.setProgress(0);
                pedigree_cert_image_upload_IV.setVisibility(View.VISIBLE);
                pedigree_cert_image_delete_IV.setVisibility(View.GONE);
                break;

            case R.id.vaccination_card_image_delete_IV:
                vaccinationCardUrl = "";
                vaccination_card_image_progress_bar.setProgress(0);
                vaccination_card_image_upload_IV.setVisibility(View.VISIBLE);
                vaccination_card_image_delete_IV.setVisibility(View.GONE);
                break;
        }
    }

    private void showPictureDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_layout);
        RelativeLayout select_camera = dialog.findViewById(R.id.select_camera);
        RelativeLayout select_gallery = dialog.findViewById(R.id.select_gallery);
        RelativeLayout cancel_dialog = dialog.findViewById(R.id.cancel_dialog);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        cancel_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        select_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                mediaUtils.openCamera();

            }
        });

        select_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                mediaUtils.openGallery();

            }
        });


        dialog.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==DOC_UPLOAD){
            Uri uri = data.getData();
            String fullPath = Commons.getPath(uri, this);
            File file = new File(fullPath);
            uploadImage(file);
        }else {
            mediaUtils.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void getPetDateofBirthDependsOnDays(String day) {
        ApiService<DateOfBirthResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().GetPetDateOfBirth(day), "getDateOfYear");

    }

    private void showDeclaration() {
        declaration_BSD = new BottomSheetDialog(this);
        declaration_BSD.requestWindowFeature(Window.FEATURE_NO_TITLE);
        declaration_BSD.setCancelable(true);
        declaration_BSD.setContentView(R.layout.declaration_dilaog_layout);

        ImageView cross_IV              = declaration_BSD.findViewById(R.id.cross_IV);
        RecyclerView declaration_RV     = declaration_BSD.findViewById(R.id.declaration_RV);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        declaration_RV.setLayoutManager(linearLayoutManager);
        diseasesListAdapter = new DiseasesListAdapter(this,diseasesListModels,this);
        declaration_RV.setAdapter(diseasesListAdapter);
        diseasesListAdapter.notifyDataSetChanged();
        declaration_BSD.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        cross_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                declaration_BSD.dismiss();
            }
        });


        declaration_BSD.show();
    }

    private void showPlansDialog() {
        plans_BSD = new BottomSheetDialog(this);
        plans_BSD.requestWindowFeature(Window.FEATURE_NO_TITLE);
        plans_BSD.setCancelable(true);
        plans_BSD.setContentView(R.layout.choose_plan_dilaog_layout);
        select_all_plans_CB                     = plans_BSD.findViewById(R.id.select_all_plans_CB);
        ImageView       cross_IV                = plans_BSD.findViewById(R.id.cross_IV);
        RecyclerView    choose_plan_RV          = plans_BSD.findViewById(R.id.choose_plan_RV);
        select_all_plans_CB.setOnClickListener(this);
        checkBoxSelectAllCheck();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        choose_plan_RV.setLayoutManager(linearLayoutManager);
        insurancePlansAdapter = new InsurancePlansAdapter(this,insurancePlanModels,this);
        choose_plan_RV.setAdapter(insurancePlansAdapter);
        insurancePlansAdapter.notifyDataSetChanged();
        plans_BSD.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        cross_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plans_BSD.dismiss();
            }
        });
        plans_BSD.show();
    }

    @Override
    public void onBackPressed() {
        if (countSteps==3){
            step_count_TV.setText("2 of 3");
            step_name_TV.setText("Pet Details");
            next_step_TV.setVisibility(View.VISIBLE);
            next_step_TV.setText("Next: Document Section");
            pet_parent_details_layout_frame.setVisibility(View.GONE);
            insurance_pet_details_layout_frame.setVisibility(View.VISIBLE);
            insurance_images_layout_frame.setVisibility(View.GONE);
            privious_BT.setVisibility(View.VISIBLE);
            circular_PB.setProgress(50);
            countSteps = 2;
        }else if (countSteps==2){
            step_count_TV.setText("1 of 3");
            step_name_TV.setText("Pet Parent Details");
            next_step_TV.setVisibility(View.VISIBLE);
            next_step_TV.setText("Next: Pet Details");
            pet_parent_details_layout_frame.setVisibility(View.VISIBLE);
            insurance_pet_details_layout_frame.setVisibility(View.GONE);
            insurance_images_layout_frame.setVisibility(View.GONE);
            privious_BT.setVisibility(View.GONE);
            circular_PB.setProgress(10);
            countSteps = 1;

        }else {
            finish();
        }
    }

    @Override
    public void onResponse(Response response, String key) {
        switch (key){
            case  "GetAllDetailOfPet":
                try {
                    methods.customProgressDismiss();
                    getAllDetailAfterLoginResponse = (GetAllDetailAfterLoginResponse) response.body();
                    int responseCode = Integer.parseInt(getAllDetailAfterLoginResponse.getResponse().getResponseCode());
                    Log.e("GetAllDetailOfPet",methods.getRequestJson(getAllDetailAfterLoginResponse));
                    if (responseCode==109) {
                        String stepperCount = getAllDetailAfterLoginResponse.getData().getStepper();
                        if (stepperCount==null){
//                            Log.e("Step_COUNT",stepperCount);
                            //parent Data
                            setIstStepData();
                            //pet data
                            set2ndStepData();
                            // document Section
                            set3rdStepData();
                        }else if (stepperCount.equals("2")){
                            Log.e("Step_COUNT",stepperCount);

                            //parent Data
                            setIstStepData();
                            //pet data
                            set2ndStepData();
                            // document Section
                            set3rdStepData();
                        }else if (stepperCount.equals("3")){
                            Log.e("Step_COUNT",stepperCount);

                            //parent Data
                            setIstStepData();
                            //pet data
                            set2ndStepData();
                            // document Section
                            set3rdStepData();
                        }else if (stepperCount.equals("4")){
                            Log.e("Step_COUNT",stepperCount);
                            setResult(RESULT_OK);
                            finish();
                        }
                    }else {
                        finish();
                        Toast.makeText(this, "Please try again !", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

                break;
            case "PunchingPolicyRequest":
                try {
                    Log.e("PunchingPolicyRequest","Response=>"+response.body().toString());
                    next_BT.setEnabled(true);
                    privious_BT.setEnabled(true);
                    JsonObject jsonObject = (JsonObject) response.body();
                    JsonObject punchingPolicyResponse = jsonObject.getAsJsonObject("response");
                    int responseCode = Integer.parseInt(String.valueOf(punchingPolicyResponse.get("responseCode")));
                    if (responseCode == 109) {
                        Log.e("CHECK_ERROR","HERE");
                        loading_PB.setVisibility(View.GONE);
                        circular_PB.setVisibility(View.VISIBLE);
                        tokenForInsurance = String.valueOf(punchingPolicyResponse.get("token"));
                        Log.e("PunchingPolicyTOKEN",tokenForInsurance);
                        step_count_TV.setText("2 of 3");
                        step_name_TV.setText("Pet Details");
                        next_step_TV.setVisibility(View.VISIBLE);
                        next_step_TV.setText("Next: Document Section");
                        pet_parent_details_layout_frame.setVisibility(View.GONE);
                        insurance_pet_details_layout_frame.setVisibility(View.VISIBLE);
                        insurance_images_layout_frame.setVisibility(View.GONE);
                        privious_BT.setVisibility(View.VISIBLE);
                        circular_PB.setProgress(50);
                        countSteps = 2;
                    }else {
                        loading_PB.setVisibility(View.GONE);
                        circular_PB.setVisibility(View.VISIBLE);
                        Toast.makeText(this, "Please try again !", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

                break;

            case  "PunchingPolicyPetRequest":
                try {
                    Log.e("PunchingPolicyPetRequest","Response=>"+response.body().toString());
                    next_BT.setEnabled(true);
                    privious_BT.setEnabled(true);
                    JsonObject jsonObject = (JsonObject) response.body();
                    JsonObject punchingPolicyResponse = jsonObject.getAsJsonObject("response");
                    int responseCode = Integer.parseInt(String.valueOf(punchingPolicyResponse.get("responseCode")));
                    JsonObject punchingPolicyData = jsonObject.getAsJsonObject("data");
                    if (responseCode == 109) {
                        petId = String.valueOf(punchingPolicyData.get("petId"));
                        loading_PB.setVisibility(View.GONE);
                        circular_PB.setVisibility(View.VISIBLE);
                        step_count_TV.setText("3 of 3");
                        step_name_TV.setText("Document Section");
                        next_step_TV.setVisibility(View.GONE);
                        if (is_pedigree_lineage){
                            pedigree_cert_image_CL.setVisibility(View.VISIBLE);
                        }else {
                            pedigree_cert_image_CL.setVisibility(View.GONE);
                        }
                        pet_parent_details_layout_frame.setVisibility(View.GONE);
                        insurance_pet_details_layout_frame.setVisibility(View.GONE);
                        insurance_images_layout_frame.setVisibility(View.VISIBLE);
                        privious_BT.setVisibility(View.VISIBLE);
                        circular_PB.setProgress(85);
                        countSteps = 3;
                    }else {
                        loading_PB.setVisibility(View.GONE);
                        circular_PB.setVisibility(View.VISIBLE);
                        Toast.makeText(this, "Please try again !", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

                break;

            case "PunchingPolicyPetDocuments":
                try {
                    next_BT.setEnabled(true);
                    privious_BT.setEnabled(true);
                    Log.e("PunchingPolicyPetRequest","Response=>"+response.body().toString());
                    JsonObject jsonObject = (JsonObject) response.body();
                    JsonObject punchingPolicyResponse = jsonObject.getAsJsonObject("response");
                    int responseCode = Integer.parseInt(String.valueOf(punchingPolicyResponse.get("responseCode")));
                    if (responseCode == 109) {
                        loading_PB.setVisibility(View.VISIBLE);
                        circular_PB.setVisibility(View.GONE);
                        setResult(RESULT_OK);
                        finish();
                    }else {
                        loading_PB.setVisibility(View.VISIBLE);
                        circular_PB.setVisibility(View.GONE);
                        Toast.makeText(this, "Please try again !", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

                break;

            case "GetState":
                try {
                    Log.d("getState", response.body().toString());
                    StateResponse stateResponse = (StateResponse) response.body();
                    int responseCode = Integer.parseInt(stateResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        state = new ArrayList<>();
                        state.add("Select State");
                        stateHasmap.put("Select State", "0.0");
                        for (int i = 0; i < stateResponse.getData().size(); i++) {
                            Log.d("kakakka", "" + stateResponse.getData().get(i).getStateName());
                            state.add(stateResponse.getData().get(i).getStateName());
                            stateHasmap.put(stateResponse.getData().get(i).getStateName(), stateResponse.getData().get(i).getId());
                        }
                        setStateSpinner();

                    } else if (responseCode == 614) {
                        Toast.makeText(this, stateResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "GetCity":
                try {
                    Log.d("GetCity", response.body().toString());
                    CityResponse cityResponse = (CityResponse) response.body();
                    int responseCode = Integer.parseInt(cityResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        city = new ArrayList<>();
                        city.add("Select City");
                        Log.d("lalal", "" + cityResponse.getData().size());
                        for (int i = 0; i < cityResponse.getData().size(); i++) {
                            Log.d("kakakkajj", "" + cityResponse.getData().get(i).getCity1());
                            city.add(cityResponse.getData().get(i).getCity1());
                            cityHasmap.put(cityResponse.getData().get(i).getCity1(), cityResponse.getData().get(i).getId());
                        }
                        setCitySpinner();

                    } else if (responseCode == 614) {
                        Toast.makeText(this, cityResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "InsuranceMasters":
                try {
                    Log.d("InsuranceMasters", methods.getRequestJson(response.body()));
                    insuranceMastersResponse = (InsuranceMastersResponse) response.body();
                    int responseCode = Integer.parseInt(insuranceMastersResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        insurancePlanModels = insuranceMastersResponse.getData().getInsurancePlanModel();
                        diseasesListModels  = insuranceMastersResponse.getData().getDiseasesListModel();
                    } else if (responseCode == 614) {
                        Toast.makeText(this, insuranceMastersResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "GetPetAgeUnit":
                try {
                    Log.d("GetPetTypes", response.body().toString());
                    PetAgeUnitResponseData petAgeUnitResponseData = (PetAgeUnitResponseData) response.body();
                    int responseCode = Integer.parseInt(petAgeUnitResponseData.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        petAgeType = new ArrayList<>();
                        Log.d("lalal", "" + petAgeUnitResponseData.getData().size());
                        for (int i = 0; i < petAgeUnitResponseData.getData().size(); i++) {
                            Log.d("petttt", "" + petAgeUnitResponseData.getData().get(i).getAge());
                            petAgeType.add(petAgeUnitResponseData.getData().get(i).getAgeUnit());
                            petAgeUnitHash.put(petAgeUnitResponseData.getData().get(i).getAgeUnit(), petAgeUnitResponseData.getData().get(i).getAge());
                        }
                        setPetAgeType();

                    } else if (responseCode == 614) {
                        Toast.makeText(this, petAgeUnitResponseData.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "getDateOfYear":
                try {
                    DateOfBirthResponse dateOfBirthResponse = (DateOfBirthResponse) response.body();
                    Log.d("getDateOfYear", dateOfBirthResponse.toString());
                    int responseCode = Integer.parseInt(dateOfBirthResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        pet_layout_calenderTextView_dialog.setText(dateOfBirthResponse.getData());
                        getPetAgeString(dateOfBirthResponse.getData());
                    } else if (responseCode == 614) {
                        Toast.makeText(this, dateOfBirthResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "GetPetAgeString":
                try {
                    Log.d("GetPetAgeString", response.body().toString());
                    GetPetAgeresponseData getPetAgeresponseData = (GetPetAgeresponseData) response.body();
                    int responseCode = Integer.parseInt(getPetAgeresponseData.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        ageViewTv.setText(getPetAgeresponseData.getData().getPetAge());
                    } else if (responseCode == 614) {
                        Toast.makeText(this, getPetAgeresponseData.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;


            case "GetPetTypes":
                try {
                    Log.d("GetPetTypes", response.body().toString());
                    PetTypeResponse petTypeResponse = (PetTypeResponse) response.body();
                    int responseCode = Integer.parseInt(petTypeResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        petType = new ArrayList<>();
                        Log.d("lalal", "" + petTypeResponse.getData().size());
                        for (int i = 0; i < petTypeResponse.getData().size(); i++) {
                            if (petTypeResponse.getData().get(i).getPetType1().equals("Dog")){
                                Log.d("petttt", "" + petTypeResponse.getData().get(i).getPetType1());
                                petType.add(petTypeResponse.getData().get(i).getPetType1());
                                petTypeHashMap.put(petTypeResponse.getData().get(i).getPetType1(), petTypeResponse.getData().get(i).getId());
                            }

                        }
                        setPetTypeSpinner();

                    } else if (responseCode == 614) {
                        Toast.makeText(this, petTypeResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            case "GetPetBreed":
                try {
                    Log.d("GetPetBreed", response.body().toString());
                    BreedCatRespose breedCatRespose = (BreedCatRespose) response.body();
                    int responseCode = Integer.parseInt(breedCatRespose.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        petBreed = new ArrayList<>();
                        petBreed.add("Pet Breed");
                        petBreedHashMap.put("Pet Breed", "0");
                        Log.d("lalal", "" + breedCatRespose.getData().size());
                        for (int i = 0; i < breedCatRespose.getData().size(); i++) {
                            Log.d("petttt", "" + breedCatRespose.getData().get(i).getBreed());
                            petBreed.add(breedCatRespose.getData().get(i).getBreed());
                            petBreedHashMap.put(breedCatRespose.getData().get(i).getBreed(), breedCatRespose.getData().get(i).getId());
                        }
                        setPetBreeSpinner();
                    } else if (responseCode == 614) {
                        Toast.makeText(this, breedCatRespose.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "GetPetColor":
                try {
                    Log.d("GetPetColor", response.body().toString());
                    PetColorValueResponse petColorValueResponse = (PetColorValueResponse) response.body();
                    int responseCode = Integer.parseInt(petColorValueResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        petColor = new ArrayList<>();
                        petColor.add("Pet Color");
                        Log.d("lalal", "" + petColorValueResponse.getData().size());
                        for (int i = 0; i < petColorValueResponse.getData().size(); i++) {
                            Log.d("petttt", "" + petColorValueResponse.getData().get(i).getColor());
                            petColor.add(petColorValueResponse.getData().get(i).getColor());
                            petColorHashMap.put(petColorValueResponse.getData().get(i).getColor(), petColorValueResponse.getData().get(i).getId());
                        }
                        setPetColorSpinner();
                    } else if (responseCode == 614) {
                        Toast.makeText(this, petColorValueResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "FrontImageUpload":
                try {
//                    Log.e("UploadDocument", response.body().toString());
                    ImageResponse imageResponse = (ImageResponse) response.body();
                    int responseCode = Integer.parseInt(imageResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        front_image_delete_IV.setVisibility(View.VISIBLE);
                        front_image_upload_IV.setVisibility(View.GONE);
                            front_image_progress_bar.setProgress(100);
                            Glide.with(this)
                                    .load(imageResponse.getData().getDocumentUrl())
                                    .placeholder(R.drawable.empty_pet_image)
                                    .into(front_image_CIV);
                        frontImageUrl = (imageResponse.getData().getDocumentUrl());
                    } else if (responseCode == 614) {
                        Toast.makeText(this, imageResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            case "BackImageUpload":
                try {
                    Log.e("UploadDocument", response.body().toString());
                    ImageResponse imageResponse = (ImageResponse) response.body();
                    int responseCode = Integer.parseInt(imageResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        back_image_delete_IV.setVisibility(View.VISIBLE);
                        back_image_upload_IV.setVisibility(View.GONE);
                            back_image_progress_bar.setProgress(100);
                            Glide.with(this)
                                    .load(imageResponse.getData().getDocumentUrl())
                                    .placeholder(R.drawable.empty_pet_image)
                                    .into(back_image_CIV);
                        backImageUrl = (imageResponse.getData().getDocumentUrl());
                    } else if (responseCode == 614) {
                        Toast.makeText(this, imageResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;


            case "LeftImageUpload":
                try {
                    Log.e("UploadDocument", response.body().toString());
                    ImageResponse imageResponse = (ImageResponse) response.body();
                    int responseCode = Integer.parseInt(imageResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        left_image_delete_IV.setVisibility(View.VISIBLE);
                        left_image_upload_IV.setVisibility(View.GONE);
                            left_image_progress_bar.setProgress(100);
                            Glide.with(this)
                                    .load(imageResponse.getData().getDocumentUrl())
                                    .placeholder(R.drawable.empty_pet_image)
                                    .into(left_image_CIV);
                        leftImageUrl = (imageResponse.getData().getDocumentUrl());
                    } else if (responseCode == 614) {
                        Toast.makeText(this, imageResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            case "RightImageUpload":
                try {
                    Log.e("UploadDocument", response.body().toString());
                    ImageResponse imageResponse = (ImageResponse) response.body();
                    int responseCode = Integer.parseInt(imageResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        right_image_delete_IV.setVisibility(View.VISIBLE);
                        right_image_upload_IV.setVisibility(View.GONE);
                        right_image_progress_bar.setProgress(100);
                        Glide.with(this)
                                .load(imageResponse.getData().getDocumentUrl())
                                .placeholder(R.drawable.empty_pet_image)
                                .into(right_image_CIV);
                        rightImageUrl = (imageResponse.getData().getDocumentUrl());
                    } else if (responseCode == 614) {
                        Toast.makeText(this, imageResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            case "TopImageUpload":
                try {
                    Log.e("UploadDocument", response.body().toString());
                    ImageResponse imageResponse = (ImageResponse) response.body();
                    int responseCode = Integer.parseInt(imageResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        top_image_delete_IV.setVisibility(View.VISIBLE);
                        top_image_upload_IV.setVisibility(View.GONE);
                        top_image_progress_bar.setProgress(100);
                        Glide.with(this)
                                .load(imageResponse.getData().getDocumentUrl())
                                .placeholder(R.drawable.empty_pet_image)
                                .into(top_image_CIV);
                        topImageUrl = (imageResponse.getData().getDocumentUrl());
                    } else if (responseCode == 614) {
                        Toast.makeText(this, imageResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            case "RfidImageUpload":
                try {
                    Log.e("UploadDocument", response.body().toString());
                    ImageResponse imageResponse = (ImageResponse) response.body();
                    int responseCode = Integer.parseInt(imageResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        rfid_image_delete_IV.setVisibility(View.VISIBLE);
                        rfid_image_upload_IV.setVisibility(View.GONE);
                        rfid_image_progress_bar.setProgress(100);
                        rfidImageUrl = (imageResponse.getData().getDocumentUrl());

                        Glide.with(this)
                                .load(imageResponse.getData().getDocumentUrl())
                                .placeholder(R.drawable.empty_pet_image)
                                .into(rfid_image_CIV);
                    } else if (responseCode == 614) {
                        Toast.makeText(this, imageResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            case "PurchaseProofUpload":
                try {
                    Log.e("UploadDocument", response.body().toString());
                    ImageResponse imageResponse = (ImageResponse) response.body();
                    int responseCode = Integer.parseInt(imageResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        purchase_proof_image_delete_IV.setVisibility(View.VISIBLE);
                        purchase_proof_image_upload_IV.setVisibility(View.GONE);
                        purchase_proof_image_progress_bar.setProgress(100);
                        purchaseProofUrl = (imageResponse.getData().getDocumentUrl());
                        Toast.makeText(this, "Purchase proof uploaded", Toast.LENGTH_SHORT).show();
                    } else if (responseCode == 614) {
                        Toast.makeText(this, imageResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            case "VaccinationCardUpload":
                try {
                    Log.e("UploadDocument", response.body().toString());
                    ImageResponse imageResponse = (ImageResponse) response.body();
                    int responseCode = Integer.parseInt(imageResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        vaccination_card_image_delete_IV.setVisibility(View.VISIBLE);
                        vaccination_card_image_upload_IV.setVisibility(View.GONE);
                        vaccination_card_image_progress_bar.setProgress(100);
                        Toast.makeText(this, "Vaccination card uploaded", Toast.LENGTH_SHORT).show();
                        vaccinationCardUrl = (imageResponse.getData().getDocumentUrl());
                    } else if (responseCode == 614) {
                        Toast.makeText(this, imageResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            case "PedigreeUpload":
                try {
                    Log.e("UploadDocument", response.body().toString());
                    ImageResponse imageResponse = (ImageResponse) response.body();
                    int responseCode = Integer.parseInt(imageResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        pedigree_cert_image_progress_bar.setProgress(100);
                        pedigree_cert_image_delete_IV.setVisibility(View.VISIBLE);
                        pedigree_cert_image_upload_IV.setVisibility(View.GONE);
                        pedigreeDocumentUrl = (imageResponse.getData().getDocumentUrl());
                        Toast.makeText(this, "Pedigree card uploaded", Toast.LENGTH_SHORT).show();
                    } else if (responseCode == 614) {
                        Toast.makeText(this, imageResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }

    }

    @Override
    public void onError(Throwable t, String key) {

        Log.e("error",t.getLocalizedMessage());

    }

    private void set3rdStepData() {
        frontImageUrl       = getAllDetailAfterLoginResponse.getData().getPetImageUrl();
        backImageUrl        = getAllDetailAfterLoginResponse.getData().getPetImageUrl2();
        leftImageUrl        = getAllDetailAfterLoginResponse.getData().getPetImageUrl3();
        rightImageUrl       = getAllDetailAfterLoginResponse.getData().getPetImageUrl4();
        topImageUrl         = getAllDetailAfterLoginResponse.getData().getPetImageUrl5();
        rfidImageUrl        = getAllDetailAfterLoginResponse.getData().getPetImageUrl6();
        purchaseProofUrl    = getAllDetailAfterLoginResponse.getData().getPurchaseProofUrl();
        pedigreeDocumentUrl = getAllDetailAfterLoginResponse.getData().getPedigreeCertificateUrl();
        vaccinationCardUrl  = getAllDetailAfterLoginResponse.getData().getVaccinationUrl();

        if (frontImageUrl!=null){
            Glide.with(this)
                    .load(frontImageUrl)
                    .placeholder(R.drawable.empty_pet_image)
                    .into(front_image_CIV);

            front_image_delete_IV.setVisibility(View.VISIBLE);
            front_image_upload_IV.setVisibility(View.GONE);
        }if (backImageUrl!=null){
            Glide.with(this)
                    .load(backImageUrl)
                    .placeholder(R.drawable.empty_pet_image)
                    .into(back_image_CIV);
            back_image_delete_IV.setVisibility(View.VISIBLE);
            back_image_upload_IV.setVisibility(View.GONE);
        }if (leftImageUrl!=null){
            Glide.with(this)
                    .load(leftImageUrl)
                    .placeholder(R.drawable.empty_pet_image)
                    .into(left_image_CIV);
            left_image_delete_IV.setVisibility(View.VISIBLE);
            left_image_upload_IV.setVisibility(View.GONE);
        }if (rightImageUrl!=null){
            Glide.with(this)
                    .load(rightImageUrl)
                    .placeholder(R.drawable.empty_pet_image)
                    .into(right_image_CIV);
            right_image_delete_IV.setVisibility(View.VISIBLE);
            right_image_upload_IV.setVisibility(View.GONE);
        }if (topImageUrl!=null){
            Glide.with(this)
                    .load(topImageUrl)
                    .placeholder(R.drawable.empty_pet_image)
                    .into(top_image_CIV);
            top_image_delete_IV.setVisibility(View.VISIBLE);
            top_image_upload_IV.setVisibility(View.GONE);
        }if (rfidImageUrl!=null){
            Glide.with(this)
                    .load(rfidImageUrl)
                    .placeholder(R.drawable.empty_pet_image)
                    .into(rfid_image_CIV);
            rfid_image_delete_IV.setVisibility(View.VISIBLE);
            rfid_image_upload_IV.setVisibility(View.GONE);
        }if (purchaseProofUrl!=null){
            purchase_proof_image_delete_IV.setVisibility(View.VISIBLE);
            purchase_proof_image_upload_IV.setVisibility(View.GONE);
        }if (pedigreeDocumentUrl!=null){
            pedigree_cert_image_delete_IV.setVisibility(View.VISIBLE);
            pedigree_cert_image_upload_IV.setVisibility(View.GONE);
        }if (vaccinationCardUrl!=null){
            vaccination_card_image_delete_IV.setVisibility(View.VISIBLE);
            vaccination_card_image_upload_IV.setVisibility(View.GONE);
        }
    }

    private void set2ndStepData() {
        strSpnerItemPetNm = "Dog";

        strSpnrSexId = String.valueOf(getAllDetailAfterLoginResponse.getData().getPetSexId());
        if (strSpnrSexId.equals("1.0")){
            maleRB.setChecked(true);
        }else {
            femaleRB.setChecked(true);
        }
        pet_name_ET.setText(getAllDetailAfterLoginResponse.getData().getPetName());
        strSpnrBreed = getAllDetailAfterLoginResponse.getData().getPetBreedName();
        strSpnrColor= getAllDetailAfterLoginResponse.getData().getPetColorName();
        Log.d("BREEDDD",strSpnrBreed+strSpnrColor);

        if (getAllDetailAfterLoginResponse.getData().getMicrochipNumber()!=null){
            do_you_have_microchip_CB.setChecked(true);
            pet_microchip_no_ET.setText(getAllDetailAfterLoginResponse.getData().getMicrochipNumber());
        }
        if (getAllDetailAfterLoginResponse.getData().getVaccinated()){
            is_vaccinated_CB.setChecked(true);
        }

        if (getAllDetailAfterLoginResponse.getData().getRegistrationPet()){
            kic_CB.setChecked(true);
        }

        if (getAllDetailAfterLoginResponse.getData().getPedigreeLineage()){
            pedigree_lineage_CB.setChecked(true);
        }
        if (getAllDetailAfterLoginResponse.getData().getCastrated()){
            castrated_neutered_CB.setChecked(true);
        }

        pet_features_ET.setText(getAllDetailAfterLoginResponse.getData().getFeatures());

        String petDOB = (getAllDetailAfterLoginResponse.getData().getPetDateofBirth());
        getPetAgeString(petDOB);
        pet_layout_calenderTextView_dialog.setText(petDOB);



    }

    private void setIstStepData() {
        parent_first_name_ET.setText(getAllDetailAfterLoginResponse.getData().getFirstName());
        parent_last_name_ET.setText(getAllDetailAfterLoginResponse.getData().getLastName());
        parent_email_ET.setText(getAllDetailAfterLoginResponse.getData().getEmail());
        parent_phone_ET.setText(getAllDetailAfterLoginResponse.getData().getPhoneNumber());
        if (getAllDetailAfterLoginResponse.getData().getOwnerDob()!=null){
            calenderTextView_dialog.setText(Config.changeDateFormat(getAllDetailAfterLoginResponse.getData().getOwnerDob()));
        }
        if (getAllDetailAfterLoginResponse.getData().getAddress()!=null){
            parent_address_ET.setText(getAllDetailAfterLoginResponse.getData().getAddress());
        }
        if (getAllDetailAfterLoginResponse.getData().getPinCode()!=null){
            parent_pinCode_ET.setText(getAllDetailAfterLoginResponse.getData().getPinCode());
        }
        if (getAllDetailAfterLoginResponse.getData().getReferralCode()!=null){
            parent_referralCode_ET.setText(getAllDetailAfterLoginResponse.getData().getReferralCode());
        }

        strStateSpnr = getAllDetailAfterLoginResponse.getData().getStateName();
        strCitySpnr  = getAllDetailAfterLoginResponse.getData().getCityName();
        setStateSpinner();
        setCitySpinner();
    }

    private void setPetColorSpinner() {
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, petColor);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        add_pet_color.setAdapter(aa);
        if (!strSpnrColor.equals("")) {
            int spinnerPosition = aa.getPosition(strSpnrColor);
            add_pet_color.setSelection(spinnerPosition);
        }
        add_pet_color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                strSpnrColor = item;
                Log.d("spnerType", "" + strSpnrColor);
                strSpnrColorId = petColorHashMap.get(strSpnrColor);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setPetBreeSpinner() {
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, petBreed);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        add_pet_breed.setAdapter(aa);
        if (!strSpnrBreed.equals("")) {
            int spinnerPosition = aa.getPosition(strSpnrBreed);
            add_pet_breed.setSelection(spinnerPosition);
        }
        add_pet_breed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                strSpnrBreed = item;
                Log.d("spnerType", "" + strSpnrBreed);
                strSpnrBreedId = petBreedHashMap.get(strSpnrBreed);

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setPetTypeSpinner() {
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, petType);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        add_pet_type.setAdapter(aa);
        if (strSpnerItemPetNm!=null) {
            int spinnerPosition = aa.getPosition(strSpnerItemPetNm);
            add_pet_type.setSelection(spinnerPosition);
        }
        add_pet_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                strSpnerItemPetNm = item;
                Log.d("spnerType", "" + strSpnerItemPetNm);
                getStrSpnerItemPetNmId = petTypeHashMap.get(strSpnerItemPetNm);
                if (!getStrSpnerItemPetNmId.equals("0")) {
                    getPetBreed();
                    getPetColor();
                }

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getPetBreed() {
        BreedRequest breedRequest = new BreedRequest();
        breedRequest.setGetAll("false");
        if (!getStrSpnerItemPetNmId.equals("0"))
            breedRequest.setPetCategoryId(getStrSpnerItemPetNmId);
        else
            breedRequest.setPetCategoryId("1");
        BreedParams breedParams = new BreedParams();
        breedParams.setData(breedRequest);

        ApiService<BreedCatRespose> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getGetPetBreedApi(breedParams), "GetPetBreed");
        Log.d("Diolog_Breed", "===>" + breedParams);
    }

    private void getPetColor() {
        BreedRequest breedRequest = new BreedRequest();
        breedRequest.setGetAll("false");
        if (!getStrSpnerItemPetNmId.equals("0"))
            breedRequest.setPetCategoryId(getStrSpnerItemPetNmId);
        else
            breedRequest.setPetCategoryId("1");
        BreedParams breedParams = new BreedParams();
        breedParams.setData(breedRequest);

        ApiService<PetColorValueResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getGetPetColorApi(breedParams), "GetPetColor");
    }
    
    private void getPetAgeString(String DOB) {
        GetPetAgeParameter getPetAgeParameter = new GetPetAgeParameter();
        getPetAgeParameter.setDateOfBirth(DOB);
        GetPetAgeRequestData getPetAgeRequestData = new GetPetAgeRequestData();
        getPetAgeRequestData.setData(getPetAgeParameter);
        ApiService<GetPetAgeresponseData> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getPetAgeString(getPetAgeRequestData), "GetPetAgeString");
        Log.e("DAILOG", "getPetAgeString==>" + getPetAgeRequestData);

    }

    private void setPetAgeType() {
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, petAgeType);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        age_wise.setAdapter(aa);
        age_wise.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                strAgeCount = item;
                Log.d("spnerType", "PetAge" + item);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setCitySpinner() {
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, city);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner

        parent_city_spinner.setAdapter(aa);
        if (!parent_city_spinner.equals("")) {
            int spinnerPosition = aa.getPosition(strCitySpnr);
            parent_city_spinner.setSelection(spinnerPosition);
        }
        parent_city_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                strCitySpnr = item;
                strStringCityId = cityHasmap.get(strCitySpnr);
                Log.e("stae",strCitySpnr);

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setStateSpinner() {
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, state);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        parent_state_spinner.setAdapter(aa);
        if (!parent_state_spinner.equals("")) {
            int spinnerPosition = aa.getPosition(strStateSpnr);
            parent_state_spinner.setSelection(spinnerPosition);
        }
        parent_state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                strStateSpnr = item;
                strStateId = stateHasmap.get(strStateSpnr);
                Log.e("stae",strStateSpnr);

                if (strStateId.equals(null)) {

                } else {
                    String stateId = strStateId.substring(0, strStateId.length() - 2);
                    String url = "common/GetCity/" + stateId;
                    Log.e("URL", url);
                    getCity(url);
                }

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getCity(String stateId) {
        ApiService<CityResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getCityApi(stateId), "GetCity");
    }



    @Override
    public void onInsurancePlanClick(int position, boolean checkBox) {
            Log.e("insurancePlanModels",checkBox+"");
            insurancePlanModels.get(position).setStatus(checkBox);
            Log.e("insurancePlanModels",methods.getRequestJson(insurancePlanModels));
            checkBoxSelectAllCheck();
    }

    private void checkBoxSelectAllCheck() {
        selectedInsurancePlans = "";
        insurancePlanModels.get(0).setStatus(true);
        int count=0;
        for (int i = 0;i<insurancePlanModels.size();i++){
            if (insurancePlanModels.get(i).isStatus()){
                selectedInsurancePlans = selectedInsurancePlans+","+insurancePlanModels.get(i).getId();
                count++;
            }
        }
        if (insurancePlanModels.size()==count){
            select_all_plans_CB.setChecked(true);
        }else {
            select_all_plans_CB.setChecked(false);
        }

        pet_choosePlan.setText("Selected plans ("+count+")");

    }

    @Override
    public void onDiseasesListClickListener(int position,boolean status, int id) {
        if (id==100.0){
            for (int i = 0;i<diseasesListModels.size();i++){
                diseasesListModels.get(i).setStatus(false);
                if (diseasesListModels.get(i).getId()==100.0){
                    diseasesListModels.get(i).setStatus(true);
                }
            }

            diseasesListAdapter.notifyDataSetChanged();

        }
        else {
            if (diseasesListModels.get(position).getId()!=100.0){
                for (int i = 0;i<diseasesListModels.size();i++){
                    if (diseasesListModels.get(i).getId()==100.0){
                        diseasesListModels.get(i).setStatus(false);
                    }
                }
                diseasesListAdapter.notifyDataSetChanged();
            }
        }
        diseasesListModelsCheckSelectedList();
    }

    private void diseasesListModelsCheckSelectedList() {
        selectedDiseasesId = "";
        int count=0;
        for (int i = 0;i<diseasesListModels.size();i++){
            if (diseasesListModels.get(i).isStatus()){
                selectedDiseasesId = selectedDiseasesId+","+diseasesListModels.get(i).getId();
                count++;
            }
        }
        pet_declaration.setText("Selected declaration ("+count+")");
    }

    @Override
    public void imgdata(String imgPath) {
        File imgFile = new File(imgPath);
        uploadImage(imgFile);
    }

    private void uploadImage(File absolutePath) {
        MultipartBody.Part userDpFilePart = null;
        if (absolutePath != null) {
            RequestBody userDpFile = RequestBody.create(MediaType.parse("multipart/form-data"), absolutePath);
            userDpFilePart = MultipartBody.Part.createFormData("file", absolutePath.getName(), userDpFile);
        }

        if (imageTypeName.equals("front_image")){
            front_status = 0;
            Handler front_handler = new Handler();
            front_image_progress_bar.setVisibility(View.VISIBLE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (front_status < 80) {

                        front_status += 1;

                        try {
                            Thread.sleep(15);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        front_handler.post(new Runnable() {
                            @Override
                            public void run() {
                                front_image_progress_bar.setProgress(front_status);
                            }
                        });
                    }
                }
            }).start();
            ApiService<ImageResponse> service = new ApiService<>();
            service.get(this, ApiClient.getApiInterface().uploadImages(Config.token, userDpFilePart), "FrontImageUpload");
            Log.e("DATALOG", "check1=> " + methods.getRequestJson(userDpFilePart));
        }else if (imageTypeName.equals("back_image")){
            back_status =0;
            Handler back_handler = new Handler();
            back_image_progress_bar.setVisibility(View.VISIBLE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (back_status < 80) {

                        back_status += 1;

                        try {
                            Thread.sleep(15);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        back_handler.post(new Runnable() {
                            @Override
                            public void run() {
                                back_image_progress_bar.setProgress(back_status);
                            }
                        });
                    }
                }
            }).start();
            ApiService<ImageResponse> service = new ApiService<>();
            service.get(this, ApiClient.getApiInterface().uploadImages(Config.token, userDpFilePart), "BackImageUpload");
            Log.e("DATALOG", "check1=> " + methods.getRequestJson(userDpFilePart));
        }else if (imageTypeName.equals("top_image")){
            top_status =0;
            Handler top_handler = new Handler();
            top_image_progress_bar.setVisibility(View.VISIBLE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (top_status < 80) {

                        top_status += 1;

                        try {
                            Thread.sleep(15);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        top_handler.post(new Runnable() {
                            @Override
                            public void run() {
                                top_image_progress_bar.setProgress(top_status);
                            }
                        });
                    }
                }
            }).start();
            ApiService<ImageResponse> service = new ApiService<>();
            service.get(this, ApiClient.getApiInterface().uploadImages(Config.token, userDpFilePart), "TopImageUpload");
            Log.e("DATALOG", "check1=> " + methods.getRequestJson(userDpFilePart));
        }else if (imageTypeName.equals("left_image")){
            left_status =0;
            Handler left_handler = new Handler();
            left_image_progress_bar.setVisibility(View.VISIBLE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (left_status < 80) {

                        left_status += 1;

                        try {
                            Thread.sleep(15);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        left_handler.post(new Runnable() {
                            @Override
                            public void run() {
                                left_image_progress_bar.setProgress(left_status);
                            }
                        });
                    }
                }
            }).start();
            ApiService<ImageResponse> service = new ApiService<>();
            service.get(this, ApiClient.getApiInterface().uploadImages(Config.token, userDpFilePart), "LeftImageUpload");
            Log.e("DATALOG", "check1=> " + methods.getRequestJson(userDpFilePart));
        }else if (imageTypeName.equals("right_image")){
            right_status =0;
            Handler right_handler = new Handler();
            right_image_progress_bar.setVisibility(View.VISIBLE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (right_status < 80) {

                        right_status += 1;

                        try {
                            Thread.sleep(15);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        right_handler.post(new Runnable() {
                            @Override
                            public void run() {
                                right_image_progress_bar.setProgress(right_status);
                            }
                        });
                    }
                }
            }).start();
            ApiService<ImageResponse> service = new ApiService<>();
            service.get(this, ApiClient.getApiInterface().uploadImages(Config.token, userDpFilePart), "RightImageUpload");
            Log.e("DATALOG", "check1=> " + methods.getRequestJson(userDpFilePart));
        }else if (imageTypeName.equals("rfid_image")){
            rfid_status = 0;
            Handler rfid_handler = new Handler();
            rfid_image_progress_bar.setVisibility(View.VISIBLE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (rfid_status < 80) {

                        rfid_status += 1;

                        try {
                            Thread.sleep(15);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        rfid_handler.post(new Runnable() {
                            @Override
                            public void run() {
                                rfid_image_progress_bar.setProgress(rfid_status);
                            }
                        });
                    }
                }
            }).start();
            ApiService<ImageResponse> service = new ApiService<>();
            service.get(this, ApiClient.getApiInterface().uploadImages(Config.token, userDpFilePart), "RfidImageUpload");
            Log.e("DATALOG", "check1=> " + methods.getRequestJson(userDpFilePart));
        }else if (imageTypeName.equals("purchase_proof_image")){
            purchase_proof_status = 0;
            Handler purchase_proof_handler = new Handler();
            purchase_proof_image_progress_bar.setVisibility(View.VISIBLE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (purchase_proof_status < 80) {

                        purchase_proof_status += 1;

                        try {
                            Thread.sleep(15);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        purchase_proof_handler.post(new Runnable() {
                            @Override
                            public void run() {
                                purchase_proof_image_progress_bar.setProgress(purchase_proof_status);
                            }
                        });
                    }
                }
            }).start();
            ApiService<ImageResponse> service = new ApiService<>();
            service.get(this, ApiClient.getApiInterface().uploadImages(Config.token, userDpFilePart), "PurchaseProofUpload");
            Log.e("DATALOG", "check1=> " + methods.getRequestJson(userDpFilePart));
        }else if (imageTypeName.equals("pedigree_proof_image")){
            pedigree_status =0;
            Handler purchase_proof_handler = new Handler();
            pedigree_cert_image_progress_bar.setVisibility(View.VISIBLE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (pedigree_status < 80) {

                        pedigree_status += 1;

                        try {
                            Thread.sleep(15);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        purchase_proof_handler.post(new Runnable() {
                            @Override
                            public void run() {
                                pedigree_cert_image_progress_bar.setProgress(pedigree_status);
                            }
                        });
                    }
                }
            }).start();
            ApiService<ImageResponse> service = new ApiService<>();
            service.get(this, ApiClient.getApiInterface().uploadImages(Config.token, userDpFilePart), "PedigreeUpload");
            Log.e("DATALOG", "check1=> " + methods.getRequestJson(userDpFilePart));
        }else if (imageTypeName.equals("vaccination_card")){
            vaccination_card = 0;
            Handler purchase_proof_handler = new Handler();
            vaccination_card_image_progress_bar.setVisibility(View.VISIBLE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (vaccination_card < 80) {
                        vaccination_card += 1;
                        try {
                            Thread.sleep(15);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        purchase_proof_handler.post(new Runnable() {
                            @Override
                            public void run() {
                                vaccination_card_image_progress_bar.setProgress(vaccination_card);
                            }
                        });
                    }
                }
            }).start();
            ApiService<ImageResponse> service = new ApiService<>();
            service.get(this, ApiClient.getApiInterface().uploadImages(Config.token, userDpFilePart), "VaccinationCardUpload");
            Log.e("DATALOG", "check1=> " + methods.getRequestJson(userDpFilePart));
        }

    }
}