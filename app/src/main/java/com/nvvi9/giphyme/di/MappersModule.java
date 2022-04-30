package com.nvvi9.giphyme.di;

import com.nvvi9.giphyme.domain.mappers.FavoriteGifItemMapper;
import com.nvvi9.giphyme.domain.mappers.GifDetailsItemMapper;
import com.nvvi9.giphyme.domain.mappers.GifItemMapper;
import com.nvvi9.giphyme.domain.mappers.SuggestionMapper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@InstallIn(SingletonComponent.class)
@Module
public class MappersModule {

    @Provides
    @Singleton
    GifItemMapper provideGifItemMapper() {
        return new GifItemMapper();
    }

    @Provides
    @Singleton
    SuggestionMapper provideSuggestionMapper() {
        return new SuggestionMapper();
    }

    @Provides
    @Singleton
    FavoriteGifItemMapper provideFavoriteGifItemMapper() {
        return new FavoriteGifItemMapper();
    }

    @Provides
    @Singleton
    GifDetailsItemMapper provideGifDetailsItemMapper() {
        return new GifDetailsItemMapper();
    }
}
