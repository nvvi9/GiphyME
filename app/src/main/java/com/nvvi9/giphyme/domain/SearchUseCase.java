package com.nvvi9.giphyme.domain;

import com.nvvi9.giphyme.domain.mappers.SuggestionMapper;
import com.nvvi9.giphyme.repositories.base.GifsRepository;
import com.nvvi9.giphyme.vo.SuggestionItem;

import java.util.Collections;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class SearchUseCase {

    private final GifsRepository gifsRepository;
    private final SuggestionMapper mapper;

    @Inject
    public SearchUseCase(GifsRepository gifsRepository, SuggestionMapper mapper) {
        this.gifsRepository = gifsRepository;
        this.mapper = mapper;
    }

    public Single<SuggestionItem> getSuggestion(String query) {
        return query.isEmpty() ? Single.just(new SuggestionItem()) : gifsRepository.getSuggestion(query)
                .map(mapper::map)
                .onErrorReturn(t -> new SuggestionItem(Collections.emptyList()));
    }
}
