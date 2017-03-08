package com.hfad.beeradviser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class FindBeerActivity extends AppCompatActivity {

    //создаём объект для связи классов
    private BeerExpert expert = new BeerExpert();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_beer);
    }

    //нажатие на кнопку Find Beer!
    public void onClickFindBeer(View view){
        //findViewById возвращает тип View, а Spinner и TextView наследуются от него

        //получаем ссылку на TextView
        TextView brands = (TextView) findViewById(R.id.brands);

        //получаем ссылку на Spinner
        Spinner color = (Spinner) findViewById(R.id.color);

        //получаем вариант, выбранный в Spinner и преобразуем его в String

        //color.getSelectedItem() возвращает обобщённый объект Java, могут быть изображения
        //и не только, но мы знаем, что это будет текст, так что заранее преобразуем
        //Object в Stirng
        String beerType = String.valueOf(color.getSelectedItem());

        //получаем названия марок пива исходя из выбранного строкой выше сорта
        List<String> brandList = expert.getBrands(beerType);
        StringBuilder brandFormatted = new StringBuilder();
        for(String brand : brandList) {
            brandFormatted.append(brand).append('\n'); //каждый сорт с новой строки
        }

        //выводим сорт
        brands.setText(brandFormatted);
    }
}
