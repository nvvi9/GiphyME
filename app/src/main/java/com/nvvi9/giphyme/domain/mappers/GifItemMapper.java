package com.nvvi9.giphyme.domain.mappers;

import com.nvvi9.giphyme.data.BaseMapper;
import com.nvvi9.giphyme.data.preview.GifPreview;
import com.nvvi9.giphyme.vo.GifItem;

public class GifItemMapper implements BaseMapper<GifPreview, GifItem> {

    @Override
    public GifItem map(GifPreview value) {
        return new GifItem(value.getId(), value.getTitle(), value.getUrl(), value.getPreviewUrl(), value.getInFavorites());
    }
}
