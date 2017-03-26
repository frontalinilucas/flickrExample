package com.lf.flickrexample.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lf.flickrexample.R;
import com.lf.flickrexample.SingletonRetrofit;
import com.lf.flickrexample.adapter.GridAdapter;
import com.lf.flickrexample.interfaces.IApiFlickrInterfaceService;
import com.lf.flickrexample.model.recentPhotos.Photo;
import com.lf.flickrexample.model.recentPhotos.RecentPublicPhotos;
import com.lf.flickrexample.utils.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Lucas on 25/3/17.
 */

public class ListPhotosFragment extends Fragment {

    private IApiFlickrInterfaceService mApiService;
    private GridAdapter mGridAdapter;

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.fragment_listphotos, container, false);
        ButterKnife.bind(this, view);

        //TODO: Agregar placeholder si no hay imagenes
        mGridAdapter = new GridAdapter(getActivity(), null);
        mRecyclerview.setAdapter(mGridAdapter);
        mRecyclerview.setLayoutManager(new GridLayoutManager(getActivity(), Constants.GRID_COLUMNS));

        getRecentPhotos();

        return view;
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
