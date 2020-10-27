package com.example.mapaturisticodeloja;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    private Button loca;
    private Button food;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        loca = (Button) findViewById(R.id.loca);
        food=(Button ) findViewById(R.id.food);
        loca.setOnClickListener(this);
        food.setOnClickListener(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng loja = new LatLng(-3.9835695, -79.2020311);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(loja));

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMapLongClickListener(this);
        prefs = getSharedPreferences("MisPrefefencias", Context.MODE_PRIVATE);

    }

    private void miPosicion() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
        }
        LocationManager objLocation = null;
        LocationListener obLocListener;

        objLocation = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        obLocListener = new MyPosicion();
        objLocation.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, obLocListener);

        if (objLocation.isProviderEnabled((LocationManager.GPS_PROVIDER))) {

            Toast.makeText(this,"GPS habilitado",Toast.LENGTH_SHORT).show();
            LatLng loja = new LatLng(MyPosicion.latitud,MyPosicion.latitud);
            mMap.addMarker(new MarkerOptions().position(loja).title(" LOJA"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(loja));
            CameraUpdate ZoomCamera=CameraUpdateFactory.zoomTo(19);
            mMap.animateCamera(ZoomCamera);

        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("GPS NO ESTA ACTIVO");
            alert.setPositiveButton("ok", null);
            alert.create().show();

        }
    }

    //este metodo se usa para preguntar  el permiso de localizacion a lo que se crea la actividad
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
        }
    }
    @Override
    public void onClick (View v){
        if(v==loca){
            miPosicion();
        } if (v==food){

            double lat=prefs.getFloat("latitud",0);
            double log=prefs.getFloat("longitud",0);

            if(lat!=0){
                CameraUpdate cam=CameraUpdateFactory.newLatLng(new LatLng(lat,log));
                mMap.moveCamera(cam);

            }else {
                Toast.makeText(MapsFragment.this,
                        "No se encuentra una zona registrata",Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onMapLongClick(LatLng punto) {

        Toast.makeText(MapsFragment.this, "Click posicion" +punto.latitude+punto.latitude, Toast.LENGTH_SHORT).show();

        prefs=getSharedPreferences("MyPreferencia",Context.MODE_PRIVATE);
        SharedPreferences.Editor ed=prefs.edit();

        Toast.makeText(MapsFragment.this, "Click posicion" +punto.latitude+punto.latitude, Toast.LENGTH_SHORT).show();
        ed.putFloat("latitud", (float)punto.latitude);
        ed.putFloat("longitud",(float)punto.longitude);
        ed.commit();

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(punto.latitude, punto.latitude))
        );

    }
}
