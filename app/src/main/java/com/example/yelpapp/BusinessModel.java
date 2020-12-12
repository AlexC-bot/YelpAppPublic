package com.example.yelpapp;

public class BusinessModel {


    private String description;
    private String name;
    private String id;
    private String coordinates;


    public BusinessModel(String description, String name, String id, String coordinates){
        this.description = description;
        this.name = name;
        this.id = id;
        this.coordinates = coordinates;

    }


    public String getId() {
        return id;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public String getDescription() {
        return description;

    }

    public String getName() {
        return name;
    }
}
