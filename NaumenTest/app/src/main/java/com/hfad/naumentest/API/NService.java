package com.hfad.naumentest.API;

import com.hfad.naumentest.ComputerCard.CardModel;
import com.hfad.naumentest.MainPage.Model;
import com.hfad.naumentest.MainPage.SimilarModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Fillow on 24.06.2017.
 */

public interface NService {
    @GET("rest/computers")
    Call<Model> getItems(@Query("p") int pages);

    @GET("rest/computers?p=2")
    Call<Model> getItems();

    @GET("rest/computers/{id}")
    Call<CardModel> getComputer(@Path("id") int id);

    @GET("rest/computers/{id}/similar")
    Call<List<CardModel>> getSimilar(@Path("id") int id);
}
