package com.example.newsapp.retrofit

import com.example.newsapp.models.NewsData
import com.example.newsapp.retrofit.model2.NAmes
import kotlinx.coroutines.flow.Flow

interface ApiHelper {
    suspend fun getNews(page:Int,type:String):NewsData

    suspend fun getTop():NewsData

    suspend  fun getNames(): NAmes
}