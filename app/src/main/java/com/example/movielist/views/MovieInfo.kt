package com.example.movielist.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.movielist.R
import com.example.movielist.databinding.FragmentMovieInfoBinding
import com.example.movielist.viewmodel.MovieViewModel
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_info, container, false)
        binding.movieInfo = movieViewModel

        val youtubePlayerFragment = YouTubePlayerSupportFragmentX.newInstance()
        youtubePlayerFragment.initialize(getString(R.string.youtube_api_key), object: YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                p1: YouTubePlayer?,
                p2: Boolean
            ) {
                p1?.loadVideo("9hIRq5HTh5s")
                p1?.play()
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Log.d("player", "$p1")
            }

        })
        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_youtube_player_holder, youtubePlayerFragment).commit()

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