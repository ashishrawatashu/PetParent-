package com.cynoteck.petofy.response.adoptionListResponse;

import com.cynoteck.petofy.response.getPetReportsResponse.PagingHeader;

import java.util.List;

public class AdoptionListData {
    private List<PetDonationList> petDonationList = null;
    private PagingHeader pagingHeader;

    public List<PetDonationList> getPetDonationList() {
        return petDonationList;
    }

    public void setPetDonationList(List<PetDonationList> petDonationList) {
        this.petDonationList = petDonationList;
    }

    public PagingHeader getPagingHeader() {
        return pagingHeader;
    }

    public void setPagingHeader(PagingHeader pagingHeader) {
        this.pagingHeader = pagingHeader;
    }

    @Override
    public String toString() {
        return "ClassPojo[" +
                "petDonationList=" + petDonationList +
                ", pagingHeader=" + pagingHeader +
                "]";
    }
}
