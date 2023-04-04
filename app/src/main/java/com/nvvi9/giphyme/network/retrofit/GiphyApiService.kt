package com.nvvi9.giphyme.network.retrofit

import com.nvvi9.giphyme.BuildConfig
import com.nvvi9.giphyme.data.giphy.gifs.GiphyGifResponse
import com.nvvi9.giphyme.data.giphy.gifs.GiphyResponse
import com.nvvi9.giphyme.data.giphy.suggestions.SuggestionResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GiphyApiService {

    @GET("gifs/trending?api_key=" + BuildConfig.GIPHY_API_TOKEN)
    fun getTrending(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("rating") rating: String
    ): Single<GiphyResponse>

    @GET("gifs/search?api_key=" + BuildConfig.GIPHY_API_TOKEN)
    fun getSearch(
        @Query("q") query: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Single<GiphyResponse>

    @GET("gifs/{id}?api_key=" + BuildConfig.GIPHY_API_TOKEN)
    fun getGifDataItem(@Path("id") gifId: String): Single<GiphyGifResponse>

    @GET("tags/related/{query}?api_key=" + BuildConfig.GIPHY_API_TOKEN)
    fun getSuggestions(@Path("query") query: String): Single<SuggestionResponse>
}