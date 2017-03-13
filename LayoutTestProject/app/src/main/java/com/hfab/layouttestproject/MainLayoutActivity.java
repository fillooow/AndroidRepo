package com.hfab.layouttestproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainLayoutActivity extends AppCompatActivity {
    private Toast toaster;
    private CharSequence text = ""; //это для уведомлений
    private int duration = Toast.LENGTH_SHORT;
    private int intTemperature = 70;
    private ArrayList<String> yourTea = new ArrayList<>(); //а это массив добавок
    private String add; //тут храним добавки

    //кнопки у нас определены глобально, но ссылки мы получаем в onCreate()
    private Button coldButt;
    private Button hotButt;


    //отображаем температуру
    private void showTemperature() {
        TextView textView = (TextView) findViewById(R.id.degrees);
        textView.setText(String.valueOf(intTemperature));
    }

    //проверяем, нужно ли нам выключать кнопки
    public void needToDisable (int temp) {
        if (temp == 35) {
            text = "Минимальная температура 35 градусов";
            getToaster();
            coldButt.setEnabled(false);
        } else if (temp == 95){
            text = "Максимальная температура 95 градусов";
            getToaster();
            hotButt.setEnabled(false);

        } else {
            coldButt.setEnabled(true);
            hotButt.setEnabled(true);
        }
    }

    /*//сюда помещаем входные данные для чая, который будем готовить, это если флажок поставлен
    private void setTea(String additive) { yourTea.add(additive);    }

    //удаляем данные для чая, если снимается флажок
     public void removeTea(String additiveRem) {  yourTea.remove(yourTea.indexOf(additiveRem));   }*/

    public void teaMaker(String additive, boolean check) {
        if (check)
            yourTea.add(additive);
        else
            yourTea.remove(yourTea.indexOf(additive));
    }

    //выводит уведомление
    private void getToaster () {
        toaster = Toast.makeText(this, text, duration);
        toaster.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        showTemperature(); //сразу заполняем поле при запуске

        //не очень хорошо понимаю. почему не выходит определять их выше, но
        //Денис чёт говорил про то, что мы вызываем методы снаружи всех
        //методов, а так нельзя, потому определяем ссылки на кнопки тут
        coldButt = (Button) findViewById(R.id.coldButton);
        hotButt = (Button) findViewById(R.id.hotButton);
    }

    public void onClickColdButton(View view) {
        if (intTemperature < 40) { //минимальная температура 35, 40 не меньше 40,
            return; //выход из метода, код ниже не компилируется, если
            //мы достигли 35 градусов
        }
        intTemperature -= 5;
        showTemperature();
        needToDisable(intTemperature);
    }

    public void onClickHotButton(View view) {
        if (intTemperature > 90) { //тут та же ситуация, максимум 95, а не 90
            return; //выход из метода, код ниже не компилируется, если
            //мы жостигли 95 градусов
        }
        intTemperature += 5;
        showTemperature();
        needToDisable(intTemperature);
    }

    public void onClickMakeTea(View view) {
        Intent teaIntent = new Intent(this, YourTeaActivity.class);
        teaIntent.putExtra("readyTea", yourTea);
        startActivity(teaIntent);

    }

    public void onCheckboxClicked(View view) {
        //Проверка установки флажка, на которй щёлкал пользователь
        boolean checked = ((CheckBox) view).isChecked();

        //определяем флажок, на который кликнул пользователь
        switch(view.getId()) {
            case R.id.checkbox_milk:
                add = "milk";
                teaMaker(add, checked);
                break;
            case R.id.checkbox_sugar:
                add = "sugar";
                teaMaker(add, checked);
                break;
            case R.id.checkbox_lemon:
                add = "lemon";
                teaMaker(add, checked);
                break;
            case R.id.checkbox_ice:
                add = "ice";
                if (checked) {
                    intTemperature = 35;
                    showTemperature();
                    yourTea.add(add);
                    hotButt.setEnabled(false);
                    coldButt.setEnabled(false);
                } else {
                    yourTea.remove(yourTea.indexOf(add));
                    hotButt.setEnabled(true);
                    coldButt.setEnabled(true);
                }
                break;

        }

    }
}
