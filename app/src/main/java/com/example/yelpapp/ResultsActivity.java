package com.example.yelpapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity {

    private static final String TAG = "ResultsActivity";

    String FILENAME = "storage.json";
    RecyclerView businessList;

    ArrayList<String> businessNames;
    ArrayList<String> businessDescriptions;
    ArrayList<String> coordinates;
    ArrayList<String> businessIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // Get the Intent that started this activity and extract the string
        //the represents the response from the yelp API
        Intent intent = getIntent();
        String response = intent.getStringExtra(SearchActivity.YELP_DATA);


        businessNames = new ArrayList<>();
        businessDescriptions = new ArrayList<>();
        coordinates = new ArrayList<>();
        businessIds = new ArrayList<>();

        businessList = findViewById(R.id.businessList);

        try {
            parseResponse(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void parseResponse(String message) throws JSONException {

        //convert the response to a  JSON object
        JSONObject response = new JSONObject(message);

        JSONArray businessesJSON = response.getJSONArray("businesses");






        for(int i=0; i<businessesJSON.length(); i++)
        {
            JSONObject biz = businessesJSON.getJSONObject(i);

            String name = biz.getString("name");
            businessNames.add(name);


            //String price = biz.getString("price");

            //getting the coordinates from the JSON response object
            JSONObject coordinatesJSON = biz.getJSONObject("coordinates");
            String latitude = coordinatesJSON.getString("latitude");
            String longitude = coordinatesJSON.getString("longitude");

            //adding the geo-coordinates to the coordinates array
            coordinates.add(latitude+","+longitude);

            String id = biz.getString("id");
            businessIds.add(id);


            String rating = biz.getString("rating");


            boolean closed = biz.getBoolean("is_closed");
            String price;
            try {
                 price = biz.getString("price");

            }
            catch (JSONException e)
            {
                price = "No Price";
            }




            if(!closed) {
                //businessNames.add("Little Italy Pizza");
                //businessDescriptions.add("11:30 AM - 10:00PM  · 4/5 · $$$$");
                businessDescriptions.add("Open · "+rating+"/5 · "+price);

                //businessDescriptions.add("Open · "+rating+" · ");

            }else{
                businessDescriptions.add("Closed · "+rating+"/5 · "+price);

            }




        }


        initRecyclerView();


    }

    private void initRecyclerView(){
        Log.d(TAG, "InitRecylerView(): init recyclerView.\n\n\n\n");
        RecyclerView recyclerView = findViewById(R.id.businessList);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(businessNames, businessDescriptions, coordinates , businessIds, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }








}