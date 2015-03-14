package me.toucantutor.toucan.views.tutorlist;

import android.location.Location;
import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Aadil on 2/13/2015.
 */
public class Tutor implements Serializable{

    private boolean isCertified;
    private String name;
    private String userName;
    private String password;
    private long id;
    private String email;
    private Double distance;
    private Double price;
    private Double rating;
    private Double availablity;
    private Location location;
    private Double latitude;
    private Double longitude;
    private String phoneNumber;

    public Tutor(JsonObject input){
        setCertification(fetch(input,"certification"));
        //setAvailability(fetch(input,"availability"));
        setId(fetch(input, "id"));
        setName(fetch(input, "name"));
        setUserName(fetch(input, "username"));
        setEmail(fetch(input, "email"));
        setRating(fetch(input, "rating"));
        setPrice(fetch(input, "price"));
        setLatitude(fetch(input, "latitude"));
        setLongitude(fetch(input, "longitude"));
        setDistance();
    }

    public Tutor(){

    }

    private String fetch(JsonObject input, String fieldName) {
        Log.v("","FIELDNAME "+fieldName);
        String s="";
        try{
            s = input.get(fieldName).getAsString();
        }catch(Exception e){};
        return s;
    }

    public void setCertification(String certified) { isCertified = Boolean.parseBoolean(certified); }
    public boolean getCertification() { return isCertified; }

    public void setPhoneNumber(String number) { phoneNumber = number; }
    public String getPhoneNumber() { return phoneNumber; }

    public void setId(String id){
        this.id = Long.parseLong(id);
    }
    public long getId(){
        return id;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }
    public String getUserName(){
        return userName;
    }

    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return email;
    }

    public void setRating(String rating){
        this.rating = Double.parseDouble(rating);
    }
    public Double getRating(){
        return rating;
    }

    public void setAvailability(String availablity){
        this.availablity= Double.parseDouble(availablity);
    }
    public double getAvailability(){
        return availablity;
    }

    public void setPrice(String price){
        this.price = Double.parseDouble(price);
    }
    public Double getPrice(){
        return price;
    }

    public void setLatitude(String latitude){
        this.latitude = Double.parseDouble(latitude);
    }
    public Double getLatitude(){
        return latitude;
    }

    public void setLongitude(String longitude){
        this.longitude = Double.parseDouble(longitude);
    }
    public Double getLongitude(){
        return longitude;
    }

    //uses lat/long of tutor and current user to find distance between them
    public void setDistance(){
        //flawed way of calculating distance.
        Location tutorLocation =  new Location("");
        tutorLocation.setLatitude(latitude);
        tutorLocation.setLongitude(longitude);
        Location currentLocation = new Location("");
        //get currentlocation from location class. This is for testing--use locationData class
        currentLocation.setLatitude(33.947688);
        currentLocation.setLongitude(-83.346157);
        //claimed to be the distance in miles btw points. Doesn't seem like it...
        double distance = currentLocation.distanceTo(tutorLocation)*0.000621371;
        distance = distance*100;
        distance = Math.round(distance);
        distance = distance /100;
        this.distance = distance;
    }
    public Double getDistance(){
        return distance;
    }

}
