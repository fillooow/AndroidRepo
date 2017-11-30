package com.fillooow.staticticrtf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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
        sortText(chars, counters);

        entropy = entropy(counters, editString.length());

        AdapterRTF adapter = new AdapterRTF(chars, counters, editString.length());
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        entropyTV = (TextView) findViewById(R.id.entropyTV);
        entropyTV.setText(String.format(Locale.getDefault(), "%(.3f", entropy));
    }

    private void sortText(ArrayList<String> chars, ArrayList<Integer> counters) {
        ArrayList<String> tempChars = new ArrayList<>();
        ArrayList<Integer> tempCounters = new ArrayList<>();
        int low = 0;
        int high = counters.size();
        Log.d("qwe", "high = " + high + ", size = " + counters.size());
        quicksort(counters, low, high-1);
    }

    private void quicksort(ArrayList<Integer> counters, int low, int high) {
        int i = low;
        int j = high;
        Log.d("qwe", "j = " + j);
        int avg = counters.get(low+(high-low)/2);
        do {
            while(counters.get(i) > avg)
                i++;
            while (counters.get(j) < avg)
                j--;
            if(i <= j) {
                int temp = counters.get(j);
                String tempS = chars.get(j);
                counters.set(j, counters.get(i));
                chars.set(j, chars.get(i));
                counters.set(i, temp);
                chars.set(i, tempS);
                i++;
                j--;
            }
        } while(i <= j);
        if (low < j)
            quicksort(counters, low, j);
        if (i < high)
            quicksort(counters, i, high);
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

    private void huffman(){
        ArrayList<String> alphabet = new ArrayList<>();
        ArrayList<String> tree = new ArrayList<>();

    }
}
