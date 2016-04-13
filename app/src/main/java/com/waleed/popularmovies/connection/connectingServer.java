package com.waleed.popularmovies.connection;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import com.waleed.popularmovies.Movie;
import com.waleed.popularmovies.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class connectingServer {

    private static final String TAG = "ServerConnector";
    private Context context;
    private final String apiKay;
    private static final int SERVER_PAGE_SIZE = 20;


    public connectingServer(Context context) {
        this.context = context;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.apiKay = sharedPreferences.getString("api-key", context.getString(R.string.server_api_key));
    }

    public String getData() throws  Exception {
        String baseUrl = this.context.getString(R.string.server_base_url);
        // TODO: store string constants in resource file(s)
        Uri uri = Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter("sort_by", "popularity.desc")
                .appendQueryParameter("api_key", this.apiKay).build();

        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(uri.toString()).openConnection();
        httpURLConnection.connect();

        int responseCode;
        try {
            responseCode = httpURLConnection.getResponseCode();
        } catch (IOException e) {
            responseCode = httpURLConnection.getResponseCode();
        }

        switch (responseCode) {
            case HttpURLConnection.HTTP_OK:
                InputStream inputStream = httpURLConnection.getInputStream();
                StringBuilder stringBuilder = new StringBuilder();

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                    stringBuilder.append("\n");
                }
                return stringBuilder.toString();
            case HttpURLConnection.HTTP_UNAUTHORIZED:
                throw new Exception();
            default:
                throw new IllegalStateException("Connection method is not equipped to handle this case");
        }
    }

    public String getPage(int page, int sortCriteria) throws IOException, Exception {
        String baseUrl = this.context.getString(R.string.server_base_url);
        // TODO: store string constants in resource file(s)
        Uri uri = Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter("sort_by", sortCriteria == 0 ? "popularity.desc" : "vote_average.desc")
                .appendQueryParameter("api_key", this.apiKay)
                .appendQueryParameter("page", String.valueOf(page))
                .build();
        Log.d(TAG, "Loading uri: " + uri.toString());
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(uri.toString()).openConnection();
        httpURLConnection.connect();

        int responseCode;
        try {
            responseCode = httpURLConnection.getResponseCode();
        } catch (IOException e) {
            responseCode = httpURLConnection.getResponseCode();
        }

        switch (responseCode) {
            case HttpURLConnection.HTTP_OK:
                InputStream inputStream = httpURLConnection.getInputStream();
                StringBuilder stringBuilder = new StringBuilder();

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                    stringBuilder.append("\n");
                }
                return stringBuilder.toString();
            case HttpURLConnection.HTTP_UNAUTHORIZED:
                throw new Exception();
            default:

                throw new IllegalStateException("Connection method is not equipped to handle this case");
        }
    }

    public List<Movie> getMovies(int page, int pageSize, int sortCriteria) throws  Exception {

        int numberOfServerPagesPerResult = pageSize / SERVER_PAGE_SIZE;

        int firstRequiredPage = (page - 1) * numberOfServerPagesPerResult + 1;
        int lastRequiredPage = page * numberOfServerPagesPerResult;

        List<Movie> movies = new ArrayList<>();
        for (int i = firstRequiredPage; i <= lastRequiredPage; i++) {
            String pageData = getPage(i, sortCriteria);
            movies.addAll(new ParseData(pageData).getMovies());
        }
        return movies;
    }
}
