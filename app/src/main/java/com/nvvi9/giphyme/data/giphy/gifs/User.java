package com.nvvi9.giphyme.data.giphy.gifs;

import com.google.gson.annotations.SerializedName;

public final class User {

    @SerializedName("avatar_url")
    private String avatarUrl;

    @SerializedName("instagram_url")
    private String instagramUrl;

    @SerializedName("website_url")
    private String websiteUrl;

    @SerializedName("profile_url")
    private String profileUrl;

    @SerializedName("description")
    private String description;

    @SerializedName("banner_url")
    private String bannerUrl;

    @SerializedName("banner_image")
    private String bannerImage;

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("is_verified")
    private boolean isVerified;

    @SerializedName("username")
    private String username;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getInstagramUrl() {
        return instagramUrl;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isIsVerified() {
        return isVerified;
    }

    public String getUsername() {
        return username;
    }
}