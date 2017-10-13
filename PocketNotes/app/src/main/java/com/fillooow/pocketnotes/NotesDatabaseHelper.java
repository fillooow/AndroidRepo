package com.fillooow.pocketnotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Работа с БД происходит тут
 */

//TODO: dictionary notes
public class NotesDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME ="notesDB"; // Имя БД
    private static final int DB_VERSION = 1; // Версия БД

    // БД создаётся лишь после обращения приложения к ней
    NotesDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    // Создаём БД, если она ещё не создана
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE NOTES (" // Создаём таблицу с названием
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, " // Создаём стобец _id
                + "TERM TEXT, " // Введённый текст
                + "DESCRIPTION TEXT );"); // Переведённый текст
        ContentValues contentValues = new ContentValues();
        contentValues.put("TERM", "тест");
        contentValues.put("DESCRIPTION", "Система формализованных заданий, " +
                "по результатам выполнения к-рых можно судить об уровне развития " +
                "определённых качеств испытуемого, а также о его знаниях, умениях и навыках.");
        db.insert("NOTES", null, contentValues);
        contentValues.put("TERM", "test");
        contentValues.put("DESCRIPTION", "Система формализованных заданий, " +
                "по результатам выполнения к-рых можно судить об уровне развития " +
                "определённых качеств испытуемого, а также о его знаниях, умениях и навыках.");
        db.insert("NOTES", null, contentValues);
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