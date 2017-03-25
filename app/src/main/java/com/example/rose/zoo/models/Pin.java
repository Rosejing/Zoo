package com.example.rose.zoo.models;

/**
 * Created by Rose on 01/04/2017.
 */

public class Pin {

    private String name;
    private double latitude;
    private double longitude;

    public Pin( String name, double longitude, double latitude) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;

    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
