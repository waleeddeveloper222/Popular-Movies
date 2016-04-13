package com.waleed.popularmovies.connection;

import com.waleed.popularmovies.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ParseData {

    public static final String RESULTS_KEY = "results";
    public static final String ORIGINAL_TITLE_KEY = "original_title";
    public static final String VOTE_AVERAGE_KEY = "vote_average";
    public static final String RELEASE_DATE_KEY = "release_date";
    public static final String OVERVIEW_KEY = "overview";
    public static final String POSTER_PATH_KEY = "poster_path";
    public static final String TOTAL_PAGES_KEY = "total_pages";
    public static final String TOTAL_RESULTS_KEY = "total_results";
    public static final String PAGE_NUMBER_KEY = "page";
    public static final String RESULTS_kEY = "results";
    public static final String ID = "id";

    private final JSONObject jsonObject;

    public ParseData(String data) throws Exception {
        this.jsonObject = new JSONObject(data);
    }


    public int getCurrentPageNumber() throws Exception {
        return jsonObject.getInt(PAGE_NUMBER_KEY);
    }

    public int getTotalNumberOfPages() throws Exception {
        return jsonObject.getInt(TOTAL_PAGES_KEY);
    }

    public int getTotalNumberOfResults() throws Exception {
        return jsonObject.getInt(TOTAL_RESULTS_KEY);
    }

    public int getNumberOfResultsInCurrentPage() throws Exception {
        JSONArray results = jsonObject.getJSONArray(RESULTS_KEY);
        return results.length();
    }

    public Movie getMovie(int id) throws Exception {
        JSONArray results = jsonObject.getJSONArray(RESULTS_kEY);
        JSONObject movieJsonObject = results.getJSONObject(id);
        return new Movie(movieJsonObject.getString(ORIGINAL_TITLE_KEY),
                movieJsonObject.getDouble(VOTE_AVERAGE_KEY),
                movieJsonObject.getString(RELEASE_DATE_KEY),
                movieJsonObject.getString(OVERVIEW_KEY),
                movieJsonObject.getString(POSTER_PATH_KEY), movieJsonObject.getInt(ID));
    }

    public List<Movie> getMovies() throws Exception {
        List<Movie> movies = new ArrayList<>();
        JSONArray results = jsonObject.getJSONArray(RESULTS_kEY);
        for (int i = 0; i < results.length(); i++) {
            JSONObject movieJsonObject = results.getJSONObject(i);
            Movie movie = new Movie(movieJsonObject.getString(ORIGINAL_TITLE_KEY),
                    movieJsonObject.getDouble(VOTE_AVERAGE_KEY),
                    movieJsonObject.getString(RELEASE_DATE_KEY),
                    movieJsonObject.getString(OVERVIEW_KEY),
                    movieJsonObject.getString(POSTER_PATH_KEY), movieJsonObject.getInt(ID));
            movies.add(movie);
        }
        return movies;
    }
}
