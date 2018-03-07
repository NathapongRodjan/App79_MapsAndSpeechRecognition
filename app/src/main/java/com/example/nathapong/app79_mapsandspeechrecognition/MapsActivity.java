package com.example.nathapong.app79_mapsandspeechrecognition;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.nathapong.app79_mapsandspeechrecognition.Model.CountryDataSource;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private String receiveCountry;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        Intent mainActivityIntent = this.getIntent();
        receiveCountry = mainActivityIntent.getStringExtra(CountryDataSource.COUNTRY_KEY);

         if (receiveCountry == null){
             receiveCountry = CountryDataSource.DEFAULT_COUNTRY_NAME;
         }


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        double countryLatitude = CountryDataSource.DEFAULT_COUNTRY_LATITUDE;
        double countryLongitude = CountryDataSource.DEFAULT_COUNTRY_LONGITUDE;

        CountryDataSource countryDataSource = MainActivity.countryDataSource;
        String countryMessage = countryDataSource.getTheInfoOfTheCountry(receiveCountry);

        Geocoder geocoder = new Geocoder(MapsActivity.this);

        try{

            String countryAddress = receiveCountry;
            List<Address> countryAddresses = geocoder.getFromLocationName(countryAddress,10);

            if (countryAddresses != null){
                countryLatitude = countryAddresses.get(0).getLatitude();
                countryLongitude = countryAddresses.get(0).getLongitude();
            }
            else {
                receiveCountry = CountryDataSource.DEFAULT_COUNTRY_NAME;
            }

        }catch (IOException ioe){

            receiveCountry = CountryDataSource.DEFAULT_COUNTRY_NAME;
        }


        LatLng myCountryLocation = new LatLng(countryLatitude, countryLongitude);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(myCountryLocation, 15.0f);
        mMap.moveCamera(cameraUpdate);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(myCountryLocation);
        markerOptions.title(countryMessage);
        markerOptions.snippet(CountryDataSource.DEFAULT_MESSAGE);
        mMap.addMarker(markerOptions);

        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(myCountryLocation);
        circleOptions.radius(400);
        circleOptions.strokeWidth(14.5f);
        circleOptions.strokeColor(Color.GREEN);
        mMap.addCircle(circleOptions);


        /*LatLng myHome = new LatLng(13.903001, 100.368078);

        // mMap.addMarker(new MarkerOptions().position(myHome).title("Marker in My Home"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(myHome));

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(myHome, 15.0f);
        mMap.moveCamera(cameraUpdate);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(myHome);
        markerOptions.title("Welcome to My Home");
        markerOptions.snippet("Fantastic");
        mMap.addMarker(markerOptions);

        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(myHome);
        circleOptions.radius(300);
        circleOptions.strokeWidth(20.0f);
        circleOptions.strokeColor(Color.YELLOW);
        mMap.addCircle(circleOptions);*/

    }
}
