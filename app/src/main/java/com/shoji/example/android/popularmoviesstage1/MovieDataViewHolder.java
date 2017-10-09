package com.shoji.example.android.popularmoviesstage1;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.shoji.example.android.popularmoviesstage1.utils.TheMovieDbUtils;


public class MovieDataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final static String TAG = MovieDataViewHolder.class.getSimpleName();
    private MovieData mMovieData;

    private MovieDataViewHolderOnClickListener mMovieDataViewHolderOnClickListener;
    private LoadUrlIntoImageViewHandler mLoadUrlIntoImageViewHandler;

    private ImageView mPoster_image;


    public interface MovieDataViewHolderOnClickListener {
        void onClick(int position);
    }

    public MovieDataViewHolder(
            MovieDataViewHolderOnClickListener movieDataViewHolderOnClickListener,
            LoadUrlIntoImageViewHandler loadUrlIntoImageViewHandler,
            View itemView) {
        super(itemView);

        mPoster_image = itemView.findViewById(R.id.movie_poster_image_view);

        itemView.setOnClickListener(this);
        mMovieDataViewHolderOnClickListener = movieDataViewHolderOnClickListener;
        mLoadUrlIntoImageViewHandler = loadUrlIntoImageViewHandler;
    }

    public void bindViewHolder(MovieData movieData) {
        mMovieData = movieData;

        mLoadUrlIntoImageViewHandler.loadImage(
                mMovieData.getPosterPath(),
                mPoster_image);
    }

    public interface LoadUrlIntoImageViewHandler {
        void loadImage(String posterPath, ImageView imageView);
    }

@Override
    public void onClick(View view) {
        int adapterPosition = getAdapterPosition();
        //Log.d(TAG, "Tapped on position="+adapterPosition);

        mMovieDataViewHolderOnClickListener.onClick(adapterPosition);
    }
}
