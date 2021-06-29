package com.cynoteck.petofyparents.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cynoteck.petofyparents.utils.PetParentSingleton;
import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.activity.AddPetRegister;
import com.cynoteck.petofyparents.activity.ChangePasswordActivity;
import com.cynoteck.petofyparents.activity.ParentFullProfileActivity;
import com.cynoteck.petofyparents.activity.PetProfileActivity;
import com.cynoteck.petofyparents.activity.SendPhoneNumber;
import com.cynoteck.petofyparents.activity.UpdateProfileActivity;
import com.cynoteck.petofyparents.adapter.PetListHorizontalAdapter;
import com.cynoteck.petofyparents.api.ApiClient;
import com.cynoteck.petofyparents.api.ApiResponse;
import com.cynoteck.petofyparents.api.ApiService;
import com.cynoteck.petofyparents.parameter.petReportsRequest.PetDataParams;
import com.cynoteck.petofyparents.parameter.petReportsRequest.PetDataRequest;
import com.cynoteck.petofyparents.parameter.uploadVetProfileImageParams.UploadProfileImageParams;
import com.cynoteck.petofyparents.parameter.uploadVetProfileImageParams.UploadVetProfileImageData;
import com.cynoteck.petofyparents.response.addPet.imageUpload.ImageResponse;
import com.cynoteck.petofyparents.response.getPetReportsResponse.getPetListResponse.GetPetListResponse;
import com.cynoteck.petofyparents.response.getPetReportsResponse.getPetListResponse.PetList;
import com.cynoteck.petofyparents.utils.Config;
import com.cynoteck.petofyparents.utils.Methods;
import com.cynoteck.petofyparents.onClicks.OnItemClickListener;
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
import java.util.Timer;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.cynoteck.petofyparents.fragments.PetRegisterFragment.registerPetAdapter;
import static com.cynoteck.petofyparents.fragments.PetRegisterFragment.total_pets_TV;

@SuppressLint("StaticFieldLeak")
public class ProfileFragment extends Fragment implements View.OnClickListener, ApiResponse, OnItemClickListener {
    TextView parent_name_TV, parent_mail_TV, parent_phone_no_TV, your_pets_TV;
    CircleImageView parent_image_CIV;
    View view;
    RelativeLayout edit_RL;
    ImageView camera_IV;
    ConstraintLayout general_details_CL, change_password_CL, plans_and_subscription_CL, privacy_CL, logout_CL;
    String selctProflImage = "";
    Dialog dialog;
    private int GALLERY = 1, CAMERA = 2, UPDATE = 3, ADD_PET = 4;
    Bitmap bitmap, thumbnail;
    File catfile1 = null;
    private static final String IMAGE_DIRECTORY = "/Picture";
    Methods methods;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor login_editor;
    ProgressBar parent_image_progress_bar;

    //    ArrayList<PetList> profileList = new ArrayList<>();
    public static PetListHorizontalAdapter petListHorizontalAdapter;
    RecyclerView pet_list_RV;
    RelativeLayout add_pet_RL;
    public static LinearLayout pet_list_LL;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        methods = new Methods(getActivity());
        requestMultiplePermissions();

        initization();
        getParentInfo();
        Timer timer = new Timer();
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        pet_list_RV.setLayoutManager(horizontalLayoutManager);
        petListHorizontalAdapter = new PetListHorizontalAdapter(getContext(), "ProfileFragment", PetParentSingleton.getInstance().getArrayList(), this);
        setPetListLayout();

