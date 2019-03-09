package com.diegopatrocinio.giphytrends.storage;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@android.arch.persistence.room.Database(entities = {StoredGifModel.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {

    private static Database instance, inMemInstance;
    private static String databaseName = "GiphyTrends.db";

    public abstract StoredGifDao storedGifDao();

    public static Database getInMemoryDatabase(Context context) {
        if (inMemInstance == null) {
            inMemInstance = Room.inMemoryDatabaseBuilder(context.getApplicationContext(), Database.class)
                            .allowMainThreadQueries()
                            .build();
        }
        return inMemInstance;
    }

    public static Database getDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), Database.class, databaseName)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
        inMemInstance = null;
    }
}