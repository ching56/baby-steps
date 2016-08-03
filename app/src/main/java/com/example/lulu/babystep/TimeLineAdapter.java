package com.example.lulu.babystep;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ching on 2016/8/1.
 */
public class TimeLineAdapter extends ArrayAdapter<String> {
    public TimeLineAdapter(Context context, ArrayList<String> event) {
        super(context, 0, event);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        String string = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.time_line_node, parent, false);
        }
        // Lookup view for data population
        TextView event = (TextView) convertView.findViewById(R.id.textView1);
        // Populate the data into the template view using the data object
        event.setText(string);
        // Return the completed view to render on screen
        return convertView;
    }
}