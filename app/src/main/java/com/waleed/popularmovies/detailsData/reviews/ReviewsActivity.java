package com.waleed.popularmovies.detailsData.reviews;

import android.support.v4.app.Fragment;

import com.waleed.popularmovies.BaseActivity;


public class ReviewsActivity extends BaseActivity {
    @Override
    protected Fragment createFragment() {
        return  ReviewsFragment.newInstance(getIntent().getIntExtra("extra_movie_id",0));
    }
}
