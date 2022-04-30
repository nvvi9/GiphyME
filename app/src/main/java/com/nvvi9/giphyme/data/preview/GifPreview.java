package com.nvvi9.giphyme.data.preview;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.nvvi9.giphyme.data.giphy.gifs.GiphyDataItem;
import com.nvvi9.giphyme.data.giphy.gifs.Images;

import java.util.Objects;

@Entity(tableName = "gif_previews")
public final class GifPreview {
    @PrimaryKey
    @NonNull
    private final String id;
    private final String title;
    private final String url;
    private final String previewUrl;
    private final Integer height;
    private final Integer width;
    private final Integer size;
    private final Boolean inFavorites;

    public GifPreview(@NonNull String id, String title, String url, String previewUrl, Integer height, Integer width, Integer size, Boolean inFavorites) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.previewUrl = previewUrl;
        this.height = height;
        this.width = width;
        this.size = size;
        this.inFavorites = inFavorites;
    }

    public static GifPreview fromGiphyDataItem(GiphyDataItem giphyDataItem) {
        Images.Downsized downsized = giphyDataItem.getImages().getDownsized();
        String id = giphyDataItem.getId();
        String title = giphyDataItem.getTitle();
        String url = giphyDataItem.getUrl();
        String previewUrl = downsized.getUrl();
        Integer height = Integer.parseInt(downsized.getHeight());
        Integer width = Integer.parseInt(downsized.getWidth());
        Integer size = Integer.parseInt(downsized.getSize());

        return new GifPreview(id, title, url, previewUrl, height, width, size, false);
    }

    @NonNull
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

    public Integer getHeight() {
        return height;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getSize() {
        return size;
    }

    public Boolean getInFavorites() {
        return inFavorites;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GifPreview)) return false;

        GifPreview that = (GifPreview) o;

        if (!id.equals(that.id)) return false;
        if (!Objects.equals(title, that.title)) return false;
        if (!Objects.equals(url, that.url)) return false;
        if (!Objects.equals(previewUrl, that.previewUrl))
            return false;
        if (!Objects.equals(height, that.height)) return false;
        if (!Objects.equals(width, that.width)) return false;
        if (!Objects.equals(size, that.size)) return false;
        return Objects.equals(inFavorites, that.inFavorites);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (previewUrl != null ? previewUrl.hashCode() : 0);
        result = 31 * result + (height != null ? height.hashCode() : 0);
        result = 31 * result + (width != null ? width.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + (inFavorites != null ? inFavorites.hashCode() : 0);
        return result;
    }
}
