package com.shoji.example.android.popularmoviesstage1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.shoji.example.android.popularmoviesstage1.utils.TheMovieDbJsonUtils;
import com.shoji.example.android.popularmoviesstage1.utils.TheMovieDbUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, MovieDataAdapter.MovieDataAdapterOnClickHandler, MovieDataViewHolder.LoadUrlIntoImageViewHandler {
    private final static String TAG = MainActivity.class.getSimpleName();
    private TextView textView;

    private ProgressBar mLoadingMovieDataProgressBar;

    private RecyclerView mMovieDataRecyclerView;
    private MovieDataAdapter mMovieDataAdapter;


    private enum SortCriterion {
        BY_POPULARITY,
        BY_TOP_RATED
    }

    private Spinner sortCriterionSpinner;
    private SortCriterion mSortCriterion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSortCriterion = SortCriterion.BY_POPULARITY;

        textView = (TextView) findViewById(R.id.hello_text);
        mLoadingMovieDataProgressBar = (ProgressBar) findViewById(R.id.pb_loading_progress);

        createMovieDataRecyclerView();
        createSortCriterionSpinner();

        doFetchMovieData();

    }

    private void createSortCriterionSpinner() {
        sortCriterionSpinner = (Spinner) findViewById(R.id.sort_criterion_spinner);
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this,
                        R.array.sort_criterion_array,
                        android.R.layout.simple_spinner_dropdown_item);
        sortCriterionSpinner.setAdapter(adapter);
        sortCriterionSpinner.setOnItemSelectedListener(this);
    }

    private void createMovieDataRecyclerView() {
        mMovieDataRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movies);

        Integer numCols = this.getResources().getInteger(R.integer.act_main_gridlayoutmanager_num_cols);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, numCols);
        mMovieDataRecyclerView.setLayoutManager(gridLayoutManager);

        mMovieDataRecyclerView.setHasFixedSize(true);

        /* set the adapter */
        mMovieDataAdapter = new MovieDataAdapter(this, this);
        mMovieDataRecyclerView.setAdapter(mMovieDataAdapter);
    }

    private void doFetchMovieData() {
        new FetchMovieData().execute(mSortCriterion);
    }


    public class FetchMovieData extends AsyncTask<SortCriterion, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            textView.setText(null);
            mLoadingMovieDataProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(SortCriterion... params) {
            SortCriterion criterion = params[0];
            String movieData = null;

            Log.d(TAG, "Will get data for criterion: " + criterion);
            if (criterion == SortCriterion.BY_POPULARITY) {
                if (isNetworkConnected())
                    movieData = TheMovieDbUtils.getMoviePopular();
            }
            else if (criterion == SortCriterion.BY_TOP_RATED) {
                if (isNetworkConnected())
                    movieData = TheMovieDbUtils.getMovieTopRated();
            }

            return movieData;
        }

        @Override
        protected void onPostExecute(String movieDataJson) {
            mLoadingMovieDataProgressBar.setVisibility(View.INVISIBLE);
            super.onPostExecute(movieDataJson);
            //Log.d(TAG, "movieData: "+movieDataJson);

            try {
                if(movieDataJson != null) {
                    MovieData[] movieData = TheMovieDbJsonUtils.parseTheMovieDbJson(movieDataJson);

                    /* Set the RecyclerView adapter here */
                    mMovieDataAdapter.setMovieData(movieData);
                    mMovieDataRecyclerView.setAdapter(mMovieDataAdapter);

                }
                else {
                    textView.setText(R.string.error_null_json_returned);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null) {
            // no internet connection
            return false;
        } else
            return true;
    }

/* [START] Spinner handling methods */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "Spinner, selected position="+position);
        switch (position) {
            case 0:
                mSortCriterion = SortCriterion.BY_POPULARITY;
                doFetchMovieData();
                break;
            case 1:
                mSortCriterion = SortCriterion.BY_TOP_RATED;
                doFetchMovieData();
                break;

            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Do nothing
    }
/* [END] Spinner handling methods */

    @Override
    public void onClick(MovieData movieData) {
        Log.d(TAG, "At main: "+movieData);
        Context context = this;
        Class destinationClass = MovieDataActivity.class;
        Intent intent = new Intent(context, destinationClass);

        intent.putExtra(MovieDataActivity.VOTE_AVERAGE, movieData.getVoteAverage());
        intent.putExtra(MovieDataActivity.TITLE, movieData.getTitle());
        intent.putExtra(MovieDataActivity.POSTER_PATH, movieData.getPosterPath());
        intent.putExtra(MovieDataActivity.OVERVIEW, movieData.getOverview());
        intent.putExtra(MovieDataActivity.RELEASE_DATE, movieData.getReleaseDate());

        startActivity(intent);
    }

    @Override
    public void loadImage(String posterPath, ImageView imageView) {
        String url = TheMovieDbUtils.getPosterURL(posterPath, TheMovieDbUtils.POSTER_SIZE_BIG);

        Picasso.with(this).load(url).into(imageView);
    }
}
