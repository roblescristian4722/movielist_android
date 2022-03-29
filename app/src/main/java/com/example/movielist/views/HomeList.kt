package com.example.movielist.views

import android.os.Bundle
import android.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movielist.R
import com.example.movielist.databinding.FragmentHomeListBinding
import com.example.movielist.viewmodel.MovieViewModel
import com.example.movielist.adapters.MovieListAdapter
import com.example.movielist.retrofitservices.TMDBService
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeList : Fragment() {
    private val movieViewModel: MovieViewModel by sharedViewModel()
    private val movieService: TMDBService by inject()
    private lateinit var binding: FragmentHomeListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_list, container, false)

        // Everytime the livedata gets updated the adapter updates too
        movieViewModel.popularMoviesByGenreLiveData.observe(viewLifecycleOwner, Observer {
            val tvContextThemeWrapper = ContextThemeWrapper(requireContext(), R.style.genre_title)
            var tvGenre: TextView
            var rvGenre: RecyclerView
            val newLinearLayout = LinearLayout(requireContext())

            // Clears all views from Linear Layout
            newLinearLayout.orientation = LinearLayout.VERTICAL
            binding.llMovieList.removeAllViews()
            
            // Iterates over every genre available and creates a RecyclerView for each one
            for (genre in it) {
                if (genre.value.movies.size > 0) {
                    // Title gets crated and added programmatically to layout
                    val tvGenreParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT)
                    tvGenreParams.setMargins(32, 32, 16, 8)
                    tvGenre = TextView(tvContextThemeWrapper)
                    tvGenre.text = genre.value.name
                    tvGenre.layoutParams = tvGenreParams
                    newLinearLayout.addView(tvGenre)

                    // RecyclerView gets created and added programmatically to layout
                    val adapter = MovieListAdapter(movieViewModel, movieService, requireContext(), genre.key)
                    val layoutManager = LinearLayoutManager(requireContext())
                    layoutManager.orientation = RecyclerView.HORIZONTAL

                    rvGenre = RecyclerView(requireContext())
                    //rvGenre.descendantFocusability = ViewGroup.FOCUS_AFTER_DESCENDANTS
                    rvGenre.layoutManager = layoutManager
                    rvGenre.adapter = adapter
                    rvGenre.isNestedScrollingEnabled = false
                    adapter.updateList(it)
                    newLinearLayout.addView(rvGenre)
                }
            }
            binding.llMovieList.addView(newLinearLayout)
        })

        return binding.root
    }
}