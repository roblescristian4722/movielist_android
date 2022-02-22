package com.example.movielist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movielist.models.TMDBService
import com.example.movielist.models.dataclasses.PopularMovieResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieViewModel: ViewModel() {
    private val baseUrl = "https://api.themoviedb.org/"
    private val apiKey = "8cf6b9028b7faea32272410623cc9121"
    private val movieAPI = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val movieService = movieAPI.create(TMDBService::class.java)
    val movieLiveData = MutableLiveData<List<PopularMovieResponse>>()
    val movieSelectedLiveData = MutableLiveData<PopularMovieResponse>()

    init {
        updateMovieList()
    }

    fun updateMovieList() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = movieService.getPopularMovies(apiKey)
            val body = call.body()
            body?.let {
                movieLiveData.postValue(it.results)
            }
        }
    }

    fun selectMovie(movie: PopularMovieResponse) {
        movieSelectedLiveData.postValue(movie)
    }
}