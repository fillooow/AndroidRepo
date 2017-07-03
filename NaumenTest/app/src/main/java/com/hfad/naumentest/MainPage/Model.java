package com.hfad.naumentest.MainPage;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hfad.naumentest.MainPage.Item;

public class Model {

    @SerializedName("items")
    @Expose
    private List<Item> items = null;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("offset")
    @Expose
    private Integer offset;
    @SerializedName("total")
    @Expose
    private Integer total;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}