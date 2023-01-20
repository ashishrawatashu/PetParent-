package com.cynoteck.petofy.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cynoteck.petofy.R;
import com.cynoteck.petofy.utils.Config;
import com.google.android.material.card.MaterialCardView;

public class PdfReaderActivity extends AppCompatActivity implements View.OnClickListener {

    WebView pdfread;
    TextView pdf_headline_TV;
    String  encryptId,type;
    MaterialCardView back_arrow_CV;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_reader);
        init();


        Intent intent  = getIntent();
        encryptId= intent.getStringExtra("encryptId");
        type = intent.getStringExtra("type");
        pdf_headline_TV.setText(type);
        pdfread.getSettings().setBuiltInZoomControls(true);
        pdfread.setWebViewClient(new WebViewClient());
        pdfread.loadUrl(Config.URL+"PetHealthRecord/DoctorsPrescription?encryptedId="+encryptId+"&&status=1");

    }

    private void init() {
        back_arrow_CV = findViewById(R.id.back_arrow_CV);
        pdf_headline_TV = findViewById(R.id.pdf_headline_TV);
        pdfread = findViewById(R.id.pdfread);
        progressBar =findViewById(R.id.progressBar);

        back_arrow_CV.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_arrow_CV:
                onBackPressed();
                break;
        }
    }

    public class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }
}
