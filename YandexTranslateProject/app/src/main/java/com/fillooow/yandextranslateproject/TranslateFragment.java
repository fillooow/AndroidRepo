package com.fillooow.yandextranslateproject;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;


/**
 * Фрагмент, в котором происходит работа с переводом
 * Вложенный класс TranslateJsonTask работает с AsyncTask и обращается
 * к классу TranslateClass, в котором идёт работа с объектом Gson
 */
public class TranslateFragment extends Fragment {
    protected String textToTranslate; // Текст для перевода, выцепляем из editTextField
    protected String wasText = ""; // Текст,отправленный на перевод, дабы запросы не дублировались
    protected String translatedText = ""; //Переведённый текст
    protected String langTranslate = ""; // Направление перевода
    protected String wasLangTranslate = ""; // Направление перевода
    protected int timeCounter = 0;

    protected EditText editTextField; // Отсюда цепляем текст, который будем переводить
    protected TextView textViewField; // Сюда загоняем текст, который перевели
    protected Switch testSwitch; // Тестовая кнопка переключения языка
    protected View view;

    TranslateJsonTask tjt;

    public TranslateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_translate, container, false);
    }

    // TODO: нужно будет закрыть поток в onDestroy()
    @Override
    public void onStart() {
        super.onStart();

        view = getView();
        editTextField = (EditText) view.findViewById(R.id.inputedText);
        textViewField = (TextView) view.findViewById(R.id.testText);
        testSwitch = (Switch) view.findViewById(R.id.translateSwitch);



        testSwitch.setChecked(false);

        // Слушатель для смены языка
        testSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (testSwitch.isChecked())
                    langTranslate = "ru-en";
                else
                    langTranslate = "en-ru";
            }
        });

        onTranslate(); // Вызываем на старте наш метод для перевода
    }


    // Метод, в котором происходит перевод текста
    public void onTranslate() {
        // Очень странный баг, всё факапается, если не прибить вручную, хоть я и сделал это
        // в слушателе
        if (testSwitch.isChecked())
            langTranslate = "ru-en";
        else
            langTranslate = "en-ru";
        final Handler handler = new Handler(); // Реализуем всё в отдельном потоке
        handler.post(new Runnable() {
            @Override
            public void run() {
                textToTranslate = editTextField.getText().toString(); // Получаем введённый текст

                // Если пустое поле, всё прибиваем ручками
                if (textToTranslate.equals("")) {
                    wasText = textToTranslate;
                    wasLangTranslate = langTranslate;
                    textViewField.setText("");
                    handler.postDelayed(this, 500);
                }

                // Если меняем язык или введённый текст, запускаем перевод
                if (!(langTranslate.equals(wasLangTranslate)) | !(wasText.equals(textToTranslate))) {
                    tjt = new TranslateJsonTask();
                    tjt.execute(textToTranslate, langTranslate);
                    // Получаем переведённый текст
                    try {
                        translatedText = tjt.get();
                    } catch (InterruptedException e) {
                        Toast.makeText(getActivity(), "InterruptedException",
                                Toast.LENGTH_SHORT).show();
                    } catch (ExecutionException e) {
                        Toast.makeText(getActivity(), "ExecutionException",
                                Toast.LENGTH_SHORT).show();
                    }
                    timeCounter = 0;
                }
                timeCounter++;
                if (timeCounter == 6) {
                    new UpdateYandexDatabase().execute(textToTranslate, translatedText);
                }
                wasText = textToTranslate;
                wasLangTranslate = langTranslate;
                handler.postDelayed(this, 500); // Рекурсируем метод, с помощью такой реализации
                // мы постоянно обновляем View
            }
        });
    }





    private class TranslateJsonTask extends AsyncTask <String, Void, String> {
        private String text;
        private String language;
        Gson gson = new Gson();
        TranslateClass translate = new TranslateClass();

        @Override
        protected String doInBackground(String... params) {
            text = params[0]; // Получаем введённый текст
            translate.setTranslateText(text);

            language = params[1];
            translate.setLang(language);
            try {
                URLConnection connection = new URL(translate.getYandexURL()
                        + "?key="
                        + translate.getKey()
                        + "&text="
                        + URLEncoder.encode(translate.getTranslateText(), "UTF-8")
                        + "&lang="
                        + translate.getLang()).openConnection();
                // Открываем поток вывода с сайта в переменную in
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                // Создаём новый объект с распаршенным Json-ом на основе in.readLine()
                // те, выведенного в in потока
                TranslateClass translated = gson.fromJson(in.readLine(), TranslateClass.class);
                in.close(); // Закрываем поток
                text = translated.getResponse(); // Получаем ответ сервера из Json объекта
            } catch (IOException e) { // В случае ошибки, выводим тост
                Toast.makeText(getActivity(), "Запрос на перевод не обработался",
                        Toast.LENGTH_SHORT).show();
            }
            return text;
        }

        @Override
        protected void onPostExecute(String string) {
            super.onPostExecute(string);
            textViewField.setText(string); // Ставим переведённый текст
        }
    }


    private class UpdateYandexDatabase extends AsyncTask <String, Void, Void> {
        private String original = "";
        private String translated = "";

        @Override
        protected Void doInBackground(String... params) {
            original = params[0];
            translated = params[1];
            ContentValues contentValues = new ContentValues();
            contentValues.put("ORIGINALTEXT", original);
            contentValues.put("DESCRIPTIONTEXT", translated);
            SQLiteOpenHelper yandexHelper =
                    new YandexDatabaseHelper(getActivity());
            try {
                SQLiteDatabase db = yandexHelper.getWritableDatabase();
                db.insert("YANDEXTRANSLATE", null, contentValues);
                db.close();
            } catch (SQLiteException e) {
                Toast.makeText(getActivity(), "Запрос на запись в историю не обработался",
                        Toast.LENGTH_SHORT).show();
            } catch (NullPointerException e) {
//                Toast.makeText(getActivity(), "NP",
                      //  Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
/*
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("textToTranslate", textToTranslate);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState == null) {
            textToTranslate = "";
       } else {
            textToTranslate = savedInstanceState.getString("textToTranslate", "meme");
        }
        //timeCounter = savedInstanceState.getInt("timer", 0);
    }
    */
}

