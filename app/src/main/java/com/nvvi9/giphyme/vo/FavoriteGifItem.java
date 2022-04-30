package com.nvvi9.giphyme.vo;

import java.util.Objects;

public final class FavoriteGifItem {
    private final String id;
    private final String title;
    private final String gifUrl;
    private final String url;
    private final String authorName;
    private final String authorAvatarUrl;

    public FavoriteGifItem(String id, String title, String gifUrl, String url, String authorName, String authorAvatarUrl) {
        this.id = id;
        this.title = title;
        this.gifUrl = gifUrl;
        this.url = url;
        this.authorName = authorName;
        this.authorAvatarUrl = authorAvatarUrl;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGifUrl() {
        return gifUrl;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getAuthorAvatarUrl() {
        return authorAvatarUrl;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FavoriteGifItem)) return false;

        FavoriteGifItem that = (FavoriteGifItem) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(title, that.title)) return false;
        if (!Objects.equals(gifUrl, that.gifUrl)) return false;
        if (!Objects.equals(url, that.url)) return false;
        if (!Objects.equals(authorName, that.authorName)) return false;
        return Objects.equals(authorAvatarUrl, that.authorAvatarUrl);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (gifUrl != null ? gifUrl.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (authorName != null ? authorName.hashCode() : 0);
        result = 31 * result + (authorAvatarUrl != null ? authorAvatarUrl.hashCode() : 0);
        return result;
    }
}
