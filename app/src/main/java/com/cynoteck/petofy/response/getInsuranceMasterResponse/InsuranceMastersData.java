package com.cynoteck.petofy.response.getInsuranceMasterResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class InsuranceMastersData {

    @SerializedName("insurancePlanModel")
    @Expose
    private ArrayList<InsurancePlanModel> insurancePlanModel = null;
    @SerializedName("diseasesListModel")
    @Expose
    private ArrayList<DiseasesListModel> diseasesListModel = null;

    public ArrayList<InsurancePlanModel> getInsurancePlanModel() {
        return insurancePlanModel;
    }

    public void setInsurancePlanModel(ArrayList<InsurancePlanModel> insurancePlanModel) {
        this.insurancePlanModel = insurancePlanModel;
    }

    public ArrayList<DiseasesListModel> getDiseasesListModel() {
        return diseasesListModel;
    }

    public void setDiseasesListModel(ArrayList<DiseasesListModel> diseasesListModel) {
        this.diseasesListModel = diseasesListModel;
    }

}
