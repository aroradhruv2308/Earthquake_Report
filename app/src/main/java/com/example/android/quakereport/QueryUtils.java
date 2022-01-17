package com.example.android.quakereport;

import static android.os.PersistableBundle.readFromStream;
import static com.example.android.quakereport.EarthquakeActivity.LOG_TAG;

import android.util.Log;
import android.util.LogPrinter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

import java.text.DecimalFormat;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {




    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link quake_data} objects that has been built up from
     * parsing a JSON response.
     */

    public static ArrayList<quake_data> extractEarthquakes(String requestUrl) throws JSONException {

        String SAMPLE_JSON_RESPONSE = null;
        URL Url = createUrl(requestUrl);
        try {
            SAMPLE_JSON_RESPONSE = makeHttpRequest(Url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<quake_data> earthquakes = new ArrayList<>();
        //now we will be parsing the data and adding the data inside the array_list
        //and finally we will be using this array as the array list
        earthquakes = extractFeaturesFromEarthquakes(SAMPLE_JSON_RESPONSE);
        String s = Integer.toString(earthquakes.size());
        Log.v("EARTHQUAKE ARRAY ",s);
        return earthquakes;
    }
    private static ArrayList<quake_data> extractFeaturesFromEarthquakes(String jsonResponse) throws JSONException {
        ArrayList<quake_data> earthquakes = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(jsonResponse);
            JSONArray features = root.getJSONArray("features");
            //now we'll loop through every object present in the features array
            for (int i = 0; i < features.length(); i++) {
                JSONObject currentFeature = features.getJSONObject(i);
                JSONObject currentProps = currentFeature.getJSONObject("properties");
                double magnitude = currentProps.getDouble("mag");
                DecimalFormat formatter = new DecimalFormat("0.0");
                String magOutput = formatter.format(magnitude);
                String location = currentProps.getString("place");
                String url = currentProps.getString("url");
                String string = location;
                String location1 = "";
                String location2 = "";
                Boolean it_contains = false;
                if (location.contains("of")) {
                    String[] parts = string.split("of");
                    location1 = parts[0]; // before of
                    location2 = parts[1]; // after of
                    it_contains = true;
                }
                long time = currentProps.getLong("time");
                long timeInMilliseconds = time;
                Date dateObject = new Date(timeInMilliseconds);
                SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd, yyyy ");
                SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a");
                String dateToDisplay = dateFormatter.format(dateObject);
                String timeToDisplay = timeFormatter.format(dateObject);
                if (it_contains) {
                    earthquakes.add(new quake_data(location2, location1, magOutput, dateToDisplay, timeToDisplay, url));
                } else {
                    earthquakes.add(new quake_data(location, "Almost at", magOutput, dateToDisplay, timeToDisplay, url));
                }
            }
        }catch(JSONException e){
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return earthquakes;
    }
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

}