package com.example.movielist.retrofitservices

import android.util.Log
import com.example.movielist.models.BaseResponse
import com.example.movielist.models.GenreGroup
import com.example.movielist.models.PopularMovieResponse
import com.example.movielist.retrofitdefinitions.TMDBServiceDefinition
import com.example.movielist.viewmodel.MovieViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import retrofit2.Response

class TMDBService(private val apiKey: String,
                  private val maxHomePages: Int,
                  private val movieService: TMDBServiceDefinition,
                  private val movieViewModel: MovieViewModel): KoinComponent {

    /**
     * Fetches the most recent list of popular movies from TMDB and posts the value on movieLiveData
     */
    fun updateMovieList() {
        CoroutineScope(Dispatchers.IO).launch {
            // Call and body used for fetching popular movies
            var callPopularMovies: Response<BaseResponse>
            var bodyPopularMovies: BaseResponse?
            // List of popular movies and map of genres both fetched from TMDB
            val movies: MutableList<PopularMovieResponse> = mutableListOf()
            var genres: Map<Int, GenreGroup>? = null

            for (page in 1 until maxHomePages) {
                callPopularMovies = movieService.getPopularMovies(apiKey, page)
                bodyPopularMovies = callPopularMovies.body()
                bodyPopularMovies?.let { res ->
                    for (item in res.results) {
                        movies += item
                    }
                }
            }

            // Call and body used for fetching genres
            val callGenres = movieService.getGenres(apiKey)
            val bodyGenres = callGenres.body()

            bodyGenres?.genres?.let { res ->
                genres = res.associate { it.id to GenreGroup(it.name, mutableListOf()) }
            }

            // If both movies and genres were fetched correctly
            if (movies.isNotEmpty() && !genres.isNullOrEmpty()) {
                for (movie in movies) {
                    genres!![movie.genreIds[0]]?.movies?.add(movie)
                }
                Log.d("genres", "$genres")
                movieViewModel.movieGenresLiveData.postValue(genres!!)
            }
        }
    }

    /**
     * Gets poster and logo sizes so they can get successfully fetched from Glide
     */
    fun getImageInfo() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = movieService.getConfiguration(apiKey)
            val body = call.body()
            // If petition has been done correctly
            body?.images?.let {
                movieViewModel.posterBaseUrlLiveData.postValue(it.secureBaseUrl + it.posterSizes[it.posterSizes.size - 1])
                movieViewModel.logoBaseUrlLiveData.postValue(it.secureBaseUrl + it.logoSizes[it.logoSizes.size - 1])
            }
        }
    }
}