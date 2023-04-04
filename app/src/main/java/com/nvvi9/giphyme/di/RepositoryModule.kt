package com.nvvi9.giphyme.di

import com.nvvi9.giphyme.db.GifPreviewDataStore
import com.nvvi9.giphyme.db.dao.GifInfoDao
import com.nvvi9.giphyme.db.dao.GifInfoRemoteKeysDao
import com.nvvi9.giphyme.network.retrofit.GiphyApiService
import com.nvvi9.giphyme.repositories.FavoriteGifsRepositoryImpl
import com.nvvi9.giphyme.repositories.GifsRepositoryImpl
import com.nvvi9.giphyme.repositories.base.FavoriteGifsRepository
import com.nvvi9.giphyme.repositories.base.GifsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideGifsRepository(
        gifPreviewDataStore: GifPreviewDataStore,
        giphyApiService: GiphyApiService,
        gifInfoRemoteKeysDao: GifInfoRemoteKeysDao
    ): GifsRepository =
        GifsRepositoryImpl(gifPreviewDataStore, giphyApiService, gifInfoRemoteKeysDao)

    @Singleton
    @Provides
    fun provideFavoriteGifsRepository(
        gifInfoDao: GifInfoDao,
        giphyApiService: GiphyApiService,
        gifPreviewDataStore: GifPreviewDataStore
    ): FavoriteGifsRepository =
        FavoriteGifsRepositoryImpl(gifInfoDao, giphyApiService, gifPreviewDataStore)
}