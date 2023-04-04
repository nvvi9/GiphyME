package com.nvvi9.giphyme.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nvvi9.giphyme.data.preview.GifPreviewRemoteKeys
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface GifInfoRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(remoteKeys: List<GifPreviewRemoteKeys>): Completable

    @Query("SELECT * FROM gif_preview_keys WHERE id = :gifInfoId")
    fun getRemoteKeys(gifInfoId: String): Single<GifPreviewRemoteKeys>

    @Query("DELETE FROM gif_preview_keys")
    fun clear(): Completable
}