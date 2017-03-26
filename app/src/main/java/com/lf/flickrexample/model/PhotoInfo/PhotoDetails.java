package com.lf.flickrexample.model.PhotoInfo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Lucas on 25/3/17.
 */

public class PhotoDetails implements Serializable {

    @SerializedName("id")
    private String mId;

    @SerializedName("dates")
    private Dates mDates;

    @SerializedName("owner")
    private Owner mOwner;

    @SerializedName("title")
    private Text mTitle;

    @SerializedName("description")
    private Text mDescription;

    public String getId() {
        return mId;
    }

    public Dates getDates() {
        return mDates;
    }

    public Owner getOwner() {
        return mOwner;
    }

    public String getTitle() {
        return mTitle.getTexto();
    }

    public String getDescription() {
        return mDescription.getTexto();
    }
}
