package com.harsh.movieapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView
import com.harsh.movieapp.databinding.ActivityMainBinding
import com.harsh.movieapp.model.Movie
import com.harsh.movieapp.repository.MovieRepo
import com.harsh.movieapp.view.MovieAdapter
import com.harsh.movieapp.view.SearchViewAdapter
import com.harsh.movieapp.viewmodel.MovieViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var viewModel: MovieViewModel
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var recyclerView2: RecyclerView
    private lateinit var movieAdapter2: SearchViewAdapter
    private lateinit var searchView: SearchView
    private lateinit var searchBar: SearchBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        val mainBinding: ActivityMainBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )
        applyWindowInsets(mainBinding)

        MobileAds.initialize(this) {
        }
        val adRequest = AdRequest.Builder().build()

        // Banner Ad
        val mAdViewBanner = mainBinding.adViewBanner
        mAdViewBanner.loadAd(adRequest)

        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]

        getMovies(mainBinding, 1)

        bottomNavigationView = mainBinding.bottomNavigationView

        swipeRefreshLayout = mainBinding.main
        swipeRefreshLayout.setColorSchemeResources(R.color.magenta)
        swipeRefreshLayout.setOnRefreshListener {
            val type: Int = when (bottomNavigationView.selectedItemId) {
                R.id.popular -> 1
                R.id.top_rated -> 2
                R.id.upcoming -> 3
                R.id.now_playing -> 4
                else -> 3
            }
            getMovies(mainBinding, type)
            swipeRefreshLayout.isRefreshing = false
        }

        // to make swipeRefreshLayout work with recyclerView inside coordinatorLayout
        recyclerView = mainBinding.recyclerView
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val topRowVerticalPosition: Int =
                    if (recyclerView.childCount == 0) {
                        0
                    } else {
                        recyclerView.getChildAt(0).top
                    }
                swipeRefreshLayout.isEnabled = topRowVerticalPosition >= 0
            }
        })

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.popular -> {
                    getMovies(mainBinding, 1)
                    true
                }
                R.id.top_rated -> {
                    getMovies(mainBinding, 2)
                    true
                }
                R.id.upcoming -> {
                    getMovies(mainBinding, 3)
                    true
                }
                R.id.now_playing -> {
                    getMovies(mainBinding, 4)
                    true
                }
                else -> false
            }
        }

        recyclerView2 = mainBinding.recyclerViewSearchView
        recyclerView2.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                swipeRefreshLayout.isEnabled = false
            }
        })
        movieAdapter2 = SearchViewAdapter(listOf())
        recyclerView2.adapter = movieAdapter2
        recyclerView2.setHasFixedSize(true)

        // get screen pixels
        val displayMetrics = resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density

        recyclerView2.layoutManager = GridLayoutManager(this, (dpWidth / 150.0f).toInt())

        searchView = mainBinding.searchView
        searchBar = mainBinding.searchBar

        searchView.editText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                val movieName: String = searchView.editText.text.toString()
                // clear all coroutine jobs
                MainScope().cancel()
                MainScope().launch {
                    val movies: List<Movie> = viewModel.getMovieSearch(movieName)
                    movieAdapter2.setData(movies)
                }
            }
        })
    }

    private fun getMovies(mainBinding: ActivityMainBinding, type: Int ) {
        viewModel.getMovies(type).observe(this
        ) {
            movieAdapter = MovieAdapter()
            movieAdapter.submitData(lifecycle, it)
            recyclerView.adapter = movieAdapter
            recyclerView.setHasFixedSize(true)

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
            v.setPadding(systemBars.left, 0, systemBars.right, 0)
            insets
        }
    }
}