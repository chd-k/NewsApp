package com.asgribovskaya.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.asgribovskaya.newsapp.adapters.NewsAdapter
import com.asgribovskaya.newsapp.databinding.FragmentNewsFeedBinding
import com.asgribovskaya.newsapp.ui.MainActivity
import com.asgribovskaya.newsapp.ui.NewsViewModel
import com.asgribovskaya.newsapp.util.ApiError
import com.asgribovskaya.newsapp.util.ApiLoading
import com.asgribovskaya.newsapp.util.ApiSuccess

class NewsFeedFragment : Fragment() {

    private val TAG = "NewsFeedFragment"
    private lateinit var binding: FragmentNewsFeedBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNewsFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        setUpRecyclerView()

        viewModel.breakingNews.observe(viewLifecycleOwner) { apiResponse ->
            when (apiResponse) {
                is ApiSuccess -> {
                    hideProgressBar()
                    newsAdapter.listDiffer.submitList(apiResponse.data.articles)
                }
                is ApiError -> {
                    hideProgressBar()
                    apiResponse.data?.let { newsResponse ->
                        newsAdapter.listDiffer.submitList(newsResponse.articles)
                    }
                    Log.e(TAG, "Error: ${apiResponse.message}")
                }
                is ApiLoading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.rvFeedArticles.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun hideProgressBar() {
        binding.pbFeedProgress.visibility = INVISIBLE
    }

    private fun showProgressBar() {
        binding.pbFeedProgress.visibility = VISIBLE
    }
}