package com.example.cocktailapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class Cocktail {
    @SerializedName("idDrink")
    public int id;
    @SerializedName("strDrink")
    public String name;
    @SerializedName("strDrinkThumb")
    public String Image;
    @SerializedName("strInstructions")
    public String descreption;





}
