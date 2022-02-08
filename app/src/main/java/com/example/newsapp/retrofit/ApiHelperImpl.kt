package com.example.newsapp.retrofit

import com.example.newsapp.models.NewsData

class ApiHelperImpl(private var apiService: ApiService) : ApiHelper {
    override suspend fun getNews(page: Int, type: String): NewsData =
        apiService.getNews(page = page, q = type)
}