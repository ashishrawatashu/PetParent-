package com.cynoteck.petofyparents.activity;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.api.ApiClient;
import com.cynoteck.petofyparents.api.ApiResponse;
import com.cynoteck.petofyparents.api.ApiService;
import com.cynoteck.petofyparents.parameter.uploadVetProfileImageParams.UploadProfileImageParams;
import com.cynoteck.petofyparents.parameter.uploadVetProfileImageParams.UploadVetProfileImageData;
import com.cynoteck.petofyparents.response.addPet.imageUpload.ImageResponse;
import com.cynoteck.petofyparents.utils.Config;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class ParentFullProfileActivity extends AppCompatActivity implements View.OnClickListener, ApiResponse {

    MaterialCardView back_arrow_CV, image_edit_CV, parent_mail_RL, parent_address_RL;
    RelativeLayout edit_profile_RL;
    TextView parent_name_TV, parent_email_TV, parent_phone_TV, parent_address_TV;
    ImageView parent_image_IV;
    String selctProflImage = "";
    Dialog dialog;
    private int GALLERY = 1, CAMERA = 2, UPDATE = 3, ADD_PET = 4;
    Bitmap bitmap, thumbnail;
    File catfile1 = null;
    ProgressBar parent_image_progress_bar;
    private static final String IMAGE_DIRECTORY = "/Picture";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor login_editor;
    private static final int MY_PERMISSIONS_REQUEST_READ_CAMERA = 200, MY_PERMISSIONS_REQUEST_READ_STORAGE = 300;
    Dialog storagePermissionDialog,cameraPermissionDialog;
    boolean cameraDialog= false, storageDialog= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_full_profile);
        checkStorageAndCameraPermission();
        back_arrow_CV = findViewById(R.id.back_arrow_CV);
        edit_profile_RL = findViewById(R.id.edit_profile_RL);
        image_edit_CV = findViewById(R.id.image_edit_CV);
        parent_name_TV = findViewById(R.id.parent_name_TV);
        parent_email_TV = findViewById(R.id.parent_email_TV);
        parent_phone_TV = findViewById(R.id.parent_phone_TV);
        parent_address_TV = findViewById(R.id.parent_address_TV);
        parent_address_RL = findViewById(R.id.parent_address_RL);
        parent_mail_RL = findViewById(R.id.parent_mail_RL);
        parent_image_IV = findViewById(R.id.parent_image_IV);
        parent_image_progress_bar = findViewById(R.id.parent_image_progress_bar);

        back_arrow_CV.setOnClickListener(this);
        edit_profile_RL.setOnClickListener(this);
        image_edit_CV.setOnClickListener(this);

        setParentData();

    }
    private void checkStorageAndCameraPermission() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_READ_CAMERA);
            return;
        }else if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_STORAGE);
            return;
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
                        checkStorageAndCameraPermission();
                    }
                } else {
                    cameraDialog = false;
                    showCameraPermissionDialog();
                }

                return;
            }
            case MY_PERMISSIONS_REQUEST_READ_STORAGE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (storageDialog) {
                        storagePermissionDialog.dismiss();
                        storageDialog = false;
                        checkStorageAndCameraPermission();
                    }
                } else {
                    storageDialog = false;
                    showStoragePermissionDialog();
                }
                return;
            }

        }
    }


    private void showStoragePermissionDialog() {
        storageDialog = true;
        storagePermissionDialog = new Dialog(this);
        storagePermissionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        storagePermissionDialog.setCancelable(false);
        storagePermissionDialog.setContentView(R.layout.storage_permission_dialog);
        storagePermissionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Button grant_permission_BT = storagePermissionDialog.findViewById(R.id.grant_permission_BT);
        storagePermissionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        grant_permission_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkStorageAndCameraPermission();
            }
        });
        storagePermissionDialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = storagePermissionDialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
    }
    private void showCameraPermissionDialog() {
        cameraDialog = true;
        cameraPermissionDialog = new Dialog(this);
        cameraPermissionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        cameraPermissionDialog.setCancelable(false);
        cameraPermissionDialog.setContentView(R.layout.camera_permission_dialog);
        cameraPermissionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Button grant_permission_BT = cameraPermissionDialog.findViewById(R.id.grant_permission_BT);
        cameraPermissionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        grant_permission_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkStorageAndCameraPermission();
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
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrow_CV:
                onBackPressed();
                break;

            case R.id.edit_profile_RL:
                Intent updateProfileIntent = new Intent(this, UpdateProfileActivity.class);
                startActivityForResult(updateProfileIntent, UPDATE);

                break;


            case R.id.image_edit_CV:

                selctProflImage = "1";
                showPictureDialog();

                break;
        }
    }

    private void showPictureDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_layout);
        RelativeLayout select_camera = dialog.findViewById(R.id.select_camera);
        RelativeLayout select_gallery = dialog.findViewById(R.id.select_gallery);
        RelativeLayout cancel_dialog = dialog.findViewById(R.id.cancel_dialog);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        cancel_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        select_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                takePhotoFromCamera();

            }
        });

        select_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                choosePhotoFromGallary();
            }
        });


        dialog.show();
    }

    private void choosePhotoFromGallary() {


        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPDATE) {
            if (resultCode == RESULT_OK) {
                setParentData();
            }
        } else if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);

                    parent_image_IV.setImageBitmap(bitmap);
                    saveImage(bitmap);


                } catch (IOException e) {
                    e.printStackTrace();

                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            if (data.getData() == null) {
                thumbnail = (Bitmap) data.getExtras().get("data");
                Log.e("jghl", "" + thumbnail);
                parent_image_IV.setImageBitmap(thumbnail);

                saveImage(thumbnail);
            } else {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                    Glide.with(this)
                            .load(bitmap)
                            .into(parent_image_IV);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
            Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }

    }

    private void setParentData() {
        parent_name_TV.setText(Config.user_name);
        parent_phone_TV.setText(Config.user_phone);
        if (Config.user_address.equals("")) {
            parent_address_RL.setVisibility(View.GONE);
        } else {
            parent_address_TV.setText(Config.user_address);

        }
        if (Config.user_emial!=null) {
            parent_email_TV.setText(Config.user_emial);
        } else {
            parent_mail_RL.setVisibility(View.GONE);
        }
        Glide.with(ParentFullProfileActivity.this)
                .load(Config.user_url)
                .placeholder(R.drawable.user_profile)
                .into(parent_image_IV);
    }


    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    private String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }
        try {
            catfile1 = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".png");
            catfile1.createNewFile();
            FileOutputStream fo = new FileOutputStream(catfile1);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{catfile1.getPath()},
                    new String[]{"image/png"}, null);
            fo.close();
            Log.d("TAG", "File Saved::---&gt;" + catfile1.getAbsolutePath());
            UploadImages(catfile1);
            return catfile1.getAbsolutePath();

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private void UploadImages(File absolutePath) {
        parent_image_progress_bar.setVisibility(View.VISIBLE);
        MultipartBody.Part userDpFilePart = null;
        if (absolutePath != null) {
            RequestBody userDpFile = RequestBody.create(MediaType.parse("image/*"), absolutePath);
            userDpFilePart = MultipartBody.Part.createFormData("file", absolutePath.getName(), userDpFile);
        }

        ApiService<ImageResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().uploadImages(Config.token, userDpFilePart), "UploadDocument");
        Log.e("DATALOG", "check1=> " + service);

    }

    @Override
    public void onResponse(Response response, String key) {
        switch (key) {
            case "UpdateProfileImage":
                try {
                    Log.d("UploadDocument", response.body().toString());
                    JsonObject jsonObject = new JsonObject();
                    jsonObject = (JsonObject) response.body();
                    int responseCode = Integer.parseInt(String.valueOf(jsonObject.getAsJsonObject("response").get("responseCode")));
                    if (responseCode == 109) {
//                        getUserDetails();
                        Toast.makeText(this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    } else if (responseCode == 614) {
                        Toast.makeText(this, jsonObject.getAsJsonObject("response").get("responseMessage").toString(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;


            case "UploadDocument":
                try {
                    parent_image_progress_bar.setVisibility(View.GONE);
                    Log.e("UploadDocument", response.body().toString());
                    ImageResponse imageResponse = (ImageResponse) response.body();
                    int responseCode = Integer.parseInt(imageResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        //Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                        Glide.with(this)
                                .load(imageResponse.getData().getDocumentUrl())
                                .placeholder(R.drawable.user_profile)
                                .into(parent_image_IV);
                        Config.user_url = imageResponse.getData().getDocumentUrl();
                        sharedPreferences = this.getSharedPreferences("userDetails", 0);
                        login_editor = sharedPreferences.edit();
                        login_editor.putString("profilePic", imageResponse.getData().getDocumentUrl());
                        login_editor.commit();
                        UploadProfileImageParams uploadProfileImageParams = new UploadProfileImageParams();
                        uploadProfileImageParams.setProfileImageUrl(imageResponse.getData().getDocumentUrl());
                        UploadVetProfileImageData uploadVetProfileImageData = new UploadVetProfileImageData();
                        uploadVetProfileImageData.setData(uploadProfileImageParams);
                        ApiService<JsonObject> service = new ApiService<>();
                        service.get(this, ApiClient.getApiInterface().updateProfileImage(Config.token, uploadVetProfileImageData), "UpdateProfileImage");
                        Log.d("UpdateProfileImage", uploadVetProfileImageData.toString());

                    } else if (responseCode == 614) {
                        Toast.makeText(this, imageResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    @Override
    public void onError(Throwable t, String key) {

    }
}