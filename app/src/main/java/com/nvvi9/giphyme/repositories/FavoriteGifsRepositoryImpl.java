package com.nvvi9.giphyme.repositories;

import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.nvvi9.giphyme.data.gifinfo.GifInfo;
import com.nvvi9.giphyme.db.GifPreviewDataStore;
import com.nvvi9.giphyme.db.dao.GifInfoDao;
import com.nvvi9.giphyme.network.retrofit.GiphyApiService;
import com.nvvi9.giphyme.repositories.base.FavoriteGifsRepository;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;

public class FavoriteGifsRepositoryImpl implements FavoriteGifsRepository {

    private final GifInfoDao gifInfoDao;
    private final GiphyApiService giphyApiService;
    private final GifPreviewDataStore gifPreviewDataStore;

    public FavoriteGifsRepositoryImpl(GifInfoDao gifInfoDao, GiphyApiService giphyApiService, GifPreviewDataStore gifPreviewDataStore) {
        this.gifInfoDao = gifInfoDao;
        this.giphyApiService = giphyApiService;
        this.gifPreviewDataStore = gifPreviewDataStore;
    }

    @Override
    public Completable addToFavorites(String gifId) {
        return giphyApiService.getGifDataItem(gifId)
                .map(giphyGifResponse -> GifInfo.fromGiphyDataItem(giphyGifResponse.getGiphyDataItem()))
                .flatMapCompletable(gifInfo -> gifInfoDao.insert(gifInfo).andThen(gifPreviewDataStore.markAsFavorite(gifInfo.getId(), true)));
    }

    @Override
    public Flowable<PagingData<GifInfo>> getFavorites() {
        return PagingRx.getFlowable(
                new Pager<>(
                        new PagingConfig(20, 5, true, 20, 30),
                        gifInfoDao::getGifs
                )
        );
    }

    @Override
    public Completable deleteFromFavorites(String gifId) {
        return gifInfoDao.delete(gifId).andThen(gifPreviewDataStore.markAsFavorite(gifId, false));
    }

    @Override
    public Observable<GifInfo> getFavorite(String gifId) {
        return gifInfoDao.getGif(gifId);
    }
}
