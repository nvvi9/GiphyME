package com.nvvi9.giphyme.data.giphy.gifs

import com.google.gson.annotations.SerializedName

data class Images(
    @SerializedName("original") val original: Original,
    @SerializedName("downsized") val downsized: Downsized,
) {
    data class Downsized(
        @SerializedName("size") val size: String,
        @SerializedName("width") val width: String,
        @SerializedName("url") val url: String,
        @SerializedName("height") val height: String
    )

    data class Original(
        @SerializedName("width") val width: String,
        @SerializedName("url") val url: String,
        @SerializedName("height") val height: String
    )
}