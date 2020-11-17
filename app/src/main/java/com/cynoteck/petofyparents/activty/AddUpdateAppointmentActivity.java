package com.cynoteck.petofyparents.activty;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.api.ApiClient;
import com.cynoteck.petofyparents.api.ApiResponse;
import com.cynoteck.petofyparents.api.ApiService;
import com.cynoteck.petofyparents.parameter.appointmentParams.CreateAppointParams;
import com.cynoteck.petofyparents.parameter.appointmentParams.CreateAppointRequest;
import com.cynoteck.petofyparents.parameter.appointmentParams.UpdateAppointmentParams;
import com.cynoteck.petofyparents.parameter.appointmentParams.UpdateAppointmentRequest;
import com.cynoteck.petofyparents.parameter.getPetListRequest.GetPetListParams;
import com.cynoteck.petofyparents.parameter.getPetListRequest.GetPetListRequest;
import com.cynoteck.petofyparents.parameter.newPetEntryParams.NewPetParams;
import com.cynoteck.petofyparents.parameter.newPetEntryParams.NewPetRequest;
import com.cynoteck.petofyparents.parameter.otpRequest.SendOtpRequest;
import com.cynoteck.petofyparents.parameter.petReportsRequest.PetDataParams;
import com.cynoteck.petofyparents.parameter.petReportsRequest.PetDataRequest;
import com.cynoteck.petofyparents.response.appointmentResponse.AppointmentDetailsResponse;
import com.cynoteck.petofyparents.response.appointmentResponse.CreateAppointmentResponse;
import com.cynoteck.petofyparents.response.getPetParentResponse.GetPetParentListData;
import com.cynoteck.petofyparents.response.getPetParentResponse.GetPetParentResponse;
import com.cynoteck.petofyparents.response.getPetReportsResponse.getPetListResponse.GetPetListResponse;
import com.cynoteck.petofyparents.response.newPetResponse.NewPetRegisterResponse;
import com.cynoteck.petofyparents.response.otpResponse.OtpResponse;
import com.cynoteck.petofyparents.utils.Config;
import com.cynoteck.petofyparents.utils.Methods;
import com.cynoteck.petofyparents.utils.RegisterRecyclerViewClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;

import retrofit2.Response;

