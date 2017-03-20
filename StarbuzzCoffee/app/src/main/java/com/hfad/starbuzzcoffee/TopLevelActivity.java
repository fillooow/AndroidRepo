package com.hfad.starbuzzcoffee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.view.*;
import android.widget.*;

public class TopLevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);

        //OnItemClickListener - вложенный класс по отношению к классу AdapterView
        //ListView - субкласс AdapterView

        //слушатель onItemClickListener отслеживает щелчки на вариантах списка
        //onItemClick определяет действия для щелчка
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            //1. Списковое представление, 2. Представление, 3. Позиция в списке, 4. Идентификатор
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(TopLevelActivity.this, DrinkCategoryActivity.class);
                    startActivity(intent);
                }
            }
        };
        ListView listView = (ListView) findViewById(R.id.list_options);
        //Если не привяжем вручную, щелчки не будут работать
        listView.setOnItemClickListener(itemClickListener);
    }
}
