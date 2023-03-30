package com.asgribovskaya.newsapp.repository

import com.asgribovskaya.newsapp.api.RetrofitInstance
import com.asgribovskaya.newsapp.db.ArticleDatabase

class NewsRepository (val database: ArticleDatabase) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode = countryCode, pageNumber = pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchNews(searchQuery = searchQuery, pageNumber = pageNumber)
}