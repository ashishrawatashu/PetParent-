package com.cynoteck.petofyparents.activty;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Response;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.api.ApiResponse;

public class DashBoardActivity extends AppCompatActivity implements ApiResponse, View.OnClickListener {

    TextView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        initialize();
    }

    public void initialize()
    {
        logout=findViewById(R.id.logout);
        logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.logout:
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("Alert message to be shown");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences preferences =getSharedPreferences("userdetails",0);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.clear();
                                editor.apply();
                                dialog.dismiss();
                                startActivity(new Intent(DashBoardActivity.this, LoginActivity.class));
                                finish();
                            }
                        });
                alertDialog.show();
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