package com.cynoteck.petofyparents.activty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import android.location.LocationListener;
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

import java.util.Timer;
import java.util.TimerTask;

public class DashBoardActivity extends AppCompatActivity {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public static final String channel_id = "channel_id";
    private static final String channel_name = "channel_name";
    private static final String channel_desc = "channel_desc";
    boolean exit = false;
    Methods methods;
    String petId = "", from = "";
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
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor login_editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        methods = new Methods(this);
        Intent intent = getIntent();
        from = intent.getStringExtra("from");
        checkLocationPermission();

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        } else {
            getCurrentLocation();
        }
        notificationMethod();

        registerNetworkBroadcastForNougat();
        //edit sharedPreferences data

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setItemIconTintList(null);

        fm.beginTransaction().add(R.id.content_frame, fragment4, "4").hide(fragment4).commit();
        fm.beginTransaction().add(R.id.content_frame, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.content_frame, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.content_frame, fragment1, "1").commit();


        SharedPreferences sharedPreferences = getSharedPreferences("userDetails", 0);
        Config.token = sharedPreferences.getString("token", "");
        Config.user_id = sharedPreferences.getString("userId", "");
        Config.user_phone = sharedPreferences.getString("phoneNumber", "");
        Config.user_emial = sharedPreferences.getString("email", "");
        Config.user_name = sharedPreferences.getString("firstName", "") + " " + sharedPreferences.getString("lastName", "");
        Config.user_address = sharedPreferences.getString("address", "");
        Config.user_online = sharedPreferences.getString("onlineAppoint", "");
        Config.user_study = sharedPreferences.getString("study", "");
        Config.user_url = sharedPreferences.getString("profilePic", "");
        Config.two_fact_auth_status = sharedPreferences.getString("twoFactAuth", "");
        Config.parent_encryptedId = sharedPreferences.getString("encryptedId", "");
        Config.first_name = sharedPreferences.getString("firstName", "");
        Config.last_name = sharedPreferences.getString("lastName", "");
//        Config.latitude = sharedPreferences.getString("userLatitude", "");
//        Config.longitude = sharedPreferences.getString("userLongitude", "");
        Config.cityId = sharedPreferences.getString("CityId", "");
        Config.cityName = sharedPreferences.getString("cityName", "");
        Config.cityFullName = sharedPreferences.getString("CityFullName", "");

        Log.e("token", Config.token);
        Log.e("user_id", Config.user_id);
        Log.e("LOCATION", Config.latitude + "  " + Config.longitude);
        if (from.equals("WITH_QR")) {
            petId = intent.getStringExtra("petId");
            Intent petProfileActivity = new Intent(this, PetProfileActivity.class);
            petProfileActivity.putExtra("pet_id", petId);
            startActivity(petProfileActivity);
        }

    }
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                ActivityCompat.requestPermissions(DashBoardActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        getCurrentLocation();
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }
    private void notificationMethod() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channel_id, channel_name, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription(channel_desc);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);

        }
    }

    private void locationMethod() {

    }

    private void registerNetworkBroadcastForNougat() {
        mNetworkReceiver = new NetworkChangeReceiver();
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

        if (value) {
            Log.e("Connected", "Yes");

            Handler handler = new Handler();
            Runnable delayrunnable = new Runnable() {
                @Override
                public void run() {
                    Log.e("Connected", "Yes1");

                }
            };
            handler.postDelayed(delayrunnable, 3000);
        } else {
            Log.e("Connected", "NO");
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

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                Config.latitude = String.valueOf(lat);
                Config.longitude = String.valueOf(longi);
                Log.e("location", Config.longitude + " " + Config.latitude);

                sharedPreferences = this.getSharedPreferences("userDetails", 0);
                login_editor = sharedPreferences.edit();
                login_editor.putString("userLatitude", String.valueOf(lat));
                login_editor.putString("userLongitude", String.valueOf(longi));
                login_editor.commit();
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
                        exit = false;
                    }
                }, 2000);
            }
        } else {
            Config.count = 1;
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