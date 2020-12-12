package com.example.yelpapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    String FILENAME = "storage.json";
    private static final String TAG = "FavoritesActivity";


    ArrayList<String> businessNames;
    ArrayList<String> businessDescriptions;
    ArrayList<String> businessIds;
    ArrayList<String> coordinates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        businessNames = new ArrayList<>();
        businessIds = new ArrayList<>();
        businessDescriptions = new ArrayList<>();
        coordinates = new ArrayList<>();

        getFavorites();
    }


    private void getFavorites(){
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());

        List<BusinessModel> favorites = dataBaseHelper.getAllFavorites();


        for(BusinessModel fav : favorites){

            //checks if any of the favorited business matches
            //any of the businesses coming in from the new API response

            businessIds.add( fav.getId());
            businessNames.add(fav.getName());
            businessDescriptions.add(fav.getDescription());
            coordinates.add(fav.getCoordinates());

        }

        initRecyclerView();

    }

    private void initRecyclerView(){
        Log.d(TAG, "InitRecylerView(): init recyclerView.");
        RecyclerView recyclerView = findViewById(R.id.businessList);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(businessNames, businessDescriptions, coordinates , businessIds, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }







}