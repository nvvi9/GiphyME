package com.nvvi9.giphyme.repositories;

import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingSource;
import androidx.paging.rxjava3.PagingRx;

import com.nvvi9.giphyme.data.giphy.suggestions.SuggestionResponse;
import com.nvvi9.giphyme.data.preview.GifPreview;
import com.nvvi9.giphyme.db.GifPreviewDataStore;
import com.nvvi9.giphyme.db.dao.GifInfoDao;
import com.nvvi9.giphyme.db.dao.GifInfoRemoteKeysDao;
import com.nvvi9.giphyme.network.retrofit.GiphyApiService;
import com.nvvi9.giphyme.repositories.base.GifsRepository;
import com.nvvi9.giphyme.repositories.paging.GifPageKeyedRemoteMediator;
import com.nvvi9.giphyme.repositories.paging.GifSearchPagingSource;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import kotlin.jvm.functions.Function0;

public class GifsRepositoryImpl implements GifsRepository {

    private final GifPreviewDataStore gifPreviewDataStore;
    private final GiphyApiService giphyApiService;
    private final GifInfoRemoteKeysDao gifInfoRemoteKeysDao;

    public GifsRepositoryImpl(GifPreviewDataStore gifPreviewDataStore, GiphyApiService giphyApiService, GifInfoRemoteKeysDao gifInfoRemoteKeysDao, GifInfoDao gifInfoDao) {
        this.gifPreviewDataStore = gifPreviewDataStore;
        this.giphyApiService = giphyApiService;
        this.gifInfoRemoteKeysDao = gifInfoRemoteKeysDao;
    }

    @Override
    public Flowable<PagingData<GifPreview>> getGifPreviews() {
        return PagingRx.getFlowable(
                new Pager(
                        new PagingConfig(20, 5, true, 20, 30),
                        0,
                        new GifPageKeyedRemoteMediator(giphyApiService, gifPreviewDataStore, gifInfoRemoteKeysDao),
                        (Function0<PagingSource>) gifPreviewDataStore::getGifs
                )
        );
    }

    @Override
    public Flowable<PagingData<GifPreview>> getGifPreviews(String query) {
        return PagingRx.getFlowable(
                new Pager<>(
                        new PagingConfig(20, 5, true, 20, 30),
                        () -> new GifSearchPagingSource(giphyApiService, query)
                )
        );
    }

    @Override
    public Single<SuggestionResponse> getSuggestion(String query) {
        return giphyApiService.getSuggestions(query);
    }
}
