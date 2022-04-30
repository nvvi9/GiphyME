package com.nvvi9.giphyme.data.giphy.gifs;

import com.google.gson.annotations.SerializedName;

public final class Pagination {

    @SerializedName("offset")
    private int offset;

    @SerializedName("total_count")
    private int totalCount;

    @SerializedName("count")
    private int count;

    public int getOffset() {
        return offset;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getCount() {
        return count;
    }
}