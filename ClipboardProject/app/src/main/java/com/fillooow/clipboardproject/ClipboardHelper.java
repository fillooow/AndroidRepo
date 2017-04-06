package com.fillooow.clipboardproject;

import java.util.ArrayList;

/**
 * Created by Fillow on 07.04.2017.
 */

public class ClipboardHelper {
    ArrayList<String> clipboardTextArrayList = new ArrayList<>();

    public void setClipboardTextArrayList(String text) {
        clipboardTextArrayList.add(text);
    }

    public String getStringFromArrayList(int id) {
        if (clipboardTextArrayList.isEmpty())
            return "Your clipboard is empty";
        else
            return clipboardTextArrayList.get(id);
    }
}
