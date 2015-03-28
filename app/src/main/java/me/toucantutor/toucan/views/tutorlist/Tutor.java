package me.toucantutor.toucan.views.tutorlist;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import me.toucantutor.toucan.locationdata.DetermineLocation;

/**
 * Created by Aadil on 2/13/2015.
 */
public class Tutor implements Serializable, Parcelable {

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


//            "biography": "",
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
        setTutorId(fetch(input, "tutorId"));
        setName(fetch(input, "name"));
        setEmail(fetch(input, "email"));
        setRating(fetch(input, "rating"));
        setRate(fetch(input, "rate"));
        setLatitude(fetch(input, "latitude"));
        setLongitude(fetch(input, "longitude"));
        setDistance();
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
    public void setDistance() {
        //flawed way of calculating distance.
        Location tutorLocation =  new Location("");
        tutorLocation.setLatitude(latitude);
        tutorLocation.setLongitude(longitude);
        setLocation(tutorLocation);

        Location currentLocation = DetermineLocation.getLocation();

        double inMeters = currentLocation.distanceTo(tutorLocation);
        double inKms = inMeters / 1000;
        double inMiles = inKms * 0.000621371;
        inMiles = Math.floor(inMiles * 100) / 100.00;

        this.distance = inMiles;

        String tutLocStr = String.format("Lat/Lng: <%f,%f>", tutorLocation.getLatitude(),
                tutorLocation.getLongitude());
        String currLocStr = String.format("Lat/Lng: <%f,%f>", currentLocation.getLatitude(),
                currentLocation.getLongitude());
        Log.d("~~~~~~~~TUTOR LOCATION:", tutLocStr);
        Log.d("~~~~~~~~CURRENT LOCATION:", currLocStr);
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
