package me.gettoucan.toucan;

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

    public Tutor(JSONObject input){
        //setCertification(fetch(input,"certification"));  //just leave in db, or put in java object as well?
        //setAvailability(fetch(input,"availability"));
        setName(fetch(input,"name"));
        setUserName(fetch(input,"username"));
        setEmail(fetch(input,"email"));
        setRating(fetch(input,"rating"));
        setPrice(fetch(input,"price"));
        setDistance(fetch(input,"distance"));
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

    public void setDistance(String distance){
        this.distance = Double.parseDouble(distance);
    }
    public double getDistance(){
        return distance;
    }

}
