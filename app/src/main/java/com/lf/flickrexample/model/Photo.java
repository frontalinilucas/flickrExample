package com.lf.flickrexample.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Lucas on 24/3/17.
 */

public class Photo {

    private static final String BASE_URL_IMAGE = "https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}_n.jpg";

    @SerializedName("id")
    private String mId;

    @SerializedName("owner")
    private String mOwner;

    @SerializedName("secret")
    private String mSecret;

    @SerializedName("server")
    private int mServer;

    @SerializedName("farm")
    private int mFarm;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("ispublic")
    private int mIsPublic;

    @SerializedName("isfriend")
    private int mIsFriend;

    @SerializedName("isfamily")
    private int mIsFamily;

    public Photo(){

    }

    public String getUrlImage(){
        return BASE_URL_IMAGE
                .replace("{farm-id}", String.valueOf(mFarm))
                .replace("{server-id}", String.valueOf(mServer))
                .replace("{id}", mId)
                .replace("{secret}", mSecret);
    }

}
