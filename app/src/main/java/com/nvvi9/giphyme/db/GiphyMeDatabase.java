package com.nvvi9.giphyme.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.nvvi9.giphyme.data.gifinfo.GifInfo;
import com.nvvi9.giphyme.data.preview.GifPreview;
import com.nvvi9.giphyme.data.preview.GifPreviewRemoteKeys;
import com.nvvi9.giphyme.db.dao.GifInfoDao;
import com.nvvi9.giphyme.db.dao.GifInfoRemoteKeysDao;
import com.nvvi9.giphyme.db.dao.GifPreviewDao;

@Database(entities = {GifPreview.class, GifPreviewRemoteKeys.class, GifInfo.class}, version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class GiphyMeDatabase extends RoomDatabase {
    public abstract GifPreviewDao getGifPreviewDao();

    public abstract GifInfoRemoteKeysDao getGifInfoRemoteKeysDao();

    public abstract GifInfoDao gitGifInfoDao();
}
