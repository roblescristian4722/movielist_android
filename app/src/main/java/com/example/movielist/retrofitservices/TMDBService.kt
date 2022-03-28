package com.example.movielist.retrofitservices

import android.util.Log
import com.example.movielist.models.BaseResponse
import com.example.movielist.models.GenreGroup
import com.example.movielist.models.MovieVideoResponse
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
     * Gets poster and logo sizes so that images can get successfully fetched from Glide
     */
    fun getConfiguration() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = movieService.getConfiguration(apiKey)
            val body = call.body()
            // If petition has been done correctly
            body?.images?.let {
                movieViewModel.configurationLiveData.postValue(it)
            }
        }
    }

    /**
     * Fetches List of movie genres
     * */
    fun getGenres() {
        CoroutineScope(Dispatchers.IO).launch {
            val callGenres = movieService.getGenres(apiKey)
            val bodyGenres = callGenres.body()

            bodyGenres?.genres?.let { res ->
                movieViewModel.genreListLiveData.postValue(res)
            }
        }
    }

    /**
     * Fetches the most recent list of popular movies from TMDB and posts the value on movieLiveData
     */
    fun updatePopularMovieList() {
        CoroutineScope(Dispatchers.IO).launch {
            // Call and body used for fetching popular movies
            var callPopularMovies: Response<BaseResponse>
            var bodyPopularMovies: BaseResponse?
            // List of popular movies and map of genres both fetched from TMDB
            val movies: MutableList<PopularMovieResponse> = mutableListOf()
            var genres: Map<Int, GenreGroup>? = null

            // Gets maxHomePages amount of pages from server
            for (page in 1 until maxHomePages) {
                callPopularMovies = movieService.getPopularMovies(apiKey, page)
                bodyPopularMovies = callPopularMovies.body()
                bodyPopularMovies?.let { res ->
                    for (item in res.results)
                        movies += item
                }
            }

            // Creates a map that stores a (key -> value) of (genre id -> list of genres)
            movieViewModel.genreListLiveData.value?.let { res ->
                genres = res.associate { it.id to GenreGroup(it.name, mutableListOf()) }
            }

            // If both movie and genre lists were fetched correctly we create a map (genre id -> movie)
            if (movies.isNotEmpty() && !genres.isNullOrEmpty()) {
                for (movie in movies)
                    genres!![movie.genreIds[0]]?.movies?.add(movie)
                // Once we iterate over all popular movies and create the genres map we post it using a viewmodel
                movieViewModel.popularMoviesByGenreLiveData.postValue(genres!!)
            }
        }
    }

    /**
     * Gets movies filtered by genres from TMDB, if the page we fetched is greater than 1 then we post
     * the received values into selectedMoviesLiveData, otherwise we append the fetched page to the end
     * of the list stored in selectedMoviesLiveData
     */
    fun getGenreMovies(page: Int = 1, withGenres: List<Int>) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = movieService.getMovies(apiKey, page, withGenres)
            val body = call.body()
            body?.results?.let {
                var genreMovies: MutableList<PopularMovieResponse> = it.toMutableList()
                // If we fetch more than one page from the server then we append the results from of
                // the call to the end of the existing list (page 1)
                if (page > 1) {
                    movieViewModel.selectedGenreMoviesLiveData.value?.let { oldList ->
                        genreMovies = (oldList + genreMovies).toMutableList()
                    }
                }
                movieViewModel.selectedGenreMoviesLiveData.postValue(genreMovies)
            }
        }
    }

    /**
     * Gets the movie trailer of a movie and posts it to selectedMovieVideo
     */
    fun getMovieVideos(movieId: Int, defaultSite: String, defaultType: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = movieService.getMovieVideos(movieId, apiKey)
            val body = call.body()
            var trailers: List<MovieVideoResponse>?
            body?.results.let { results ->
                Log.d("movieVideo", "results: $results")
                trailers = results?.filter {
                    it.official && it.site == defaultSite && it.type == defaultType }
                trailers?.let {
                    if (it.isNotEmpty())
                        movieViewModel.selectedMovieVideo.postValue(it[0].key)
                }
            }
        }
    }
}