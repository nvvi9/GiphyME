package com.nvvi9.giphyme.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.nvvi9.giphyme.data.preview.GifPreviewRemoteKeys;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface GifInfoRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(List<GifPreviewRemoteKeys> remoteKeys);

    @Query("SELECT * FROM gif_preview_keys WHERE id = :gifInfoId")
    Single<GifPreviewRemoteKeys> getRemoteKeys(String gifInfoId);

    @Query("DELETE FROM gif_preview_keys")
    Completable clear();
}
