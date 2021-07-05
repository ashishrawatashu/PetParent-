package com.cynoteck.petofy.utils;

import com.cynoteck.petofy.response.appointmentResponse.AppointmentList;
import com.cynoteck.petofy.response.getAdoptionRequestListResponse.GetAdoptionRequestListData;
import com.cynoteck.petofy.response.getDonationRequestResponse.GetDonationRequestData;
import com.cynoteck.petofy.response.getPetReportsResponse.getPetListResponse.PetList;

import java.util.ArrayList;

public class PetParentSingleton {
    private static PetParentSingleton petParentSingleton;
    public static PetParentSingleton getInstance() {
        if (petParentSingleton == null)
            petParentSingleton = new PetParentSingleton();
        return petParentSingleton;
    }
    public ArrayList<PetList> petLists = new ArrayList<>();
    public ArrayList<AppointmentList> upComingAppointmentList = new ArrayList<>();
    public ArrayList<AppointmentList> pastAppointmentList = new ArrayList<>();
    public ArrayList<GetAdoptionRequestListData> getAdoptionRequestListData = new ArrayList<>();
    public ArrayList<GetDonationRequestData> getDonationRequestListData = new ArrayList<>();

    private PetParentSingleton() { }

    public ArrayList<GetDonationRequestData> getGetDonationRequestListData() {
        return getDonationRequestListData;
    }

    public void setGetDonationRequestListData(ArrayList<GetDonationRequestData> getDonationRequestListData) {
        this.getDonationRequestListData = getDonationRequestListData;
    }

    public ArrayList<GetAdoptionRequestListData> getGetAdoptionRequestListData() {
        return getAdoptionRequestListData;
    }

    public void setGetAdoptionRequestListData(ArrayList<GetAdoptionRequestListData> getAdoptionRequestListData) {
        this.getAdoptionRequestListData = getAdoptionRequestListData;
    }

    public void setArrayList(ArrayList<PetList> petLists) {
        this.petLists = petLists;

    }

    public ArrayList<PetList> getArrayList() {
        return this.petLists;

    }

    public ArrayList<AppointmentList> getUpComingAppointmentList() {
        return upComingAppointmentList;
    }

    public void setUpComingAppointmentList(ArrayList<AppointmentList> upComingAppointmentList) {
        this.upComingAppointmentList = upComingAppointmentList;
    }

    public ArrayList<AppointmentList> getPastAppointmentList() {
        return pastAppointmentList;
    }

    public void setPastAppointmentList(ArrayList<AppointmentList> pastAppointmentList) {
        this.pastAppointmentList = pastAppointmentList;
    }
}
