package me.gettoucan.toucan;

import android.location.Location;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Aadil on 2/13/2015.
 */
public class Tutor implements Serializable{

    private boolean isCertified;
    private String name;
    private String userName;
    private String password;
    private int id;
    private String email;
    private double distance;
    private double price;
    private double rating;
    private double availablity;
    private Location location;
    private double latitude;
    private double longitude;

    //
    public Tutor(JSONObject input){
        //setCertification(fetch(input,"certification"));  //just leave in db, or put in java object as well?
        //setAvailability(fetch(input,"availability"));
        setId(fetch(input, "id"));
        setName(fetch(input, "name"));
        setUserName(fetch(input, "username"));
        setEmail(fetch(input, "email"));
        setRating(fetch(input, "rating"));
        setPrice(fetch(input, "price"));
        //setLatitude(fetch(input, "latitude"));
        //setLongitude(fetch(input, "longitude"));
        //setDistance();

    }
    public Tutor(){

    }
    private String fetch(JSONObject input, String fieldName) {
        String s="";
        try{
            s = input.get(fieldName).toString();
        }catch(Exception e){};
        return s;
    }

    public void setCertification(String certified) { isCertified = Boolean.parseBoolean(certified); }
    public boolean getCertification() { return isCertified; }

    public void setId(String id){
        this.id = Integer.parseInt(id);
    }
    public int getId(){
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
    public double getRating(){
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
    public double getPrice(){
        return price;
    }

    public void setLatitude(String latitude){
        this.latitude = Double.parseDouble(latitude);
    }
    public double getLatitude(){
        return latitude;
    }

    public void setLongitude(String longitude){
        this.longitude = Double.parseDouble(longitude);
    }
    public double getLongitude(){
        return longitude;
    }

    //uses lat/long of tutor and current user to find distance between them
    public void setDistance(){
        Location tutorLocation =  new Location("");
        tutorLocation.setLatitude(latitude);
        tutorLocation.setLongitude(longitude);
        Location currentLocation = new Location("");
        //get currentlocation from location class. This is for testing
        currentLocation.setLongitude(33.947688);
        currentLocation.setLongitude(-83.346157);
        double distance = currentLocation.distanceTo(tutorLocation);
        this.distance = distance;
    }
    public double getDistance(){
        return distance;
    }

}
