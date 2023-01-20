package com.cynoteck.petofy.activity;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cynoteck.petofy.R;
import com.google.android.material.card.MaterialCardView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.w3c.dom.Text;

import java.util.List;

public class PermissionCheckActivity extends AppCompatActivity implements View.OnClickListener {

    Button allow_BT;
    MaterialCardView permission_MCB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_check);
        allow_BT = findViewById(R.id.allow_BT);
        permission_MCB=findViewById(R.id.permission_MCB);
        allow_BT.setOnClickListener(this);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        requestMultiplePermissions();

    }

    private void requestMultiplePermissions() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
//                            Log.d("PERMISSION","All permissions are granted by user!");
                            startActivity(new Intent(PermissionCheckActivity.this,DashBoardActivity.class));
                            permission_MCB.setVisibility(View.GONE);

                        }
//                        else {
//                            Log.d("STORAGE_DIALOG","storagePermissionDialog");
//                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            allow_BT.setText("Open Setting");
                            permission_MCB.setVisibility(View.VISIBLE);
                            Log.d("STORAGE_DIALOG","openSettingsDialog");
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
                        Log.d("DexterError",error.toString());
                        allow_BT.setText("Open Setting");
                    }
                })
                .onSameThread()
                .check();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.allow_BT:
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
//        Log.d("TAG", "onResume: hiiiii ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestMultiplePermissions();
        Log.d("TAG", "onResume: hiiiii ");
    }
}