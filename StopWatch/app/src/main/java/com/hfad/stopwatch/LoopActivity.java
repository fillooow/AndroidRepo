package com.hfad.stopwatch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class LoopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loop);
        Intent intent = getIntent();
        String loopTextMes = intent.getStringExtra("loopStrings");
        TextView loopMessageView = (TextView) findViewById(R.id.loopStrings);
        loopMessageView.setText(loopTextMes);
    }
}
