package com.lf.flickrexample.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lf.flickrexample.R;
import com.lf.flickrexample.model.Photo;
import com.lf.flickrexample.utils.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lucas on 25/3/17.
 */

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.GridHolder> {

    private Context mContext;
    private List<Photo> mListPhotos;

    public GridAdapter(Context context, List<Photo> photos){
        mContext = context;
        mListPhotos = photos;
    }

    public void setListPhotos(List<Photo> photos){
        mListPhotos = photos;
    }

    @Override
    public GridHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        return new GridHolder(inflater, parent);
    }

    @Override
    public void onBindViewHolder(GridHolder holder, int position) {
        Photo photo = mListPhotos.get(position);
        holder.bindPhoto(photo);
    }

    @Override
    public int getItemCount() {
        return (mListPhotos != null ? mListPhotos.size() : 0);
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
