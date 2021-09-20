package com.cynoteck.petofy.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Formatter;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.cynoteck.petofy.R;
import com.cynoteck.petofy.utils.Config;
import com.cynoteck.petofy.utils.Methods;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;

public class SplashScreen extends AppCompatActivity {

    Animation   animation;
    ImageView   splash_logo;
    Methods     methods;
    int internetChkCode=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        splash_logo     = findViewById(R.id.splashlogo);
        methods         = new Methods(this);

        animation       = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.bounce);
        splash_logo.setAnimation(animation);
        NetwordDetect();
        try {
            if (methods.isInternetOn()) {
                internetChkCode=1;
                updateMethod();

            } else {
                internetChkCode=0;
                methods.DialogInternet();
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private void NetwordDetect() {
        boolean WIFI = false;
        boolean MOBILE = false;
        ConnectivityManager CM = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo[] networkInfo = CM.getAllNetworkInfo();
        for (NetworkInfo netInfo : networkInfo) {
            if (netInfo.getTypeName().equalsIgnoreCase("WIFI"))
                if (netInfo.isConnected())
                    WIFI = true;
            if (netInfo.getTypeName().equalsIgnoreCase("MOBILE"))
                if (netInfo.isConnected())
                    MOBILE = true;
        }

        if (WIFI) {
            Config.IpAddress = GetDeviceipWiFiData();
//            Constants.EndUserIp= Config.IpAddress;
            //Log.d"WIFI", Config.IpAddress + "");
        }

        if (MOBILE) {
            Config.IpAddress = getLocalIpAddress();
//          Constants.EndUserIp= Config.IpAddress;
            //Log.d"MOBILE", Config.IpAddress + "");
        }
    }

    public String GetDeviceipWiFiData() {
        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        @SuppressWarnings("deprecation")
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        return ip;
    }




    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String ip = Formatter.formatIpAddress(inetAddress.hashCode());
                        //Log.d"sssss", ip + "");
                        return ip;
                    }
                }
            }
        } catch (Exception ex) {
            //Log.d"IP Address", ex.toString());
        }
        return null;
    }

    void updateMethod() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                requestMultiplePermissions();


//                Intent intent;
//                SharedPreferences sharedPreferences = getSharedPreferences("userDetails", 0);
//                String loggedIn = sharedPreferences.getString("loggedIn", "");
//                if (loggedIn.equals("loggedIn")) {
//                    intent = new Intent(SplashScreen.this, DashBoardActivity.class);
//                    intent.putExtra("from", "SPLASH");
//                } else {
//                    intent = new Intent(SplashScreen.this, WelcomeScreenActivity.class);
////                    intent = new Intent(SplashScreen.this, LoginActivity.class);
//
//                }
//                startActivity(intent);
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
        }, 2500);
    }



    private void requestMultiplePermissions() {
        Dexter.withActivity(this)
                .withPermissions(
                        android.Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Log.d("STORAGE_DIALOG","All permissions are granted by user!");
                            intentActivity();
                        }else {
                            Log.d("STORAGE_DIALOG","storagePermissionDialog");
                            intentActivity();

                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            Log.d("STORAGE_DIALOG","openSettingsDialog");
                            startActivity(new Intent(SplashScreen.this,PermissionCheckActivity.class));

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
                        Log.d("STORAGE_DIALOG",error.toString());

                    }
                })
                .onSameThread()
                .check();
    }













    private void intentActivity() {
        internetChkCode=0;
        Intent intent;
        SharedPreferences sharedPreferences = getSharedPreferences("userdetails", 0);
        String loggedIn = sharedPreferences.getString("loggedIn", "");

        if (loggedIn.equals("loggedIn")){
            intent = new Intent(SplashScreen.this,DashBoardActivity.class);
        }else {
            intent = new Intent(SplashScreen.this, SendPhoneNumber.class);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(internetChkCode==0)
        {
            try {
                if (methods.isInternetOn()) {
                    updateMethod();

                } else {
                    methods.DialogInternet();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }

    }





}