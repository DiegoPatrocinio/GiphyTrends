package com.diegopatrocinio.giphytrends.storage;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "storedGifs")
public class StoredGifModel {

    @PrimaryKey(autoGenerate = true)
    private int storedGifId;

    @ColumnInfo(name = "storedGifURL")
    private String storedGifURL;

    @ColumnInfo(name = "storedGifData")
    private String storedGifData;

    public int getStoredGifId() { return this.storedGifId; }
    public void setStoredGifId(int id) { this.storedGifId = id; }

    public String getStoredGifURL() { return this.storedGifURL; }
    public void setStoredGifURL(String url) { this.storedGifURL = url; }

    public String getStoredGifData() { return this.storedGifData; }
    public void setStoredGifData(String data) { this.storedGifData = data; }
}
