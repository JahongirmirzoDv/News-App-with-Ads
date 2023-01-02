package com.example.newsapp.retrofit.model2

data class NAmes(
    val copyright: String,
    val num_results: Int,
    val results: List<Result>,
    val status: String
)