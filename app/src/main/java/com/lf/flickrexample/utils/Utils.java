package com.lf.flickrexample.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.lf.flickrexample.R;
import com.lf.flickrexample.model.recentPhotos.Photo;
import com.lf.flickrexample.ui.activity.PhotoDetailsActivity;

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

    public static void startListPhotosActivity(Activity activity, RecyclerView.ViewHolder holder, Photo photo) {
        Intent intent = PhotoDetailsActivity.getIntent(activity, photo);
        activity.startActivity(intent);
        Utils.addStartTransitionAnimation(activity);
    }
}
