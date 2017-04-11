package com.fillooow.recyclerview;

/**
 * Created by Fillow on 11.04.2017.
 */

public class ItemData {
    private String title;
    private int imageUrl;

    public ItemData(String title, int imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }
}
