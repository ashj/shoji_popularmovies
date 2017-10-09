package com.shoji.example.android.popularmoviesstage1.utils;

import android.util.Log;

import com.shoji.example.android.popularmoviesstage1.MovieData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class TheMovieDbJsonUtils {
    private static final String TAG = TheMovieDbJsonUtils.class.getSimpleName();


    public static MovieData[] parseTheMovieDbJson(String jsonStr)
        throws JSONException {

        final String JSON_PAGE = "page";

        final String JSON_RESULTS = "results";

        final String JSON_VOTE_AVERAGE = "vote_average";
        final String JSON_TITLE = "title";
        final String JSON_POSTER_PATH = "poster_path";

        final String JSON_OVERVIEW = "overview";
        final String JSON_RELEASE_DATE = "release_date";


        Log.d(TAG, "Starting parseTheMovieDbJson");
        JSONObject movieJson = new JSONObject(jsonStr);

        MovieData[] movieData = null;

        if(movieJson.has(JSON_RESULTS)) {
            JSONArray resultsArray = movieJson.getJSONArray(JSON_RESULTS);

            Log.d(TAG, "Total result in this page=" + resultsArray.length());
            movieData = new MovieData[resultsArray.length()];

            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject movie = resultsArray.getJSONObject(i);

                movieData[i] = new MovieData();
                movieData[i].setVoteAverage(movie.getString(JSON_VOTE_AVERAGE));
                movieData[i].setTitle(movie.getString(JSON_TITLE));
                movieData[i].setPosterPath(movie.getString(JSON_POSTER_PATH));
                movieData[i].setOverview(movie.getString(JSON_OVERVIEW));
                movieData[i].setReleaseDate(movie.getString(JSON_RELEASE_DATE));

                //Log.d(TAG, "Parsed: " + movieData[i]);
            }
        }

        return movieData;

    }

}
