package com.harsh.movieapp.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.harsh.movieapp.R
import com.harsh.movieapp.apiservice.MovieApiService
import com.harsh.movieapp.apiservice.RetrofitInstance
import com.harsh.movieapp.model.Movie
import com.harsh.movieapp.model.Result
import retrofit2.Callback

class MovieRepo(private val application: Application) {
    private var movies: List<Movie> = listOf()
    private val mutableLiveData: MutableLiveData<List<Movie>> = MutableLiveData()

    fun getMutableLiveData(): MutableLiveData<List<Movie>> {
        val movieApiService: MovieApiService = RetrofitInstance.getService()
        val call = movieApiService.getPopularMovies(application.applicationContext.getString(R.string.api_key))
        call.enqueue(object : Callback<Result> {
            override fun onResponse(call: retrofit2.Call<Result>, response: retrofit2.Response<Result>) {
                val result = response.body()
                if (result?.results != null) {
                    movies = result.results
                    mutableLiveData.value = movies
                }
            }

            override fun onFailure(call: retrofit2.Call<Result>, t: Throwable) {
                t.printStackTrace()
            }
        })
        return mutableLiveData
    }
}