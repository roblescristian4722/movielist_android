package com.example.movielist.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.movielist.R
import com.example.movielist.adapters.CategoryListAdapter
import com.example.movielist.databinding.FragmentGenreListBinding
import com.example.movielist.retrofitservices.TMDBService
import com.example.movielist.viewmodel.MovieViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class GenreList : Fragment() {
    private lateinit var binding: FragmentGenreListBinding
    private val movieViewModel: MovieViewModel by sharedViewModel()
    private val movieService: TMDBService by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val adapter = CategoryListAdapter(movieService, movieViewModel)

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_genre_list, container, false)

        binding.rvCategoryList.adapter = adapter
        movieViewModel.genreListLiveData.observe(viewLifecycleOwner, Observer {
            adapter.updateList(it)
        })

        return binding.root
    }
}