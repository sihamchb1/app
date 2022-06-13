package com.example.cocktailapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cocktailapp.model.Cocktail;
import com.example.cocktailapp.model.CocktailDbResponse;
import com.example.cocktailapp.service.CocktailDbServiceAPI;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CocktailDetails extends AppCompatActivity {
    //List<Cocktail> data=new ArrayList<>();
    Cocktail data=new Cocktail();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail_details);
        StrictMode.ThreadPolicy policy =new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Intent intent=getIntent();
        int id=intent.getIntExtra(BoissonActivity.COCKTAIL_ID_PARAM,0);
        setTitle("Cocktail Detail");
        TextView textViewCocktailName=findViewById(R.id.textViewCocktailNameD);
        TextView textViewCocktailDetails=findViewById(R.id.textViewDescriptionD);
        ImageView imageViewCocktail=findViewById(R.id.imageViewCocktailD);
        TextView textViewIngredients=findViewById(R.id.textViewIngredients);
        textViewCocktailName.setText(String.valueOf(id));

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CocktailDbServiceAPI cocktailDbServiceAPI=retrofit.create(CocktailDbServiceAPI.class);
        Call<CocktailDbResponse> callCocktail = cocktailDbServiceAPI.cocktailDetail(id);

        callCocktail.enqueue(new Callback<CocktailDbResponse>() {
            @Override
            public void onResponse(Call<CocktailDbResponse> call, Response<CocktailDbResponse> response) {
                if(!response.isSuccessful()){
                    Log.i("indo",String.valueOf(response.code()));
                    return;
                }
                CocktailDbResponse cocktailDbResponse=response.body();

                for(Cocktail cocktail:cocktailDbResponse.cocktails){
                    data=cocktail;
                }
                textViewCocktailName.setText(data.name);
                textViewCocktailDetails.setText(data.descreption);
                try {
                    URL url =new URL(data.Image);
                    Bitmap bitmap= BitmapFactory.decodeStream(url.openStream());
                    imageViewCocktail.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CocktailDbResponse> call, Throwable t) {
                Log.e("error","Error");
            }
        });

    }
}