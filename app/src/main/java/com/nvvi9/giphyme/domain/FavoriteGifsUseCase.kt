package com.nvvi9.giphyme.domain

import androidx.paging.PagingData
import androidx.paging.map
import com.nvvi9.giphyme.domain.mappers.FavoriteGifItemMapper
import com.nvvi9.giphyme.repositories.base.FavoriteGifsRepository
import com.nvvi9.giphyme.vo.FavoriteGifItem
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import java.util.concurrent.Executors
import javax.inject.Inject

class FavoriteGifsUseCase @Inject constructor(
    private val repository: FavoriteGifsRepository,
    private val mapper: FavoriteGifItemMapper
) {
    fun getFavorites(): Flowable<PagingData<FavoriteGifItem>> = repository.getFavorites()
        .map { gifInfoPagingData ->
            gifInfoPagingData.map(Executors.newCachedThreadPool()) { mapper.map(it) }
        }

    fun deleteFromFavorites(gifId: String): Completable =
        repository.deleteFromFavorites(gifId)
}