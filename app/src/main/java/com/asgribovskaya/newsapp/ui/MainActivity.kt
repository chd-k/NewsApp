package com.asgribovskaya.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.asgribovskaya.newsapp.R
import com.asgribovskaya.newsapp.databinding.ActivityMainBinding
import com.asgribovskaya.newsapp.db.ArticleDatabase
import com.asgribovskaya.newsapp.repository.NewsRepository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var newsRepository: NewsRepository
    val viewModel: NewsViewModel by viewModels { NewsViewModel.Factory(newsRepository) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        newsRepository = NewsRepository(ArticleDatabase(this))

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fcv_main_fragments) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bnvMainBottom.setupWithNavController(navController)

    }
}