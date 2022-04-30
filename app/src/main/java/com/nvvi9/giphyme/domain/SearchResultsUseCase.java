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

public class SearchResultsUseCase {

    private final GifsRepository gifsRepository;
    private final FavoriteGifsRepository favoriteGifsRepository;
    private final GifItemMapper mapper;

    @Inject
    public SearchResultsUseCase(GifsRepository gifsRepository, FavoriteGifsRepository favoriteGifsRepository, GifItemMapper mapper) {
        this.gifsRepository = gifsRepository;
        this.favoriteGifsRepository = favoriteGifsRepository;
        this.mapper = mapper;
    }

    public Flowable<PagingData<GifItem>> getGifsFromQuery(String query) {
        return gifsRepository.getGifPreviews(query)
                .map(gifPreviewPagingData -> PagingDataTransforms.map(gifPreviewPagingData, Executors.newCachedThreadPool(), mapper::map));
    }

    public Completable saveInFavorites(String id) {
        return favoriteGifsRepository.addToFavorites(id);
    }
}