public class AddUpdateAppointmentActivity extends AppCompatActivity implements ApiResponse, View.OnClickListener, RegisterRecyclerViewClickListener, TextWatcher {
    Button create_appointment_BT;
    ImageView appointment_headline_back,new_pet_search;
    TextView appointment_headline ,calenderTextViewAppointDt ,time_TV,parent_TV,cancelOtpDialog,add_new_pet;
    EditText title_ET, duration_TV, description_ET;
    AutoCompleteTextView pet_parent_TV;
    LinearLayout pet_search_layout;
    Methods methods;
    ArrayList<GetPetParentListData> petParent = new ArrayList<>();
    Dialog petParentDilog;
    String vetUserId="", currentTime="", strResponseOtp="",petParentContactNumber="",petName="",petSex="",petAge="",petId="",id="",appointmentID="",userID="", type="", titleString="",descriptionString="",dateString="",timeString ="",durationString="",petParentString="", petUniqueID="";
    DatePickerDialog picker;
    TimePicker timePicker;
    ArrayList<String> petUniueId=null;
    HashMap<String,String> petExistingSearch;
    Dialog otpDialog;
    TextInputLayout mobile_numberTL,otp_TL;
    TextInputEditText pet_parent_mobile_number, pet_parent_otp;
    Button submit_parent_otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_appointment);
        methods = new Methods(this);
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        id = intent.getStringExtra("id");
        petId = intent.getStringExtra("pet_id");
        petParentString = intent.getStringExtra("petParent");
        vetUserId = intent.getStringExtra("vetUserId");
        init();
        if (type.equals("add")){
            appointment_headline.setText("ADD APPOINTMENT");
            create_appointment_BT.setText("CREATE APPOINTMENT");
            currentTime = new SimpleDateFormat("hh:mm a").format(new Date());
            time_TV.setText(currentTime);
            duration_TV.setText("15");
        }else if (type.equals("update")){
            pet_search_layout.setVisibility(View.INVISIBLE);
            add_new_pet.setVisibility(View.INVISIBLE);
            parent_TV.setVisibility(View.VISIBLE);
            parent_TV.setText(petParentString);
            duration_TV.setFocusable(false);
            appointment_headline.setText("EDIT APPOINTMENT");
            create_appointment_BT.setText("UPDATE APPOINTMENT");
            GetPetListParams  getPetListParams = new GetPetListParams();
            getPetListParams.setId(id);
            GetPetListRequest getPetListRequest = new GetPetListRequest();
            getPetListRequest.setData(getPetListParams);
            getAppointmentDeatils(getPetListRequest);
        }


        pet_parent_TV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                //... your stuff
                Log.d("akkakaka",""+petUniueId.get(position));
                String value=petExistingSearch.get(pet_parent_TV.getText().toString());
                Log.d("kakakka",""+value);
                StringTokenizer st = new StringTokenizer(value, ",");
                String PetUniqueId = st.nextToken();
                String PetName = st.nextToken();
                String PetParentName = st.nextToken();
                String PetSex = st.nextToken();
                String PetAge = st.nextToken();
                petId = st.nextToken();
                String pet_DOB = st.nextToken();
                userID = st.nextToken();
                Log.d("ppppp",""+PetUniqueId+" "+PetName+" "+PetParentName+" "+PetSex+" "+PetAge+" "+petId+" "+pet_DOB+" "+userID);

            }
        });

    }


    private void getPetList() {
            PetDataParams getPetDataParams = new PetDataParams();
            getPetDataParams.setPageNumber(1);
            getPetDataParams.setPageSize(10);
            getPetDataParams.setSearch_Data("");
            PetDataRequest getPetDataRequest = new PetDataRequest();
            getPetDataRequest.setData(getPetDataParams);

            ApiService<GetPetListResponse> service = new ApiService<>();
            service.get( this, ApiClient.getApiInterface().getPetList(Config.token,getPetDataRequest), "GetPetList");
            Log.e("DATALOG","check1=> "+getPetDataRequest);



    }


    private void init() {
        appointment_headline_back = findViewById(R.id.appointment_headline_back);
        appointment_headline = findViewById(R.id.appointment_headline);
        calenderTextViewAppointDt = findViewById(R.id.calenderTextViewAppointDt);
        time_TV = findViewById(R.id.time_TV);
        title_ET = findViewById(R.id.title_ET);
        duration_TV = findViewById(R.id.duration_TV);
        description_ET = findViewById(R.id.description_ET);
        pet_parent_TV = findViewById(R.id.pet_parent_ACTV);
        create_appointment_BT=findViewById(R.id.create_appointment_BT);
        parent_TV = findViewById(R.id.parent_TV);
        add_new_pet=findViewById(R.id.add_new_pet);
        pet_search_layout= findViewById(R.id.pet_search_layout);

        add_new_pet.setOnClickListener(this);
        appointment_headline_back.setOnClickListener(this);
        create_appointment_BT.setOnClickListener(this);
        calenderTextViewAppointDt.setOnClickListener(this);
        time_TV.setOnClickListener(this);
        pet_parent_TV.addTextChangedListener(this);
        pet_parent_TV.addTextChangedListener(new TextWatcher() {
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
                if (methods.isInternetOn()){
                    searchPet(value);
                }else {
                    methods.DialogInternet();
                }
            }
        });


    }

    private void searchPet(String prefix) {
        PetDataParams getPetDataParams = new PetDataParams();
        getPetDataParams.setPageNumber(0);
        getPetDataParams.setPageSize(10);
        getPetDataParams.setSearch_Data(prefix);
        PetDataRequest getPetDataRequest = new PetDataRequest();
        getPetDataRequest.setData(getPetDataParams);

        ApiService<GetPetListResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getPetList(Config.token,getPetDataRequest), "SearchPet");
        Log.e("DATALOG","check1=> "+getPetDataRequest);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.add_new_pet:
                Intent adNewIntent = new Intent(this,AddPetRegister.class);
                adNewIntent.putExtra("appointment","appointment");
                startActivityForResult(adNewIntent, 1);
                break;
            case R.id.appointment_headline_back:
                onBackPressed();
                break;

            case R.id.pet_parent_ACTV:
