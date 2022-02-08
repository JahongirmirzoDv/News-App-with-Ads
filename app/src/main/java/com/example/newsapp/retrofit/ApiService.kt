package com.example.newsapp.retrofit

import com.example.newsapp.models.NewsData
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("everything")
    suspend fun getNews(
        @Query("apiKey") apiKey: String = "7676840141384f57bf770f9eaea7e821",
        @Query("sources") sources: String = "bbc-news",
        @Query("q") q: String,
        @Query("page") page: Int
    ): NewsData
}