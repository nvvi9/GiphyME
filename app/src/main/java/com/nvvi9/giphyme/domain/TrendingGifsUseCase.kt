package com.nvvi9.giphyme.domain

import androidx.paging.PagingData
import androidx.paging.map
import com.nvvi9.giphyme.data.preview.GifPreview
import com.nvvi9.giphyme.domain.mappers.GifItemMapper
import com.nvvi9.giphyme.repositories.base.FavoriteGifsRepository
import com.nvvi9.giphyme.repositories.base.GifsRepository
import com.nvvi9.giphyme.vo.GifItem
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import java.util.concurrent.Executors
import javax.inject.Inject

class TrendingGifsUseCase @Inject constructor(
    private val gifsRepository: GifsRepository,
    private val favoriteGifsRepository: FavoriteGifsRepository,
    private val mapper: GifItemMapper
) {
    fun getTrendingGifs(): Flowable<PagingData<GifItem>> =
        gifsRepository.getGifPreviews()
            .map { data: PagingData<GifPreview> ->
                data.map(Executors.newCachedThreadPool()) { mapper.map(it) }
            }

    fun addToFavorites(gifId: String): Completable =
        favoriteGifsRepository.addToFavorites(gifId)

    fun removeFromFavorites(gifId: String): Completable =
        favoriteGifsRepository.deleteFromFavorites(gifId)
}