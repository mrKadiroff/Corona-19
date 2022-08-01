package com.shoxrux.covid19.models

data class CovidResults(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)