package com.harsh.movieapp.repository

import android.app.Application
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
}