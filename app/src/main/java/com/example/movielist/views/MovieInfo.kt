package com.example.movielist.views

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.movielist.R
import com.example.movielist.databinding.FragmentMovieInfoBinding
import com.example.movielist.viewmodel.MovieViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MovieInfo : Fragment() {
    private lateinit var binding: FragmentMovieInfoBinding
    private val movieViewModel: MovieViewModel by sharedViewModel()
    private val player: ExoPlayer by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Creates a MediaItem out of a sample mp4 video to stream into the player
        val mediaItem = MediaItem.fromUri(Uri.parse(getString(R.string.sample_mp4_video)))
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_info, container, false)
        binding.movieInfo = movieViewModel

        binding.btnPlayTrailer.setOnClickListener {
            // When button is pressed then we play the video and navigate to VideoPlayer fragment so
            // that fullscreen gets enabled
            player.setMediaItem(mediaItem)
            player.prepare()
            player.play()
            findNavController().navigate(R.id.action_movieInfo_to_videoPlayer)
        }

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