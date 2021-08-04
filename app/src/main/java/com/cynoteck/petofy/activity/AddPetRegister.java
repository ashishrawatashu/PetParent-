package com.cynoteck.petofy.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
import com.cynoteck.petofy.parameter.addParamRequest.AddPetParams;
import com.cynoteck.petofy.parameter.addParamRequest.AddPetRequset;
import com.cynoteck.petofy.parameter.getpetAgeRequest.GetPetAgeParameter;
import com.cynoteck.petofy.parameter.getpetAgeRequest.GetPetAgeRequestData;
import com.cynoteck.petofy.parameter.petBreedRequest.BreedParams;
import com.cynoteck.petofy.parameter.petBreedRequest.BreedRequest;
import com.cynoteck.petofy.response.addPet.addPetResponse.AddPetValueResponse;
import com.cynoteck.petofy.response.addPet.breedResponse.BreedCatRespose;
import com.cynoteck.petofy.response.addPet.imageUpload.ImageResponse;
import com.cynoteck.petofy.response.addPet.petAgeResponse.PetAgeValueResponse;
import com.cynoteck.petofy.response.addPet.petColorResponse.PetColorValueResponse;
import com.cynoteck.petofy.response.dateOfBirthResponse.DateOfBirthResponse;
import com.cynoteck.petofy.response.getPetAgeResponse.GetPetAgeresponseData;
import com.cynoteck.petofy.response.getPetParrentnameReponse.GetPetParentResponseData;
import com.cynoteck.petofy.response.petAgeUnitResponse.PetAgeUnitResponseData;
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

public class AddPetRegister extends AppCompatActivity implements View.OnClickListener, ApiResponse, TextWatcher,MediaUtils.GetImg {
    ScrollView                      scrollView;
    AppCompatSpinner                age_wise, add_pet_age, add_pet_type, add_pet_breed, add_pet_color;
    EditText                        pet_name, age_neumeric;
    TextView                        calenderView, ageViewTv;
    MaterialCardView                back_arrow_CV;
    Button                          pet_submit;
    CheckBox                        convert_yr_to_age;
    LinearLayout                    day_and_age_layout,upload_image_LL;
    String                          strPetName = "", strPetDescription = "", strPetAdress = "", strPetBirthDay = "",
                                    strSpnerItemPetNm = "", getStrSpnerItemPetNmId = "", strSpnrBreed = "", strSpnrBreedId = "", petUniqueId = "", strAgeCount = "",
                                    strSpnrAge = "", strSpnrAgeId = "", strSpnrColor = "", strSpnrColorId = "", strSpneSizeId = "",
                                    strSpnrSexId = "", currentDateandTime = "", selctProflImage = "0", strProfileImgUrl = null, strFirstImgUrl = "", strSecondImgUrl = "",
                                    strThirdImgUrl = "", strFourthImUrl = "", strFifthImgUrl = "";
    Dialog                          dialog;
    ImageView                       pet_image_IV;
    ConstraintLayout                uploaded_image_CL;
    TextView                        upload_image_TV,image_path_TV,change_image_TV;
    RelativeLayout                  remove_image_RL;
    Methods                         methods;
    DatePickerDialog                picker;
    private final int               MY_PERMISSIONS_REQUEST_READ_CAMERA = 200, MY_PERMISSIONS_REQUEST_READ_STORAGE = 300;
    private static final String     IMAGE_DIRECTORY = "/Picture";
    private final int               GALLERY = 1, CAMERA = 2;
    File                            file = null;
    String                          intentFrom = "Add";

    RadioGroup genderRG;
    RadioButton maleRB, femaleRB;
    Dialog storagePermissionDialog,cameraPermissionDialog;

    boolean cameraDialog= false, storageDialog= false;

    MediaUtils mediaUtils;

    ArrayList<String>               petType;
    ArrayList<String>               petBreed;
    ArrayList<String>               petAge;
    ArrayList<String>               petColor;
    ArrayList<String>               petAgeType;

