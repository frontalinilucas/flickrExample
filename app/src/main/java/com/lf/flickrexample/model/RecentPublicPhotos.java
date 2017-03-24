package com.lf.flickrexample.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Lucas on 24/3/17.
 */

public class RecentPublicPhotos {

    @SerializedName("photos")
    private Photos mPhotos;

    @SerializedName("stat")
    private String mStatus;

    public RecentPublicPhotos(){

    }

    public Photos getPhotos() {
        return mPhotos;
    }
}
