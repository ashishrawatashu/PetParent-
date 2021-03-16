package com.cynoteck.petofyparents.fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.activty.ChangePasswordActivity;
import com.cynoteck.petofyparents.activty.GetPetDetailsActivity;
import com.cynoteck.petofyparents.activty.LoginActivity;
import com.cynoteck.petofyparents.activty.SendPhoneNumber;
import com.cynoteck.petofyparents.activty.SettingActivity;
import com.cynoteck.petofyparents.activty.UpdateProfileActivity;
import com.cynoteck.petofyparents.api.ApiClient;
import com.cynoteck.petofyparents.api.ApiResponse;
import com.cynoteck.petofyparents.api.ApiService;
import com.cynoteck.petofyparents.parameter.uploadVetProfileImageParams.UploadProfileImageParams;
import com.cynoteck.petofyparents.parameter.uploadVetProfileImageParams.UploadVetProfileImageData;
import com.cynoteck.petofyparents.response.addPet.imageUpload.ImageResponse;
import com.cynoteck.petofyparents.utils.Config;
import com.cynoteck.petofyparents.utils.Methods;
import com.google.gson.JsonObject;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.media.MediaRecorder.VideoSource.CAMERA;


public class ProfileFragment extends Fragment implements View.OnClickListener, ApiResponse {
    TextView tv,heder,parent_name_TV,parent_email_TV,parent_address_TV,parent_phone_TV;
    ImageView parent_profile_pic,edit_image,camera_IV;
    View view;
    RelativeLayout setings_layout,logout_layout,changePass_layout;
    String selctProflImage="";
    Dialog dialog;
    private int GALLERY = 1, CAMERA = 2, UPDATE=3;
    Bitmap bitmap, thumbnail;
    File catfile1 = null;
    private static final String IMAGE_DIRECTORY = "/Picture";
    Methods methods;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor login_editor;
    public ProfileFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        methods=new Methods(getActivity());
        requestMultiplePermissions();

        initization();
        getParentInfo();

