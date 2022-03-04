package com.example.movielist.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movielist.R
import com.example.movielist.models.dataclasses.GenreGroup
import com.example.movielist.models.dataclasses.PopularMovieResponse
import com.example.movielist.viewmodel.MovieViewModel

class MovieListAdapter(
    private val viewModel: MovieViewModel,
    private val genre: Int): RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {
    private var movies = mutableListOf<PopularMovieResponse>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_movie_card, parent, false)
        return ViewHolder(view, viewModel)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (movies.size > 0)
            holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    fun updateList(newMovieMap: Map<Int, GenreGroup>) {
        newMovieMap[genre]?.movies?.let {
            movies = it
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(private val view: View, private val viewModel: MovieViewModel): RecyclerView.ViewHolder(view) {
        private val ivMovieImage = view.findViewById<ImageView>(R.id.iv_movie_image)
        private val tvTitle = view.findViewById<TextView>(R.id.tv_title)
        private val tvVoteAverage = view.findViewById<TextView>(R.id.tv_rating)
        private val tvDate = view.findViewById<TextView>(R.id.tv_date)

        fun bind(movie: PopularMovieResponse) {
            tvTitle.text = movie.title
            tvVoteAverage.text = movie.voteAverage.toString()
            tvDate.text = movie.date
            Glide.with(view)
                .load(viewModel.logoBaseUrlLiveData.value + movie.poster)
                .fitCenter()
                .into(ivMovieImage)
            view.setOnClickListener {
                viewModel.selectMovie(movie)
                view.findNavController().navigate(R.id.action_homeList_to_movieInfo)
            }
        }
    }
}