package com.fillooow.yandextranslateproject;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {
    // Мы добавляем эти переменные, чтобы курсор и БД можно было бы закрыть в onDestroy()
    private SQLiteDatabase db;

    private final String dbName = "YANDEXTRANSLATE";
    private final String dbOriginalRow = "ORIGINALTEXT";
    private final String dbDescriptionRow = "DESCRIPTIONTEXT";

    private String[] originalText;
    private String[] descriptionText;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RecyclerView historyRecycler = (RecyclerView) inflater.inflate(
                R.layout.fragment_history, container, false);

        int counter = 0; // Счётчик записей в БД

        // Делаем эту штуку ниже, чтобы не обрабатывать пустые строки и не прибивать вручную размер
        // массива равный 20. Пролистав вниз возникнет желание сказать "Но постой, в catch ты и
        // так вручную прибиваешь 20 значений, а их ещё и обработать надо будет, тк вполне
        // себе появится/может появиться несколько пустых строк", а я отвечу - чувак, у меня
        // горел дедлайн

        // TODO: Таки обработать эти нулевые строки, хотя бы потом, для себя и выработки привычки
        try {
            SQLiteOpenHelper yandexCountDB = new YandexDatabaseHelper(getActivity());
            db = yandexCountDB.getReadableDatabase();

            Cursor countCursor = db.query(dbName, // Создаём курсор
                    new String[] {"COUNT (_id) AS count"},
                    null, null, null, null, null);
            if (countCursor.moveToFirst()) {
                counter = countCursor.getInt(0);
            }
            countCursor.close();
        } catch (SQLiteException e) {
            Toast.makeText(getActivity(), "Счётчик " + /*факапнулся*/ "не хочет работать",
                    Toast.LENGTH_SHORT).show();
            counter = 20; // Таки прибиваем максимальный размер истории ручками
        }
        originalText = new String[counter]; // Всё лишь ради этого
        descriptionText = new String[counter];



        int i = 0; // Другой счётчик. Нам он будет помогать перебирать строки
        // Цепляем строки из БД, расфасовываем в массивы для адаптера
        try {
            SQLiteOpenHelper yandexDatabaseHelper = new YandexDatabaseHelper(getActivity());
            db = yandexDatabaseHelper.getReadableDatabase(); // Получаем ссылку на БД

            Cursor cursor = db.query(dbName, // Создаём курсор
                    new String[] {dbOriginalRow,dbDescriptionRow},
                    null, null, null, null, null);
            // Выгружаем текст в массив
            if (cursor.moveToFirst()) {
                do {
                    String origText = cursor.getString(0);
                    originalText[i] = origText;
                    String desText = cursor.getString(1);
                    descriptionText[i] = desText;
                    i++;
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e) { // В случае ошибки, выводим тост
            Toast.makeText(getActivity(), "БД факапнулась в истории", Toast.LENGTH_SHORT).show();
        }
        // Загружаем массивы в адаптер
        YandexTranslateAdapter adapter = new YandexTranslateAdapter(originalText, descriptionText);
        historyRecycler.setAdapter(adapter); // Ставим адаптер
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        historyRecycler.setLayoutManager(layoutManager); // Ставим разметку
        return historyRecycler;
    }

}

