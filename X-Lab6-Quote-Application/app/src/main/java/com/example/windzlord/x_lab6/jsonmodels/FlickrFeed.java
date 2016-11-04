package com.example.windzlord.x_lab6.jsonmodels;

import java.util.ArrayList;

/**
 * Created by WindzLord on 10/13/2016.
 */

public class FlickrFeed {
    private ArrayList<Item> items;

    public ArrayList<Item> getItems() {
        return items;
    }

    public class Item {
        private String date_taken;
        private Media media;

        public String getDate() {
            return date_taken;
        }

        public Media getMedia() {
            return media;
        }

        public class Media {
            private String m;

            public String getLink() {
                return m;
            }
        }
    }
}
