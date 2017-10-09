package com.shoji.example.android.popularmoviesstage1;

public class MovieData {
    private String vote_average;
    private String title;
    private String poster_path;
    private String overview;
    private String release_date;

    public String getVoteAverage() {
        return vote_average;
    }

    public void setVoteAverage(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return poster_path;
    }

    public void setPosterPath(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return release_date;
    }

    public void setReleaseDate(String release_date) {
        this.release_date = release_date;
    }

    @Override
    public String toString() {
        return "Title: " + title +
                "\nPoster Path: " + poster_path +
                "\nOverview: " + overview +
                "\nVote Average: "+ vote_average +
                "\nRelease Date: "+ release_date;
    }
}
