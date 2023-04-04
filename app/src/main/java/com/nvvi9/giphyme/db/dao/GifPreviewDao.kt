package com.nvvi9.giphyme.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nvvi9.giphyme.data.preview.GifPreview
import io.reactivex.rxjava3.core.Completable

@Dao
interface GifPreviewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(gifPreviewList: List<GifPreview>): Completable

    @Query("SELECT * FROM gif_previews")
    fun getGifs(): PagingSource<Int, GifPreview>

    @Query("UPDATE gif_previews SET inFavorites = :inFavorites WHERE id = :id")
    fun updateSetInFavourites(id: String, inFavorites: Boolean): Completable

    @Query("DELETE FROM gif_previews")
    fun clear(): Completable
}