package com.nvvi9.giphyme.data.preview

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gif_preview_keys")
data class GifPreviewRemoteKeys(@PrimaryKey val id: String, val prevKey: Int?, val nextKey: Int?)