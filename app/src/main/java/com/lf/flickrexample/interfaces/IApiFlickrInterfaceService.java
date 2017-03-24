package com.lf.flickrexample.interfaces;

import com.lf.flickrexample.model.RecentPublicPhotos;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Lucas on 24/3/17.
 */

public interface IApiFlickrInterfaceService {

    @POST("/services/rest?method=flickr.photos.getRecent&format=json&nojsoncallback=1")
    Call<RecentPublicPhotos> getPublicPhotos(@Query("api_key") String api_key);

}
