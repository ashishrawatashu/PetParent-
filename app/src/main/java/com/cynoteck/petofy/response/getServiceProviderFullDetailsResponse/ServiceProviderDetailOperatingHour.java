
package com.cynoteck.petofy.response.getServiceProviderFullDetailsResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceProviderDetailOperatingHour {

    @SerializedName("id")
    @Expose
    private Double id;
    @SerializedName("veterinarianId")
    @Expose
    private Integer veterinarianId;
    @SerializedName("dayId")
    @Expose
    private Double dayId;
    @SerializedName("startTime")
    @Expose
    private String startTime;
    @SerializedName("endTime")
    @Expose
    private String endTime;
    @SerializedName("startTimeId")
    @Expose
    private Integer startTimeId;
    @SerializedName("endTimeId")
    @Expose
    private Integer endTimeId;
    @SerializedName("isClosed")
    @Expose
    private Boolean isClosed;
    @SerializedName("allDayOpen")
    @Expose
    private Boolean allDayOpen;
    @SerializedName("day")
    @Expose
    private ServiceProviderDetailDay day;
    @SerializedName("veterinarian")
    @Expose
    private Object veterinarian;
    @SerializedName("timeList")
    @Expose
    private Object timeList;

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public Integer getVeterinarianId() {
        return veterinarianId;
    }

    public void setVeterinarianId(Integer veterinarianId) {
        this.veterinarianId = veterinarianId;
    }

    public Double getDayId() {
        return dayId;
    }

    public void setDayId(Double dayId) {
        this.dayId = dayId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getStartTimeId() {
        return startTimeId;
    }

    public void setStartTimeId(Integer startTimeId) {
        this.startTimeId = startTimeId;
    }

    public Integer getEndTimeId() {
        return endTimeId;
    }

    public void setEndTimeId(Integer endTimeId) {
        this.endTimeId = endTimeId;
    }

    public Boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }

    public Boolean getAllDayOpen() {
        return allDayOpen;
    }

    public void setAllDayOpen(Boolean allDayOpen) {
        this.allDayOpen = allDayOpen;
    }

    public ServiceProviderDetailDay getDay() {
        return day;
    }

    public void setDay(ServiceProviderDetailDay day) {
        this.day = day;
    }

    public Object getVeterinarian() {
        return veterinarian;
    }

    public void setVeterinarian(Object veterinarian) {
        this.veterinarian = veterinarian;
    }

    public Object getTimeList() {
        return timeList;
    }

    public void setTimeList(Object timeList) {
        this.timeList = timeList;
    }

}
