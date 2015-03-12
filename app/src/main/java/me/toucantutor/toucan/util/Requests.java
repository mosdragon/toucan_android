package me.toucantutor.toucan.util;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;

/**
 * Created by aadil on 3/12/15.
 */
public final class Requests {

    private Requests(){

    }

    public static void findActiveTutors(){
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
        JsonArray array = new JsonArray();
        JsonObject object = new JsonObject();
        object.addProperty("latitude", 33);
        object.addProperty("longitude", 55);
        object.addProperty("userid", "213412435");
        object.addProperty("course", "calc1");
        object.addProperty("miles", 1234.4);
        object.addProperty("endTime", 12);
        System.out.println(gson.toJson(object));
        System.exit(1);

    }
    public static void something(){

    }
    public static void something1(){

    }
    public static void something3(){

    }
    public static void something4(){

    }
    public static void something5(){

    }
    public static void something6(){

    }


}
