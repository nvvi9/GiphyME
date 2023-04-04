package com.nvvi9.giphyme.repositories.base

import androidx.paging.PagingData
import com.nvvi9.giphyme.data.gifinfo.GifInfo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable

interface FavoriteGifsRepository {
    fun addToFavorites(gifId: String): Completable
    fun getFavorites(): Flowable<PagingData<GifInfo>>
    fun deleteFromFavorites(gifId: String): Completable
    fun getFavorite(gifId: String): Observable<GifInfo>
}