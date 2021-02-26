
package com.cynoteck.petofyparents.activty;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.api.ApiClient;
import com.cynoteck.petofyparents.api.ApiResponse;
import com.cynoteck.petofyparents.api.ApiService;
import com.cynoteck.petofyparents.parameter.getOrderDetailsParms.GetOrderParams;
import com.cynoteck.petofyparents.parameter.getOrderDetailsParms.GetOrderRequest;
import com.cynoteck.petofyparents.parameter.paymentHistoryStaus.PaymentHistoryParms;
import com.cynoteck.petofyparents.parameter.paymentHistoryStaus.PaymentHistoryRequest;
import com.cynoteck.petofyparents.response.appointmentResponse.GetAppointmentResponse;
import com.cynoteck.petofyparents.response.getAppointmentsStatusResponse.AppointmentStatusResponse;
import com.cynoteck.petofyparents.response.getOrderResponse.GetOrderResponse;
import com.cynoteck.petofyparents.response.paymentStatusResponse.PaymentStatusResponse;
import com.cynoteck.petofyparents.utils.Config;
import com.cynoteck.petofyparents.utils.Methods;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONObject;

import retrofit2.Response;

public class PaymentScreenActivity extends AppCompatActivity implements PaymentResultWithDataListener, ApiResponse, View.OnClickListener {

    Button procced_appointment_BT;
    ImageView back_arrow_IV;
    TextView vetName_TV,topic_TV,startTime_TV,endTime_TV,date_TV;
    String vetUserId,vetName,startDate,endDate,topic,mettingID,order_ID,amount;
    Methods methods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_screen);
        methods = new Methods(this);
        Intent intent = getIntent();
        vetUserId = intent.getStringExtra("vetId");
        vetName = intent.getStringExtra("vetName");
        startDate = intent.getStringExtra("startDate");
        endDate = intent.getStringExtra("endDate");
        topic = intent.getStringExtra("topic");
        mettingID = intent.getStringExtra("mettingId");

        if (methods.isInternetOn()) {
            getOrderDetails();
        }else {
            methods.DialogInternet();
        }

        initization();

    }

    private void getOrderDetails() {
        methods.showCustomProgressBarDialog(this);
        GetOrderParams getOrderParams = new GetOrderParams();
        getOrderParams.setVeterinarianId(vetUserId);
        GetOrderRequest getOrderRequest = new GetOrderRequest();
        getOrderRequest.setData(getOrderParams);

        ApiService<GetOrderResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getOrderDetails(Config.token,getOrderRequest), "GetOrder");


    }

    private void initization() {
        date_TV = findViewById(R.id.date_TV);
        vetName_TV = findViewById(R.id.vetName_TV);
        topic_TV = findViewById(R.id.topic_TV);
        startTime_TV = findViewById(R.id.startTime_TV);
        endTime_TV = findViewById(R.id.endTime_TV);
        procced_appointment_BT = findViewById(R.id.procced_appointment_BT);
        back_arrow_IV=findViewById(R.id.back_arrow_IV);
        back_arrow_IV.setOnClickListener(this);
        procced_appointment_BT.setOnClickListener(this);

    }

    private void startPayment() {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_f1R6dTWpxaiwgg");
        checkout.setImage(R.drawable.petofy);
        final Activity activity = this;
        try {
            JSONObject options = new JSONObject();

            options.put("name", "Petofy");
            options.put("description", "Book Appointment");
            options.put("currency", "INR");
            options.put("amount", amount);//pass amount in currency subunits
            options.put("order_id", order_ID);//from response of step 3.
            options.put("theme.color", "#6fac00");
            JSONObject preFill = new JSONObject();
            preFill.put("email", Config.user_emial);
            preFill.put("contact", Config.user_phone);
            options.put("prefill", preFill);
            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
//        Toast.makeText(PaymentScreenActivity.this, "Transaction Successful: " + paymentData.getOrderId(), Toast.LENGTH_LONG).show();

        Log.e("mettingId",mettingID);
        Log.e("orderId",paymentData.getOrderId());
        Log.e("PaymentId",paymentData.getPaymentId());
        Log.e("amount",amount);

        PaymentHistoryParms paymentHistoryParms = new PaymentHistoryParms();
        paymentHistoryParms.setAmount(amount);
        paymentHistoryParms.setAppointmentId(mettingID);
        paymentHistoryParms.setOrderId(paymentData.getOrderId());
        paymentHistoryParms.setPaymentId(paymentData.getPaymentId());

        PaymentHistoryRequest paymentHistoryRequest = new PaymentHistoryRequest();
        paymentHistoryRequest.setData(paymentHistoryParms);


        ApiService<PaymentStatusResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getPaymentHistory(Config.token,paymentHistoryRequest), "PaymentHistory");
        Log.e("paymentHistory",paymentHistoryRequest.toString());
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
                        order_ID = getOrderResponse.getData().getAttributes().getId();
                        amount = getOrderResponse.getData().getAttributes().getAmount();

                        vetName_TV.setText(vetName);
                        topic_TV.setText(topic);
                        startTime_TV.setText(startDate);
                        endTime_TV.setText(endDate);

                    }else {
                        onBackPressed();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
                break;

            case "PaymentHistory":
                try {
                    methods.customProgressDismiss();
                    Log.d("GetOrder",arg0.body().toString());
                    PaymentStatusResponse paymentStatusResponse = (PaymentStatusResponse) arg0.body();
                    Log.d("getOrderResponse",paymentStatusResponse.toString());
                    int responseCode = Integer.parseInt(paymentStatusResponse.getResponse().getResponseCode());
                    if (responseCode==109) {
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.procced_appointment_BT:
                startPayment();
                break;

            case R.id.back_arrow_IV:
                onBackPressed();
                break;
        }
    }
}