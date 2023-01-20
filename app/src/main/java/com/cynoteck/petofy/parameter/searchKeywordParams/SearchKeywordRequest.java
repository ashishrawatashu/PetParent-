
package com.cynoteck.petofy.parameter.searchKeywordParams;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchKeywordRequest {

    @SerializedName("data")
    @Expose
    private SearchKeywordParams data;

    public SearchKeywordParams getData() {
        return data;
    }

    public void setData(SearchKeywordParams data) {
        this.data = data;
    }

}
