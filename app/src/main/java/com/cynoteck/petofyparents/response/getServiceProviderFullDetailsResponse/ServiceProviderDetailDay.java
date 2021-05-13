
package com.cynoteck.petofyparents.response.getServiceProviderFullDetailsResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceProviderDetailDay {

    @SerializedName("id")
    @Expose
    private Double id;
    @SerializedName("dayName")
    @Expose
    private String dayName;

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

}
