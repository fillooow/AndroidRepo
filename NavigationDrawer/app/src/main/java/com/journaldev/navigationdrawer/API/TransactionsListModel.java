package com.journaldev.navigationdrawer.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TransactionsListModel {

        @SerializedName("Id")
        @Expose
        private Integer id;
        @SerializedName("with")
        @Expose
        private With with;
        @SerializedName("count")
        @Expose
        private Integer count;
        @SerializedName("time")
        @Expose
        private String time;
        @SerializedName("hidden")
        @Expose
        private Boolean hidden;
        @SerializedName("accepted")
        @Expose
        private Boolean accepted;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("sign")
        @Expose
        private List<Object> sign = null;
        @SerializedName("cent")
        @Expose
        private Integer cent;
        @SerializedName("closed")
        @Expose
        private Object closed;
        @SerializedName("total")
        @Expose
        private Integer total;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public With getWith() {
            return with;
        }

        public void setWith(With with) {
            this.with = with;
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

        public List<Object> getSign() {
            return sign;
        }

        public void setSign(List<Object> sign) {
            this.sign = sign;
        }

        public Integer getCent() {
            return cent;
        }

        public void setCent(Integer cent) {
            this.cent = cent;
        }

        public Object getClosed() {
            return closed;
        }

        public void setClosed(Object closed) {
            this.closed = closed;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

    }