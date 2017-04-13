package com.hfad.starbuzzcoffee;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DrinkActivity extends AppCompatActivity {
    public static final String EXTRA_DRINKNO = "drinkNo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        int drinkNo = (Integer)getIntent().getExtras().get(EXTRA_DRINKNO);
        Drink drink = Drink.drinks[drinkNo];

        ImageView photo = (ImageView) findViewById(R.id.photo);
        // Источник данных графического поля назначается вызовом sIR()
        // либо в макете android:src
        photo.setImageResource(drink.getImageResourceId());
        // Это для повышения уровня доступности приложения
        // Либо можно в макете прописать android:contentDescription
        photo.setContentDescription(drink.getName());

        // Название напитка
        TextView name = (TextView) findViewById(R.id.name);
        name.setText(drink.getName());

        // Описание напитка
        TextView description = (TextView) findViewById(R.id.description);
        description.setText(drink.getDescription());
    }
}
