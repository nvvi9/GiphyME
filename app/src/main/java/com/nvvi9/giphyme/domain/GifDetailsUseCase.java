package com.nvvi9.giphyme.domain;

import com.nvvi9.giphyme.domain.mappers.GifDetailsItemMapper;
import com.nvvi9.giphyme.repositories.base.FavoriteGifsRepository;
import com.nvvi9.giphyme.vo.GifDetailsItem;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class GifDetailsUseCase {

    private final FavoriteGifsRepository repository;
    private final GifDetailsItemMapper mapper;

    @Inject
    public GifDetailsUseCase(FavoriteGifsRepository repository, GifDetailsItemMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Observable<GifDetailsItem> getGifDetails(String gifId) {
        return repository.getFavorite(gifId)
                .map(mapper::map);
    }
}
