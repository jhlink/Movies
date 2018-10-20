package com.udacity.oliverh.movies.data.network;

import android.content.Context;

import com.udacity.oliverh.movies.BuildConfig;
import com.udacity.oliverh.movies.R;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MovieServiceAPI {
    private final static String MOVIE_SERVICE_API_TAG = MovieServiceAPI.class.getSimpleName();
    private final static String MOVIE_DB_API_KEY = BuildConfig.MOVIE_API_KEY;

    // Resource IDs for strings used to construct Movie URL
    private final static int MOVIE_DB_BASE_URL_ID = R.string.MOVIE_SERVICE_BASE_URL;
    private final static int MOVIE_IMAGE_BASE_URL_ID = R.string.MOVIE_IMAGE_BASE_URL;
    private final static int API_KEY_QUERY_STRING_ID = R.string.API_KEY_QUERY_STRING;
    private final static int POPULAR_MOVIES_QUERY_STRING_ID = R.string.POPULAR_MOVIES_QUERY_STRING;
    private final static int TOP_RATED_QUERY_STRING_ID = R.string.TOP_RATED_QUERY_STRING;

    private static final OkHttpClient client = OkHttpSingleton.getInstance().getClient();

    public static Call getPopularMovies(Context context) {
        HttpUrl builtUri = HttpUrl.parse(context.getString(MOVIE_DB_BASE_URL_ID)).newBuilder()
                .addPathSegment(context.getString(POPULAR_MOVIES_QUERY_STRING_ID))
                .addQueryParameter(context.getString(API_KEY_QUERY_STRING_ID), MOVIE_DB_API_KEY)
                .build();

        Request request = new Request.Builder()
                .url(builtUri)
                .build();

        return client.newCall(request);
    }

    public static Call getTopRatedMovies(Context context) {
        HttpUrl builtUri = HttpUrl.parse(context.getString(MOVIE_DB_BASE_URL_ID)).newBuilder()
                .addPathSegment(context.getString(TOP_RATED_QUERY_STRING_ID))
                .addQueryParameter(context.getString(API_KEY_QUERY_STRING_ID), MOVIE_DB_API_KEY)
                .build();

        Request request = new Request.Builder()
                .url(builtUri)
                .build();

        return client.newCall(request);
    }

    public static String getMoviePosterUrl(Context context, String imageSize, String imagePath) {
        HttpUrl builtUri = HttpUrl.parse(context.getString(MOVIE_IMAGE_BASE_URL_ID)).newBuilder()
                .addPathSegment(imageSize)
                .addPathSegment(imagePath)
                .build();

        return builtUri.toString();
    }

    public static Call getMovieReviews(Context context, int movieId) {
      HttpUrl builtUri = HttpUrl.parse(context.getString(MOVIE_DB_BASE_URL_ID)).newBuilder()
                .addPathSegment(String.valueOf(movieId))
                .addPathSegment(context.getString(R.string.MOVIE_REVIEWS_QUERY_STRING_ID))
                .addQueryParameter(context.getString(API_KEY_QUERY_STRING_ID), MOVIE_DB_API_KEY)
                .build();

        Request request = new Request.Builder()
                .url(builtUri)
                .build();

        return client.newCall(request);
    }
}