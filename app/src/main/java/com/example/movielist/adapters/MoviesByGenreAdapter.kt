package com.example.movielist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movielist.R
import com.example.movielist.models.PopularMovieResponse
import com.example.movielist.viewmodel.MovieViewModel

class MoviesByGenreAdapter(
    private val movieViewModel: MovieViewModel): RecyclerView.Adapter<MoviesByGenreAdapter.ViewHolder>() {
    private var movies = mutableListOf<PopularMovieResponse>()

    class ViewHolder(private val view: View, private val movieViewModel: MovieViewModel): RecyclerView.ViewHolder(view) {
        private val tvTitle: TextView = view.findViewById(R.id.tv_title_movie_by_id)
        private val ivMovie: ImageView = view.findViewById(R.id.iv_movie_by_genre)

        fun bind(movie: PopularMovieResponse) {
            tvTitle.text = movie.title
            movieViewModel.configurationLiveData.value?.let {
                Glide.with(view)
                    .load(view.context.getString(R.string.image_base_url) + it.logoSizes[it.logoSizes.size - 1] + movie.poster)
                    .fitCenter()
                    .into(ivMovie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holderView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_movie_card_grid, parent, false)
        return ViewHolder(holderView, movieViewModel)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    fun updateList(newList: MutableList<PopularMovieResponse>) {
        movies = newList
        notifyDataSetChanged()
    }
}