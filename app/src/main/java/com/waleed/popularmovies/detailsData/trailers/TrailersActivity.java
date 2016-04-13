package com.waleed.popularmovies.detailsData.trailers;

import android.support.v4.app.Fragment;

import com.waleed.popularmovies.BaseActivity;


public class TrailersActivity extends BaseActivity {
    @Override
    protected Fragment createFragment() {
        return  TrailersFragment.newInstance(getIntent().getIntExtra("extra_movie_id",0));
    }
}
