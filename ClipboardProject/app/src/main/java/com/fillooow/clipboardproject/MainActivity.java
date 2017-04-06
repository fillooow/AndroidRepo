package com.fillooow.clipboardproject;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity/* implements ClipboardTextListFragment.ClipboardListListener*/{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*@Override
    public void itemClicked(long id) {
        Intent intent = new Intent(this, )
    }*/
}





    /*private StringBuffer sharedPreferencesText = new StringBuffer("");
    private String text = "";
    private String textOnSave = "";
    String pasteData = "";
    String checkPasteData = "";
    private static final String APP_PREFERENCES = "mysettings"; // Имя файла настроек
    private static final String PREF_TEXT = "ClipboardText"; // Текст из буфера
    SharedPreferences mSettings; // Переменная, представляющая экземпляр класса
    // мы инициализируем её в onCreate()
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Передаём в указанный метод название нашего файла (он будет создан
        // автоматически) и разрешение, дающее доступ только
        // компонентам приложения
        textView = (TextView) findViewById(R.id.clipboard_text);
        mSettings = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);

        //if(mSettings.contains(APP_PREFERENCES)){
        //textView.setText(mSettings.getString(APP_PREFERENCES, "123"));
       // }
        refreshDat();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mSettings.contains(APP_PREFERENCES)) {
            savedText = mSettings.getString(PREF_TEXT, "123");
            textView.setText(savedText);
        }
    }

    public void refreshDat () {
        final TextView textView = (TextView) findViewById(R.id.clipboard_text);
        final Handler handler = new Handler();
        handler.post(new Runnable() { //post() обеспечивает мгновенное выполнение кода
            @Override
            public void run() {
                // Получаем ссылку на буфер обмена
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0); // Получаем первый айтем из буфера обмена
                text = (String) item.getText();
                if (!(checkPasteData.equals(text))) {
                    textView.append("\n" + text);
                    checkPasteData = text;
                    sharedPreferencesText = sharedPreferencesText.append(text + "\n");
                }
                handler.post(this);
            }
        });
    }

    public void onClickRefresh(View view) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
        text = (String) item.getText();
        if (checkPasteData.equals(text))
            return;

        TextView textView = (TextView) findViewById(R.id.clipboard_text);
        textView.append("\n" + text);
        checkPasteData = text;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //textOnSave = textView.getText().toString();
        textOnSave = sharedPreferencesText.toString();
        outState.putString("savedText", textOnSave);
        sharedPreferencesText = textOnSave.to`
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        textOnSave = savedInstanceState.getString("savedText");
        textView.setText(textOnSave);
    }

    @Override
    protected void onPause() {
        super.onPause();
        String textToSave = textView.getText().toString(); // Вытаскиваем текст
        // для его сохранения
        SharedPreferences.Editor editor = mSettings.edit(); // Получаем ссылку на эдитор
        editor.putString(PREF_TEXT, textToSave); // Сейвим текст
        editor.apply();
    }*/

