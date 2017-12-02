package com.journaldev.navigationdrawer.API;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Fillow on 24.06.2017.
 */

public interface PenyokService {
    @GET("json/login")
    Call<Model> getLogIn(@Query("id") String id, @Query("pass") String pass, @Query("device") String device);

    @GET("json/user_stats")
    Call<Model> getStats(@Query("sid") String sid);

    @GET("json/my_friends")
    Call<Model> getFriends(@Query("sid") String sid);

    @GET("json/remove_friend")
    Call<Model> getRemoveFriend(@Query("id") int id, @Query("sid") String sid);

    @GET("json/name_for")
    Call<Model> getUserName(@Query("id") int id, @Query("sid") String sid);

    @GET("json/transactions_list")
    Call<Model> getTransactionsHistory(@Query("sid") String sid);


    /* @GET("rest/computers?p=2")
    Call<Model> getItems();

    @GET("rest/computers/{id}")
    Call<CardModel> getComputer(@Path("id") int id);

    @GET("rest/computers/{id}/similar")
    Call<List<CardModel>> getSimilar(@Path("id") int id); */
}