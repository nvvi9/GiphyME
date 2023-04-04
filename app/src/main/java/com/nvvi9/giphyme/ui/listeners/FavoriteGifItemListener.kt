package com.nvvi9.giphyme.ui.listeners

import com.nvvi9.giphyme.vo.FavoriteGifItem

interface FavoriteGifItemListener {
    fun onItemClick(gifItem: FavoriteGifItem)
    fun onItemLongClick(gifItem: FavoriteGifItem): Boolean
}