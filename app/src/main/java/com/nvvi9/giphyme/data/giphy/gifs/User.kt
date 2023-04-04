package com.nvvi9.giphyme.data.giphy.gifs

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("profile_url") val profileUrl: String,
    @SerializedName("display_name") val displayName: String
)