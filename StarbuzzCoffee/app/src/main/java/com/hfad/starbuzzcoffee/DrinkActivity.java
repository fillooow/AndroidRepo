package com.hfad.starbuzzcoffee;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DrinkActivity extends AppCompatActivity {
    public static final String EXTRA_DRINKNO = "drinkNo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        int drinkNo = (Integer)getIntent().getExtras().get(EXTRA_DRINKNO);

        try {
            SQLiteOpenHelper starbuzzDatabaseHelper = new SturbuzzDatabaseHelper(this);
            // Открываем БД для чтения
            SQLiteDatabase db = starbuzzDatabaseHelper.getReadableDatabase();
            // Создаём курсор
            Cursor cursor = db.query("DRINK",
                    new String[] {"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID"},
                    "_id = ?", // Получаем запись через id
                    new String[] {Integer.toString(drinkNo)},
                    null, null, null);
            // Переходим к первой записи в курсоре
            if (cursor.moveToFirst()) {
                // Даже если запись одна, всё равно получаем её
                // Получаем данные из напитка
                String nameText = cursor.getString(0); // Получаем NAME
                String getDescription = cursor.getString(1); // Получаем DESCRIPTION
                int photoId = cursor.getInt(2);                // Название напитка
                TextView name = (TextView) findViewById(R.id.name);
                name.setText(nameText);
            }
        }
        Drink drink = Drink.drinks[drinkNo];

        ImageView photo = (ImageView) findViewById(R.id.photo);
        // Источник данных графического поля назначается вызовом sIR()
        // либо в макете android:src
        photo.setImageResource(drink.getImageResourceId());
        // Это для повышения уровня доступности приложения
        // Либо можно в макете прописать android:contentDescription
        photo.setContentDescription(drink.getName());



        // Описание напитка
        TextView description = (TextView) findViewById(R.id.description);
        description.setText(drink.getDescription());
    }
}
