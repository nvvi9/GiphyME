package com.nvvi9.giphyme.ui.listeners

import com.nvvi9.giphyme.vo.GifDetailsItem.GifAuthorDetailsItem

interface GifItemDetailsListener {
    fun onProfileClicked(authorDetails: GifAuthorDetailsItem)
}