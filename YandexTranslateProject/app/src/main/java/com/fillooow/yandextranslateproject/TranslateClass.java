package com.fillooow.yandextranslateproject;

/**
 * Храним в этом классе сеттеры и геттеры, URL и его составляющие
 */

public class TranslateClass {
    private String yandexURL = "https://translate.yandex.net/api/v1.5/tr.json/translate"; // Ссылка на яндекс
    private String key = "trnsl.1.1.20170330T085156Z.928b6e6d5afb8d9a.32082885800f6b054b0b0ec2becc4adf884fb27a"; // API ключ
    private String code = ""; // Код ошибки или успешного завершения
    private String lang = ""; // Направление перевода
    private String[] text = { "" }; // Сообщение, которое будем переводить
    private String response; // Ответ

    // Геттер ссылки на API запрос
    public String getYandexURL() {
        return yandexURL;
    }

    // Геттер ключа
    public String getKey() {
        return key;
    }

    // Геттер пришедшего от сервера кода
    public String getCode(){
        return code;
    }

    // Геттер направления перевода
    public String getLang(){
        return lang;
    }

    // Сеттер направления перевода
    public void setLang(String lang) {
        this.lang = lang;
    }

    // Геттер переводимого текста
    public String getTranslateText() {
        return text[0];
    }

    // Сеттер переводимого текста
    public void setTranslateText(String text) {
        this.text[0] = text;
    }

    // Вывод нужной информации
    public String getResponse() {
        //response = getTranslateText() + ". Мы это перевели с направления " + getLang() + " и получили код " + getCode();
        response = getTranslateText();
        return response;
    }
}