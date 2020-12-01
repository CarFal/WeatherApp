package com.example.networkingproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DatabaseCitiesAdapter extends RecyclerView.Adapter<DatabaseCitiesAdapter.FavoriteCitiesViewHolder> {

    private Context mCtx;
    private List<City> cityList;
    private ClickListener listener;

    interface ClickListener{
        void cityDidClicked(City city);
    }

    public DatabaseCitiesAdapter(Context mCtx,Activity activity, List<City> cityList) {
        this.mCtx = mCtx;
        this.cityList = cityList;
        this.listener = (ClickListener)activity;

    }

    @Override
    public FavoriteCitiesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_cities, parent, false);
        return new FavoriteCitiesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DatabaseCitiesAdapter.FavoriteCitiesViewHolder holder, int position) {
        City t = cityList.get(position);
        holder.cityTextView.setText(t.getCityName() +": "+t.getCountry());

    }


    @Override
    public int getItemCount() {
        return cityList.size();
    }

    class FavoriteCitiesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView cityTextView, countryTextView;

        public FavoriteCitiesViewHolder(View itemView) {
            super(itemView);
            cityTextView = itemView.findViewById(R.id.cityy);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            City city = cityList.get(getAdapterPosition());
            listener.cityDidClicked(city);


//            Intent intent = new Intent(mCtx,WeatherActivity.class);
//            intent.putExtra("city",city.getCityName());
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            mCtx.startActivity(intent);
        }
    }

}
