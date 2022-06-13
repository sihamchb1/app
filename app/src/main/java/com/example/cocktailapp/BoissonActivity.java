package com.example.cocktailapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.cocktailapp.model.Cocktail;
import com.example.cocktailapp.model.CocktailDbResponse;
import com.example.cocktailapp.model.CocktailsListViemModel;
import com.example.cocktailapp.service.CocktailDbServiceAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BoissonActivity extends AppCompatActivity {
    List<Cocktail> data=new ArrayList<>();
    public static final String COCKTAIL_ID_PARAM="cocktail.id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boisson);
        StrictMode.ThreadPolicy policy =new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        EditText editTextQuery =findViewById(R.id.editTextquery);
        Button buttonSearch =findViewById(R.id.buttonSearch);
        ListView listViewCocktails=findViewById(R.id.listviewCocktails);

       // ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,data);
        CocktailsListViemModel listViemModel =new CocktailsListViemModel(this,R.layout.cocktail_listview_layout,data);
        listViewCocktails.setAdapter(listViemModel);
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CocktailDbServiceAPI cocktailDbServiceAPI=retrofit.create(CocktailDbServiceAPI.class);
        Call<CocktailDbResponse> callCocktails = cocktailDbServiceAPI.searchCocktails("Margarita");
        callCocktails.enqueue(new Callback<CocktailDbResponse>() {
            @Override
            public void onResponse(Call<CocktailDbResponse> call, Response<CocktailDbResponse> response) {
                if(!response.isSuccessful()){
                    Log.i("indo",String.valueOf(response.code()));
                    return;
                }
                CocktailDbResponse cocktailDbResponse=response.body();
                data.clear();
                for(Cocktail cocktail:cocktailDbResponse.cocktails){
                    data.add(cocktail);
                }
                listViemModel.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CocktailDbResponse> call, Throwable t) {
                Log.e("error","Error");
            }
        });

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query=editTextQuery.getText().toString();
                CocktailDbServiceAPI cocktailDbServiceAPI=retrofit.create(CocktailDbServiceAPI.class);
                Call<CocktailDbResponse> callCocktails = cocktailDbServiceAPI.searchCocktails(query);
                callCocktails.enqueue(new Callback<CocktailDbResponse>() {
                    @Override
                    public void onResponse(Call<CocktailDbResponse> call, Response<CocktailDbResponse> response) {
                        if(!response.isSuccessful()){
                            Log.i("indo",String.valueOf(response.code()));
                            return;
                        }
                        CocktailDbResponse cocktailDbResponse=response.body();
                        data.clear();
                        for(Cocktail cocktail:cocktailDbResponse.cocktails){
                            data.add(cocktail);
                        }
                        listViemModel.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<CocktailDbResponse> call, Throwable t) {
                        Log.e("error","Error");
                    }
                });
            }

        });
        listViewCocktails.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long i) {
                int id=data.get(position).id;
                Intent intent=new Intent(getApplicationContext(),CocktailDetails.class);
                intent.putExtra(COCKTAIL_ID_PARAM,id);
                startActivity(intent);
            }
        });

    }
}