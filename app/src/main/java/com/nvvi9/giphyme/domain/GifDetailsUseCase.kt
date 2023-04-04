package com.nvvi9.giphyme.domain

import com.nvvi9.giphyme.domain.mappers.GifDetailsItemMapper
import com.nvvi9.giphyme.repositories.base.FavoriteGifsRepository
import com.nvvi9.giphyme.vo.GifDetailsItem
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GifDetailsUseCase @Inject constructor(
    private val repository: FavoriteGifsRepository,
    private val mapper: GifDetailsItemMapper
) {
    fun getGifDetails(gifId: String): Observable<GifDetailsItem> =
        repository.getFavorite(gifId)
            .map { mapper.map(it) }
}