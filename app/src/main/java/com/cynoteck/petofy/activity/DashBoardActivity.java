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
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
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
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;


public class DashBoardActivity extends AppCompatActivity {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private final int MY_PERMISSIONS_REQUEST_READ_CAMERA = 200, MY_PERMISSIONS_REQUEST_READ_STORAGE = 300;
    private GpsTracker gpsTracker;
    public static final String channel_id = "channel_id";
    private static final String channel_name = "channel_name";
    private static final String channel_desc = "channel_desc";
    boolean exit = false;
    Methods methods;
    String petId = "", from = "";
    LocationManager locationManager;
    private static final int REQUEST_LOCATION = 1;
    private BroadcastReceiver mNetworkReceiver;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor login_editor;
    Dialog location_permission_dialog,settingDialog;
    Dialog storagePermissionDialog, cameraPermissionDialog;
    BottomNavigationView navigation;
    final Fragment fragment1 = new ParentHomeFragment();
    final Fragment fragment2 = new PetRegisterFragment();
    final Fragment fragment3 = new AppointmentListFragment();
    final Fragment fragment4 = new ProfileFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;
    boolean cameraDialog = false, storageDialog = false;
    boolean  dialogStatus = false;
    boolean locationPermission = false;
    Button open_setting_BT;
    BottomSheetDialog updateDialog;
    String currentVersion, latestVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        methods = new Methods(this);
        notificationMethod();
//        getLocation();
//        checkCameraPermission();


//        getCurrentVersion();


        registerNetworkBroadcastForNougat();
//        requestMultiplePermissions();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setItemIconTintList(null);

        fm.beginTransaction().add(R.id.content_frame, fragment4, "4").hide(fragment4).commit();
        fm.beginTransaction().add(R.id.content_frame, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.content_frame, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.content_frame, fragment1, "1").commit();

        if (Config.fragmentNumber == 3) {
            Config.fragmentNumber = 0;
            fm.beginTransaction().hide(active).show(fragment3).commit();
            active = fragment3;
            MenuItem homeItem = navigation.getMenu().getItem(2);
            navigation.setSelectedItemId(homeItem.getItemId());
        } else if (Config.fragmentNumber == 2) {
            Config.fragmentNumber = 0;
            fm.beginTransaction().hide(active).show(fragment2).commit();
            active = fragment2;
            MenuItem homeItem = navigation.getMenu().getItem(1);
            navigation.setSelectedItemId(homeItem.getItemId());
            Toast.makeText(this, "Select your pet for insurance ", Toast.LENGTH_SHORT).show();
        }


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
        Config.cityId = sharedPreferences.getString("CityId", "");
        Config.cityName = sharedPreferences.getString("cityName", "");
        Config.cityFullName = sharedPreferences.getString("CityFullName", "");
        Config.locationPermission = sharedPreferences.getString("locationPermission", "");
//      Config.latitude                         = sharedPreferences.getString("userLatitude", "");
//      Config.longitude                        = sharedPreferences.getString("userLongitude", "");

        requestMultiplePermissions();

//        getLocation();


//        if (Config.locationPermission.equals("true")) {
//            getLocation();
//        }
//        else{
//            requestMultiplePermissions();
//        }
//        requestMultiplePermissions();
//        getLocation();

        Log.d("TOKEN", Config.token);
        Log.d("user_id", Config.user_id);
//        Log.d("LOCATION", Config.latitude + "  " + Config.longitude);

    }



    public void getLocation() {
        gpsTracker = new GpsTracker(DashBoardActivity.this);
        if (gpsTracker.canGetLocation()) {
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
        } else {
            gpsTracker.showSettingsAlert();
        }
    }

//    -----------------------------------------------------------------------------------

    private void getCurrentVersion() {
        PackageManager pm = this.getPackageManager();
        PackageInfo pInfo = null;

        try {
            pInfo = pm.getPackageInfo(this.getPackageName(), 0);

        } catch (PackageManager.NameNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        currentVersion = pInfo.versionName;
        //currentVersion="1.0.2";
        Log.d("currentVersion", currentVersion);
        new GetLatestVersion().execute();

    }


    private class GetLatestVersion extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            try {
//It retrieves the latest version by scraping the content of current version from play store at runtime

                Document doc = Jsoup.connect("https://play.google.com/store/apps/details?id=com.cynoteck.petofy").get();
                latestVersion = doc.getElementsByClass("htlgb").get(6).text();

                //latestVersion = "1.0.1";
                Log.d("latestVersion", latestVersion);

            } catch (Exception e) {
                e.printStackTrace();

            }
            return new JSONObject();
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            if (latestVersion != null) {
                if (!currentVersion.equalsIgnoreCase(latestVersion)) {
                    if (!isFinishing()) { //This would help to prevent Error : BinderProxy@45d459c0 is not valid; is your activity running? error
                        newUpdateDialog();
                    }
                }
            }
        }

    }

    private void newUpdateDialog() {
        updateDialog  = new BottomSheetDialog(this);
        updateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        updateDialog.setCancelable(false);
        updateDialog.setContentView(R.layout.update_new_version_dialog);
        updateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Button download_BT = updateDialog.findViewById(R.id.download_BT);
        updateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        download_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent;
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setData(Uri.parse("market://details?id=" + "com.cynoteck.petofy"));
                    startActivity(intent);
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.cynoteck.petofy")));
                }
            }
        });

        updateDialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = updateDialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
    }

//    -----------------------------------------------------------------------------------






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

    //    it will check all the permission related to the app
    private void requestMultiplePermissions() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
//
                            Log.d("STORAGE_DIALOG", "All permissions are granted by user!");

                        }
                        else
                            {
//
                            startActivity(new Intent(DashBoardActivity.this,PermissionCheckActivity.class));
                            Toast.makeText(DashBoardActivity.this, "Please allow storage permission !", Toast.LENGTH_SHORT).show();
//                            Log.d("DEXTER", "storagePermissionDialog");

                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
//                            locationPermission = false;
                            startActivity(new Intent(DashBoardActivity.this,PermissionCheckActivity.class));
                            Log.d("STORAGE_DIALOG", "dashboardopenSettingsDialog");

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Log.e("DEXTER", error.name());
//                        Toast.makeText(DashBoardActivity.this, "Some Error ! ", Toast.LENGTH_SHORT).show();
                           }
                })
                .onSameThread()
                .check();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterNetworkChanges();

    }


}