package com.example.cocktailapp.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cocktailapp.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class CocktailsListViemModel extends ArrayAdapter<Cocktail> {
    private int resource;
    public CocktailsListViemModel(@NonNull Context context, int resource, List<Cocktail> data) {
        super(context, resource,data);
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listViewItem=convertView;
        if(listViewItem==null){
            listViewItem= LayoutInflater.from(getContext()).inflate(resource,parent,false);
        }
        ImageView imageViewCocktail=listViewItem.findViewById(R.id.imageViewCocktail);
        TextView textViewCocktailName=listViewItem.findViewById(R.id.textViewCocktailName);
        TextView textViewDescription=listViewItem.findViewById(R.id.textViewDescription);

        textViewCocktailName.setText(getItem(position).name);
        if(getItem(position).descreption.length()>100){
            textViewDescription.setText(getItem(position).descreption.substring(0,100)+"...");
        }
        else{
            textViewDescription.setText(getItem(position).descreption);}
        try {
            URL url =new URL(getItem(position).Image);
            Bitmap bitmap= BitmapFactory.decodeStream(url.openStream());
            imageViewCocktail.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listViewItem;
    }
}
