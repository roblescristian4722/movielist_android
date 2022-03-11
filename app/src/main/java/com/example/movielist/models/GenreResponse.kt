package com.example.movielist.models

import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @SerializedName("genres") val genres: List<GenreInfoResponse>
)