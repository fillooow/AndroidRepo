package com.fillooow.staticticrtf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity {
    private String editString;
    private ArrayList<String> chars;
    private ArrayList<Integer> counters;
    private Double entropy;

    TextView entropyTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        editString = getIntent().getStringExtra("editString");
        boolean registerOff = getIntent().getBooleanExtra("registerOff", false);
        boolean spacesOff = getIntent().getBooleanExtra("spacesOff", false);
        boolean marksOff = getIntent().getBooleanExtra("marksOff", false);

        if (registerOff)
            editString = editString.toUpperCase();
        if (spacesOff)
            editString = editString.replaceAll("\\s+","");
        if (marksOff)
            editString = editString.replaceAll("[^a-zA-Zа-яёА-ЯЁ]", "");

        chars = new ArrayList<>();
        counters = new ArrayList<>();

        dismemberOfText(editString);
        //entropy = entropy(counters);
        entropy = entropy;

        AdapterRTF adapter = new AdapterRTF(chars, counters);
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        entropyTV = (TextView) findViewById(R.id.entropyTV);
        entropyTV.setText(entropy.toString());
    }

    private void dismemberOfText(String string) {
        for (int i = 0; i < string.length(); i++) {
            Character ch = string.charAt(i);
            if (!chars.contains(ch.toString())) {
                chars.add(ch.toString());
                counters.add(1);
            } else
                counters.set(chars.indexOf(ch.toString()), counters.get(chars.indexOf(ch.toString())) + 1);
        }
    }

    private double entropy(ArrayList<Integer> count) {
        double entropy = 0;
        for (int i: count) {
            entropy = entropy + ((i/count.size())*(Math.log(i/count.size())/Math.log(2)));
        }
        return entropy*(-1);
    }
}
