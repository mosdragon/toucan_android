package me.toucantutor.toucan.locationdata;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import me.toucantutor.toucan.util.MapsClientCallback;

/**
 * Created by Osama on 02/19/15.
 */
public class DetermineLocation implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private static DetermineLocation instance = null;
    private static Location location;
    private static GoogleApiClient googleApiClient;
    private static LocationRequest locationRequest;
    private static MapsClientCallback activity;

    //        Want updates every 3 seconds
    private static final long TIME = 1000 * 3;
    //        Want updates every 100 meters
    private static final float DISTANCE = 100;

    //    Exists only to defeat instantiation
    protected DetermineLocation() {
    }

    /**
     * Execute this on the launcher activity/ home activity during onCreate()
     * It will create the API client and take care of setting up location updates.
     * @param act, a reference to the launch/home activity
     */
    public static void createInstance(MapsClientCallback act) {
        DetermineLocation.clear();
        activity = act;
        instance = new DetermineLocation();
        createApiClient();
    }

    protected static synchronized void createApiClient() {
        googleApiClient = new GoogleApiClient.Builder((Activity) activity)
                .addConnectionCallbacks(instance)
                .addOnConnectionFailedListener(instance)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
    }

    protected static void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(TIME);
        locationRequest.setSmallestDisplacement(DISTANCE);
//        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /**
     * Execute this in the launcher/home activity during onStart()
     */
    public static void connect() {
        if (true && googleApiClient != null) {
            Log.d("DetermineLocation - connect method", "~~~~~~~not null~~~~");
            googleApiClient.connect();
        }
    }

    public static DetermineLocation getInstance() {
        return instance;
    }

    public static void setLocation(Location loc) {
        location = loc;
        Log.d("DetermineLocation - setLocation","~~~~location set~~~");
        if (location == null) {
//                For emulator testing, default location is Atlanta, GA
            Log.d("DetermineLocation - setLocation","~~~~location set was NULL~~~");
            double latitude = 33.775618;
            double longitude = -84.396285;
            location = new Location("");
            location.setLatitude(latitude);
            location.setLongitude(longitude);
        } else {
            Log.d("DetermineLocation - setLocation","~~~~location was not null~~~");
        }
    }

    public static Location getLocation() {
        return location;
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d("DetermineLocation - onConnect","~~~~~~~called~~~~~~");
        Location loc = LocationServices.FusedLocationApi.getLastLocation(
                googleApiClient);
        DetermineLocation.setLocation(loc);
        startLocationUpdates();
        activity.clientFinished();
    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("onConnectionSuspended", "~~~~~~~~~~~~~Suspended~~~~~~~~~~~");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("onConnectionFailed", "~~~~~~~~~~~~~Failed~~~~~~~~~~~");
    }

    @Override
    public void onLocationChanged(Location loc) {
        Log.d("DetermineLocation", "onLocationChanged");
        setLocation(loc);
    }


    public static void clear() {
        instance = null;
        location = null;
        googleApiClient = null;
        locationRequest = null;
        activity = null;
    }
}
