package com.lf.flickrexample.ui.activity;

import android.support.v4.app.Fragment;

import com.lf.flickrexample.ui.fragment.ListPhotosFragment;

/**
 * Created by Lucas on 25/3/17.
 */

public class ContainerFragmentActivity extends SingleFragmentActivity {

    private Fragment mFragment;

    @Override
    protected Fragment createFragment() {
        return (mFragment == null ? new ListPhotosFragment() : mFragment);
    }

    public Fragment getFragment() {
        return mFragment;
    }
}
