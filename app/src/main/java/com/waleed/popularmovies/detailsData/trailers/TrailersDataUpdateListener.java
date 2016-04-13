package com.waleed.popularmovies.detailsData.trailers;

import java.util.Map;

public interface TrailersDataUpdateListener {
    void onDataSetUpdated(Map<String, String> trailers);
}
