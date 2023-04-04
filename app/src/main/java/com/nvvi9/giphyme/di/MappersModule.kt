package com.nvvi9.giphyme.di

import com.nvvi9.giphyme.domain.mappers.FavoriteGifItemMapper
import com.nvvi9.giphyme.domain.mappers.GifDetailsItemMapper
import com.nvvi9.giphyme.domain.mappers.GifItemMapper
import com.nvvi9.giphyme.domain.mappers.SuggestionMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class MappersModule {

    @Provides
    @Singleton
    fun provideGifItemMapper(): GifItemMapper = GifItemMapper()

    @Provides
    @Singleton
    fun provideSuggestionMapper(): SuggestionMapper = SuggestionMapper()

    @Provides
    @Singleton
    fun provideFavoriteGifItemMapper(): FavoriteGifItemMapper = FavoriteGifItemMapper()

    @Provides
    @Singleton
    fun provideGifDetailsItemMapper(): GifDetailsItemMapper = GifDetailsItemMapper()
}