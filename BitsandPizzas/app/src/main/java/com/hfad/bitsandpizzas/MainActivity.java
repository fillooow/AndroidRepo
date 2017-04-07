package com.hfad.bitsandpizzas;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ShareActionProvider;

// Для реализации транзакций и для работы с support импортом нужно расширять FragmentActivity
// и вызывать getSupportFragmentManager, вместо getFragmentManager
public class MainActivity extends FragmentActivity{

    private String[] titles;
    private ListView drawerList;

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        Fragment fragment;

        switch (position) {
            case 1:
                fragment = new PizzaFragment(); // Следим за тем, чтобы везде была саппорт библиотека
                break;
            case 2:
                fragment = new PastaFragment();
                break;
            case 3:
                fragment = new StoresFragment();
                break;
            default:
                fragment = new TopFragment();
        }

        // Вот из-за этого FragmentActivity, а не Activity
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment); // Заменяем фрагмент
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE); // Анимация (затухания?)
        ft.commit();
        setActionBarTitle(position); // Меняем заголовок
        // Закрываем выдвижную панель
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(drawerList);
    }

    private void setActionBarTitle(int position) {
        String title;
        if (position == 0) {
            title = getResources().getString(R.string.app_name);
        } else {
            title = titles[position];
        }
        getActionBar().setTitle(title);
    }

    // Переменная для вывода провайдера информации
    private ShareActionProvider shareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titles = getResources().getStringArray(R.array.titles); // Получаем массив заголовков
        drawerList = (ListView) findViewById(R.id.drawer);
        drawerList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_activated_1, titles));
        // Режим ..._activated_ ... подсвечивает выбранный вариант
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        // Добавляем экземпляр OnItemClickListener к списковому представлению выдвижной панели
    }

    @Override
    // Реализация этого метода добавляет в панель действий все
    // элементы действий, содержащиеся в файле ресурсов меню
    public boolean onCreateOptionsMenu(Menu menu) {
        // Берём ресурсы и пихаем их в меню
        getMenuInflater().inflate(R.menu.menu_main, menu); // menu - объект Menu, представляющий
        // панель действий
        MenuItem menuItem = menu.findItem(R.id.action_share);
        // Получаем ссылку на провайдера действия передачи информации
        // и присвоить её приватно переменной
        shareActionProvider = (ShareActionProvider) menuItem.getActionProvider();
        setIntent("This is example text");
        return super.onCreateOptionsMenu(menu);
    }

    // Метод созлаёт интент и передаёт его провайдеру действия передачи информации
    public void setIntent(String text) {
        Intent intent = new Intent(Intent.ACTION_SEND); // Создаём интент передачи
        intent.setType("text/plain"); // Тип передаваемой информации
        intent.putExtra(Intent.EXTRA_TEXT, text); // Передаём этот текст нашим интентом
        shareActionProvider.setShareIntent(intent); // Вот этим методом передаём провайдеру
    }

    @Override
    // Объект MenuItem представляет элемент на панели действий, на котором сделан щелчок
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_create_order:
                Intent intent = new Intent(this, OrderActivity.class);
                startActivity(intent);
                return true; // Щелчок отработан
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
