package com.nvvi9.giphyme.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.nvvi9.giphyme.data.gifinfo.GifInfo
import com.nvvi9.giphyme.db.GifPreviewDataStore
import com.nvvi9.giphyme.db.dao.GifInfoDao
import com.nvvi9.giphyme.network.retrofit.GiphyApiService
import com.nvvi9.giphyme.repositories.base.FavoriteGifsRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable

class FavoriteGifsRepositoryImpl(
    private val gifInfoDao: GifInfoDao,
    private val giphyApiService: GiphyApiService,
    private val gifPreviewDataStore: GifPreviewDataStore
) : FavoriteGifsRepository {

    override fun addToFavorites(gifId: String): Completable =
        giphyApiService.getGifDataItem(gifId)
            .map { GifInfo.fromGiphyDataItem(it.giphyDataItem) }
            .flatMapCompletable {
                gifInfoDao.insert(it).andThen(gifPreviewDataStore.markAsFavorite(it.id, true))
            }

    override fun getFavorites(): Flowable<PagingData<GifInfo>> =
        Pager(PagingConfig(20, 5, true, 20, 30)) {
            gifInfoDao.getGifs()
        }.flowable

    override fun deleteFromFavorites(gifId: String): Completable =
        gifInfoDao.delete(gifId).andThen(gifPreviewDataStore.markAsFavorite(gifId, false))

    override fun getFavorite(gifId: String): Observable<GifInfo> =
        gifInfoDao.getGif(gifId)
}