package com.nvvi9.giphyme.data.giphy.gifs

import com.google.gson.annotations.SerializedName

data class GiphyGifResponse(@SerializedName("data") val giphyDataItem: GiphyDataItem)