package com.example.movielist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movielist.retrofitservices.TMDBService
import com.example.movielist.viewmodel.MovieViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val movieViewModel: MovieViewModel by viewModel()
    private val movieService: TMDBService by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        movieService.getConfiguration()
        movieService.getGenres()
        movieService.updatePopularMovieList()
    }
}