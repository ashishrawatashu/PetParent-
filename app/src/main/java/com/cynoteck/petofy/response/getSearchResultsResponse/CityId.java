
package com.cynoteck.petofy.response.getSearchResultsResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityId {

    @SerializedName("value")
    @Expose
    private Integer value;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

}
