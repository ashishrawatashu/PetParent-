
package com.cynoteck.petofy.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cynoteck.petofy.R;
import com.cynoteck.petofy.api.ApiClient;
import com.cynoteck.petofy.api.ApiResponse;
import com.cynoteck.petofy.api.ApiService;
import com.cynoteck.petofy.parameter.getOrderDetailsParms.GetOrderParams;
import com.cynoteck.petofy.parameter.getOrderDetailsParms.GetOrderRequest;
import com.cynoteck.petofy.parameter.paymentHistoryStaus.PaymentHistoryParms;
import com.cynoteck.petofy.parameter.paymentHistoryStaus.PaymentHistoryRequest;
import com.cynoteck.petofy.response.getOrderResponse.GetOrderResponse;
import com.cynoteck.petofy.response.paymentStatusResponse.PaymentStatusResponse;
import com.cynoteck.petofy.utils.Config;
import com.cynoteck.petofy.utils.Methods;
import com.google.android.material.card.MaterialCardView;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Response;

public class PaymentScreenActivity extends AppCompatActivity implements PaymentResultWithDataListener, ApiResponse, View.OnClickListener {

    Button              procced_appointment_BT;
    MaterialCardView    back_arrow_CV;
    TextView            vetName_TV,topic_TV,appointment_time_TV,date_TV,pet_name_TV,consultation_fee_TV;
    String              vetUserId,vetName,appointment_time,topic,mettingID,order_ID,amount,vet_image_url;
    Methods             methods;
    ConstraintLayout    payment_details_CL;
    CircleImageView     parent_profile_CIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_screen);
        methods = new Methods(this);
        Checkout.preload(getApplicationContext());
        intentData();

        initization();

        if (methods.isInternetOn()) {
            getOrderDetails();
        }else {
            methods.DialogInternet();
        }

    }

    private void intentData() {
        Intent intent       = getIntent();
        vetUserId           = intent.getStringExtra("vetId");
        vetName             = intent.getStringExtra("vetName");
        appointment_time    = intent.getStringExtra("appointment_time");
        topic               = intent.getStringExtra("topic");
        mettingID           = intent.getStringExtra("mettingId");
        vet_image_url       = intent.getStringExtra("vet_image_url");
    }

    private void getOrderDetails() {
        payment_details_CL.setVisibility(View.INVISIBLE);
        methods.showCustomProgressBarDialog(this);
        GetOrderParams getOrderParams = new GetOrderParams();
        getOrderParams.setVeterinarianId(vetUserId);
        GetOrderRequest getOrderRequest = new GetOrderRequest();
        getOrderRequest.setData(getOrderParams);

        ApiService<GetOrderResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getOrderDetails(Config.token,getOrderRequest), "GetOrder");


    }

    private void initization() {
        date_TV                     = findViewById(R.id.date_TV);
        vetName_TV                  = findViewById(R.id.vet_name_TV);
        appointment_time_TV         = findViewById(R.id.appointment_time_TV);
        procced_appointment_BT      = findViewById(R.id.procced_appointment_BT);
        consultation_fee_TV         = findViewById(R.id.consultation_fee_TV);
        payment_details_CL          = findViewById(R.id.payment_details_CL);
        pet_name_TV                 = findViewById(R.id.pet_name_TV);
        back_arrow_CV               = findViewById(R.id.back_arrow_CV);
        parent_profile_CIV          = findViewById(R.id.parent_profile_CIV);

        back_arrow_CV.setOnClickListener(this);
        procced_appointment_BT.setOnClickListener(this);

    }

    private void startPayment() {

        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_live_33I1Yzt5hkmdzN");

        // rzp_test_f1R6dTWpxaiwgg   testing key
        checkout.setImage(R.drawable.petofy_p_logo);
        final Activity activity = this;
        try {
            JSONObject options = new JSONObject();

            options.put("name", "Petofy");
            options.put("description", "Book Appointment");
            options.put("currency", "INR");
            options.put("amount", amount);//pass amount in currency subunits
            options.put("order_id", order_ID);//from response of step 3.
            options.put("theme.color", "#3366FF");
            JSONObject preFill = new JSONObject();
            preFill.put("email", Config.user_emial);
            preFill.put("contact", Config.user_phone);
            options.put("prefill", preFill);
            checkout.open(activity, options);
        } catch(Exception e) {
            Log.d("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
//        Toast.makeText(PaymentScreenActivity.this, "Transaction Successful: " + paymentData.getOrderId(), Toast.LENGTH_LONG).show();
        Intent intent = new Intent();
        intent.putExtra("Amount",amount);
        setResult(RESULT_OK, intent);
        finish();
        //Log.d"mettingId",mettingID);
        //Log.d"orderId",paymentData.getOrderId());
        //Log.d"PaymentId",paymentData.getPaymentId());
        //Log.d"amount",amount);

        PaymentHistoryParms paymentHistoryParms = new PaymentHistoryParms();
        paymentHistoryParms.setAmount(amount);
        paymentHistoryParms.setAppointmentId(mettingID);
        paymentHistoryParms.setOrderId(paymentData.getOrderId());
        paymentHistoryParms.setPaymentId(paymentData.getPaymentId());

        PaymentHistoryRequest paymentHistoryRequest = new PaymentHistoryRequest();
        paymentHistoryRequest.setData(paymentHistoryParms);


        ApiService<PaymentStatusResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getPaymentHistory(Config.token,paymentHistoryRequest), "PaymentHistory");
        //Log.d"paymentHistory",paymentHistoryRequest.toString());
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {

    }

    @Override
    public void onResponse(Response arg0, String key) {

        switch (key){
            case "GetOrder":
                try {
                    methods.customProgressDismiss();
                    Log.d("GetOrder",arg0.body().toString());
                    GetOrderResponse getOrderResponse = (GetOrderResponse) arg0.body();
                    Log.d("getOrderResponse",getOrderResponse.toString());
                    int responseCode = Integer.parseInt(getOrderResponse.getResponse().getResponseCode());
                    if (responseCode==109) {
                        payment_details_CL.setVisibility(View.VISIBLE);
                        order_ID = getOrderResponse.getData().getAttributes().getId();
                        amount = getOrderResponse.getData().getAttributes().getAmount();
                        vetName_TV.setText(vetName);
                        appointment_time_TV.setText(appointment_time);
                        consultation_fee_TV.setText("₹ "+amount);
                        Glide.with(this)
                                .load(vet_image_url)
                                .placeholder(R.drawable.doctor_dummy_image)
                                .into(parent_profile_CIV);
                    }else {
                        onBackPressed();
                        Toast.makeText(this, "Please try again !", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
                break;

            case "PaymentHistory":
                try {
                    //Log.d"GetOrder",arg0.body().toString());
                    PaymentStatusResponse paymentStatusResponse = (PaymentStatusResponse) arg0.body();
                    //Log.d"getOrderResponse",paymentStatusResponse.toString());
                    int responseCode = Integer.parseInt(paymentStatusResponse.getResponse().getResponseCode());
                    if (responseCode==109) {

                    }else {
                        onBackPressed();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onError(Throwable t, String key) {

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.procced_appointment_BT:
                startPayment();
                break;

            case R.id.back_arrow_CV:
                onBackPressed();
                break;
        }
    }
}