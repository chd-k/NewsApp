package com.asgribovskaya.newsapp.api

import com.asgribovskaya.newsapp.models.NewsResponse
import com.asgribovskaya.newsapp.util.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {
    @GET("/v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("apiKey")
        apiKey: String = API_KEY,

        @Query("country")
        countryCode: String = "ru",

        @Query("page")
        pageNumber: Int = 1,
    ): Response<NewsResponse>

    @GET("/v2/everything")
    suspend fun searchNews(
        @Query("apiKey")
        apiKey: String = API_KEY,

        @Query("page")
        pageNumber: Int = 1,

        @Query("q")
        phrase: String,
    ): Response<NewsResponse>
}