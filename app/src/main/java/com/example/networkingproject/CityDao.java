package com.example.networkingproject;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CityDao {


    @Query("SELECT * FROM City")
    public List<City> getAllCities();

    @Insert
    public void insertCity(City city);


}
