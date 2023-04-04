package com.nvvi9.giphyme.vo

import java.util.*

data class GifDetailsItem(
    val id: String,
    val title: String,
    val gifUrl: String,
    val url: String,
    val gifAuthorDetailsItem: GifAuthorDetailsItem,
    val createdAt: Date
) {
    data class GifAuthorDetailsItem(val name: String, val avatarUrl: String, val profileUrl: String)
}