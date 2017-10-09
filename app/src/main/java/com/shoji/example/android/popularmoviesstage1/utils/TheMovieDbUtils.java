package com.shoji.example.android.popularmoviesstage1.utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class TheMovieDbUtils {
    private static final String TAG = TheMovieDbUtils.class.getSimpleName();;

    private final static String BASE_POSTER_URL = "http://image.tmdb.org/t/p/";

    private final static String BASE_POSTER_SIZE_W342 = "w342";
    private final static String BASE_POSTER_SIZE_W185 = "w185";
    public final static int POSTER_SIZE_SMALL = 0;
    public final static int POSTER_SIZE_BIG = 1;

    private final static String API_BASE_URL = "https://api.themoviedb.org/3/";

    private final static String MOVIE_POPULAR = "movie/popular";
    private final static String MOVIE_TOP_RATED = "movie/top_rated";

    private final static String QUERY_AUTHKEY = "api_key";
    // COMPLETED 01a: Delete this when submitting source code.
    // TODO 01b: Use your own AUTH KEY
    private final static String QUERY_AUTHKEY_VALUE = "";


    public static String getPosterURL(String posterPath) {
        return getPosterURL(posterPath, POSTER_SIZE_SMALL);
    }
    public static String getPosterURL(String posterPath, int posterSize) {
        String posterSizeStr = BASE_POSTER_SIZE_W185;
        switch(posterSize) {
            case POSTER_SIZE_SMALL:
                break;

            case POSTER_SIZE_BIG:
                posterSizeStr = BASE_POSTER_SIZE_W342;
                break;
            default:
                break;
        }
        return BASE_POSTER_URL + posterSizeStr + "/" + posterPath;
    }


    private static URL simpleURLBuilder(String criterion) {
        Uri builtUri = Uri.parse(API_BASE_URL + criterion).buildUpon()
                .appendQueryParameter(QUERY_AUTHKEY, QUERY_AUTHKEY_VALUE)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;

    }

    private static String getMovieData(String criterion) {
        String movieData = null;

        try {
            URL url = simpleURLBuilder(criterion);
            if (url != null)
                movieData = NetworkUtils.getResponseFromHttpUrl(url);
        } catch (IOException e) {
            //e.printStackTrace();
            return null;
        }
        return movieData;

    }

    public static String getMoviePopular() {
        return getMovieData(MOVIE_POPULAR);
    }
    public static String getMovieTopRated() {
        return getMovieData(MOVIE_TOP_RATED);
    }

}
