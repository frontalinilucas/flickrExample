package com.lf.flickrexample.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.lf.flickrexample.ui.fragment.PhotoFragment;

/**
 * Created by Lucas on 25/3/17.
 */

public class PhotoActivity extends SingleFragmentActivity {

    private static final String KEY_URL_PHOTO = "KEY_URL_PHOTO";

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
        Fragment fragment = new PhotoFragment();
        fragment.setArguments(intent.getExtras());
        return fragment;
    }

    public static Intent getIntent(Context context, String urlPhoto){
        Intent intent = new Intent(context, PhotoActivity.class);
        Bundle args = new Bundle();
        args.putString(KEY_URL_PHOTO, urlPhoto);
        intent.putExtras(args);
        return intent;
    }

    public static String getUrlPhoto(Bundle bundle){
        return bundle.getString(KEY_URL_PHOTO);
    }

}
