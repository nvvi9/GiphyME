package com.nvvi9.giphyme.network.retrofit;

import com.nvvi9.giphyme.BuildConfig;
import com.nvvi9.giphyme.data.giphy.gifs.GiphyGifResponse;
import com.nvvi9.giphyme.data.giphy.gifs.GiphyResponse;
import com.nvvi9.giphyme.data.giphy.suggestions.SuggestionResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GiphyApiService {

    @GET("gifs/trending?api_key=" + BuildConfig.GIPHY_API_TOKEN)
    Single<GiphyResponse> getTrending(
            @Query("limit") Integer limit,
            @Query("offset") Integer offset,
            @Query("rating") String rating
    );

    @GET("gifs/search?api_key=" + BuildConfig.GIPHY_API_TOKEN)
    Single<GiphyResponse> getSearch(
            @Query("q") String query,
            @Query("limit") Integer limit,
            @Query("offset") Integer offset
    );

    @GET("gifs/{id}?api_key=" + BuildConfig.GIPHY_API_TOKEN)
    Single<GiphyGifResponse> getGifDataItem(@Path("id") String gifId);

    @GET("tags/related/{query}?api_key=" + BuildConfig.GIPHY_API_TOKEN)
    Single<SuggestionResponse> getSuggestions(@Path("query") String query);
}
