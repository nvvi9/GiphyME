package com.nvvi9.giphyme.data.giphy.gifs;

import com.google.gson.annotations.SerializedName;

public final class GiphyGifResponse {

    @SerializedName("data")
    private GiphyDataItem giphyDataItem;

    public GiphyDataItem getGiphyDataItem() {
        return giphyDataItem;
    }
}
