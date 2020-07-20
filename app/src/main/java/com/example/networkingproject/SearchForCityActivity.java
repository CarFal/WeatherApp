package com.example.networkingproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SearchForCityActivity extends AppCompatActivity implements NetworkingClass.APIDataListner, CitiesAdapter.AlertDialogListner {
NetworkingClass networkingClass;
JsonManager jsonManager;
    ArrayList<City> cities = new ArrayList<City>();
    RecyclerView recyclerView;
    CitiesAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_city);
        networkingClass = new NetworkingClass(this,getApplicationContext());
        jsonManager = new JsonManager();
        recyclerView = findViewById(R.id.citiesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CitiesAdapter(this,cities);
        recyclerView.setAdapter(adapter);
        setTitle("Search for new cities..");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchViewMenuItem = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) searchViewMenuItem.getActionView();
        ImageView v = (ImageView) searchView.findViewById(androidx.appcompat.R.id.search_button);
        String searchFor = searchView.getQuery().toString();
        if (!searchFor.isEmpty()) {
            searchView.setIconified(false);
            searchView.setQuery(searchFor, false);
        }

        searchView.setQueryHint("Search for cities");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("query", query);
                networkingClass.searchForCity(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() >= 3) {
                  networkingClass.searchForCity(newText);
                }
                return false;
            }
        });
        return true;
    }

    @Override
    public void returnAPIData(String data) {
            Log.d("data",data);
            ArrayList<City> result = jsonManager.parceCitiesData(data);
            cities = new ArrayList<City>();
            cities.addAll(result);
             recyclerView.setAdapter(new CitiesAdapter(this,cities));
             recyclerView.invalidate();
    }


    @Override
    public void alertDialogFinishSavingCity(City insertedCity) {
        Toast.makeText(this,insertedCity.getCityName() +" is Saved to DB",Toast.LENGTH_SHORT).show();
        finish();

    }
}
