package com.fillooow.yandextranslateproject;

/**
 * Created by Fillow on 23.04.2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Работа с БД происходит тут
 */

public class YandexDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME ="yandexTranslate"; // Имя БД
    private static final int DB_VERSION = 1; // Версия БД

    // БД создаётся лишь после обращения приложения к ней
    YandexDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    // Создаём БД, если она ещё не создана
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE YANDEXTRANSLATE (" // Создаём таблицу с названием DRINK
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, " // Создаём стобец _id
                + "ORIGINALTEXT TEXT, " // Введённый текст
                + "DESCRIPTIONTEXT TEXT, " // Переведённый текст
                + "COUNT INTEGER);"); // Счётчик записей
        ContentValues contentValues = new ContentValues();
        contentValues.put("ORIGINALTEXT", "test");
        contentValues.put("DESCRIPTIONTEXT", "тест");
        db.insert("YANDEXTRANSLATE", null, contentValues);
        contentValues.put("ORIGINALTEXT", "poshe ti naher, kozyol");
        contentValues.put("DESCRIPTIONTEXT", "go fuck yourself, you asshole");
        db.insert("YANDEXTRANSLATE", null, contentValues);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Вызывается при необходимости обновления БД
    }

    /*
    // Метод для автоматизации забрасывания инфы в таблицу
    private static void insertDrink(SQLiteDatabase db, String name,
                                    String description, int resourceId) {
        // Для добавления пар имя/значение необходимо создать объект типа ContentValues
        ContentValues drinkValues = new ContentValues();
        // Для каждого зачения указываем название столбца и сохраняемое значение
        drinkValues.put("NAME", name);
        drinkValues.put("DESCRIPTION", description);
        drinkValues.put("IMAGE_RESOURCE_ID", resourceId);
        db.insert("DRINK", null, drinkValues); // Созраняем строку в таблицу DRINK
        // null для передачи null в пустую таблицу (параметр nullColumnHack)
    } */
}