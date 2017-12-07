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
    private List<MyFriendModel> myFriends = null;

    @SerializedName("user_stats")
    @Expose
    private List<MyStatsModel> myStats = null;

    @SerializedName("name_for")
    @Expose
    private String nameFor;

    @SerializedName("transactions_list")
    @Expose
    private List<TransactionsListModel> transactionsList = null;

    @SerializedName("remove_friend")
    @Expose
    private Boolean removeFriend;

    @SerializedName("fill_state")
    @Expose
    private String fillState;


    public String getLogin() {
        return login;
    }

    private void setLogin(String login) {
        this.login = login;
    }

    public List<MyFriendModel> getMyFriends() {
        return myFriends;
    }

    public void setMyFriends(List<MyFriendModel> myFriends) {
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


    public List<TransactionsListModel> getTransactionsList() {
        return transactionsList;
    }

    public void setTransactionsList(List<TransactionsListModel> transactionsList) {
        this.transactionsList = transactionsList;
    }

    public Boolean getRemoveFriend() {
        return removeFriend;
    }

    public void setRemoveFriend(Boolean removeFriend) {
        this.removeFriend = removeFriend;
    }

    public String getFillState() {
        return fillState;
    }

    public void setFillState(String fillState) {
        this.fillState = fillState;
    }
}
