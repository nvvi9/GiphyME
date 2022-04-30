package com.nvvi9.giphyme.di;

import android.app.Application;

import androidx.room.Room;

import com.nvvi9.giphyme.db.GiphyMeDatabase;
import com.nvvi9.giphyme.db.dao.GifInfoDao;
import com.nvvi9.giphyme.db.dao.GifInfoRemoteKeysDao;
import com.nvvi9.giphyme.db.dao.GifPreviewDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@InstallIn(SingletonComponent.class)
@Module
public class DatabaseModule {

    @Provides
    @Singleton
    GiphyMeDatabase provideDatabase(Application application) {
        return Room.databaseBuilder(application, GiphyMeDatabase.class, "GiphyME.db")
                .build();
    }

    @Provides
    GifPreviewDao provideGifPreviewDao(GiphyMeDatabase database) {
        return database.getGifPreviewDao();
    }

    @Provides
    GifInfoRemoteKeysDao provideGifInfoRemoteKeysDao(GiphyMeDatabase database) {
        return database.getGifInfoRemoteKeysDao();
    }

    @Provides
    GifInfoDao provideGifInfoDao(GiphyMeDatabase database) {
        return database.gitGifInfoDao();
    }
}