//                openParentDiloag();
                break;

            case R.id.create_appointment_BT:
                Log.e("user_id",Config.user_id);
                titleString = title_ET.getText().toString();
//                petParentString =pet_parent_TV.getText().toString();
                dateString = calenderTextViewAppointDt.getText().toString();
                timeString = time_TV.getText().toString();
                durationString=duration_TV.getText().toString();
                descriptionString=description_ET.getText().toString();

                String currentTimeDate = new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(new Date());
                String userGiveTimeDate=dateString+" "+timeString;

                if (titleString.isEmpty()){
                    title_ET.setError("Enter Title !");
                    description_ET.setError(null);

                }/*else if (userID.isEmpty()){
                    Toast.makeText(this, "Please Select Pet ", Toast.LENGTH_SHORT).show();

                }*/else if (dateString.isEmpty()){
                    Toast.makeText(this, "Please Select Date", Toast.LENGTH_SHORT).show();

                }else if (timeString.isEmpty()){
                    Toast.makeText(this, "Please Select Time", Toast.LENGTH_SHORT).show();

                }else if (durationString.isEmpty()){
                    Toast.makeText(this, "Please Select Duration", Toast.LENGTH_SHORT).show();

                }
               else if(methods.checktimings(currentTimeDate,userGiveTimeDate)==false)
               {
                   AlertDialog alertDialog = new AlertDialog.Builder(com.cynoteck.petofyparents.activty.AddUpdateAppointmentActivity.this).create();
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
               }
                else {

                    if (type.equals("add")){
                        CreateAppointParams createAppointParams = new CreateAppointParams();
                        createAppointParams.setUserId(userID);
                        createAppointParams.setDescription(descriptionString);
                        createAppointParams.setDuration(durationString);
                        createAppointParams.setEventStartDate(dateString);
                        createAppointParams.setEventStartTime(timeString);
                        createAppointParams.setTitle(titleString);
                        createAppointParams.setPetid(petId.substring(0,petId.length()-2));
                        createAppointParams.setVetId(vetUserId);
                        CreateAppointRequest createAppointRequest = new CreateAppointRequest();
                        createAppointRequest.setData(createAppointParams);
                        if (methods.isInternetOn()){
                            createUpdateAppointment(createAppointRequest);

                        }else {
                            methods.DialogInternet();
                        }
                    }else {
                        UpdateAppointmentParams updateAppointmentParams = new UpdateAppointmentParams();
                        updateAppointmentParams.setUserId(userID);
                        updateAppointmentParams.setDescription(descriptionString);
                        updateAppointmentParams.setDuration(durationString);
                        updateAppointmentParams.setEventStartDate(dateString);
                        updateAppointmentParams.setEventStartTime(timeString);
                        updateAppointmentParams.setTitle(titleString);
                        updateAppointmentParams.setId(id);
                        updateAppointmentParams.setPetId(petId);
                        UpdateAppointmentRequest  updateAppointmentRequest = new UpdateAppointmentRequest();
                        updateAppointmentRequest.setData(updateAppointmentParams);
                        if (methods.isInternetOn()){
                            Log.d("updateRequest",updateAppointmentRequest.toString());
                            updateAppointment(updateAppointmentRequest);

                        }else {
                            methods.DialogInternet();
                        }

                    }
                }

                break;


            case R.id.calenderTextViewAppointDt:

                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                                String displayValue = dayOfMonth + "/" + (monthOfYear+1) + "/" + year;
                                calenderTextViewAppointDt.setText(Config.changeDateFormat(displayValue));
                            }
                        }, year, month, day);
                picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                picker.show();
                break;


            case R.id.time_TV:
                Calendar c = Calendar.getInstance();

                if(type.equals("update"))
                {
                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
                    Date date = null;
                    try {
                        date = sdf.parse(time_TV.getText().toString());
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
                                String displayValue = hourOfDay+":"+ minute;
                                time_TV.setText(Config.changeTimePicker(displayValue));
                                timeString = Config.changeTimePicker(displayValue);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();

                break;



        }

    }

    private void clearSearch() {
        pet_parent_TV.getText().clear();
//        search_boxRL.setVisibility(View.GONE);
//        back_arrow_IV_new_entry.setVisibility(View.GONE);
//        staff_headline_TV.setVisibility(View.VISIBLE);
        InputMethodManager imm1 = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm1.hideSoftInputFromWindow(pet_parent_TV.getWindowToken(), 0);
    }

    private void getAppointmentDeatils(GetPetListRequest id) {
        ApiService<AppointmentDetailsResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getAppointmentsDetails(Config.token,id), "GetAppointmentDetails");

    }

    private void getPetParent() {
        ApiService<GetPetParentResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getPetParentList(Config.token), "GetPetParent");
    }

    private void updateAppointment(UpdateAppointmentRequest updateAppointmentRequest) {
        methods.showCustomProgressBarDialog(this);
        ApiService<CreateAppointmentResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().updateAppointment(Config.token,updateAppointmentRequest), "UpdateAppointment");
    }

    private void createUpdateAppointment(CreateAppointRequest createAppointRequest) {
        methods.showCustomProgressBarDialog(this);
        Log.d("create",createAppointRequest.toString());
        ApiService<CreateAppointmentResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().createAppointment(Config.token,createAppointRequest), "CreateAppointment");
    }
