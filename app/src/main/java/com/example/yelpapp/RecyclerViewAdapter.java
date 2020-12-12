package com.example.yelpapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.internal.HashAccumulator;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";
    public static final String ANOTHER_MESSAGE = "AnotherMsg";
    String FILENAME = "favorites.txt";
    int MAP_REQUEST = 777;


    private ArrayList<String> businessNames;
    private ArrayList<String> businessDescriptions;
    private ArrayList<String> coordinates;
    private  ArrayList<String> businessIds;
    private Context context;

    //stores a hash map where the business name is the key
    //and its corresponding index (or position) is the value
    private HashMap<String, Integer> businessPosition;

    public RecyclerViewAdapter(ArrayList<String> businessNames, ArrayList<String> businessDescriptions, ArrayList<String> coordinates , ArrayList<String> businessIds, Context context ) {
        Log.d(TAG, "RecyclerViewAdapterConstructor: called.");

        this.businessDescriptions = businessDescriptions;
        this.businessNames = businessNames;
        this.coordinates = coordinates;
        this.context = context;
        this.businessPosition = new HashMap<>();
        this.businessIds = businessIds;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);



        Log.d(TAG, "onBindViewHolder: called.");

        //DataBaseHelper dbHelper = new DataBaseHelper(context);

        holder.businessName.setText(businessNames.get(position));
        holder.businessInfo.setText(businessDescriptions.get(position));
        businessPosition.put(businessNames.get(position), position );


        List<BusinessModel> favorites = dataBaseHelper.getAllFavorites();

        for(BusinessModel fav : favorites){

            //checks if any of the favorited business matches
            //any of the businesses coming in from the new API response
            if(fav.getId().equals(businessIds.get(position))){
                holder.starButton.setLiked(true);
            }
        }



    }

    @Override
    public int getItemCount() {
        return businessNames.size();
    }


    //This checks whether the storage file exists
    public boolean isFilePresent(Context context) {
        String path = context.getFilesDir().getAbsolutePath() + "/" + FILENAME;
        File file = new File(path);
        return file.exists();
    }







    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView businessName;
        TextView businessInfo;
        LikeButton starButton;


        public ViewHolder(View itemView){

            super(itemView);

            businessName = itemView.findViewById(R.id.businessName);
            businessInfo = itemView.findViewById(R.id.businessInfo);

            starButton = itemView.findViewById(R.id.star_button);

            starButton.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    DataBaseHelper dbHelper = new DataBaseHelper(context);

                    int position = businessPosition.get(businessName.getText().toString());

                    boolean success =  dbHelper.insert(businessIds.get(position), businessNames.get(position), coordinates.get(position), businessDescriptions.get(position));

                    if(success){
                        Log.d(TAG, "Liked: Item added to the database!");
                    }

                }

                @Override
                public void unLiked(LikeButton likeButton) {

                    DataBaseHelper dbHelper = new DataBaseHelper(context);


                    int position = businessPosition.get(businessName.getText().toString());

                    boolean success = dbHelper.delete(businessIds.get(position));

                    if(success){
                        Log.d(TAG, "Unliked: Item removed from the database!");
                    }


                }
            });

            businessName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, MapsActivity.class);
                    int position = businessPosition .get(businessName.getText().toString());
                    String geoLocation = coordinates.get(position);
                    intent.putExtra(ANOTHER_MESSAGE, geoLocation );

                    context.startActivity(intent);

                }
            });
            //mapButton = itemView.findViewById(R.id.mapButton);
            //likeButton = itemView.findViewById(R.id.likeButton);

        }

    }
}
