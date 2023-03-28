package com.asgribovskaya.newsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.asgribovskaya.newsapp.databinding.FragmentNewsFeedBinding
import com.asgribovskaya.newsapp.ui.MainActivity
import com.asgribovskaya.newsapp.ui.NewsViewModel

class NewsFeedFragment : Fragment() {

    private lateinit var binding: FragmentNewsFeedBinding
    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNewsFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }
}