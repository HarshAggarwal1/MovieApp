package com.harsh.movieapp.model

import android.widget.ImageView
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.harsh.movieapp.BR

class Movie : BaseObservable() {
    @SerializedName("adult")
    @Expose
    private var adult: Boolean = false
    @SerializedName("backdrop_path")
    @Expose
    private var backdropPath: String = ""
    @SerializedName("genre_ids")
    @Expose
    private var genreIds: List<Int> = listOf()
    @SerializedName("id")
    @Expose
    private var id: Int = 0
    @SerializedName("original_language")
    @Expose
    private var originalLanguage: String = ""
    @SerializedName("original_title")
    @Expose
    private var originalTitle: String = ""
    @SerializedName("overview")
    @Expose
    private var overview: String = ""
    @SerializedName("popularity")
    @Expose
    private var popularity: Double = 0.0
    @SerializedName("poster_path")
    @Expose
    private var posterPath: String = ""
    companion object {
        @JvmStatic
        @BindingAdapter("posterPath")
        fun loadImage(view: ImageView, url: String) {
            Glide.with(view.context).load("https://image.tmdb.org/t/p/w500$url").into(view)
        }
    }
    @SerializedName("release_date")
    @Expose
    private var releaseDate: String = ""
    @SerializedName("title")
    @Expose
    private var title: String = ""
    @SerializedName("video")
    @Expose
    private var video: Boolean = false
    @SerializedName("vote_average")
    @Expose
    private var voteAverage: Double = 0.0
    @SerializedName("vote_count")
    @Expose
    private var voteCount: Int = 0

    fun setAdult(adult: Boolean) {
        this.adult = adult
        notifyPropertyChanged(BR.adult)
    }
    fun setBackdropPath(backdropPath: String) {
        this.backdropPath = backdropPath
        notifyPropertyChanged(BR.backdropPath)
    }
    fun setGenreIds(genreIds: List<Int>) {
        this.genreIds = genreIds
        notifyPropertyChanged(BR.genreIds)
    }
    fun setId(id: Int) {
        this.id = id
        notifyPropertyChanged(BR.id)
    }
    fun setOriginalLanguage(originalLanguage: String) {
        this.originalLanguage = originalLanguage
        notifyPropertyChanged(BR.originalLanguage)
    }
    fun setOriginalTitle(originalTitle: String) {
        this.originalTitle = originalTitle
        notifyPropertyChanged(BR.originalTitle)
    }
    fun setOverview(overview: String) {
        this.overview = overview
        notifyPropertyChanged(BR.overview)
    }
    fun setPopularity(popularity: Double) {
        this.popularity = popularity
        notifyPropertyChanged(BR.popularity)
    }
    fun setPosterPath(posterPath: String) {
        this.posterPath = posterPath
        notifyPropertyChanged(BR.posterPath)
    }
    fun setReleaseDate(releaseDate: String) {
        this.releaseDate = releaseDate
        notifyPropertyChanged(BR.releaseDate)
    }
    fun setTitle(title: String) {
        this.title = title
        notifyPropertyChanged(BR.title)
    }
    fun setVideo(video: Boolean) {
        this.video = video
        notifyPropertyChanged(BR.video)
    }
    fun setVoteAverage(voteAverage: Double) {
        this.voteAverage = voteAverage
        notifyPropertyChanged(BR.voteAverage)
    }
    fun setVoteCount(voteCount: Int) {
        this.voteCount = voteCount
        notifyPropertyChanged(BR.voteCount)
    }

    @Bindable
    fun getAdult(): Boolean {
        return adult
    }
    @Bindable
    fun getBackdropPath(): String {
        return backdropPath
    }
    @Bindable
    fun getGenreIds(): List<Int> {
        return genreIds
    }
    @Bindable
    fun getId(): Int {
        return id
    }
    @Bindable
    fun getOriginalLanguage(): String {
        return originalLanguage
    }
    @Bindable
    fun getOriginalTitle(): String {
        return originalTitle
    }
    @Bindable
    fun getOverview(): String {
        return overview
    }
    @Bindable
    fun getPopularity(): Double {
        return popularity
    }
    @Bindable
    fun getPosterPath(): String {
        return posterPath
    }
    @Bindable
    fun getReleaseDate(): String {
        return releaseDate
    }
    @Bindable
    fun getTitle(): String {
        return title
    }
    @Bindable
    fun getVideo(): Boolean {
        return video
    }
    @Bindable
    fun getVoteAverage(): Double {
        return voteAverage
    }
    @Bindable
    fun getVoteCount(): Int {
        return voteCount
    }

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }
}
