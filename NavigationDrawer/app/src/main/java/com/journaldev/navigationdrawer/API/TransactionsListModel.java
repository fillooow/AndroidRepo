package com.journaldev.navigationdrawer.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionsListModel {

    @SerializedName("Id") // Id транзакции
    @Expose
    private Integer id;
    @SerializedName("author") // Создатель транзакции
    @Expose
    private Integer author; // Автор
    @SerializedName("payer")
    @Expose
    private Integer payer; // Отдаёт, ему надо будет вернуть
    @SerializedName("payee")
    @Expose
    private Integer payee; // Получает, он должен будет вернуть
    @SerializedName("count")
    @Expose
    private Integer count; // Сколько
    @SerializedName("time")
    @Expose
    private String time; // Когда принята
    @SerializedName("hidden")
    @Expose
    private Boolean hidden; // Отображается ли в публичной истории
    @SerializedName("accepted")
    @Expose
    private Boolean accepted; // Принята
    @SerializedName("message")
    @Expose
    private String message; // Сообщение к транзакции
    @SerializedName("date")
    @Expose
    private String date; // До этой даты

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAuthor() {
        return author;
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }

    public Integer getPayer() {
        return payer;
    }

    public void setPayer(Integer payer) {
        this.payer = payer;
    }

    public Integer getPayee() {
        return payee;
    }

    public void setPayee(Integer payee) {
        this.payee = payee;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
