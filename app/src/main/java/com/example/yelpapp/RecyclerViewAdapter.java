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

import java.util.ArrayList;
import java.util.HashMap;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";
    public static final String ANOTHER_MESSAGE = "AnotherMsg";
    int MAP_REQUEST = 777;


    private ArrayList<String> businessNames;
    private ArrayList<String> businessDescriptions;
    private ArrayList<String> coordinates;
    private Context context;
    //stores a hash map where the business name is the key
    //and the value is the coordinates
    private HashMap<String, String> businessCoords;

    public RecyclerViewAdapter(ArrayList<String> businessNames, ArrayList<String> businessDescriptions, ArrayList<String> coordinates , Context context ) {
        Log.d(TAG, "RecyclerViewAdapterConstructor: called.");

        this.businessDescriptions = businessDescriptions;
        this.businessNames = businessNames;
        this.coordinates = coordinates;
        this.context = context;
        this.businessCoords = new HashMap<>();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Log.d(TAG, "onBindViewHolder: called.");

        holder.businessName.setText(businessNames.get(position));
        holder.businessInfo.setText(businessDescriptions.get(position));
        businessCoords.put(businessNames.get(position), this.coordinates.get(position));



    }

    @Override
    public int getItemCount() {
        return businessNames.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView businessName;
        TextView businessInfo;


        public ViewHolder(View itemView){

            super(itemView);

            businessName = itemView.findViewById(R.id.businessName);
            businessInfo = itemView.findViewById(R.id.businessInfo);

            businessName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, MapsActivity.class);
                    intent.putExtra(ANOTHER_MESSAGE, businessCoords.get(businessName.getText().toString()));

                    context.startActivity(intent);

                }
            });
            //mapButton = itemView.findViewById(R.id.mapButton);
            //likeButton = itemView.findViewById(R.id.likeButton);

        }

    }
}
