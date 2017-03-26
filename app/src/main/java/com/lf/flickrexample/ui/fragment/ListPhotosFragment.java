package com.lf.flickrexample.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private SearchView mSearchView;

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

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

        mApiService = SingletonRetrofit.getInstance()
                .getRetrofit()
                .create(IApiFlickrInterfaceService.class);

        getRecentPhotos();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRecentPhotos();
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if(menu.findItem(R.id.action_search) == null){
            getActivity().getMenuInflater().inflate(R.menu.menu_listphotos, menu);
            mSearchView = (SearchView)menu.findItem(R.id.action_search).getActionView();
            setupSearchView();
        }
    }

    private void setupSearchView() {
        mSearchView.setIconified(false);
        try{
            mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });

            mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    return false;
                }
            });
        }catch(Exception e){

        }
        mSearchView.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        mSearchView.clearFocus();
    }

    private void getRecentPhotos() {
        Call<RecentPublicPhotos> callPublicPhotos = mApiService.getPublicPhotos(getString(R.string.flickrApiKey), Constants.PHOTOS_PER_PAGE);
        callPublicPhotos.enqueue(new Callback<RecentPublicPhotos>() {
            @Override
            public void onResponse(Call<RecentPublicPhotos> call, Response<RecentPublicPhotos> response) {
                if(response.code() == Constants.CODE_RESULT_OK){
                    RecentPublicPhotos photos = response.body();
                    updatePhotos(photos.getPhotos().getListPhotos());
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<RecentPublicPhotos> call, Throwable t) {
                //TODO. Mostrar error
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void updatePhotos(List<Photo> listPhotos) {
        mGridAdapter.setListPhotos(listPhotos);
        mGridAdapter.notifyDataSetChanged();
    }

}
