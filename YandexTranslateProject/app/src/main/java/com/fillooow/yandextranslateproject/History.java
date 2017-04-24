package com.fillooow.yandextranslateproject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Fillow on 24.04.2017.
 */

public class History {
    private String originalText;
    private String descriptionText;
    private SQLiteDatabase db;
    private TranslateAdapter tA;

    public History() {
        try {
            //SQLiteOpenHelper yandexDatabaseHelper = new YandexDatabaseHelper(getActivity());
            //db = yandexDatabaseHelper.getReadableDatabase(); // Получаем ссылку на БД

            Cursor cursor = db.query("YANDEXTRANSLATE", // Создаём курсор
                    new String[]{"ORIGINALTEXT", "DESCRIPTIONTEXT"},
                    "_id = ?", new String[] {Integer.toString(1)} , null, null, null);
            if (cursor.moveToFirst()) {
                String origText = cursor.getString(0);
                String desText = cursor.getString(1);
                //tA = new TranslateAdapter(origText, desText);
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e) { // В случае ошибки, выводим тост
            //Toast.makeText(getActivity(), "Чёт факапнулось 2", Toast.LENGTH_SHORT).show();
        }
    }
}
