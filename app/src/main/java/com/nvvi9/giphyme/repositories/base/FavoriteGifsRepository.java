package com.nvvi9.giphyme.repositories.base;

import androidx.paging.PagingData;

import com.nvvi9.giphyme.data.gifinfo.GifInfo;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;

public interface FavoriteGifsRepository {

    Completable addToFavorites(String gifId);

    Flowable<PagingData<GifInfo>> getFavorites();

    Completable deleteFromFavorites(String gifId);

    Observable<GifInfo> getFavorite(String gifId);
}
