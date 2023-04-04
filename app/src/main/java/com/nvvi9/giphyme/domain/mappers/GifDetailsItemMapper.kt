package com.nvvi9.giphyme.domain.mappers

import com.nvvi9.giphyme.data.BaseMapper
import com.nvvi9.giphyme.data.gifinfo.GifInfo
import com.nvvi9.giphyme.vo.GifDetailsItem
import com.nvvi9.giphyme.vo.GifDetailsItem.GifAuthorDetailsItem

class GifDetailsItemMapper : BaseMapper<GifInfo, GifDetailsItem> {

    override fun map(value: GifInfo): GifDetailsItem =
        GifDetailsItem(
            value.id,
            value.title,
            value.gifUrl,
            value.url,
            GifAuthorDetailsItem(value.authorName, value.authorAvatarUrl, value.authorProfileUrl),
            value.createdAt
        )
}