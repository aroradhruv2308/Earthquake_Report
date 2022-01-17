package com.example.android.quakereport;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.drawable.GradientDrawable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class quake_data_adapter extends ArrayAdapter {

    public quake_data_adapter(Activity context, ArrayList<quake_data> quake_data_list) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, quake_data_list);
    }
    public quake_data_adapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.earthquake_list_item, parent, false);
        }

        quake_data currentItem = (quake_data) getItem(position);
        TextView magnitudeTextView = (TextView) listItemView.findViewById(R.id.magnitude);
        magnitudeTextView.setText(currentItem.getAmplitude());

        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date);
        dateTextView.setText(currentItem.getDate());
        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time);
        timeTextView.setText(currentItem.getTime());

        TextView location1TextView = (TextView) listItemView.findViewById(R.id.location1);
        location1TextView.setText(currentItem.getRefKms());
        TextView location2TextView = (TextView) listItemView.findViewById(R.id.location2);
        location2TextView.setText(currentItem.getStateName());
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeTextView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentItem.getAmplitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        return listItemView;
    }

    private int getMagnitudeColor(String amplitude) {
        double mag = Double.parseDouble(amplitude);
        int magnitude = (int) mag;
        switch(magnitude){
            case 1:
                int magnitude1Color = ContextCompat.getColor(getContext(), R.color.magnitude1);
                return magnitude1Color;
            case 2:
                int magnitude2Color = ContextCompat.getColor(getContext(), R.color.magnitude2);
                return magnitude2Color;
            case 3:
                int magnitude3Color = ContextCompat.getColor(getContext(), R.color.magnitude3);
                return magnitude3Color;
            case 4:
                int magnitude4Color = ContextCompat.getColor(getContext(), R.color.magnitude4);
                return magnitude4Color;
            case 5:
                int magnitude5Color = ContextCompat.getColor(getContext(), R.color.magnitude5);
                return magnitude5Color;
            case 6:
                int magnitude6Color = ContextCompat.getColor(getContext(), R.color.magnitude6);
                return magnitude6Color;
            case 7:
                int magnitude7Color = ContextCompat.getColor(getContext(), R.color.magnitude7);
                return magnitude7Color;
            case 8:
                int magnitude8Color = ContextCompat.getColor(getContext(), R.color.magnitude8);
                return magnitude8Color;
            case 9:
                int magnitude9Color = ContextCompat.getColor(getContext(), R.color.magnitude9);
                return magnitude9Color;
            default:
                int magnitude10Color = ContextCompat.getColor(getContext(), R.color.magnitude10plus);
                return magnitude10Color;
        }

    }
}
