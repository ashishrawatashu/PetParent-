package com.cynoteck.petofy.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.cynoteck.petofy.utils.GpsTracker;
import com.cynoteck.petofy.R;
import com.cynoteck.petofy.fragments.AppointmentListFragment;
import com.cynoteck.petofy.fragments.ParentHomeFragment;
import com.cynoteck.petofy.fragments.PetRegisterFragment;
import com.cynoteck.petofy.fragments.ProfileFragment;
import com.cynoteck.petofy.utils.Config;
import com.cynoteck.petofy.utils.Methods;
import com.cynoteck.petofy.utils.NetworkChangeReceiver;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class DashBoardActivity extends AppCompatActivity {
    public static final int     MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private final int           MY_PERMISSIONS_REQUEST_READ_CAMERA = 200, MY_PERMISSIONS_REQUEST_READ_STORAGE = 300;
    private GpsTracker          gpsTracker;
    public static final         String channel_id = "channel_id";
    private static final        String channel_name = "channel_name";
    private static final        String channel_desc = "channel_desc";
    boolean                     exit = false;
    Methods                     methods;
    String                      petId = "", from = "";
    LocationManager             locationManager;
    private static final int    REQUEST_LOCATION = 1;
    private BroadcastReceiver   mNetworkReceiver;
    SharedPreferences           sharedPreferences;
    SharedPreferences.Editor    login_editor;
    Dialog                      location_permission_dialog;
    Dialog                      storagePermissionDialog,cameraPermissionDialog;
    BottomNavigationView        navigation;
    final Fragment              fragment1 = new ParentHomeFragment();
    final Fragment              fragment2 = new PetRegisterFragment();
    final Fragment              fragment3 = new AppointmentListFragment();
    final Fragment              fragment4 = new ProfileFragment();
    final FragmentManager       fm        = getSupportFragmentManager();
    Fragment                    active    = fragment1;
    boolean                     cameraDialog= false, storageDialog= false;
    boolean                     locationPermission = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        methods = new Methods(this);

        notificationMethod();
//        checkCameraPermission();
        registerNetworkBroadcastForNougat();


        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setItemIconTintList(null);

        fm.beginTransaction().add(R.id.content_frame, fragment4, "4").hide(fragment4).commit();
        fm.beginTransaction().add(R.id.content_frame, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.content_frame, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.content_frame, fragment1, "1").commit();

        if (Config.fragmentNumber==3){
            Config.fragmentNumber = 0;
            fm.beginTransaction().hide(active).show(fragment3).commit();
            active = fragment3;
            MenuItem homeItem = navigation.getMenu().getItem(2);
            navigation.setSelectedItemId(homeItem.getItemId());
            Toast.makeText(this, "Select your pet for insurance ", Toast.LENGTH_SHORT).show();
        }else if (Config.fragmentNumber==2){
            Config.fragmentNumber = 0;
            fm.beginTransaction().hide(active).show(fragment2).commit();
            active = fragment2;
            MenuItem homeItem = navigation.getMenu().getItem(1);
            navigation.setSelectedItemId(homeItem.getItemId());
        }


        SharedPreferences sharedPreferences     = getSharedPreferences("userDetails", 0);
        Config.token                            = sharedPreferences.getString("token", "");
        Config.user_id                          = sharedPreferences.getString("userId", "");
        Config.user_phone                       = sharedPreferences.getString("phoneNumber", "");
        Config.user_emial                       = sharedPreferences.getString("email", "");
        Config.user_name                        = sharedPreferences.getString("firstName", "") + " " + sharedPreferences.getString("lastName", "");
        Config.user_address                     = sharedPreferences.getString("address", "");
        Config.user_online                      = sharedPreferences.getString("onlineAppoint", "");
        Config.user_study                       = sharedPreferences.getString("study", "");
        Config.user_url                         = sharedPreferences.getString("profilePic", "");
        Config.two_fact_auth_status             = sharedPreferences.getString("twoFactAuth", "");
        Config.parent_encryptedId               = sharedPreferences.getString("encryptedId", "");
        Config.first_name                       = sharedPreferences.getString("firstName", "");
        Config.last_name                        = sharedPreferences.getString("lastName", "");
        Config.cityId                           = sharedPreferences.getString("CityId", "");
        Config.cityName                         = sharedPreferences.getString("cityName", "");
        Config.cityFullName                     = sharedPreferences.getString("CityFullName", "");
        Config.locationPermission               = sharedPreferences.getString("locationPermission", "");
//      Config.latitude                         = sharedPreferences.getString("userLatitude", "");
//      Config.longitude                        = sharedPreferences.getString("userLongitude", "");
        if (Config.locationPermission.equals("false")){
            showLocationPermissionDialog();
        }else {
            getLocation();
        }

        Log.d("TOKEN", Config.token);
        Log.d("user_id", Config.user_id);
//        Log.d("LOCATION", Config.latitude + "  " + Config.longitude);

    }

    private void checkCameraPermission() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_READ_CAMERA);
            return;
        }
    }

    private void showLocationPermissionDialog() {
        locationPermission = true;
        location_permission_dialog = new Dialog(this);
        location_permission_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        location_permission_dialog.setCancelable(false);
        location_permission_dialog.setContentView(R.layout.location_permission_dialog);
        location_permission_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Button grant_permission_BT = location_permission_dialog.findViewById(R.id.grant_permission_BT);
        location_permission_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        grant_permission_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                        ActivityCompat.requestPermissions(DashBoardActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        location_permission_dialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = location_permission_dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
    }

    public void getLocation(){
        gpsTracker = new GpsTracker(DashBoardActivity.this);
        if(gpsTracker.canGetLocation()){
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            Config.latitude = String.valueOf(latitude);
            Config.longitude = String.valueOf(longitude);
            Log.e("location", Config.longitude + " " + Config.latitude);
            sharedPreferences = this.getSharedPreferences("userDetails", 0);
            login_editor = sharedPreferences.edit();
            login_editor.putString("userLatitude", String.valueOf(latitude));
            login_editor.putString("userLongitude", String.valueOf(longitude));
            login_editor.apply();
        }else{
            gpsTracker.showSettingsAlert();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CAMERA: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (cameraDialog) {
                        cameraPermissionDialog.dismiss();
                        cameraDialog = false;
                        checkCameraPermission();
                    }
                } else {
                    cameraDialog = false;
                    showCameraPermissionDialog();
                }

                return;
            }
            case 101: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sharedPreferences = this.getSharedPreferences("userDetails", 0);
                    login_editor = sharedPreferences.edit();
                    login_editor.putString("locationPermission", "true");
                    login_editor.apply();
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    if (locationPermission) {
                        location_permission_dialog.dismiss();
                        locationPermission = false;
                    }
                } else {
                    locationPermission = false;
                    showLocationPermissionDialog();
                }

            }

        }
    }

    private void showCameraPermissionDialog() {
        cameraDialog            = true;
        cameraPermissionDialog  = new Dialog(this);
        cameraPermissionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        cameraPermissionDialog.setCancelable(false);
        cameraPermissionDialog.setContentView(R.layout.camera_permission_dialog);
        cameraPermissionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Button grant_permission_BT = cameraPermissionDialog.findViewById(R.id.grant_permission_BT);
        cameraPermissionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        grant_permission_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCameraPermission();
            }
        });

        cameraPermissionDialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = cameraPermissionDialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
    }


    private void notificationMethod() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channel_id, channel_name, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription(channel_desc);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);

        }
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
//            Log.e("Connected", "Yes");

            Handler handler = new Handler();
            Runnable delayrunnable = new Runnable() {
                @Override
                public void run() {
//                    Log.e("Connected", "Yes1");

                }
            };
            handler.postDelayed(delayrunnable, 3000);
        } else {
//            Log.e("Connected", "NO");
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Config.count = 1;
                    fm.beginTransaction().hide(active).show(fragment1).commit();
                    fm.beginTransaction().hide(fragment2).commit();
                    fm.beginTransaction().hide(fragment3).commit();
                    fm.beginTransaction().hide(fragment4).commit();
                    active = fragment1;
                    return true;

                case R.id.navigation_mypets:
                    Config.count = 0;
                    fm.beginTransaction().hide(active).show(fragment2).commit();
                    fm.beginTransaction().hide(fragment3).commit();
                    fm.beginTransaction().hide(fragment4).commit();
                    fm.beginTransaction().hide(fragment1).commit();
                    active = fragment2;
                    return true;

                case R.id.navigation_appointment:
                    Config.count = 0;
                    fm.beginTransaction().hide(active).show(fragment3).commit();
                    fm.beginTransaction().hide(fragment2).commit();
                    fm.beginTransaction().hide(fragment4).commit();
                    fm.beginTransaction().hide(fragment1).commit();
                    active = fragment3;
                    return true;

                case R.id.navigation_profile:
                    Config.count = 0;
                    fm.beginTransaction().hide(active).show(fragment4).commit();
                    fm.beginTransaction().hide(fragment2).commit();
                    fm.beginTransaction().hide(fragment3).commit();
                    fm.beginTransaction().hide(fragment1).commit();
                    active = fragment4;
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onResume() {
        super.onResume();

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