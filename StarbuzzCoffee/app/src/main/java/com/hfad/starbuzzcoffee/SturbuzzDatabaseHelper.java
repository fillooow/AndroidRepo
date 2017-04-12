package com.hfad.starbuzzcoffee;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Fillow on 12.04.2017.
 */

public class SturbuzzDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME ="starbuzz"; // Имя БД
    private static final int DB_VERSION = 1; // Версия БД

    // БД создаётся лишь после обращения приложения к ней
    SturbuzzDatabaseHelper(Context context) {
        super(context, DB_NAME,null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE DRINK (" // Создаём таблицу с названием DRINK
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, " // Создаём стобец _id (он обязателен),
                // который является идентефикатором строки, прописываем ему тип данных INTEGER,
                // указываем, что это первичный ключ (PRIMARY KEY) и ставим AUTOINCREMENT,
                // это чтобы при занесении новой строки ей генерится новый идентефикатор
                + "NAME TEXT, " // Создаём текстовый столб NAME
                + "IMAGE_RESOURCE_ID INTEGER);"); // Столб для id картинок

    }

    // Вызывается при необходимости обновления БД
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private static void insertDrink(SQLiteDatabase db, String name,
                                    String desciption, int resourceId) {
        ContentValues drinkValues = new ContentValues();
        drinkValues.put("NAME", name);
    }
}
