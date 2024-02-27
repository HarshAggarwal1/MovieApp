package com.harsh.movieapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.harsh.movieapp.model.Movie
import com.harsh.movieapp.repository.MovieRepo

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val movieRepo: MovieRepo = MovieRepo(application)
    fun getMovies(type: Int): LiveData<PagingData<Movie>> {
        return movieRepo.getMutableLiveData(type).cachedIn(viewModelScope)
    }
    suspend fun getMovieSearch(movieName: String): List<Movie> {
        return movieRepo.getMutableLiveData(movieName)
    }
}