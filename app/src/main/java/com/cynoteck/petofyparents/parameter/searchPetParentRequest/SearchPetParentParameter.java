package com.cynoteck.petofyparents.parameter.searchPetParentRequest;

public class SearchPetParentParameter {

    private String prefix;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String toString() {
        return "ClassPojo[" +
                "prefix= " + prefix +
                "]";
    }
}
