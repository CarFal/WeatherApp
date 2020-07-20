package com.example.networkingproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.TasksViewHolder> {

    interface AlertDialogListner {
        public void alertDialogFinishSavingCity(City insertedCity);
    }
        private Context mCtx;
        private List<City> cityList;
        AlertDialogListner listner;

        public CitiesAdapter(Context mCtx, List<City> cityList) {
            this.mCtx = mCtx;
            this.cityList = cityList;
            listner = (AlertDialogListner)mCtx;
        }

        @Override
        public TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_cities, parent, false);
            return new TasksViewHolder(view);
        }

        @Override
        public void onBindViewHolder(TasksViewHolder holder, int position) {
            City t = cityList.get(position);
            holder.cityTextView.setText(t.getCityName() +": "+t.getCountry());


        }

        @Override
        public int getItemCount() {
            return cityList.size();
        }

        class TasksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView cityTextView, countryTextView;

            public TasksViewHolder(View itemView) {
                super(itemView);

                cityTextView = itemView.findViewById(R.id.cityy);

                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                City city = cityList.get(getAdapterPosition());
                new AlertDialog.Builder(mCtx)
                        .setTitle("Favorite City?")
                        .setMessage("Are you sure you want to insert "+ city.getCityName()+" to Database?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("city to save",city.getCityName());
                                DatabaseClient.insertFavoraitCity(city);
                                listner.alertDialogFinishSavingCity(city);

                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        }


}
