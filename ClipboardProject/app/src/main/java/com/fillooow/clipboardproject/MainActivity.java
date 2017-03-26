package com.fillooow.clipboardproject;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    String checkPasteData = "";
    String pasteData = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refreshDat();
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
                if (checkPasteData.equals(pasteData))
                    return;
                else {
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
}
