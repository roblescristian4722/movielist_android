package com.example.movielist.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.movielist.R
import com.example.movielist.databinding.FragmentHomeListBinding
import com.example.movielist.models.module
import com.example.movielist.viewmodel.MovieViewModel
import com.example.movielist.views.adapters.MovieListAdapter
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.navigation.koinNavGraphViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_list, container, false)

        Log.d("module", "$movieViewModel")
        val adapter = MovieListAdapter(movieViewModel)
        // Inflate the layout for this fragment
        binding.rvMovieList.adapter = adapter
        movieViewModel.movieLiveData.observe(viewLifecycleOwner, Observer {
            adapter.updateList(it)
        })
        movieViewModel.updateMovieList()
        return binding.root
    }

}