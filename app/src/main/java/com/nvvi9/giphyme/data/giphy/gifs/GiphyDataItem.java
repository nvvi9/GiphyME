package com.nvvi9.giphyme.data.giphy.gifs;

import com.google.gson.annotations.SerializedName;

public final class GiphyDataItem {

    @SerializedName("import_datetime")
    private String importDatetime;

    @SerializedName("images")
    private Images images;

    @SerializedName("embed_url")
    private String embedUrl;

    @SerializedName("trending_datetime")
    private String trendingDatetime;

    @SerializedName("bitly_url")
    private String bitlyUrl;

    @SerializedName("rating")
    private String rating;

    @SerializedName("is_sticker")
    private int isSticker;

    @SerializedName("source")
    private String source;

    @SerializedName("type")
    private String type;

    @SerializedName("bitly_gif_url")
    private String bitlyGifUrl;

    @SerializedName("title")
    private String title;

    @SerializedName("source_tld")
    private String sourceTld;

    @SerializedName("url")
    private String url;

    @SerializedName("analytics_response_payload")
    private String analyticsResponsePayload;

    @SerializedName("source_post_url")
    private String sourcePostUrl;

    @SerializedName("content_url")
    private String contentUrl;

    @SerializedName("id")
    private String id;

    @SerializedName("user")
    private User user;

    @SerializedName("slug")
    private String slug;

    @SerializedName("username")
    private String username;

    public String getImportDatetime() {
        return importDatetime;
    }

    public Images getImages() {
        return images;
    }

    public String getEmbedUrl() {
        return embedUrl;
    }

    public String getTrendingDatetime() {
        return trendingDatetime;
    }

    public String getBitlyUrl() {
        return bitlyUrl;
    }

    public String getRating() {
        return rating;
    }

    public int getIsSticker() {
        return isSticker;
    }

    public String getSource() {
        return source;
    }

    public String getType() {
        return type;
    }

    public String getBitlyGifUrl() {
        return bitlyGifUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getSourceTld() {
        return sourceTld;
    }

    public String getUrl() {
        return url;
    }

    public String getAnalyticsResponsePayload() {
        return analyticsResponsePayload;
    }

    public String getSourcePostUrl() {
        return sourcePostUrl;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getSlug() {
        return slug;
    }

    public String getUsername() {
        return username;
    }
}