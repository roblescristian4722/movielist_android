package com.example.movielist.models

data class GenreGroup(
    val name: String,
    val movies: MutableList<PopularMovieResponse>
)