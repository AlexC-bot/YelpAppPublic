package com.example.yelpapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    public static final String YELP_DATA = "com.examp.yelpapp.YELP_DATA";
    private static final String TAG = "SearchActivity";

    public static final String API_KEY = "yw3x2eNvZC3k0XpfCNTX0l92DIis1AnSBoegKWBTfkl3dp4FjXKOgwqL_FGtnTgaQMur9w17Imrd9nsf7R5_-v58k1wwBMC7iQvWCNQ0zl6pt6BmZsC4Vwqol5HOX3Yx";

    private LocationListener locationListener;
    private LocationManager locationManager;


    private double longitude;

    private double latitude;

    private boolean gpsCoordinatesFound =false;

    private String keyWordsString;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        //enabling the location services to the GPS location
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {

            //Called whenever the location is updated
            @Override
            public void onLocationChanged(@NonNull Location location) {

                    longitude = location.getLongitude();
                    latitude = location.getLatitude();

                    Toast.makeText(getApplicationContext(), "long: " + longitude + ", lat: " + latitude+", keyword: "+keyWordsString, Toast.LENGTH_SHORT).show();

                    String url = "https://api.yelp.com/v3/businesses/search?term="+keyWordsString+"&latitude="+latitude+"&longitude="+longitude;

                    getAPIResult(url);

            }

            //Checks if the GPS is turned off
            @Override
            public void onProviderDisabled(String s) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.INTERNET
            }, 999);
            return;
        } else {
            gpsButtonListener();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case 999:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    locationManager.requestLocationUpdates("gps", 500, 10, locationListener);

                }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu, menu);
        return true;
    }


    public void getAPIResult(String url) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("api response", response);
                        //opens the WeatherInfo Activity and passes
                        //the response data to it
                        openResultsActivity(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("ERROR", "error => " + error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + API_KEY);

                return params;
            }
        };
        queue.add(getRequest);

    }
    /*
    private void getAPIResult(String url ){

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //textView.setText("Response is: "+ response.substring(0,500));
                        Log.d("api response", response);
                        //opens the WeatherInfo Activity and passes
                        //the response data to it
                        openResultsActivity(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("Error Request", "Error getting request");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);


    }
    */

    //the main activity serves as the login Activity
    //for the application
    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void openFavoritesActivity() {
        Intent intent = new Intent(this, FavoritesActivity.class);
        startActivity(intent);
    }


    //opens the ResultsActivity and sends the resulting
    //messages from the API
    private void openResultsActivity(String message) {
        Intent intent = new Intent(this, ResultsActivity.class);
        intent.putExtra(YELP_DATA, message);
        startActivity(intent);

    }

    //handles when items are selected from the menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_logout:
                Toast.makeText(this, "You Have Logged Out", Toast.LENGTH_SHORT).show();
                AuthUI.getInstance().signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    openMainActivity();
                                } else {
                                    Log.e(TAG, "onComplete", task.getException());
                                }
                            }
                        });
            case R.id.action_favorites:
                openFavoritesActivity();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void gpsButtonListener() {

        Button gpsButton = (Button) findViewById(R.id.gpsBtn);

        gpsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                TextView keyWordsTextView = (TextView) findViewById(R.id.keyWordsText);

                keyWordsString = keyWordsTextView.getText().toString();

                if(!keyWordsString.isEmpty()) {


                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    locationManager.requestLocationUpdates("gps", 1000, 15, locationListener);

                }
                else{
                    Toast.makeText(getApplicationContext(), "Keyword Field Empty", Toast.LENGTH_SHORT).show();

                }




            }
        });





    }


    //gets the location and keywords from the input fields
    //and passed the fields to the API and passes the response
    //and opens the ResultsActivity
    public void locationButtonListener(View view) {

         Button locationButton = locationButton = (Button) findViewById(R.id.locationBtn);

        TextView keyWordsTextView = (TextView) findViewById(R.id.keyWordsText);
        keyWordsString = keyWordsTextView.getText().toString();

        if(!keyWordsString.isEmpty())
        {

                TextView locationTextView = (TextView) findViewById(R.id.locationText);
                String locationString = locationTextView.getText().toString();

                if(!locationString.isEmpty()) {
                    String URL = "https://api.yelp.com/v3/businesses/search?term=" + keyWordsString + "&location=" + locationString;

                    getAPIResult(URL);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Location Field Empty", Toast.LENGTH_SHORT).show();

                }

        }
        else{
            Toast.makeText(getApplicationContext(), "Keyword Field Empty", Toast.LENGTH_SHORT).show();

        }





    }
}