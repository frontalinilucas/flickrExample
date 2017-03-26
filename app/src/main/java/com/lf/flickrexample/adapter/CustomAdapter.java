package com.lf.flickrexample.adapter;

import android.app.Activity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lf.flickrexample.R;
import com.lf.flickrexample.model.recentPhotos.Photo;
import com.lf.flickrexample.utils.Constants;
import com.lf.flickrexample.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lucas on 26/3/17.
 */

public class CustomAdapter extends RecyclerView.Adapter implements Filterable {

    protected Activity mActivity;
    protected List<Photo> mListPhotos;
    protected List<Photo> mListFiltered;

    public CustomAdapter(Activity context, List<Photo> photos){
        mActivity = context;
        mListPhotos = photos;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mActivity);

        return new Holder(inflater, parent);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        Photo photo = mListPhotos.get(position);
        Holder customHolder = (Holder) holder;
        customHolder.bindPhoto(photo);
        customHolder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.startListPhotosActivity(mActivity, holder, mListPhotos.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mListPhotos != null ? mListPhotos.size() : 0);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final List<Photo> results = new ArrayList<>();
                if (mListFiltered == null)
                    mListFiltered = mListPhotos;
                if (constraint != null) {
                    if (mListFiltered != null && mListFiltered.size() > 0) {
                        for (Photo photo : mListFiltered) {
                            if(photo.containText(constraint.toString()))
                                results.add(photo);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mListPhotos = (ArrayList<Photo>) results.values;
                notifyDataSetChanged();

            }
        };
    }

    public void setListPhotos(List<Photo> photos){
        mListPhotos = photos;
    }

    class Holder extends RecyclerView.ViewHolder{

        @BindView(R.id.image)
        AppCompatImageView mImageView;

        public Holder(LayoutInflater inflater, ViewGroup container) {
            super(inflater.inflate(R.layout.item_adapter, container, false));
            ButterKnife.bind(this, itemView);
        }

        public void bindPhoto(Photo photo){
            clearImage();

            Glide.with(mImageView.getContext())
                    .load(photo.getUrlImage())
                    .centerCrop()
                    .thumbnail(Constants.THUMBNAIL_IMAGE)
                    .crossFade(Constants.CROSSFADE_TIME)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(mImageView);
        }

        private void clearImage() {
            Glide.clear(mImageView);
            mImageView.setImageDrawable(null);
        }
    }
}
