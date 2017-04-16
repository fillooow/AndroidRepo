package com.hfad.starbuzzcoffee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Fillow on 12.04.2017.
 */

public class StarbuzzDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME ="starbuzz"; // Имя БД
    private static final int DB_VERSION = 2; // Версия БД, если она будет выше версии, прибитой к
    // БД - вызывается onUpdate(), если ниже - onDowngrade()

    // БД создаётся лишь после обращения приложения к ней
    StarbuzzDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // Заполняем БД информацией
    // onCreate() вызывается лишь если БД не установлена, обязателен
    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    // Вызывается при необходимости обновления БД, обязателен
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion ); // Вызываем наш метод апдейта
    }

    // Тут апдейтим нашу БД
    private void updateMyDatabase (SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE DRINK (" // Создаём таблицу с названием DRINK
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT, " // Создаём стобец _id (он обязателен),
                    // который является идентефикатором строки, прописываем ему тип данных INTEGER,
                    // указываем, что это первичный ключ (PRIMARY KEY) и ставим AUTOINCREMENT,
                    // это чтобы при занесении новой строки ей генерится новый идентефикатор
                    + "NAME TEXT, " // Создаём текстовый столб NAME
                    + "DESCRIPTION TEXT, "
                    + "IMAGE_RESOURCE_ID INTEGER);"); // Столб для id картинок
            // db.execSQL("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC"); эт для добавления столбца
            insertDrink(db, "Latte", "A couple of espresso shots with steamed milk", R.drawable.latte);
            insertDrink(db, "Cappuccino", "Espresso, hot milk and a steamed milk foam", R.drawable.cappuccino);
        /*Cursor cursor = db.query("DRINK", new String[] {"NAME", "DESCRIPTION"}, "NAME = ?",
                new String[] {DB_NAME}, null, null, null);*/
        }
        insertDrink(db, "Filter", "Our best drip coffee", R.drawable.filter);
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC");
        }
    }

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
    }
}
