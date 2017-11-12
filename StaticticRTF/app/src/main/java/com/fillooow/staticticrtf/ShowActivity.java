package com.fillooow.staticticrtf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

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
        chars = new ArrayList<>();
        counters = new ArrayList<>();
        if (getIntent().getStringExtra("key").equals("alphabet")) {
            alphabetStuff(getIntent().getStringExtra("editString"));
            //Toast.makeText(getApplicationContext(), "alphabet", Toast.LENGTH_SHORT).show();
            Log.d("tag", "alphabet");
        }
        else if (getIntent().getStringExtra("key").equals("symbols")) {
            symbolsStuff(getIntent().getStringExtra("editString"));
            //Toast.makeText(getApplicationContext(), "symbols", Toast.LENGTH_SHORT).show();
            Log.d("tag", "symbols");
        }
        AdapterRTF adapter = new AdapterRTF(chars, counters);
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    private void alphabetStuff(String key) {
    }

    private void symbolsStuff(String string) {
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
