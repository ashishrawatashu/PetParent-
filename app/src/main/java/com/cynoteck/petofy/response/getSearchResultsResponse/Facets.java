
package com.cynoteck.petofy.response.getSearchResultsResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Facets {

    @SerializedName("CityId")
    @Expose
    private List<CityId> cityId = null;

    public List<CityId> getCityId() {
        return cityId;
    }

    public void setCityId(List<CityId> cityId) {
        this.cityId = cityId;
    }

}
