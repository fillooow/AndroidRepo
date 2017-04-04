package com.fillooow.yandextranslateproject;

import android.os.AsyncTask;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class TranslateJson extends AsyncTask<Void, Void, Void>{

    @Override
    protected Void doInBackground(Void... params) {
        Translate translate = new Translate();
        translate.setLang("en-ru");
        //translate.setText("Denis loves memes");
        Gson gson = new Gson(); // Создаём объект Json

        // Пилим подключение к Яндексу и его переводчику, мы собираем ссылку вручную, не забывая про перекодировани
        URLConnection connection = null;
        try {
            connection = new URL(translate.getYandexURL() + "?key=" + translate.getKey() + "&text=" + URLEncoder.encode(translate.getText(), "UTF-8") + "&lang=" + translate.getLang()).openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader in = null; // Открываем поток вывода с сайта в переменную in
        try {
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Translate translated = null; // Создаём новый объект с распаршенным Json-ом на основе in.reaLine() - те, выведенного
        try {
            translated = gson.fromJson(in.readLine(), Translate.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // в in потока
        try {
            in.close(); // Закрываем поток
        } catch (IOException e) {
            e.printStackTrace();
        }
        //translated.showMessageInfo(); // Выводим информацию
        //return translated.getText();
        return null;
    }

    //public static void main(String args[]) throws IOException {
    /*public String TranslateMessage() throws IOException{
        Translate translate = new Translate();
        translate.setLang("en-ru");
        //translate.setText("Denis loves memes");
        Gson gson = new Gson(); // Создаём объект Json

        // Пилим подключение к Яндексу и его переводчику, мы собираем ссылку вручную, не забывая про перекодировани
        URLConnection connection = new URL(translate.getYandexURL() + "?key=" + translate.getKey() + "&text=" + URLEncoder.encode(translate.getText(), "UTF-8") + "&lang=" + translate.getLang()).openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream())); // Открываем поток вывода с сайта в переменную in
        Translate translated = gson.fromJson(in.readLine(), Translate.class); // Создаём новый объект с распаршенным Json-ом на основе in.reaLine() - те, выведенного
        // в in потока
        in.close(); // Закрываем поток
        //translated.showMessageInfo(); // Выводим информацию
        return translated.getText();
    }*/
}
