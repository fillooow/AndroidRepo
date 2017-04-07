package com.hfad.bitsandpizzas;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

public class OrderActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        // Для активности обязательно необходимо задавать родителя
        ActionBar actionBar = getActionBar(); // Получаем ссылку на панель действий
        actionBar.setDisplayHomeAsUpEnabled(true); // Подрубаем кнопенцию
    }
}
