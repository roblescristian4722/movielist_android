package com.example.movielist.models.dataclasses

data class GenreGroup(
    val name: String,
    val movies: MutableList<PopularMovieResponse>
)
