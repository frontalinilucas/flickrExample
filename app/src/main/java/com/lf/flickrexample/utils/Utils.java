package com.lf.flickrexample.utils;

import android.app.Activity;

import com.lf.flickrexample.R;

/**
 * Created by Lucas on 25/3/17.
 */

public class Utils {

    public static void addStartTransitionAnimation(Activity activity){
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public static void addFinishTransitionAnimation(Activity activity){
        activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
