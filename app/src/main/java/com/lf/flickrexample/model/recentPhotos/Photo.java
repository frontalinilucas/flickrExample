package com.lf.flickrexample.model.recentPhotos;

import com.google.gson.annotations.SerializedName;
import com.lf.flickrexample.utils.Constants;

import java.io.Serializable;

/**
 * Created by Lucas on 24/3/17.
 */

public class Photo implements Serializable {

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

    public String getId() {
        return mId;
    }

    public String getOwner() {
        return mOwner;
    }

    public String getSecret() {
        return mSecret;
    }

    public int getServer() {
        return mServer;
    }

    public int getFarm() {
        return mFarm;
    }

    public String getTitle() {
        return mTitle;
    }

    public int getIsPublic() {
        return mIsPublic;
    }

    public int getIsFriend() {
        return mIsFriend;
    }

    public int getIsFamily() {
        return mIsFamily;
    }

    public String getUrlImage(){
        return Constants.BASE_URL_IMAGE
                .replace("{farm-id}", String.valueOf(mFarm))
                .replace("{server-id}", String.valueOf(mServer))
                .replace("{id}", mId)
                .replace("{secret}", mSecret);
    }

    public boolean containText(String searchText) {
        return getTitle().toLowerCase().contains(searchText);
    }
}
