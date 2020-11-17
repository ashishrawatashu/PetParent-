package com.cynoteck.petofyparents.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.activty.AddUpdateAppointmentActivity;
import com.cynoteck.petofyparents.activty.PaymentScreenActivity;
import com.cynoteck.petofyparents.adapter.DateListAdapter;
import com.cynoteck.petofyparents.adapter.VetListAdapter;
import com.cynoteck.petofyparents.api.ApiClient;
import com.cynoteck.petofyparents.api.ApiResponse;
import com.cynoteck.petofyparents.api.ApiService;
import com.cynoteck.petofyparents.parameter.appointmentParams.AppointmentStatusParams;
import com.cynoteck.petofyparents.parameter.appointmentParams.AppointmentsStatusRequest;
import com.cynoteck.petofyparents.parameter.getVetListParams.GetVetListParams;
import com.cynoteck.petofyparents.parameter.getVetListParams.GetVetListRequest;
import com.cynoteck.petofyparents.response.appointmentResponse.AppointmentList;
import com.cynoteck.petofyparents.response.appointmentResponse.GetAppointmentDates;
import com.cynoteck.petofyparents.response.appointmentResponse.GetAppointmentResponse;
import com.cynoteck.petofyparents.response.getAppointmentsStatusResponse.AppointmentStatusResponse;
import com.cynoteck.petofyparents.response.getVetListResponse.GetVetListResponse;
import com.cynoteck.petofyparents.response.getVetListResponse.ProviderList;
import com.cynoteck.petofyparents.utils.AppointmentsClickListner;
import com.cynoteck.petofyparents.utils.Config;
import com.cynoteck.petofyparents.utils.Methods;
import com.cynoteck.petofyparents.utils.RegisterRecyclerViewClickListener;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class AppointementFragment extends Fragment implements ApiResponse,View.OnClickListener, RegisterRecyclerViewClickListener {
    FloatingActionButton create_appointment_FBT;
    GetAppointmentResponse getAppointmentResponse;
    View view;
    RecyclerView date_day_RV;
    DateListAdapter dateListAdapter;
    ArrayList<GetAppointmentDates> getAppointmentDates;
    Methods methods;
    ArrayList<AppointmentList> appointmentList;
    String mettingId = "", status = "", pet_id = "", pet_owner_name = "", pet_sex = "", pet_age = "", pet_unique_id = "";
    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    String latitude="30.3647", longitude="78.0888";
    private ShimmerFrameLayout mShimmerViewContainer;
    AppointmentsClickListner appointmentsClickListner;
    Dialog vetDilog;
    VetListAdapter vetListAdapter;
    RecyclerView vetList_RV;
    private ArrayList<ProviderList> providerLists;
    GetVetListResponse getVetListResponse;

    public AppointementFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_appointement, container, false);

        methods = new Methods(getContext());
        date_day_RV = view.findViewById(R.id.date_day_RV);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        create_appointment_FBT = view.findViewById(R.id.create_appointment_FBT);
        create_appointment_FBT.setOnClickListener(this);

        if (methods.isInternetOn()) {
            getAppointment();
        } else {
            methods.DialogInternet();
        }locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        } else {
            getCurrentLocation();
        }


        return view;

    }

    private void getAppointment() {
        ApiService<GetAppointmentResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getAppointment(Config.token), "GetAppointment");

    }



    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.create_appointment_FBT:

                GetVetListParams getVetListParams = new GetVetListParams();
                getVetListParams.setLattitude(latitude);
                getVetListParams.setLongitude(longitude);
                getVetListParams.setViewMore("true");

                GetVetListRequest getVetListRequest = new GetVetListRequest();
                getVetListRequest.setData(getVetListParams);

                ApiService<GetVetListResponse> service = new ApiService<>();
                service.get(this, ApiClient.getApiInterface().getVetList(Config.token, getVetListRequest), "GetVetList");
                Log.e("DATALOG", "check1=> " + getVetListRequest);


                vetListDiloag();
                break;


        }
    }

    private void vetListDiloag() {
        vetDilog = new Dialog(getContext());
        vetDilog.setContentView(R.layout.vet_list_dilog);
        vetList_RV = vetDilog.findViewById(R.id.vetList_RV);
        mShimmerViewContainer = vetDilog.findViewById(R.id.shimmer_view_container);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = vetDilog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        vetDilog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        vetDilog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        vetDilog.show();


    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);
                Log.e("location", latitude + " " + longitude);
            } else {
                Toast.makeText(getActivity(), "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onResponse(Response arg0, String key) {
        Log.d("Response", arg0.toString());
        switch (key) {
            case "GetAppointment":
                try {
                    getAppointmentResponse = (GetAppointmentResponse) arg0.body();
                    Log.d("GetAppointment", getAppointmentResponse.toString());
                    int responseCode = Integer.parseInt(getAppointmentResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
//                        approveAndReject("19","false");

                        mShimmerViewContainer.setVisibility(View.GONE);
                        create_appointment_FBT.setVisibility(View.VISIBLE);
                        mShimmerViewContainer.stopShimmer();
                        dateListAdapter = new DateListAdapter(getAppointmentResponse.getData(), getContext(), new AppointmentsClickListner() {
                            @Override
                            public void onItemClick(int position, ArrayList<AppointmentList> appointmentLists) {
//                                Intent intent = new Intent(getContext(),AddUpdateAppointmentActivity.class);
//                                intent.putExtra("type","update");
//                                intent.putExtra("id",appointmentLists.get(position).getId());
//                                intent.putExtra("pet_id",appointmentLists.get(position).getPetId());
//                                intent.putExtra("petParent",appointmentLists.get(position).getPetUniqueId());
//
//                                startActivity(intent);

                            }

                            @Override
                            public void onJoinClick(int position, ArrayList<AppointmentList> appointmentLists, Button button) {
                                Uri uri = Uri.parse(appointmentLists.get(position).getMeetingUrl()); // missing 'http://' will cause crashed
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);

                            }

                            @Override
                            public void onApproveClick(final int position, final ArrayList<AppointmentList> appointmentLists, final Button button) {

                                if (methods.isInternetOn()) {
                                    Intent intent = new Intent(getContext(), PaymentScreenActivity.class);
                                    intent.putExtra("vetId", appointmentLists.get(position).getVeterinarianUserId());
                                    intent.putExtra("vetName", appointmentLists.get(position).getVetName());
                                    intent.putExtra("topic", appointmentLists.get(position).getTitle());
                                    intent.putExtra("startDate", appointmentLists.get(position).getStartDateString());
                                    intent.putExtra("endDate", appointmentLists.get(position).getEndDateString());
                                    intent.putExtra("mettingId", appointmentLists.get(position).getId());
                                    Log.e("PaymentOrder",intent.toString());

                                    startActivityForResult(intent,1);
                                } else {
                                    methods.DialogInternet();
                                }

                            }

                            @Override
                            public void onCancelClick(int position, ArrayList<AppointmentList> appointmentLists, Button button) {
                                {
                                    mettingId = appointmentLists.get(position).getId();
                                    Log.d("Add Anotheer Veterian","vet");
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
                            }


                        });
                        date_day_RV.setVisibility(View.VISIBLE);
                        date_day_RV.setLayoutManager(new LinearLayoutManager(getContext()));
                        date_day_RV.setAdapter(dateListAdapter);
                        dateListAdapter.notifyDataSetChanged();


                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "Status":
                try {
                    Log.d("appointmentstaus", arg0.body().toString());
                    AppointmentStatusResponse appointmentStatusResponse = (AppointmentStatusResponse) arg0.body();
                    Log.d("appointmentstaus", appointmentStatusResponse.toString());
                    int responseCode = Integer.parseInt(appointmentStatusResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        Toast.makeText(getContext(), "Status Changes Successfully", Toast.LENGTH_SHORT).show();
                        getAppointment();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "GetVetList":
                    try {
                        getVetListResponse = (GetVetListResponse) arg0.body();
                        Log.d("DATALOG", getVetListResponse.toString());
                        int responseCode = Integer.parseInt(getVetListResponse.getResponse().getResponseCode());
                        if (responseCode == 109) {
                            if (getVetListResponse.getData().getProviderList().isEmpty()) {
                                mShimmerViewContainer.setVisibility(View.GONE);
                                mShimmerViewContainer.stopShimmer();
                                Toast.makeText(getContext(), "No Data Found!", Toast.LENGTH_SHORT).show();
                            } else {
//                            search_IV.setVisibility(View.VISIBLE);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                                vetList_RV.setLayoutManager(linearLayoutManager);
                                vetListAdapter = new VetListAdapter(getContext(), getVetListResponse.getData().getProviderList(), this);
                                providerLists = getVetListResponse.getData().getProviderList();
                                vetList_RV.setAdapter(vetListAdapter);
                                vetListAdapter.notifyDataSetChanged();
                                mShimmerViewContainer.setVisibility(View.GONE);
                                mShimmerViewContainer.stopShimmer();
                            }

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
        Log.d("Statusrequest",appointmentsStatusRequest.toString());

        ApiService<AppointmentStatusResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().cancelAppointment(Config.token,appointmentsStatusRequest), "Status");

    }

    @Override
        public void onError (Throwable t, String key){

        }

    @Override
    public void onProductClick(int position) {
        Intent  intent = new Intent(getActivity(), AddUpdateAppointmentActivity.class);
        intent.putExtra("type","add");
        intent.putExtra("id","");
        intent.putExtra("pet_id","");
        intent.putExtra("vetUserId",getVetListResponse.getData().getProviderList().get(position).getId());
        startActivityForResult(intent,1);
        vetDilog.dismiss();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                mShimmerViewContainer.setVisibility(View.VISIBLE);
                mShimmerViewContainer.startShimmer();
                date_day_RV.setVisibility(View.GONE);
                getAppointment();
            }
        }
    }


}
