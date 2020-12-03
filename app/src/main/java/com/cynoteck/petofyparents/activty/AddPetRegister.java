package com.cynoteck.petofyparents.activty;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.api.ApiClient;
import com.cynoteck.petofyparents.api.ApiResponse;
import com.cynoteck.petofyparents.api.ApiService;
import com.cynoteck.petofyparents.parameter.addParamRequest.AddPetParams;
import com.cynoteck.petofyparents.parameter.addParamRequest.AddPetRequset;
import com.cynoteck.petofyparents.parameter.petBreedRequest.BreedParams;
import com.cynoteck.petofyparents.parameter.petBreedRequest.BreedRequest;
import com.cynoteck.petofyparents.response.addPet.addPetResponse.AddPetValueResponse;
import com.cynoteck.petofyparents.response.addPet.breedResponse.BreedCatRespose;
import com.cynoteck.petofyparents.response.addPet.imageUpload.ImageResponse;
import com.cynoteck.petofyparents.response.addPet.petAgeResponse.PetAgeValueResponse;
import com.cynoteck.petofyparents.response.addPet.petColorResponse.PetColorValueResponse;
import com.cynoteck.petofyparents.response.addPet.petSizeResponse.PetSizeValueResponse;
import com.cynoteck.petofyparents.response.addPet.uniqueIdResponse.UniqueResponse;
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
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class AddPetRegister extends AppCompatActivity implements View.OnClickListener, ApiResponse {

    CircleImageView pet_profile_image;

    AppCompatSpinner add_pet_age,add_pet_type,add_pet_sex,add_pet_breed,add_pet_color,add_pet_size;
    EditText pet_name,pet_parent_name,pet_contact_number,pet_description,pet_address;
    TextView peto_reg_number,calenderView;
    ImageView back_arrow_IV, service_cat_img_one,service_cat_img_two,service_cat_img_three,service_cat_img_four,
            service_cat_img_five;
    Button pet_submit;
    String strPetName="",strPetParentName="",strPetContactNumber="",strPetDescription="",strPetAdress="",strPetBirthDay="",
            strSpnerItemPetNm="",getStrSpnerItemPetNmId="",strSpnrBreed="",strSpnrBreedId="",petUniqueId="",
            strSpnrAge="",strSpnrAgeId="",strSpnrColor="",strSpnrColorId="",strSpnrSize="",strSpneSizeId="",
            strSpnrSex="",strSpnrSexId="",currentDateandTime="",selctProflImage="0",selctImgOne="0",selctImgtwo="0",
            slctImgThree="0",slctImgFour="0",slctImgFive="0",strProfileImgUrl="",strFirstImgUrl="",strSecondImgUrl="",
            strThirdImgUrl="",strFourthImUrl="",strFifthImgUrl="";
    Dialog dialog;
    Methods methods;
    DatePickerDialog picker;
    ArrayList<String> petType;
    ArrayList<String> petBreed;
    ArrayList<String> petAge;
    ArrayList<String> petColor;
    ArrayList<String> petSize;
    ArrayList<String> petSex;

    HashMap<String,String> petTypeHashMap=new HashMap<>();
    HashMap<String,String> petBreedHashMap=new HashMap<>();
    HashMap<String,String> petAgeHashMap=new HashMap<>();
    HashMap<String,String> petColorHashMap=new HashMap<>();
    HashMap<String,String> petSizeHashMap=new HashMap<>();
    HashMap<String,String> petSexHashMap=new HashMap<>();

    private static final String IMAGE_DIRECTORY = "/Picture";
    private int GALLERY = 1, CAMERA = 2;
    File file = null;
    File fileImg1 = null;
    File fileImg2 = null;
    File fileImg3 = null;
    File fileImg4 = null;
    File fileImg5 = null;
    Bitmap bitmap, thumbnail;
    String capImage;

    private Button upload;
    private String baseUrl;

    String addType="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet_register);

        Intent intent = getIntent();
        intent.getStringExtra("appointment");
        init();
        requestMultiplePermissions();
        currentDateAndTime();
        methods=new Methods(this);

        petSex=new ArrayList<>();
        petSex.add("Pet Sex");
        petSex.add("Male");
        petSex.add("Female");

        petSexHashMap.put("Pet Sex","0");
        petSexHashMap.put("Male","1");
        petSexHashMap.put("Female","2");

        if(methods.isInternetOn())
        {
            petType();
            genaretePetUniqueKey();
            setSpinnerPetSex();
        }
        else
        {
            methods.DialogInternet();
        }

    }

    private void currentDateAndTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, d MMM yyyy h:mm:ss a", Locale.getDefault());
        currentDateandTime = sdf.format(new Date());
        Log.d("currentDateandTime",""+currentDateandTime);
    }


    private void petType() {
        methods.showCustomProgressBarDialog(this);
        ApiService<PetTypeResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().petTypeApi(Config.token), "GetPetTypes");
    }

    private void genaretePetUniqueKey() {
        ApiService<UniqueResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getGeneratePetUniqueId(Config.token), "GeneratePetUniqueId");
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



    private void init() {
        //Spinner
        add_pet_type=findViewById(R.id.add_pet_type);
        add_pet_sex=findViewById(R.id.add_pet_sex_dialog);
        add_pet_breed=findViewById(R.id.add_pet_breed_dialog);
        add_pet_color=findViewById(R.id.add_pet_color_dialog);
        add_pet_size=findViewById(R.id.add_pet_size_dialog);
        add_pet_age=findViewById(R.id.add_pet_age_dialog);

        //TextInputEditText
        pet_name=findViewById(R.id.pet_name_ET);
        pet_parent_name=findViewById(R.id.pet_parent_name_ET);
        pet_contact_number=findViewById(R.id.pet_contact_number);
        pet_description=findViewById(R.id.pet_desc_ET);
        pet_address=findViewById(R.id.pet_address_ET);

        //TextView
        peto_reg_number=findViewById(R.id.peto_reg_number);
        calenderView=findViewById(R.id.calenderTextView_dialog);
        calenderView.setOnClickListener(this);

        //ImageView
        back_arrow_IV=findViewById(R.id.back_arrow_IV);
        pet_profile_image=findViewById(R.id.pet_profile_image);
        service_cat_img_one=findViewById(R.id.service_cat_img_one);
        service_cat_img_two=findViewById(R.id.service_cat_img_two);
        service_cat_img_three=findViewById(R.id.service_cat_img_three);
        service_cat_img_four=findViewById(R.id.service_cat_img_four);
        service_cat_img_five=findViewById(R.id.service_cat_img_five);
        pet_profile_image.setOnClickListener(this);
        service_cat_img_one.setOnClickListener(this);
        service_cat_img_two.setOnClickListener(this);
        service_cat_img_three.setOnClickListener(this);
        service_cat_img_four.setOnClickListener(this);
        service_cat_img_five.setOnClickListener(this);
        back_arrow_IV.setOnClickListener(this);

        //Button
        pet_submit=findViewById(R.id.pet_submit);
        pet_submit.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.back_arrow_IV:
                onBackPressed();
                break;
            case R.id.pet_submit:
                strPetName= pet_name.getText().toString().trim();
                strPetParentName = pet_parent_name.getText().toString().trim();
                strPetContactNumber = pet_contact_number.getText().toString().trim();
                strPetDescription = pet_description.getText().toString().trim();
                strPetAdress = pet_address.getText().toString().trim();
                strPetBirthDay = calenderView.getText().toString().trim();

                if(strPetName.isEmpty())
                {
                    pet_name.setError("Enter Pet Name");
                    pet_parent_name.setError(null);
                    pet_contact_number.setError(null);
                    pet_description.setError(null);
                    pet_address.setError(null);
                    calenderView.setError(null);
                }
//                else if(strPetParentName.isEmpty())
//                {
//                    pet_name.setError(null);
//                    pet_parent_name.setError("Enter Parent Name");
//                    pet_contact_number.setError(null);
//                    pet_description.setError(null);
//                    pet_address.setError(null);
//                    calenderView.setError(null);
//                }
//                else if(strPetContactNumber.isEmpty())
//                {
//                    pet_name.setError(null);
//                    pet_parent_name.setError(null);
//                    pet_contact_number.setError("Enter Contact Number");
//                    pet_description.setError(null);
//                    pet_address.setError(null);
//                    calenderView.setError(null);
//                }
                else if(strPetDescription.isEmpty())
                {
                    pet_name.setError(null);
                    pet_parent_name.setError(null);
                    pet_contact_number.setError(null);
                    pet_description.setError("Enter Description");
                    pet_address.setError(null);
                    calenderView.setError(null);
                }
//                else if(strPetAdress.isEmpty())
//                {
//                    pet_name.setError(null);
//                    pet_parent_name.setError(null);
//                    pet_contact_number.setError(null);
//                    pet_description.setError(null);
//                    pet_address.setError("Enter Pet Address");
//                    calenderView.setError(null);
//                }
                else if(strPetBirthDay.isEmpty())
                {
                    pet_name.setError(null);
                    pet_parent_name.setError(null);
                    pet_contact_number.setError(null);
                    pet_description.setError(null);
                    pet_address.setError(null);
                    calenderView.setError("Pet YOB");
                }
                //pet size and color.
                else if(strSpnerItemPetNm.isEmpty()||(strSpnerItemPetNm.equals("Select Pet Type")))
                {
                    Toast.makeText(this, "Select Type!!", Toast.LENGTH_SHORT).show();
                }
                else if(strSpnrBreed.isEmpty()||(strSpnrBreed.equals("Pet Breed")))
                {
                    Toast.makeText(this, "Select Breed!!", Toast.LENGTH_SHORT).show();
                }
                else if(strSpnrAge.isEmpty()||(strSpnrAge.equals("Select Pet Age")))
                {
                    Toast.makeText(this, "Select Pet Age!!", Toast.LENGTH_SHORT).show();
                }
                else if(strSpnrSex.isEmpty()||(strSpnrSex.equals("Pet Sex")))
                {
                    Toast.makeText(this, "Select Pet Sex!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    pet_name.setError(null);
                    pet_parent_name.setError(null);
                    pet_contact_number.setError(null);
                    pet_description.setError(null);
                    pet_address.setError(null);
                    calenderView.setError(null);
                    Log.d("hahahah",""+getStrSpnerItemPetNmId+" "+strSpnrSexId+" "+strSpnrAgeId+" "+strSpneSizeId+
                            " "+strSpnrColorId+" "+strSpnrBreedId+" "+strPetName+" "+strPetBirthDay+" "+strPetDescription+" "+currentDateandTime);
                    AddPetRequset addPetRequset = new AddPetRequset();
                    AddPetParams data = new AddPetParams();
                    data.setPetUniqueId(petUniqueId);
                    data.setPetCategoryId(getStrSpnerItemPetNmId);
                    data.setPetSexId(strSpnrSexId);
                    data.setPetAgeId(strSpnrAgeId);
                    data.setPetSizeId(strSpneSizeId);
                    data.setPetColorId(strSpnrColorId);
                    data.setPetBreedId(strSpnrBreedId);
                    data.setPetName(strPetName);
                    data.setPetParentName(Config.user_name);
                    data.setContactNumber(Config.user_phone);
                    data.setAddress(Config.user_address);
                    data.setDescription(strPetDescription);
                    data.setCreateDate(currentDateandTime);
                    data.setDateOfBirth(strPetBirthDay);

                    data.setPetProfileImageUrl(strProfileImgUrl);
                    data.setFirstServiceImageUrl(strFirstImgUrl);
                    data.setSecondServiceImageUrl(strSecondImgUrl);
                    data.setThirdServiceImageUrl(strThirdImgUrl);
                    data.setFourthServiceImageUrl(strFourthImUrl);
                    data.setFifthServiceImageUrl(strFifthImgUrl);
                    addPetRequset.setAddPetParams(data);
                    if(methods.isInternetOn())
                    {
                        addPetData(addPetRequset);
                    }
                    else
                    {
                        methods.DialogInternet();
                    }
                }
                break;

            case R.id.calenderTextView_dialog:
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(AddPetRegister.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                calenderView.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
                break;
            case R.id.pet_profile_image:
                selctProflImage="1";
                showPictureDialog();
                break;
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
                if(selctProflImage.equals("1")){
                    selctProflImage="0";
                }
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

                    if(selctProflImage.equals("1")){
                        pet_profile_image.setImageBitmap(bitmap);
                        saveImage(bitmap);
                    }
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
                    if(selctProflImage.equals("1")){
                        selctProflImage="0";
                    }
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
                    Toast.makeText(AddPetRegister.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        }
        else if (requestCode == CAMERA) {

            if (data.getData() == null)
            {
                thumbnail = (Bitmap) data.getExtras().get("data");
                Log.e("jghl",""+thumbnail);
                if(selctProflImage.equals("1")){
                    pet_profile_image.setImageBitmap(thumbnail);
                    saveImage(thumbnail);
                }
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
                Toast.makeText(AddPetRegister.this, "Image Saved!", Toast.LENGTH_SHORT).show();
            }

            else{
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(AddPetRegister.this.getContentResolver(), data.getData());
                    if(selctProflImage.equals("1")){
                        pet_profile_image.setImageBitmap(bitmap);
                        saveImage(bitmap);
                    }
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
                    Toast.makeText(AddPetRegister.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    if(selctProflImage.equals("1")){
                        selctProflImage="0";
                    }
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
            if(selctProflImage.equals("1")){
                file = new File(wallpaperDirectory, Calendar.getInstance()
                        .getTimeInMillis() + ".png");
                file.createNewFile();
                FileOutputStream fo = new FileOutputStream(file);
                fo.write(bytes.toByteArray());
                MediaScannerConnection.scanFile(this,
                        new String[]{file.getPath()},
                        new String[]{"image/png"}, null);
                fo.close();
                Log.d("TAG", "File Saved::---&gt;" + file.getAbsolutePath());
                UploadImages(file);
                return file.getAbsolutePath();
            }
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

    private void addPetData(AddPetRequset addPetRequset) {
        methods.showCustomProgressBarDialog(this);
        ApiService<JsonObject> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().addNewPet(Config.token,addPetRequset), "AddPet");
        Log.e("DATALOG","check1=> "+addPetRequset);

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

            case "GeneratePetUniqueId":
                try {
                    Log.d("GeneratePetUniqueId",arg0.body().toString());
                    UniqueResponse uniqueResponse = (UniqueResponse) arg0.body();
                    int responseCode = Integer.parseInt(uniqueResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        petUniqueId=uniqueResponse.getData().getPetUniqueId();
                        peto_reg_number.setText(petUniqueId);
                    }else if (responseCode==614){
                        Toast.makeText(this, uniqueResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
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

            case "AddPet":
                try {
                    Log.d("AddPet",arg0.body().toString());
                    JsonObject addPetValueResponse = (JsonObject) arg0.body();

                    JsonObject response = addPetValueResponse.getAsJsonObject("response");
                    Log.d("hhshshhs",""+response);

                    int responseCode = Integer.parseInt(String.valueOf(response.get("responseCode")));
                    if(responseCode==109)
                    {
                        methods.customProgressDismiss();
                        Toast.makeText(this, "Pet Added Successfully ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                    else
                    {
                        methods.customProgressDismiss();
                        Toast.makeText(this, "Try Again!!", Toast.LENGTH_SHORT).show();
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
                        if(selctProflImage.equals("1")){
                            strProfileImgUrl=imageResponse.getData().getDocumentUrl();
                            selctProflImage="0";
                        }
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



    private void setPetTypeSpinner() {
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,petType);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        add_pet_type.setAdapter(aa);
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
        add_pet_breed.setAdapter(aa);
        add_pet_breed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        add_pet_age.setAdapter(aa);
        add_pet_age.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        add_pet_color.setAdapter(aa);
        add_pet_color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        add_pet_size.setAdapter(aa);
        add_pet_size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        add_pet_sex.setAdapter(aa);
        add_pet_sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

}