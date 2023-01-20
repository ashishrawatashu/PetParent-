package com.cynoteck.petofy.parameter.adoptionListRequest;

public class AdoptionListRequestModel {

    private AdoptionListHeader header;
    private AdoptionListParameter data;

    public AdoptionListHeader getHeader() {
        return header;
    }

    public void setHeader(AdoptionListHeader header) {
        this.header = header;
    }

    public AdoptionListParameter getData() {
        return data;
    }

    public void setData(AdoptionListParameter data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ClassPojo[" +
                "header=" + header +
                ", data=" + data +
                "]";
    }
}
