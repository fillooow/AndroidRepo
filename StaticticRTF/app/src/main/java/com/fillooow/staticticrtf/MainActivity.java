package com.fillooow.staticticrtf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    Button symbols;
    EditText editText;
    CheckBox registerOff;
    CheckBox spacesOff;
    CheckBox marksOff;
    CheckBox doubleChars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        symbols = (Button) findViewById(R.id.symbolsButton);
        editText = (EditText) findViewById(R.id.editText);

        registerOff = (CheckBox) findViewById(R.id.checkBoxRegisterOff);
        spacesOff = (CheckBox) findViewById(R.id.checkBoxSpacesOff);
        marksOff = (CheckBox) findViewById(R.id.checkBoxMarksOff);
        doubleChars = (CheckBox) findViewById(R.id.doubleChars);
    }

    public void onSymbolsButtonClick(View v) {
        if (!editText.getText().toString().equals(""))
            startActivity(new Intent(MainActivity.this, ShowActivity.class)
                    .putExtra("editString", editText.getText().toString())
                    .putExtra("registerOff", registerOff.isChecked())
                    .putExtra("spacesOff", spacesOff.isChecked())
                    .putExtra("marksOff", marksOff.isChecked())
                    .putExtra("doubleChars", doubleChars.isChecked()));
    }
}
