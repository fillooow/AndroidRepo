package com.hfab.layouttestproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class YourTeaActivity extends AppCompatActivity {
    ArrayList<String> gotTea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_tea);

        Intent teaIntent = getIntent();
        gotTea = teaIntent.getStringArrayListExtra("readyTea");
        TextView teaView = (TextView) findViewById(R.id.teaTV);
        for(String tea: gotTea)
            teaView.append(tea + " ");
    }
}
