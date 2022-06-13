package com.example.cocktailapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CocktailDbResponse {
    @SerializedName("drinks")
    public List<Cocktail> cocktails=new ArrayList<>();
}
