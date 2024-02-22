package com.harsh.movieapp.paging

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.harsh.movieapp.R
import com.harsh.movieapp.apiservice.MovieApiService
import com.harsh.movieapp.model.Movie
import com.harsh.movieapp.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviePagingSource(private val movieApiService: MovieApiService, private val application: Application, private val type: Int): PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        try {
            val position: Int = params.key ?: 1
            val result = withContext(Dispatchers.IO) {
                try {
                    lateinit var call: Call<Result>
                    when (type) {
                        1 -> call = movieApiService.getPopularMovies(
                            application.applicationContext.getString(R.string.api_key),
                            position
                        )
                        2 -> call = movieApiService.getTopRatedMovies(
                            application.applicationContext.getString(R.string.api_key),
                            position
                        )
                        3 -> call = movieApiService.getUpcomingMovies(
                            application.applicationContext.getString(R.string.api_key),
                            position
                        )
                        4 -> call = movieApiService.getNowPlayingMovies(
                            application.applicationContext.getString(R.string.api_key),
                            position
                        )
                    }
                    val response = call.execute()
                    if (response.isSuccessful) {
                        response.body() ?: Result(1, emptyList(), 1, 1)
                    }
                    else {
                        Result(1, emptyList(), 1, 1)
                    }
                }
                catch (e: Exception) {
                    throw Exception("Error: ${e.message}")
                }
            }
            return LoadResult.Page(
                data = result.results,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position == result.totalPages) null else position + 1
            )
        }
        catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

}