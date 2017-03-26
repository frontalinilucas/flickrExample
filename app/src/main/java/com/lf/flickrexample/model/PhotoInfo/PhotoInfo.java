package com.lf.flickrexample.model.PhotoInfo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Lucas on 25/3/17.
 */

public class PhotoInfo {

    @SerializedName("photo")
    private Photo mPhoto;

    @SerializedName("stat")
    private String mStatus;

    public Photo getPhoto() {
        return mPhoto;
    }
}
