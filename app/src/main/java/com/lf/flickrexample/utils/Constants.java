package com.lf.flickrexample.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Lucas on 25/3/17.
 */

public class Constants {

    public static final SimpleDateFormat FORMAT_TAKEN_PROFILE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
    public static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("MMM d", Locale.US);

    public static final String BASE_URL_IMAGE = "https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}_n.jpg";
    public static final String BASE_URL_ICON1 = "http://farm{icon-farm}.staticflickr.com/{icon-server}/buddyicons/{nsid}.jpg";
    public static final String BASE_URL_ICON2 = "https://flickr.com/buddyicons/{nsid}.jpg";

    public static final int GRID_COLUMNS = 3;
    public static final int PHOTOS_PER_PAGE = 60;

    public static final float THUMBNAIL_IMAGE = 0.5f;
    public static final float THUMBNAIL_ICON = 0.1f;
    public static final int CROSSFADE_TIME = 200;

    public static final int CODE_RESULT_OK = 200;

}
