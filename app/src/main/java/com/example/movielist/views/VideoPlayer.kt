package com.example.movielist.views

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import com.example.movielist.R
import com.example.movielist.databinding.FragmentVideoPlayerBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
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
        binding.spvPlayer.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL

        binding.spvPlayer.setOnKeyListener(object: View.OnKeyListener {
            override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
                if (p2?.action == KeyEvent.ACTION_DOWN) {
                    if (p1 == KeyEvent.KEYCODE_DPAD_CENTER) {
                        (binding.spvPlayer.player as ExoPlayer).playWhenReady = !(binding.spvPlayer.player as ExoPlayer).playWhenReady
                        return true
                    }
                }
                return false
            }

        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Enables fullscreen mode both for API < 30 and API >= 30
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().window.insetsController?.hide(WindowInsets.Type.statusBars())
            requireActivity().window.insetsController?.hide(WindowInsets.Type.navigationBars())
        } else {
            requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
    }

    override fun onDetach() {
        super.onDetach()
        // Disables fullscreen mode both for API < 30 and API >= 30 when back button is pressed
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().window.insetsController?.show(WindowInsets.Type.statusBars())
            requireActivity().window.insetsController?.show(WindowInsets.Type.navigationBars())
        } else {
            requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
    }
}