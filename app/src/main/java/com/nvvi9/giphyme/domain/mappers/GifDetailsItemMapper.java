package com.nvvi9.giphyme.domain.mappers;

import com.nvvi9.giphyme.data.BaseMapper;
import com.nvvi9.giphyme.data.gifinfo.GifInfo;
import com.nvvi9.giphyme.vo.GifDetailsItem;

public class GifDetailsItemMapper implements BaseMapper<GifInfo, GifDetailsItem> {

    @Override
    public GifDetailsItem map(GifInfo value) {
        GifDetailsItem.GifAuthorDetailsItem gifAuthorDetailsItem = new GifDetailsItem.GifAuthorDetailsItem(value.getAuthorName(), value.getAuthorAvatarUrl(), value.getAuthorProfileUrl());
        return new GifDetailsItem(value.getId(), value.getTitle(), value.getGifUrl(), value.getUrl(), gifAuthorDetailsItem, value.getCreatedAt());
    }
}
