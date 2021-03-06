package com.lf.flickrexample.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.lf.flickrexample.model.recentPhotos.Photo;
import com.lf.flickrexample.ui.fragment.PhotoDetailsFragment;

/**
 * Created by Lucas on 25/3/17.
 */

public class PhotoDetailsActivity extends SingleFragmentActivity {

    private static final String KEY_PHOTO = "KEY_PHOTO";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    protected Fragment createFragment(Intent intent) {
        Fragment fragment = new PhotoDetailsFragment();
        fragment.setArguments(intent.getExtras());
        return fragment;
    }

    public static Intent getIntent(Context context, Photo photo){
        Intent intent = new Intent(context, PhotoDetailsActivity.class);
        Bundle args = new Bundle();
        args.putSerializable(KEY_PHOTO, photo);
        intent.putExtras(args);
        return intent;
    }

    public static Photo getPhoto(Bundle bundle){
        return (Photo)bundle.getSerializable(KEY_PHOTO);
    }

}
