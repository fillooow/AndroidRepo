package com.fillooow.yandextranslateproject;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class TranslateFragment extends Fragment {
    protected String textToTranslate; // Текст для перевода, выцепляем из textField
    protected EditText textField; // Отсюда цепляем текст, который будем переводить
    protected TextView testText;
    protected boolean running = true; // Резерв под синхронность перевода

    public TranslateFragment() {
        // Required empty public constructor
    }

    // Метод, в котором происходит перевод текста
    public void onTranslate() {
        View view = getView();
        textField = (EditText) view.findViewById(R.id.inputedText);
        testText = (TextView) view.findViewById(R.id.testText);
        final Handler handler = new Handler(); // Реализуем всё в отдельном потоке
        handler.post(new Runnable() {
            @Override
            public void run() {
                textToTranslate = textField.getText().toString();
                testText.setText(textToTranslate);
                handler.post(this); // Рекурсируем метод, с помощью такой реализации
                // мы постоянно обновляем View
            }
        });
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
