package com.example.yelpapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    int AUTHUI_REQUEST = 10001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            //get

            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
            finish();


        }
        //FireBaseAuth.getInstance().getCurrentUser();

    }

    //this method handles the sign on/registeration and
    //authorization of users that have a google account
    public void handleLoginRegister(View view) {

        //list of different providers that users can use to login to the Application
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        //opens a sign-in activity with the listed providers
        Intent intent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
               // .setAlwaysShowSignInMethodScreen(true)
                .build();


        startActivityForResult(intent, AUTHUI_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AUTHUI_REQUEST){
            if(resultCode == RESULT_OK){
                //We have sighned in the user or we have a new user:w
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Log.d(TAG, "onActivityResult:" + user.getEmail());

                Intent intent   = new Intent(this, SearchActivity.class);
                startActivity(intent);
                this.finish();

            }else{
                IdpResponse  response = IdpResponse.fromResultIntent(data);
                if(response==null){
                    Log.d(TAG, "onActivityResult: the user has cancelled teh sign in request");
                }
                else{
                    Log.e(TAG, "onActivityResult:" , response.getError());
                }

                Toast.makeText(this, "Error occured while signing in", Toast.LENGTH_SHORT).show();

            }
        }
    }
}