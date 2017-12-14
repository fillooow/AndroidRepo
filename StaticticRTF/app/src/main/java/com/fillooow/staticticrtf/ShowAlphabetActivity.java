package com.fillooow.staticticrtf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class ShowAlphabetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_alphabet);

        ArrayList<String> chars = getIntent().getStringArrayListExtra("chars");
        ArrayList<String> prefixes = getIntent().getStringArrayListExtra("prefixes");

        AlphabetAdapter adapter = new AlphabetAdapter(chars, prefixes);
        RecyclerView rv = (RecyclerView) findViewById(R.id.rvAlphabet);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
}
