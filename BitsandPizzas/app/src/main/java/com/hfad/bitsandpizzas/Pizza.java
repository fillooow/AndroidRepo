package com.hfad.bitsandpizzas;

/**
 * Определяет массив с элементами, содержащими название и идентивикатор
 * ресурса изображения
 */

public class Pizza {
    private String name;
    private int imageResourceId;

    // Массив с пиццами
    public static final Pizza[] pizzas = {
            new Pizza("Diavolo", R.drawable.diavolo),
            new Pizza("Finghi", R.drawable.funghi),
            //new Pizza("Pepperoni"),
            //new Pizza("Denis pidor"),
            //new Pizza("Huylo")
    };

    // Конструктор с картинкой
    private Pizza(String name, int imageResourceId) {
        this.name = name;
        this.imageResourceId = imageResourceId;
    }

    // Конструктор без картинки
    //private Pizza(String name) {
    //    this.name = name;
    //}

    public String getName() {
        return name;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
