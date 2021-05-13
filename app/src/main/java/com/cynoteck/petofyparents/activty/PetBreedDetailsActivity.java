package com.cynoteck.petofyparents.activty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cynoteck.petofyparents.R;
import com.google.android.material.card.MaterialCardView;

public class PetBreedDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView breed_profile_image_IV;
    MaterialCardView back_arrow_CV;
    TextView breed_name_TV, breed_size_TV, breed_life_TV, breed_weight_TV, breed_height_TV, breed_color_TV, breed_origin_TV, breed_desc_TV;
    String breed_image_url, breed_name, breed_size, breed_life, breed_weight, breed_height, breed_color, breed_origin, breed_description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_breed_deatils);

        getDataFromIntent();

        findViewByIds();

        setDataOnView();

    }

    private void getDataFromIntent() {
        Intent getIntent = getIntent();
        breed_image_url = getIntent.getStringExtra("breed_image_url");
        breed_name = getIntent.getStringExtra("breed_name");
        breed_size = getIntent.getStringExtra("breed_size");
        breed_life = getIntent.getStringExtra("breed_life");
        breed_weight = getIntent.getStringExtra("breed_weight");
        breed_height = getIntent.getStringExtra("breed_height");
        breed_color = getIntent.getStringExtra("breed_color");
        breed_origin = getIntent.getStringExtra("breed_origin");
        breed_description = getIntent.getStringExtra("breed_description");
    }


    private void findViewByIds() {
        breed_profile_image_IV = findViewById(R.id.breed_profile_image_IV);
        back_arrow_CV = findViewById(R.id.back_arrow_CV);
        breed_name_TV = findViewById(R.id.breed_name_TV);
        breed_size_TV = findViewById(R.id.breed_size_TV);
        breed_life_TV = findViewById(R.id.breed_life_TV);
        breed_weight_TV = findViewById(R.id.breed_weight_TV);
        breed_height_TV = findViewById(R.id.breed_height_TV);
        breed_color_TV = findViewById(R.id.breed_color_TV);
        breed_origin_TV = findViewById(R.id.breed_origin_TV);
        breed_desc_TV = findViewById(R.id.breed_desc_TV);

        back_arrow_CV.setOnClickListener(this);
    }


    private void setDataOnView() {
        Glide.with(this)
                .load(breed_image_url)
                .placeholder(R.drawable.empty_pet_image)
                .into(breed_profile_image_IV);

        breed_name_TV.setText(breed_name);
        breed_size_TV.setText(breed_size);
        breed_life_TV.setText(breed_life);
        breed_weight_TV.setText(breed_weight);
        breed_height_TV.setText(breed_height);
        breed_color_TV.setText(breed_color);
        breed_origin_TV.setText(breed_origin);
        breed_desc_TV.setText(breed_description);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.back_arrow_CV:
                onBackPressed();
                break;
        }

    }
}