package com.example.movielist

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.movielist.retrofitservices.TMDBService
import com.example.movielist.viewmodel.MovieViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class TvActivity : AppCompatActivity() {
    private val movieViewModel: MovieViewModel by viewModel()
    private val movieService: TMDBService by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv)

        movieService.getConfiguration()
        movieService.getGenres()
        movieService.updatePopularMovieList()
    }
}