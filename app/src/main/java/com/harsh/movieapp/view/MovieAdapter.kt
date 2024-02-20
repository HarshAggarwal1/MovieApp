package com.harsh.movieapp.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.harsh.movieapp.R
import com.harsh.movieapp.databinding.MovieItemBinding
import com.harsh.movieapp.model.Movie

class MovieAdapter(private val context: Context, private val movieList: List<Movie>): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {


    class MovieViewHolder(val movieItemBinding: MovieItemBinding) : RecyclerView.ViewHolder(movieItemBinding.root) {
        init {
            movieItemBinding.root.setOnClickListener {
                val movie = movieItemBinding.movie
                Toast.makeText(it.context, "You clicked " + movie!!.getTitle(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val movieItemBinding: MovieItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.movie_item,
            parent,
            false)
        return MovieViewHolder(movieItemBinding)
    }

    override fun getItemCount(): Int {
        return this.movieList.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        holder.movieItemBinding.movie = movie
    }
}