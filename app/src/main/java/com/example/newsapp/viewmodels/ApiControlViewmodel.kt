package com.example.newsapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.models.NewsData
import com.example.newsapp.retrofit.ApiHelper
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class ApiControlViewmodel(private var apiHelper: ApiHelper) : ViewModel() {
    var newsList = MutableLiveData<NewsData>()

    fun getNews(page: Int, type: String): MutableLiveData<NewsData> {
        viewModelScope.launch {
            coroutineScope {
                supervisorScope {
                    try {
                        val news = apiHelper.getNews(page, type)
                        newsList.value = news
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
        return newsList
    }
}