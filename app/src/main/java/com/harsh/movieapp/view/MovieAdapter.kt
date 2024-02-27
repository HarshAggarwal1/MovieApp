package com.harsh.movieapp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.harsh.movieapp.MainActivity
import com.harsh.movieapp.MovieActivity
import com.harsh.movieapp.R
import com.harsh.movieapp.databinding.MovieItemBinding
import com.harsh.movieapp.model.Movie

class MovieAdapter(): PagingDataAdapter<Movie, MovieAdapter.MovieViewHolder>(COMPARATOR) {


    class MovieViewHolder(val movieItemBinding: MovieItemBinding, context: Context) : RecyclerView.ViewHolder(movieItemBinding.root) {
        init {
            // Interstitial Ad
            val adRequest = AdRequest.Builder().build()
            var mInterstitialAd: InterstitialAd? = null
            InterstitialAd.load(context,"ca-app-pub-5316532507620289/8265128479", adRequest, object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
//                    adError.toString().let { Log.d("Interstitial Ad Error", it) }
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                }
            })

            movieItemBinding.root.setOnClickListener {
                val movie = movieItemBinding.movie
                val bundle: Bundle = Bundle()
                bundle.putString("posterPath", movie?.getPosterPath())
                bundle.putString("title", movie?.getTitle())
                bundle.putString("releaseDate", movie?.getReleaseDate())
                bundle.putBoolean("adult", movie?.getAdult()!!)
                bundle.putString("overview", movie.getOverview())
                bundle.putString("voteAverage", movie.getVoteAverage())
                bundle.putString("originalLanguage", movie.getOriginalLanguage())
                bundle.putDouble("popularity", movie.getPopularity())
                bundle.putInt("voteCount", movie.getVoteCount())
                val intent: Intent = Intent(movieItemBinding.root.context, MovieActivity::class.java)
                intent.putExtras(bundle)
                movieItemBinding.root.context.startActivity(intent)

                // Show the ad
                if (mInterstitialAd != null) {
                    mInterstitialAd?.show(context as MainActivity)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val movieItemBinding: MovieItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.movie_item,
            parent,
            false)
        return MovieViewHolder(movieItemBinding, parent.context)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.movieItemBinding.movie = movie
    }
}

object COMPARATOR : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.getId() == newItem.getId()
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

}

