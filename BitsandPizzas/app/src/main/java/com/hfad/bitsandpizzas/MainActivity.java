package com.hfad.bitsandpizzas;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ShareActionProvider;

// Для реализации транзакций и для работы с support импортом нужно расширять FragmentActivity
// и вызывать getSupportFragmentManager, вместо getFragmentManager
public class MainActivity extends Activity{

    private ShareActionProvider shareActionProvider; // Переменная для вывода провайдера информации (кнопка Share)
    private DrawerLayout drawerLayout; // Выдвижная панель
    private String[] titles; // Наполнитель
    private ListView drawerList; // Список из панели
    private ActionBarDrawerToggle drawerToggle; // Слушатель для кнопочки
    private int currentPosition = 0;



    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        // Вызывается после щелчка на одном из вариантов списка
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // Этот метод прописан ручками чуть ниже
            selectItem(position); // Он ставит фрагмент и определяет его поведение
        }
    }



    // Сейвим currentPosition для отображения заголовка
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt("position", currentPosition);
    }



    // Ручками забиваем, какой фрагмент подгружать и как именно они будут ставиться
    private void selectItem(int position) {
        currentPosition = position;
        Fragment fragment;

        switch (position) {
            case 1:
                fragment = new PizzaFragment(); // Следим за тем, чтобы везде была саппорт библиотека
                break; // Ну или не саппорт, главное, чтобы всюду совпадала одна из библиотек
            case 2:
                fragment = new PastaFragment();
                break;
            case 3:
                fragment = new StoresFragment();
                break;
            default:
                fragment = new TopFragment();
        }

        // Вот из-за этого FragmentActivity, а не Activity, речь про getSupportFragmentManager()
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment, "visible_fragment"); // Заменяем фрагмент
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE); // Анимация (затухания?)
        ft.commit();

        setActionBarTitle(position); // Меняем заголовок

        // Закрываем выдвижную панель
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(drawerList); // Вот эта штука закрывает
    }



    // Ручками прибиваем заголовок
    private void setActionBarTitle(int position) {
        String title;
        if (position == 0) {
            title = getResources().getString(R.string.app_name);
        } else {
            title = titles[position]; // Тут вытаскиваем его из массива
        }
        getActionBar().setTitle(title); // Тут ставим заголовок
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titles = getResources().getStringArray(R.array.titles); // Получаем массив заголовков
        // Инициализируем ListView
        drawerList = (ListView) findViewById(R.id.drawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList.setAdapter(new ArrayAdapter<String>(this,
                // Режим ..._activated_ ... подсвечивает выбранный вариант
                android.R.layout.simple_list_item_activated_1, titles));
        // Добавляем экземпляр OnItemClickListener к списковому представлению выдвижной панели
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        // Выводим верный текст заголовка после пересоздания активности
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("position");
            setActionBarTitle(currentPosition); // Метод сами прописали, ручками прибиваем заголовок
        } else {
            selectItem(0); // выводим TopFragment
        }
        // Создаём ActionBarDrawerToggle
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.open_drawer, R.string.close_drawer) {

            // Вызывается при переходе выдвижной панели в полностью закрытое состояние
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }

            // Вызывается при переходе выдвижной панели в полностью открытое состояние
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
        getActionBar().setDisplayHomeAsUpEnabled(true); // Подрубаем кнопку вверх
        getActionBar().setHomeButtonEnabled(true); // для открытия панели

        // Вызывается при изменении стека возврата
        getFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    @Override
                    public void onBackStackChanged() {
                        FragmentManager fragMan = getFragmentManager();
                        Fragment fragment = fragMan.findFragmentByTag("visible_fragment");
                        // Проверяем экземпляром какого класса является фрагмент с активность
                        // и присваиваем currentPosition соответствующее значение
                        if (fragment instanceof TopFragment)
                            currentPosition = 0;
                        if (fragment instanceof PizzaFragment)
                            currentPosition = 1;
                        if (fragment instanceof PastaFragment)
                            currentPosition = 2;
                        if (fragment instanceof StoresFragment)
                            currentPosition = 3;
                        setActionBarTitle(currentPosition);
                        // Выводим текст на панели действий и выделяем верный вариант в списке
                        // на выдвижной панели
                        drawerList.setItemChecked(currentPosition, true);
                    }
                }
        );
    }



    // Вызывается при каждом вызове invalidateOptionsMenu()
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        // Задаём видимость действия Share при открытии и закрытии панели
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        // Есди выджвижная панель открыта, скрываем элементы, связанные с контентом
        menu.findItem(R.id.action_share).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }



    @Override
    // Реализация этого метода добавляет в панель действий все
    // элементы действий, содержащиеся в файле ресурсов меню
    public boolean onCreateOptionsMenu(Menu menu) {
        // Берём ресурсы из файла меню и пихаем их на панель действий, если она есть
        getMenuInflater().inflate(R.menu.menu_main, menu); // menu - объект Menu, представляющий
        // панель действий
        MenuItem menuItem = menu.findItem(R.id.action_share);
        // Получаем ссылку на провайдера действия передачи информации
        // и присвоить её приватно переменной
        shareActionProvider = (ShareActionProvider) menuItem.getActionProvider();
        setIntent("This is example text"); // Этот метод прописан ручками
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
        // Для реагирования ActionBarDrawerToggle на щелчки добавляем этот if
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true; // Вернёт true, только если сделан щелчок на панели действий
            // в противном случае выполняется код ниже
        }
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



    // Метод для синхронизации состояния с панелью, тип, кнопка меняется в зависимости от состояни панели
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState(); // Синхронизируем состояние ActionBarDrawerToggle
        // после onRestoreInstanceState
    }



    // Сведения о любых изменениях передаются в ActionBarDrawerToggle
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
}