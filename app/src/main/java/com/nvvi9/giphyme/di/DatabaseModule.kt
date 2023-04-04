package com.nvvi9.giphyme.di

import android.app.Application
import androidx.room.Room.databaseBuilder
import com.nvvi9.giphyme.db.GiphyMeDatabase
import com.nvvi9.giphyme.db.dao.GifInfoDao
import com.nvvi9.giphyme.db.dao.GifInfoRemoteKeysDao
import com.nvvi9.giphyme.db.dao.GifPreviewDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(application: Application?): GiphyMeDatabase =
        databaseBuilder(application!!, GiphyMeDatabase::class.java, "GiphyME.db")
            .build()


    @Provides
    fun provideGifPreviewDao(database: GiphyMeDatabase): GifPreviewDao =
        database.getGifPreviewDao()

    @Provides
    fun provideGifInfoRemoteKeysDao(database: GiphyMeDatabase): GifInfoRemoteKeysDao =
        database.getGifInfoRemoteKeysDao()

    @Provides
    fun provideGifInfoDao(database: GiphyMeDatabase): GifInfoDao =
        database.gitGifInfoDao()
}