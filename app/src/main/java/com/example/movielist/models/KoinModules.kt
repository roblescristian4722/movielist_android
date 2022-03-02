package com.example.movielist.models

import com.example.movielist.R
import com.example.movielist.viewmodel.MovieViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val module = module {
    viewModel { MovieViewModel(androidContext().getString(R.string.base_url), androidContext().getString(R.string.api_key)) }
}