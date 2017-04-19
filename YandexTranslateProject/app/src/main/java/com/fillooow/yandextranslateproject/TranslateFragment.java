package com.fillooow.yandextranslateproject;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class TranslateFragment extends Fragment {
    protected String textToTranslate; // Текст для перевода, выцепляем из textField
    protected String translatedText= ""; // Переведённый текст
    protected EditText testField; // Отсюда цепляем текст, который будем переводить
    protected TextView testText;

    public TranslateFragment() {
        // Required empty public constructor
    }

    // Метод, в котором происходит перевод текста
    public void onTranslate() {
        View view = getView();
        testField = (EditText) view.findViewById(R.id.inputedText);
        testText = (TextView) view.findViewById(R.id.testText);
        final Handler handler = new Handler(); // Реализуем всё в отдельном потоке
        handler.post(new Runnable() {
            @Override
            public void run() {
                textToTranslate = testField.getText().toString(); // Получаем введённый текст
                //testText.setText(textToTranslate); // Вот сюда мы должны запихать перевод
                //Translate translateObj = new Translate();
                //translateObj.setText(textToTranslate);
                //TranslateJson tj = new TranslateJson();
                //tj.doInBackground();

                new TranslateJsonTask().execute(textToTranslate);

                //testText.setText(textToTranslate);
                handler.post(this); // Рекурсируем метод, с помощью такой реализации
                // мы постоянно обновляем View
            }
        });
    }

    private class TranslateJsonTask extends AsyncTask <String, Void, String> {
        private String text;
        Gson gson = new Gson();
        TranslateClass translate = new TranslateClass();

        @Override
        protected void onPreExecute() {
            translate.setLang("en-ru");
        }

        @Override
        protected String doInBackground(String... params) {
            text = params[0];
            /*translate.setTranslateText(text);
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
                text = translated.getResponse();
            } catch (IOException e) {
                // e.printStackTrace();
                // Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
                // toast.show();
                Toast.makeText(getActivity(), "Чёт факапнулось", Toast.LENGTH_SHORT).show();
            }*/
            return text;
        }

        @Override
        protected void onPostExecute(String string) {
            super.onPostExecute(string);
            testText.setText(string);
        }
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
        onTranslate();
    }
}
