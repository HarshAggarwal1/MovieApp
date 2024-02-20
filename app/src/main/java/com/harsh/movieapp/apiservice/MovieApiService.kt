package com.harsh.movieapp.apiservice

import com.harsh.movieapp.model.Result
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey: String): Call<Result>
}