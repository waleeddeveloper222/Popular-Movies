package com.waleed.popularmovies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


public class FavoriteMovies {
    private static FavoriteMovies sMoviesManager;
    private com.waleed.popularmovies.MoviesDatabase Moviesdb;

    private FavoriteMovies(Context context) {
        Moviesdb = new MoviesDatabase(context, 1);
    }

    public static FavoriteMovies create(Context context) {
        if (sMoviesManager == null) {
            sMoviesManager = new FavoriteMovies(context);
        }
        return sMoviesManager;
    }

    public void add(Movie movie) {
        SQLiteDatabase database = Moviesdb.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", movie.getId());
        values.put("title", movie.getOriginalTitle());
        values.put("rating", movie.getUserRating());
        values.put("release_date", movie.getReleaseDate());
        values.put("plot", movie.getPlotSynopsis());
        values.put("poster_url", movie.getPosterUrl());

        database.insert("movie", null, values);
        database.close();
    }

    public void remove(Movie movie) {


        SQLiteDatabase database = Moviesdb.getWritableDatabase();
        int result = database.delete("movie", "id = " + movie.getId(), null);

        database.close();
    }

    public boolean isFavorite(Movie movie) {
        SQLiteDatabase database = Moviesdb.getWritableDatabase();
        Cursor cursor = database.query("movie", null, null, null, null, null, null);
        int columnIndex = cursor.getColumnIndex("id");
        while (cursor.moveToNext()) {
            if (cursor.getInt(columnIndex) == movie.getId()) {
                cursor.close();
                return true;
            }
        }
        cursor.close();
        return false;
    }

    public List<Movie> getMovies() {
        List<Movie> movies = new ArrayList<>();
        SQLiteDatabase database = Moviesdb.getWritableDatabase();
        Cursor cursor = database.query("movie", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Movie movie = new Movie(cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getDouble(cursor.getColumnIndex("rating")),
                    cursor.getString(cursor.getColumnIndex("release_date")),
                    cursor.getString(cursor.getColumnIndex("plot")),
                    cursor.getString(cursor.getColumnIndex("poster_url")),
                    cursor.getInt(cursor.getColumnIndex("id")));
            movies.add(movie);
        }
        cursor.close();
        return movies;
    }

    private class MoviesDatabase extends com.waleed.popularmovies.MoviesDatabase {

        public MoviesDatabase(Context context, int version) {
            super(context, "movie.Moviesdb", null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("CREATE TABLE movie(_ID INT PRIMARY KEY, id, title, rating, release_date, plot, poster_url);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            // upgrade is not supported yet
        }
    }

}
