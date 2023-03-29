package com.asgribovskaya.newsapp.repository

import com.asgribovskaya.newsapp.api.RetrofitInstance
import com.asgribovskaya.newsapp.db.ArticleDatabase

class NewsRepository (val database: ArticleDatabase) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode = countryCode, pageNumber = pageNumber)
}