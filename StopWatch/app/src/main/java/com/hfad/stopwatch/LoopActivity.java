package com.hfad.stopwatch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class LoopActivity extends AppCompatActivity {
    ArrayList<String> loopWasLooped;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loop);

        Intent intent = getIntent(); //получаем интент
        loopWasLooped = intent.getStringArrayListExtra("loopStrings"); //вытаскиваем строки
        TextView loopMessageView = (TextView) findViewById(R.id.loopStrings); //получаем ссылку на текстовое поле
        for(String al: loopWasLooped) //выводим значения
            loopMessageView.append(al + "\n");
    }

    //Шеф, всё пропало. Походу, кнопка факапает потоки, ибо, через физическую кнопку "Назад" всё норм,
    //а через эту Back активность запускается с чистого листа  //вроде пофикшено, ща будем возиться с передачей лупов-залупов
    //Было бы неплохо дизайн поделать, мб, ограниченное количество loop-ов
    public void onClickBack(View view) {
        Intent backIntent = new Intent(this, StopWatchActivity.class);
        backIntent.putExtra("wasLooped", loopWasLooped);
        finish();
    }
}
