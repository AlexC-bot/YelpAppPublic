package com.example.yelpapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity {

    private static final String TAG = "ResultsActivity";

    String FILENAME = "storage.json";
    RecyclerView businessList;

    ArrayList<String> businessNames;
    ArrayList<String> businessDescriptions;
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
        businessNames.add("Big Italy Pizza");
        businessDescriptions.add("11:30 AM - 10:00PM  · 4/5 · $$$$");

        businessNames.add("Little Italy Pizza");
        businessDescriptions.add("11:30 AM - 10:00PM  · 4/5 · $$$$");

        initRecyclerView();


    }

    private void initRecyclerView(){
        Log.d(TAG, "InitRecylerView(): init recyclerView.\n\n\n\n");
        RecyclerView recyclerView = findViewById(R.id.businessList);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(businessNames, businessDescriptions, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    //This checks whether the storage file exists
    public boolean isFilePresent(Context context) {
        String path = context.getFilesDir().getAbsolutePath() + "/" + FILENAME;
        File file = new File(path);
        return file.exists();
    }

    public void addFavorite(){

    }

    private boolean createFile(Context context, String jsonString){
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME,Context.MODE_PRIVATE);
            if (jsonString != null) {
                fos.write(jsonString.getBytes());
            }
            fos.close();
            return true;
        } catch (FileNotFoundException fileNotFound) {
            return false;
        } catch (IOException ioException) {
            return false;
        }

    }


    //saves a favorite  to the file
    private boolean saveFavorite(Context context, String jsonString){
        try{
            FileOutputStream fOut = openFileOutput(FILENAME,  MODE_APPEND);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);
            osw.write(jsonString);
            osw.flush();
            osw.close();
        }
        catch (FileNotFoundException fileNotFound) {
            return false;
        } catch (IOException ioException) {
            return false;
        }
        return false;
    }





}