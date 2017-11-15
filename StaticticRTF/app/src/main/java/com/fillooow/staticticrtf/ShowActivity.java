package com.fillooow.staticticrtf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

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
        chars = new ArrayList<>();
        counters = new ArrayList<>();
        entropy = 0.0;

        editString = getIntent().getStringExtra("editString");
        boolean registerOff = getIntent().getBooleanExtra("registerOff", false);
        boolean spacesOff = getIntent().getBooleanExtra("spacesOff", false);
        boolean marksOff = getIntent().getBooleanExtra("marksOff", false);
        boolean doubleChars = getIntent().getBooleanExtra("doubleChars", false);

        if (registerOff)
            editString = editString.toUpperCase();
        if (spacesOff)
            editString = editString.replaceAll("\\s+","");
        if (marksOff)
            editString = editString.replaceAll("[^a-zA-Zа-яёА-ЯЁ]", "");
        if (!doubleChars) {
            dismemberOfText(editString);
        } else {
            editString = editString.replaceAll("[^a-zA-Zа-яёА-ЯЁ]", "");
            doubleDismemberOfText(editString);
        }

        entropy = entropy(counters, editString.length());

        AdapterRTF adapter = new AdapterRTF(chars, counters, editString.length());
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        entropyTV = (TextView) findViewById(R.id.entropyTV);
        entropyTV.setText(String.format(Locale.getDefault(), "%(.3f", entropy));
    }

    private void doubleDismemberOfText(String string) {
        for (int i = 0; i < string.length()-1; i++) {
            Character ch1 = string.charAt(i);
            Character ch2 = string.charAt(i+1);
            String doubleCh = ch1.toString() + ch2.toString();
            if (!chars.contains(doubleCh)) {
                chars.add(doubleCh);
                counters.add(1);
            } else
                counters.set(chars.indexOf(doubleCh), counters.get(chars.indexOf(doubleCh)) + 1);
        }
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

    private double entropy(ArrayList<Integer> count, int length) {
        double entropy = 0;
        for (int i: count) {
            entropy = entropy + ((double)i/length)*(Math.log((double)i/length)/Math.log(2));
        }
        return entropy*(-1);
    }
}
