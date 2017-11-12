package com.fillooow.staticticrtf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    Button symbols;
    Button alphabet;
    EditText editText;
    String editString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        symbols = (Button) findViewById(R.id.symbolsButton);
        alphabet = (Button) findViewById(R.id.alphabetButton);
    }

    public void onSymbolsButtonClick(View v) {
        getEditText("symbols");
    }

    public void onAlphabetButtonClick(View v) {
        getEditText("alphabet");
    }

    public void getEditText(String key) {
        editText = (EditText) findViewById(R.id.editText);
        editString = editText.getText().toString();
        startActivity(new Intent(MainActivity.this, ShowActivity.class)
                .putExtra("editString", editString).putExtra("key", key));
    }

    /*private void alphabetStuff(String string) {
        //for(int i = 0; i<string.length(); i++)
            //true;

    }*/

    /*private void symbolsStuff(String string) {
        for (int i = 0; i<string.length(); i++) {
            if(!chars.contains(string.charAt(i))) {
                chars.add(string.charAt(i));
                counters.add(1);
            }
            else {
                int value = counters.get(i);
                counters.add(i, value+1);
            }
        }
    }*/

}
