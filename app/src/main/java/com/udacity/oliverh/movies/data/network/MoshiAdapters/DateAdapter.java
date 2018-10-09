package com.udacity.oliverh.movies.data.network.MoshiAdapters;

import android.util.Log;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.ToJson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateAdapter {
    private final static String DATE_ADAPTER_TAG = DateAdapter.class.getSimpleName();
    private final static SimpleDateFormat mformat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    @ToJson String toJson(Date date) {
        return mformat.format(date);
    }

    @FromJson Date fromJson(String sDate) {
        if ( sDate.length() != 10) {
            throw new JsonDataException("Date JSON Anomaly");
        }

        Date date = null;
        try {
            date = mformat.parse(String.valueOf(sDate));
        } catch (ParseException e) {
            Log.e(DATE_ADAPTER_TAG, e.toString());
        }

        return date;
    }
}
