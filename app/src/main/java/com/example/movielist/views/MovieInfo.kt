package com.example.movielist.views

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.view.marginTop
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.movielist.R
import com.example.movielist.databinding.FragmentMovieInfoBinding
import com.example.movielist.viewmodel.MovieViewModel
import kotlinx.coroutines.CoroutineScope
import okhttp3.Dispatcher

class MovieInfo : Fragment() {
    private val movieViewModel: MovieViewModel by activityViewModels()
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

        movieViewModel.movieSelectedLiveData.observe(viewLifecycleOwner, Observer {
            val baseUrl = "https://image.tmdb.org/t/p/original/"
            Glide.with(this)
                .load(baseUrl + it.poster)
                .fitCenter()
                .into(binding.ivMovieImage)
        })

        return binding.root
    }
}