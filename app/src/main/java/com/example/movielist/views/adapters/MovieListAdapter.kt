package com.example.movielist.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movielist.R
import com.example.movielist.models.dataclasses.PopularMovieResponse

class MovieListAdapter: RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {
    private var movies = mutableListOf<PopularMovieResponse>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_movie_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    fun updateList(newMovieList: List<PopularMovieResponse>) {
        movies = newMovieList.toMutableList()
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        private val baseImageUrl = "https://image.tmdb.org/t/p/original/"
        private val ivMovieImage = view.findViewById<ImageView>(R.id.iv_movie_image)
        private val tvTitle = view.findViewById<TextView>(R.id.tv_title)
        private val tvVoteAverage = view.findViewById<TextView>(R.id.tv_rating)
        private val tvDate = view.findViewById<TextView>(R.id.tv_date)

        fun bind(movie: PopularMovieResponse) {
            tvTitle.text = movie.title
            tvVoteAverage.text = movie.voteAverage.toString()
            tvDate.text = movie.date
            Glide.with(view).load(baseImageUrl + movie.poster).into(ivMovieImage)
        }
    }
}