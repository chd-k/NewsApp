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
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asgribovskaya.newsapp.adapters.NewsAdapter
import com.asgribovskaya.newsapp.databinding.FragmentSearchBinding
import com.asgribovskaya.newsapp.ui.MainActivity
import com.asgribovskaya.newsapp.ui.NewsViewModel
import com.asgribovskaya.newsapp.util.ApiError
import com.asgribovskaya.newsapp.util.ApiLoading
import com.asgribovskaya.newsapp.util.ApiSuccess
import com.asgribovskaya.newsapp.util.Constants
import com.asgribovskaya.newsapp.util.Constants.SEARCH_NEWS_DELAY
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private val TAG = "SearchFragment"
    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        setUpRecyclerView()

        newsAdapter.setOnItemClickListener {
            findNavController().navigate(
                SearchFragmentDirections
                    .actionSearchFragmentToArticlePageFragment(it)
            )
        }

        setUpSearch()

        viewModel.searchedNews.observe(viewLifecycleOwner) { apiResponse ->
            when(apiResponse) {
                is ApiSuccess -> {
                    hideProgressBar()
                    newsAdapter.listDiffer.submitList(apiResponse.data.articles.toList())
                    (apiResponse.data.totalResults / Constants.QUERY_PAGE_SIZE + 2).let {
                        isLastPage = viewModel.searchedNewsPage == it
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
        binding.rvSearchResponses.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(searchFeedScrollListener)
        }
    }

    private fun setUpSearch() {
        var job: Job? = null
        binding.etSearchRequest.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_NEWS_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty())
                        viewModel.searchNews(editable.toString())
                }
            }
        }
    }

    private fun hideProgressBar() {
        binding.pbSearchProgress.visibility = INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        binding.pbSearchProgress.visibility = VISIBLE
        isLoading = true
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val searchFeedScrollListener = object : RecyclerView.OnScrollListener() {
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
            val isTotalMoreThanPageSize = totalItemCount >= Constants.QUERY_PAGE_SIZE
            val shouldPaginate =
                isNotLoadingAndNotLastPage
                        && isAtLastItem
                        && isNotAtBeginning
                        && isTotalMoreThanPageSize
                        && isScrolling
            if (shouldPaginate) {
                viewModel.searchNews(binding.etSearchRequest.toString())
                isScrolling = false
            }
        }
    }
}