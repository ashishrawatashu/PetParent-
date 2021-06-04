package com.cynoteck.petofyparents.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cynoteck.petofyparents.PetParentSingleton;
import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.adapter.PetListHorizontalAdapter;
import com.cynoteck.petofyparents.api.ApiClient;
import com.cynoteck.petofyparents.api.ApiResponse;
import com.cynoteck.petofyparents.api.ApiService;
import com.cynoteck.petofyparents.parameter.appointmentParams.CreateAppointParams;
import com.cynoteck.petofyparents.parameter.appointmentParams.CreateAppointRequest;
import com.cynoteck.petofyparents.parameter.appointmentParams.UpdateAppointmentParams;
import com.cynoteck.petofyparents.parameter.appointmentParams.UpdateAppointmentRequest;
import com.cynoteck.petofyparents.parameter.getPetListRequest.GetPetListParams;
import com.cynoteck.petofyparents.parameter.getPetListRequest.GetPetListRequest;
import com.cynoteck.petofyparents.parameter.petReportsRequest.PetDataParams;
import com.cynoteck.petofyparents.parameter.petReportsRequest.PetDataRequest;
import com.cynoteck.petofyparents.response.appointmentResponse.AppointmentDetailsResponse;
import com.cynoteck.petofyparents.response.appointmentResponse.CreateAppointmentResponse;
import com.cynoteck.petofyparents.response.getPetReportsResponse.getPetListResponse.GetPetListResponse;
import com.cynoteck.petofyparents.response.getPetReportsResponse.getPetListResponse.PetList;
import com.cynoteck.petofyparents.utils.Config;
import com.cynoteck.petofyparents.utils.Methods;
import com.cynoteck.petofyparents.utils.OnItemClickListener;
import com.cynoteck.petofyparents.utils.RegisterRecyclerViewClickListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Response;

import static com.cynoteck.petofyparents.fragments.PetRegisterFragment.page;
import static com.cynoteck.petofyparents.fragments.PetRegisterFragment.pagelimit;
import static com.cynoteck.petofyparents.fragments.PetRegisterFragment.registerPetAdapter;
import static com.cynoteck.petofyparents.fragments.PetRegisterFragment.total_pets_TV;
import static com.cynoteck.petofyparents.fragments.ProfileFragment.petListHorizontalAdapter;

