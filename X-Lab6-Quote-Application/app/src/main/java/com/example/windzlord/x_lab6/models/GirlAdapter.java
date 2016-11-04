package com.example.windzlord.x_lab6.models;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.windzlord.x_lab6.R;
import com.example.windzlord.x_lab6.jsonmodels.FlickrFeed;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by WindzLord on 10/13/2016.
 */

public class GirlAdapter extends ArrayAdapter<FlickrFeed.Item> {

    public GirlAdapter(Context context, int resource, List<FlickrFeed.Item> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.getContext());
        if (convertView == null)
            convertView = layoutInflater.inflate(R.layout.layout_girl_item, parent, false);

        FlickrFeed.Item item = getItem(position);

        ((TextView) convertView.findViewById(R.id.textView_date)).setText("     Date: " + item.getDate());
        ((TextView) convertView.findViewById(R.id.textView_link)).setText("     Link: " + item.getMedia().getLink());
        ImageLoader.getInstance().displayImage(
                item.getMedia().getLink(),
                (ImageView) convertView.findViewById(R.id.imageView_girl));

        return convertView;
    }
}
