package com.nvvi9.giphyme.data.giphy.gifs;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public final class GiphyResponse {

    @SerializedName("pagination")
    private Pagination pagination;

    @SerializedName("data")
    private List<GiphyDataItem> data;

    public Pagination getPagination() {
        return pagination;
    }

    public List<GiphyDataItem> getData() {
        return data;
    }
}