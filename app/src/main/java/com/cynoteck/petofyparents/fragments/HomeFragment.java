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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.activty.DashBoardActivity;
import com.cynoteck.petofyparents.activty.LoginActivity;
import com.cynoteck.petofyparents.api.ApiResponse;
import com.cynoteck.petofyparents.utils.Config;
import com.cynoteck.petofyparents.utils.Methods;

public class HomeFragment extends Fragment implements View.OnClickListener, ApiResponse {

    Context context;
    View view;
    ImageView reports_IV;
    Methods methods;
    TextView logout;
    CardView reports_CV, all_staff_CV, allPets_CV,appoint_CV;

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
        appoint_CV=view.findViewById(R.id.appoint_CV);
        logout=view.findViewById(R.id.logout);

        allPets_CV.setOnClickListener(this);
        reports_CV.setOnClickListener(this);
        all_staff_CV.setOnClickListener(this);
        appoint_CV.setOnClickListener(this);
        logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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

            case R.id.appoint_CV:
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

    @Override
    public void onResponse(Response arg0, String key) {
        methods.customProgressDismiss();


    }

    @Override
    public void onError(Throwable t, String key) {

    }
}