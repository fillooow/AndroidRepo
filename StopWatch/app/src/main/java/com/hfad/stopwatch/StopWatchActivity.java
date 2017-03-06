package com.hfad.stopwatch;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class StopWatchActivity extends AppCompatActivity {
    private int seconds = 0; //количество секунд
    private boolean running; //проверка работы таймера, false по умолчанию
    private boolean wasRunning; //хранит информациию
    private ArrayList<String> loopArrayList = new ArrayList<>(); //сюда передаём круги

    @Override
    //по умолчанию, если активность создаётся с нуля, то параметр типа Bundle содержит null
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);
        //но если он не равен null, те, уже был создан, то вытаскиваем переменные
        if (savedInstanceState != null) {
            //bundle.get*("name"); bundle - имя объекта Bundle,* - тип значения
            //name - имя получаемых данных
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
    }

    //сохраняем данные, вызывается перед уничтожением активности
    public void onSaveInstanceState(Bundle savedInstanceState) {
        //bundle.put*("name", value); всё то же самое, но добавляется value - значение
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    public void onClickStart(View view) {
        running = true;
    }

    public void onClickStop(View view) {
        running = false;
    }

    public void onClickReset(View view) {
        running = false;
        seconds = 0;
    }

    public void onClickShowLoop(View view) {
        ArrayList<String> loopMes = loopArrayList;
        //создаём интент
        Intent intent = new Intent(this, LoopActivity.class);
        intent.putExtra("loopStrings", loopMes);
        startActivity(intent);
    }

    public void onClickLoop(View view) {
        //вытаскиваем текстовое поле с цифрами
        TextView loopView = (TextView) findViewById(R.id.time_view);
        //вытаскиваем текст
        String loopText = loopView.getText().toString();
        loopArrayList.add(loopText);
    }

    protected void onStop() {
        super.onStop(); //super обязательно должен быть вызван, иначе генерируется исключение
        wasRunning = running; //сохраняем информацию о работе секундомера на
        //момент запуска метода onStop()
        running = false;
    }

    //если секундомер работал,его работа возобновится
    protected void onStart() {
        super.onStart();
        if (wasRunning)
            running = true;
    }

    private void runTimer() {
        final TextView timeView = (TextView) findViewById(R.id.time_view);
        final Handler handler = new Handler(); //новый объект типа Handler
        handler.post(new Runnable() { //post() обеспечивает мгновенное выполнение кода
            @Override
            public void run() {
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;
                String time = String.format("%d:%02d:%02d", hours, minutes, secs);

                timeView.setText(time);
                if(running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000); //postDelayed может принимать long параметр -
                //задержку в миллисекундах
            }
        });

    }
}
