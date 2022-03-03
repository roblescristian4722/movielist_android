package com.example.movielist.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.movielist.R
import com.example.movielist.databinding.FragmentHomeListBinding
import com.example.movielist.viewmodel.MovieViewModel
import com.example.movielist.views.adapters.MovieListAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeList : Fragment() {
    private val movieViewModel: MovieViewModel by sharedViewModel()
    private lateinit var binding: FragmentHomeListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_list, container, false)

        // Adapter for RecyclerView gets created and bound
        val adapter = MovieListAdapter(movieViewModel)
        binding.rvMovieList.adapter = adapter

        // Everytime the livedata gets updated the adapter updates too
        movieViewModel.movieLiveData.observe(viewLifecycleOwner, Observer {
            adapter.updateList(it)
        })

        // Updates the movie list every time this fragment gets rendered
        movieViewModel.updateMovieList()
        return binding.root
    }
}