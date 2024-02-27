package com.harsh.movieapp.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.harsh.movieapp.R
import com.harsh.movieapp.apiservice.MovieApiService
import com.harsh.movieapp.apiservice.RetrofitInstance
import com.harsh.movieapp.model.Movie
import com.harsh.movieapp.model.Result
import com.harsh.movieapp.paging.MoviePagingSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.Callback

class MovieRepo(private val application: Application) {
    private val movieApiService: MovieApiService = RetrofitInstance.getService()

    fun getMutableLiveData(type: Int): LiveData<PagingData<Movie>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 100,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { MoviePagingSource(movieApiService, application, type) }
    ).liveData

    suspend fun getMutableLiveData(movieName: String): List<Movie>{
        val movieApiService: MovieApiService = RetrofitInstance.getService()
        val call = movieApiService.searchMovies(application.applicationContext.getString(R.string.api_key), movieName, 1)

        val movies = withContext(Dispatchers.IO) {
            try {
                val calls = movieApiService.searchMovies(
                    application.applicationContext.getString(R.string.api_key),
                    movieName,
                    1
                )
                val response = calls.execute()
                if (response.isSuccessful) {
                    response.body() ?: Result(1, emptyList(), 1, 1)

                } else {
                    Result(1, emptyList(), 1, 1)
                }
            }
            catch (e: Exception) {
                throw Exception("Error: ${e.message}")
            }
        }
        return movies.results
    }
}