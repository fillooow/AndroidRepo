package com.hfad.bitsandpizzas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;

public class MainActivity extends Activity {

    // Переменная для вывода провайдера информации
    private ShareActionProvider shareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
