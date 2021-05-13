package com.cynoteck.petofyparents.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cynoteck.petofyparents.PetParentSingleton;
import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.activty.PaymentScreenActivity;
import com.cynoteck.petofyparents.adapter.PastAppointmentListAdapter;
import com.cynoteck.petofyparents.adapter.UpcomingAppointmentListAdapter;
import com.cynoteck.petofyparents.api.ApiClient;
import com.cynoteck.petofyparents.api.ApiResponse;
import com.cynoteck.petofyparents.api.ApiService;
import com.cynoteck.petofyparents.parameter.appointmentParams.AppointmentStatusParams;
import com.cynoteck.petofyparents.parameter.appointmentParams.AppointmentsStatusRequest;
import com.cynoteck.petofyparents.parameter.getPetParentAppointmentsParams.GetAppointmentRequest;
import com.cynoteck.petofyparents.parameter.getPetParentAppointmentsParams.GetAppointmentsParams;
import com.cynoteck.petofyparents.response.appointmentResponse.AppointmentList;
import com.cynoteck.petofyparents.response.appointmentResponse.GetAppointmentResponse;
import com.cynoteck.petofyparents.response.getAppointmentsStatusResponse.AppointmentStatusResponse;
import com.cynoteck.petofyparents.utils.AppointmentsClickListner;
import com.cynoteck.petofyparents.utils.Config;
import com.cynoteck.petofyparents.utils.Methods;
import com.cynoteck.petofyparents.utils.NetworkChangeReceiver;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

@SuppressLint("StaticFieldLeak")
public class AppointmentListFragment extends Fragment implements View.OnClickListener, ApiResponse, AppointmentsClickListner {
    View view;
    Methods methods;
    MaterialCardView back_arrow_CV;
    LinearLayout upcoming_appointment_tab_LL, past_appointment_tab_LL;
    TextView upcoming_appointment_TV,past_appointment_TV;
    View upcoming_appointment_line,past_appointment_view;
    static RecyclerView upcoming_appointment_RV,past_appointment_RV;
    GetAppointmentResponse getUpcomingAppointmentResponse, pastAppointmentResponse;
    PastAppointmentListAdapter pastAppointmentListAdapter;
    String mettingId = "";
    UpcomingAppointmentListAdapter upcomingAppointmentListAdapter;
    int cancelPosition;
    static ProgressBar progressBar;
    Dialog payment_successfully_dialog;
    static LinearLayout appoint_tabs_LL;
    private BroadcastReceiver mNetworkReceiver;
    static boolean isOnline = true;
    static LinearLayout something_wrong_LL;
    static Button retry_BT;
    static boolean isLoaded = false;

