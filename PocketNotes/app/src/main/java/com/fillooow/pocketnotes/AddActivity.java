package com.fillooow.pocketnotes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
    private String key = "";

    private EditText term;
    private EditText description;
    private String textTerm = "";
    private String textDescription = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        term = (EditText) findViewById(R.id.editTerm);
        description = (EditText) findViewById(R.id.editDescription);

        key = getIntent().getExtras().getString("key");
        if (key.equals("new")) {

        }

    }

    public void onSaveClick(View view) {

        textTerm = term.getText().toString();
        textDescription = description.getText().toString();

        if (textTerm.equals("") && textDescription.equals(""))
            Toast.makeText(getApplicationContext(), "Заметка пустая", Toast.LENGTH_SHORT).show();
        else if (textTerm.equals(""))
            Toast.makeText(getApplicationContext(), "Введите термин", Toast.LENGTH_SHORT).show();
        else if (description.equals(""))
            Toast.makeText(getApplicationContext(), "Введите описание", Toast.LENGTH_SHORT).show();
        else {
            new UpdateNotesDatabase().execute(textTerm, textDescription);
            Intent intent = new Intent(AddActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }


    private class UpdateNotesDatabase extends AsyncTask<String, Void, Void> {
        private String termUpdate = "";
        private String descriptionUpdate = "";

        @Override
        protected Void doInBackground(String... params) {
            termUpdate = params[0];
            descriptionUpdate = params[1];
            ContentValues contentValues = new ContentValues();
            contentValues.put("TERM", termUpdate);
            contentValues.put("DESCRIPTION", descriptionUpdate);
            SQLiteOpenHelper notesHelper =
                    new NotesDatabaseHelper(getApplicationContext());
            try {
                SQLiteDatabase db = notesHelper.getWritableDatabase();
                db.insert("NOTES", null, contentValues);
                db.close();
            } catch (SQLiteException e) {
                Toast.makeText(getApplicationContext(), "Запрос на запись не обработался",
                        Toast.LENGTH_SHORT).show();
            } catch (NullPointerException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

}
