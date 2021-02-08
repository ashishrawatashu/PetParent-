package com.cynoteck.petofyparents.activty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Response;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.api.ApiResponse;
import com.cynoteck.petofyparents.fragments.AppointementFragment;
import com.cynoteck.petofyparents.fragments.HomeFragment;
import com.cynoteck.petofyparents.fragments.PetRegisterFragment;
import com.cynoteck.petofyparents.fragments.ProfileFragment;
import com.cynoteck.petofyparents.utils.Config;
import com.cynoteck.petofyparents.utils.Methods;

public class DashBoardActivity extends AppCompatActivity implements ApiResponse, View.OnClickListener {


    private RelativeLayout homeRL,profileRL,petregisterRL,appointmentRL;
    public ImageView icHome, icProfile, icPetRegister, icAppointment;
    boolean doubleBackToExitPressedOnce = false;
    boolean exit = false;
    Methods methods;
    String IsVeterinarian="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        initialize();
        methods = new Methods(this);
        SharedPreferences sharedPreferences = getSharedPreferences("userdetails", 0);
        Config.token = sharedPreferences.getString("token", "");
        Log.e("token",Config.token);
        Config.user_id=sharedPreferences.getString("userId", "");
        Log.e("user_id",Config.user_id);
        Config.user_phone=sharedPreferences.getString("phoneNumber", "");
        Config.user_emial=sharedPreferences.getString("email", "");
        Config.user_name=sharedPreferences.getString("firstName", "")+" "+sharedPreferences.getString("lastName", "");
        Config.user_address=sharedPreferences.getString("address", "");
        Config.user_online=sharedPreferences.getString("onlineAppoint", "");
        Config.user_id=sharedPreferences.getString("vetid", "");
        Config.user_study=sharedPreferences.getString("study", "");
        Config.user_url=sharedPreferences.getString("profilePic", "");
        Config.two_fact_auth_status=sharedPreferences.getString("twoFactAuth", "");

        if (savedInstanceState == null) {
            HomeFragment homeFragment = new HomeFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.content_frame, homeFragment);
            ft.commit();
            icHome.setImageResource(R.drawable.home_green_icon);
        }

    }

    public void initialize()
    {
        homeRL = findViewById(R.id.homeRL);
        profileRL = findViewById(R.id.profileRL);
        petregisterRL=findViewById(R.id.petRegisterRL);
        appointmentRL=findViewById(R.id.appointmentRL);

        icHome=findViewById(R.id.icHome);
        icProfile = findViewById(R.id.icProfile);
        icPetRegister=findViewById(R.id.icPetRegister);
        icAppointment=findViewById(R.id.icAppointment);

        homeRL.setOnClickListener(this);
        profileRL.setOnClickListener(this);
        petregisterRL.setOnClickListener(this);
        appointmentRL.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.homeRL:
                Config.count = 1;
                icHome.setImageResource(R.drawable.home_green_icon);
                icProfile.setImageResource(R.drawable.profile_normal_icon);
                icPetRegister.setImageResource(R.drawable.pet_normal_icon);
                icAppointment.setImageResource(R.drawable.appointment_normal_icon);
                HomeFragment homeFragment = new HomeFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, homeFragment);
                ft.commit();

                break;

            case R.id.profileRL:
                Config.count = 0;
                icHome.setImageResource(R.drawable.home_normal_icon);
                icProfile.setImageResource(R.drawable.profile_green_icon);
                icPetRegister.setImageResource(R.drawable.pet_normal_icon);
                icAppointment.setImageResource(R.drawable.appointment_normal_icon);
                ProfileFragment profileFragment = new ProfileFragment();
                FragmentTransaction ftProfile = getSupportFragmentManager().beginTransaction();
                ftProfile.replace(R.id.content_frame, profileFragment);
                ftProfile.commit();
                break;

            case R.id.petRegisterRL:
                Config.count = 0;
                icHome.setImageResource(R.drawable.home_normal_icon);
                icProfile.setImageResource(R.drawable.profile_normal_icon);
                icPetRegister.setImageResource(R.drawable.pet_green_icon);
                icAppointment.setImageResource(R.drawable.appointment_normal_icon);
                PetRegisterFragment petRegisterFragment = new PetRegisterFragment();
                FragmentTransaction ftPetRegister = getSupportFragmentManager().beginTransaction();
                ftPetRegister.replace(R.id.content_frame, petRegisterFragment);
                ftPetRegister.commit();
                break;

            case R.id.appointmentRL:
                Config.count = 0;
                icHome.setImageResource(R.drawable.home_normal_icon);
                icProfile.setImageResource(R.drawable.profile_normal_icon);
                icPetRegister.setImageResource(R.drawable.pet_normal_icon);
                icAppointment.setImageResource(R.drawable.appointment_green_icon);
                AppointementFragment appointementFragment = new AppointementFragment();
                FragmentTransaction ftAppointment = getSupportFragmentManager().beginTransaction();
                ftAppointment.replace(R.id.content_frame, appointementFragment);
                ftAppointment.commit();
                break;

        }

    }

    @Override
    public void onResponse(Response arg0, String key) {
        switch (key)
        {

        }
    }

    @Override
    public void onError(Throwable t, String key) {
        Log.e("error",t.getMessage());
        Log.e("errrrr",t.getLocalizedMessage());
    }


    @Override
    public void onBackPressed() {
        Log.e("count", String.valueOf(Config.count));
        Log.e("exit", String.valueOf(exit));
        if (Config.count == 1) {
            if (exit) {
                super.onBackPressed();
                finishAffinity();
                System.exit(0);
                return;
            } else {
                Toast.makeText(this, "Press Back again to Exit.", Toast.LENGTH_SHORT).show();
                exit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit =  false;
                    }
                }, 2000);
            }
        }else {
            Config.count=1;
            HomeFragment homeFragment = new HomeFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, homeFragment);

            getSupportFragmentManager().popBackStack();
            icHome.setImageResource(R.drawable.home_green_icon);
            icProfile.setImageResource(R.drawable.profile_normal_icon);
            icPetRegister.setImageResource(R.drawable.pet_normal_icon);
            icAppointment.setImageResource(R.drawable.appointment_normal_icon);
            ft.commit();
        }
    }
}