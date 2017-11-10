package com.fillooow.staticticrtf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
        getEditText();
    }

    public void onAlphabetButtonClick(View v) {
        getEditText();
    }

    public void getEditText() {
        editText = (EditText) findViewById(R.id.editText);
        editString = editText.getText().toString();
        startActivity(new Intent(MainActivity.this, ShowActivity.class).putExtra("edit text", editString));
    }

}
