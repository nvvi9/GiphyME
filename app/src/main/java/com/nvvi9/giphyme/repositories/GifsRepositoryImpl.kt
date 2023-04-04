package com.nvvi9.giphyme.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.nvvi9.giphyme.data.giphy.suggestions.SuggestionResponse
import com.nvvi9.giphyme.data.preview.GifPreview
import com.nvvi9.giphyme.db.GifPreviewDataStore
import com.nvvi9.giphyme.db.dao.GifInfoRemoteKeysDao
import com.nvvi9.giphyme.network.retrofit.GiphyApiService
import com.nvvi9.giphyme.repositories.base.GifsRepository
import com.nvvi9.giphyme.repositories.paging.GifPageKeyedRemoteMediator
import com.nvvi9.giphyme.repositories.paging.GifSearchPagingSource
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

class GifsRepositoryImpl(
    private val gifPreviewDataStore: GifPreviewDataStore,
    private val giphyApiService: GiphyApiService,
    private val gifInfoRemoteKeysDao: GifInfoRemoteKeysDao
) : GifsRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getGifPreviews(): Flowable<PagingData<GifPreview>> =
        Pager(
            PagingConfig(20, 5, true, 20, 30),
            0,
            GifPageKeyedRemoteMediator(giphyApiService, gifPreviewDataStore, gifInfoRemoteKeysDao)
        ) {
            gifPreviewDataStore.getGifs()
        }.flowable

    override fun getGifPreviews(query: String): Flowable<PagingData<GifPreview>> =
        Pager(PagingConfig(20, 5, true, 20, 30)) {
            GifSearchPagingSource(
                giphyApiService,
                query
            )
        }.flowable

    override fun getSuggestion(query: String): Single<SuggestionResponse> =
        giphyApiService.getSuggestions(query)
}