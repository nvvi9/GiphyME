package com.nvvi9.giphyme.ui.listeners

import com.nvvi9.giphyme.vo.GifItem

interface GifItemListener {
    fun onItemClicked(gifItem: GifItem)
    fun onItemLongClicked(gifItem: GifItem): Boolean
}