package com.cynoteck.petofyparents.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.activty.LoginActivity;
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

public class HomeFragment extends Fragment implements View.OnClickListener, ApiResponse {

    Context context;
    View view;
    ImageView reports_IV,new_pet_search,back_arrow_IV_new_entry;
    RelativeLayout mainHome,search_boxRL;
    Methods methods;
    HashMap<String,String> petExistingSearch;
    TextView staff_headline_TV;
    AutoCompleteTextView search_box_add_new;
    CardView reports_CV, all_staff_CV, allPets_CV,appoint_CV;
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
        reports_CV=view.findViewById(R.id.reports_CV);
        all_staff_CV = view.findViewById(R.id.staff_CV);
        allPets_CV=view.findViewById(R.id.allPets_CV);
        appoint_CV=view.findViewById(R.id.appointment_CV);
        search_boxRL = view.findViewById(R.id.search_boxRL);

        new_pet_search = view.findViewById(R.id.new_pet_search);
        back_arrow_IV_new_entry = view.findViewById(R.id.back_arrow_IV_new_entry);
        new_pet_search = view.findViewById(R.id.new_pet_search);
        search_box_add_new = view.findViewById(R.id.search_box_add_new);
        staff_headline_TV = view.findViewById(R.id.staff_headline_TV);

        allPets_CV.setOnClickListener(this);
        reports_CV.setOnClickListener(this);
        all_staff_CV.setOnClickListener(this);
        appoint_CV.setOnClickListener(this);
//        logout.setOnClickListener(this);
        new_pet_search.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.new_pet_search:
                    intent = new Intent(getContext(), SearchActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);

                break;


            case R.id.reports_CV:

                ReportsFragment profileFragment = new ReportsFragment();
                replaceFragment(profileFragment);

                break;

            case R.id.staff_CV:

                AllStaffFragment allStaffFragment = new AllStaffFragment();
                replaceFragment(allStaffFragment);

                break;

            case R.id.allPets_CV:
                PetRegisterFragment petRegisterFragment = new PetRegisterFragment();
                replaceFragment(petRegisterFragment);

                break;

            case R.id.appointment_CV:
                AppointementFragment appointementFragment = new AppointementFragment();
                replaceFragment(appointementFragment);

                break;

            case R.id.logout:
                AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("Alert message to be shown");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences preferences =getContext().getSharedPreferences("userdetails",0);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.clear();
                                editor.apply();
                                dialog.dismiss();
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                                getActivity().finish();
                            }
                        });
                alertDialog.show();

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
    private void clearSearch() {
        search_box_add_new.getText().clear();
        search_boxRL.setVisibility(View.GONE);
        back_arrow_IV_new_entry.setVisibility(View.GONE);
        staff_headline_TV.setVisibility(View.VISIBLE);
        InputMethodManager imm1 = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm1.hideSoftInputFromWindow(search_box_add_new.getWindowToken(), 0);
    }

    @Override
    public void onResponse(Response arg0, String key) {
        methods.customProgressDismiss();


    }

    @Override
    public void onError(Throwable t, String key) {

    }
}