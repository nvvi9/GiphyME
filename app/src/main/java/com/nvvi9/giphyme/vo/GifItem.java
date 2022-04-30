package com.nvvi9.giphyme.vo;

import java.util.Objects;

public final class GifItem {
    private final String id;
    private final String title;
    private final String url;
    private final String previewUrl;
    private final Boolean inFavourites;

    public GifItem(String id, String title, String url, String previewUrl, Boolean inFavourites) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.previewUrl = previewUrl;
        this.inFavourites = inFavourites;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public Boolean getInFavourites() {
        return inFavourites;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GifItem)) return false;

        GifItem gifItem = (GifItem) o;

        if (!Objects.equals(id, gifItem.id)) return false;
        if (!Objects.equals(title, gifItem.title)) return false;
        if (!Objects.equals(url, gifItem.url)) return false;
        if (!Objects.equals(previewUrl, gifItem.previewUrl)) return false;
        return Objects.equals(inFavourites, gifItem.inFavourites);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (previewUrl != null ? previewUrl.hashCode() : 0);
        result = 31 * result + (inFavourites != null ? inFavourites.hashCode() : 0);
        return result;
    }
}
