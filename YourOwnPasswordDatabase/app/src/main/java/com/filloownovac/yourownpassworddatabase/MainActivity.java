package com.filloownovac.yourownpassworddatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText passwordField = (EditText) findViewById(R.id.passField);
    String passwordDB = passwordField.getText().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onOpen(View view) {
        FileWorker.openDB(pathDB,passwordDB);
    }

    public void onCheckBoxClicked(View view){
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {
            case R.id.checkbox_backup:
                if (checked)
                    FileWorker.isBackupNeed = true;
                else
                    FileWorker.isBackupNeed = false;
                break;
        }
    }

}
