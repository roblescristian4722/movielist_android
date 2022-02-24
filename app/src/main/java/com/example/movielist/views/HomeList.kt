package com.example.movielist.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.movielist.R
import com.example.movielist.databinding.FragmentHomeListBinding
import com.example.movielist.viewmodel.MovieViewModel
import com.example.movielist.views.adapters.MovieListAdapter

class HomeList : Fragment() {
    private lateinit var binding: FragmentHomeListBinding
    private val movieViewModel: MovieViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val adapter = MovieListAdapter(movieViewModel)
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_list, container, false)
        binding.rvMovieList.adapter = adapter
        movieViewModel.movieLiveData.observe(viewLifecycleOwner, Observer {
            adapter.updateList(it)
        })
        movieViewModel.updateMovieList()

        return binding.root
    }

}