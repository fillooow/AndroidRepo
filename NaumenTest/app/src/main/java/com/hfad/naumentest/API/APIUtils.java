package com.hfad.naumentest.API;

// Содержит базовый URL в качестве статичной переменной и связывается с интерфейсом NService

import com.hfad.naumentest.API.NService;
import com.hfad.naumentest.API.RetrofitClient;

public class APIUtils {

    public static final String BASE_URL = "http://testwork.nsd.naumen.ru/";

    public static NService getMService() {
        return RetrofitClient.getClient(BASE_URL).create(NService.class);
    }
}
