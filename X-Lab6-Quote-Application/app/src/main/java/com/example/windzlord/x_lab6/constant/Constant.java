package com.example.windzlord.x_lab6.constant;

import java.util.Random;

/**
 * Created by WindzLord on 10/12/2016.
 */

public class Constant {
    public static boolean running = true;
    public static final Random RANDOM = new Random();
    public static final int NUMBER = 20;

    private static final String[] CATEGORIES = {"buildings", "food", "nature", "people", "technology", "objects"};
    private static final int SIZE_WIDTH = 600;
    private static final int SIZE_HEIGHT = 800;

    public static final String UNSPLASH_API = String.format(
            "https://source.unsplash.com/category/%s/%sx%s",
            CATEGORIES[2], SIZE_WIDTH, SIZE_HEIGHT
    );

    public static final String QUOTE_API = String.format(
            "http://quotesondesign.com/wp-json/posts?filter[orderby]=rand&filter[posts_per_page]=%s",
            NUMBER
    );

    public static final String PLACE_API = "http://jsonplaceholder.typicode.com/posts";

    public static final String GIRL_API = "http://www.flickr.com/services/feeds/photos_public.gne?tags=girl&format=json&title=girl";
}
