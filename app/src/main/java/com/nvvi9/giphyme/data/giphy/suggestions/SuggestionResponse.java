package com.nvvi9.giphyme.data.giphy.suggestions;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SuggestionResponse {

    @SerializedName("data")
    private List<DataItem> data;

    public List<DataItem> getData() {
        return data;
    }

    public static class DataItem {

        @SerializedName("name")
        private String name;

        public String getName() {
            return name;
        }
    }
}