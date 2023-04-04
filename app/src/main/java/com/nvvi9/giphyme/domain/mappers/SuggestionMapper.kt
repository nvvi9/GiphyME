package com.nvvi9.giphyme.domain.mappers

import com.nvvi9.giphyme.data.BaseMapper
import com.nvvi9.giphyme.data.giphy.suggestions.SuggestionResponse
import com.nvvi9.giphyme.data.giphy.suggestions.SuggestionResponse.DataItem
import com.nvvi9.giphyme.vo.SuggestionItem
import java.util.function.Function
import java.util.stream.Collectors

class SuggestionMapper : BaseMapper<SuggestionResponse, SuggestionItem> {

    override fun map(value: SuggestionResponse): SuggestionItem =
        SuggestionItem(value.data.map { it.name })
}