package com.hfad.starbuzzcoffee;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.hfad.starbuzzcoffee.Drink.drinks;

public class DrinkActivity extends AppCompatActivity {
    public static final String EXTRA_DRINKNO = "drinkNo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        int drinkNo = (Integer)getIntent().getExtras().get(EXTRA_DRINKNO);

        try {
            SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
            // Открываем БД для чтения
            SQLiteDatabase db = starbuzzDatabaseHelper.getReadableDatabase();
            // Создаём курсор
            Cursor cursor = db.query("DRINK",
                    new String[] {"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID", "FAVORITE"},
                    "_id = ?", // Получаем запись через id
                    new String[] {Integer.toString(drinkNo)},
                    null, null, null);
            // Переходим к первой записи в курсоре
            if (cursor.moveToFirst()) {
                // Даже если запись одна, всё равно получаем её
                // Получаем данные из напитка
                String nameText = cursor.getString(0); // Получаем имя (столбец NAME)
                String getDescription = cursor.getString(1); // Получаем описание (столбец DESCRIPTION)
                int photoId = cursor.getInt(2); // Получаем id картинки (нного писать, и так понятно)

                TextView name = (TextView) findViewById(R.id.name);
                name.setText(nameText); // Заполняем название напитка
                TextView description = (TextView) findViewById(R.id.description);
                description.setText(getDescription); // Заполняем описание
                ImageView photo = (ImageView) findViewById(R.id.photo);
                photo.setImageResource(photoId); // Льём фоточку красоточки
                photo.setContentDescription(nameText);
                boolean isFavorite = (cursor.getInt(3) == 1); // Если вернётся 0, выдаст false,
                // если 1 - true
                CheckBox favorite = (CheckBox) findViewById(R.id.favorite);
                favorite.setChecked(isFavorite); // Задаём состояние флажка
            }
            cursor.close(); // Закрываем курсор
            db.close(); // Закрываем БД
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    // Обновляем БД по щелчку
    public void onFavoriteClicked (View view) {
        int drinkNo = (Integer) getIntent().getExtras().get(EXTRA_DRINKNO);
        new UpdateDrinkTask().execute(drinkNo); // Выполняем задачу

        /*CheckBox favorite = (CheckBox) findViewById(R.id.favorite);
        ContentValues drinkValues = new ContentValues();
        drinkValues.put("FAVORITE", favorite.isChecked());
        SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(DrinkActivity.this);
        try {
            SQLiteDatabase db = starbuzzDatabaseHelper.getWritableDatabase();
            db.update("DRINK", drinkValues,
                    "_id = ?", new String[] { Integer.toString(drinkNo) });
            db.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }*/
    }

    // Params (для doInBackGround), Progress (для onProgressUpdate) и Result (onPostExecute)
    private class UpdateDrinkTask extends AsyncTask<Integer, Void, Boolean> {
        ContentValues drinkValues;

        @Override
        protected void onPreExecute() {
            CheckBox favorite = (CheckBox) findViewById(R.id.favorite);
            drinkValues = new ContentValues();
            drinkValues.put("FAVORITE", favorite.isChecked());
        }

        @Override
        protected Boolean doInBackground(Integer... drinks) {
            int drinkNo = drinks[0];
            SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(DrinkActivity.this);
            try {
                SQLiteDatabase db = starbuzzDatabaseHelper.getWritableDatabase();
                db.update("DRINK", drinkValues,
                        "_id = ?", new String[] { Integer.toString(drinkNo) });
                db.close();
            } catch (SQLiteException e) {
                return false;
            }
        }
    }
}
