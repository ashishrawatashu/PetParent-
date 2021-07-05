
package com.cynoteck.petofy.parameter.searchProviderRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchProviderRequest {

    @SerializedName("data")
    @Expose
    private SearchProviderParameters data;

    public SearchProviderParameters getData() {
        return data;
    }

    public void setData(SearchProviderParameters data) {
        this.data = data;
    }

}
