package me.toucantutor.toucan.views.tutorlist;

import android.location.Location;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import me.toucantutor.toucan.util.AppConstants;

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
    private Double latitude;
    private Double longitude;
    private String phoneNumber;
    private String bio;
    private String major;
    private int experience;
    private String year;
    private ArrayList<String>reviews;
    private String course;


    public Tutor(JsonObject input){
        setCertification(fetch(input,"isCertified"));
        //setAvailability(fetch(input,"availability"));
        //    setUserName(fetch(input, "username"));
        //    setEmail(fetch(input, "email"));
        setId(fetch(input, "tutorId"));
        setName(fetch(input, "name"));
        setCertification(fetch(input,"isCertified"));
        setCourse(fetch(input, "course"));
        setPhoneNumber(fetch(input, "tutorPhone"));
        setRating(fetch(input, "rating"));
        setPrice(fetch(input, "rate"));
        setLatitude(fetch(input, "latitude"));
        setLongitude(fetch(input, "longitude"));
        setDistance();
        setBio(fetch(input, "biography"));
        setMajor(fetch(input, "major"));
       // setYear(fetch(input, "year"));        //refuses to work for some reason
        setExperience(fetch(input, "experience"));
       // setReviews(fetch(input, "reviews"));  //this one as well
    }

    public Tutor(){

    }

    private String fetch(JsonObject input, String fieldName) {
        Log.v("","FIELDNAME "+fieldName);
        String s="";
        s = input.get(fieldName).getAsString();
        return s;
    }

    public void setBio(String bio) { this.bio = bio; }
    public String getBio() { return bio; }

    public void setMajor(String major) { this.major = major; }
    public String getMajor() { return major; }

    public void setYear(String year) { this.year = year; }
    public String getYear() { return year; }

    public void setExperience(String experience) { this.experience = Integer.parseInt(experience); }
    public int getExperience() { return experience; }

    public void setReviews(String reviews) {
        this.reviews = new ArrayList<String>();
        JsonArray array = new JsonParser().parse(reviews).getAsJsonArray();
        for (int x=0;x<array.size();x++){
            String review = array.get(x).toString();
            this.reviews.add(review);
        }
    }
    public ArrayList<String> getReviews() { return reviews; }

    public void setCertification(String certified) { isCertified = Boolean.parseBoolean(certified); }
    public boolean getCertification() { return isCertified; }

    public void setPhoneNumber(String number) { phoneNumber = number; }
    public String getPhoneNumber() { return phoneNumber; }

    public void setCourse(String course) { this.course = course; }
    public String getCourse() { return course; }

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
        currentLocation.setLatitude(AppConstants.DUMMY_LAT);
        currentLocation.setLongitude(AppConstants.DUMMY_LONG);
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