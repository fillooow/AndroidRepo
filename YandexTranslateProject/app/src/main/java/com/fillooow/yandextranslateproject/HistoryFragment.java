package com.fillooow.yandextranslateproject;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends ListFragment {
    // Мы добавляем эти переменные, чтобы курсор и БД можно было бы закрыть в onDestroy()
    private SQLiteDatabase db;
    // private Cursor cursor;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            SQLiteOpenHelper yandexDatabaseHelper = new YandexDatabaseHelper(getActivity());
            db = yandexDatabaseHelper.getReadableDatabase(); // Получаем ссылку на БД

            Cursor cursor = db.query("YANDEXTRANSLATE", // Создаём курсор
                    new String[]{"_id", "ORIGINALTEXT", "DESCRIPTIONTEXT"},
                    null, null, null, null, null);
            if (cursor.moveToFirst()) {


            /* CursorAdapter listAdapter = new SimpleCursorAdapter(getActivity(),
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"ORIGINALTEXT", "DESCRIPTIONTEXT"},
                    new int[]{android.R.id.text1}, // Дефолтный TextView, вроде как
                    0);

            setListAdapter(listAdapter);*/

            }
        } catch (SQLiteException e) { // В случае ошибки, выводим тост
            Toast.makeText(getActivity(), "Чёт факапнулось", Toast.LENGTH_SHORT).show();
        }
    }
/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       /* try {
            SQLiteOpenHelper yandexDatabaseHelper = new YandexDatabaseHelper(getActivity());
            db = yandexDatabaseHelper.getReadableDatabase(); // Получаем ссылку на БД

            cursor = db.query("YANDEXTRANSLATE", // Создаём курсор
                    new String[]{"_id", "ORIGINALTEXT", "DESCRIPTIONTEXT"},
                    null, null, null, null, null);

            CursorAdapter listAdapter = new SimpleCursorAdapter(getActivity(),
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"ORIGINALTEXT", "DESCRIPTION TEXT"},
                    new int[]{android.R.id.text1}, // Дефолтный TextView, вроде как
                    0);
            setListAdapter(listAdapter);
        } catch (SQLiteException e) { // В случае ошибки, выводим тост
            Toast.makeText(getActivity(), "Чёт факапнулось", Toast.LENGTH_SHORT).show();
        }
       ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, data);
        setListAdapter(adapter);

        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
*/

}
