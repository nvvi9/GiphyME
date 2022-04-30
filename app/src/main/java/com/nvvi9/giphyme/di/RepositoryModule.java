package com.nvvi9.giphyme.di;

import com.nvvi9.giphyme.db.GifPreviewDataStore;
import com.nvvi9.giphyme.db.dao.GifInfoDao;
import com.nvvi9.giphyme.db.dao.GifInfoRemoteKeysDao;
import com.nvvi9.giphyme.network.retrofit.GiphyApiService;
import com.nvvi9.giphyme.repositories.FavoriteGifsRepositoryImpl;
import com.nvvi9.giphyme.repositories.GifsRepositoryImpl;
import com.nvvi9.giphyme.repositories.base.FavoriteGifsRepository;
import com.nvvi9.giphyme.repositories.base.GifsRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@InstallIn(SingletonComponent.class)
@Module
public class RepositoryModule {

    @Singleton
    @Provides
    GifsRepository provideGifsRepository(GifPreviewDataStore gifPreviewDataStore, GiphyApiService giphyApiService, GifInfoRemoteKeysDao gifInfoRemoteKeysDao, GifInfoDao gifInfoDao) {
        return new GifsRepositoryImpl(gifPreviewDataStore, giphyApiService, gifInfoRemoteKeysDao, gifInfoDao);
    }

    @Singleton
    @Provides
    FavoriteGifsRepository provideFavoriteGifsRepository(GifInfoDao gifInfoDao, GiphyApiService giphyApiService, GifPreviewDataStore gifPreviewDataStore) {
        return new FavoriteGifsRepositoryImpl(gifInfoDao, giphyApiService, gifPreviewDataStore);
    }
}
