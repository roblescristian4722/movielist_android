package com.example.movielist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movielist.models.GenreGroup
import com.example.movielist.models.PopularMovieResponse

class MovieViewModel(): ViewModel() {
    val movieSelectedLiveData = MutableLiveData<PopularMovieResponse>()
    val posterBaseUrlLiveData = MutableLiveData<String>()
    val logoBaseUrlLiveData = MutableLiveData<String>()
    val movieGenresLiveData = MutableLiveData<Map<Int, GenreGroup>>()

    fun selectMovie(movie: PopularMovieResponse) {
        movieSelectedLiveData.postValue(movie)
    }
}