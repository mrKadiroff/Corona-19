package com.shoxrux.covid19.api

import com.shoxrux.covid19.models.CovidResults
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v2/everything")
    suspend fun getCovidResults(
        @Query("q") q: String,
        @Query("from") from: String,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") apiKey: String,
    ): Response<CovidResults>


}