package com.example.newsapp.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient2 {
    var BASE_URL = "https://api.nytimes.com/svc/books/v3/lists/"
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }
    val apiService = getRetrofit().create(ApiService2::class.java)
}