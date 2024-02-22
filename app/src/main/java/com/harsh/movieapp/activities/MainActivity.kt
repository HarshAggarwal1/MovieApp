package com.harsh.movieapp.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.harsh.movieapp.R
import com.harsh.movieapp.databinding.ActivityMainBinding
import com.harsh.movieapp.view.MovieAdapter
import com.harsh.movieapp.viewmodel.MovieViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var viewModel: MovieViewModel
    private lateinit var bottomAppBar: BottomAppBar
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        val mainBinding: ActivityMainBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )
        applyWindowInsets(mainBinding)

        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]

        getMovies(mainBinding, 3)

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

        // to add padding to recyclerView so that bottomAppBar doesn't overlap the last item
        bottomAppBar = mainBinding.bottomAppBar
        bottomAppBar.viewTreeObserver.addOnGlobalLayoutListener {
            bottomAppBar.viewTreeObserver.removeOnGlobalLayoutListener {
                recyclerView.setPadding(
                    recyclerView.paddingLeft,
                    recyclerView.paddingTop,
                    recyclerView.paddingRight,
                    recyclerView.paddingBottom + bottomAppBar.height
                )
            }
        }

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
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
    }
}