package com.nvvi9.giphyme.db.dao;

import androidx.paging.PagingSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.nvvi9.giphyme.data.preview.GifPreview;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

@Dao
public interface GifPreviewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(List<GifPreview> gifPreviewList);

    @Query("SELECT * FROM gif_previews")
    PagingSource<Integer, GifPreview> getGifs();

    @Query("UPDATE gif_previews SET inFavorites = :inFavorites WHERE id = :id")
    Completable updateSetInFavourites(String id, Boolean inFavorites);

    @Query("DELETE FROM gif_previews")
    Completable clear();
}
