package com.example.newsapp.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.retrofit.ApiHelper
import com.example.newsapp.viewmodels.ApiControlViewmodel

class ViewModelFactory(private val apiHelper: ApiHelper) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ApiControlViewmodel::class.java)) {
            return ApiControlViewmodel(apiHelper) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}