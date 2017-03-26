package com.lf.flickrexample.adapter;

import android.app.Activity;
import android.content.Intent;
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
import com.lf.flickrexample.ui.activity.PhotoDetailsActivity;
import com.lf.flickrexample.utils.Constants;
import com.lf.flickrexample.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lucas on 25/3/17.
 */

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.GridHolder> implements Filterable {

    private Activity mActivity;
    private List<Photo> mListPhotos;
    private List<Photo> mListFiltered;

    public GridAdapter(Activity context, List<Photo> photos){
        mActivity = context;
        mListPhotos = photos;
    }

    public void setListPhotos(List<Photo> photos){
        mListPhotos = photos;
    }

    @Override
    public GridHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mActivity);

        return new GridHolder(inflater, parent);
    }

    @Override
    public void onBindViewHolder(final GridHolder holder, int position) {
        Photo photo = mListPhotos.get(position);
        holder.bindPhoto(photo);
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = PhotoDetailsActivity.getIntent(mActivity, mListPhotos.get(holder.getAdapterPosition()));
                mActivity.startActivity(intent);
                Utils.addStartTransitionAnimation(mActivity);
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

    class GridHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.image)
        AppCompatImageView mImageView;

        public GridHolder(LayoutInflater inflater, ViewGroup container) {
            super(inflater.inflate(R.layout.list_item_image, container, false));
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
