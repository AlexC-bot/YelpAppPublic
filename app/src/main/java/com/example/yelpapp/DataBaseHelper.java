package com.example.yelpapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper(@Nullable Context context) {
        super(context, "favorites.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableStatement = "CREATE TABLE FAVORITES (" +
                "id TEXT PRIMARY KEY, name TEXT, coordinates TEXT, description TEXT  )";
        db.execSQL(createTableStatement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


    }

    public boolean insert(String id, String name, String coordinates, String description){

        SQLiteDatabase db  = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("id", id);
        cv.put("name", name);
        cv.put("coordinates", coordinates);
        cv.put("description", description);

        long insertReturn = db.insert("FAVORITES", null, cv);

        if(insertReturn == -1)
        {
            return false;
        }
        else {
            return true;
        }


    }

    public boolean delete(String idString){
        SQLiteDatabase db = this.getWritableDatabase();
        String query =  "DELETE FROM FAVORITES WHERE id= '" + idString+"'";

        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            return true;
        }
        else{
            return false;
        }
    }

    public List<BusinessModel> getAllFavorites(){
        List<BusinessModel> returnList = new ArrayList<>();

        String query = "SELECT * FROM  FAVORITES";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do {
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String coordinates = cursor.getString(2);
                String description = cursor.getString(3);

                returnList.add(new BusinessModel(description,name,id,coordinates));




            }while (cursor.moveToNext());

        }
        else {
        }

        cursor.close();
        db.close();
        return returnList;

    }
}
