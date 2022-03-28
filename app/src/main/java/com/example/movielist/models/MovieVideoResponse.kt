package com.example.movielist.models

import com.google.gson.annotations.SerializedName

data class MovieVideoResponse(
    @SerializedName("name") val name: String,
    @SerializedName("key") val key: String,
    @SerializedName("site") val site: String,
    @SerializedName("type") val type: String,
    @SerializedName("official") val official: Boolean,
)
