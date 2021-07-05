package com.cynoteck.petofy.onClicks;

import android.widget.Button;

import com.cynoteck.petofy.response.appointmentResponse.AppointmentList;

import java.util.ArrayList;

public interface AppointmentsClickListner {
    public void onItemClick(int position, ArrayList<AppointmentList> appointmentLists);
    public void onJoinClick(int position,ArrayList<AppointmentList> appointmentLists, Button button);
    public void onApproveClick(int position,ArrayList<AppointmentList> appointmentLists, Button button);
    public void onCancelClick(int position,ArrayList<AppointmentList> appointmentLists, Button button);

}
