package com.waleed.popularmovies.detailsData.trailers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.waleed.popularmovies.R;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ParseTrailers extends AsyncTask<Integer, Void, Map<String, String>> {

    private Activity mActivity;
    private ProgressDialog pd;
    private boolean ExceptionOccurred = false;
    private TrailersDataUpdateListener listener;


    public ParseTrailers(Activity activity, TrailersDataUpdateListener listener) {
        mActivity = activity;
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
    protected Map<String, String> doInBackground(Integer... params) {
        MovieTrailersServerConnector connector = new MovieTrailersServerConnector(mActivity, params[0]);
        Map<String, String> trailers;
        try {
            trailers = connector.getTrailers();
        } catch (IOException | JSONException e) {
            // TODO: Display error message
            Log.e("", "Error occurred while parsing trailers data...: " + e.toString());
            return new HashMap<>();
        } catch (Exception e) {
            ExceptionOccurred = true;
            return new HashMap<>();
        }

        return trailers;
    }

    @Override
    protected void onPostExecute(Map<String, String> trailers) {

        listener.onDataSetUpdated(trailers);
        if (pd != null) {
            pd.dismiss();
        }
    }
}
