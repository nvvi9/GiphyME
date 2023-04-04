package com.nvvi9.giphyme.domain.mappers

import com.nvvi9.giphyme.data.BaseMapper
import com.nvvi9.giphyme.data.preview.GifPreview
import com.nvvi9.giphyme.vo.GifItem

class GifItemMapper : BaseMapper<GifPreview, GifItem> {

    override fun map(value: GifPreview): GifItem =
        GifItem(value.id, value.title, value.url, value.previewUrl, value.inFavorites)
}