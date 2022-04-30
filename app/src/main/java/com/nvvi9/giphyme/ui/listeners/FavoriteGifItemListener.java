package com.nvvi9.giphyme.ui.listeners;

import com.nvvi9.giphyme.vo.FavoriteGifItem;

public interface FavoriteGifItemListener {
    void onItemClick(FavoriteGifItem gifItem);

    boolean onItemLongClick(FavoriteGifItem gifItem);
}
