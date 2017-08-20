package com.hfad.penyok.API;



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

    @GET("json/my_stats")
    Call<Model> getStats(@Query("sid") String sid);

    /* @GET("rest/computers?p=2")
    Call<Model> getItems();

    @GET("rest/computers/{id}")
    Call<CardModel> getComputer(@Path("id") int id);

    @GET("rest/computers/{id}/similar")
    Call<List<CardModel>> getSimilar(@Path("id") int id); */
}
