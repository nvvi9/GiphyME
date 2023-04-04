package com.nvvi9.giphyme.data.gifinfo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nvvi9.giphyme.data.giphy.gifs.GiphyDataItem
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "gif_infos")
data class GifInfo(
    @PrimaryKey val id: String,
    val title: String,
    val url: String,
    val gifUrl: String,
    val authorName: String,
    val authorAvatarUrl: String,
    val authorProfileUrl: String,
    val createdAt: Date,
) {

    companion object {

        fun fromGiphyDataItem(giphyDataItem: GiphyDataItem): GifInfo =
            with(giphyDataItem) {
                GifInfo(
                    id,
                    title,
                    url,
                    images.original.url,
                    user.displayName,
                    user.avatarUrl,
                    user.profileUrl,
                    SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                        .parse(importDatetime)
                )
            }
    }
}