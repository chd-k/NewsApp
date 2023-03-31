package com.asgribovskaya.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.asgribovskaya.newsapp.adapters.NewsAdapter
import com.asgribovskaya.newsapp.databinding.FragmentSearchBinding
import com.asgribovskaya.newsapp.ui.MainActivity
import com.asgribovskaya.newsapp.ui.NewsViewModel
import com.asgribovskaya.newsapp.util.ApiError
import com.asgribovskaya.newsapp.util.ApiLoading
import com.asgribovskaya.newsapp.util.ApiSuccess
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
        binding.rvSearchResponses.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
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
    }

    private fun showProgressBar() {
        binding.pbSearchProgress.visibility = VISIBLE
    }
}