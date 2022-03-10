package com.example.movielist.models

import com.example.movielist.R
import com.example.movielist.retrofitdefinitions.TMDBServiceDefinition
import com.example.movielist.retrofitservices.TMDBService
import com.example.movielist.viewmodel.MovieViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Module made for Dependency Injection
 */
val module = module {
    // Retrofit API for using http methods
    single {
        val movieAPI = Retrofit.Builder()
            .baseUrl(androidContext().getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        movieAPI.create(TMDBServiceDefinition::class.java)
    }
    // ViewModel on activity level
    single { MovieViewModel() }
    // Retrofit service that uses the http methods defined on Retrofit API to fetch data from TMDB server
    single {
        TMDBService(androidContext().getString(R.string.api_key),
                    androidContext().resources.getInteger(R.integer.max_home_pages),
                    get(),
                    get())
    }
}