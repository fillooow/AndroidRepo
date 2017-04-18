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

        @Override
        protected String doInBackground(String... params) {
            text = params[0] + " катя не спит";
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

    @Override
    public void onStart() {
        super.onStart();
        onTranslate();
    }
}
