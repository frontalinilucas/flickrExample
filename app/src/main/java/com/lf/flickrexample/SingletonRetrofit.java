package com.lf.flickrexample;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lucas on 24/3/17.
 */

public class SingletonRetrofit {

    private static SingletonRetrofit mSingletonRetrofit = null;
    private Retrofit mRetrofit;

    private SingletonRetrofit(){
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.FLICKR_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static SingletonRetrofit getInstance(){
        if(mSingletonRetrofit == null){
            mSingletonRetrofit = new SingletonRetrofit();
        }
        return mSingletonRetrofit;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }
}
