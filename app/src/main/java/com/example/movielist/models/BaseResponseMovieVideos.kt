package com.example.movielist.models

import com.google.gson.annotations.SerializedName

data class BaseResponseMovieVideos(
    @SerializedName("results") val results: List<MovieVideoResponse>,
)