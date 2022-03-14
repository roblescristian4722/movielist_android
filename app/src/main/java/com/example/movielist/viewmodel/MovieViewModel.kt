package com.example.movielist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movielist.models.ConfigurationResponse
import com.example.movielist.models.GenreGroup
import com.example.movielist.models.GenreInfoResponse
import com.example.movielist.models.PopularMovieResponse

class MovieViewModel: ViewModel() {
    val selectedMovieLiveData = MutableLiveData<PopularMovieResponse>()
    val configurationLiveData = MutableLiveData<ConfigurationResponse>()
    val popularMoviesByGenreLiveData = MutableLiveData<Map<Int, GenreGroup>>()
    val genreListLiveData = MutableLiveData<List<GenreInfoResponse>>()
    val selectedGenreMoviesLiveData = MutableLiveData<List<PopularMovieResponse>>()
    val selectedGenreLiveData = MutableLiveData<Int>()

    fun selectMovie(movie: PopularMovieResponse) {
        selectedMovieLiveData.postValue(movie)
    }
}