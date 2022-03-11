package com.example.movielist.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.movielist.R
import com.example.movielist.databinding.FragmentMovieInfoBinding
import com.example.movielist.viewmodel.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MovieInfo : Fragment() {
    private val movieViewModel: MovieViewModel by sharedViewModel()
    private lateinit var binding: FragmentMovieInfoBinding

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

        movieViewModel.selectedMovieLiveData.observe(viewLifecycleOwner, Observer {
            movieViewModel.configurationLiveData.value?.let { configuration ->
                Glide.with(this)
                    .load(getString(R.string.image_base_url) + configuration.posterSizes[configuration.posterSizes.size - 1] + it.poster)
                    .fitCenter()
                    .into(binding.ivMovieImage)

            }
        })

        return binding.root
    }
}