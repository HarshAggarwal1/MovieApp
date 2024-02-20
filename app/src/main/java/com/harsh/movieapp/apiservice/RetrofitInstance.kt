package com.harsh.movieapp.apiservice

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance private constructor() {
    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"
        private var retrofit: Retrofit? = null
        fun getService(): MovieApiService {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!.create(MovieApiService::class.java)
        }
    }
}