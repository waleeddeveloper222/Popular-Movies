package com.waleed.popularmovies.list;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.waleed.popularmovies.FavoriteMovies;
import com.waleed.popularmovies.Movie;
import com.waleed.popularmovies.R;
import com.waleed.popularmovies.detailsData.DetailsActivity;
import com.waleed.popularmovies.detailsData.DetailsActivityFragment;

import java.util.ArrayList;
import java.util.List;


public class MovieListFragment extends Fragment implements DataSetUpdateListener {

    public static final String KEY_MOVIES = "key_movies";
    private CustomArrayAdapter arrayAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayAdapter = new CustomArrayAdapter(getActivity(), R.layout.gridview, new ArrayList<Movie>());

        List<Movie> movies = null;
        if (savedInstanceState != null) {
            movies = savedInstanceState.getParcelableArrayList(KEY_MOVIES);
        }
        if (movies == null) {
            FetchMoviesData fetchMoviesData = new FetchMoviesData(getActivity(), this);
            fetchMoviesData.execute();
        } else {
            arrayAdapter.updateValues(movies);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentmovieslist, container, false);
        GridView gridViewLayout = (GridView) view.findViewById(R.id.grid_view_layout);
        gridViewLayout.setAdapter(arrayAdapter);
        gridViewLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (getActivity().findViewById(R.id.fragment_details) != null) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                    Movie movie = (Movie) parent.getAdapter().getItem(position);
                    Fragment fragment = DetailsActivityFragment.newInstance(movie);
                    fragmentManager.beginTransaction().add(R.id.fragment_details, fragment).commit();

                } else {
                    Movie movie = (Movie) parent.getAdapter().getItem(position);
                    Intent intent = new Intent(getActivity(), DetailsActivity.class);
                    intent.putExtra("movie", movie);
                    startActivity(intent);
                }
            }
        });
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_MOVIES, (ArrayList<? extends Parcelable>) arrayAdapter.getElements());
    }

    @Override
    public void onDataSetUpdated(List<Movie> movies) {
        arrayAdapter.updateValues(movies);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sort_user_rating) {
            FetchMoviesData fetchMoviesData = new FetchMoviesData(getActivity(), this, 1);
            fetchMoviesData.execute();
        } else if (id == R.id.action_sort_popularity) {
            FetchMoviesData fetchMoviesData = new FetchMoviesData(getActivity(), this, 0);
            fetchMoviesData.execute();

        } else if (id == R.id.action_favorites) {
            onDataSetUpdated(FavoriteMovies.create(getActivity()).getMovies());
        }
        return true;
    }

}
