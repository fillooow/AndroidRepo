package com.fillooow.yandextranslateproject;

public class Translate {
    private String yandexURL = "https://translate.yandex.net/api/v1.5/tr.json/translate"; // Ссылка на яндекс
    private String key = "trnsl.1.1.20170330T085156Z.928b6e6d5afb8d9a.32082885800f6b054b0b0ec2becc4adf884fb27a"; // API ключ
    private String code = ""; // Код ошибки или успешного завершения
    private String lang = ""; // Направление перевода
    private String[] text = { "" }; // Сообщение, которое будем переводить

    public String getYandexURL() {
        return yandexURL;
    }

    public String getKey() {
        return key;
    }

    public String getCode(){
        return code;
    }

    public String getLang(){
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getText() {
        return text[0];
    }

    public void setText(String text) {
        this.text[0] = text;
    }

    // Вывод нужной информации
    public void showMessageInfo(){
        System.out.println(getText() + ". Мы это перевели с направления " + getLang() + " и получили код " + getCode());
    }
}