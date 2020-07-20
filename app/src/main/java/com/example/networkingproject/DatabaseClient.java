package com.example.networkingproject;

import android.content.Context;

import androidx.room.Room;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DatabaseClient {

        private static AppDataBase sDatabase;
        public static DatabaseClient sDBInstance;

        public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);

        public static DatabaseClient getInstance(Context context){

            if (sDBInstance == null) {
                sDatabase = Room.databaseBuilder(context, AppDataBase.class, "CitiesDB").build();        }

            return sDBInstance;
        }

        public static AppDataBase getsDatabase(){
            return sDatabase;
        }

        public static void insertFavoraitCity(City city){
            databaseWriteExecutor.execute(()->{
                getsDatabase().getDao().insertCity(city);

            });
        }


    }
