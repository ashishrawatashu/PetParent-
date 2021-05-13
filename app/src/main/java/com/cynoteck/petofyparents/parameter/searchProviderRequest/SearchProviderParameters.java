
package com.cynoteck.petofyparents.parameter.searchProviderRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchProviderParameters {

    @SerializedName("searchkeyword")
    @Expose
    private String searchkeyword;
    @SerializedName("cityId")
    @Expose
    private Integer cityId;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;

    public String getSearchkeyword() {
        return searchkeyword;
    }

    public void setSearchkeyword(String searchkeyword) {
        this.searchkeyword = searchkeyword;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

}
