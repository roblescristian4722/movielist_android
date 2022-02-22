package com.example.movielist.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.movielist.R
import com.example.movielist.databinding.FragmentMovieInfoBinding
import com.example.movielist.viewmodel.MovieViewModel

class MovieInfo : Fragment() {
    val movieViewModel: MovieViewModel by activityViewModels()
    lateinit var binding: FragmentMovieInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_info,container, false)
        binding.movieInfo = movieViewModel

        movieViewModel.movieSelectedLiveData.observe(viewLifecycleOwner, Observer {
            val baseUrl = "https://image.tmdb.org/t/p/original/"
            Glide.with(this).load(baseUrl + it.poster).into(binding.ivMovieImage)
        })

        binding.svMovieInfo.viewTreeObserver.addOnScrollChangedListener {
        }

        return binding.root
    }
}