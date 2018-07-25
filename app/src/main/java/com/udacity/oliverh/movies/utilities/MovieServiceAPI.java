package com.udacity.oliverh.movies.utilities;

import com.udacity.oliverh.movies.BuildConfig;
import com.udacity.oliverh.movies.R;

public class MovieServiceAPI {
    final static String MOVIE_DB_API_KEY = BuildConfig.MOVIE_API_KEY;

    // Resource IDs for strings used to construct Movie URL
    final static int MOVIE_DB_BASE_URL_ID = R.string.MOVIE_SERVICE_BASE_URL;
    final static int API_KEY_QUERY_STRING_ID = R.string.API_KEY_QUERY_STRING;
    final static int POPULAR_MOVIES_QUERY_STRING_ID = R.string.POPULAR_MOVIES_QUERY_STRING;
    final static int TOP_RATED_QUERY_STRING_ID = R.string.TOP_RATED_QUERY_STRING;

    // Movie posters, most popular, top rated
    //  movie information
        // orginal title
        // poster thumbnail
        // plot synopsis
        // user rating / vote_average
        // release date
}