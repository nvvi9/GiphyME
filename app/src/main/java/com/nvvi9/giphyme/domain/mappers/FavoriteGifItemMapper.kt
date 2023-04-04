package com.nvvi9.giphyme.domain.mappers

import com.nvvi9.giphyme.data.BaseMapper
import com.nvvi9.giphyme.data.gifinfo.GifInfo
import com.nvvi9.giphyme.vo.FavoriteGifItem

class FavoriteGifItemMapper : BaseMapper<GifInfo, FavoriteGifItem> {

    override fun map(value: GifInfo): FavoriteGifItem =
        FavoriteGifItem(
            value.id,
            value.title,
            value.gifUrl,
            value.gifUrl,
            value.authorName,
            value.authorAvatarUrl
        )
}