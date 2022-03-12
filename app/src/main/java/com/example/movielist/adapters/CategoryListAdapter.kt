package com.example.movielist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.movielist.R
import com.example.movielist.models.GenreInfoResponse
import com.example.movielist.retrofitservices.TMDBService
import com.example.movielist.viewmodel.MovieViewModel

class CategoryListAdapter(
    private val movieService: TMDBService,
    private val movieViewModel: MovieViewModel
    ): RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {
    private var genreList = listOf<GenreInfoResponse>()

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        private val tvGenre: TextView = view.findViewById(R.id.tv_genre)
        private val cvGenre: CardView = view.findViewById(R.id.cv_genre)

        fun bind(genre: GenreInfoResponse) {
            tvGenre.text = genre.name

            // TODO: Create an OnClickListener for the cardview that navigates to genre grid
            cvGenre.setOnClickListener {
                movieService.getGenreMovies(withGenres = listOf(genre.id))
                view.findNavController().navigate(R.id.action_mainPager_to_moviesByGenre)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryListAdapter.ViewHolder {
        val holder = LayoutInflater.from(parent.context).inflate(R.layout.fragment_genre_card, parent, false)
        return ViewHolder(holder)
    }

    override fun getItemCount(): Int = genreList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (genreList.isNotEmpty())
            holder.bind(genreList[position])
    }

    fun updateList(list: List<GenreInfoResponse>) {
        genreList = list
        notifyDataSetChanged()
    }
}