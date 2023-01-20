
package com.cynoteck.petofy.response.insuranceAfterLoginResponses.getAllDetailsAfterLogin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PetAgeUnit {

    @SerializedName("disabled")
    @Expose
    private Boolean disabled;
    @SerializedName("group")
    @Expose
    private String group;
    @SerializedName("selected")
    @Expose
    private Boolean selected;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("value")
    @Expose
    private String value;

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
