package com.waleed.popularmovies.list;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.waleed.popularmovies.BaseActivity;
import com.waleed.popularmovies.R;
import com.waleed.popularmovies.detailsData.DetailsActivityFragment;
import com.waleed.popularmovies.detailsData.reviews.ReviewsFragment;
import com.waleed.popularmovies.detailsData.trailers.TrailersFragment;

public class MovieListActivity extends BaseActivity implements DetailsActivityFragment.CallBacks {


    @Override
    protected Fragment createFragment() {


        return new MovieListFragment();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_movies_list;
    }

    @Override
    public void showReviews(int id) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_details, ReviewsFragment.newInstance(id)).addToBackStack(null).commit();
    }

    @Override
    public void showTrailers(int id) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_details, TrailersFragment.newInstance(id)).addToBackStack(null).commit();
    }
}
