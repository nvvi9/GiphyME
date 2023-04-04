package com.nvvi9.giphyme.data.preview

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nvvi9.giphyme.data.giphy.gifs.GiphyDataItem

@Entity(tableName = "gif_previews")
data class GifPreview(
    @PrimaryKey val id: String,
    val title: String,
    val url: String,
    val previewUrl: String,
    val height: Int,
    val width: Int,
    val size: Int,
    val inFavorites: Boolean
) {

    companion object {

        fun fromGiphyDataItem(giphyDataItem: GiphyDataItem): GifPreview =
            with(giphyDataItem) {
                GifPreview(
                    id,
                    title,
                    url,
                    images.downsized.url,
                    images.downsized.height.toInt(),
                    images.downsized.width.toInt(),
                    images.downsized.size.toInt(),
                    false
                )
            }
    }
}