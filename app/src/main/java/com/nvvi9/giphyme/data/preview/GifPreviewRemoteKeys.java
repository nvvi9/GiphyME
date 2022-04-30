package com.nvvi9.giphyme.data.preview;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "gif_preview_keys")
public final class GifPreviewRemoteKeys {
    @PrimaryKey
    @NonNull
    private final String id;
    private final Integer prevKey;
    private final Integer nextKey;

    public GifPreviewRemoteKeys(@NonNull String id, Integer prevKey, Integer nextKey) {
        this.id = id;
        this.prevKey = prevKey;
        this.nextKey = nextKey;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public Integer getPrevKey() {
        return prevKey;
    }

    public Integer getNextKey() {
        return nextKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GifPreviewRemoteKeys)) return false;

        GifPreviewRemoteKeys that = (GifPreviewRemoteKeys) o;

        if (!id.equals(that.id)) return false;
        if (!Objects.equals(prevKey, that.prevKey)) return false;
        return Objects.equals(nextKey, that.nextKey);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (prevKey != null ? prevKey.hashCode() : 0);
        result = 31 * result + (nextKey != null ? nextKey.hashCode() : 0);
        return result;
    }
}
