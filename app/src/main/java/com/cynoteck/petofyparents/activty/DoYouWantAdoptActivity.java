package com.cynoteck.petofyparents.activty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cynoteck.petofyparents.R;

public class DoYouWantAdoptActivity extends AppCompatActivity implements View.OnClickListener {

    AppCompatSpinner add_pet_type,add_pet_sex_dialog,add_pet_size_dialog,add_pet_age_dialog,add_pet_breed_dialog,
            add_pet_color_dialog,state_spinner,city_spinner;
    EditText pet_name_ET,pet_conatct_ET,pet_addrs_ET,pet_desc_ET;
    TextView pet_parent_name_TV;
    ImageView service_cat_img_one,service_cat_img_two,service_cat_img_three,service_cat_img_four,service_cat_img_five;
    Button submit_BT,reset_BT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopt_pet);
        initialize();
    }

    public void initialize()
    {
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


    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.service_cat_img_one:
                break;
            case R.id.service_cat_img_two:
                break;
            case R.id.service_cat_img_three:
                break;
            case R.id.service_cat_img_four:
                break;
            case R.id.service_cat_img_five:
                break;
            case R.id.submit_BT:
                break;
            case R.id.reset_BT:
                break;
        }

    }
}