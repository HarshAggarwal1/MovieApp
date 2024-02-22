package com.harsh.movieapp.model

import android.widget.ImageView
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

class MovieLayout: BaseObservable() {

    private var posterPath: String = ""
    private var title: String = ""
    private var releaseDate: String = ""
    private var adult: Boolean = false
    private var overview: String = ""
    private var voteAverage: Double = 0.0
    private var originalLanguage: String = ""
    private var popularity: Double = 0.0
    private var voteCount: Int = 0

    @Bindable
    fun getPosterPath(): String {
        return posterPath
    }
    fun setPosterPath(posterPath: String) {
        this.posterPath = posterPath
        notifyPropertyChanged(com.harsh.movieapp.BR.posterPath)
    }

    @Bindable
    fun getTitle(): String {
        return title
    }
    fun setTitle(title: String) {
        this.title = title
        notifyPropertyChanged(com.harsh.movieapp.BR.title)
    }

    @Bindable
    fun getReleaseDate(): String {
        return releaseDate
    }
    fun setReleaseDate(releaseDate: String) {
        this.releaseDate = releaseDate
        notifyPropertyChanged(com.harsh.movieapp.BR.releaseDate)
    }

    @Bindable
    fun getAdult(): Boolean {
        return adult
    }
    fun setAdult(adult: Boolean) {
        this.adult = adult
        notifyPropertyChanged(com.harsh.movieapp.BR.adult)
    }

    @Bindable
    fun getOverview(): String {
        return overview
    }
    fun setOverview(overview: String) {
        this.overview = overview
        notifyPropertyChanged(com.harsh.movieapp.BR.overview)
    }

    @Bindable
    fun getVoteAverage(): Double {
        return voteAverage
    }
    fun setVoteAverage(voteAverage: Double) {
        this.voteAverage = voteAverage
        notifyPropertyChanged(com.harsh.movieapp.BR.voteAverage)
    }

    @Bindable
    fun getOriginalLanguage(): String {
        return originalLanguage
    }
    fun setOriginalLanguage(originalLanguage: String) {
        this.originalLanguage = originalLanguage
        notifyPropertyChanged(com.harsh.movieapp.BR.originalLanguage)
    }

    @Bindable
    fun getPopularity(): Double {
        return popularity
    }
    fun setPopularity(popularity: Double) {
        this.popularity = popularity
        notifyPropertyChanged(com.harsh.movieapp.BR.popularity)
    }

    @Bindable
    fun getVoteCount(): Int {
        return voteCount
    }
    fun setVoteCount(voteCount: Int) {
        this.voteCount = voteCount
        notifyPropertyChanged(com.harsh.movieapp.BR.voteCount)
    }
}