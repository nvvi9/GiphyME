package com.nvvi9.giphyme.repositories.base

import androidx.paging.PagingData
import com.nvvi9.giphyme.data.giphy.suggestions.SuggestionResponse
import com.nvvi9.giphyme.data.preview.GifPreview
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

interface GifsRepository {
    fun getGifPreviews(): Flowable<PagingData<GifPreview>>
    fun getGifPreviews(query: String): Flowable<PagingData<GifPreview>>
    fun getSuggestion(query: String): Single<SuggestionResponse>
}