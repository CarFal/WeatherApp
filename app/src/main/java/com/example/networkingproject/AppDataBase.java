package com.example.networkingproject;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {City.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract CityDao getDao();

}
