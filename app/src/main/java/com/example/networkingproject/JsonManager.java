package com.example.networkingproject;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.ArrayList;

import static android.text.TextUtils.indexOf;
import static android.text.TextUtils.substring;

public class JsonManager {

    public Double getWeatherData(String obj){
        Double temp = 0.0;
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(obj);
            JSONArray weatherArray = jsonObject.getJSONArray("weather");
            JSONObject weatherDetails = weatherArray.getJSONObject(0);
            String mainWeather = weatherDetails.getString("main");
            JSONObject weatherTempObject = jsonObject.getJSONObject("main");
            temp = weatherTempObject.getDouble("temp");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return temp;

    }

    ArrayList<City> parceCitiesData(String json){
        ArrayList<City> citiesFromAPI = new ArrayList<>();

        JSONObject jsonObject = null;
        try {
            JSONArray array =  new JSONArray(json);
            for (int i = 0 ; i< array.length(); i++){
                String fullCity = array.getString(i);
                String cityname = substring(fullCity,0,indexOf(fullCity,','));
                String country = substring(fullCity,indexOf(fullCity,',') + 1, fullCity.length());
                City city = new City(cityname,country);
                citiesFromAPI.add(city);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return citiesFromAPI;
    }
}
