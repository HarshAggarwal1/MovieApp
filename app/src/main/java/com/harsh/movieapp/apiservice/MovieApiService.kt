package com.harsh.movieapp.apiservice

import com.harsh.movieapp.model.Result
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey: String, @Query("page") page: Int): Call<Result>

    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("api_key") apiKey: String, @Query("page") page: Int): Call<Result>

    @GET("movie/upcoming")
    fun getUpcomingMovies(@Query("api_key") apiKey: String, @Query("page") page: Int): Call<Result>

    @GET("movie/now_playing")
    fun getNowPlayingMovies(@Query("api_key") apiKey: String, @Query("page") page: Int): Call<Result>

    @GET("search/movie")
    fun searchMovies(@Query("api_key") apiKey: String, @Query("query") movieName: String, @Query("page") page: Int): Call<Result>
}