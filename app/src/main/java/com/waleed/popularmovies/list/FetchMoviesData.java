package com.waleed.popularmovies.list;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.waleed.popularmovies.Movie;
import com.waleed.popularmovies.R;
import com.waleed.popularmovies.connection.connectingServer;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FetchMoviesData extends AsyncTask<Void, Void, List<Movie>> {

    private static final int PAGE_NUMBER_1 = 1;
    private Activity mActivity;
    private ProgressDialog pd;
    private boolean unauthorizedExceptionOccured = false;
    private DataSetUpdateListener listener;
    private int mSortCriteria = 0;

    public FetchMoviesData(Activity activity, DataSetUpdateListener listener, int sortCriteria) {
        mActivity = activity;
        this.mSortCriteria = sortCriteria;
        this.listener = listener;
    }

    public FetchMoviesData(Activity activity, DataSetUpdateListener listener) {
        mActivity = activity;
        this.mSortCriteria = 0;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(mActivity);
        pd.setTitle(mActivity.getString(R.string.dialog_progress_title));
        pd.setMessage(mActivity.getString(R.string.dialog_progress_message));
        pd.setCancelable(false);
        pd.setIndeterminate(true);
        pd.show();
    }

    @Override
    protected List<Movie> doInBackground(Void... params) {
        connectingServer connector = new connectingServer(mActivity.getApplicationContext());
        List<Movie> movies;
        try {
            movies = connector.getMovies(PAGE_NUMBER_1, mActivity.getResources().getInteger(R.integer.number_of_movies_to_load), mSortCriteria);
        } catch (IOException | JSONException e) {

            return new ArrayList<>();
        } catch (Exception e) {
            unauthorizedExceptionOccured = true;
            return new ArrayList<>();
        }

        return movies;
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {

        listener.onDataSetUpdated(movies);
        if (pd != null) {
            pd.dismiss();
        }
    }
}
