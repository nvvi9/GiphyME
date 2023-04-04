package com.nvvi9.giphyme.db

import androidx.paging.PagingSource
import com.nvvi9.giphyme.data.gifinfo.GifInfo
import com.nvvi9.giphyme.data.preview.GifPreview
import com.nvvi9.giphyme.db.dao.GifInfoDao
import com.nvvi9.giphyme.db.dao.GifPreviewDao
import io.reactivex.rxjava3.core.Completable
import java.util.stream.Collectors
import javax.inject.Inject

class GifPreviewDataStore @Inject constructor(
    private val gifPreviewDao: GifPreviewDao,
    private val gifInfoDao: GifInfoDao
) {
    fun insert(gifPreviews: List<GifPreview>): Completable =
        gifInfoDao.getGifs(gifPreviews.map { it.id })
            .map { gifInfoList -> gifInfoList.map { it.id } }
            .map { gifInfoIds ->
                gifPreviews
                    .map {
                        if (gifInfoIds.contains(it.id)) GifPreview(
                            it.id,
                            it.title,
                            it.previewUrl,
                            it.previewUrl,
                            it.height,
                            it.width,
                            it.size,
                            true
                        ) else it
                    }
            }
            .flatMapCompletable { gifPreviewDao.insert(it) }

    fun clear(): Completable = gifPreviewDao.clear()

    fun getGifs(): PagingSource<Int, GifPreview> =
        gifPreviewDao.getGifs()

    fun markAsFavorite(gifId: String, inFavorites: Boolean): Completable =
        gifPreviewDao.updateSetInFavourites(gifId, inFavorites)
}