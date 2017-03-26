package com.lf.flickrexample.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.lf.flickrexample.ui.fragment.ListPhotosFragment;

/**
 * Created by Lucas on 25/3/17.
 */

public class ListPhotosActivity extends SingleFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        if(getSupportActionBar() != null){
            getSupportActionBar().setElevation(0);
        }
    }

    @Override
    protected Fragment createFragment(Intent intent) {
        return new ListPhotosFragment();
    }
}
