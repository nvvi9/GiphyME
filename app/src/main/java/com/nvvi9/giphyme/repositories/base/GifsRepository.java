package com.nvvi9.giphyme.repositories.base;

import androidx.paging.PagingData;

import com.nvvi9.giphyme.data.giphy.suggestions.SuggestionResponse;
import com.nvvi9.giphyme.data.preview.GifPreview;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface GifsRepository {

    Flowable<PagingData<GifPreview>> getGifPreviews();

    Flowable<PagingData<GifPreview>> getGifPreviews(String query);

    Single<SuggestionResponse> getSuggestion(String query);
}
