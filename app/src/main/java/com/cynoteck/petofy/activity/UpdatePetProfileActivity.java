package com.cynoteck.petofy.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.cynoteck.petofy.R;
import com.cynoteck.petofy.api.ApiClient;
import com.cynoteck.petofy.api.ApiResponse;
import com.cynoteck.petofy.api.ApiService;
import com.cynoteck.petofy.parameter.getPetListRequest.GetPetListParams;
import com.cynoteck.petofy.parameter.getPetListRequest.GetPetListRequest;
import com.cynoteck.petofy.parameter.petBreedRequest.BreedParams;
import com.cynoteck.petofy.parameter.petBreedRequest.BreedRequest;
import com.cynoteck.petofy.parameter.updateRequest.updateParamRequest.UpdatePetParam;
import com.cynoteck.petofy.parameter.updateRequest.updateParamRequest.UpdatePetRequest;
import com.cynoteck.petofy.response.addPet.addPetResponse.AddPetValueResponse;
import com.cynoteck.petofy.response.addPet.breedResponse.BreedCatRespose;
import com.cynoteck.petofy.response.addPet.imageUpload.ImageResponse;
import com.cynoteck.petofy.response.addPet.petAgeResponse.PetAgeValueResponse;
import com.cynoteck.petofy.response.addPet.petColorResponse.PetColorValueResponse;
import com.cynoteck.petofy.response.addPet.petSizeResponse.PetSizeValueResponse;
import com.cynoteck.petofy.response.addPet.uniqueIdResponse.UniqueResponse;
import com.cynoteck.petofy.response.getPetDetailsResponse.GetPetResponse;
import com.cynoteck.petofy.response.updateProfileResponse.PetTypeResponse;
import com.cynoteck.petofy.utils.Config;
import com.cynoteck.petofy.utils.MediaUtils;
import com.cynoteck.petofy.utils.Methods;
import com.google.android.material.card.MaterialCardView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class UpdatePetProfileActivity extends AppCompatActivity implements View.OnClickListener, ApiResponse , MediaUtils.GetImg{
    Methods                     methods;
    AppCompatSpinner            add_pet_type, add_pet_breed_dialog, add_pet_color_dialog;
    EditText                    pet_name_ET;
    TextView                    calenderTextViewDetails;
    ImageView                   pet_image_IV;
    MaterialCardView            back_arrow_CV;
    Button                      pet_update;
    DatePickerDialog            picker;
    ArrayList<String>           petType;
    ArrayList<String>           petBreed;
    ArrayList<String>           petAge;
    ArrayList<String>           petColor;
    ArrayList<String>           petSize;
    HashMap<String, String>     petTypeHashMap = new HashMap<>();
    HashMap<String, String>     petBreedHashMap = new HashMap<>();
    HashMap<String, String>     petAgeHashMap = new HashMap<>();
    HashMap<String, String>     petColorHashMap = new HashMap<>();
    HashMap<String, String>     petSizeHashMap = new HashMap<>();
    HashMap<String, String>     petSexHashMap = new HashMap<>();
    private static final String IMAGE_DIRECTORY = "/Picture";
    File                        file = null;
    String                      pet_id = "", currentDateandTime = "", strPetCategory = "", strPetName = "", strPetParentName = "", image_url = "",
                                strPetContactNumber = "", strPetDescription = "", strPetAdress = "", strPetBirthDay = "",
                                strSpnerItemPetNm = "", getStrSpnerItemPetNmId = "", strSpnrBreed = "", strSpnrBreedId = "", petUniqueId = "",
                                strSpnrAge = "", strSpnrAgeId = "", strSpnrColor = "", strSpnrColorId = "", strSpnrSize = "", strSpneSizeId = "",
                                strSpnrSex = "", strSpnrSexId = "", selctProflImage = "0", strProfileImgUrl = null, strFirstImgUrl = "", strSecondImgUrl = "",
                                strThirdImgUrl = "", strFourthImUrl = "", strFifthImgUrl = "", strDateofBirthObject = "";
    Dialog                      dialog;
    RadioGroup                  genderRG;
    RadioButton                 maleRB, femaleRB;
    LinearLayout                upload_image_LL;
    ConstraintLayout            uploaded_image_CL;
    RelativeLayout              remove_image_RL;
    TextView                    change_image_TV,upload_image_TV,image_path_TV;
    private static final int    MY_PERMISSIONS_REQUEST_READ_CAMERA = 200, MY_PERMISSIONS_REQUEST_READ_STORAGE = 300;
    Dialog                      storagePermissionDialog,cameraPermissionDialog;
    boolean                     cameraDialog= false, storageDialog= false;
    MediaUtils                  mediaUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_pet_details);
        methods     = new Methods(this);
        mediaUtils  = new MediaUtils(this);
        currentDateAndTime();
        Bundle extras = getIntent().getExtras();
        init();
        checkStorageAndCameraPermission();

        upload_image_LL     = findViewById(R.id.upload_image_LL);
        uploaded_image_CL   = findViewById(R.id.uploaded_image_CL);
        remove_image_RL     = findViewById(R.id.remove_image_RL);
        change_image_TV     = findViewById(R.id.change_image_TV);
        upload_image_TV     = findViewById(R.id.upload_image_TV);
        image_path_TV       = findViewById(R.id.image_path_TV);

        upload_image_LL.setOnClickListener(this);
        remove_image_RL.setOnClickListener(this);
        change_image_TV.setOnClickListener(this);


        if (extras != null) {
            pet_id = extras.getString("pet_id");
            strSpnerItemPetNm = extras.getString("pet_category");
            strPetName = extras.getString("pet_name");
            strSpnrSex = extras.getString("pet_sex");
            strDateofBirthObject = extras.getString("pet_DOB");
            strSpnrAge = extras.getString("pet_age");
            strSpnrSize = extras.getString("pet_size");
            strSpnrBreed = extras.getString("pet_breed");
            strSpnrColor = extras.getString("pet_color");
            strPetParentName = extras.getString("pet_parent");
            strPetContactNumber = extras.getString("pet_parent_contact");
            strProfileImgUrl = extras.getString("image_url");

            if (strSpnrSex.equals("Male")) {
                maleRB.setChecked(true);
                femaleRB.setChecked(false);
            } else {
                maleRB.setChecked(false);
                femaleRB.setChecked(true);
            }

            if (strProfileImgUrl==null) {
                upload_image_LL.setVisibility(View.VISIBLE);
                uploaded_image_CL.setVisibility(View.GONE);
            }else {
                Glide.with(this)
                        .load(image_url)
                        .placeholder(R.drawable.empty_pet_image)
                        .into(pet_image_IV);
                upload_image_TV.setText("Photo Uploaded ");
                upload_image_LL.setVisibility(View.GONE);
                uploaded_image_CL.setVisibility(View.VISIBLE);
            }

            pet_name_ET.setText(strPetName);
        }
        GetPetListParams getPetListParams = new GetPetListParams();
        getPetListParams.setId(pet_id);
        GetPetListRequest getPetListRequest = new GetPetListRequest();
        getPetListRequest.setData(getPetListParams);
        if (methods.isInternetOn()) {
            getPetlistData(getPetListRequest);
            petTypee();
            genaretePetUniqueKey();
        } else {
            methods.DialogInternet();
        }
    }

    private void init() {
        maleRB                  = findViewById(R.id.maleRB);
        genderRG                = findViewById(R.id.genderRG);
        femaleRB                = findViewById(R.id.femaleRB);

        add_pet_type            = findViewById(R.id.add_pet_type);
      
        add_pet_breed_dialog    = findViewById(R.id.add_pet_breed_dialog);
        add_pet_color_dialog    = findViewById(R.id.add_pet_color_dialog);
        pet_name_ET             = findViewById(R.id.pet_name_ET);
        calenderTextViewDetails = findViewById(R.id.calenderTextViewDetails);
        pet_image_IV            = findViewById(R.id.pet_image_IV);
        back_arrow_CV           = findViewById(R.id.back_arrow_CV);
        pet_update              = findViewById(R.id.pet_submit);

        back_arrow_CV.setOnClickListener(this);
        pet_update.setOnClickListener(this);
        calenderTextViewDetails.setOnClickListener(this);

    }

    private void checkStorageAndCameraPermission() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_READ_CAMERA);
            return;
        }else if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_STORAGE);
            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CAMERA: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (cameraDialog) {
                        cameraPermissionDialog.dismiss();
                        cameraDialog = false;
                        checkStorageAndCameraPermission();
                    }
                } else {
                    cameraDialog = false;
                    showCameraPermissionDialog();
                }

                return;
            }
            case MY_PERMISSIONS_REQUEST_READ_STORAGE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (storageDialog) {
                        storagePermissionDialog.dismiss();
                        storageDialog = false;
                        checkStorageAndCameraPermission();
                    }
                } else {
                    storageDialog = false;
                    showStoragePermissionDialog();
                }
                return;
            }

        }
    }


    private void showStoragePermissionDialog() {
        storageDialog = true;
        storagePermissionDialog = new Dialog(this);
        storagePermissionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        storagePermissionDialog.setCancelable(false);
        storagePermissionDialog.setContentView(R.layout.storage_permission_dialog);
        storagePermissionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Button grant_permission_BT = storagePermissionDialog.findViewById(R.id.grant_permission_BT);
        storagePermissionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        grant_permission_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkStorageAndCameraPermission();
            }
        });
        storagePermissionDialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = storagePermissionDialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
    }
    private void showCameraPermissionDialog() {
        cameraDialog = true;
        cameraPermissionDialog = new Dialog(this);
        cameraPermissionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        cameraPermissionDialog.setCancelable(false);
        cameraPermissionDialog.setContentView(R.layout.camera_permission_dialog);
        cameraPermissionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Button grant_permission_BT = cameraPermissionDialog.findViewById(R.id.grant_permission_BT);
        cameraPermissionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        grant_permission_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkStorageAndCameraPermission();
            }
        });

        cameraPermissionDialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = cameraPermissionDialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
    }

    private void getPetlistData(GetPetListRequest getPetListRequest) {
        methods.showCustomProgressBarDialog(this);
        ApiService<GetPetResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getPetDetails(Config.token, getPetListRequest), "GetPetDetail");
        Log.e("DATALOG", "check1=> PET_DETAILS " + methods.getRequestJson(getPetListRequest));
    }

    private void currentDateAndTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, d MMM yyyy h:mm:ss a", Locale.getDefault());
        currentDateandTime = sdf.format(new Date());
        Log.d("currentDateandTime", "" + currentDateandTime);
    }


    private void petTypee() {
        ApiService<PetTypeResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().petTypeApi(), "GetPetTypes");
    }

    private void genaretePetUniqueKey() {
        ApiService<UniqueResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getGeneratePetUniqueId(Config.token), "GeneratePetUniqueId");
    }

    private void getPetBreed() {
        BreedRequest breedRequest = new BreedRequest();
        breedRequest.setGetAll("false");
        if (!getStrSpnerItemPetNmId.equals("0")) {
            breedRequest.setPetCategoryId(getStrSpnerItemPetNmId);
        }
        else {
            breedRequest.setPetCategoryId("1");
        }
        BreedParams breedParams = new BreedParams();
        breedParams.setData(breedRequest);

        ApiService<BreedCatRespose> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getGetPetBreedApi(breedParams), "GetPetBreed");
        Log.d("Diolog_Breed", "===>" + breedParams);
    }

    private void getPetAge() {
        BreedRequest breedRequest = new BreedRequest();
        breedRequest.setGetAll("false");
        if (!getStrSpnerItemPetNmId.equals("0"))
            breedRequest.setPetCategoryId(getStrSpnerItemPetNmId);
        else
            breedRequest.setPetCategoryId("1");
        BreedParams breedParams = new BreedParams();
        breedParams.setData(breedRequest);

        ApiService<PetAgeValueResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getGetPetAgeApi(breedParams), "GetPetAge");
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

    private void getPetSize() {
        BreedRequest breedRequest = new BreedRequest();
        breedRequest.setGetAll("false");
        if (!getStrSpnerItemPetNmId.equals("0"))
            breedRequest.setPetCategoryId(getStrSpnerItemPetNmId);
        else
            breedRequest.setPetCategoryId("1");
        BreedParams breedParams = new BreedParams();
        breedParams.setData(breedRequest);

        ApiService<PetSizeValueResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getGetPetSizeApi(breedParams), "GetPetSize");
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pet_submit:
                strPetName          = pet_name_ET.getText().toString().trim();
                strPetParentName    = Config.user_name;
                strPetContactNumber = Config.user_phone;
                strPetDescription   = "";
                strPetAdress        = Config.user_address;
                strPetBirthDay      = calenderTextViewDetails.getText().toString().trim();
                if (maleRB.isChecked()) {
                    strSpnrSexId = "1";
                } else if (femaleRB.isChecked()) {
                    strSpnrSexId = "2";
                }
                if (strSpnerItemPetNm.isEmpty() || (strSpnerItemPetNm.equals("Select Pet Type"))) {
                    Toast.makeText(this, "Select Type!!", Toast.LENGTH_SHORT).show();
                }  else if (strSpnrSexId.equals("")) {
                    Toast.makeText(this, "Select Gender !", Toast.LENGTH_SHORT).show();
                }else if (strSpnrBreed.isEmpty() || (strSpnrBreed.equals("Pet Breed"))) {
                    Toast.makeText(this, "Select Breed!!", Toast.LENGTH_SHORT).show();
                } else if (strPetName.isEmpty()) {
                    Toast.makeText(this, "Enter Pet Name", Toast.LENGTH_SHORT).show();
                    pet_name_ET.setError("Enter Pet Name");
                }else if (strPetBirthDay.isEmpty()) {
                    Toast.makeText(this, "Pet YOB", Toast.LENGTH_SHORT).show();
                    pet_name_ET.setError(null);
                }  else if ((strSpnrColor.isEmpty()) || (strSpnrColor.equals("Pet Color"))) {
                    Toast.makeText(this, "Select Pet Color!!", Toast.LENGTH_SHORT).show();
                } else {
                    pet_name_ET.setError(null);
                    calenderTextViewDetails.setError(null);
                    UpdatePetRequest updatePetRequest = new UpdatePetRequest();
                    UpdatePetParam data = new UpdatePetParam();
                    data.setId(pet_id);
                    data.setPetCategoryId(getStrSpnerItemPetNmId);
                    data.setPetSexId(strSpnrSexId);
                    data.setPetAgeId("0");
                    data.setPetSizeId("0");
                    data.setPetColorId(strSpnrColorId);
                    data.setPetBreedId(strSpnrBreedId);
                    data.setPetName(strPetName);
                    data.setPetParentName(strPetParentName);
                    data.setContactNumber(strPetContactNumber);
                    data.setAddress(strPetAdress);
                    data.setDescription("Description");
                    data.setCreateDate(currentDateandTime);
                    data.setDateOfBirth(strPetBirthDay);
                    data.setPetProfileImageUrl(strProfileImgUrl);
                    data.setFirstServiceImageUrl(strFirstImgUrl);
                    data.setSecondServiceImageUrl(strSecondImgUrl);
                    data.setThirdServiceImageUrl(strThirdImgUrl);
                    data.setFourthServiceImageUrl(strFourthImUrl);
                    data.setFifthServiceImageUrl(strFifthImgUrl);
                    updatePetRequest.setAddPetParams(data);
                    if (methods.isInternetOn()) {
                        updatePetDetails(updatePetRequest);
                    } else {
                        methods.DialogInternet();
                    }
                }
                break;

            case R.id.calenderTextViewDetails:
                final Calendar cldr     = Calendar.getInstance();
                int day                 = cldr.get(Calendar.DAY_OF_MONTH);
                int month               = cldr.get(Calendar.MONTH);
                int year                = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(UpdatePetProfileActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                calenderTextViewDetails.setText(Config.changeDateFormat(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year) );
                            }
                        }, year, month, day);
                picker.getDatePicker().setMaxDate(System.currentTimeMillis());
                picker.show();
                break;

            case R.id.change_image_TV:

            case R.id.upload_image_LL:
                selctProflImage = "1";
                showPictureDialog();
                break;

            case R.id.remove_image_RL:
                upload_image_TV.setText("Upload photo ");
                upload_image_LL.setVisibility(View.VISIBLE);
                uploaded_image_CL.setVisibility(View.GONE);
                strProfileImgUrl =null;
                break;

            case R.id.back_arrow_CV:
                onBackPressed();
                break;

        }

    }

    @Override
    public void onResponse(Response arg0, String key) {
        switch (key) {
            case "GetPetDetail":
                try {
                    methods.customProgressDismiss();
                    GetPetResponse getPetResponse = (GetPetResponse) arg0.body();
                    Log.d("GetPetDetail", methods.getRequestJson(getPetResponse));
                    int responseCode = Integer.parseInt(getPetResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        pet_name_ET.setText(getPetResponse.getData().getPetName());
                        calenderTextViewDetails.setText(getPetResponse.getData().getDateOfBirth());
                        Glide.with(this).load(getPetResponse.getData().getPetProfileImageUrl()).placeholder(R.drawable.empty_pet_image).into(pet_image_IV);
                        strProfileImgUrl = getPetResponse.getData().getPetProfileImageUrl();
                    } else if (responseCode == 614) {
                        Toast.makeText(this, getPetResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "GetPetTypes":
                try {
                    Log.d("GetPetTypes", arg0.body().toString());
                    PetTypeResponse petTypeResponse = (PetTypeResponse) arg0.body();
                    int responseCode = Integer.parseInt(petTypeResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        petType = new ArrayList<>();
                        petType.add("Select Pet Type");
                        petTypeHashMap.put("Select Pet Type", "0");
                        Log.d("lalal", "" + petTypeResponse.getData().size());
                        for (int i = 0; i < petTypeResponse.getData().size(); i++) {
                            Log.d("petttt", "" + petTypeResponse.getData().get(i).getPetType1());
                            petType.add(petTypeResponse.getData().get(i).getPetType1());
                            petTypeHashMap.put(petTypeResponse.getData().get(i).getPetType1(), petTypeResponse.getData().get(i).getId());
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
            case "GetPetBreed":
                try {
                    Log.d("GetPetBreed", arg0.body().toString());
                    BreedCatRespose breedCatRespose = (BreedCatRespose) arg0.body();
                    int responseCode = Integer.parseInt(breedCatRespose.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        petBreed = new ArrayList<>();
                        petBreed.add("Pet Breed");
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
                        Log.e("error","PETBREED");
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "GetPetAge":
                try {
                    Log.d("GetPetAge", arg0.body().toString());
                    PetAgeValueResponse petAgeValueResponse = (PetAgeValueResponse) arg0.body();
                    int responseCode = Integer.parseInt(petAgeValueResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        petAge = new ArrayList<>();
                        petAge.add("Select Pet Age");
                        Log.d("lalal", "" + petAgeValueResponse.getData().size());
                        for (int i = 0; i < petAgeValueResponse.getData().size(); i++) {
                            Log.d("petttt", "" + petAgeValueResponse.getData().get(i).getAge());
                            petAge.add(petAgeValueResponse.getData().get(i).getAge());
                            petAgeHashMap.put(petAgeValueResponse.getData().get(i).getAge(), petAgeValueResponse.getData().get(i).getId());
                        }
                    } else if (responseCode == 614) {
                        Toast.makeText(this, petAgeValueResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "GetPetColor":
                try {
                    Log.d("GetPetColor", arg0.body().toString());
                    PetColorValueResponse petColorValueResponse = (PetColorValueResponse) arg0.body();
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
            case "GetPetSize":
                try {
                    Log.d("GetPetSize", arg0.body().toString());
                    PetSizeValueResponse petSizeValueResponse = (PetSizeValueResponse) arg0.body();
                    int responseCode = Integer.parseInt(petSizeValueResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        petSize = new ArrayList<>();
                        petSize.add("Pet Size");
                        Log.d("lalal", "" + petSizeValueResponse.getData().size());
                        for (int i = 0; i < petSizeValueResponse.getData().size(); i++) {
                            Log.d("petttt", "" + petSizeValueResponse.getData().get(i).getSize());
                            petSize.add(petSizeValueResponse.getData().get(i).getSize());
                            petSizeHashMap.put(petSizeValueResponse.getData().get(i).getSize(), petSizeValueResponse.getData().get(i).getId());
                        }
                    } else if (responseCode == 614) {
                        Toast.makeText(this, petSizeValueResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "UpdatePetDetails":
                try {
                    methods.customProgressDismiss();
                    Log.d("UpdatePetDetails", arg0.body().toString());
                    AddPetValueResponse addPetValueResponse = (AddPetValueResponse) arg0.body();
                    int responseCode = Integer.parseInt(addPetValueResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        Config.isLoaded = true;
                        Intent intent = new Intent();
                        Log.e("AGE",addPetValueResponse.getData().getDateOfBirth()+"AGE"+addPetValueResponse.getData().getPetAge());
                        intent.putExtra("pet_id", addPetValueResponse.getData().getId().substring(0,addPetValueResponse.getData().getId().length()-2));
                        intent.putExtra("pet_unique_id", addPetValueResponse.getData().getPetUniqueId());
                        intent.putExtra("pet_image_url", addPetValueResponse.getData().getPetProfileImageUrl());
                        intent.putExtra("pet_breed", addPetValueResponse.getData().getPetBreed());
                        intent.putExtra("pet_age", addPetValueResponse.getData().getPetAge());
                        intent.putExtra("pet_parent", addPetValueResponse.getData().getPetParentName());
                        intent.putExtra("pet_sex", addPetValueResponse.getData().getPetSex());
                        intent.putExtra("pet_name", addPetValueResponse.getData().getPetName());
                        intent.putExtra("pet_category", addPetValueResponse.getData().getPetCategory());
                        intent.putExtra("pet_date_of_birth", addPetValueResponse.getData().getDateOfBirth());
                        intent.putExtra("pet_color", addPetValueResponse.getData().getPetColor());
                        setResult(RESULT_OK, intent);
                        finish();
                        Toast.makeText(this, "Update Successfully ", Toast.LENGTH_SHORT).show();
                    } else if (responseCode == 614) {
                        Toast.makeText(this, addPetValueResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "UploadDocument":
                try {
                    methods.customProgressDismiss();
                    Log.d("UploadDocument", arg0.body().toString());
                    ImageResponse imageResponse = (ImageResponse) arg0.body();
                    int responseCode = Integer.parseInt(imageResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        if (selctProflImage.equals("1")) {
                            strProfileImgUrl = imageResponse.getData().getDocumentUrl();
                            selctProflImage = "0";
                            upload_image_TV.setText("Photo Uploaded");
                            upload_image_LL.setVisibility(View.GONE);
                            Glide.with(this)
                                    .load(strProfileImgUrl)
                                    .placeholder(R.drawable.empty_pet_image)
                                    .into(pet_image_IV);
                            uploaded_image_CL.setVisibility(View.VISIBLE);
                        }
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
        methods.customProgressDismiss();
        Toast.makeText(this, "Please try again!", Toast.LENGTH_SHORT).show();
        Log.e("ERROR",t.getLocalizedMessage());

    }

    private void setPetTypeSpinner() {
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, petType);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        add_pet_type.setAdapter(aa);
        if (!strSpnerItemPetNm.equals("")) {
            int spinnerPosition = aa.getPosition(strSpnerItemPetNm);
            add_pet_type.setSelection(spinnerPosition);
        }
        add_pet_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                strSpnerItemPetNm = item;
                Log.d("spnerType", "" + strSpnerItemPetNm);
                getStrSpnerItemPetNmId = petTypeHashMap.get(strSpnerItemPetNm);
                if (!getStrSpnerItemPetNmId.equals("0")) {
                    getPetBreed();
                    getPetAge();
                    getPetColor();
                    getPetSize();
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setPetBreeSpinner() {
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, petBreed);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        add_pet_breed_dialog.setAdapter(aa);
        if (!strSpnrBreed.equals("")) {
            int spinnerPosition = aa.getPosition(strSpnrBreed);
            add_pet_breed_dialog.setSelection(spinnerPosition);
        }
        add_pet_breed_dialog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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



    private void setPetColorSpinner() {
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, petColor);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        add_pet_color_dialog.setAdapter(aa);
        if (!strSpnrColor.equals("")) {
            int spinnerPosition = aa.getPosition(strSpnrColor);
            add_pet_color_dialog.setSelection(spinnerPosition);
        }
        add_pet_color_dialog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                mediaUtils.openCamera();
                dialog.dismiss();
            }
        });

        select_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaUtils.openGallery();
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mediaUtils.onActivityResult(requestCode, resultCode, data);
    }

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }
        try {
            if (selctProflImage.equals("1")) {
                file = new File(wallpaperDirectory, Calendar.getInstance()
                        .getTimeInMillis() + ".png");
                file.createNewFile();
                FileOutputStream fo = new FileOutputStream(file);
                fo.write(bytes.toByteArray());
                MediaScannerConnection.scanFile(this,
                        new String[]{file.getPath()},
                        new String[]{"image/png"}, null);
                fo.close();
                Log.d("TAG", "File Saved::---&gt;" + file.getAbsolutePath());
                image_path_TV.setText(file.getAbsolutePath());
                UploadImages(file);
                return file.getAbsolutePath();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private void UploadImages(File absolutePath) {
        methods.showCustomProgressBarDialog(this);
        MultipartBody.Part userDpFilePart = null;
        if (absolutePath != null) {
            RequestBody userDpFile = RequestBody.create(MediaType.parse("image/*"), absolutePath);
            userDpFilePart = MultipartBody.Part.createFormData("file", absolutePath.getName(), userDpFile);
        }

        ApiService<ImageResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().uploadImages(Config.token, userDpFilePart), "UploadDocument");
        Log.e("DATALOG", "check1=> " + service);

    }
    private void updatePetDetails(UpdatePetRequest addPetRequset) {
        methods.showCustomProgressBarDialog(this);
        ApiService<AddPetValueResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().updatePetDetails(Config.token, addPetRequset), "UpdatePetDetails");
        Log.e("DATALOG", "check1=> " + methods.getRequestJson(addPetRequset));

    }

    @Override
    public void imgdata(String imgPath) {
        Log.d ("imgdata123" , imgPath.toString());
        Uri selectedImageURI = null;
        File imgFile = new File(imgPath);
        Log.d ("imgdata: " , imgFile.toString());
        UploadImages(imgFile);
    }
}