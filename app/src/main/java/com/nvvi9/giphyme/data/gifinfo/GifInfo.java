package com.nvvi9.giphyme.data.gifinfo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.nvvi9.giphyme.data.giphy.gifs.GiphyDataItem;
import com.nvvi9.giphyme.data.giphy.gifs.Images;
import com.nvvi9.giphyme.data.giphy.gifs.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

@Entity(tableName = "gif_infos")
public final class GifInfo {
    @PrimaryKey
    @NonNull
    private final String id;
    private final String title;
    private final String url;
    private final String gifUrl;
    private final Integer height;
    private final Integer width;
    private final Integer size;
    private final String authorName;
    private final String authorAvatarUrl;
    private final String authorProfileUrl;
    private final Date createdAt;
    private final Date savedAt;

    public GifInfo(@NonNull String id, String title, String url, String gifUrl, Integer height, Integer width, Integer size, String authorName, String authorAvatarUrl, String authorProfileUrl, Date createdAt, Date savedAt) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.gifUrl = gifUrl;
        this.height = height;
        this.width = width;
        this.size = size;
        this.authorName = authorName;
        this.authorAvatarUrl = authorAvatarUrl;
        this.authorProfileUrl = authorProfileUrl;
        this.createdAt = createdAt;
        this.savedAt = savedAt;
    }

    public static GifInfo fromGiphyDataItem(GiphyDataItem giphyDataItem) throws ParseException {
        Images.Original original = giphyDataItem.getImages().getOriginal();
        String id = giphyDataItem.getId();
        String title = giphyDataItem.getTitle();
        String url = giphyDataItem.getUrl();
        String gifUrl = original.getUrl();
        Integer height = Integer.parseInt(original.getHeight());
        Integer width = Integer.parseInt(original.getWidth());
        Integer size = Integer.parseInt(original.getSize());

        User user = giphyDataItem.getUser();

        String authorName = user != null ? user.getDisplayName() : null;
        String authorAvatarUrl = user != null ? user.getAvatarUrl() : null;
        String authorProfileUrl = user != null ? user.getProfileUrl() : null;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date createdAt = simpleDateFormat.parse(giphyDataItem.getImportDatetime());
        Date savedAt = new Date();

        return new GifInfo(id, title, url, gifUrl, height, width, size, authorName, authorAvatarUrl, authorProfileUrl, createdAt, savedAt);
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGifUrl() {
        return gifUrl;
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

    public String getAuthorName() {
        return authorName;
    }

    public String getAuthorAvatarUrl() {
        return authorAvatarUrl;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getSavedAt() {
        return savedAt;
    }

    public String getAuthorProfileUrl() {
        return authorProfileUrl;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GifInfo)) return false;

        GifInfo gifInfo = (GifInfo) o;

        if (!id.equals(gifInfo.id)) return false;
        if (!Objects.equals(title, gifInfo.title)) return false;
        if (!Objects.equals(url, gifInfo.url)) return false;
        if (!Objects.equals(gifUrl, gifInfo.gifUrl)) return false;
        if (!Objects.equals(height, gifInfo.height)) return false;
        if (!Objects.equals(width, gifInfo.width)) return false;
        if (!Objects.equals(size, gifInfo.size)) return false;
        if (!Objects.equals(authorName, gifInfo.authorName)) return false;
        if (!Objects.equals(authorAvatarUrl, gifInfo.authorAvatarUrl)) return false;
        if (!Objects.equals(authorProfileUrl, gifInfo.authorProfileUrl)) return false;
        if (!Objects.equals(createdAt, gifInfo.createdAt)) return false;
        return Objects.equals(savedAt, gifInfo.savedAt);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (gifUrl != null ? gifUrl.hashCode() : 0);
        result = 31 * result + (height != null ? height.hashCode() : 0);
        result = 31 * result + (width != null ? width.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + (authorName != null ? authorName.hashCode() : 0);
        result = 31 * result + (authorAvatarUrl != null ? authorAvatarUrl.hashCode() : 0);
        result = 31 * result + (authorProfileUrl != null ? authorProfileUrl.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (savedAt != null ? savedAt.hashCode() : 0);
        return result;
    }
}
