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

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";
    int MAP_REQUEST = 777;


    private ArrayList<String> businessNames;
    private ArrayList<String> businessDescriptions;
    private Context context;

    public RecyclerViewAdapter(ArrayList<String> businessNames, ArrayList<String> businessDescriptions, Context context ) {
        Log.d(TAG, "RecyclerViewAdapterConstructor: called.");

        this.businessDescriptions = businessDescriptions;
        this.businessNames = businessNames;
        this.context = context;


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


    }

    @Override
    public int getItemCount() {
        return businessNames.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView businessName;
        TextView businessInfo;
        //ImageView mapButton;
        //ImageView likeButton;

        public ViewHolder(View itemView){

            super(itemView);

            businessName = itemView.findViewById(R.id.businessName);
            businessInfo = itemView.findViewById(R.id.businessInfo);

            businessName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, MapsActivity.class);
                    context.startActivity(intent);

                }
            });
            //mapButton = itemView.findViewById(R.id.mapButton);
            //likeButton = itemView.findViewById(R.id.likeButton);

        }

    }
}
