package com.cynoteck.petofy.activity;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.cynoteck.petofy.R;
import com.cynoteck.petofy.response.qrCodeResponse.QrCOdeResponse;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;

import java.io.IOException;

public class ScannerQR extends AppCompatActivity {

    SurfaceView                 surfaceView;
    TextView                    textViewBarCodeValue;
    private CameraSource        cameraSource;
    private static final int    REQUEST_CAMERA_PERMISSION = 201;
    String                      intentData = "";
    MaterialCardView            back_arrow_CV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        initComponents();

    }

    private void initComponents() {
        back_arrow_CV           = findViewById(R.id.back_arrow_CV);
        textViewBarCodeValue    = findViewById(R.id.txtBarcodeValue);
        surfaceView             = findViewById(R.id.surfaceView);
        back_arrow_CV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initialiseDetectorsAndSources() {
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build();
        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                openCamera();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barCode = detections.getDetectedItems();
                if (barCode.size() > 0) {
                    setBarCode(barCode);
                }
            }
        });
    }

    private void openCamera() {
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                cameraSource.start(surfaceView.getHolder());
            } else {
                ActivityCompat.requestPermissions(this, new
                        String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                onResume();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final class JSONUtils {
        private static final Gson gson = new Gson();

        private JSONUtils() {
        }

        public static boolean isJSONValid(String jsonInString) {
            try {
                gson.fromJson(jsonInString, Object.class);
                return true;
            } catch (com.google.gson.JsonSyntaxException ex) {
                return false;
            }
        }
    }

    private void setBarCode(final SparseArray<Barcode> barCode) {
        textViewBarCodeValue.post(new Runnable() {
            @Override
            public void run() {
                intentData          = barCode.valueAt(0).displayValue;
                boolean isJsonOrNot = JSONUtils.isJSONValid(intentData); //true
                if (isJsonOrNot) {
                    Gson g = new Gson();
                    QrCOdeResponse qrCOdeResponse   = g.fromJson(intentData, QrCOdeResponse.class);
                    String veterinarianUserId       = qrCOdeResponse.getVeterinarianUserId();
                    String veterinarianName         = qrCOdeResponse.getVeterinarianName();
                    String clinicName               = qrCOdeResponse.getClinicName();
                    String profileImageUrl          = qrCOdeResponse.getProfileImageUrl();
                    String Rating                   = String.valueOf(qrCOdeResponse.getRating());
                    String key                      = qrCOdeResponse.getKey();
                    String IsInsurance              = qrCOdeResponse.getInsurance();

                    Intent intent = new Intent();
                    if (IsInsurance==null){
                        //Log.d"intentData", key + "" + veterinarianUserId);
                        intent.putExtra("veterinarianUserId", veterinarianUserId);
                        intent.putExtra("veterinarianName", veterinarianName);
                        intent.putExtra("clinicName", clinicName);
                        intent.putExtra("Rating", Rating);
                        intent.putExtra("profileImageUrl", profileImageUrl);
                    }else if (IsInsurance.equals("true")){
                        String InsuranceUrl = qrCOdeResponse.getInsuranceUrl();
                        intent.putExtra("IsInsurance", "true");
                        intent.putExtra("InsuranceUrl", InsuranceUrl);
                    }
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    finish();
                    Toast.makeText(ScannerQR.this, "INVALID QR Code!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialiseDetectorsAndSources();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, "Allow Permission for QR Scanner.", Toast.LENGTH_SHORT).show();
                onBackPressed();
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                }
            } else {
                //Log.d"NOPERMISION", "no");
            }
        } else
            finish();
    }
}
