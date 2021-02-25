package com.cynoteck.petofyparents.activty;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.api.ApiClient;
import com.cynoteck.petofyparents.api.ApiResponse;
import com.cynoteck.petofyparents.api.ApiService;
import com.cynoteck.petofyparents.parameter.petReportsRequest.PetClinicVisitDetailsRequest;
import com.cynoteck.petofyparents.parameter.petReportsRequest.PetClinicVistsDetailsParams;
import com.cynoteck.petofyparents.response.getPetReportsResponse.AddUpdateDeleteClinicVisitResponse;
import com.cynoteck.petofyparents.response.getXRayReports.getXRayReportDetailsResponse.GetXRayReportDeatilsResponse;
import com.cynoteck.petofyparents.utils.Config;
import com.cynoteck.petofyparents.utils.Methods;

import retrofit2.Response;

public class XRayReportDeatilsActivity extends AppCompatActivity implements ApiResponse, View.OnClickListener {

    TextView nature_of_visit_textView,test_date_textView,Result_textView,recommended_follow_up_textView,recommended_follow_up_date_textView;
    Button deleteReport_BT;
    ImageView back_arrow_IV;
    TextView pet_name_TV,pet_sex_TV,pet_id_TV,pet_owner_name_TV,pet_owner_phone_no_TV;
    String nature="",date_of_test="",follow_up="",result="",pet_unique_id, pet_name,pet_sex, pet_owner_name,pet_owner_contact,pet_id ,report_type_id,type,follow_up_date;

    Methods methods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_x_ray_report_deatils);

        methods = new Methods(this);
        getIntentData();
        init();
        setdataInFields();
        //getXRayReportDeatilsDeatils();
        deleteReport_BT.setVisibility(View.GONE);
    }

    private void setdataInFields() {
        pet_name_TV.setText(pet_name);
        pet_sex_TV.setText(pet_sex);
        pet_id_TV.setText(pet_unique_id);
        pet_owner_name_TV.setText(pet_owner_name);
        pet_owner_phone_no_TV.setText(pet_owner_contact);
        recommended_follow_up_date_textView.setText(follow_up_date);
        recommended_follow_up_textView.setText(follow_up);
        nature_of_visit_textView.setText(nature);
        test_date_textView.setText(date_of_test);
        Result_textView.setText(result);

    }

    private void init() {

        nature_of_visit_textView = findViewById(R.id.nature_of_visit_textView);
        test_date_textView = findViewById(R.id.test_date_textView);
        Result_textView = findViewById(R.id.Result_textView);
        recommended_follow_up_textView = findViewById(R.id.recommended_follow_up_textView);
        recommended_follow_up_date_textView = findViewById(R.id.recommended_follow_up_date_textView);

        pet_name_TV = findViewById(R.id.pet_name_TV);
        pet_sex_TV = findViewById(R.id.pet_sex_TV);
        pet_id_TV = findViewById(R.id.pet_id_TV);
        pet_owner_name_TV = findViewById(R.id.pet_owner_name_TV);
        pet_owner_phone_no_TV = findViewById(R.id.pet_owner_phone_no_TV);
        deleteReport_BT = findViewById(R.id.deleteReport_BT);
        back_arrow_IV = findViewById(R.id.back_arrow_IV);

        back_arrow_IV.setOnClickListener(this);
        deleteReport_BT.setOnClickListener(this);
    }

    private void getIntentData() {
        Intent extras = getIntent();
        pet_id = extras.getExtras().getString("pet_id");
        pet_owner_contact = extras.getExtras().getString("pet_owner_contact");
        pet_owner_name = extras.getExtras().getString("pet_owner_name");
        pet_sex = extras.getExtras().getString("pet_sex");
        pet_name = extras.getExtras().getString("pet_name");
        pet_unique_id = extras.getExtras().getString("pet_unique_id");
        report_type_id=extras.getExtras().getString("id");
        follow_up_date = extras.getExtras().getString("follow_up_date");
        follow_up = extras.getExtras().getString("follow_up");
        nature = extras.getExtras().getString("nature");
        date_of_test = extras.getExtras().getString("date_of_test");
        result = extras.getExtras().getString("result");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.back_arrow_IV:
                onBackPressed();
                break;

            case R.id.deleteReport_BT:
                Log.d("Add Anotheer Veterian","vet");
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Are you sure?");
                alertDialog.setMessage("Do You Want to Delete This Report ?");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (methods.isInternetOn()) {
                                    deleteXRay();
                                } else {
                                    methods.DialogInternet();
                                }
                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                alertDialog.show();

                break;
        }

    }

    private void getXRayReportDeatilsDeatils() {

        PetClinicVistsDetailsParams petClinicVistsDetailsParams = new PetClinicVistsDetailsParams();
        petClinicVistsDetailsParams.setId(report_type_id.substring(0,report_type_id.length()-2));
        PetClinicVisitDetailsRequest petClinicVisitDetailsRequest = new PetClinicVisitDetailsRequest();
        petClinicVisitDetailsRequest.setData(petClinicVistsDetailsParams);
        Log.d("GetXRaykDetails",petClinicVisitDetailsRequest.toString());

        ApiService<GetXRayReportDeatilsResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getTestXRayDetails(Config.token,petClinicVisitDetailsRequest), "GetXRaykDetails");


    }


    private void deleteXRay() {
        PetClinicVistsDetailsParams petClinicVistsDetailsParams = new PetClinicVistsDetailsParams();
        petClinicVistsDetailsParams.setId(report_type_id.substring(0,report_type_id.length()-2));
        PetClinicVisitDetailsRequest petClinicVisitDetailsRequest = new PetClinicVisitDetailsRequest();
        petClinicVisitDetailsRequest.setData(petClinicVistsDetailsParams);
        Log.d("DeleteTestXRay",petClinicVisitDetailsRequest.toString());
        ApiService<AddUpdateDeleteClinicVisitResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().deleteTestXRay(Config.token,petClinicVisitDetailsRequest), "DeleteTestXRay");

    }

    @Override
    public void onResponse(Response response, String key) {
        switch (key){
            case "GetXRaykDetails":
                try {
                    Log.d("GetXRaykDetails",response.body().toString());
                    GetXRayReportDeatilsResponse getXRayReportDeatilsResponse  = (GetXRayReportDeatilsResponse) response.body();
                    Log.e("GetXRaykDetails",methods.getRequestJson(getXRayReportDeatilsResponse));
                    int responseCode = Integer.parseInt(getXRayReportDeatilsResponse.getResponse().getResponseCode());
                    if (responseCode== 109){

                    }else {
                        Toast.makeText(this, "Something went wrong !", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;

            case "DeleteTestXRay":
                try {
                    Log.d("DeleteClinicVisit",response.body().toString());
                    AddUpdateDeleteClinicVisitResponse addUpdateDeleteClinicVisitResponse = (AddUpdateDeleteClinicVisitResponse) response.body();
                    int responseCode = Integer.parseInt(addUpdateDeleteClinicVisitResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        Config.type = "XRay";
                        onBackPressed();
                        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();

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
        Log.e("error",t.getLocalizedMessage());

    }
}
