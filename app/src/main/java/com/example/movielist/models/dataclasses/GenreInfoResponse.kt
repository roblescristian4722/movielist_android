package com.example.movielist.models.dataclasses

import com.google.gson.annotations.SerializedName

data class GenreInfoResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
)
