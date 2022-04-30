package com.nvvi9.giphyme.domain;

import androidx.paging.PagingData;
import androidx.paging.PagingDataTransforms;

import com.nvvi9.giphyme.domain.mappers.GifItemMapper;
import com.nvvi9.giphyme.repositories.base.FavoriteGifsRepository;
import com.nvvi9.giphyme.repositories.base.GifsRepository;
import com.nvvi9.giphyme.vo.GifItem;

import java.util.concurrent.Executors;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class TrendingGifsUseCase {

    private final GifsRepository gifsRepository;
    private final FavoriteGifsRepository favoriteGifsRepository;
    private final GifItemMapper mapper;

    @Inject
    public TrendingGifsUseCase(GifsRepository gifsRepository, FavoriteGifsRepository favoriteGifsRepository, GifItemMapper mapper) {
        this.gifsRepository = gifsRepository;
        this.favoriteGifsRepository = favoriteGifsRepository;
        this.mapper = mapper;
    }

    public Flowable<PagingData<GifItem>> getTrendingGifs() {
        return gifsRepository.getGifPreviews()
                .map(data -> PagingDataTransforms.map(data, Executors.newCachedThreadPool(), mapper::map));
    }

    public Completable addToFavorites(String gifId) {
        return favoriteGifsRepository.addToFavorites(gifId);
    }

    public Completable removeFromFavorites(String gifId) {
        return favoriteGifsRepository.deleteFromFavorites(gifId);
    }
}
