package com.example.newsapp.retrofit

import com.example.newsapp.models.NewsData
import com.example.newsapp.retrofit.model2.NAmes

class ApiHelperImpl(private var apiService: ApiService, private var apiService2: ApiService2) :
    ApiHelper {
    override suspend fun getNews(page: Int, type: String): NewsData =
        apiService.getNews(page = page, q = type)

    override suspend fun getTop(): NewsData = apiService.getTop()
    override suspend fun getNames(): NAmes = apiService2.getNames()
}