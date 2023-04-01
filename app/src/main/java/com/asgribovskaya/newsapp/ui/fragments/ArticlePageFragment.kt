package com.asgribovskaya.newsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.asgribovskaya.newsapp.databinding.FragmentArticlePageBinding
import com.asgribovskaya.newsapp.ui.MainActivity
import com.asgribovskaya.newsapp.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class ArticlePageFragment : Fragment() {

    private lateinit var binding: FragmentArticlePageBinding
    private lateinit var viewModel: NewsViewModel
    private val args: ArticlePageFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentArticlePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        val article = args.article
        binding.wvArticlePage.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }

        binding.fabArticleSave.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(view, "Saved successfully!", Snackbar.LENGTH_SHORT).show()
        }
    }
}