package com.diegopatrocinio.giphytrends.storage;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface StoredGifDao {

    @Query("Select * FROM storedGifs WHERE storedGifURL = :url")
    StoredGifModel selectByUrl(String url);

    @Query("SELECT * FROM storedGifs")
    List<StoredGifModel> getAllImages();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(StoredGifModel imageModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<StoredGifModel> imageModels);

    @Query("DELETE FROM storedGifs WHERE storedGifURL = :url")
    void deleteByUrl(String url);

    @Delete
    void delete(StoredGifModel imageModel);
}