package com.nvvi9.giphyme.data.giphy.gifs

import com.google.gson.annotations.SerializedName

data class GiphyResponse(
    @SerializedName("pagination") val pagination: Pagination,
    @SerializedName("data") val data: List<GiphyDataItem>
)