public class AddUpdateAppointmentActivity extends AppCompatActivity implements ApiResponse, View.OnClickListener, RegisterRecyclerViewClickListener, TextWatcher, OnItemClickListener {
    Button create_appointment_BT;
    TextView purpose_TV, rating_TV, vet_name_TV, vet_study_TV, vet_address_TV, consultation_fee_TV, pet_details_TV, visit_type_TV, appointment_headline, calenderTextView_dialog, select_time_TV, parent_TV, cancelOtpDialog;
    EditText select_purpose_ET;
    Methods methods;
    String appointment_duration,vet_fee, vet_study = "", vet_rating = "", vet_address = "", vet_name = "", isVedioCall = "false", vetUserId = "", currentTime = "", strResponseOtp = "", vet_image_url = "", petName = "", petSex = "", petAge = "", petId = "", id = "", appointmentID = "", userID = "", type = "", titleString = "", descriptionString = "", dateString = "", timeString = "", petParentString = "", petUniqueID = "";
    DatePickerDialog picker;
    Dialog otpDialog;
    TextInputLayout otp_TL;
    TextInputEditText pet_parent_otp;
    Button submit_parent_otp;
    ArrayList<String> purpose;
    SwitchCompat visit_type_SC;
    RelativeLayout add_pet_RL;
    MaterialCardView back_arrow_CV;
    RecyclerView pet_list_RV;
    //    ArrayList<PetList> profileList = new ArrayList<>();
    public static PetListHorizontalAdapter petListHorizontalAdapterForAppointment;
    BottomSheetDialog selectPurposeDialog;
    ProgressBar pet_list_progress_bar;
    Dialog appointment_successfully_dialog;
    ConstraintLayout select_purpose_RL;
    int toatalProgress = 0;
    private int ADD_PET = 1;

    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE = 200;
    Dialog phonePermissionDialog;
    boolean phoneDialog= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_appointment);
        cellphonePermission();
        methods = new Methods(this);
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        id = intent.getStringExtra("id");
        petId = intent.getStringExtra("pet_id");
        petParentString = intent.getStringExtra("petParent");
        vet_study = intent.getStringExtra("vet_study");
        vet_rating = intent.getStringExtra("vet_rating");
        vet_address = intent.getStringExtra("vet_address");
        vet_name = intent.getStringExtra("vet_name");
        vet_image_url = intent.getStringExtra("vet_image_url");
        vetUserId = intent.getStringExtra("vetUserId");
        vet_fee = intent.getStringExtra("vet_fee");
        appointment_duration = intent.getStringExtra("appointment_duration");
        init();
        purpose = new ArrayList<>();
        purpose.add("Vaccination");
        purpose.add("Deworming");
        purpose.add("Routine health check up");
        purpose.add("Skin, coat & hair related problems");
        purpose.add("Diarrhea, Vomitting, other stomach realted problems");
        purpose.add("Eye check up");
        purpose.add("Dental check up");
        purpose.add("Other");





        if (type.equals("add")) {
            currentTime = new SimpleDateFormat("hh:mm a").format(new Date());
            select_time_TV.setText(currentTime);
            rating_TV.setText(vet_rating);
            vet_name_TV.setText(vet_name);
            vet_address_TV.setText(vet_address);
            vet_study_TV.setText(vet_study);
            consultation_fee_TV.setText("₹ "+vet_fee);
            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            pet_list_RV.setLayoutManager(horizontalLayoutManager);
            petListHorizontalAdapterForAppointment = new PetListHorizontalAdapter(this, "AppointmentScreen", PetParentSingleton.getInstance().getArrayList(), this);
            if (!PetParentSingleton.getInstance().getArrayList().isEmpty()) {
                pet_list_progress_bar.setVisibility(View.GONE);
                add_pet_RL.setVisibility(View.VISIBLE);
                pet_list_RV.setAdapter(petListHorizontalAdapterForAppointment);
                petListHorizontalAdapterForAppointment.notifyDataSetChanged();
            } else {
                if (methods.isInternetOn()) {
                    pet_list_progress_bar.setVisibility(View.VISIBLE);
                    add_pet_RL.setVisibility(View.GONE);
                    getPetList();
                } else {

                    methods.DialogInternet();
                }

            }


        } else if (type.equals("update")) {
            GetPetListParams getPetListParams = new GetPetListParams();
            getPetListParams.setId(id);
            GetPetListRequest getPetListRequest = new GetPetListRequest();
            getPetListRequest.setData(getPetListParams);
            getAppointmentDeatils(getPetListRequest);
        }

    }

    private void cellphonePermission() {
        if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_READ_PHONE);
            return;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_PHONE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (phoneDialog) {
                        phonePermissionDialog.dismiss();
                        phoneDialog = false;
                        cellphonePermission();
                    }
                } else {
                    phoneDialog = false;
                    showStoragePermissionDialog();
                }

                return;
            }
        }
    }


    private void showStoragePermissionDialog() {
        phoneDialog = true;
        phonePermissionDialog = new Dialog(this);
        phonePermissionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        phonePermissionDialog.setCancelable(false);
        phonePermissionDialog.setContentView(R.layout.storage_permission_dialog);
        phonePermissionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Button grant_permission_BT = phonePermissionDialog.findViewById(R.id.grant_permission_BT);
        phonePermissionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        grant_permission_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cellphonePermission();
            }
        });
        phonePermissionDialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = phonePermissionDialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
    }
    private void getPetList() {
        PetDataParams getPetDataParams = new PetDataParams();
        getPetDataParams.setPageNumber(1);
        getPetDataParams.setPageSize(100);
        getPetDataParams.setSearch_Data("");
        PetDataRequest getPetDataRequest = new PetDataRequest();
        getPetDataRequest.setData(getPetDataParams);

        ApiService<GetPetListResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getPetList(Config.token, getPetDataRequest), "GetPetList");
        Log.e("DATALOG", "check1=> " + getPetDataRequest);


    }


    private void init() {
        rating_TV = findViewById(R.id.rating_TV);
        purpose_TV = findViewById(R.id.purpose_TV);
        pet_list_progress_bar = findViewById(R.id.pet_list_progress_bar);
        vet_name_TV = findViewById(R.id.vet_name_TV);
        vet_study_TV = findViewById(R.id.vet_study_TV);
        vet_address_TV = findViewById(R.id.vet_address_TV);
        consultation_fee_TV = findViewById(R.id.consultation_fee_TV);
        calenderTextView_dialog = findViewById(R.id.calenderTextView_dialog);
        select_time_TV = findViewById(R.id.select_time_TV);
        select_purpose_ET = findViewById(R.id.select_purpose_ET);
        create_appointment_BT = findViewById(R.id.create_appointment_BT);
        parent_TV = findViewById(R.id.parent_TV);
        add_pet_RL = findViewById(R.id.add_pet_RL);
        visit_type_TV = findViewById(R.id.visit_type_TV);
        visit_type_SC = findViewById(R.id.visit_type_SC);
        back_arrow_CV = findViewById(R.id.back_arrow_CV);
        pet_list_RV = findViewById(R.id.pet_list_RV);
        select_purpose_RL = findViewById(R.id.select_purpose_RL);
        select_purpose_RL.setOnClickListener(this);
        add_pet_RL.setOnClickListener(this);
        calenderTextView_dialog.setOnClickListener(this);
        back_arrow_CV.setOnClickListener(this);
        select_time_TV.setOnClickListener(this);
        create_appointment_BT.setOnClickListener(this);
        create_appointment_BT.setEnabled(false);
        visit_type_SC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isVedioCall = "true";
                    visit_type_TV.setText("Online Appointment");
                } else {
                    isVedioCall = "false";
                    visit_type_TV.setText("Clinic Visit");

                }
            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.add_pet_RL:
                Intent adNewIntent = new Intent(this, AddPetRegister.class);
                adNewIntent.putExtra("intent_from", "add");
                startActivityForResult(adNewIntent, ADD_PET);
                break;

            case R.id.back_arrow_CV:
                onBackPressed();
                break;

            case R.id.select_purpose_RL:
                showPurposeDialog();

                break;

            case R.id.create_appointment_BT:
                Log.e("user_id", Config.user_id);
                titleString = select_purpose_ET.getText().toString();
                dateString = calenderTextView_dialog.getText().toString();
                timeString = select_time_TV.getText().toString();
                String currentTimeDate = new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(new Date());
                String userGiveTimeDate = dateString + " " + timeString;
                if (petId.isEmpty()) {
                    Toast.makeText(this, "Please Select Pet ", Toast.LENGTH_SHORT).show();

                } else if (titleString.isEmpty()) {
                    select_purpose_ET.setError("Enter Purpose !");
                    Toast.makeText(this, "Enter Purpose !", Toast.LENGTH_SHORT).show();
                } else if (dateString.isEmpty()) {
                    Toast.makeText(this, "Please Select Date", Toast.LENGTH_SHORT).show();

                } else if (timeString.isEmpty()) {
                    Toast.makeText(this, "Please Select Time", Toast.LENGTH_SHORT).show();

                } /*else if (durationString.isEmpty()) {
                    Toast.makeText(this, "Please Select Duration", Toast.LENGTH_SHORT).show();

                }*/ else if (methods.checktimings(currentTimeDate, userGiveTimeDate) == false) {
                    AlertDialog alertDialog = new AlertDialog.Builder(com.cynoteck.petofyparents.activity.AddUpdateAppointmentActivity.this).create();
                    alertDialog.setTitle("warning");
                    alertDialog.setMessage("Appointment can not be created in a back date and time !");
                    alertDialog.setIcon(getDrawable(R.drawable.ic_baseline_warning));
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                } else {

                    if (type.equals("add")) {
                        CreateAppointParams createAppointParams = new CreateAppointParams();
                        createAppointParams.setUserId(Config.user_id);
                        createAppointParams.setDescription(descriptionString);
                        createAppointParams.setDuration(appointment_duration);
                        createAppointParams.setEventStartDate(dateString);
                        createAppointParams.setEventStartTime(timeString);
                        createAppointParams.setTitle(titleString);
                        createAppointParams.setPetid(petId.substring(0, petId.length() - 2));
                        createAppointParams.setVetId(vetUserId);
                        createAppointParams.setIsVideoCall(isVedioCall);

                        CreateAppointRequest createAppointRequest = new CreateAppointRequest();
                        createAppointRequest.setData(createAppointParams);
                        if (methods.isInternetOn()) {
                            createUpdateAppointment(createAppointRequest);

                        } else {
                            methods.DialogInternet();
                        }
                    } else {
                        UpdateAppointmentParams updateAppointmentParams = new UpdateAppointmentParams();
                        updateAppointmentParams.setUserId(userID);
                        updateAppointmentParams.setDescription(descriptionString);
                        updateAppointmentParams.setDuration(appointment_duration);
                        updateAppointmentParams.setEventStartDate(dateString);
                        updateAppointmentParams.setEventStartTime(timeString);
                        updateAppointmentParams.setTitle(titleString);
                        updateAppointmentParams.setId(id);
                        updateAppointmentParams.setPetId(petId);
                        updateAppointmentParams.setIsVideoCall(isVedioCall);

                        UpdateAppointmentRequest updateAppointmentRequest = new UpdateAppointmentRequest();
                        updateAppointmentRequest.setData(updateAppointmentParams);
                        if (methods.isInternetOn()) {
                            Log.d("updateRequest", updateAppointmentRequest.toString());
                            updateAppointment(updateAppointmentRequest);

                        } else {
                            methods.DialogInternet();
                        }

                    }
                }

                break;


            case R.id.calenderTextView_dialog:

                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                String displayValue = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                calenderTextView_dialog.setText(Config.changeDateFormat(displayValue));
                            }
                        }, year, month, day);
                picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                picker.show();
                break;


            case R.id.select_time_TV:
                Calendar c = Calendar.getInstance();

                if (type.equals("update")) {
                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
                    Date date = null;
                    try {
                        date = sdf.parse(select_time_TV.getText().toString());
                    } catch (ParseException e) {
                    }
                    c = Calendar.getInstance();
                    c.setTime(date);

                }

                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);


                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                String displayValue = hourOfDay + ":" + minute;
                                select_time_TV.setText(Config.changeTimePicker(displayValue));
                                timeString = Config.changeTimePicker(displayValue);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();

                break;


        }

    }

    private void showPurposeDialog() {
        selectPurposeDialog = new BottomSheetDialog(this);
        selectPurposeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        selectPurposeDialog.setCancelable(true);
        selectPurposeDialog.setContentView(R.layout.select_purpose_list);
        ImageView cross_IV = selectPurposeDialog.findViewById(R.id.cross_IV);
        RecyclerView select_purpose_RV = selectPurposeDialog.findViewById(R.id.select_purpose_RV);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        select_purpose_RV.setLayoutManager(linearLayoutManager);
        SelectPurposeAdapter selectPurposeAdapter = new SelectPurposeAdapter(this, purpose);
        select_purpose_RV.setAdapter(selectPurposeAdapter);
        selectPurposeAdapter.notifyDataSetChanged();
        selectPurposeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        cross_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPurposeDialog.dismiss();
            }
        });


        selectPurposeDialog.show();
    }

    private void getAppointmentDeatils(GetPetListRequest id) {
        ApiService<AppointmentDetailsResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getAppointmentsDetails(Config.token, id), "GetAppointmentDetails");

    }

    private void updateAppointment(UpdateAppointmentRequest updateAppointmentRequest) {
        methods.showCustomProgressBarDialog(this);
        ApiService<CreateAppointmentResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().updateAppointment(Config.token, updateAppointmentRequest), "UpdateAppointment");
    }

    private void createUpdateAppointment(CreateAppointRequest createAppointRequest) {
        methods.showCustomProgressBarDialog(this);
        Log.d("create", createAppointRequest.toString());
        ApiService<CreateAppointmentResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().createAppointment(Config.token, createAppointRequest), "CreateAppointment");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_PET) {
            if (resultCode == RESULT_OK) {
                PetList petList = new PetList();
                petList.setId(data.getStringExtra("pet_id"));
                petList.setPetUniqueId(data.getStringExtra("pet_unique_id"));
                petList.setPetProfileImageUrl(data.getStringExtra("pet_image_url"));
                petList.setPetBreed(data.getStringExtra("pet_breed"));
                petList.setPetAge(data.getStringExtra("pet_age"));
                petList.setPetParentName(data.getStringExtra("pet_parent"));
                petList.setPetSex(data.getStringExtra("pet_sex"));
                petList.setPetName(data.getStringExtra("pet_name"));
                petList.setPetCategory(data.getStringExtra("pet_category"));
                petList.setDateOfBirth(data.getStringExtra("pet_date_of_birth"));
                petList.setPetColor(data.getStringExtra("pet_color"));
                PetParentSingleton.getInstance().getArrayList().add(0, petList);
                total_pets_TV.setText("You have " + PetParentSingleton.getInstance().getArrayList().size() + " pets registered ");
                registerPetAdapter.notifyDataSetChanged();
                petListHorizontalAdapter.notifyDataSetChanged();
                petListHorizontalAdapterForAppointment.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onResponse(Response arg0, String key) {

        switch (key) {
            case "GetPetList":
                try {
                    GetPetListResponse getPetListResponse = (GetPetListResponse) arg0.body();
                    int responseCode = Integer.parseInt(getPetListResponse.getResponse().getResponseCode());
                    pet_list_progress_bar.setVisibility(View.GONE);
                    add_pet_RL.setVisibility(View.VISIBLE);

                    if (responseCode == 109) {
                        if (getPetListResponse.getData().getPetList().isEmpty()) {
//                            empty_IV.setVisibility(View.VISIBLE);
                            Toast.makeText(this, "No pet in register !", Toast.LENGTH_SHORT).show();
                        } else {
                            PetParentSingleton.getInstance().getArrayList().clear();
                            if (getPetListResponse.getData().getPetList().size() > 0) {
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
                                    petList.setPetCategoryId(getPetListResponse.getData().getPetList().get(i).getPetCategoryId());
                                    petList.setLastVisitEncryptedId(getPetListResponse.getData().getPetList().get(i).getLastVisitEncryptedId());
                                    petList.setPetBreed(getPetListResponse.getData().getPetList().get(i).getPetBreed());
                                    petList.setPetCategory(getPetListResponse.getData().getPetList().get(i).getPetCategory());
                                    petList.setPetColor(getPetListResponse.getData().getPetList().get(i).getPetColor());
                                    PetParentSingleton.getInstance().getArrayList().add(petList);
                                }

                                total_pets_TV.setText("You have " + PetParentSingleton.getInstance().getArrayList().size() + " pets registered ");
                                petListHorizontalAdapterForAppointment.notifyDataSetChanged();
                                pet_list_RV.setVisibility(View.VISIBLE);
                                registerPetAdapter.notifyDataSetChanged();
                                petListHorizontalAdapter.notifyDataSetChanged();
                            }
                        }


                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            case "UpdateAppointment":
                try {
                    Log.d("updateAppoint", arg0.body().toString());
                    CreateAppointmentResponse createAppointmentResponse = (CreateAppointmentResponse) arg0.body();
                    Log.d("updateAppoint", createAppointmentResponse.toString());
                    int responseCode = Integer.parseInt(createAppointmentResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        methods.customProgressDismiss();
                        Toast.makeText(this, "Appointment Updated Successfully", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        methods.customProgressDismiss();
                        Toast.makeText(this, "Appointment not update", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "CreateAppointment":
                try {
                    Log.d("createAppoint", arg0.body().toString());
                    CreateAppointmentResponse createAppointmentResponse = (CreateAppointmentResponse) arg0.body();
                    Log.d("createAppoint", createAppointmentResponse.toString());
                    int responseCode = Integer.parseInt(createAppointmentResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        methods.customProgressDismiss();
                        showAppointmentSuccessfully();
                    } else {
                        methods.customProgressDismiss();
                        Toast.makeText(this, "Appointment not created", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    methods.customProgressDismiss();
                    Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;


            case "GetAppointmentDetails":
                try {
                    Log.d("AppointDeatils", arg0.body().toString());
                    AppointmentDetailsResponse appointmentDetailsResponse = (AppointmentDetailsResponse) arg0.body();
                    Log.d("AppointDeatils", appointmentDetailsResponse.toString());
                    int responseCode = Integer.parseInt(appointmentDetailsResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        userID = appointmentDetailsResponse.getData().getUserId();
//                        pet_parent_TV.setText(appointmentDetailsResponse.getData().getPetParent().getFullName());
                        select_purpose_ET.setText(appointmentDetailsResponse.getData().getTitle());
                        dateString = appointmentDetailsResponse.getData().getEventStartDate().substring(0, 10);
                        Log.d("time", dateString);
                        calenderTextView_dialog.setText(Config.changeDateFormat(appointmentDetailsResponse.getData().getEventStartDate().substring(0, 10)));
                        appointment_duration = (appointmentDetailsResponse.getData().getDuration());
                        isVedioCall = appointmentDetailsResponse.getData().getIsVideoCall();
                        if (isVedioCall.equals("true")) {
                            visit_type_TV.setText("Online Appointment");
                            visit_type_SC.setChecked(true);
                        } else {
                            visit_type_TV.setText("Clinic Visit");
                            visit_type_SC.setChecked(false);

                        }
                        timeString = appointmentDetailsResponse.getData().getEventStartDate().substring(appointmentDetailsResponse.getData().getEventStartDate().length() - 8, appointmentDetailsResponse.getData().getEventStartDate().length());
                        Log.d("datee", timeString);
                        Log.d("datee", Config.changeTimeFormat(timeString));
                        select_time_TV.setText(Config.changeTimeFormat(timeString));


                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }
    }

    private void showAppointmentSuccessfully() {
        appointment_successfully_dialog = new Dialog(this);
        appointment_successfully_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        appointment_successfully_dialog.setCancelable(false);
        appointment_successfully_dialog.setContentView(R.layout.appointment_created_dialog);
        appointment_successfully_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        TextView back_to_appointments_TV = appointment_successfully_dialog.findViewById(R.id.back_to_appointments_TV);
        TextView information_TV = appointment_successfully_dialog.findViewById(R.id.information_TV);

        information_TV.setText("Someone from our team will shortly confirm your appointment with Doctor " + vet_name + " .");
        selectPurposeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        back_to_appointments_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appointment_successfully_dialog.dismiss();
//                onBackPressed();
                Config.fragmentNumber = 3;

                Intent dashBoardTOAppointmentIntent = new Intent(AddUpdateAppointmentActivity.this,DashBoardActivity.class);
                startActivity(dashBoardTOAppointmentIntent);
            }
        });

        appointment_successfully_dialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = appointment_successfully_dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    @Override
    public void onError(Throwable t, String key) {

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
    public void onProductClick(int position) {

    }

    public void otpDialog() {
        otpDialog = new Dialog(this);
        otpDialog.setContentView(R.layout.otp_layout);

        otp_TL = otpDialog.findViewById(R.id.otp_TL);
        pet_parent_otp = otpDialog.findViewById(R.id.pet_parent_otp);
        submit_parent_otp = otpDialog.findViewById(R.id.submit_parent_otp);
        cancelOtpDialog = otpDialog.findViewById(R.id.cancelOtpDialog);

        submit_parent_otp.setOnClickListener(this);
        cancelOtpDialog.setOnClickListener(this);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = otpDialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        otpDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        otpDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        otpDialog.show();

    }

    @Override
    public void onViewDetailsClick(int position) {
        petId = PetParentSingleton.getInstance().getArrayList().get(position).getId();
        toatalProgress = toatalProgress + 50;
        if (toatalProgress == 100) {
            create_appointment_BT.setBackgroundResource(R.drawable.blue_background_bg);
            create_appointment_BT.setEnabled(true);
        }
    }

    public class SelectPurposeAdapter extends RecyclerView.Adapter<SelectPurposeAdapter.MyViewHolder> {
        Context context;
        ArrayList<String> purposeList;

        public SelectPurposeAdapter(Context context, ArrayList<String> purposeList) {
            this.context = context;
            this.purposeList = purposeList;
        }

        @NonNull
        @Override
        public SelectPurposeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            SelectPurposeAdapter.MyViewHolder vh = new SelectPurposeAdapter.MyViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull SelectPurposeAdapter.MyViewHolder holder, int position) {
            holder.item_name_TV.setText(purposeList.get(position));
            holder.item_name_TV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toatalProgress = toatalProgress + 50;
                    if (toatalProgress == 100) {
                        create_appointment_BT.setBackgroundResource(R.drawable.blue_background_bg);
                        create_appointment_BT.setEnabled(true);
                    }
                    selectPurposeDialog.dismiss();
                    select_purpose_RL.setBackgroundResource(R.drawable.edit_text_active_state);
                    if (purposeList.get(position).equals("Other")) {
                        purpose_TV.setText(purposeList.get(position));
                        select_purpose_ET.setVisibility(View.VISIBLE);
                    } else {
                        purpose_TV.setText(purposeList.get(position));
                        select_purpose_ET.setText(purposeList.get(position));
                        select_purpose_ET.setVisibility(View.GONE);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return purposeList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView item_name_TV;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                item_name_TV = itemView.findViewById(R.id.item_name_TV);

            }
        }
    }

}


