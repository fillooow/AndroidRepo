package com.fillooow.staticticrtf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity {
    String editString;
    ArrayList<String> chars;
    ArrayList<Integer> counters;
    boolean registerOff;
    boolean spacesOff;
    boolean marksOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        editString = getIntent().getStringExtra("editString");
        registerOff = getIntent().getBooleanExtra("registerOff", false);
        spacesOff = getIntent().getBooleanExtra("spacesOff", false);
        marksOff = getIntent().getBooleanExtra("marksOff", false);

        if (registerOff)
            editString = editString.toUpperCase();
        if (spacesOff)
            editString = editString.replaceAll("\\s+","");
        if (marksOff)
            editString = editString.replaceAll("[^a-zA-Zа-яА-Я]", "");

        chars = new ArrayList<>();
        counters = new ArrayList<>();

        entropy(editString);

        AdapterRTF adapter = new AdapterRTF(chars, counters);
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    private void entropy(String string) {
        int counter = 0;
        for (int i = 0; i < string.length(); i++) {
            Character ch = string.charAt(i);
            if (!chars.contains(ch.toString())) {
                chars.add(ch.toString());
                counters.add(1);
                counter++;
            } else {
                counters.set(chars.indexOf(ch.toString()), counters.get(chars.indexOf(ch.toString())) + 1);
                counter++;
            }
            Log.d("tag", "counter " + counter);
        }
    }
}
