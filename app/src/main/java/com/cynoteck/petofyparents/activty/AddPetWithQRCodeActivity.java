
package com.cynoteck.petofyparents.activty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.api.ApiClient;
import com.cynoteck.petofyparents.api.ApiResponse;
import com.cynoteck.petofyparents.api.ApiService;
import com.cynoteck.petofyparents.parameter.getpetAgeRequest.GetPetAgeParameter;
import com.cynoteck.petofyparents.parameter.getpetAgeRequest.GetPetAgeRequestData;
import com.cynoteck.petofyparents.parameter.petBreedRequest.BreedParams;
import com.cynoteck.petofyparents.parameter.petBreedRequest.BreedRequest;
import com.cynoteck.petofyparents.parameter.registrationWithQrCodeRequest.RegistrationWithQrCodeParams;
import com.cynoteck.petofyparents.parameter.registrationWithQrCodeRequest.RegistrationWithQrCodeRequest;
import com.cynoteck.petofyparents.response.addPet.addPetResponse.AddPetValueResponse;
import com.cynoteck.petofyparents.response.addPet.breedResponse.BreedCatRespose;
import com.cynoteck.petofyparents.response.addPet.petAgeResponse.PetAgeValueResponse;
import com.cynoteck.petofyparents.response.addPet.petColorResponse.PetColorValueResponse;
import com.cynoteck.petofyparents.response.addPet.petSizeResponse.PetSizeValueResponse;
import com.cynoteck.petofyparents.response.addPet.uniqueIdResponse.UniqueResponse;
import com.cynoteck.petofyparents.response.dateOfBirthResponse.DateOfBirthResponse;
import com.cynoteck.petofyparents.response.getPetAgeResponse.GetPetAgeresponseData;
import com.cynoteck.petofyparents.response.petAgeUnitResponse.PetAgeUnitResponseData;
import com.cynoteck.petofyparents.response.registerParentWithQRResponse.RegisterParentWithQRResponse;
import com.cynoteck.petofyparents.response.updateProfileResponse.PetTypeResponse;
import com.cynoteck.petofyparents.utils.Config;
import com.cynoteck.petofyparents.utils.Methods;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Response;

public class AddPetWithQRCodeActivity extends AppCompatActivity implements ApiResponse, View.OnClickListener, TextWatcher {

    AppCompatSpinner age_wise, parent_address, add_pet_age, add_pet_type, add_pet_sex_dialog, add_pet_breed_dialog, add_pet_color_dialog, add_pet_size;
    ImageView back_arrow_IV;
    EditText firstName_ET, lastName_ET, pet_parent_email_ET, pincode_ET, address_ET, pet_name_ET, age_neumeric;
    TextView calenderView, ageViewTv;
    Methods methods;

