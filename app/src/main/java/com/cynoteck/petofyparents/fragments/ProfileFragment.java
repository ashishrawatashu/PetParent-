package com.cynoteck.petofyparents.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.activty.ChangePasswordActivity;
import com.cynoteck.petofyparents.activty.LoginActivity;
import com.cynoteck.petofyparents.activty.SettingActivity;
import com.cynoteck.petofyparents.api.ApiResponse;
import com.cynoteck.petofyparents.utils.Config;

import retrofit2.Response;


public class ProfileFragment extends Fragment implements View.OnClickListener, ApiResponse {
    TextView tv,heder,parent_name_TV,parent_email_TV,parent_address_TV,parent_phone_TV;
    ImageView parent_profile_pic;
    View view;
    RelativeLayout setings_layout,logout_layout,changePass_layout;

    public ProfileFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        initization();
        getParentInfo();

        return view;

    }

    private void getParentInfo() {
        Glide.with(this)
                .load(Config.user_url)
                .into(parent_profile_pic);
        parent_name_TV.setText(Config.user_name);
        parent_email_TV.setText(Config.user_emial);
        parent_address_TV.setText(Config.user_address);
        parent_phone_TV.setText(Config.user_phone);

    }

    private void initization() {
        setings_layout=view.findViewById(R.id.setings_layout);
        logout_layout=view.findViewById(R.id.logout_layout);
        changePass_layout=view.findViewById(R.id.changePass_layout);
        parent_email_TV = view.findViewById(R.id.parent_email_TV);
        parent_name_TV = view.findViewById(R.id.parent_name_TV);
        parent_address_TV = view.findViewById(R.id.parent_address_TV);
        parent_phone_TV = view.findViewById(R.id.parent_phone_TV);
        parent_profile_pic = view.findViewById(R.id.parent_profile_pic);

        setings_layout.setOnClickListener(this);
        logout_layout.setOnClickListener(this);
        changePass_layout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

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
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;

        }
    }

    @Override
    public void onResponse(Response arg0, String key) {

    }

    @Override
    public void onError(Throwable t, String key) {

    }
}