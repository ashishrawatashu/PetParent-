package com.cynoteck.petofyparents.activty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Response;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.api.ApiResponse;
import com.cynoteck.petofyparents.fragments.AppointmentListFragment;
import com.cynoteck.petofyparents.fragments.ParentHomeFragment;
import com.cynoteck.petofyparents.fragments.PetRegisterFragment;
import com.cynoteck.petofyparents.fragments.ProfileFragment;
import com.cynoteck.petofyparents.utils.Config;
import com.cynoteck.petofyparents.utils.Methods;
import com.cynoteck.petofyparents.utils.NetworkChangeReceiver;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashBoardActivity extends AppCompatActivity{
    public static final String channel_id="channel_id";
    private static final String channel_name="channel_name";
    private static final String channel_desc="channel_desc";
    boolean exit = false;
    Methods methods;
    String petId="",from="";
    LocationManager locationManager;
    private static final int REQUEST_LOCATION = 1;

    final Fragment fragment1 = new ParentHomeFragment();
    final Fragment fragment2 = new PetRegisterFragment();
    final Fragment fragment3 = new AppointmentListFragment();
    final Fragment fragment4 = new ProfileFragment();

    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;
    BottomNavigationView navigation;

    private BroadcastReceiver mNetworkReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        mNetworkReceiver = new NetworkChangeReceiver();
        registerNetworkBroadcastForNougat();

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setItemIconTintList(null);

        fm.beginTransaction().add(R.id.content_frame, fragment4, "4").hide(fragment4).commit();
        fm.beginTransaction().add(R.id.content_frame, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.content_frame, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.content_frame,fragment1, "1").commit();
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        } else {
            getCurrentLocation();
        }
        methods = new Methods(this);
        Intent  intent = getIntent();
        from = intent.getStringExtra("from");
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel=new NotificationChannel(channel_id, channel_name, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription(channel_desc);
            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);

        }

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
        Config.user_study=sharedPreferences.getString("study", "");
        Config.user_url=sharedPreferences.getString("profilePic", "");
        Config.two_fact_auth_status=sharedPreferences.getString("twoFactAuth", "");
        Config.parent_encryptedId=sharedPreferences.getString("encryptedId", "");
        Config.first_name = sharedPreferences.getString("firstName", "");
        Config.last_name = sharedPreferences.getString("lastName", "");

        if (from.equals("WITH_QR")){
            petId = intent.getStringExtra("petId");
            Intent petProfileActivity = new Intent(this,PetProfileActivity.class);
            petProfileActivity.putExtra("pet_id",petId);
            startActivity(petProfileActivity);
        }

    }

    private void registerNetworkBroadcastForNougat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    protected void unregisterNetworkChanges() {
        try {
            unregisterReceiver(mNetworkReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
    public static void dialog(boolean value) {

        if(value){
            Log.e("Connected","Yes");

            Handler handler = new Handler();
            Runnable delayrunnable = new Runnable() {
                @Override
                public void run() {
                    Log.e("Connected","Yes1");

                }
            };
            handler.postDelayed(delayrunnable, 3000);
        }else {
            Log.e("Connected","NO");
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Config.count = 1;
                    fm.beginTransaction().hide(active).show(fragment1).commit();
                    active = fragment1;
                    return true;

                case R.id.navigation_mypets:
                    Config.count = 0;
                    fm.beginTransaction().hide(active).show(fragment2).commit();
                    active = fragment2;
                    return true;

                case R.id.navigation_appointment:
                    Config.count = 0;
                    fm.beginTransaction().hide(active).show(fragment3).commit();
                    active = fragment3;
                    return true;

                case R.id.navigation_profile:
                    Config.count = 0;
                    fm.beginTransaction().hide(active).show(fragment4).commit();
                    active = fragment4;
                    return true;
            }
            return false;
        }
    };

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                Config.latitude = String.valueOf(lat);
                Config.longitude = String.valueOf(longi);
                Log.e("location", Config.longitude  + " " + Config.latitude );
            } else {
                Log.e("Loaction Error", "Unable to find location.");
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (Config.count == 1) {
            if (exit) {
                super.onBackPressed();
                finishAffinity();
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
            fm.beginTransaction().hide(active).show(fragment1).commit();
            active = fragment1;
            MenuItem homeItem = navigation.getMenu().getItem(0);
            navigation.setSelectedItemId(homeItem.getItemId());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterNetworkChanges();

    }
}