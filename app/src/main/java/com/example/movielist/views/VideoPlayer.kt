package com.example.movielist.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.movielist.R
import com.example.movielist.databinding.FragmentVideoPlayerBinding
import com.google.android.exoplayer2.ExoPlayer
import org.koin.android.ext.android.inject

class VideoPlayer : Fragment() {
    private lateinit var binding: FragmentVideoPlayerBinding
    private val player: ExoPlayer by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_video_player, container, false)
        binding.spvPlayer.player = player

        return binding.root
    }
}