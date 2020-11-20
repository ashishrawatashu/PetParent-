package com.cynoteck.petofyparents.activty;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.util.Calendar;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.api.ApiClient;
import com.cynoteck.petofyparents.api.ApiResponse;
import com.cynoteck.petofyparents.api.ApiService;
import com.cynoteck.petofyparents.parameter.donateParamRequest.DonatePetParameter;
import com.cynoteck.petofyparents.parameter.donateParamRequest.DonatePetRequest;
import com.cynoteck.petofyparents.parameter.petBreedRequest.BreedParams;
import com.cynoteck.petofyparents.parameter.petBreedRequest.BreedRequest;
import com.cynoteck.petofyparents.parameter.updateDonation.UpdateDonationRequest;
import com.cynoteck.petofyparents.parameter.updateDonation.UpdatedonationParamter;
import com.cynoteck.petofyparents.response.addPet.breedResponse.BreedCatRespose;
import com.cynoteck.petofyparents.response.addPet.imageUpload.ImageResponse;
import com.cynoteck.petofyparents.response.addPet.petAgeResponse.PetAgeValueResponse;
import com.cynoteck.petofyparents.response.addPet.petColorResponse.PetColorValueResponse;
import com.cynoteck.petofyparents.response.addPet.petSizeResponse.PetSizeValueResponse;
import com.cynoteck.petofyparents.response.cityResponse.CityResponseModel;
import com.cynoteck.petofyparents.response.stateResponse.StateResponse;
import com.cynoteck.petofyparents.response.updateProfileResponse.PetTypeResponse;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class DoYouWantAdoptActivity extends AppCompatActivity implements View.OnClickListener, ApiResponse {

    AppCompatSpinner add_pet_type,add_pet_sex_dialog,add_pet_size_dialog,add_pet_age_dialog,add_pet_breed_dialog,
            add_pet_color_dialog,state_spinner,city_spinner;
    EditText pet_name_ET,pet_conatct_ET,pet_addrs_ET,pet_desc_ET;
    TextView pet_parent_name_TV;
    ImageView service_cat_img_one,service_cat_img_two,service_cat_img_three,service_cat_img_four,service_cat_img_five;
    Button submit_BT,reset_BT;

    Methods methods;
    ArrayList<String> petType,petBreed,petAge,petColor,petSize,petSex,state,city;
    HashMap<String,String> petTypeHashMap,petBreedHashMap,petAgeHashMap,petColorHashMap,petSizeHashMap,petSexHashMap,stateHashMap,cityHasMap;
    String strSpnerItemPetNm="",getStrSpnerItemPetNmId="",strSpnrBreed="",strSpnrBreedId="",strSpnrAge="",
           strSpnrAgeId="",strSpnrColor="",strSpnrColorId="",strSpnrSize="",strSpneSizeId="",strSpnrSex="",
           strSpnrSexId="",strSpnrState="",strSpnrStateId="",strSpinnerCity="",strSpnrCityId="",strPetName="",
           strPetParentName="",strPhoneNumber="",strAddress="",strDescription="",selctImgOne="0",selctImgtwo="0",
           slctImgThree="0",slctImgFour="0",slctImgFive="0",strFirstImgUrl="",strSecondImgUrl="",
           strThirdImgUrl="",strFourthImUrl="",strFifthImgUrl="",petId="",strEncryptedId="",strType="" ;

    private static final String IMAGE_DIRECTORY = "/Picture";
    private int GALLERY = 1, CAMERA = 2;

    File fileImg1 = null;
    File fileImg2 = null;
    File fileImg3 = null;
    File fileImg4 = null;
    File fileImg5 = null;
    Bitmap bitmap, thumbnail;
    String capImage;

    Dialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopt_pet);
        requestMultiplePermissions();
        initialize();
    }

    public void initialize()
    {
        methods=new Methods(this);
        add_pet_type=findViewById(R.id.add_pet_type);
        add_pet_sex_dialog=findViewById(R.id.add_pet_sex_dialog);
        add_pet_size_dialog=findViewById(R.id.add_pet_size_dialog);
        add_pet_age_dialog=findViewById(R.id.add_pet_age_dialog);
        add_pet_breed_dialog=findViewById(R.id.add_pet_breed_dialog);
        add_pet_color_dialog=findViewById(R.id.add_pet_color_dialog);
        state_spinner=findViewById(R.id.state_spinner);
        city_spinner=findViewById(R.id.city_spinner);

        pet_name_ET=findViewById(R.id.pet_name_ET);
        pet_conatct_ET=findViewById(R.id.pet_conatct_ET);
        pet_addrs_ET=findViewById(R.id.pet_addrs_ET);
        pet_desc_ET=findViewById(R.id.pet_desc_ET);

        pet_parent_name_TV=findViewById(R.id.pet_parent_name_TV);

        pet_parent_name_TV.setText(Config.user_name);

        service_cat_img_one=findViewById(R.id.service_cat_img_one);
        service_cat_img_two=findViewById(R.id.service_cat_img_two);
        service_cat_img_three=findViewById(R.id.service_cat_img_three);
        service_cat_img_four=findViewById(R.id.service_cat_img_four);
        service_cat_img_five=findViewById(R.id.service_cat_img_five);

        submit_BT=findViewById(R.id.submit_BT);
        reset_BT=findViewById(R.id.reset_BT);

        submit_BT.setOnClickListener(this);
        reset_BT.setOnClickListener(this);
        service_cat_img_one.setOnClickListener(this);
        service_cat_img_two.setOnClickListener(this);
        service_cat_img_three.setOnClickListener(this);
        service_cat_img_four.setOnClickListener(this);
        service_cat_img_five.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();

        if(extras!=null)
        {
            strType=extras.getString("type");

            if(strType.equals("update"))
            {
                submit_BT.setText("UPDATE");
                reset_BT.setText("CANCEL");
            }
            else
            {
                submit_BT.setText("SUBMIT");
                reset_BT.setText("RESET");
            }

            petId=extras.getString("id");
            strPetName=extras.getString("petName");
            pet_name_ET.setText(strPetName);
            strDescription=extras.getString("petDescription");
            pet_desc_ET.setText(strDescription);
            strAddress=extras.getString("petAddress");
            pet_addrs_ET.setText(strAddress);
            strSpnerItemPetNm=extras.getString("petCategory");
            strSpnrSex=extras.getString("petSex");
            strSpnrAge=extras.getString("petAge");
            strSpnrSize=extras.getString("petSize");
            strSpnrColor=extras.getString("petColor");
            strSpnrBreed=extras.getString("petBreed");
            strSpnrState=extras.getString("state");
            strSpinnerCity=extras.getString("city");
            strPhoneNumber=extras.getString("phoneNumber");
            if(strType.equals("update"))
            pet_conatct_ET.setText(strPhoneNumber);
            else
            pet_conatct_ET.setText(Config.user_phone);
            strEncryptedId=extras.getString("encryptedId");
            strFirstImgUrl=extras.getString("imageOne");
            strSecondImgUrl=extras.getString("imageTwo");
            strThirdImgUrl=extras.getString("imageThree");
            strFourthImUrl=extras.getString("imageFour");
            strFifthImgUrl=extras.getString("imageFive");
        }

        petSex=new ArrayList<>();
        petSex.add("Pet Sex");
        petSex.add("Male");
        petSex.add("Female");

        petSexHashMap=new HashMap<>();
        petSexHashMap.put("Pet Sex","0");
        petSexHashMap.put("Male","1");
        petSexHashMap.put("Female","2");

        if(methods.isInternetOn())
        {
                petType();
                setSpinnerPetSex();
                getState();
                getCity();


        }
        else
            methods.DialogInternet();


    }

    private void petType() {
        methods.showCustomProgressBarDialog(this);
        ApiService<PetTypeResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().petTypeApi(Config.token), "GetPetTypes");
    }

    private void getPetBreed() {
        BreedRequest breedRequest = new BreedRequest();
        breedRequest.setGetAll("false");
        if(!getStrSpnerItemPetNmId.equals("0"))
            breedRequest.setPetCategoryId(getStrSpnerItemPetNmId);
        else
            breedRequest.setPetCategoryId("1");
        BreedParams breedParams = new BreedParams();
        breedParams.setData(breedRequest);

        ApiService<BreedCatRespose> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getGetPetBreedApi(Config.token,breedParams), "GetPetBreed");
        Log.d("Diolog_Breed","===>"+breedParams);
    }
    private void getPetAge() {
        BreedRequest breedRequest = new BreedRequest();
        breedRequest.setGetAll("false");
        if(!getStrSpnerItemPetNmId.equals("0"))
            breedRequest.setPetCategoryId(getStrSpnerItemPetNmId);
        else
            breedRequest.setPetCategoryId("1");
        BreedParams breedParams = new BreedParams();
        breedParams.setData(breedRequest);

        ApiService<PetAgeValueResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getGetPetAgeApi(Config.token,breedParams), "GetPetAge");
    }

    private void getPetColor() {
        BreedRequest breedRequest = new BreedRequest();
        breedRequest.setGetAll("false");
        if(!getStrSpnerItemPetNmId.equals("0"))
            breedRequest.setPetCategoryId(getStrSpnerItemPetNmId);
        else
            breedRequest.setPetCategoryId("1");
        BreedParams breedParams = new BreedParams();
        breedParams.setData(breedRequest);

        ApiService<PetColorValueResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getGetPetColorApi(Config.token,breedParams), "GetPetColor");
    }

    private void getPetSize() {
        BreedRequest breedRequest = new BreedRequest();
        breedRequest.setGetAll("false");
        if(!getStrSpnerItemPetNmId.equals("0"))
            breedRequest.setPetCategoryId(getStrSpnerItemPetNmId);
        else
            breedRequest.setPetCategoryId("1");
        BreedParams breedParams = new BreedParams();
        breedParams.setData(breedRequest);

        ApiService<PetSizeValueResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getGetPetSizeApi(Config.token,breedParams), "GetPetSize");
    }

    private void getState()
    {
        ApiService<StateResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getState(), "getState");
    }

    private void getCity()
    {
        ApiService<CityResponseModel> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getCity(), "GetCity");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.service_cat_img_one:
                selctImgOne="1";
                showPictureDialog();
                break;
            case R.id.service_cat_img_two:
                selctImgtwo="1";
                showPictureDialog();
                break;
            case R.id.service_cat_img_three:
                slctImgThree="1";
                showPictureDialog();
                break;
            case R.id.service_cat_img_four:
                slctImgFour="1";
                showPictureDialog();
                break;
            case R.id.service_cat_img_five:
                slctImgFive="1";
                showPictureDialog();
                break;
            case R.id.submit_BT:

                strPetName=pet_name_ET.getText().toString();
                strPetParentName=pet_parent_name_TV.getText().toString();
                strPhoneNumber=pet_conatct_ET.getText().toString();
                strAddress=pet_addrs_ET.getText().toString();
                strDescription=pet_desc_ET.getText().toString();

                if(strPetName.isEmpty())
                {
                    Toast.makeText(this, "Enter Pet Name", Toast.LENGTH_SHORT).show();
                    pet_name_ET.setError("Enter Pet Name");
                    pet_conatct_ET.setError(null);
                    pet_addrs_ET.setError(null);
                }

                else if(strPhoneNumber.isEmpty())
                {
                    Toast.makeText(this, "Enter Valid Phone Number", Toast.LENGTH_SHORT).show();
                    pet_name_ET.setError(null);
                    pet_conatct_ET.setError("Enter Valid Phone Number");
                    pet_addrs_ET.setError(null);
                }

                else if(strAddress.isEmpty())
                {
                    Toast.makeText(this, "Enter Address", Toast.LENGTH_SHORT).show();
                    pet_name_ET.setError(null);
                    pet_conatct_ET.setError(null);
                    pet_addrs_ET.setError("Enter Address");
                }

                else if(strSpnerItemPetNm.equals("Select Pet Type"))
                {
                    Toast.makeText(this, "Select Pet Type", Toast.LENGTH_SHORT).show();
                    pet_name_ET.setError(null);
                    pet_conatct_ET.setError(null);
                    pet_addrs_ET.setError(null);
                }

                else if(strSpnrBreed.equals("Pet Breed"))
                {
                    Toast.makeText(this, "Select Pet Breed", Toast.LENGTH_SHORT).show();
                    pet_name_ET.setError(null);
                    pet_conatct_ET.setError(null);
                    pet_addrs_ET.setError(null);
                }

                else if(strSpnrAge.equals("Select Pet Age"))
                {
                    Toast.makeText(this, "Select Pet Age", Toast.LENGTH_SHORT).show();
                    pet_name_ET.setError(null);
                    pet_conatct_ET.setError(null);
                    pet_addrs_ET.setError(null);

                }

                else if(strSpnrColor.equals("Pet Color"))
                {
                    Toast.makeText(this, "Select Pet Color", Toast.LENGTH_SHORT).show();
                    pet_name_ET.setError(null);
                    pet_conatct_ET.setError(null);
                    pet_addrs_ET.setError(null);
                }

                else if(strSpnrSize.equals("Pet Size"))
                {
                    Toast.makeText(this, "Select Pet Size", Toast.LENGTH_SHORT).show();
                    pet_name_ET.setError(null);
                    pet_conatct_ET.setError(null);
                    pet_addrs_ET.setError(null);
                }

                else if(strSpnrState.equals("Select State"))
                {
                    Toast.makeText(this, "Select Pet State", Toast.LENGTH_SHORT).show();
                    pet_name_ET.setError(null);
                    pet_conatct_ET.setError(null);
                    pet_addrs_ET.setError(null);
                }

                else if(strSpinnerCity.equals("Select City"))
                {
                    Toast.makeText(this, "Select Pet City", Toast.LENGTH_SHORT).show();
                    pet_name_ET.setError(null);
                    pet_conatct_ET.setError(null);
                    pet_addrs_ET.setError(null);
                }

                if(strType.equals("update"))
                {
                    pet_name_ET.setError(null);
                    pet_conatct_ET.setError(null);
                    pet_addrs_ET.setError(null);

                    UpdatedonationParamter updatedonationParamter=new UpdatedonationParamter();
                    updatedonationParamter.setId(petId);
                    updatedonationParamter.setPetCategoryId(getStrSpnerItemPetNmId);
                    updatedonationParamter.setPetSexId(strSpnrSexId);
                    updatedonationParamter.setPetAgeId(strSpnrAgeId);
                    updatedonationParamter.setPetSizeId(strSpneSizeId);
                    updatedonationParamter.setPetColorId(strSpnrColorId);
                    updatedonationParamter.setPetBreedId(strSpnrBreedId);
                    updatedonationParamter.setPetName(strPetName);
                    updatedonationParamter.setDescription(strDescription);
                    updatedonationParamter.setAddress(strAddress);
                    updatedonationParamter.setCityId(strSpnrCityId);
                    updatedonationParamter.setStateId(strSpnrStateId);
                    updatedonationParamter.setFirstServiceImageUrl(strFirstImgUrl);
                    updatedonationParamter.setSecondServiceImageUrl(strSecondImgUrl);
                    updatedonationParamter.setThirdServiceImageUrl(strThirdImgUrl);
                    updatedonationParamter.setFourthServiceImageUrl(strFourthImUrl);
                    updatedonationParamter.setFifthServiceImageUrl(strFifthImgUrl);
                    updatedonationParamter.setEncryptedId("");
                    updatedonationParamter.setDonarName(strPetParentName);
                    updatedonationParamter.setPhoneNumber(strPhoneNumber);

                    UpdateDonationRequest updateDonationRequest=new UpdateDonationRequest();
                    updateDonationRequest.setData(updatedonationParamter);

                    if(methods.isInternetOn())
                    {
                        ApiService<JsonObject> service = new ApiService<>();
                        service.get(this, ApiClient.getApiInterface().updateDonationRequest(Config.token,updateDonationRequest), "update-donation-request");
                        Log.e("UPDTDONATEDATA","====>"+updateDonationRequest);
                    }
                    else
                        methods.DialogInternet();

                }
                else
                {
                        pet_name_ET.setError(null);
                        pet_conatct_ET.setError(null);
                        pet_addrs_ET.setError(null);

                        DonatePetParameter donatePetParameter=new DonatePetParameter();
                        donatePetParameter.setPetCategoryId(getStrSpnerItemPetNmId);
                        donatePetParameter.setPetSexId(strSpnrSexId);
                        donatePetParameter.setPetAgeId(strSpnrAgeId);
                        donatePetParameter.setPetSizeId(strSpneSizeId);
                        donatePetParameter.setPetColorId(strSpnrColorId);
                        donatePetParameter.setPetBreedId(strSpnrBreedId);
                        donatePetParameter.setPetName(strPetName);
                        donatePetParameter.setDescription(strDescription);
                        donatePetParameter.setAddress(strAddress);
                        donatePetParameter.setCityId(strSpnrCityId);
                        donatePetParameter.setStateId(strSpnrStateId);
                        donatePetParameter.setFirstServiceImageUrl(strFirstImgUrl);
                        donatePetParameter.setSecondServiceImageUrl(strSecondImgUrl);
                        donatePetParameter.setThirdServiceImageUrl(strThirdImgUrl);
                        donatePetParameter.setFourthServiceImageUrl(strFourthImUrl);
                        donatePetParameter.setFifthServiceImageUrl(strFifthImgUrl);
                        donatePetParameter.setEncryptedId("");
                        donatePetParameter.setDonarName(strPetParentName);
                        donatePetParameter.setPhoneNumber(strPhoneNumber);

                        DonatePetRequest donatePetRequest=new DonatePetRequest();
                        donatePetRequest.setData(donatePetParameter);

                        if(methods.isInternetOn())
                        {
                            ApiService<JsonObject> service = new ApiService<>();
                            service.get(this, ApiClient.getApiInterface().donateAPet(Config.token,donatePetRequest), "donate-a-pet");
                            Log.e("DONATEDATA","====>"+donatePetRequest);
                        }
                        else
                            methods.DialogInternet();

                }


                break;
            case R.id.reset_BT:
                if(strType.equals("update"))
                {
                   onBackPressed();
                }
                else
                {
                    this.startActivity(new Intent(v.getContext(), DoYouWantAdoptActivity.class));
                    this.overridePendingTransition(0, 0);
                    finish();
                }

                break;
        }

    }


    private void showPictureDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_layout);

        TextView select_camera = (TextView) dialog.findViewById(R.id.select_camera);
        TextView select_gallery = (TextView) dialog.findViewById(R.id.select_gallery);
        TextView cancel_dialog = (TextView) dialog.findViewById(R.id.cancel_dialog);

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

        cancel_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selctImgOne.equals("1")){
                    selctImgOne="0";
                }
                if(selctImgtwo.equals("1")){
                    selctImgtwo="0";
                }
                if(slctImgThree.equals("1")){
                    slctImgThree="0";
                }
                if(slctImgFour.equals("1")){
                    slctImgFour="0";
                }
                if(slctImgFive.equals("1")){
                    slctImgFive="0";
                }
                dialog.dismiss();
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

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        dialog.dismiss();
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {

                Uri contentURI = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), contentURI);

                    if(selctImgOne.equals("1")){
                        service_cat_img_one.setImageBitmap(bitmap);
                        saveImage(bitmap);
                    }
                    if(selctImgtwo.equals("1")){
                        service_cat_img_two.setImageBitmap(bitmap);
                        saveImage(bitmap);
                    }
                    if(slctImgThree.equals("1")){
                        service_cat_img_three.setImageBitmap(bitmap);
                        saveImage(bitmap);
                    }
                    if(slctImgFour.equals("1")){
                        service_cat_img_four.setImageBitmap(bitmap);
                        saveImage(bitmap);
                    }
                    if(slctImgFive.equals("1")){
                        service_cat_img_five.setImageBitmap(bitmap);
                        saveImage(bitmap);
                    }



                } catch (IOException e) {
                    e.printStackTrace();

                    if(selctImgOne.equals("1")){
                        selctImgOne="0";
                    }
                    if(selctImgtwo.equals("1")){
                        selctImgtwo="0";
                    }
                    if(slctImgThree.equals("1")){
                        slctImgThree="0";
                    }
                    if(slctImgFour.equals("1")){
                        slctImgFour="0";
                    }
                    if(slctImgFive.equals("1")){
                        slctImgFive="0";
                    }
                    Toast.makeText(DoYouWantAdoptActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        }
        else if (requestCode == CAMERA) {

            if (data.getData() == null)
            {
                thumbnail = (Bitmap) data.getExtras().get("data");
                Log.e("jghl",""+thumbnail);
                if(selctImgOne.equals("1")){
                    service_cat_img_one.setImageBitmap(thumbnail);
                    saveImage(thumbnail);
                }
                if(selctImgtwo.equals("1")){
                    service_cat_img_two.setImageBitmap(thumbnail);
                    saveImage(thumbnail);
                }
                if(slctImgThree.equals("1")){
                    service_cat_img_three.setImageBitmap(thumbnail);
                    saveImage(thumbnail);
                }
                if(slctImgFour.equals("1")){
                    service_cat_img_four.setImageBitmap(thumbnail);
                    saveImage(thumbnail);
                }
                if(slctImgFive.equals("1")){
                    service_cat_img_five.setImageBitmap(thumbnail);
                    saveImage(thumbnail);
                }
                Toast.makeText(DoYouWantAdoptActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
            }

            else{
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(DoYouWantAdoptActivity.this.getContentResolver(), data.getData());

                    if(selctImgOne.equals("1")){
                        service_cat_img_one.setImageBitmap(bitmap);
                        saveImage(bitmap);
                    }
                    if(selctImgtwo.equals("1")){
                        service_cat_img_two.setImageBitmap(bitmap);
                        saveImage(bitmap);
                    }
                    if(slctImgThree.equals("1")){
                        service_cat_img_three.setImageBitmap(bitmap);
                        saveImage(bitmap);
                    }
                    if(slctImgFour.equals("1")){
                        service_cat_img_four.setImageBitmap(bitmap);
                        saveImage(bitmap);
                    }
                    if(slctImgFive.equals("1")){
                        service_cat_img_five.setImageBitmap(bitmap);
                        saveImage(bitmap);
                    }
                    Toast.makeText(DoYouWantAdoptActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();

                    if(selctImgOne.equals("1")){
                        selctImgOne="0";
                    }
                    if(selctImgtwo.equals("1")){
                        selctImgtwo="0";
                    }
                    if(slctImgThree.equals("1")){
                        slctImgThree="0";
                    }
                    if(slctImgFour.equals("1")){
                        slctImgFour="0";
                    }
                    if(slctImgFive.equals("1")){
                        slctImgFive="0";
                    }
                }
            }

        }

        return;
    }

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            if(selctImgOne.equals("1")){
                fileImg1 = new File(wallpaperDirectory, Calendar.getInstance()
                        .getTimeInMillis() + ".jpg");
                fileImg1.createNewFile();
                FileOutputStream fo = new FileOutputStream(fileImg1);
                fo.write(bytes.toByteArray());
                MediaScannerConnection.scanFile(this,
                        new String[]{fileImg1.getPath()},
                        new String[]{"image/jpeg"}, null);
                fo.close();
                Log.d("TAG", "File Saved::---&gt;" + fileImg1.getAbsolutePath());
                UploadImages(fileImg1);
                return fileImg1.getAbsolutePath();

            }
            if(selctImgtwo.equals("1")){
                fileImg2 = new File(wallpaperDirectory, Calendar.getInstance()
                        .getTimeInMillis() + ".jpg");
                fileImg2.createNewFile();
                FileOutputStream fo = new FileOutputStream(fileImg2);
                fo.write(bytes.toByteArray());
                MediaScannerConnection.scanFile(this,
                        new String[]{fileImg2.getPath()},
                        new String[]{"image/jpeg"}, null);
                fo.close();
                Log.d("TAG", "File Saved::---&gt;" + fileImg2.getAbsolutePath());
                UploadImages(fileImg2);
                return fileImg2.getAbsolutePath();

            }
            if(slctImgThree.equals("1")){
                fileImg3 = new File(wallpaperDirectory, Calendar.getInstance()
                        .getTimeInMillis() + ".jpg");
                fileImg3.createNewFile();
                FileOutputStream fo = new FileOutputStream(fileImg3);
                fo.write(bytes.toByteArray());
                MediaScannerConnection.scanFile(this,
                        new String[]{fileImg3.getPath()},
                        new String[]{"image/jpeg"}, null);
                fo.close();
                Log.d("TAG", "File Saved::---&gt;" + fileImg3.getAbsolutePath());
                UploadImages(fileImg3);
                return fileImg2.getAbsolutePath();
            }
            if(slctImgFour.equals("1")){
                fileImg4 = new File(wallpaperDirectory, Calendar.getInstance()
                        .getTimeInMillis() + ".jpg");
                fileImg4.createNewFile();
                FileOutputStream fo = new FileOutputStream(fileImg4);
                fo.write(bytes.toByteArray());
                MediaScannerConnection.scanFile(this,
                        new String[]{fileImg4.getPath()},
                        new String[]{"image/jpeg"}, null);
                fo.close();
                Log.d("TAG", "File Saved::---&gt;" + fileImg4.getAbsolutePath());
                UploadImages(fileImg4);
                return fileImg4.getAbsolutePath();
            }
            if(slctImgFive.equals("1")){
                fileImg5 = new File(wallpaperDirectory, Calendar.getInstance()
                        .getTimeInMillis() + ".jpg");
                fileImg5.createNewFile();
                FileOutputStream fo = new FileOutputStream(fileImg5);
                fo.write(bytes.toByteArray());
                MediaScannerConnection.scanFile(this,
                        new String[]{fileImg5.getPath()},
                        new String[]{"image/jpeg"}, null);
                fo.close();
                Log.d("TAG", "File Saved::---&gt;" + fileImg5.getAbsolutePath());
                UploadImages(fileImg5);
                return fileImg5.getAbsolutePath();

            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private void UploadImages(File absolutePath) {
        MultipartBody.Part userDpFilePart = null;
        if (absolutePath != null) {
            RequestBody userDpFile = RequestBody.create(MediaType.parse("image/*"), absolutePath);
            userDpFilePart = MultipartBody.Part.createFormData("file", absolutePath.getName(), userDpFile);
        }

        ApiService<ImageResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().uploadImages(Config.token,userDpFilePart), "UploadDocument");
        Log.e("DATALOG","check1=> "+service);

    }

    private void requestMultiplePermissions() {
        Dexter.withActivity(this)
                .withPermissions(
                        android.Manifest.permission.CAMERA,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Log.d("Debuging","All Permission Granted");
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
                        Log.d("Debuging","Some Error");
                    }
                })
                .onSameThread()
                .check();
    }

    @Override
    public void onResponse(Response arg0, String key) {
        methods.customProgressDismiss();
        switch (key)
        {
            case "GetPetTypes":
                try {
                    Log.d("GetPetTypes",arg0.body().toString());
                    PetTypeResponse petTypeResponse = (PetTypeResponse) arg0.body();
                    int responseCode = Integer.parseInt(petTypeResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        petType=new ArrayList<>();
                        petType.add("Select Pet Type");
                        petTypeHashMap=new HashMap<>();
                        petTypeHashMap.put("Select Pet Type","0");
                        Log.d("lalal",""+petTypeResponse.getData().size());
                        for(int i=0; i<petTypeResponse.getData().size(); i++){
                            Log.d("petttt",""+petTypeResponse.getData().get(i).getPetType1());
                            petType.add(petTypeResponse.getData().get(i).getPetType1());
                            petTypeHashMap.put(petTypeResponse.getData().get(i).getPetType1(),petTypeResponse.getData().get(i).getId());
                        }
                        setPetTypeSpinner();

                    }else if (responseCode==614){
                        Toast.makeText(this, petTypeResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            case "GetPetBreed":
                try {
                    Log.d("GetPetBreed",arg0.body().toString());
                    BreedCatRespose breedCatRespose = (BreedCatRespose) arg0.body();
                    int responseCode = Integer.parseInt(breedCatRespose.getResponse().getResponseCode());
                    if (responseCode== 109){
                        petBreed=new ArrayList<>();
                        petBreed.add("Pet Breed");
                        petBreedHashMap=new HashMap<>();
                        petBreedHashMap.put("Pet Breed","0");
                        Log.d("lalal",""+breedCatRespose.getData().size());
                        for(int i=0; i<breedCatRespose.getData().size(); i++){
                            Log.d("petttt",""+breedCatRespose.getData().get(i).getBreed());
                            petBreed.add(breedCatRespose.getData().get(i).getBreed());
                            petBreedHashMap.put(breedCatRespose.getData().get(i).getBreed(),breedCatRespose.getData().get(i).getId());
                        }
                        setPetBreeSpinner();
                    }else if (responseCode==614){
                        Toast.makeText(this, breedCatRespose.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            case "GetPetAge":
                try {
                    Log.d("GetPetAge",arg0.body().toString());
                    PetAgeValueResponse petAgeValueResponse = (PetAgeValueResponse) arg0.body();
                    int responseCode = Integer.parseInt(petAgeValueResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        petAge=new ArrayList<>();
                        petAge.add("Select Pet Age");
                        petAgeHashMap=new HashMap<>();
                        petAgeHashMap.put("0","Select Pet Age");
                        Log.d("lalal",""+petAgeValueResponse.getData().size());
                        for(int i=0; i<petAgeValueResponse.getData().size(); i++){
                            Log.d("petttt",""+petAgeValueResponse.getData().get(i).getAge());
                            petAge.add(petAgeValueResponse.getData().get(i).getAge());
                            petAgeHashMap.put(petAgeValueResponse.getData().get(i).getAge(),petAgeValueResponse.getData().get(i).getId());
                        }
                        setPetAgeSpinner();
                    }else if (responseCode==614){
                        Toast.makeText(this, petAgeValueResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;

            case "GetPetColor":
                try {
                    Log.d("GetPetColor",arg0.body().toString());
                    PetColorValueResponse petColorValueResponse = (PetColorValueResponse) arg0.body();
                    int responseCode = Integer.parseInt(petColorValueResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        petColor=new ArrayList<>();
                        petColor.add("Pet Color");
                        petColorHashMap=new HashMap<>();
                        petColorHashMap.put("0","Pet Color");
                        Log.d("lalal",""+petColorValueResponse.getData().size());
                        for(int i=0; i<petColorValueResponse.getData().size(); i++){
                            Log.d("petttt",""+petColorValueResponse.getData().get(i).getColor());
                            petColor.add(petColorValueResponse.getData().get(i).getColor());
                            petColorHashMap.put(petColorValueResponse.getData().get(i).getColor(),petColorValueResponse.getData().get(i).getId());
                        }
                        setPetColorSpinner();
                    }else if (responseCode==614){
                        Toast.makeText(this, petColorValueResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            case "GetPetSize":
                try {
                    Log.d("GetPetSize",arg0.body().toString());
                    PetSizeValueResponse petSizeValueResponse = (PetSizeValueResponse) arg0.body();
                    int responseCode = Integer.parseInt(petSizeValueResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        petSize=new ArrayList<>();
                        petSize.add("Pet Size");
                        petSizeHashMap=new HashMap<>();
                        petSizeHashMap.put("0","Pet Size");
                        Log.d("lalal",""+petSizeValueResponse.getData().size());
                        for(int i=0; i<petSizeValueResponse.getData().size(); i++){
                            Log.d("petttt",""+petSizeValueResponse.getData().get(i).getSize());
                            petSize.add(petSizeValueResponse.getData().get(i).getSize());
                            petSizeHashMap.put(petSizeValueResponse.getData().get(i).getSize(),petSizeValueResponse.getData().get(i).getId());
                        }
                        setPetSizeSpinner();
                    }else if (responseCode==614){
                        Toast.makeText(this, petSizeValueResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            case "getState":
                try {
                    Log.d("getState",arg0.body().toString());
                    StateResponse stateResponse = (StateResponse) arg0.body();
                    int responseCode = Integer.parseInt(stateResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        state=new ArrayList<>();
                        state.add("Select State");
                        stateHashMap=new HashMap<>();
                        stateHashMap.put("0","Select State");
                        Log.d("lalal",""+stateResponse.getData().size());
                        for(int i=0; i<stateResponse.getData().size(); i++){
                            Log.d("petttt",""+stateResponse.getData().get(i).getStateName());
                            state.add(stateResponse.getData().get(i).getStateName());
                            stateHashMap.put(stateResponse.getData().get(i).getStateName(),stateResponse.getData().get(i).getId());
                        }
                        setStateSpinner();
                    }else if (responseCode==614){
                        Toast.makeText(this, stateResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            case "GetCity":
                try {
                    Log.d("GetCity",arg0.body().toString());
                    CityResponseModel cityResponseModel = (CityResponseModel) arg0.body();
                    int responseCode = Integer.parseInt(cityResponseModel.getResponse().getResponseCode());
                    if (responseCode== 109){
                        city=new ArrayList<>();
                        city.add("Select City");
                        cityHasMap=new HashMap<>();
                        cityHasMap.put("0","Select City");
                        Log.d("lalal",""+cityResponseModel.getData().size());
                        for(int i=0; i<cityResponseModel.getData().size(); i++){
                            Log.d("petttt",""+cityResponseModel.getData().get(i).getCity1());
                            city.add(cityResponseModel.getData().get(i).getCity1());
                            cityHasMap.put(cityResponseModel.getData().get(i).getCity1(),cityResponseModel.getData().get(i).getId());
                        }
                        setCitySpinner();
                    }else if (responseCode==614){
                        Toast.makeText(this, cityResponseModel.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            case "donate-a-pet":
                try {
                    JsonObject jsonObject = (JsonObject) arg0.body();
                    Log.d("donateAPet",""+jsonObject.toString());
                    JsonObject response = jsonObject.getAsJsonObject("response");
                    Log.d("hhshshhs",""+response);

                    int responseCode = Integer.parseInt(String.valueOf(response.get("responseCode")));
                    if(responseCode==109)
                    {
                        Toast.makeText(this, "Successfully Donate", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            case "update-donation-request":
                try {
                    JsonObject jsonObject = (JsonObject) arg0.body();
                    Log.d("donateAPetUpdate",""+jsonObject.toString());
                    JsonObject response = jsonObject.getAsJsonObject("response");
                    Log.d("update",""+response);

                    int responseCode = Integer.parseInt(String.valueOf(response.get("responseCode")));
                    if(responseCode==109)
                    {
                        Toast.makeText(this, "Successfully Donate", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            case "UploadDocument":
                try {
                    Log.d("UploadDocument",arg0.body().toString());
                    ImageResponse imageResponse = (ImageResponse) arg0.body();
                    int responseCode = Integer.parseInt(imageResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();

                        if(selctImgOne.equals("1")){
                            strFirstImgUrl=imageResponse.getData().getDocumentUrl();
                            selctImgOne="0";
                        }
                        if(selctImgtwo.equals("1")){
                            strSecondImgUrl=imageResponse.getData().getDocumentUrl();
                            selctImgtwo="0";
                        }
                        if(slctImgThree.equals("1")){
                            strThirdImgUrl=imageResponse.getData().getDocumentUrl();
                            slctImgThree="0";
                        }
                        if(slctImgFour.equals("1")){
                            strFourthImUrl=imageResponse.getData().getDocumentUrl();
                            slctImgFour="0";
                        }
                        if(slctImgFive.equals("1")){
                            strFifthImgUrl=imageResponse.getData().getDocumentUrl();
                            slctImgFive="0";
                        }

                    }else if (responseCode==614){
                        Toast.makeText(this, imageResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
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

    private void setPetTypeSpinner()
    {
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,petType);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        add_pet_type.setAdapter(aa);
        if (strSpnerItemPetNm != null) {
            int spinnerPosition = aa.getPosition(strSpnerItemPetNm);
            add_pet_type.setSelection(spinnerPosition);
        }
        add_pet_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                strSpnerItemPetNm=item;
                Log.d("spnerType",""+strSpnerItemPetNm);
                getStrSpnerItemPetNmId=petTypeHashMap.get(strSpnerItemPetNm);
                if(!getStrSpnerItemPetNmId.equals("0"))
                {
                    getPetBreed();
                    getPetAge();
                    getPetColor();
                    getPetSize();
                }

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setPetBreeSpinner() {
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,petBreed);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        add_pet_breed_dialog.setAdapter(aa);
        if (strSpnrBreed != null) {
            int spinnerPosition = aa.getPosition(strSpnrBreed);
            add_pet_breed_dialog.setSelection(spinnerPosition);
        }
        add_pet_breed_dialog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                strSpnrBreed=item;
                Log.d("spnerType",""+strSpnrBreed);
                strSpnrBreedId=petBreedHashMap.get(strSpnrBreed);

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setPetAgeSpinner() {
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,petAge);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        add_pet_age_dialog.setAdapter(aa);
        if (strSpnrAge != null) {
            int spinnerPosition = aa.getPosition(strSpnrAge);
            add_pet_age_dialog.setSelection(spinnerPosition);
        }
        add_pet_age_dialog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                strSpnrAge=item;
                Log.d("spnerType",""+strSpnrAge);
                strSpnrAgeId=petAgeHashMap.get(strSpnrAge);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setPetColorSpinner() {
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,petColor);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        add_pet_color_dialog.setAdapter(aa);
        if (strSpnrColor != null) {
            int spinnerPosition = aa.getPosition(strSpnrColor);
            add_pet_color_dialog.setSelection(spinnerPosition);
        }
        add_pet_color_dialog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                strSpnrColor=item;
                Log.d("spnerType",""+strSpnrColor);
                strSpnrColorId=petColorHashMap.get(strSpnrColor);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setPetSizeSpinner() {
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,petSize);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        add_pet_size_dialog.setAdapter(aa);
        if (strSpnrSize != null) {
            int spinnerPosition = aa.getPosition(strSpnrSize);
            add_pet_size_dialog.setSelection(spinnerPosition);
        }
        add_pet_size_dialog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                strSpnrSize=item;
                Log.d("spnerType",""+strSpnrSize);
                strSpneSizeId=petSizeHashMap.get(strSpnrSize);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setSpinnerPetSex() {
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,petSex);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        add_pet_sex_dialog.setAdapter(aa);
        if (strSpnerItemPetNm != null) {
            int spinnerPosition = aa.getPosition(strSpnerItemPetNm);
            add_pet_sex_dialog.setSelection(spinnerPosition);
        }
        add_pet_sex_dialog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                strSpnrSex=item;
                Log.d("spnerType",""+strSpnrSex);
                strSpnrSexId=petSexHashMap.get(strSpnrSex);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setStateSpinner() {
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,state);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        state_spinner.setAdapter(aa);
        if (strSpnrState != null) {
            int spinnerPosition = aa.getPosition(strSpnrState);
            state_spinner.setSelection(spinnerPosition);
        }
        state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                strSpnrState=item;
                Log.d("spnerTypeState",""+strSpnrState);
                strSpnrStateId=stateHashMap.get(strSpnrState);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setCitySpinner()
    {
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,city);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        city_spinner.setAdapter(aa);
        if (strSpinnerCity != null) {
            int spinnerPosition = aa.getPosition(strSpinnerCity);
            city_spinner.setSelection(spinnerPosition);
        }
        city_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                strSpinnerCity=item;
                Log.d("spnerTypeCity",""+strSpinnerCity);
                strSpnrCityId=cityHasMap.get(strSpinnerCity);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}