        return view;

    }

    private void setPetListLayout() {
        if (!PetParentSingleton.getInstance().getArrayList().isEmpty()) {
            pet_list_LL.setVisibility(View.VISIBLE);
            total_pets_TV.setText("You have " + PetParentSingleton.getInstance().getArrayList().size() + " pets registered ");
            pet_list_RV.setAdapter(petListHorizontalAdapter);
            registerPetAdapter.notifyDataSetChanged();
            petListHorizontalAdapter.notifyDataSetChanged();
        } else {
            pet_list_LL.setVisibility(View.GONE);
            pet_list_RV.setAdapter(petListHorizontalAdapter);
            petListHorizontalAdapter.notifyDataSetChanged();
        }

    }

    private void getPetList() {
        PetDataParams getPetDataParams = new PetDataParams();
        getPetDataParams.setPageNumber(1);
        getPetDataParams.setPageSize(100);
        getPetDataParams.setSearch_Data("");
        PetDataRequest getPetDataRequest = new PetDataRequest();
        getPetDataRequest.setData(getPetDataParams);

        ApiService<GetPetListResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getPetList(Config.token, getPetDataRequest), "GetPetList");
        Log.e("DATALOG", "check1=> " + getPetDataRequest);


    }

    private void requestMultiplePermissions() {
        Dexter.withActivity(getActivity())
                .withPermissions(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
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

    @Override
    public void onResume() {
        super.onResume();
        getParentInfo();

    }

    private void getParentInfo() {
        Glide.with(this)
                .load((Config.user_url))
                .placeholder(R.drawable.user_profile)
                .into(parent_image_CIV);

        parent_name_TV.setText(Config.user_name.substring(0, 1).toUpperCase() + Config.user_name.substring(1));
        parent_mail_TV.setText(Config.user_emial);
//        parent_address_TV.setText(Config.user_address);
        parent_phone_no_TV.setText(Config.user_phone);

    }

    private void initization() {
        pet_list_LL = view.findViewById(R.id.pet_list_LL);
        your_pets_TV = view.findViewById(R.id.your_pets_TV);
        parent_image_progress_bar = view.findViewById(R.id.parent_image_progress_bar);
        plans_and_subscription_CL = view.findViewById(R.id.plans_and_subscription_CL);
        general_details_CL = view.findViewById(R.id.general_details_CL);
        privacy_CL = view.findViewById(R.id.privacy_CL);
        logout_CL = view.findViewById(R.id.logout_CL);
        change_password_CL = view.findViewById(R.id.change_password_CL);
        parent_mail_TV = view.findViewById(R.id.parent_mail_TV);
        parent_name_TV = view.findViewById(R.id.parent_name_TV);
//        parent_address_TV = view.findViewById(R.id.parent_address_TV);
        parent_phone_no_TV = view.findViewById(R.id.parent_phone_no_TV);
        camera_IV = view.findViewById(R.id.camera_IV);
        parent_image_CIV = view.findViewById(R.id.parent_image_CIV);
        edit_RL = view.findViewById(R.id.edit_RL);
        add_pet_RL = view.findViewById(R.id.add_pet_RL);
        pet_list_RV = view.findViewById(R.id.pet_list_RV);
        edit_RL.setOnClickListener(this);
        general_details_CL.setOnClickListener(this);
        plans_and_subscription_CL.setOnClickListener(this);
        privacy_CL.setOnClickListener(this);
        change_password_CL.setOnClickListener(this);
        logout_CL.setOnClickListener(this);
        camera_IV.setOnClickListener(this);
        add_pet_RL.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.add_pet_RL:
                Intent adNewIntent = new Intent(getContext(), AddPetRegister.class);
                adNewIntent.putExtra("intent_from", "add");
                startActivityForResult(adNewIntent, ADD_PET);

                break;

            case R.id.general_details_CL:
                Intent parentFullProfileIntent = new Intent(getContext(), ParentFullProfileActivity.class);
                startActivity(parentFullProfileIntent);
                break;

            case R.id.camera_IV:
                selctProflImage = "1";
                showPictureDialog();

                break;

            case R.id.edit_RL:
                Intent updateProfileIntent = new Intent(getActivity(), UpdateProfileActivity.class);
                startActivityForResult(updateProfileIntent, UPDATE);

                break;


            case R.id.change_password_CL:

                Intent changePass = new Intent(getContext(), ChangePasswordActivity.class);
                startActivity(changePass);
                break;

            case R.id.logout_CL:
                SharedPreferences preferences = getActivity().getSharedPreferences("userDetails", 0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                editor.commit();
                PetParentSingleton.getInstance().getArrayList().clear();
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

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UPDATE) {
            if (resultCode == RESULT_OK) {
                parent_name_TV.setText(Config.first_name + " " + Config.last_name);
                parent_mail_TV.setText(Config.user_emial);
                parent_phone_no_TV.setText(Config.user_phone);
            }
        } else if (requestCode == ADD_PET) {
            if (resultCode == RESULT_OK) {
                PetList petList = new PetList();
                petList.setId(data.getStringExtra("pet_id"));
                petList.setPetUniqueId(data.getStringExtra("pet_unique_id"));
                petList.setPetProfileImageUrl(data.getStringExtra("pet_image_url"));
                petList.setPetBreed(data.getStringExtra("pet_breed"));
                petList.setPetAge(data.getStringExtra("pet_age"));
                petList.setPetSex(data.getStringExtra("pet_sex"));
                petList.setPetName(data.getStringExtra("pet_name"));
                petList.setPetCategory(data.getStringExtra("pet_category"));
                petList.setDateOfBirth(data.getStringExtra("pet_date_of_birth"));
                petList.setPetParentName(data.getStringExtra("pet_parent"));
                petList.setPetColor(data.getStringExtra("pet_color"));
//                profileList.add(0, petList);
                PetParentSingleton.getInstance().getArrayList().add(0, petList);
                total_pets_TV.setText("You have " + PetParentSingleton.getInstance().getArrayList().size() + " pets registered ");
                registerPetAdapter.notifyDataSetChanged();
                petListHorizontalAdapter.notifyDataSetChanged();
            }
        } else if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), contentURI);

                    parent_image_CIV.setImageBitmap(bitmap);
                    saveImage(bitmap);


                } catch (IOException e) {
                    e.printStackTrace();

                    Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            if (data.getData() == null) {
                thumbnail = (Bitmap) data.getExtras().get("data");
                Log.e("jghl", "" + thumbnail);
                parent_image_CIV.setImageBitmap(thumbnail);

                saveImage(thumbnail);
            } else {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), data.getData());
                    Glide.with(this)
                            .load(bitmap)
                            .into(parent_image_CIV);
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
        switch (key) {
            case "UpdateProfileImage":
                try {
                    Log.d("UploadDocument", response.body().toString());
                    JsonObject jsonObject = new JsonObject();
                    jsonObject = (JsonObject) response.body();
                    int responseCode = Integer.parseInt(String.valueOf(jsonObject.getAsJsonObject("response").get("responseCode")));
                    if (responseCode == 109) {

//                        getUserDetails();
                        Toast.makeText(getContext(), "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    } else if (responseCode == 614) {
                        Toast.makeText(getContext(), jsonObject.getAsJsonObject("response").get("responseMessage").toString(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Please Try Again !", Toast.LENGTH_SHORT).show();
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
                                .into(parent_image_CIV);
                        Config.user_url = imageResponse.getData().getDocumentUrl();
                        sharedPreferences = getContext().getSharedPreferences("userDetails", 0);
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
                        Toast.makeText(getActivity(), imageResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;


            case "GetPetList":
                try {
                    GetPetListResponse getPetListResponse = (GetPetListResponse) response.body();
                    int responseCode = Integer.parseInt(getPetListResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        if (getPetListResponse.getData().getPetList().isEmpty()) {

                        } else {

                            if (getPetListResponse.getData().getPetList().size() > 0) {
                                for (int i = 0; i < getPetListResponse.getData().getPetList().size(); i++) {
                                    PetList petList = new PetList();
                                    petList.setPetUniqueId(getPetListResponse.getData().getPetList().get(i).getPetUniqueId());
                                    petList.setDateOfBirth(getPetListResponse.getData().getPetList().get(i).getDateOfBirth());
                                    petList.setPetName(getPetListResponse.getData().getPetList().get(i).getPetName());
                                    petList.setPetSex(getPetListResponse.getData().getPetList().get(i).getPetSex());
                                    petList.setPetParentName(getPetListResponse.getData().getPetList().get(i).getPetParentName());
                                    petList.setPetProfileImageUrl(getPetListResponse.getData().getPetList().get(i).getPetProfileImageUrl());
                                    petList.setEncryptedId(getPetListResponse.getData().getPetList().get(i).getEncryptedId());
                                    petList.setId(getPetListResponse.getData().getPetList().get(i).getId());
                                    petList.setPetAge(getPetListResponse.getData().getPetList().get(i).getPetAge());
                                    petList.setPetCategoryId(getPetListResponse.getData().getPetList().get(i).getPetCategoryId());
                                    petList.setLastVisitEncryptedId(getPetListResponse.getData().getPetList().get(i).getLastVisitEncryptedId());
                                    petList.setPetBreed(getPetListResponse.getData().getPetList().get(i).getPetBreed());
                                    petList.setPetCategory(getPetListResponse.getData().getPetList().get(i).getPetCategory());
                                    petList.setPetColor(getPetListResponse.getData().getPetList().get(i).getPetColor());

//                                    profileList.add(petList);
//                                    Config.profileListLoaded.add(petList);
                                    PetParentSingleton.getInstance().getArrayList().add(petList);


                                }
//                                Config.isLoaded = true;
                                LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                                pet_list_RV.setLayoutManager(horizontalLayoutManager);
                                petListHorizontalAdapter = new PetListHorizontalAdapter(getContext(), "ProfileFragment", PetParentSingleton.getInstance().getArrayList(), this);
                                pet_list_RV.setAdapter(petListHorizontalAdapter);
                                petListHorizontalAdapter.notifyDataSetChanged();
                            }
                        }


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

    @Override
    public void onViewDetailsClick(int position) {
        Intent intent = new Intent(getActivity(), PetProfileActivity.class);
        intent.putExtra("pet_list_position", String.valueOf(position));
        intent.putExtra("pet_id", PetParentSingleton.getInstance().getArrayList().get(position).getId().substring(0, PetParentSingleton.getInstance().getArrayList().get(position).getId().length() - 2));
        intent.putExtra("pet_unique_id", PetParentSingleton.getInstance().getArrayList().get(position).getPetUniqueId());
        intent.putExtra("pet_image_url", PetParentSingleton.getInstance().getArrayList().get(position).getPetProfileImageUrl());
        intent.putExtra("pet_breed", PetParentSingleton.getInstance().getArrayList().get(position).getPetBreed());
        intent.putExtra("pet_age", PetParentSingleton.getInstance().getArrayList().get(position).getPetAge());
        intent.putExtra("pet_sex", PetParentSingleton.getInstance().getArrayList().get(position).getPetSex());
        intent.putExtra("pet_name", PetParentSingleton.getInstance().getArrayList().get(position).getPetName());
        intent.putExtra("pet_category", PetParentSingleton.getInstance().getArrayList().get(position).getPetCategory());
        intent.putExtra("pet_DOB", PetParentSingleton.getInstance().getArrayList().get(position).getDateOfBirth());
        intent.putExtra("pet_color", PetParentSingleton.getInstance().getArrayList().get(position).getPetColor());
        startActivity(intent);
    }

}