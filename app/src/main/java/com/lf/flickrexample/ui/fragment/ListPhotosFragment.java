package com.lf.flickrexample.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import com.lf.flickrexample.adapter.CustomAdapter;
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
    private CustomAdapter mAdapter;
    private SearchView mSearchView;

    private Menu mMenu;
    private boolean mIsGridVisible;
    private List<Photo> mListPhotos;

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
        mAdapter = new CustomAdapter(getActivity(), null);
        mRecyclerview.setAdapter(mAdapter);
        mRecyclerview.setLayoutManager(new GridLayoutManager(getActivity(), Constants.GRID_COLUMNS));
        mIsGridVisible = true;

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_change_layoutmanager:
                mMenu.findItem(R.id.action_search).collapseActionView();
                if(mIsGridVisible){
                    //List
                    mIsGridVisible = false;
                    item.setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_grid));
                    mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                }else{
                    //Grid
                    mIsGridVisible = true;
                    item.setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_format));
                    mRecyclerview.setLayoutManager(new GridLayoutManager(getActivity(), Constants.GRID_COLUMNS));
                }
                mAdapter = new CustomAdapter(getActivity(), null);
                mRecyclerview.setAdapter(mAdapter);
                updatePhotos();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if(menu.findItem(R.id.action_search) == null){
            mMenu = menu;
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
                    queryTextChanged(newText);
                    return false;
                }
            });

            mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    queryTextChanged("");
                    return false;
                }
            });
        }catch(Exception e){

        }
        mSearchView.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        mSearchView.clearFocus();
    }

    private void queryTextChanged(String searchText) {
        mAdapter.getFilter().filter(searchText);
    }

    private void getRecentPhotos() {
        Call<RecentPublicPhotos> callPublicPhotos = mApiService.getPublicPhotos(getString(R.string.flickrApiKey), Constants.PHOTOS_PER_PAGE);
        callPublicPhotos.enqueue(new Callback<RecentPublicPhotos>() {
            @Override
            public void onResponse(Call<RecentPublicPhotos> call, Response<RecentPublicPhotos> response) {
                if(response.code() == Constants.CODE_RESULT_OK){
                    RecentPublicPhotos photos = response.body();
                    mListPhotos = photos.getPhotos().getListPhotos();
                    updatePhotos();
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

    private void updatePhotos() {
        mAdapter.setListPhotos(mListPhotos);
        mAdapter.notifyDataSetChanged();
    }

}
