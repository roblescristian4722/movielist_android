package com.example.movielist.views

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.movielist.R
import com.example.movielist.databinding.FragmentMovieInfoBinding
import com.example.movielist.viewmodel.MovieViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MovieInfo : Fragment() {
    private val movieViewModel: MovieViewModel by sharedViewModel()
    private lateinit var binding: FragmentMovieInfoBinding
    private var player: ExoPlayer? = null

    private fun instantiatePlayer() {
        if (player == null) {
            player = ExoPlayer.Builder(requireContext()).build()
                .also {

                }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fl_player_container, VideoPlayer(), "video_player")
        fragmentTransaction.commit()

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_info, container, false)
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