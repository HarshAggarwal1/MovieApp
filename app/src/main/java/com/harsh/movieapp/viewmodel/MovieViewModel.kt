package com.harsh.movieapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.harsh.movieapp.model.Movie
import com.harsh.movieapp.repository.MovieRepo

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val movieRepo: MovieRepo = MovieRepo(application)

    fun getMovies(): LiveData<List<Movie>> = movieRepo.getMutableLiveData()
}