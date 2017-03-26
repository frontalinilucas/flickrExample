package com.lf.flickrexample.model.recentPhotos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Lucas on 24/3/17.
 */

public class Photos {

    @SerializedName("photo")
    private List<Photo> mPhotos;

    @SerializedName("page")
    private int mPage;

    @SerializedName("pages")
    private int mPages;

    @SerializedName("perpage")
    private int mPerPage;

    @SerializedName("total")
    private int mTotal;

    public Photos(){

    }

    public List<Photo> getListPhotos(){
        return mPhotos;
    }

    public List<Photo> getPhotos() {
        return mPhotos;
    }

    public int getPage() {
        return mPage;
    }

    public int getPages() {
        return mPages;
    }

    public int getPerPage() {
        return mPerPage;
    }

    public int getTotal() {
        return mTotal;
    }
}
