package com.hfad.stopwatch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class LoopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loop);

        Intent intent = getIntent(); //получаем интент
        ArrayList<String> loopTextMes = intent.getStringArrayListExtra("loopStrings"); //вытаскиваем строки
        TextView loopMessageView = (TextView) findViewById(R.id.loopStrings); //получаем ссылку на текстовое поле
        for(String al: loopTextMes) //выводим значения
            loopMessageView.append(al + "\n");
    }

    //Шеф, всё пропало. Походу, кнопка факапает потоки, ибо, через физическую кнопку "Назад" всё норм,
    //а через эту Back активность запускается с чистого листа
    //Было бы неплохо дизайн поделать, мб, ограниченное количество loop-ов
    public void onClickBack(View view) {
        Intent backIntent = new Intent(this, StopWatchActivity.class);
        startActivity(backIntent);
    }
}
