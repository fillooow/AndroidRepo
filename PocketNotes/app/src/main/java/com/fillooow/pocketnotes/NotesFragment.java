package com.fillooow.pocketnotes;


import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotesFragment extends Fragment {
    // Мы добавляем эти переменные, чтобы курсор и БД можно было бы закрыть в onDestroy()
    private SQLiteDatabase db;

    private final String dbName = "NOTES";
    private final String dbTermRow = "TERM";
    private final String dbDescriptionRow = "DESCRIPTION";

    private ArrayList<String> term = new ArrayList<>();
    private ArrayList<String> description = new ArrayList<>();

    private RecyclerView notesRecycler;
    private Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        notesRecycler = (RecyclerView) inflater.inflate(
                R.layout.fragment_notes, container, false);

        loadDBNotes(context);

        // Загружаем массивы в адаптер
        // TODO: синк эбоут ит
        NotesDatabaseAdapter adapter = new NotesDatabaseAdapter(getActivity(), 10/*,
                new NotesDatabaseAdapter.NoteDeleteItemListener() {
                    @Override
                    public void onChangeClick(int id) {

                    }
                }*/);
        notesRecycler.setAdapter(adapter); // Ставим адаптер
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        notesRecycler.setLayoutManager(layoutManager); // Ставим разметку
        return notesRecycler;
    }

    public void loadDBNotes(Context context){
        int counter = 0; // Счётчик записей в БД


        try {
            SQLiteOpenHelper notesCountDB = new NotesDatabaseHelper(context);
            db = notesCountDB.getReadableDatabase();

            Cursor countCursor = db.query(dbName, // Создаём курсор
                    new String[]{"COUNT (_id) AS count"},
                    null, null, null, null, null);
            /*if (countCursor.moveToFirst()) {
                counter = countCursor.getInt(0);
            } */
            countCursor.close();
        } catch (SQLiteException e) {
            Toast.makeText(getActivity(), "Счётчик " + /*факапнулся*/ "не хочет работать",
                    Toast.LENGTH_SHORT).show();
            //counter = 20; // Таки прибиваем максимальный размер истории ручками
        }


        int i = 0; // Другой счётчик. Нам он будет помогать перебирать строки
        // Цепляем строки из БД, расфасовываем в массивы для адаптера
        try {
            SQLiteOpenHelper notesDatabaseHelper = new NotesDatabaseHelper(context);
            db = notesDatabaseHelper.getReadableDatabase(); // Получаем ссылку на БД

            Cursor cursor = db.query(dbName, // Создаём курсор
                    new String[]{dbTermRow, dbDescriptionRow},
                    null, null, null, null, null);
            // Выгружаем текст в массив
            if (cursor.moveToFirst()) {
                do {
                    String termText = cursor.getString(0);
                    term.add(termText);
                    String desText = cursor.getString(1);
                    description.add(desText);
                    i++;
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e) { // В случае ошибки, выводим тост
            Toast.makeText(getActivity(), "БД факапнулась в заметках", Toast.LENGTH_SHORT).show();
        }
    }

    public void loadDBRow(Context context, int id) {}

    public ArrayList<String> getTerm() {
        return term;
    }

    public ArrayList<String> getDescription() {
        return description;
    }

    public RecyclerView getNotesRecycler() {
        return notesRecycler;
    }
}