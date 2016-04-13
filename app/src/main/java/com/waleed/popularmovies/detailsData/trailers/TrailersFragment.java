package com.waleed.popularmovies.detailsData.trailers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.waleed.popularmovies.R;

import java.util.HashMap;
import java.util.Map;


public class TrailersFragment extends Fragment implements TrailersDataUpdateListener {

    public static final String KEY_ID = "id";
    private TrailersArrayAdapter arrayAdapter;

    public static TrailersFragment newInstance(int id) {
        TrailersFragment fragment = new TrailersFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(KEY_ID, id);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayAdapter = new TrailersArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, new HashMap<String, String>());

        Map<String, String> elements = null;

        ParseTrailers parseTrailers = new ParseTrailers(getActivity(), this);
        parseTrailers.execute(getArguments().getInt(KEY_ID));


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmenttrailers, container, false);

        ListView gridViewLayout = (ListView) view.findViewById(R.id.trailer_list_view);
        gridViewLayout.setAdapter(arrayAdapter);
        gridViewLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + arrayAdapter.getItem(position))));
            }
        });
        view.setBackgroundColor(getResources().getColor(R.color.windowBackground));

        return view;
    }

    @Override
    public void onDataSetUpdated(Map<String, String> trailers) {
        arrayAdapter.updateValues(trailers);
    }
}
