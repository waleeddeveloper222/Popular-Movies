package com.waleed.popularmovies.list;

import com.waleed.popularmovies.Movie;

import java.util.List;



public interface DataSetUpdateListener {
    public void onDataSetUpdated(List<Movie> movies);
}
