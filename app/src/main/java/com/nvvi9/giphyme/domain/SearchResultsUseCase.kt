package com.nvvi9.giphyme.domain

import androidx.paging.PagingData
import androidx.paging.map
import com.nvvi9.giphyme.domain.mappers.GifItemMapper
import com.nvvi9.giphyme.repositories.base.FavoriteGifsRepository
import com.nvvi9.giphyme.repositories.base.GifsRepository
import com.nvvi9.giphyme.vo.GifItem
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import java.util.concurrent.Executors
import javax.inject.Inject

class SearchResultsUseCase @Inject constructor(
    private val gifsRepository: GifsRepository,
    private val favoriteGifsRepository: FavoriteGifsRepository,
    private val mapper: GifItemMapper
) {
    fun getGifsFromQuery(query: String): Flowable<PagingData<GifItem>> =
        gifsRepository.getGifPreviews(query)
            .map { gifPreviewPagingData ->
                gifPreviewPagingData.map(Executors.newCachedThreadPool()) { mapper.map(it) }
            }

    fun saveInFavorites(id: String): Completable = favoriteGifsRepository.addToFavorites(id)
}