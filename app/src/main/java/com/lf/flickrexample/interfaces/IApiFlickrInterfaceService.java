package com.lf.flickrexample.interfaces;

import com.lf.flickrexample.model.PhotoInfo.PhotoInfo;
import com.lf.flickrexample.model.recentPhotos.RecentPublicPhotos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Lucas on 24/3/17.
 */

public interface IApiFlickrInterfaceService {

    @GET("/services/rest?method=flickr.photos.getRecent&format=json&nojsoncallback=1")
    Call<RecentPublicPhotos> getPublicPhotos(
            @Query("api_key") String api_key,
            @Query("per_page") int per_page);

    @GET("/services/rest?method=flickr.photos.getInfo&format=json&nojsoncallback=1")
    Call<PhotoInfo> getInfoExtra(
            @Query("api_key") String api_key,
            @Query("photo_id") String photo_id);

}
