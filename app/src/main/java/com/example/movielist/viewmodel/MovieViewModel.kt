package com.example.movielist.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movielist.models.TMDBService
import com.example.movielist.models.dataclasses.PopularMovieResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieViewModel(private val baseUrl: String, private val apiKey: String): ViewModel() {
    private val movieAPI = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val movieService = movieAPI.create(TMDBService::class.java)
    val movieLiveData = MutableLiveData<List<PopularMovieResponse>>()
    val movieSelectedLiveData = MutableLiveData<PopularMovieResponse>()

    init {
        Log.d("baseUrl", baseUrl)
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