    public AppointmentListFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_appointment_list, container, false);
        mNetworkReceiver = new NetworkChangeReceiver();
        registerNetworkBroadcastForNougat();
        methods = new Methods(getActivity());
        initization();
        progressBar.setVisibility(View.VISIBLE);

        getUpcomingAppointments();
        getPastAppointments();
        upcoming_appointment_RV.setLayoutManager(new LinearLayoutManager(getContext()));
        upcomingAppointmentListAdapter = new UpcomingAppointmentListAdapter(getContext(), PetParentSingleton.getInstance().getUpComingAppointmentList(),this);
        upcoming_appointment_RV.setAdapter(upcomingAppointmentListAdapter);

        past_appointment_RV.setLayoutManager(new LinearLayoutManager(getContext()));
        pastAppointmentListAdapter = new PastAppointmentListAdapter(getContext(), PetParentSingleton.getInstance().getPastAppointmentList());
        past_appointment_RV.setAdapter(pastAppointmentListAdapter);

        Timer timer = new Timer ();
        TimerTask hourlyTask = new TimerTask () {
            @Override
            public void run () {
                getUpcomingAppointments();
            }
        };

        timer.schedule (hourlyTask, 0l, 20000);
        return view;

    }

    private void getPastAppointments() {
        GetAppointmentsParams getAppointmentsParams = new GetAppointmentsParams();
        getAppointmentsParams.setAppointmentType("0");
        GetAppointmentRequest getAppointmentRequest = new GetAppointmentRequest();
        getAppointmentRequest.setData(getAppointmentsParams);

        ApiService<GetAppointmentResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getAppointment(Config.token,getAppointmentRequest), "GetPastAppointment");

    }

    private void getUpcomingAppointments() {
        GetAppointmentsParams getAppointmentsParams = new GetAppointmentsParams();
        getAppointmentsParams.setAppointmentType("1");
        GetAppointmentRequest getAppointmentRequest = new GetAppointmentRequest();
        getAppointmentRequest.setData(getAppointmentsParams);
        ApiService<GetAppointmentResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getAppointment(Config.token,getAppointmentRequest), "GetUpcomingAppointment");

    }

    private void initization() {
        progressBar = view.findViewById(R.id.progressBar);
        back_arrow_CV = view.findViewById(R.id.back_arrow_CV);
        appoint_tabs_LL = view.findViewById(R.id.appoint_tabs_LL);
        upcoming_appointment_tab_LL = view.findViewById(R.id.upcoming_appointment_tab_LL);
        past_appointment_tab_LL = view.findViewById(R.id.past_appointment_tab_LL);
        upcoming_appointment_TV = view.findViewById(R.id.upcoming_appointment_TV);
        past_appointment_TV = view.findViewById(R.id.past_appointment_TV);
        upcoming_appointment_line = view.findViewById(R.id.upcoming_appointment_line);
        past_appointment_view = view.findViewById(R.id.past_appointment_view);
        upcoming_appointment_RV = view.findViewById(R.id.upcoming_appointment_RV);
        past_appointment_RV = view.findViewById(R.id.past_appointment_RV);
        something_wrong_LL = view.findViewById(R.id.something_wrong_LL);
        retry_BT=  view.findViewById(R.id.retry_BT);
        retry_BT.setOnClickListener(this);
        back_arrow_CV.setOnClickListener(this);
        upcoming_appointment_tab_LL.setOnClickListener(this);
        past_appointment_tab_LL.setOnClickListener(this);

//        upcoming_appointment_tab_LL.setEnabled(false);
//        past_appointment_tab_LL.setEnabled(false);

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_arrow_CV:
                getActivity().onBackPressed();
                break;

            case R.id.upcoming_appointment_tab_LL:
                upcoming_appointment_RV.setVisibility(View.VISIBLE);
                upcoming_appointment_line.setBackgroundResource(R.color.colorPrimary);
                upcoming_appointment_TV.setTextColor(this.getResources().getColor(R.color.colorPrimary));

                past_appointment_RV.setVisibility(View.GONE);
                past_appointment_view.setBackgroundResource(R.color.gray_4);
                past_appointment_TV.setTextColor(this.getResources().getColor(R.color.gray_3));

                break;


            case R.id.past_appointment_tab_LL:
                past_appointment_RV.setVisibility(View.VISIBLE);
                past_appointment_view.setBackgroundResource(R.color.colorPrimary);
                past_appointment_TV.setTextColor(this.getResources().getColor(R.color.colorPrimary));

                upcoming_appointment_RV.setVisibility(View.GONE);
                upcoming_appointment_line.setBackgroundResource(R.color.gray_4);
                upcoming_appointment_TV.setTextColor(this.getResources().getColor(R.color.gray_3));

                break;

            case R.id.retry_BT:
                if (isOnline){
                    something_wrong_LL.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    getUpcomingAppointments();
                    getPastAppointments();
                }else {
                    somethingWrong();
                }

                break;
        }
    }

    @Override
    public void onResponse(Response arg0, String key) {
        switch (key){
                
            case "GetUpcomingAppointment":
                try {
                    isLoaded = true;
                    somethingWrong();
                    progressBar.setVisibility(View.GONE);
                    appoint_tabs_LL.setVisibility(View.VISIBLE);
                    upcoming_appointment_tab_LL.setEnabled(true);
                    upcoming_appointment_RV.setVisibility(View.VISIBLE);
                    getUpcomingAppointmentResponse = (GetAppointmentResponse) arg0.body();
                    int responseCode = Integer.parseInt(getUpcomingAppointmentResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        PetParentSingleton.getInstance().getUpComingAppointmentList().clear();
                        for (int i = 0; i < getUpcomingAppointmentResponse.getData().size(); i++) {
                            AppointmentList appointmentList = new AppointmentList();
                            appointmentList.setVetName(getUpcomingAppointmentResponse.getData().get(i).getVetName());
                            appointmentList.setPetName(getUpcomingAppointmentResponse.getData().get(i).getPetName());
                            appointmentList.setVetProfileImage(getUpcomingAppointmentResponse.getData().get(i).getVetProfileImage());
                            appointmentList.setMeetingUrl(getUpcomingAppointmentResponse.getData().get(i).getMeetingUrl());
                            appointmentList.setIsApproved(getUpcomingAppointmentResponse.getData().get(i).getIsApproved());
                            appointmentList.setAppointmentDate(getUpcomingAppointmentResponse.getData().get(i).getAppointmentDate());
                            appointmentList.setStartDateString(getUpcomingAppointmentResponse.getData().get(i).getStartDateString());
                            appointmentList.setEndDateString(getUpcomingAppointmentResponse.getData().get(i).getEndDateString());
                            appointmentList.setPaymentStatus(getUpcomingAppointmentResponse.getData().get(i).getPaymentStatus());
                            appointmentList.setId(getUpcomingAppointmentResponse.getData().get(i).getId());
                            appointmentList.setVeterinarianUserId(getUpcomingAppointmentResponse.getData().get(i).getVeterinarianUserId());
                            appointmentList.setTitle(getUpcomingAppointmentResponse.getData().get(i).getTitle());


                            PetParentSingleton.getInstance().getUpComingAppointmentList().add(appointmentList);
                        }
                        upcomingAppointmentListAdapter.notifyDataSetChanged();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                break;

            case "GetPastAppointment":
                try {
                    past_appointment_tab_LL.setEnabled(true);
                    pastAppointmentResponse = (GetAppointmentResponse) arg0.body();
                    int responseCode = Integer.parseInt(pastAppointmentResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        PetParentSingleton.getInstance().getPastAppointmentList().clear();
                        for (int i = 0; i < pastAppointmentResponse.getData().size(); i++) {
                            AppointmentList appointmentList = new AppointmentList();
                            appointmentList.setVetName(pastAppointmentResponse.getData().get(i).getVetName());
                            appointmentList.setPetName(pastAppointmentResponse.getData().get(i).getPetName());
                            appointmentList.setVetProfileImage(pastAppointmentResponse.getData().get(i).getVetProfileImage());
                            appointmentList.setMeetingUrl(pastAppointmentResponse.getData().get(i).getMeetingUrl());
                            appointmentList.setIsApproved(pastAppointmentResponse.getData().get(i).getIsApproved());
                            appointmentList.setAppointmentDate(pastAppointmentResponse.getData().get(i).getAppointmentDate());
                            appointmentList.setStartDateString(pastAppointmentResponse.getData().get(i).getStartDateString());
                            appointmentList.setEndDateString(pastAppointmentResponse.getData().get(i).getEndDateString());
                            appointmentList.setPaymentStatus(pastAppointmentResponse.getData().get(i).getPaymentStatus());
                            appointmentList.setId(pastAppointmentResponse.getData().get(i).getId());
                            appointmentList.setVeterinarianUserId(pastAppointmentResponse.getData().get(i).getVeterinarianUserId());
                            appointmentList.setTitle(pastAppointmentResponse.getData().get(i).getTitle());
                            PetParentSingleton.getInstance().getPastAppointmentList().add(appointmentList);
                        }
                        pastAppointmentListAdapter.notifyDataSetChanged();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "Status":
                try {
                    AppointmentStatusResponse appointmentStatusResponse = (AppointmentStatusResponse) arg0.body();
                    int responseCode = Integer.parseInt(appointmentStatusResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        Toast.makeText(getContext(), "Appointment canceled Successfully", Toast.LENGTH_SHORT).show();
                        PetParentSingleton.getInstance().getUpComingAppointmentList().remove(cancelPosition);
                        upcomingAppointmentListAdapter.notifyDataSetChanged();
                    }else {
                        Toast.makeText(getContext(), "Please try again !", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
    private void cancelAppointment(String id) {
        AppointmentStatusParams appointmentStatusParams = new AppointmentStatusParams();
        appointmentStatusParams.setId(id);
        AppointmentsStatusRequest appointmentsStatusRequest = new AppointmentsStatusRequest();
        appointmentsStatusRequest.setData(appointmentStatusParams);

        ApiService<AppointmentStatusResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().cancelAppointment(Config.token, appointmentsStatusRequest), "Status");

    }
    @Override
    public void onError(Throwable t, String key) {
        somethingWrong();
    }

    @Override
    public void onItemClick(int position, ArrayList<AppointmentList> appointmentLists) {

    }

    @Override
    public void onJoinClick(int position, ArrayList<AppointmentList> appointmentLists, Button button) {
        Uri uri = Uri.parse(appointmentLists.get(position).getMeetingUrl()); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @Override
    public void onApproveClick(int position, ArrayList<AppointmentList> appointmentLists, Button button) {
        if (methods.isInternetOn()) {
            Intent intent = new Intent(getContext(), PaymentScreenActivity.class);
            intent.putExtra("vetId", appointmentLists.get(position).getVeterinarianUserId());
            intent.putExtra("vetName", appointmentLists.get(position).getVetName());
            intent.putExtra("topic", appointmentLists.get(position).getTitle());
            intent.putExtra("appointment_time", appointmentLists.get(position).getAppointmentDate()+" "+appointmentLists.get(position).getStartDateString()+" "+appointmentLists.get(position).getEndDateString());
            intent.putExtra("mettingId", appointmentLists.get(position).getId());
            intent.putExtra("vet_image_url", appointmentLists.get(position).getVetProfileImage());

            Log.e("PaymentOrder", intent.toString());

            startActivityForResult(intent, 1);
        } else {
            methods.DialogInternet();
        }

    }

    @Override
    public void onCancelClick(int position, ArrayList<AppointmentList> appointmentLists, Button button) {
        cancelPosition = position;
        mettingId = appointmentLists.get(position).getId();
        Log.d("Add Anotheer Veterian", "vet");
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("");
        alertDialog.setMessage("Do you want to cancel this appointment?");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        button.setVisibility(View.GONE);
                        cancelAppointment(mettingId);
                        dialog.dismiss();

                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cancelAppointment(mettingId);
                        dialogInterface.dismiss();

                    }

                });
        alertDialog.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                progressBar.setVisibility(View.VISIBLE);
                getUpcomingAppointments();
                showPaymentDoneDialog(data.getStringExtra("Amount"));
            }
        }
    }

    private void showPaymentDoneDialog(String amount) {
        payment_successfully_dialog = new Dialog(getContext());
        payment_successfully_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        payment_successfully_dialog.setCancelable(true);
        payment_successfully_dialog.setContentView(R.layout.payment_done_dialog);
        payment_successfully_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        TextView total_payment_TV = payment_successfully_dialog.findViewById(R.id.total_payment_TV);
        TextView  back_to_appointments_TV = payment_successfully_dialog.findViewById(R.id.back_to_appointments_TV);
        total_payment_TV.setText(amount);
        payment_successfully_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        total_payment_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payment_successfully_dialog.dismiss();
            }
        });

        payment_successfully_dialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = payment_successfully_dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }
    private void registerNetworkBroadcastForNougat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getActivity().registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getActivity().registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    public static void dialog(boolean value) {

        if(value){
            Log.e("Connected","Yes");
            isOnline = true;

            Handler handler = new Handler();
            Runnable delayrunnable = new Runnable() {
                @Override
                public void run() {
                    somethingWrong();
                }
            };
            handler.postDelayed(delayrunnable, 3000);
        }else {
            isOnline = false;
            Log.e("Connected","NO");
            somethingWrong();

        }
    }

    private static void somethingWrong() {
        progressBar.setVisibility(View.GONE);
        if (isLoaded){
            something_wrong_LL.setVisibility(View.GONE);
            upcoming_appointment_RV.setVisibility(View.VISIBLE);

        }else {
            something_wrong_LL.setVisibility(View.VISIBLE);
            upcoming_appointment_RV.setVisibility(View.GONE);
        }
    }
}