package com.nvvi9.giphyme.vo;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class SuggestionItem {

    private final List<String> suggestions;

    public SuggestionItem(List<String> suggestions) {
        this.suggestions = suggestions;
    }

    public SuggestionItem() {
        this.suggestions = Collections.emptyList();
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SuggestionItem)) return false;

        SuggestionItem that = (SuggestionItem) o;

        return Objects.equals(suggestions, that.suggestions);
    }

    @Override
    public int hashCode() {
        return suggestions != null ? suggestions.hashCode() : 0;
    }
}
