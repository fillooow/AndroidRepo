package com.journaldev.navigationdrawer.API;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyFriendModel {

    @SerializedName("key")
    @Expose
    private Integer key;
    @SerializedName("value")
    @Expose
    private String value;

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}