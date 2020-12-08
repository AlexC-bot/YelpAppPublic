package com.example.yelpapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int AUTHUI_REQUEST = 10001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            //get

            Intent intent = new Intent(this,)
            startActivity(intent);
            finish()

        }
        //FireBaseAuth.getInstance().getCurrentUser();

    }

    //this method handles the sign on/registeration and
    //authorization of users that have a google account
    public void handleLoginRegister(View view) {

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        Intent intent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setAlwaysShowSignInMethodScreen(true)
                .build();


        startActivityForResult(intent, AUTHUI_REQUEST);
    }
}