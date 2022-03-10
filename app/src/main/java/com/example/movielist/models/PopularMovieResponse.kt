package com.example.movielist.models

import com.google.gson.annotations.SerializedName

data class PopularMovieResponse(
    @SerializedName("title") val title: String,
    @SerializedName("id") val id: Int,
    @SerializedName("vote_average") val voteAverage: Float,
    @SerializedName("poster_path") val poster: String,
    @SerializedName("release_date") val date: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("genre_ids") val genreIds: List<Int>,
)
