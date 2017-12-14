package com.fillooow.staticticrtf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Locale;

public class ShowPrefixTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_prefix_text);

        String prefixString = getIntent().getExtras().getString("prefixString");
        double ratio = getIntent().getExtras().getDouble("ratio");

        TextView ratioTV = (TextView) findViewById(R.id.ratio);
        Log.d("test", "" + ratio);
        ratioTV.setText("Коэффициент: " + String.format(Locale.getDefault(), "%(.3f", ratio));

        TextView prefStrTV = (TextView) findViewById(R.id.parsedPrefixText);
        prefStrTV.setText(prefixString);
    }
}
