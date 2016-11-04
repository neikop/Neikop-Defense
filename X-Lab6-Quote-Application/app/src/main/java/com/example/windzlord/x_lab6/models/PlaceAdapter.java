package com.example.windzlord.x_lab6.models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.windzlord.x_lab6.R;
import com.example.windzlord.x_lab6.jsonmodels.PlaceJSONModel;

import java.util.List;

/**
 * Created by WindzLord on 10/13/2016.
 */

public class PlaceAdapter extends ArrayAdapter<PlaceJSONModel> {
    public PlaceAdapter(Context context, int resource, List<PlaceJSONModel> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.getContext());
        if (convertView == null)
            convertView = layoutInflater.inflate(R.layout.layout_space_item, parent, false);

        PlaceJSONModel place = getItem(position);

        ((TextView)convertView.findViewById(R.id.textView2_title)).setText("     Title: " + place.getTitle());
        ((TextView)convertView.findViewById(R.id.textView2_body)).setText("     Body: " + place.getBody());

        return convertView;
    }
}
