package com.example.mapaturisticodeloja;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import androidx.annotation.NonNull;

public class MyPosicion implements LocationListener {

    public static double latitud;
    public static double longitud;
    public static boolean statusGPS;
    public static Location cordenadas;


    public void onLocationChanged(@NonNull Location location) {
        latitud=location.getLatitude();
        latitud=location.getLongitude();
        cordenadas=location;
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    public void onProviderEnabled(@NonNull String provider) {
        statusGPS=true;

    }

    public void onProviderDisabled(@NonNull String provider) {
        statusGPS=false;

    }
}