    ArrayList<String> petType;
    ArrayList<String> petBreed;
    ArrayList<String> petAge;
    ArrayList<String> petColor;
    ArrayList<String> petSize;
    ArrayList<String> petSex;
    ArrayList<String> petAgeType;
    ArrayList<String> parentAdress;
    LinearLayout day_and_age_layout;
    HashMap<String, String> petTypeHashMap = new HashMap<>();
    HashMap<String, String> petBreedHashMap = new HashMap<>();
    HashMap<String, String> petAgeHashMap = new HashMap<>();
    HashMap<String, String> petColorHashMap = new HashMap<>();
    HashMap<String, String> petSizeHashMap = new HashMap<>();
    HashMap<String, String> petSexHashMap = new HashMap<>();
    HashMap<String, String> petAgeUnitHash = new HashMap<>();
    CheckBox convert_yr_to_age;
    Button signUP_BT;
    DatePickerDialog picker;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor login_editor;
    ScrollView scrollView;
    String strPetName = "", strPetParentFirstName = "", strPetParentLastName = "", strPetParentEmail = "", strPetParentPincode = "", strPetParentAddress = "", strPetContactNumber = "", strPetBirthDay = "",
            strSpnerItemPetNm = "", getStrSpnerItemPetNmId = "", strSpnrBreed = "", strSpnrBreedId = "", petUniqueId = "", strAgeCount = "",
            strSpnrAge = "", strSpnrAgeId = "", strSpnrColor = "", strSpnrColorId = "", strSpnrSize = "", strSpneSizeId = "",
            strSpnrSex = "", strSpnrSexId = "", currentDateandTime = "", selctProflImage = "0", selctImgOne = "0", selctImgtwo = "0",
            slctImgThree = "0", slctImgFour = "0", slctImgFive = "0", strProfileImgUrl = "", strFirstImgUrl = "", strSecondImgUrl = "",
            strThirdImgUrl = "", strFourthImUrl = "", strFifthImgUrl = "", veterinarianUserId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet_with_q_r_code);
        methods = new Methods(this);
        Intent intent = getIntent();
        strPetContactNumber = intent.getStringExtra("phoneNumber");
        veterinarianUserId = intent.getStringExtra("veterinarianUserId");

        currentDateAndTime();
        scrollView = findViewById(R.id.scrollView);
        add_pet_type = findViewById(R.id.add_pet_type);
        add_pet_sex_dialog = findViewById(R.id.add_pet_sex_dialog);
        add_pet_breed_dialog = findViewById(R.id.add_pet_breed_dialog);
        add_pet_color_dialog = findViewById(R.id.add_pet_color_dialog);
        back_arrow_IV = findViewById(R.id.back_arrow_IV);
        firstName_ET = findViewById(R.id.firstName_ET);
        lastName_ET = findViewById(R.id.lastName_ET);
        pet_parent_email_ET = findViewById(R.id.pet_parent_email_ET);
        pincode_ET = findViewById(R.id.pincode_ET);
        address_ET = findViewById(R.id.address_ET);
        pet_name_ET = findViewById(R.id.pet_name_ET);
        calenderView = findViewById(R.id.calenderTextView_dialog);
        add_pet_size = findViewById(R.id.add_pet_size_dialog);
        ageViewTv = findViewById(R.id.ageViewTv);
        signUP_BT = findViewById(R.id.signUP_BT);
        age_wise = findViewById(R.id.age_wise);
        age_neumeric = findViewById(R.id.age_neumeric);
        convert_yr_to_age = findViewById(R.id.convert_yr_to_age);
        day_and_age_layout = findViewById(R.id.day_and_age_layout);
        back_arrow_IV.setOnClickListener(this);
        signUP_BT.setOnClickListener(this);
        calenderView.setOnClickListener(this);
        convert_yr_to_age.setOnClickListener(this);

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
        methods = new Methods(this);

        petSex = new ArrayList<>();
        petSex.add("Pet Sex");
        petSex.add("Male");
        petSex.add("Female");
        petSexHashMap.put("Pet Sex", "0");
        petSexHashMap.put("Male", "1");
        petSexHashMap.put("Female", "2");

        if (methods.isInternetOn()) {
            petType();
            genaretePetUniqueKey();
            setSpinnerPetSex();
            getPetAgeUnit();

        } else {
            methods.DialogInternet();
        }


    }

    private void getPetDateofBirthDependsOnDays(String day) {
        ApiService<DateOfBirthResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().GetPetDateOfBirth(day), "getDateOfYear");

    }

    private void currentDateAndTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, d MMM yyyy h:mm:ss a", Locale.getDefault());
        currentDateandTime = sdf.format(new Date());
        Log.d("currentDateandTime", "" + currentDateandTime);
    }

    private void getPetAgeUnit() {
        ApiService<PetAgeUnitResponseData> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getPetAgeUnit(), "GetPetAgeUnit");

    }


    private void setSpinnerPetSex() {
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, petSex);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        add_pet_sex_dialog.setAdapter(aa);
        add_pet_sex_dialog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                strSpnrSex = item;
                Log.d("spnerType", "" + strSpnrSex);
                strSpnrSexId = petSexHashMap.get(strSpnrSex);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void genaretePetUniqueKey() {
        ApiService<UniqueResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getGeneratePetUniqueId(Config.token), "GeneratePetUniqueId");
    }

    private void petType() {
        methods.showCustomProgressBarDialog(this);
        ApiService<PetTypeResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().petTypeApi(), "GetPetTypes");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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

            case R.id.calenderTextView_dialog:
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(AddPetWithQRCodeActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                calenderView.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                String DoB = dayOfMonth + " " + (monthOfYear + 1) + " " + year;
                                Log.d("jajajaajja", "" + methods.getDays(DoB, methods.getDate()));
                                String age = String.valueOf(methods.getDays(DoB, methods.getDate()));
                                String DoBforage = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                age = age.substring(0, age.length() - 2);
                                getPetAgeString(DoBforage);
                                age_neumeric.setText(age);
                            }
                        }, year, month, day);
                picker.getDatePicker().setMaxDate(System.currentTimeMillis());
                picker.show();
                break;

            case R.id.signUP_BT:
                strPetParentFirstName = firstName_ET.getText().toString().trim();
                strPetParentLastName = lastName_ET.getText().toString().trim();
                strPetParentEmail = pet_parent_email_ET.getText().toString().trim();
                strPetParentPincode = pincode_ET.getText().toString().trim();
                strPetParentAddress = address_ET.getText().toString().trim();
                strPetName = pet_name_ET.getText().toString().trim();
                strPetBirthDay = calenderView.getText().toString().trim();

                if (strPetName.isEmpty()) {
                    pet_parent_email_ET.setError(null);
                    firstName_ET.setError(null);
                    lastName_ET.setError(null);
                    pet_name_ET.setError("Enter Pet Name!");
                    pincode_ET.setError(null);
                    address_ET.setError(null);
                } else if (strSpnerItemPetNm.isEmpty() || (strSpnerItemPetNm.equals("Select Pet Type"))) {
                    Toast.makeText(this, "Select Type !", Toast.LENGTH_SHORT).show();
                } else if (strSpnrSex.isEmpty() || (strSpnrSex.equals("Pet Sex"))) {
                    Toast.makeText(this, "Select Pet Sex !", Toast.LENGTH_SHORT).show();
                } else if (strPetBirthDay.isEmpty()) {
                    Toast.makeText(this, "Pet DOB", Toast.LENGTH_SHORT).show();
                    pet_name_ET.setError(null);
                    firstName_ET.setError(null);
                    lastName_ET.setError(null);
                    address_ET.setError(null);
                    pincode_ET.setError(null);
                    calenderView.setError("Pet DOB");
                } else if (strSpnrBreed.isEmpty() || (strSpnrBreed.equals("Pet Breed"))) {
                    Toast.makeText(this, "Select Breed !", Toast.LENGTH_SHORT).show();
                } else if ((strSpnrColor.isEmpty()) || (strSpnrColor.equals("Pet Color"))) {
                    Toast.makeText(this, "Select Pet Color !", Toast.LENGTH_SHORT).show();
                } else if (strPetParentPincode.isEmpty()) {
                    pet_parent_email_ET.setError(null);
                    firstName_ET.setError(null);
                    lastName_ET.setError(null);
                    pet_name_ET.setError(null);
                    pincode_ET.setError("Enter Pincode!");
                    address_ET.setError(null);
                } else if (strPetParentPincode.length() < 6) {
                    pet_parent_email_ET.setError(null);
                    firstName_ET.setError(null);
                    lastName_ET.setError(null);
                    pet_name_ET.setError(null);
                    pincode_ET.setError("Invalid Pincode !");
                    address_ET.setError(null);
                } else if (strPetParentAddress.isEmpty()) {
                    pet_parent_email_ET.setError(null);
                    firstName_ET.setError(null);
                    lastName_ET.setError(null);
                    pet_name_ET.setError(null);
                    pincode_ET.setError(null);
                    address_ET.setError("Enter Your Address!");
                } else {

                    RegistrationWithQrCodeParams registrationWithQrCodeParams = new RegistrationWithQrCodeParams();
                    registrationWithQrCodeParams.setPetName(strPetName);
                    registrationWithQrCodeParams.setPhoneNumber(Config.user_phone);
                    registrationWithQrCodeParams.setPetCategoryId(getStrSpnerItemPetNmId);
                    registrationWithQrCodeParams.setPetAgeId("0");
                    registrationWithQrCodeParams.setPetSizeId("0");
                    registrationWithQrCodeParams.setPetSexId(strSpnrSexId);
                    registrationWithQrCodeParams.setPetColorId(strSpnrColorId);
                    registrationWithQrCodeParams.setPetBreedId(strSpnrBreedId);
                    registrationWithQrCodeParams.setPetDateofBirth(strPetBirthDay);
                    registrationWithQrCodeParams.setAddress(strPetParentAddress + " ,Pincode " + strPetParentPincode);
                    registrationWithQrCodeParams.setVeterinarianUserId(veterinarianUserId);
                    RegistrationWithQrCodeRequest registrationWithQrCodeRequest = new RegistrationWithQrCodeRequest();
                    registrationWithQrCodeRequest.setData(registrationWithQrCodeParams);
                    petParentRegistrationUsingQRCode(registrationWithQrCodeRequest);

                }


                break;

            case R.id.back_arrow_IV:
                onBackPressed();
                break;
        }



    }

    private void petParentRegistrationUsingQRCode(RegistrationWithQrCodeRequest registrationWithQrCodeRequest) {
        Log.e("DATALOG", "check1=> " + methods.getRequestJson(registrationWithQrCodeRequest));
        methods.showCustomProgressBarDialog(this);
        ApiService<RegisterParentWithQRResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().petParentRegistrationUsingQRCode(Config.token,registrationWithQrCodeRequest), "ParentRegistrationUsingQRCode");

    }

    @Override
    public void onResponse(Response arg0, String key) {
        switch (key) {
            case "ParentRegistrationUsingQRCode":

                try {
                    methods.customProgressDismiss();
                    RegisterParentWithQRResponse registerParentWithQRResponse = (RegisterParentWithQRResponse) arg0.body();
                    Log.e("RegistrationQR", registerParentWithQRResponse.toString());

                    if (registerParentWithQRResponse.getResponse().getResponseCode().equals("109")) {

                        setResult(RESULT_OK);
                        Toast.makeText(this, "Pet Added", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Please Try again !", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "GetPetTypes":
                try {
                    methods.customProgressDismiss();
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
                        setPetSizeSpinner();
                    } else if (responseCode == 614) {
                        Toast.makeText(this, petSizeValueResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "GetPetAgeUnit":
                try {
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
        }

    }

    private void getPetAgeString(String DOB) {
        GetPetAgeParameter getPetAgeParameter = new GetPetAgeParameter();
        getPetAgeParameter.setDateOfBirth(DOB);
        GetPetAgeRequestData getPetAgeRequestData = new GetPetAgeRequestData();
        getPetAgeRequestData.setData(getPetAgeParameter);
        ApiService<GetPetAgeresponseData> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getPetAgeString(getPetAgeRequestData), "GetPetAgeString");
        Log.e("DAILOG", "getPetAgeString==>" + methods.getRequestJson(getPetAgeRequestData));
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

    private void setPetSizeSpinner() {
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, petSize);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        add_pet_size.setAdapter(aa);
        add_pet_size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                strSpnrSize = item;
                Log.d("spnerType", "" + strSpnrSize);
                strSpneSizeId = petSizeHashMap.get(strSpnrSize);
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

    private void setPetBreeSpinner() {
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, petBreed);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        add_pet_breed_dialog.setAdapter(aa);
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
//                    getPetSize();
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
    public void onError(Throwable t, String key) {

        methods.customProgressDismiss();
        Log.e("ERROR", t.getLocalizedMessage());
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