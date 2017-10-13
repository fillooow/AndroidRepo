package com.fillooow.pocketnotes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.CardView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Fillow on 24.04.2017.
 */

public class NotesDatabaseAdapter extends RecyclerView.Adapter<NotesDatabaseAdapter.ViewHolder>{
    private String termText;
    private String desText;

    private SQLiteDatabase db;

    private final String dbName = "NOTES";
    private final String dbTermRow = "TERM";
    private final String dbDescriptionRow = "DESCRIPTION";
    private final String dbID = "_id";

    private int size = 0; // Счётчик записей в БД
    private int counter = 0;

    Context context;

    public NotesDatabaseAdapter(Context context, int size) {
        this.context = context;
        this.size = size;
    }

    // Предоставляет ссылку на представления, используемые в RecyclerView
    // Класс ViewHolder сообщает, с какими данными должен работать адаптер
    public static class ViewHolder extends RecyclerView.ViewHolder/* implements View.OnClickListener*/{
        private CardView cardView;
        // Так как мы отображаем карточки в RecycleView, определяем их тут
        public ViewHolder(CardView v/*, NoteDeleteItemListener itemListener*/) { // Каждый объект ViewHolder отображает CardView
            super(v); // Необходимо вызвать конструктор суперкласса, тк СК ViewHolder включает
            // метаданные, необходимые для правильной работы адаптера
            // (позиция варианта в RecyclerView, например)
            cardView = v;
        }
    }

    @Override
    public NotesDatabaseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_notes, parent, false);
        return new ViewHolder(cv);
    }


    @Override
    public void onBindViewHolder(NotesDatabaseAdapter.ViewHolder holder, final int position) {

        final int pos = holder.getAdapterPosition();

        CardView cardView = holder.cardView; // Получаем карточку
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(pos);
                findRow();
            }
        });

            // Цепляем строки из БД, расфасовываем в массивы для адаптера
            try {
                SQLiteOpenHelper notesDatabaseHelper = new NotesDatabaseHelper(context);
                db = notesDatabaseHelper.getReadableDatabase(); // Получаем ссылку на БД

                Cursor cursor = db.query(dbName, // Создаём курсор
                        new String[]{dbTermRow, dbDescriptionRow},
                        null, null, null, null, null);
                // Выгружаем текст в массив
                if (cursor.moveToFirst()) {
                    for (int i = 0; i < counter; i++)
                        cursor.moveToNext();
                    termText = cursor.getString(0);
                    desText = cursor.getString(1);
                    counter++;
                }
                cursor.close();
                db.close();
            } catch (SQLiteException e) { // В случае ошибки, выводим тост
                Toast.makeText(context, "БД факапнулась в заметках", Toast.LENGTH_SHORT).show();
            }
            // Заполняем поля
            TextView originalText = (TextView) cardView.findViewById(R.id.term);
            originalText.setText(termText);
            TextView descriptionText = (TextView) cardView.findViewById(R.id.description);
            descriptionText.setText(desText);
            Log.d("testim", "size = " + size + ", counter = " + counter);
    }

    private void removeItem(int position) {
        //Log.d("tag1234", "term = " + termTextList.get(position) + ", decription = " + desTextList.get(position) + ", position = " + position + ", size = " + getItemCount());
        //termTextList.remove(position);
        //desTextList.remove(position);
        notifyItemRemoved(position);
        counter = position;
        size--; // должно остаться на один меньше, всё норм
        notifyItemRangeChanged(position, size); //грузим начиная с этой позиции
    }

    private void removeRow(int id, String term, String description) {
        SQLiteOpenHelper notesHelper =
                new NotesDatabaseHelper(context);
        try {
            Integer i = new Integer(id);
            SQLiteDatabase db = notesHelper.getWritableDatabase();
            db.delete("NOTES", "_id = ? AND TERM = ? AND DESCRIPTION = ?", new String[] {i.toString(), term, description});
            db.close();
        } catch (SQLiteException e) {
            Toast.makeText(context, "Запрос на запись не обработался" + e.toString(),
                    Toast.LENGTH_SHORT).show();
            Log.d("testim", e.toString());
        } catch (NullPointerException e) {
        }
    }

    private void findRow() {
        String dbName = "NOTES";
        int count = counter;
        String decsDB = "";
        String termDB = "";
        int iddb = 0;
        SQLiteOpenHelper notesHelper =
                new NotesDatabaseHelper(context);
        SQLiteDatabase db = notesHelper.getWritableDatabase();
        try {
            Cursor cursor = db.query(dbName, // Создаём курсор
                    new String[]{dbID, dbTermRow, dbDescriptionRow},
                    null, null, null, null, null);
            if (cursor.moveToFirst()) {
                for (int i = 0; i < count; i++)
                    cursor.moveToNext();
                iddb = cursor.getInt(0);
                termDB = cursor.getString(1);
                decsDB = cursor.getString(2);
            }
            cursor.close();
        } catch (SQLiteException e) {
            Toast.makeText(context, "Счётчик не хочет работать",
                    Toast.LENGTH_SHORT).show();
        }
        removeRow(iddb, termDB, decsDB);
    }

    // Длина массива равна количеству элементов данных в RecylcerView
    @Override
    public int getItemCount() {
        return size;
    }
}