        return view;

    }

    private void requestMultiplePermissions() {
        Dexter.withActivity(getActivity())
                .withPermissions(
                        android.Manifest.permission.CAMERA,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
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
                        Toast.makeText(getActivity(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private void getParentInfo() {
        Glide.with(this)
                .load(Config.user_url).placeholder(R.drawable.user_profile)
                .into(parent_profile_pic);
        parent_name_TV.setText(Config.user_name.substring(0, 1).toUpperCase() + Config.user_name.substring(1));
        parent_email_TV.setText(Config.user_emial);
        parent_address_TV.setText(Config.user_address);
        parent_phone_TV.setText(Config.user_phone);

    }

    private void initization() {
        camera_IV=view.findViewById(R.id.camera_IV);
        setings_layout=view.findViewById(R.id.setings_layout);
        logout_layout=view.findViewById(R.id.logout_layout);
        changePass_layout=view.findViewById(R.id.changePass_layout);
        parent_email_TV = view.findViewById(R.id.parent_email_TV);
        parent_name_TV = view.findViewById(R.id.parent_name_TV);
        parent_address_TV = view.findViewById(R.id.parent_address_TV);
        parent_phone_TV = view.findViewById(R.id.parent_phone_TV);
        parent_profile_pic = view.findViewById(R.id.parent_profile_pic);
        edit_image=view.findViewById(R.id.edit_image);

        edit_image.setOnClickListener(this);
        setings_layout.setOnClickListener(this);
        logout_layout.setOnClickListener(this);
        changePass_layout.setOnClickListener(this);
        camera_IV.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.camera_IV:
                selctProflImage = "1";
                showPictureDialog();


                break;

            case R.id.edit_image:
                Intent updateProfileIntent = new Intent(getActivity(), UpdateProfileActivity.class);
                startActivityForResult(updateProfileIntent,UPDATE);

                break;


            case R.id.changePass_layout:

                Intent changePass = new Intent(getContext(), ChangePasswordActivity.class);
                startActivity(changePass);
                break;
            case R.id.setings_layout:
                Intent setting = new Intent(getContext(), SettingActivity.class);
                startActivity(setting);

                break;

            case R.id.logout_layout:
                SharedPreferences preferences =getActivity().getSharedPreferences("userdetails",0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                editor.commit();
                startActivity(new Intent(getActivity(), SendPhoneNumber.class));
                getActivity().finish();
                break;

        }
    }

    private void showPictureDialog() {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_layout);

        TextView select_camera = (TextView) dialog.findViewById(R.id.select_camera);
        TextView select_gallery = (TextView) dialog.findViewById(R.id.select_gallery);
        TextView cancel_dialog = (TextView) dialog.findViewById(R.id.cancel_dialog);
        cancel_dialog.setVisibility(View.INVISIBLE);

        select_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhotoFromCamera();
            }
        });

        select_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePhotoFromGallary();
            }
        });


        dialog.show();
    }
    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==UPDATE){

                parent_name_TV.setText(Config.first_name+" "+Config.last_name);
                parent_email_TV.setText(Config.user_emial);
                parent_address_TV.setText(Config.user_address);
                parent_phone_TV.setText(Config.user_phone);

            }
        }

        if (requestCode == GALLERY) {
            if (data != null) {
                dialog.dismiss();
                Uri contentURI = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), contentURI);

                    parent_profile_pic.setImageBitmap(bitmap);
                    saveImage(bitmap);


                } catch (IOException e) {
                    e.printStackTrace();

                    Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        }
        else if (requestCode == CAMERA) {
            dialog.dismiss();
            if (data.getData() == null)
            {
                thumbnail = (Bitmap) data.getExtras().get("data");
                Log.e("jghl",""+thumbnail);
                parent_profile_pic.setImageBitmap(thumbnail);

                saveImage(thumbnail);
            }

            else{
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), data.getData());
                    Glide.with(this)
                            .load(bitmap)
                            .into(parent_profile_pic);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
            Toast.makeText(getActivity(), "Image Saved!", Toast.LENGTH_SHORT).show();
        }

        return;
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
            MediaScannerConnection.scanFile(getContext(),
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
        methods.showCustomProgressBarDialog(getActivity());
        MultipartBody.Part userDpFilePart = null;
        if (absolutePath != null) {
            RequestBody userDpFile = RequestBody.create(MediaType.parse("image/*"), absolutePath);
            userDpFilePart = MultipartBody.Part.createFormData("file", absolutePath.getName(), userDpFile);
        }

        ApiService<ImageResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().uploadImages(Config.token,userDpFilePart), "UploadDocument");
        Log.e("DATALOG","check1=> "+service);

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
    public void onResponse(Response response, String key) {
        switch (key){
            case "UpdateProfileImage":
                try {
                    methods.customProgressDismiss();
                    Log.d("UploadDocument",response.body().toString());
                    JsonObject jsonObject = new JsonObject();
                    jsonObject = (JsonObject) response.body();
                    int responseCode = Integer.parseInt(String.valueOf(jsonObject.getAsJsonObject("response").get("responseCode")));
                    if (responseCode== 109){

//                        getUserDetails();
                        Toast.makeText(getContext(), "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    }else if (responseCode==614){
                        Toast.makeText(getContext(), jsonObject.getAsJsonObject("response").get("responseMessage").toString(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getContext(), "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }

                break;


            case "UploadDocument":
                try {
                    Log.e("UploadDocument",response.body().toString());
                    ImageResponse imageResponse = (ImageResponse) response.body();
                    int responseCode = Integer.parseInt(imageResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        //Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                        Glide.with(this)
                                .load(imageResponse.getData().getDocumentUrl()).placeholder(R.drawable.user_profile)
                                .into(parent_profile_pic);
                        Config.user_url = imageResponse.getData().getDocumentUrl();
                        sharedPreferences = getContext().getSharedPreferences("userdetails", 0);
                        login_editor = sharedPreferences.edit();
                        login_editor.putString("profilePic",imageResponse.getData().getDocumentUrl());
                        login_editor.commit();
                        UploadProfileImageParams uploadProfileImageParams = new UploadProfileImageParams();
                        uploadProfileImageParams.setProfileImageUrl(imageResponse.getData().getDocumentUrl());
                        UploadVetProfileImageData uploadVetProfileImageData = new UploadVetProfileImageData();
                        uploadVetProfileImageData.setData(uploadProfileImageParams);
                        ApiService<JsonObject> service = new ApiService<>();
                        service.get(this, ApiClient.getApiInterface().updateProfileImage(Config.token,uploadVetProfileImageData), "UpdateProfileImage");
                        Log.d("UpdateProfileImage",uploadVetProfileImageData.toString());

                    }else if (responseCode==614){
                        Toast.makeText(getActivity(), imageResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(), "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    @Override
    public void onError(Throwable t, String key) {

    }
}