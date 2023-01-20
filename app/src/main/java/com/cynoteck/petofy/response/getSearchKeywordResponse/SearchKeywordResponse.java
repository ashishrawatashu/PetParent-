
package com.cynoteck.petofy.response.getSearchKeywordResponse;

import java.util.List;

import com.cynoteck.petofy.response.Header;
import com.cynoteck.petofy.response.Response;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchKeywordResponse {

    @SerializedName("header")
    @Expose
    private Header header;
    @SerializedName("data")
    @Expose
    private List<SearchKeywordData> data = null;
    @SerializedName("response")
    @Expose
    private Response response;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public List<SearchKeywordData> getData() {
        return data;
    }

    public void setData(List<SearchKeywordData> data) {
        this.data = data;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}
