package com.hfab.layouttestproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainLayoutActivity extends AppCompatActivity {
    private Toast toaster;
    private CharSequence text = "";
    private int duration = Toast.LENGTH_SHORT;
    private int intTemperature = 70;

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
            //мы жостигли 35 градусов
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

    public void onCheckboxClicked(View view) {
        //Проверка установки флажка, на которй щёлкал пользователь
        boolean checked = ((CheckBox) view).isChecked();

        //определяем флажок, на который кликнул пользователь
        switch(view.getId()) {
            case R.id.checkbox_lemon:
                if (checked) {
                    text = "lemon";
                    toaster = Toast.makeText(this, "you choose  " + text, duration);
                    toaster.show();
                }
                else {
                    text = "lemon";
                    toaster = Toast.makeText(this, "you choose " + text, duration);
                    toaster.show();
                }
                break;
            case R.id.checkbox_milk:
                if (checked) {
                    text = "milk";
                    toaster = Toast.makeText(this, "you choose " + text, duration);
                    toaster.show();
                }
                //else {                }
                break;
            case R.id.checkbox_sugar:
                if (checked) {
                    text = "sugar";
                    toaster = Toast.makeText(this, "you choose " + text, duration);
                    toaster.show();
                }
                //else {                }
                break;
            case R.id.checkbox_ice:
                if (checked) {
                    intTemperature = 35;
                    showTemperature();

                    hotButt.setEnabled(false);
                    coldButt.setEnabled(false);
                } else {
                    hotButt.setEnabled(true);
                    coldButt.setEnabled(true);
                }
                break;

        }

    }
}
