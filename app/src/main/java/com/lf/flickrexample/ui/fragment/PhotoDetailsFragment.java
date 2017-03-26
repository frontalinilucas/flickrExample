package com.lf.flickrexample.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lf.flickrexample.R;
import com.lf.flickrexample.SingletonRetrofit;
import com.lf.flickrexample.interfaces.IApiFlickrInterfaceService;
import com.lf.flickrexample.model.PhotoInfo.PhotoDetails;
import com.lf.flickrexample.model.PhotoInfo.PhotoInfo;
import com.lf.flickrexample.model.recentPhotos.Photo;
import com.lf.flickrexample.ui.activity.PhotoActivity;
import com.lf.flickrexample.ui.activity.PhotoDetailsActivity;
import com.lf.flickrexample.utils.CircleTransform;
import com.lf.flickrexample.utils.Constants;
import com.lf.flickrexample.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Lucas on 25/3/17.
 */

public class PhotoDetailsFragment extends Fragment {

    private IApiFlickrInterfaceService mApiService;
    private Photo mPhoto;
    private Call<PhotoInfo> mPhotoInfo;

    @BindView(R.id.scrollview)
    ScrollView mScrollview;

    @BindView(R.id.imgDetail)
    AppCompatImageView mImgDetail;

    @BindView(R.id.imgIconProfile)
    AppCompatImageView mImgIconProfile;

    @BindView(R.id.textUsernameProfile)
    AppCompatTextView mTextUsernameProfile;

    @BindView(R.id.textLocationProfile)
    AppCompatTextView mTextLocationProfile;

    @BindView(R.id.textDateProfile)
    AppCompatTextView mTextDateProfile;

    @BindView(R.id.textTitle)
    AppCompatTextView mTextTitle;

    @BindView(R.id.textDescription)
    AppCompatTextView mTextDescription;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mPhoto = PhotoDetailsActivity.getPhoto(getArguments());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.fragment_photodetails, container, false);
        ButterKnife.bind(this, view);

        mApiService = SingletonRetrofit.getInstance()
                .getRetrofit()
                .create(IApiFlickrInterfaceService.class);
        getInfoExtra();
        updateImage();

        mImgDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = PhotoActivity.getIntent(getActivity(), mPhoto.getUrlImage());
                startActivity(intent);
                Utils.addStartTransitionAnimation(getActivity());
            }
        });

        return view;
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        mPhotoInfo.cancel();
    }

    private void getInfoExtra() {
        mPhotoInfo = mApiService.getInfoExtra(getString(R.string.flickrApiKey), mPhoto.getId());
        mPhotoInfo.enqueue(new Callback<PhotoInfo>() {
            @Override
            public void onResponse(Call<PhotoInfo> call, Response<PhotoInfo> response) {
                if(response.code() == Constants.CODE_RESULT_OK){
                    PhotoInfo photoInfo = response.body();
                    updateInfoProfile(photoInfo.getPhoto());
                }
            }

            @Override
            public void onFailure(Call<PhotoInfo> call, Throwable t) {
                Snackbar.make(mScrollview, getString(R.string.error), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void updateInfoProfile(PhotoDetails photoDetails) {
        if(mPhotoInfo != null && !mPhotoInfo.isCanceled()){
            Glide.with(mImgIconProfile.getContext())
                    .load(photoDetails.getOwner().getUrlImage())
                    .centerCrop()
                    .thumbnail(Constants.THUMBNAIL_ICON)
                    .crossFade(Constants.CROSSFADE_TIME)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .transform(new CircleTransform(mImgIconProfile.getContext()))
                    .into(mImgIconProfile);

            mTextUsernameProfile.setText(photoDetails.getOwner().getUsername());
            mTextLocationProfile.setText(photoDetails.getOwner().getLocation());
            mTextDateProfile.setText(photoDetails.getDates().getDate());
            if(!photoDetails.getTitle().equals(""))
                mTextTitle.setText(photoDetails.getTitle());
            else
                mTextTitle.setVisibility(View.GONE);
            mTextDescription.setText(photoDetails.getDescription());
        }
    }

    private void updateImage() {
        Glide.with(mImgDetail.getContext())
                .load(mPhoto.getUrlImage())
                .centerCrop()
                .thumbnail(Constants.THUMBNAIL_IMAGE)
                .crossFade(Constants.CROSSFADE_TIME)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(mImgDetail);
    }

}
