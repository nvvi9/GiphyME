package com.nvvi9.giphyme.data.giphy.suggestions

import com.google.gson.annotations.SerializedName

data class SuggestionResponse(@SerializedName("data") val data: List<DataItem>) {
    data class DataItem(@SerializedName("name") val name: String)
}