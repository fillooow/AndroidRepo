package com.hfad.workout;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hfad.DetailActivity;

public class MainActivity extends AppCompatActivity implements WorkoutListFragment.WorkoutListListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void itemClicked(long id) {
        //Получем ссылку на фрейм, содержащий WorkoutDetailFragment, он существует лишь если приложение
        //выполняется на большом экране
        View fragmentContainer = findViewById(R.id.fragment_container);
        if (fragmentContainer == null) {
            //Код для отображения подробной информации
            //Этот метод определяется в слушателе
            WorkoutDetailFragment details = new WorkoutDetailFragment();
            //Ругается на импорт android.app.v4.FT;
            FragmentTransaction ft = getFragmentManager().beginTransaction(); //начало транзакции фрагмента
            details.setWorkout(id);
            ft.replace(R.id.fragment_container, details); //заменяем фрагмент
            ft.addToBackStack(null); //и добавляем его в стек возврата
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE); //анимация растворения и проявления фрагментов
            ft.commit(); //закрепление транзакции
        } else {
            //если фрейм отсутствует, запускаем DetailActivity
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_WORKOUT_ID, (int) id);
            startActivity(intent);
        }
    }
}
