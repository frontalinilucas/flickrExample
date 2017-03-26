package com.lf.flickrexample.model.PhotoInfo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Lucas on 25/3/17.
 */

public class Text {

    @SerializedName("_content")
    private String mTexto;

    public String getTexto() {
        return mTexto;
    }
}
