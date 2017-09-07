package com.journaldev.navigationdrawer.API;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Model {

    @SerializedName("login")
    @Expose
    private String login;

    @SerializedName("my_friends")
    @Expose
    private List<Integer> myFriends = null;

    @SerializedName("my_stats")
    @Expose
    private List<MyStatsModel> myStats = null;

    @SerializedName("name_for")
    @Expose
    private String nameFor;



    public String getLogin() {
        return login;
    }

    private void setLogin(String login) {
        this.login = login;
    }

    public List<Integer> getMyFriends() {
        return myFriends;
    }

    public void setMyFriends(List<Integer> myFriends) {
        this.myFriends = myFriends;
    }

    public List<MyStatsModel> getMyStats() {
        return myStats;
    }

    public void setMyStats(List<MyStatsModel> myStats) {
        this.myStats = myStats;
    }

    public String getNameFor() {
        return nameFor;
    }

    public void setNameFor(String nameFor) {
        this.nameFor = nameFor;
    }

}
