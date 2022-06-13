package com.example.cocktailapp.service;

import com.example.cocktailapp.model.CocktailDbResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CocktailDbServiceAPI {
    @GET("search.php")
    public Call<CocktailDbResponse> searchCocktails(@Query("s") String query);

    @GET("filter.php")
    public  Call<CocktailDbResponse> CategoryCocktail(@Query("c") String query);
    @GET("lookup.php")
    public Call<CocktailDbResponse> cocktailDetail(@Query("i") int id);
}
