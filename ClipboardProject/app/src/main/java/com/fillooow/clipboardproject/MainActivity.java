package com.fillooow.clipboardproject;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String APP_PREFERENCES = "mysettings"; // Имя файла настроек
    private static final String PREF_TEXT = "ClipboardText"; // Текст из буфера
    String checkPasteData = "";
    String pasteData = "";
    SharedPreferences mSettings; // Переменная, представляющая экземпляр класса
    // мы инициализируем её в onCreate()

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Передаём в указанный метод название нашего файла (он будет создан
        // автоматически) и разрешение, дающее доступ только
        // компонентам приложения
        if(mSettings.contains(APP_PREFERENCES)){
            TextView tw = (TextView) findViewById(R.id.clipboard_text);
            tw.append(mSettings.getString(APP_PREFERENCES, ""));
        }
        mSettings = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        //refreshDat();
    }

    public void refreshDat () {
        final TextView textView = (TextView) findViewById(R.id.clipboard_text);
        final Handler handler = new Handler();
        handler.post(new Runnable() { //post() обеспечивает мгновенное выполнение кода
            @Override
            public void run() {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
                pasteData = (String) item.getText();
                if (!(checkPasteData.equals(pasteData))) {
                    textView.append("\n" + pasteData);
                    checkPasteData = pasteData;
                }
                handler.postDelayed(this, 100);
            }
        });
    }

    public void onClickRefresh(View view) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
        pasteData = (String) item.getText();
        if (checkPasteData.equals(pasteData))
            return;

        TextView textView = (TextView) findViewById(R.id.clipboard_text);
        textView.append("\n" + pasteData);
        checkPasteData = pasteData;
    }

    @Override
    protected void onStop() {
        super.onStop();
        TextView textView = (TextView) findViewById(R.id.clipboard_text);
        String textToSave = textView.getText().toString(); // Вытаскиваем текст
        // для его сохранения
        SharedPreferences.Editor editor = mSettings.edit(); // Получаем ссылку на эдитор
        editor.putString(PREF_TEXT, textToSave); // Сейвим текст
        editor.apply();
    }
}
