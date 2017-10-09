package com.shoji.example.android.popularmoviesstage1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.shoji.example.android.popularmoviesstage1.utils.TheMovieDbUtils;
import com.squareup.picasso.Picasso;

public class MovieDataActivity extends AppCompatActivity {
    private static final String TAG = MovieDataActivity.class.getSimpleName();

    public static final String VOTE_AVERAGE = "vote_average";
    public static final String TITLE = "title";
    public static final String POSTER_PATH = "poster_path";
    public static final String OVERVIEW = "overview";
    public static final String RELEASE_DATE = "release_date";

    private TextView mVoteAverage;
    private TextView mTitle;
    private ImageView mPosterImage;
    private TextView mOverview;
    private TextView mReleaseDate;
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_movie_data);

        mVoteAverage = (TextView) findViewById(R.id.act_movie_data_average_vote_tv);
        mTitle = (TextView) findViewById(R.id.act_movie_data_title_tv);
        mPosterImage = (ImageView) findViewById(R.id.act_movie_data_poster_image_view);
        mOverview = (TextView) findViewById(R.id.act_movie_data_overview_tv);
        mReleaseDate = (TextView) findViewById(R.id.act_movie_data_release_date_tv);

        Intent intent = getIntent();
        if(intent != null) {
            mVoteAverage.setText(getExtraFromIntent(VOTE_AVERAGE, intent));
            mTitle.setText(getExtraFromIntent(TITLE, intent));
            mOverview.setText(getExtraFromIntent(OVERVIEW, intent));
            mReleaseDate.setText(getExtraFromIntent(RELEASE_DATE, intent));

            String posterPath = getExtraFromIntent(POSTER_PATH, intent);
            Log.d(TAG, "poster_path=" +posterPath);
            loadImage(posterPath, mPosterImage);

        }
    }

    private String getExtraFromIntent(String key, Intent intent) {
        if(intent.hasExtra(key)) {
            return intent.getStringExtra(key);
        }
        return "";
    }

    public void loadImage(String posterPath, ImageView imageView) {
        String url = TheMovieDbUtils.getPosterURL(posterPath, TheMovieDbUtils.POSTER_SIZE_BIG);
        Picasso.with(this).load(url).into(imageView);
    }
}
