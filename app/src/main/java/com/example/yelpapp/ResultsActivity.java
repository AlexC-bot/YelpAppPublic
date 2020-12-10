package com.example.yelpapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class ResultsActivity extends AppCompatActivity {
    String FILENAME = "storage.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // Get the Intent that started this activity and extract the string
        //the represents the response from the yelp API
        Intent intent = getIntent();
        String response = intent.getStringExtra(SearchActivity.YELP_DATA);


        try {
            parseResponse(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void parseResponse(String message) throws JSONException {

        //convert the response to a  JSON object
        JSONObject response = new JSONObject(message);




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