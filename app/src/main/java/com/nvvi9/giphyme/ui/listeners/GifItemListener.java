package com.nvvi9.giphyme.ui.listeners;

import com.nvvi9.giphyme.vo.GifItem;

public interface GifItemListener {

    void onItemClicked(GifItem gifItem);

    boolean onItemLongClicked(GifItem gifItem);
}
