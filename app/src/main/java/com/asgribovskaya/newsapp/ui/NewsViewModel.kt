package com.asgribovskaya.newsapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.asgribovskaya.newsapp.models.NewsResponse
import com.asgribovskaya.newsapp.repository.NewsRepository
import com.asgribovskaya.newsapp.util.ApiError
import com.asgribovskaya.newsapp.util.ApiLoading
import com.asgribovskaya.newsapp.util.ApiResponse
import com.asgribovskaya.newsapp.util.ApiSuccess
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    val newsRepository: NewsRepository,
) : ViewModel() {

    val breakingNews: MutableLiveData<ApiResponse<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1
    val searchedNews: MutableLiveData<ApiResponse<NewsResponse>> = MutableLiveData()
    var searchedNewsPage = 1

    init {
        getBreakingNews("us")
    }

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        breakingNews.postValue(ApiLoading())
        val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    fun searchNews(searchQuery: String) = viewModelScope.launch {
        searchedNews.postValue(ApiLoading())
        val response = newsRepository.searchNews(searchQuery, searchedNewsPage)
        searchedNews.postValue(handleSearchNewsResponse(response))
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): ApiResponse<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return ApiSuccess(resultResponse)
            }
        }
        return ApiError(message = response.message())
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>): ApiResponse<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return ApiSuccess(resultResponse)
            }
        }
        return ApiError(message = response.message())
    }

    /*
        ViewModel factory. Defined in ViewModel file for better context, readability, and easier
        discovery according to Google recommendations
    */
    class Factory(private val newsRepository: NewsRepository) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NewsViewModel(newsRepository) as T
        }
    }

}