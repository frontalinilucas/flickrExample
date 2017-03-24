package com.lf.flickrexample.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lf.flickrexample.R;
import com.lf.flickrexample.SingletonRetrofit;
import com.lf.flickrexample.interfaces.IApiFlickrInterfaceService;
import com.lf.flickrexample.model.RecentPublicPhotos;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private IApiFlickrInterfaceService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getPhotos();
    }

    private void getPhotos() {
        mApiService = SingletonRetrofit.getInstance()
                .getRetrofit()
                .create(IApiFlickrInterfaceService.class);

        Call<RecentPublicPhotos> callPublicPhotos = mApiService.getPublicPhotos(getString(R.string.flickrApiKey));
        callPublicPhotos.enqueue(new Callback<RecentPublicPhotos>() {
            @Override
            public void onResponse(Call<RecentPublicPhotos> call, Response<RecentPublicPhotos> response) {
                int statusCode = response.code();
                RecentPublicPhotos photos = response.body();
            }

            @Override
            public void onFailure(Call<RecentPublicPhotos> call, Throwable t) {
                //TODO. Mostrar error
            }
        });
    }
}