//    private void chkVetInregister(InPetRegisterRequest inPetRegisterRequest) {
//        ApiService<InPetVeterianResponse> service = new ApiService<>();
//        service.get( this, ApiClient.getApiInterface().checkPetInVetRegister(Config.token,inPetRegisterRequest), "CheckPetInVetRegister");
//        Log.e("DATALOG","check1=> "+inPetRegisterRequest);
//    }

    private void sendotpUsingMobileNumber(SendOtpRequest sendOtpRequest) {
        ApiService<OtpResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().senOtp(Config.token,sendOtpRequest), "SendOtp");
        Log.e("DATALOG","check1=> "+sendOtpRequest);
    }

    private void addNewRegisterPet(NewPetRequest newPetRequest) {
        ApiService<NewPetRegisterResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().addPetToRegister(Config.token,newPetRequest), "AddPetToRegister");
        Log.e("DATALOG","check1=> "+newPetRequest);
    }
//    private void openParentDiloag() {
//
//        petParentDilog = new Dialog(this);
//        petParentDilog.setContentView(R.layout.pet_parent_dilog);
//        RecyclerView pet_parent_list_recycler=petParentDilog.findViewById(R.id.pet_parent_list_recycler);
//        EditText ed_parent=petParentDilog.findViewById(R.id.ed_parent);
//
//        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        pet_parent_list_recycler.setLayoutManager(layoutManager);
//        petParentAdapter  = new PetParentAdapter(this,petParent,this);
//        pet_parent_list_recycler.setAdapter(petParentAdapter);
//        petParentAdapter.notifyDataSetChanged();
//
//        ed_parent.addTextChangedListener(this);
//
//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        Window window = petParentDilog.getWindow();
//        lp.copyFrom(window.getAttributes());
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
//        window.setAttributes(lp);
//        petParentDilog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        petParentDilog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
//        petParentDilog.show();
//
//
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                pet_search_layout.setVisibility(View.INVISIBLE);
                parent_TV.setVisibility(View.VISIBLE);
                petId = data.getStringExtra("petId");
                userID = data.getStringExtra("userId");
                Log.d("userId",userID.toString());
                petUniqueID = data.getStringExtra("uniqueId");
                petParentString = data.getStringExtra("parent");
                petSex= data.getStringExtra("sex");
                petAge = data.getStringExtra("age");
                petName = data.getStringExtra("petName");
                parent_TV.setText(petUniqueID+":-"+petName+"("+petSex+","+petParentString+")");

            }
        }
    }

    @Override
    public void onResponse(Response arg0, String key) {

        switch (key){
            case "SearchPet":
                try {
                    GetPetListResponse getPetListResponse = (GetPetListResponse) arg0.body();
                    Log.d("GetPetList", getPetListResponse.toString());
                    int responseCode = Integer.parseInt(getPetListResponse.getResponse().getResponseCode());

                    if (responseCode == 109) {
                        petUniueId = new ArrayList<>();
                        petExistingSearch = new HashMap<>();
                        for (int i = 0; i < getPetListResponse.getData().getPetList().size(); i++) {
                            petUniueId.add(getPetListResponse.getData().getPetList().get(i).getPetUniqueId() + ":- "
                                    + getPetListResponse.getData().getPetList().get(i).getPetName() + "(" + getPetListResponse.getData().getPetList().get(i).getPetSex() + "," + getPetListResponse.getData().getPetList().get(i).getPetParentName() + ")");

                            petExistingSearch.put(getPetListResponse.getData().getPetList().get(i).getPetUniqueId() + ":- "
                                            + getPetListResponse.getData().getPetList().get(i).getPetName() + "(" + getPetListResponse.getData().getPetList().get(i).getPetSex() + "," + getPetListResponse.getData().getPetList().get(i).getPetParentName() + ")",
                                    getPetListResponse.getData().getPetList().get(i).getPetUniqueId() + ","
                                            + getPetListResponse.getData().getPetList().get(i).getPetName() + ","
                                            + getPetListResponse.getData().getPetList().get(i).getPetParentName() + ","
                                            + getPetListResponse.getData().getPetList().get(i).getPetSex() + ","
                                            + getPetListResponse.getData().getPetList().get(i).getPetAge() + ","
                                            + getPetListResponse.getData().getPetList().get(i).getId() + ","
                                            + getPetListResponse.getData().getPetList().get(i).getDateOfBirth() + ","
                                            + getPetListResponse.getData().getPetList().get(i).getUserId());
                        }

                        Log.d("jajajajjaja", "" + petUniueId.size() + " \n" + petUniueId.toString());
                        Log.d("lllllllllll", "" + petExistingSearch.size() + " \n" + petExistingSearch);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                (this, android.R.layout.simple_list_item_1, petUniueId);
                        pet_parent_TV.setThreshold(1);//will start working from first character
                        pet_parent_TV.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
                        pet_parent_TV.setTextColor(Color.BLACK);
                    }
                    else
                    {
                        methods.customProgressDismiss();
                        if(getPetListResponse.getResponse().getResponseMessage().equals("Invalid token."))
                        {
                            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                            alertDialog.setTitle("Warning!!");
                            alertDialog.setMessage("Your Session Expired. Please, Logout and Login Again.");
                            alertDialog.setIcon(this.getDrawable(R.drawable.ic_baseline_warning));
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "LOGOUT",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            SharedPreferences preferences =getSharedPreferences("userdetails",0);
                                            SharedPreferences.Editor editor = preferences.edit();
                                            editor.clear();
                                            editor.apply();
                                            startActivity(new Intent(AddUpdateAppointmentActivity.this, LoginActivity.class));
                                            finish();
                                        }
                                    });
                            alertDialog.setCancelable(false);
                            alertDialog.setCanceledOnTouchOutside(false);
                            alertDialog.show();
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "GetPetParent":
                try {
                    Log.d("GetPetParent",arg0.body().toString());
                    GetPetParentResponse getPetParentResponse = (GetPetParentResponse) arg0.body();
                    Log.d("GetPetParent",getPetParentResponse.toString());
                    int responseCode = Integer.parseInt(getPetParentResponse.getResponse().getResponseCode());
                    if (responseCode==109) {
                        petParent = getPetParentResponse.getData();
//                        pet_parent_TV.setOnClickListener(this);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
                break;

            case "UpdateAppointment":
                try {
                    Log.d("updateAppoint",arg0.body().toString());
                    CreateAppointmentResponse createAppointmentResponse  = (CreateAppointmentResponse) arg0.body();
                    Log.d("updateAppoint",createAppointmentResponse.toString());
                    int responseCode = Integer.parseInt(createAppointmentResponse.getResponse().getResponseCode());
                    if (responseCode==109) {
                        methods.customProgressDismiss();
                        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
                        Config.backCall="hit";
                        onBackPressed();
                    }
                    else
                    {
                        methods.customProgressDismiss();
                        Toast.makeText(this, "Appointment not update", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
                break;


            case "CreateAppointment":
                try {
                    Log.d("createAppoint",arg0.body().toString());
                    CreateAppointmentResponse createAppointmentResponse  = (CreateAppointmentResponse) arg0.body();
                    Log.d("createAppoint",createAppointmentResponse.toString());
                    int responseCode = Integer.parseInt(createAppointmentResponse.getResponse().getResponseCode());
                    if (responseCode==109) {
                        methods.customProgressDismiss();
                        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                    else
                    {
                        methods.customProgressDismiss();
                        Toast.makeText(this, "Appointment not created", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
                break;



            case "GetAppointmentDetails":
                try {
                    Log.d("AppointDeatils",arg0.body().toString());
                    AppointmentDetailsResponse appointmentDetailsResponse  = (AppointmentDetailsResponse) arg0.body();
                    Log.d("AppointDeatils",appointmentDetailsResponse.toString());
                    int responseCode = Integer.parseInt(appointmentDetailsResponse.getResponse().getResponseCode());
                    if (responseCode==109) {
                        userID = appointmentDetailsResponse.getData().getUserId();
//                        pet_parent_TV.setText(appointmentDetailsResponse.getData().getPetParent().getFullName());
                        title_ET.setText(appointmentDetailsResponse.getData().getTitle());
                        dateString = appointmentDetailsResponse.getData().getEventStartDate().substring(0,10);
                        Log.d("time",dateString);
                        calenderTextViewAppointDt.setText(Config.changeDateFormat(appointmentDetailsResponse.getData().getEventStartDate().substring(0,10)));
                        description_ET.setText(appointmentDetailsResponse.getData().getDescription());
                        duration_TV.setText(appointmentDetailsResponse.getData().getDuration());

                        timeString = appointmentDetailsResponse.getData().getEventStartDate().substring(appointmentDetailsResponse.getData().getEventStartDate().length()-8,appointmentDetailsResponse.getData().getEventStartDate().length());
                        Log.d("datee",timeString);
                        Log.d("datee",Config.changeTimeFormat(timeString));
                        time_TV.setText(Config.changeTimeFormat(timeString));


                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
                break;

            case "GetPetList":
                try {
                    GetPetListResponse getPetListResponse = (GetPetListResponse) arg0.body();
                    Log.d("GetPetList", getPetListResponse.toString());
                    int responseCode = Integer.parseInt(getPetListResponse.getResponse().getResponseCode());

                    if (responseCode== 109){
                        petUniueId=new ArrayList<>();
                        petExistingSearch=new HashMap<>();
                        for(int i=0;i<getPetListResponse.getData().getPetList().size();i++){
                            petUniueId.add(getPetListResponse.getData().getPetList().get(i).getPetUniqueId()+":- "
                                    +getPetListResponse.getData().getPetList().get(i).getPetName()+"("+getPetListResponse.getData().getPetList().get(i).getPetSex()+","+getPetListResponse.getData().getPetList().get(i).getPetParentName()+")");
                            petExistingSearch.put(getPetListResponse.getData().getPetList().get(i).getPetUniqueId()+":- "
                                            +getPetListResponse.getData().getPetList().get(i).getPetName()+"("+getPetListResponse.getData().getPetList().get(i).getPetSex()+","+getPetListResponse.getData().getPetList().get(i).getPetParentName()+")",
                                            getPetListResponse.getData().getPetList().get(i).getPetUniqueId()+","
                                            +getPetListResponse.getData().getPetList().get(i).getPetName()+","
                                            +getPetListResponse.getData().getPetList().get(i).getPetParentName()+","
                                            +getPetListResponse.getData().getPetList().get(i).getId()+","
                                            +getPetListResponse.getData().getPetList().get(i).getPetAge()+","
                                            +getPetListResponse.getData().getPetList().get(i).getUserId());
                        }

                        Log.d("jajajajjaja",""+petUniueId.size()+" \n"+ petUniueId.toString());
                        Log.d("lllllllllll",""+petExistingSearch.size()+" \n"+ petExistingSearch);

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                (this,android.R.layout.simple_list_item_1,petUniueId);
                        pet_parent_TV.setThreshold(1);//will start working from first character
                        pet_parent_TV.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
                        pet_parent_TV.setTextColor(Color.BLACK);
                    }

                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                
                break;

//            case "CheckPetInVetRegister":
//                try {
//                    Log.d("CheckPetInVetRegister",arg0.body().toString());
//                    InPetVeterianResponse inPetVeterianResponse = (InPetVeterianResponse) arg0.body();
//                    int responseCode = Integer.parseInt(inPetVeterianResponse.getResponse().getResponseCode());
//                    if (responseCode== 109){
//                        if(!inPetVeterianResponse.getData().getPetId().equals("0"))
//                        {
//                            petId=inPetVeterianResponse.getData().getPetId();
//                            petParentContactNumber=inPetVeterianResponse.getData().getPetParentContactNumber();
//                            String actualNumber=petParentContactNumber.replaceAll("-", "");
//                            SendOtpRequest sendOtpRequest = new SendOtpRequest();
//                            SendOtpParameter data = new SendOtpParameter();
//                            data.setPhoneNumber(actualNumber);
//                            data.setEmailId("");
//                            sendOtpRequest.setData(data);
//                            if (methods.isInternetOn()) {
//                                sendotpUsingMobileNumber(sendOtpRequest);
//                            } else {
//                                methods.DialogInternet();
//                            }
//                        }
//                        else
//                        {
//                            Toast.makeText(this, "Invalid pet unique Id", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }else if (responseCode==614){
//                        Toast.makeText(this, inPetVeterianResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
//                    }else {
//                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                catch(Exception e) {
//                    e.printStackTrace();
//                }
//                break;
            case "SendOtp":
                try {
                    Log.d("SendOtp",arg0.body().toString());
                    OtpResponse otpResponse = (OtpResponse) arg0.body();
                    int responseCode = Integer.parseInt(otpResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        if(otpResponse.getData().getSuccess().equals("true"))
                        {
                            strResponseOtp=otpResponse.getData().getOtp();
                            otpDialog();
                        }
                        else
                        {
                            Toast.makeText(this, "Invalid Mobile Number", Toast.LENGTH_SHORT).show();
                        }
                    }else if (responseCode==614){
                        Toast.makeText(this, otpResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;

            case "AddPetToRegister":
                try {
                    Log.d("SendOtp",arg0.body().toString());
                    NewPetRegisterResponse newPetRegisterResponse = (NewPetRegisterResponse) arg0.body();
                    int responseCode = Integer.parseInt(newPetRegisterResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        otpDialog.dismiss();
                        //Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
                        String sexName="";
                        if(newPetRegisterResponse.getData().getSexId().equals("2.0"))
                            sexName="Female";
                        else
                            sexName="Male";
                        getPetList();
                        pet_parent_TV.setText(newPetRegisterResponse.getData().getPetUniqueId()+":-"+newPetRegisterResponse.getData().getPetName()+"("+sexName+","+newPetRegisterResponse.getData().getPetParentName()+")");
                        userID = newPetRegisterResponse.getData().getUserId();
                        petId = newPetRegisterResponse.getData().getId();

                    }else if (responseCode==614){
                        Toast.makeText(this, newPetRegisterResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
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
        petParentDilog.dismiss();
        userID=petParent.get(position).getUserId();
//        pet_parent_TV.setText(petParent.get(position).getFirstName()+" "+petParent.get(position).getLastName());


    }
    public void otpDialog()
    {
        otpDialog=new Dialog(this);
        otpDialog.setContentView(R.layout.otp_layout);

        otp_TL=otpDialog.findViewById(R.id.otp_TL);
        pet_parent_otp=otpDialog.findViewById(R.id.pet_parent_otp);
        submit_parent_otp=otpDialog.findViewById(R.id.submit_parent_otp);
        cancelOtpDialog=otpDialog.findViewById(R.id.cancelOtpDialog);

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
}
