package com.example.movielist.models

import com.example.movielist.models.dataclasses.BaseResponse
import com.example.movielist.models.dataclasses.ConfigurationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBService {
    @GET("/3/movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String, @Query("page") page: Int = 1): Response<BaseResponse>

    @GET("/3/configuration")
    suspend fun getConfiguration(@Query("api_key") apiKey: String): Response<BaseResponse>
}