    HashMap<String, String> petTypeHashMap = new HashMap<>();
    HashMap<String, String> petBreedHashMap = new HashMap<>();
    HashMap<String, String> petAgeHashMap = new HashMap<>();
    HashMap<String, String> petColorHashMap = new HashMap<>();
    HashMap<String, String> petAgeUnitHash = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet_register);
        Intent intent = getIntent();

        intentFrom = intent.getStringExtra("intent_from");
        init();
        currentDateAndTime();
        checkStorageAndCameraPermission();
        methods = new Methods(this);
        mediaUtils =new MediaUtils(this);
        if (methods.isInternetOn()) {
            petType();
            getPetAgeUnit();
        } else {
            methods.DialogInternet();
        }
    }


    private void getPetAgeUnit() {
        ApiService<PetAgeUnitResponseData> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getPetAgeUnit(), "GetPetAgeUnit");
    }

    private void getPetDateofBirthDependsOnDays(String day) {
        ApiService<DateOfBirthResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().GetPetDateOfBirth(day), "getDateOfYear");
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

    private void currentDateAndTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, d MMM yyyy h:mm:ss a", Locale.getDefault());
        currentDateandTime = sdf.format(new Date());
        Log.d("currentDateandTime", "" + currentDateandTime);
    }


    private void petType() {
        methods.showCustomProgressBarDialog(this);
        ApiService<PetTypeResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().petTypeApi(), "GetPetTypes");
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

    private void init() {
        back_arrow_CV   = findViewById(R.id.back_arrow_CV);

        scrollView      = findViewById(R.id.scrollView);
        maleRB          = findViewById(R.id.maleRB);
        genderRG        = findViewById(R.id.genderRG);
        femaleRB        = findViewById(R.id.femaleRB);


        //Spinner
        add_pet_type    = findViewById(R.id.add_pet_type);
        add_pet_breed   = findViewById(R.id.add_pet_breed_dialog);
        add_pet_color   = findViewById(R.id.add_pet_color_dialog);
        add_pet_age     = findViewById(R.id.add_pet_age_dialog);

        //TextInputEditText
        pet_name        = findViewById(R.id.pet_name_ET);


        //TextView
        calenderView    = findViewById(R.id.calenderTextView_dialog);
        calenderView.setOnClickListener(this);

        //ImageView
        pet_image_IV        = findViewById(R.id.pet_image_IV);
        upload_image_LL     = findViewById(R.id.upload_image_LL);
        image_path_TV       = findViewById(R.id.image_path_TV);
        upload_image_TV     = findViewById(R.id.upload_image_TV);
        remove_image_RL     = findViewById(R.id.remove_image_RL);
        change_image_TV     =findViewById(R.id.change_image_TV);

        change_image_TV.setOnClickListener(this);
        remove_image_RL.setOnClickListener(this);
        upload_image_LL.setOnClickListener(this);

        uploaded_image_CL   = findViewById(R.id.uploaded_image_CL);
        convert_yr_to_age   = findViewById(R.id.convert_yr_to_age);
        age_wise            = findViewById(R.id.age_wise);
        age_neumeric        = findViewById(R.id.age_neumeric);
        day_and_age_layout  = findViewById(R.id.day_and_age_layout);
        ageViewTv           = findViewById(R.id.ageViewTv);
        ageViewTv.setText("Age:- 0 Days");

        back_arrow_CV.setOnClickListener(this);
        convert_yr_to_age.setOnClickListener(this);

        //Button
        pet_submit = findViewById(R.id.pet_submit);
        pet_submit.setOnClickListener(this);
        age_neumeric.addTextChangedListener(this);
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


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.back_arrow_CV:
                onBackPressed();
                break;
            case R.id.convert_yr_to_age:
                if (((CompoundButton) view).isChecked()) {
                    day_and_age_layout.setVisibility(View.VISIBLE);
                    calenderView.setVisibility(View.GONE);
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
                    calenderView.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.pet_submit:
                strPetName          = pet_name.getText().toString().trim();
                strPetDescription   = "";
                strPetBirthDay      = calenderView.getText().toString().trim();
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
                    pet_name.setError("Enter Pet Name");
                    calenderView.setError(null);
                }else if (strPetBirthDay.isEmpty()) {
                    Toast.makeText(this, "Pet YOB", Toast.LENGTH_SHORT).show();
                    pet_name.setError(null);
                    calenderView.setError("Pet YOB");
                }  else if ((strSpnrColor.isEmpty()) || (strSpnrColor.equals("Pet Color"))) {
                    Toast.makeText(this, "Select Pet Color!!", Toast.LENGTH_SHORT).show();
                } else {
                    pet_name.setError(null);
                    calenderView.setError(null);
                    Log.d("hahahah", "" + getStrSpnerItemPetNmId + " " + strSpnrSexId + " " + strSpnrAgeId + " " + strSpneSizeId + " " + strSpnrColorId + " " + strSpnrBreedId + " " + strPetName + " " + strPetBirthDay + " " + strPetDescription + " " + currentDateandTime);
                    AddPetRequset addPetRequset = new AddPetRequset();
                    AddPetParams data = new AddPetParams();
                    data.setPetUniqueId(petUniqueId);
                    data.setPetCategoryId(getStrSpnerItemPetNmId);
                    data.setPetSexId(strSpnrSexId);
                    data.setPetAgeId("0.0");
                    data.setPetSizeId("0.0");
                    data.setPetColorId(strSpnrColorId);
                    data.setPetBreedId(strSpnrBreedId);
                    data.setPetName(strPetName);
                    data.setPetParentName(Config.user_name);
                    data.setContactNumber(Config.user_phone);
                    data.setAddress(strPetAdress);
                    data.setDescription(strPetDescription);
                    data.setCreateDate(currentDateandTime);
                    data.setDateOfBirth(strPetBirthDay);
                    data.setPetProfileImageUrl(strProfileImgUrl);
                    data.setFirstServiceImageUrl(strFirstImgUrl);
                    data.setSecondServiceImageUrl(strSecondImgUrl);
                    data.setThirdServiceImageUrl(strThirdImgUrl);
                    data.setFourthServiceImageUrl(strFourthImUrl);
                    data.setFifthServiceImageUrl(strFifthImgUrl);
                    addPetRequset.setAddPetParams(data);
                    if (methods.isInternetOn()) {
                        pet_submit.setEnabled(false);
                        addPetData(addPetRequset);
                    } else {
                        methods.DialogInternet();
                    }
                }
                break;

            case R.id.calenderTextView_dialog:
                final Calendar cldr = Calendar.getInstance();
                int day             = cldr.get(Calendar.DAY_OF_MONTH);
                int month           = cldr.get(Calendar.MONTH);
                int year            = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(com.cynoteck.petofy.activity.AddPetRegister.this,new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                calenderView.setText(Config.changeDateFormat(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year));
                                String DoB          = dayOfMonth + " " + (monthOfYear + 1) + " " + year;
                                String age          = String.valueOf(methods.getDays(DoB, methods.getDate()));
                                String DoBforage    = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                age                 = age.substring(0, age.length() - 2);
                                getPetAgeString(DoBforage);
                                age_neumeric.setText(age);
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

        }

    }

    private void showPictureDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_layout);
        RelativeLayout select_camera    = dialog.findViewById(R.id.select_camera);
        RelativeLayout select_gallery   = dialog.findViewById(R.id.select_gallery);
        RelativeLayout cancel_dialog    = dialog.findViewById(R.id.cancel_dialog);

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
            }
        });

        select_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaUtils.openGallery();

            }
        });


        dialog.show();
    }




    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        dialog.dismiss();
        mediaUtils.onActivityResult(requestCode, resultCode, data);
    }

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
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

    private void addPetData(AddPetRequset addPetRequset) {
        Log.e("yes", "yes");
        methods.showCustomProgressBarDialog(this);
        ApiService<AddPetValueResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().addNewPet(Config.token, addPetRequset), "AddPet");
        Log.e("DATALOG", "check1=> " + methods.getRequestJson(addPetRequset));

    }

    @Override
    public void onResponse(Response arg0, String key) {
        pet_submit.setEnabled(true);
        switch (key) {
            case "SearchPetParent":
                try {
                    Log.d("SearchPetParent", arg0.body().toString());
                    GetPetParentResponseData getPetParentResponseData = (GetPetParentResponseData) arg0.body();
                    int responseCode = Integer.parseInt(getPetParentResponseData.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        Log.d("SearchPetParent", "" + getPetParentResponseData.getData().size());
                        ArrayList remarksSearchList = new ArrayList<>();
                        for (int i = 0; i < getPetParentResponseData.getData().size(); i++) {
                            remarksSearchList.add(getPetParentResponseData.getData().get(i).getPetParentName()
                                    + "\n( " + getPetParentResponseData.getData().get(i).getContactNumber() + " )");
                        }

                        //for parent name
                        ArrayAdapter<String> randomArray = new ArrayAdapter<String>(this,
                                android.R.layout.simple_list_item_1, remarksSearchList);
                        randomArray.notifyDataSetChanged();

                        //for contact number
                        ArrayAdapter<String> randomArrayContactNumber = new ArrayAdapter<String>(this,
                                android.R.layout.simple_list_item_1, remarksSearchList);
                        randomArrayContactNumber.notifyDataSetChanged();


                    } else if (responseCode == 614) {
                        Toast.makeText(this, getPetParentResponseData.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "getDateOfYear":
                try {
                    DateOfBirthResponse dateOfBirthResponse = (DateOfBirthResponse) arg0.body();
                    Log.d("getDateOfYear", dateOfBirthResponse.toString());
                    int responseCode = Integer.parseInt(dateOfBirthResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        calenderView.setText(dateOfBirthResponse.getData());
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
                    Log.d("GetPetAgeString", arg0.body().toString());
                    GetPetAgeresponseData getPetAgeresponseData = (GetPetAgeresponseData) arg0.body();
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

            case "GetPetAgeUnit":
                try {
                    methods.customProgressDismiss();
                    Log.d("GetPetTypes", arg0.body().toString());
                    PetAgeUnitResponseData petAgeUnitResponseData = (PetAgeUnitResponseData) arg0.body();
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

                break;

            case "GetPetBreed":
                try {
                    Log.d("GetPetBreed", arg0.body().toString());
                    BreedCatRespose breedCatRespose = (BreedCatRespose) arg0.body();
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
                        setPetAgeSpinner();
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

            case "AddPet":
                try {
                    methods.customProgressDismiss();
                    Log.d("AddPet", arg0.body().toString());
                    AddPetValueResponse addPetValueResponse = (AddPetValueResponse) arg0.body();
                    Log.d("addPetValueResponse", "" + addPetValueResponse);
                    int responseCode = Integer.parseInt(addPetValueResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                            if (!intentFrom.equals("AfterScanScreenActivity")){
                                Toast.makeText(this, "Pet Added Successfully ", Toast.LENGTH_SHORT).show();
                            }

                            Intent intent = new Intent();
                            intent.putExtra("pet_id", addPetValueResponse.getData().getId());
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
        Log.e("error", "" + t.getLocalizedMessage());
        pet_submit.setEnabled(true);
        methods.customProgressDismiss();
    }


    private void setPetTypeSpinner() {
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, petType);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        add_pet_type.setAdapter(aa);
        add_pet_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                strSpnerItemPetNm = item;
                Log.d("spnerType", "" + strSpnerItemPetNm);
                getStrSpnerItemPetNmId = petTypeHashMap.get(strSpnerItemPetNm);
                if (!getStrSpnerItemPetNmId.equals("0")) {
                    getPetBreed();
                    getPetAge();
                    getPetColor();
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
        add_pet_breed.setAdapter(aa);
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

    private void setPetAgeSpinner() {
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, petAge);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        add_pet_age.setAdapter(aa);
        add_pet_age.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                strSpnrAge = item;
                Log.d("spnerType", "" + strSpnrAge);
                strSpnrAgeId = petAgeHashMap.get(strSpnrAge);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setPetColorSpinner() {
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, petColor);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        add_pet_color.setAdapter(aa);
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


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

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