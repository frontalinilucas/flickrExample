package com.lf.flickrexample.model.PhotoInfo;

import com.google.gson.annotations.SerializedName;
import com.lf.flickrexample.utils.Constants;

import java.util.Date;

/**
 * Created by Lucas on 25/3/17.
 */

public class Dates {

    @SerializedName("posted")
    private long mPosted;

    @SerializedName("taken")
    private String mTaken;

    public String getTaken() {
        return mTaken;
    }

    public String getDate(){
        String stringDate = "";
        try{
            Date date = Constants.FORMAT_TAKEN_PROFILE.parse(getTaken());
            stringDate = Constants.FORMAT_DATE.format(date);
        }catch(Exception e){

        }
        return stringDate;
    }
}
