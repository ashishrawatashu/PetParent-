package com.cynoteck.petofyparents.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.activty.LoginActivity;
import com.cynoteck.petofyparents.activty.ScannerQR;
import com.cynoteck.petofyparents.activty.SearchActivity;
import com.cynoteck.petofyparents.api.ApiClient;
import com.cynoteck.petofyparents.api.ApiResponse;
import com.cynoteck.petofyparents.api.ApiService;
import com.cynoteck.petofyparents.parameter.checkpetInVetRegister.InPetRegisterRequest;
import com.cynoteck.petofyparents.response.InPetVeterian.InPetVeterianResponse;
import com.cynoteck.petofyparents.utils.Config;
import com.cynoteck.petofyparents.utils.Methods;

import java.util.ArrayList;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment implements View.OnClickListener, ApiResponse {

    private static final int QR_CODE_SCANNER = 100;

    Context context;
    View view;
    ImageView reports_IV,new_pet_search,back_arrow_IV_new_entry,bar_code_scanner_IV;
    RelativeLayout mainHome,search_boxRL;
    Methods methods;
    HashMap<String,String> petExistingSearch;
    TextView staff_headline_TV;
    TextView search_box_add_new;
    CardView medical_history_CV, adoption_donation_CV, my_pets_CV,appoint_CV;
    ArrayList<String> petUniueId=null;
    Intent intent;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        init();
        return view;
    }

    private void init() {
        methods = new Methods(context);
        medical_history_CV=view.findViewById(R.id.medical_history_CV);
        adoption_donation_CV = view.findViewById(R.id.adoption_donation_CV);
        my_pets_CV=view.findViewById(R.id.my_pets_CV);
        appoint_CV=view.findViewById(R.id.appointment_CV);
        search_boxRL = view.findViewById(R.id.search_boxRL);

        new_pet_search = view.findViewById(R.id.new_pet_search);
        back_arrow_IV_new_entry = view.findViewById(R.id.back_arrow_IV_new_entry);
        new_pet_search = view.findViewById(R.id.new_pet_search);
        search_box_add_new = view.findViewById(R.id.search_box_add_new);
        staff_headline_TV = view.findViewById(R.id.staff_headline_TV);
        bar_code_scanner_IV = view.findViewById(R.id.bar_code_scanner_IV);
        my_pets_CV.setOnClickListener(this);
        medical_history_CV.setOnClickListener(this);
        adoption_donation_CV.setOnClickListener(this);
        appoint_CV.setOnClickListener(this);
        bar_code_scanner_IV.setOnClickListener(this);
        search_boxRL.setOnClickListener(this);
        search_box_add_new.setOnClickListener(this);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==QR_CODE_SCANNER){
            if (resultCode==RESULT_OK){
                String veterinarianUserId = data.getStringExtra("veterinarianUserId");
                String veterinarianName = data.getStringExtra("veterinarianName");
                String clinicName = data.getStringExtra("clinicName");
                String Rating = data.getStringExtra("Rating");
                String profileImageUrl = data.getStringExtra("profileImageUrl");
                Log.e("veterinarianUserId",veterinarianUserId);
                AfterScanScreenFragment afterScanScreenFragment = new AfterScanScreenFragment();
                Bundle qrCodeDataBudle = new Bundle();
                qrCodeDataBudle.putString("veterinarianUserId",veterinarianUserId);
                qrCodeDataBudle.putString("veterinarianName",veterinarianName);
                qrCodeDataBudle.putString("clinicName",clinicName);
                qrCodeDataBudle.putString("Rating",Rating);
                qrCodeDataBudle.putString("profileImageUrl",profileImageUrl);
                afterScanScreenFragment.setArguments(qrCodeDataBudle);
                replaceFragment(afterScanScreenFragment);

            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.bar_code_scanner_IV:
                Intent qr_code_intent = new Intent(getContext(), ScannerQR.class);
                startActivityForResult(qr_code_intent,QR_CODE_SCANNER);
                break;

            case R.id.search_box_add_new:
                    intent = new Intent(getContext(), SearchActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);

                break;


            case R.id.medical_history_CV:

                ReportsFragment profileFragment = new ReportsFragment();
                replaceFragment(profileFragment);

                break;

            case R.id.adoption_donation_CV:

               AllStaffFragment allStaffFragment = new AllStaffFragment();
               replaceFragment(allStaffFragment);
//                Toast.makeText(context, "Coming Soon !", Toast.LENGTH_SHORT).show();

                break;

            case R.id.my_pets_CV:
                PetRegisterFragment petRegisterFragment = new PetRegisterFragment();
                replaceFragment(petRegisterFragment);

                break;

            case R.id.appointment_CV:
                AppointementFragment appointementFragment = new AppointementFragment();
                replaceFragment(appointementFragment);

                break;
        }

    }

    private void replaceFragment(Fragment fragment) {
        Config.count = 0;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void chkVetInregister(InPetRegisterRequest inPetRegisterRequest) {
        ApiService<InPetVeterianResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().checkPetInVetRegister(Config.token,inPetRegisterRequest), "CheckPetInVetRegister");
        Log.e("DATALOG","check1=> "+inPetRegisterRequest);
    }
//    private void clearSearch() {
//        search_box_add_new.getText().clear();
//        search_boxRL.setVisibility(View.GONE);
//        back_arrow_IV_new_entry.setVisibility(View.GONE);
//        staff_headline_TV.setVisibility(View.VISIBLE);
//        InputMethodManager imm1 = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm1.hideSoftInputFromWindow(search_box_add_new.getWindowToken(), 0);
//    }

    @Override
    public void onResponse(Response arg0, String key) {
        methods.customProgressDismiss();


    }

    @Override
    public void onError(Throwable t, String key) {

    }
}