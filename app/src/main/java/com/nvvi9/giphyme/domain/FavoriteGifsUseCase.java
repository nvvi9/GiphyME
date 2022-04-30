package com.nvvi9.giphyme.domain;

import androidx.paging.PagingData;
import androidx.paging.PagingDataTransforms;

import com.nvvi9.giphyme.domain.mappers.FavoriteGifItemMapper;
import com.nvvi9.giphyme.repositories.base.FavoriteGifsRepository;
import com.nvvi9.giphyme.vo.FavoriteGifItem;

import java.util.concurrent.Executors;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class FavoriteGifsUseCase {

    private final FavoriteGifsRepository repository;
    private final FavoriteGifItemMapper mapper;

    @Inject
    public FavoriteGifsUseCase(FavoriteGifsRepository repository, FavoriteGifItemMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Flowable<PagingData<FavoriteGifItem>> getFavorites() {
        return repository.getFavorites()
                .map(gifInfoPagingData -> PagingDataTransforms.map(gifInfoPagingData, Executors.newCachedThreadPool(), mapper::map));
    }

    public Completable deleteFromFavorites(String gifId) {
        return repository.deleteFromFavorites(gifId);
    }
}
