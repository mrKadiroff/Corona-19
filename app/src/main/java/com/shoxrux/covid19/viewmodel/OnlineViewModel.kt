package com.shoxrux.covid19.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoxrux.covid19.di.NetworkRepository
import com.shoxrux.covid19.models.CovidResults
import com.shoxrux.covid19.utils.Constants.API_KEY
import com.shoxrux.covid19.utils.Constants.COUNTRY_CODE
import com.shoxrux.covid19.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
 class OnlineViewModel @Inject constructor(private val networkRepository: NetworkRepository) : ViewModel() {

    private val _topHeadlines = MutableLiveData<DataHandler<CovidResults>>()
    val topHeadlines: LiveData<DataHandler<CovidResults>> = _topHeadlines

    fun getTopHeadlines() {
        _topHeadlines.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.getTopHeadlines()
            _topHeadlines.postValue(handleResponse(response))
        }
    }

    private fun handleResponse(response: Response<CovidResults>): DataHandler<CovidResults> {
        if (response.isSuccessful) {
            response.body()?.let { it ->
                return DataHandler.SUCCESS(it)
            }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }
}