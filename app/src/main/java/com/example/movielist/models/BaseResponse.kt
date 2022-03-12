package com.example.movielist.models

import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("results") val results: List<PopularMovieResponse>,
    @SerializedName("images") val images: ConfigurationResponse,
)
