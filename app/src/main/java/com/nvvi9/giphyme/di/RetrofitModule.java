package com.nvvi9.giphyme.di;

import com.nvvi9.giphyme.network.retrofit.GiphyApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class RetrofitModule {

    @Provides
    @Singleton
    GiphyApiService provideGiphyApiService() {
        return new Retrofit.Builder()
                .baseUrl("https://api.giphy.com/v1/")
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GiphyApiService.class);
    }
}
