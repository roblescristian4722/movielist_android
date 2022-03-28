package com.example.movielist.adapters

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movielist.R
import com.example.movielist.models.PopularMovieResponse
import com.example.movielist.retrofitservices.TMDBService
import com.example.movielist.viewmodel.MovieViewModel
import com.google.firebase.analytics.FirebaseAnalytics

class MoviesByGenreAdapter(
    private val movieViewModel: MovieViewModel,
    private val movieService: TMDBService,
    private val context: Context): RecyclerView.Adapter<MoviesByGenreAdapter.ViewHolder>() {
    private var movies = mutableListOf<PopularMovieResponse>()
    private var currentPage = 1

    class ViewHolder(
        private val view: View,
        private val movieViewModel: MovieViewModel,
        private val movieService: TMDBService,
        private val context: Context): RecyclerView.ViewHolder(view) {
        private val tvTitle: TextView = view.findViewById(R.id.tv_title_movie_by_id)
        private val ivMovie: ImageView = view.findViewById(R.id.iv_movie_by_genre)
        private val llMovieByGenre: LinearLayout = view.findViewById(R.id.ll_movie_by_genre)

        fun bind(movie: PopularMovieResponse) {
            tvTitle.text = movie.title
            movieViewModel.configurationLiveData.value?.let {
                Glide.with(view)
                    .load(view.context.getString(R.string.image_base_url) + it.logoSizes[it.logoSizes.size - 1] + movie.poster)
                    .centerCrop()
                    .into(ivMovie)
            }

            llMovieByGenre.setOnClickListener {
                // Gets Google Analytics instance
                val firebase = FirebaseAnalytics.getInstance(view.context)
                val bundle = Bundle()

                // Gets movie trailer
                movieService.getMovieVideos(
                    movie.id,
                    context.getString(R.string.default_video_site),
                    context.getString(R.string.default_video_type))

                // Logs the click event
                bundle.putInt(FirebaseAnalytics.Param.ITEM_ID, movie.id)
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, movie.title)
                firebase.logEvent(FirebaseAnalytics.Event.SELECT_ITEM, bundle)

                movieViewModel.selectedMovieLiveData.postValue(movie)
                it.findNavController().navigate(R.id.action_moviesByGenre_to_movieInfo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holderView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_movie_card_grid, parent, false)
        return ViewHolder(holderView, movieViewModel,movieService, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies[position])
        if (position == movies.size - 1) {
            currentPage++
            movieViewModel.selectedGenreLiveData.value?.let {
                movieService.getGenreMovies(currentPage, mutableListOf(it))
            }
        }
    }

    override fun getItemCount(): Int = movies.size

    fun updateList(newList: MutableList<PopularMovieResponse>) {
        movies = newList
        notifyDataSetChanged()
    }
}