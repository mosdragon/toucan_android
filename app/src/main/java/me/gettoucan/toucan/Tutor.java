package me.gettoucan.toucan;

import com.google.gson.JsonObject;

import org.json.JSONObject;

/**
 * Created by Aadil on 2/13/2015.
 */
public class Tutor {

    private String name;
    private String userName;
    private String password;
    private int id;
    private String email;


    private String firstName;
    private String lastName;
    private double price;
    private double rating;
    private double availablity;

    public Tutor(JSONObject input){
        setName(fetch(input,"name"));
        setUserName(fetch(input,"username"));
        setEmail(fetch(input,"email"));

    }
    public Tutor(){

    }
    private String fetch(JSONObject input, String fieldName) {
        String s="";
        try{
            s = input.get(fieldName).toString();
        }catch(Exception e){};
        System.out.println(s+ " PPPPASPFPAPSDF");
        return s;
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
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }



    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public String getFirstName(){
        return firstName;
    }

    public void setLastName(String lastNMame){
        this.firstName = firstName;
    }
    public String getLastName(){
        return lastName;
    }

    public void setRating(double rating){
        this.rating = rating;
    }
    public double getRating(){
        return rating;
    }

    public void setAvailable(double availablity){
        this.availablity= availablity;
    }
    public double getAvailable(){
        return availablity;
    }

    public void setPrice(double price){
        this.price = price;
    }
    public double getPrice(){
        return price;
    }

}
