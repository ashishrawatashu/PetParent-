
package com.cynoteck.petofy.response.getSearchResultsResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultList {

    @SerializedName("totalCount")
    @Expose
    private Integer totalCount;
    @SerializedName("coverage")
    @Expose
    private Object coverage;
    @SerializedName("facets")
    @Expose
    private Facets facets;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Object getCoverage() {
        return coverage;
    }

    public void setCoverage(Object coverage) {
        this.coverage = coverage;
    }

    public Facets getFacets() {
        return facets;
    }

    public void setFacets(Facets facets) {
        this.facets = facets;
    }

}
