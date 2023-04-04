package com.nvvi9.giphyme.data.giphy.gifs

import com.google.gson.annotations.SerializedName

data class GiphyDataItem(
    @SerializedName("import_datetime") val importDatetime: String,
    @SerializedName("images") val images: Images,
    @SerializedName("title") val title: String,
    @SerializedName("url") val url: String,
    @SerializedName("id") val id: String,
    @SerializedName("user") val user: User,
)