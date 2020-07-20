package com.example.networkingproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class WeatherActivity extends AppCompatActivity implements NetworkingClass.APIDataListner {

    JsonManager jsonManager;
    NetworkingClass networkingClass;
    TextView cityText;
    TextView weatherText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        jsonManager = new JsonManager();
        networkingClass = new NetworkingClass(this,getApplicationContext());
        String cityName = getIntent().getStringExtra("city");
        networkingClass.getWeatherDataForCity(cityName);
        cityText = findViewById(R.id.cityName);
        weatherText = findViewById(R.id.weather);
        cityText.setText(cityName);


    }

    @Override
    public void returnAPIData(String data) {
       Double temp = jsonManager.getWeatherData(data);
        weatherText.setText(String.valueOf(temp));

    }
}
