package com.shoji.example.android.popularmoviesstage1;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MovieDataAdapter extends RecyclerView.Adapter<MovieDataViewHolder> implements MovieDataViewHolder.MovieDataViewHolderOnClickListener {
    private final static String TAG = MovieDataAdapter.class.getSimpleName();
    private MovieData[] movieData;

    private MovieDataAdapterOnClickHandler mMovieDataAdapterOnClickHandler;
    private MovieDataViewHolder.LoadUrlIntoImageViewHandler mLoadUrlIntoImageViewHandler;


    public interface MovieDataAdapterOnClickHandler {
        void onClick(MovieData movieData);
    }


    public MovieDataAdapter(
            MovieDataAdapterOnClickHandler movieDataAdapterOnClickHandler,
            MovieDataViewHolder.LoadUrlIntoImageViewHandler loadUrlIntoImageViewHandler) {
        this.mMovieDataAdapterOnClickHandler = movieDataAdapterOnClickHandler;
        this.mLoadUrlIntoImageViewHandler = loadUrlIntoImageViewHandler;
    }


    public void setMovieData(MovieData[] movieData) {
        this.movieData = movieData;
    }

    /* [START] Methods to setup the RecyclerView.Adapter */
    @Override
    public MovieDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        boolean attachToRoot = false;
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.movie_data_items, parent, attachToRoot);
        return new MovieDataViewHolder(this, mLoadUrlIntoImageViewHandler, itemView);
    }

    @Override
    public void onBindViewHolder(MovieDataViewHolder holder, int position) {
        MovieData item = movieData[position];

        holder.bindViewHolder(item);


    }

    @Override
    public int getItemCount() {
        if (movieData == null)
            return 0;

        return movieData.length;
    }
    /* [END] Methods to setup the RecyclerView.Adapter */


    @Override
    public void onClick(int position) {
        MovieData movie = movieData[position];
        //Log.d(TAG, "Tapped on position="+position);
        //Log.d(TAG, "INFO: "+movie.toString());
        mMovieDataAdapterOnClickHandler.onClick(movie);
    }



}
