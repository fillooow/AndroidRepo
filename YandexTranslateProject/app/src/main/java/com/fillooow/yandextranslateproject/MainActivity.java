package com.fillooow.yandextranslateproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    //private static final String TAG = MainActivity.class.getSimpleName();
    private BottomNavigationView bottomNavigation;
    private Fragment fragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavigation = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigation.inflateMenu(R.menu.bottom_menu);
        fragmentManager = getSupportFragmentManager(); // У нас саппорт библиотека,
        // поэтому используется getSupportFragmentManager()

        // Не особо понял эту уличную магию, но нам и и установку фрагмента при запуске
        // нужно обрабатывать транзакцией, видимо, невнимательно прочитал, раз думал, что транзакции
        // нужны лишь для смены фрагментов, а не для любой работы с ними
        final FragmentTransaction trans = fragmentManager.beginTransaction().replace(R.id.main_container, new TranslateFragment());
        trans.commit(); // Подтверждение транзакции на установку стартового фрагмента

        // Вся нижняя навигация обрабатывается тута через кейсы через слушателя
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            //#ffcc00 - цвет яндекса
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.action_translate:
                        fragment = new TranslateFragment();
                        break;
                    case R.id.action_bookmark:
                        fragment = new BookmarksFragment();
                        break;
                    case R.id.action_settings:
                        fragment = new SettingsFragment();
                        break;
                    case R.id.action_history:
                        fragment = new HistoryFragment();
                }
                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container, fragment).commit();
                return true;
            }
        });
    }
}