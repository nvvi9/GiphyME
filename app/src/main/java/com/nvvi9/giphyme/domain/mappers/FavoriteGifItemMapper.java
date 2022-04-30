package com.nvvi9.giphyme.domain.mappers;

import com.nvvi9.giphyme.data.BaseMapper;
import com.nvvi9.giphyme.data.gifinfo.GifInfo;
import com.nvvi9.giphyme.vo.FavoriteGifItem;

public class FavoriteGifItemMapper implements BaseMapper<GifInfo, FavoriteGifItem> {

    @Override
    public FavoriteGifItem map(GifInfo value) {
        return new FavoriteGifItem(value.getId(), value.getTitle(), value.getGifUrl(), value.getGifUrl(), value.getAuthorName(), value.getAuthorAvatarUrl());
    }
}
