package com.example.newsapp.retrofit

import com.example.newsapp.retrofit.model2.NAmes
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService2 {
    @GET("names")
   suspend fun getNames(
        @Query("api-key") api_key:String = "GwG7WBy6awsjZWYELhFxJfAAVnvTQQZd"
    ):NAmes
}