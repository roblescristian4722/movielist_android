package com.example.movielist.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.movielist.R
import com.example.movielist.adapters.MoviesByGenreAdapter
import com.example.movielist.databinding.FragmentMoviesByGenreBinding
import com.example.movielist.retrofitservices.TMDBService
import com.example.movielist.viewmodel.MovieViewModel
import org.koin.android.ext.android.inject

class MoviesByGenre : Fragment() {
    private lateinit var binding: FragmentMoviesByGenreBinding
    private val movieViewModel: MovieViewModel by inject()
    private val movieService: TMDBService by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val adapter = MoviesByGenreAdapter(movieViewModel, movieService, requireContext())
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies_by_genre, container, false)
        binding.rvMoviesById.adapter = adapter
        movieViewModel.selectedGenreMoviesLiveData.observe(viewLifecycleOwner, Observer {
            adapter.updateList(it.toMutableList())
        })

        return binding.root
    }
}