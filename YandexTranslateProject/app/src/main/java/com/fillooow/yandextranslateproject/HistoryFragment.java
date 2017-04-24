package com.fillooow.yandextranslateproject;


import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {
    // Мы добавляем эти переменные, чтобы курсор и БД можно было бы закрыть в onDestroy()
    private SQLiteDatabase db;
    private TranslateAdapter tA;
    // private Cursor cursor;

    private String[] originalText;
    private String[] descriptionText;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {
            SQLiteOpenHelper yandexDatabaseHelper = new YandexDatabaseHelper(getActivity());
            db = yandexDatabaseHelper.getReadableDatabase(); // Получаем ссылку на БД

            Cursor cursor = db.query("YANDEXTRANSLATE", // Создаём курсор
                    new String[] {"ORIGINALTEXT","DESCRIPTIONTEXT"},
                    "_id = ?",
                    new String[] {Integer.toString(1)} , null, null, null);
            if (cursor.moveToFirst()) {
                String origText = cursor.getString(0);
                //TextView orTW = (TextView) getActivity().findViewById(R.id.or_text);
                //orTW.setText(origText);
                //TextView desTW = (TextView) getActivity().findViewById(R.id.des_text);
                String desText = cursor.getString(1);
                //desTW.setText(desText);
//              Тут NPE, не шарю
                originalText[0] = origText;
                descriptionText[0] = desText;
                tA = new TranslateAdapter(originalText, descriptionText);
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e) { // В случае ошибки, выводим тост
            Toast.makeText(getActivity(), "Чёт факапнулось 2", Toast.LENGTH_SHORT).show();
        }

        //RecyclerView translateRecycler = (RecyclerView) inflater.inflate(
         //       R.layout.fragment_history, container, false);
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

}

