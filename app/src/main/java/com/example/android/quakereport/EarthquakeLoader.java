package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.util.ArrayList;

public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<quake_data>> {

    private String URL = "";
    public EarthquakeLoader(Context context,String url) {
        super(context);
        URL = url;
    }
    @Override
    protected void onStartLoading(){
        forceLoad();
    }
    @Override
    public ArrayList<quake_data> loadInBackground() {
        String url = URL;
        Log.v("THE URL IS : ", url);
        if (url == null || url == "") {
            return null;
        }
        ArrayList<quake_data> earthquakes = null;
        try {
            earthquakes = QueryUtils.extractEarthquakes(url);
            String s = Integer.toString(earthquakes.size());
            Log.v("EARTHQUAKE111", s);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return earthquakes;
    }

}
