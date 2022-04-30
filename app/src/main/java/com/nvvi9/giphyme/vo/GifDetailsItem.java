package com.nvvi9.giphyme.vo;

import java.util.Date;
import java.util.Objects;

public final class GifDetailsItem {

    private final String id;
    private final String title;
    private final String gifUrl;
    private final String url;
    private final GifAuthorDetailsItem gifAuthorDetailsItem;
    private final Date createdAt;

    public GifDetailsItem(String id, String title, String gifUrl, String url, GifAuthorDetailsItem gifAuthorDetailsItem, Date createdAt) {
        this.id = id;
        this.title = title;
        this.gifUrl = gifUrl;
        this.url = url;
        this.gifAuthorDetailsItem = gifAuthorDetailsItem;
        this.createdAt = createdAt;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public GifAuthorDetailsItem getGifAuthorDetailsItem() {
        return gifAuthorDetailsItem;
    }

    public String getUrl() {
        return url;
    }

    public static final class GifAuthorDetailsItem {
        private final String name;
        private final String avatarUrl;
        private final String profileUrl;

        public GifAuthorDetailsItem(String name, String avatarUrl, String profileUrl) {
            this.name = name;
            this.avatarUrl = avatarUrl;
            this.profileUrl = profileUrl;
        }

        public String getName() {
            return name;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public String getProfileUrl() {
            return profileUrl;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GifAuthorDetailsItem)) return false;

            GifAuthorDetailsItem that = (GifAuthorDetailsItem) o;

            if (!Objects.equals(name, that.name)) return false;
            if (!Objects.equals(avatarUrl, that.avatarUrl))
                return false;
            return Objects.equals(profileUrl, that.profileUrl);
        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + (avatarUrl != null ? avatarUrl.hashCode() : 0);
            result = 31 * result + (profileUrl != null ? profileUrl.hashCode() : 0);
            return result;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GifDetailsItem)) return false;

        GifDetailsItem that = (GifDetailsItem) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(title, that.title)) return false;
        if (!Objects.equals(gifUrl, that.gifUrl)) return false;
        if (!Objects.equals(url, that.url)) return false;
        if (!Objects.equals(gifAuthorDetailsItem, that.gifAuthorDetailsItem))
            return false;
        return Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (gifUrl != null ? gifUrl.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (gifAuthorDetailsItem != null ? gifAuthorDetailsItem.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }
}
