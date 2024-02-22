package com.harsh.movieapp

import android.os.Bundle
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.harsh.movieapp.databinding.ActivityMovieBinding
import com.harsh.movieapp.model.MovieLayout

class MovieActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        val movieBinding: ActivityMovieBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_movie
        )
        applyWindowInsets(movieBinding)

        val bundle: Bundle? = intent.extras
        val posterPath: String? = bundle?.getString("posterPath")
        val title: String? = bundle?.getString("title")
        val releaseDate: String? = bundle?.getString("releaseDate")
        val adult: Boolean? = bundle?.getBoolean("adult")
        val overview: String? = bundle?.getString("overview")
        val voteAverage: Double? = bundle?.getDouble("voteAverage")
        val originalLanguage: String? = bundle?.getString("originalLanguage")
        val popularity: Double? = bundle?.getDouble("popularity")
        val voteCount: Int? = bundle?.getInt("voteCount")

        val movieLayout: MovieLayout = MovieLayout()
        movieLayout.setPosterPath(posterPath!!)
        movieLayout.setTitle(title!!)
        movieLayout.setReleaseDate(releaseDate!!)
        movieLayout.setAdult(adult!!)
        movieLayout.setOverview(overview!!)
        movieLayout.setVoteAverage(voteAverage!!)
        movieLayout.setOriginalLanguage(originalLanguage!!)
        movieLayout.setPopularity(popularity!!)
        movieLayout.setVoteCount(voteCount!!)

        movieBinding.setMovieLayout(movieLayout)

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${movieLayout.getPosterPath()}")
            .into(movieBinding.mvPoster)

        val menu: Menu = movieBinding.mvToolbar.menu
        menu.findItem(R.id.rating).title = "${((movieLayout.getVoteAverage() * 10.0).toInt()).toDouble() / 10.0} / 10"
    }
    private fun applyWindowInsets(mainBinding: ActivityMovieBinding) {
        ViewCompat.setOnApplyWindowInsetsListener(mainBinding.movieLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
    }
}