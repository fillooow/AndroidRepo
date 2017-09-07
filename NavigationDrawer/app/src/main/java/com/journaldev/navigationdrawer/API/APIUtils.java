package com.journaldev.navigationdrawer.API;

/**
 * Created by Fillow on 22.07.2017.
 */

public class APIUtils {

    public static final String BASE_URL = "https://penok.lisenkosoft.ru/";

    public static PenyokService getPService() {
        return RetrofitClient.getClient(BASE_URL).create(PenyokService.class);
    }
}
