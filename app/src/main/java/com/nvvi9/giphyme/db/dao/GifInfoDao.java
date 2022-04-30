package com.nvvi9.giphyme.db.dao;

import androidx.paging.PagingSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.nvvi9.giphyme.data.gifinfo.GifInfo;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface GifInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(GifInfo gifInfo);

    @Query("SELECT * FROM gif_infos WHERE id in (:ids)")
    Single<List<GifInfo>> getGifs(List<String> ids);

    @Query("SELECT * FROM gif_infos WHERE id = :id")
    Observable<GifInfo> getGif(String id);

    @Query("SELECT * FROM gif_infos ORDER BY savedAt DESC")
    PagingSource<Integer, GifInfo> getGifs();

    @Query("DELETE FROM gif_infos WHERE id = :gifId")
    Completable delete(String gifId);
}
