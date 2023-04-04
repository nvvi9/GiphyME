package com.nvvi9.giphyme.domain

import com.nvvi9.giphyme.domain.mappers.SuggestionMapper
import com.nvvi9.giphyme.repositories.base.GifsRepository
import com.nvvi9.giphyme.vo.SuggestionItem
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val gifsRepository: GifsRepository,
    private val mapper: SuggestionMapper
) {

    fun getSuggestion(query: String): Single<SuggestionItem> =
        if (query.isEmpty()) Single.just(SuggestionItem()) else gifsRepository.getSuggestion(query)
            .map { mapper.map(it) }
            .onErrorReturn { SuggestionItem(emptyList()) }
}