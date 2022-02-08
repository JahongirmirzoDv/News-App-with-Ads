package com.example.newsapp.retrofit

import com.example.newsapp.models.NewsData

interface ApiHelper {
    suspend fun getNews(page:Int,type:String):NewsData
}