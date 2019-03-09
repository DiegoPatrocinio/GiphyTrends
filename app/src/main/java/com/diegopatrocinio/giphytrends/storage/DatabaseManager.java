package com.diegopatrocinio.giphytrends.storage;

import com.giphy.sdk.core.models.Media;
import com.google.gson.Gson;
import com.diegopatrocinio.giphytrends.adapters.MediaAdapter;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private Database database;

    public DatabaseManager(Database db) {
        this.database = db;
    }

    public boolean isStoredGif(Media media) {
        StoredGifModel storedGif = null;
        String url = MediaAdapter.getImageUrlFrom(media);

        if (url != null) {
            storedGif = database.storedGifDao().selectByUrl(url);
        }
        return storedGif != null;
    }

    public int insertStoredGif(Media media) {
        int primaryKey = 0;

        String url = MediaAdapter.getImageUrlFrom(media);

        if (url != null) {
            StoredGifModel storedGif = new StoredGifModel();
            storedGif.setStoredGifURL(url);
            storedGif.setStoredGifData(new Gson().toJson(media));

            primaryKey = (int)database.storedGifDao().insert(storedGif);
        }
        return primaryKey;
    }

    public void removeStoredGif(Media media) {
        String url = MediaAdapter.getImageUrlFrom(media);

        if (url != null) {
            database.storedGifDao().deleteByUrl(url);
        }
    }

    public List<Media> getAllStoredGif() {
        return extractMedia(database.storedGifDao().getAllImages());
    }

    public static List<Media> extractMedia(List<StoredGifModel> storedGifList) {
        List<Media> mediaList = new ArrayList<>();

        for (StoredGifModel storedGif : storedGifList) {
            Media media = new Gson().fromJson(storedGif.getStoredGifData(), Media.class);
            mediaList.add(media);
        }

        return mediaList;
    }
}