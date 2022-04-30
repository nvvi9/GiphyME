package com.nvvi9.giphyme.domain.mappers;

import com.nvvi9.giphyme.data.BaseMapper;
import com.nvvi9.giphyme.data.giphy.suggestions.SuggestionResponse;
import com.nvvi9.giphyme.vo.SuggestionItem;

import java.util.List;
import java.util.stream.Collectors;

public class SuggestionMapper implements BaseMapper<SuggestionResponse, SuggestionItem> {

    @Override
    public SuggestionItem map(SuggestionResponse value) {
        List<String> suggestions = value.getData().stream()
                .map(SuggestionResponse.DataItem::getName)
                .collect(Collectors.toList());

        return new SuggestionItem(suggestions);
    }
}
