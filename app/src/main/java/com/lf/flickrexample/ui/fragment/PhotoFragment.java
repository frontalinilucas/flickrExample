package com.lf.flickrexample.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lf.flickrexample.R;
import com.lf.flickrexample.ui.activity.PhotoActivity;
import com.lf.flickrexample.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lucas on 25/3/17.
 */

public class PhotoFragment extends Fragment {

    private String mUrlImage;

    @BindView(R.id.imgProfile)
    AppCompatImageView mImgProfile;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mUrlImage = PhotoActivity.getUrlPhoto(getArguments());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        ButterKnife.bind(this, view);

        updateImage();

        return view;
    }

    private void updateImage() {
        Glide.with(mImgProfile.getContext())
                .load(mUrlImage)
                .thumbnail(Constants.THUMBNAIL_IMAGE)
                .crossFade(Constants.CROSSFADE_TIME)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(mImgProfile);
    }

}
