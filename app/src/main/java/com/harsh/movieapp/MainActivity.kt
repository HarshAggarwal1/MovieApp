package com.harsh.movieapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.harsh.movieapp.databinding.ActivityMainBinding
import com.harsh.movieapp.model.Movie
import com.harsh.movieapp.view.MovieAdapter
import com.harsh.movieapp.viewmodel.MovieViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        val mainBinding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        applyWindowInsets(mainBinding)

        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]

        getMovies(mainBinding)

        swipeRefreshLayout = mainBinding.main
        swipeRefreshLayout.setColorSchemeResources(R.color.magenta)
        swipeRefreshLayout.setOnRefreshListener {
            getMovies(mainBinding)
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun getMovies(mainBinding: ActivityMainBinding) {
        viewModel.getMovies().observe(this
        ) {
            movieAdapter = MovieAdapter()
            movieAdapter.submitData(lifecycle, it)
            recyclerView = mainBinding.recyclerView
            recyclerView.adapter = movieAdapter

            // get screen pixels
            val displayMetrics = resources.displayMetrics
            val dpWidth = displayMetrics.widthPixels / displayMetrics.density

            recyclerView.layoutManager = GridLayoutManager(this, (dpWidth / 150.0f).toInt())

            movieAdapter.notifyDataSetChanged()
        }
    }

    private fun applyWindowInsets(mainBinding: ActivityMainBinding) {
        ViewCompat.setOnApplyWindowInsetsListener(mainBinding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}