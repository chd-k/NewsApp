package com.asgribovskaya.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asgribovskaya.newsapp.adapters.NewsAdapter
import com.asgribovskaya.newsapp.databinding.FragmentNewsFeedBinding
import com.asgribovskaya.newsapp.ui.MainActivity
import com.asgribovskaya.newsapp.ui.NewsViewModel
import com.asgribovskaya.newsapp.util.ApiError
import com.asgribovskaya.newsapp.util.ApiLoading
import com.asgribovskaya.newsapp.util.ApiSuccess
import com.asgribovskaya.newsapp.util.Constants.COUNTRY_CODE
import com.asgribovskaya.newsapp.util.Constants.QUERY_PAGE_SIZE

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

        newsAdapter.setOnItemClickListener {
            findNavController().navigate(
                NewsFeedFragmentDirections
                    .actionNewsFeedFragmentToArticlePageFragment(it)
            )
        }

        viewModel.breakingNews.observe(viewLifecycleOwner) { apiResponse ->
            when (apiResponse) {
                is ApiSuccess -> {
                    hideProgressBar()
                    newsAdapter.listDiffer.submitList(apiResponse.data.articles.toList())
                    (apiResponse.data.totalResults / QUERY_PAGE_SIZE + 2).let {
                        isLastPage = viewModel.breakingNewsPage == it
                    }
                }
                is ApiError -> {
                    hideProgressBar()
                    apiResponse.data?.let { newsResponse ->
                        newsAdapter.listDiffer.submitList(newsResponse.articles.toList())
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
            addOnScrollListener(newsFeedScrollListener)
        }
    }

    private fun hideProgressBar() {
        binding.pbFeedProgress.visibility = INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        binding.pbFeedProgress.visibility = VISIBLE
        isLoading = true
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val newsFeedScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                isScrolling = true
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanPageSize = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate =
                isNotLoadingAndNotLastPage
                    && isAtLastItem
                    && isNotAtBeginning
                    && isTotalMoreThanPageSize
                    && isScrolling
            if (shouldPaginate) {
                viewModel.getBreakingNews(COUNTRY_CODE)
                isScrolling = false
            }
        }
    }
}