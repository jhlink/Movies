package com.udacity.oliverh.movies.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = AppDatabase.class;
    private static final String DATABASE_NAME = "moviedb";
    private static volatile AppDatabase ourInstance;

    public abstract MovieDao movieDao();

    public static AppDatabase getInstance(final Context context) {
        if (ourInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                ourInstance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, AppDatabase.DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
            }
        }
        Log.d(LOG_TAG, "Getting database instance");
        return ourInstance;
    }
}
