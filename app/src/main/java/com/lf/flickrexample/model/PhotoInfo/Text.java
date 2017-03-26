package com.lf.flickrexample.model.PhotoInfo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Lucas on 25/3/17.
 */

public class Text implements Serializable {

    @SerializedName("_content")
    private String mTexto;

    public String getTexto() {
        return mTexto;
    }
}
