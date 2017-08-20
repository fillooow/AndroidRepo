package com.hfad.penyok.API;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Model {

    @SerializedName("login")
    @Expose
    private String login;

    /* @SerializedName("result")
    @Expose
    private List<ResultModel> resultStats = null;*/

    public String getLogin() {
        return login;
    }

    private void setLogin(String login) {
        this.login = login;
    }

    /*public List<ResultModel> getResultStats() {
        return resultStats;
    }

    public void setResultStats(List<ResultModel> resultStats) {
        this.resultStats = resultStats;
    }*/
}
