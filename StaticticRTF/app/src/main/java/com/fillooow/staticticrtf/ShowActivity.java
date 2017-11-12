package com.fillooow.staticticrtf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity {
    String key;
    String editString;
    ArrayList<String> chars;
    ArrayList<Integer> counters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        
        key = getIntent().getStringExtra("key");
        if (getIntent().getStringExtra("key").equals("alphabet"))
            symbolsStuff(getIntent().getStringExtra("editString"));
        else if (getIntent().getStringExtra("key").equals("alphabet"))
            alphabetStuff(getIntent().getStringExtra("editString"));
        AdapterRTF adapter = new AdapterRTF(chars, counters);
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    private void alphabetStuff(String key) {
    }

    private void symbolsStuff(String string) {
        for (int i = 0; i < string.length(); i++) {
            Character ch = string.charAt(i);
            if (!chars.contains(ch.toString())) {
                chars.add(ch.toString());
                counters.add(1);
            } else {
                counters.add(chars.indexOf(ch.toString()), counters.get(i) + 1);
            }
        }
    }
}
