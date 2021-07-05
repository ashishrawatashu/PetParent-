
package com.cynoteck.petofy.parameter.serviceProviderDetailRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchProviderFullDetailRequest {

    @SerializedName("data")
    @Expose
    private SearchProviderFullDetailData data;

    public SearchProviderFullDetailData getData() {
        return data;
    }

    public void setData(SearchProviderFullDetailData data) {
        this.data = data;
    }

}
