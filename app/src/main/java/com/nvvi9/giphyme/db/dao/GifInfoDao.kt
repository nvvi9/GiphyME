package com.nvvi9.giphyme.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nvvi9.giphyme.data.gifinfo.GifInfo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

@Dao
interface GifInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(gifInfo: GifInfo): Completable

    @Query("SELECT * FROM gif_infos WHERE id in (:ids)")
    fun getGifs(ids: List<String>): Single<List<GifInfo>>

    @Query("SELECT * FROM gif_infos WHERE id = :id")
    fun getGif(id: String): Observable<GifInfo>

    @Query("SELECT * FROM gif_infos ORDER BY savedAt DESC")
    fun getGifs(): PagingSource<Int, GifInfo>

    @Query("DELETE FROM gif_infos WHERE id = :gifId")
    fun delete(gifId: String): Completable
}