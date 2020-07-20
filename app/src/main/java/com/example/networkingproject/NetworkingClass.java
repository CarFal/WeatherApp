package com.example.networkingproject;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkingClass  {

    APIDataListner activity;
    Context mainActivityContext;

    public  interface APIDataListner{
        public void returnAPIData(String data);
    }

    NetworkingClass(APIDataListner listner,Context context){
        this.activity = listner;
        mainActivityContext = context;
    }

    void getWeatherDataForCity(final String city) {
        final String  url = "https://api.openweathermap.org/data/2.5/weather?q=+" + city + "+&appid=071c3ffca10be01d334505630d2c1a9c";
        connectAnAPI(url);

    }
    void searchForCity(String cityName) {
        final String url = "http://gd.geobytes.com/AutoCompleteCity?&q=" + cityName;
        connectAnAPI(url);
    }

        void connectAnAPI(final String url){
        try {

            Thread thread = new Thread() {

                public void run() {

                    Looper.prepare();
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            String data = "";
                            HttpURLConnection httpURLConnection = null;
                            try {

                                httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
                                httpURLConnection.setRequestMethod("GET");
                                httpURLConnection.setRequestProperty("Content-Type", "application/json");

                                int status = httpURLConnection.getResponseCode();
                                Log.d("GET RX", " status=> " + status);

                                try {
                                    InputStream in = httpURLConnection.getInputStream();
                                    InputStreamReader inputStreamReader = new InputStreamReader(in);
                                    int inputStreamData = inputStreamReader.read();
                                    while (inputStreamData != -1) {
                                        char current = (char) inputStreamData;
                                        inputStreamData = inputStreamReader.read();
                                        data += current;
                                    }
                                    String finalData = data;
                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {
                                            //do stuff in main activity
                                           activity.returnAPIData(finalData);
                                        }
                                    });


                                }

                                catch (Exception exx) {
                                    Log.d("error", exx.toString());
                                }
                            }
                            catch (Exception e) {
                                Log.e("TX", " error => " + e.getMessage());
                                e.printStackTrace();
                            } finally {
                                if (httpURLConnection != null) {
                                    httpURLConnection.disconnect();
                                }
                            }

                            handler.removeCallbacks(this);
                            Looper.myLooper().quit();
                        }
                    }, 1000);

                    Looper.loop();
                }
            };
            thread.start();

        } catch (Exception ex) {
            Log.e("ERROR =>", "" + ex.getMessage());
            ex.printStackTrace();
        }

    }


}
