package com.lf.flickrexample.model.PhotoInfo;

import com.google.gson.annotations.SerializedName;
import com.lf.flickrexample.utils.Constants;

/**
 * Created by Lucas on 25/3/17.
 */

public class Owner {

    @SerializedName("nsid")
    private String mId;

    @SerializedName("username")
    private String mUsername;

    @SerializedName("location")
    private String mLocation;

    @SerializedName("iconserver")
    private int mIconServer;

    @SerializedName("iconfarm")
    private int mIconFarm;

    public String getUrlImage(){
        if(mIconServer > 0){
            return Constants.BASE_URL_ICON1
                    .replace("{icon-farm}", String.valueOf(mIconFarm))
                    .replace("{icon-server}", String.valueOf(mIconServer))
                    .replace("{nsid}", mId);
        }else{
            return Constants.BASE_URL_ICON2
                    .replace("{nsid}", mId);
        }
    }

    public String getId() {
        return mId;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getLocation() {
        return mLocation;
    }

    public int getIconServer() {
        return mIconServer;
    }

    public int getIconFarm() {
        return mIconFarm;
    }
}
