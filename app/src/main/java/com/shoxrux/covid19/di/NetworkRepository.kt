package com.shoxrux.covid19.di

import com.shoxrux.covid19.api.ApiService
import com.shoxrux.covid19.models.CovidResults
import retrofit2.Response
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    val topHeadlinesApi: ApiService
) {

    suspend fun getTopHeadlines(): Response<CovidResults> {
        return topHeadlinesApi.getCovidResults("covid","publishedAt","06a47076dd5e42f087a92e24ffcb32fb")
    }

}