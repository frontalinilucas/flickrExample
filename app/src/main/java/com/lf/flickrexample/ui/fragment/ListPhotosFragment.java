package com.lf.flickrexample.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
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
import com.lf.flickrexample.model.recentPhotos.Photos;
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
    private Photos mPhotos;
    private int mPageView;

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.imgPagerBack)
    AppCompatImageView mImgPagerBack;

    @BindView(R.id.textPagerTitle)
    AppCompatTextView mTextPagerTitle;

    @BindView(R.id.imgPagerProceed)
    AppCompatImageView mImgPagerProceed;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.fragment_listphotos, container, false);
        ButterKnife.bind(this, view);
        setPageView(1);

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
                setPageView(1);
                getRecentPhotos();
            }
        });

        mImgPagerBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPageView(mPageView - 1);
                clickPager();
            }
        });

        mImgPagerProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPageView(mPageView + 1);
                clickPager();
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
        Call<RecentPublicPhotos> callPublicPhotos = mApiService.getPublicPhotos(getString(R.string.flickrApiKey), Constants.PHOTOS_PER_PAGE, mPageView);
        callPublicPhotos.enqueue(new Callback<RecentPublicPhotos>() {
            @Override
            public void onResponse(Call<RecentPublicPhotos> call, Response<RecentPublicPhotos> response) {
                if(response.code() == Constants.CODE_RESULT_OK){
                    RecentPublicPhotos photos = response.body();
                    mPhotos = photos.getPhotos();
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
        updatePagerTitle();
    }

    private void updatePhotos() {
        mAdapter.setListPhotos(mPhotos.getListPhotos());
        mAdapter.notifyDataSetChanged();
    }

    private void updatePagerTitle() {
        mTextPagerTitle.setText(getString(R.string.numberPage, mPageView));
    }

    private void clickPager() {
        mSwipeRefreshLayout.setRefreshing(true);
        getRecentPhotos();
    }

    private void setPageView(int pageView) {
        //mImgPagerBack.setEnabled(false);
        mImgPagerBack.setVisibility(View.VISIBLE);
        mImgPagerProceed.setVisibility(View.VISIBLE);
        if(pageView <= 1) {
            mPageView = 1;
            mImgPagerBack.setVisibility(View.INVISIBLE);
        }else if(mPhotos != null && pageView >= mPhotos.getPages()) {
            mPageView = mPhotos.getPages();
            mImgPagerProceed.setVisibility(View.INVISIBLE);
        }else {
            mPageView = pageView;
        }
    }
}
