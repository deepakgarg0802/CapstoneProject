package com.example.deepakgarg.capstoneproject;

/**
 * Created by Deepak Garg on 26-03-2017.
 */

public class NewsData {

    private final String moviePoster;
    private final int movieId;

    public NewsData(String moviePoster, int movieId) {
        this.moviePoster = moviePoster;
        this.movieId = movieId;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public int getMovieId() {
        return movieId;
    }

}
