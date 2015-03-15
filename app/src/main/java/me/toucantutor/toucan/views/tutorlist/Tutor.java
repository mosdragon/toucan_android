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

    private Double availablity;
    private Double distance;
    private Double rate;
    private Double rating;
    private Location location;
    private long tutorId;
    private String email;
    private String name;
    private String phoneNumber;

    private String biography;
    private String course;
    private boolean isCertified;
    private Double latitude;
    private Double longitude;
    private String major;


//    "biography": "",
//            "course": "PORT 2001",
//            "experience": 0,
//            "isCertified": true,
//            "latitude": 33.300933,
//            "longitude": -83.794122,
//            "major": "",
//            "name": "John Cena",
//            "rate": 8,
//            "rating": 0
//            "reviews": [ ],
//            "tutorId": 903778,
//            "tutorPhone": "6783219900",
//            "year": "",


    public Tutor(JsonObject input){
        setCertified(input.get("isCertified").getAsBoolean());
        //setAvailability(fetch(input,"availability"));
        setTutorId(fetch(input, "tutorId"));
        setName(fetch(input, "name"));
        setEmail(fetch(input, "email"));
        setRating(fetch(input, "rating"));
        setRate(fetch(input, "rate"));
        setLatitude(fetch(input, "latitude"));
        setLongitude(fetch(input, "longitude"));
        setDistance();
    }

    public Tutor(){

    }

    private String fetch(JsonObject input, String fieldName) {
        Log.v("","FIELDNAME "+fieldName);
        String s="";
        try {
            s = input.get(fieldName).getAsString();
        } catch(Exception e){};
        return s;
    }

    public void setCertified(Boolean isCertified) {
        this.isCertified = isCertified;
    }
    public boolean getCertification() {
        return isCertified;
    }

    public void setPhoneNumber(String number) { phoneNumber = number; }
    public String getPhoneNumber() { return phoneNumber; }

    public void setTutorId(String givenTutorId){
        this.tutorId = Long.parseLong(givenTutorId);
    }
    public long getTutorId(){
        return tutorId;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
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

    public void setRate(String rate){
        this.rate = Double.parseDouble(rate);
    }
    public Double getRate(){
        return rate;
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

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }
}
