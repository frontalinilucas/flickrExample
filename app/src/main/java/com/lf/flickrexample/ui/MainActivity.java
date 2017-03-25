package com.lf.flickrexample.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lf.flickrexample.R;
import com.lf.flickrexample.SingletonRetrofit;
import com.lf.flickrexample.adapter.GridAdapter;
import com.lf.flickrexample.interfaces.IApiFlickrInterfaceService;
import com.lf.flickrexample.model.Photo;
import com.lf.flickrexample.model.RecentPublicPhotos;
import com.lf.flickrexample.utils.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private IApiFlickrInterfaceService mApiService;
    private List<Photo> mListPhotos;
    private GridAdapter mGridAdapter;

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mGridAdapter = new GridAdapter(this, mListPhotos);
        mRecyclerview.setAdapter(mGridAdapter);
        mRecyclerview.setLayoutManager(new GridLayoutManager(this, Constants.GRID_COLUMNS));

        getRecentPhotos();
    }

    private void getRecentPhotos() {
        mApiService = SingletonRetrofit.getInstance()
                .getRetrofit()
                .create(IApiFlickrInterfaceService.class);

        Call<RecentPublicPhotos> callPublicPhotos = mApiService.getPublicPhotos(getString(R.string.flickrApiKey), Constants.PHOTOS_PER_PAGE);
        callPublicPhotos.enqueue(new Callback<RecentPublicPhotos>() {
            @Override
            public void onResponse(Call<RecentPublicPhotos> call, Response<RecentPublicPhotos> response) {
                int statusCode = response.code();
                RecentPublicPhotos photos = response.body();

                updatePhotos(photos.getPhotos().getListPhotos());
            }

            @Override
            public void onFailure(Call<RecentPublicPhotos> call, Throwable t) {
                //TODO. Mostrar error
            }
        });
    }

    private void updatePhotos(List<Photo> listPhotos) {
        mGridAdapter.setListPhotos(listPhotos);
        mGridAdapter.notifyDataSetChanged();
    }
}
