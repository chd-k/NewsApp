package com.asgribovskaya.newsapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.asgribovskaya.newsapp.repository.NewsRepository

class NewsViewModel(
    val newsRepository: NewsRepository
    ) : ViewModel() {


/*
    ViewModel factory. Defined in ViewModel file for better context, readability, and easier
    discovery according to Google`s recommendations
*/
    class Factory(private val newsRepository: NewsRepository) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NewsViewModel(newsRepository) as T
        }
    }

}