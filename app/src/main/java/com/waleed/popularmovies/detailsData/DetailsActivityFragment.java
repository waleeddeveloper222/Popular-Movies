package com.waleed.popularmovies.detailsData;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.waleed.popularmovies.FavoriteMovies;
import com.waleed.popularmovies.Movie;
import com.waleed.popularmovies.R;
import com.squareup.picasso.Picasso;


/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsActivityFragment extends Fragment {

    public static final String ARG_MOVIE = "arg_movie";
    private FavoriteMovies mManager;

    private CallBacks mCallBacks;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallBacks = (CallBacks) activity;
    }

    public DetailsActivityFragment() {
    }

    public static DetailsActivityFragment newInstance(Movie movie) {
        DetailsActivityFragment fragment = new DetailsActivityFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_MOVIE, movie);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragmentdetails, container, false);
        mManager = FavoriteMovies.create(getActivity());
        final Movie movie = getArguments().getParcelable(ARG_MOVIE);

        TextView moviePlot = (TextView) rootView.findViewById(R.id.movie_plot);
        moviePlot.setText("null".equals(movie.getPlotSynopsis()) ? "" : movie.getPlotSynopsis());

        TextView movieTitle = (TextView) rootView.findViewById(R.id.movie_title);
        movieTitle.setText(movie.getOriginalTitle());

        final Button moviefavorite = (Button)rootView.findViewById(R.id.movie_loved);


        if (mManager.isFavorite(movie)) {
            moviefavorite.setTextColor(Color.BLACK);
        } else {
            moviefavorite.setTextColor(Color.LTGRAY);
        }
        moviefavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mManager.isFavorite(movie)) {
                    mManager.remove(movie);
                    moviefavorite.setTextColor(Color.LTGRAY);
                } else {
                    mManager.add(movie);
                    moviefavorite.setTextColor(Color.BLACK);
                }
            }
        });

        TextView rating = (TextView) rootView.findViewById(R.id.movie_rating);
        rating.setText(String.valueOf("User Rating [ " + movie.getUserRating() + "/10 ]"));

        TextView releaseDate = (TextView) rootView.findViewById(R.id.movie_release_date);
        releaseDate.setText("Release Date [ " + ("null".equals(movie.getReleaseDate()) ? "N/A" : movie.getReleaseDate()) + " ]");

        ImageView imageView = (ImageView) rootView.findViewById(R.id.movie_poster);
        Picasso.with(getActivity()).load(movie.getPosterUrl()).error(R.drawable.no_poseter_found).into(imageView);

        rootView.findViewById(R.id.movie_trailers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallBacks.showTrailers(movie.getId());
            }
        });
        rootView.findViewById(R.id.movie_reviews).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallBacks.showReviews(movie.getId());
            }
        });

        return rootView;
    }

    public interface CallBacks{
        public void showReviews(int id);
        public void showTrailers(int id);
    }

}
