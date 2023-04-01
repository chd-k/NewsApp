package com.asgribovskaya.newsapp.repository

import com.asgribovskaya.newsapp.api.RetrofitInstance
import com.asgribovskaya.newsapp.db.ArticleDatabase
import com.asgribovskaya.newsapp.models.Article

class NewsRepository (val database: ArticleDatabase) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode = countryCode, pageNumber = pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchNews(searchQuery = searchQuery, pageNumber = pageNumber)

    suspend fun insertArticle(article: Article) = database.getArticleDao().insertArticle(article)

    fun getAllArticles() = database.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) = database.getArticleDao().deleteArticle(article)
}