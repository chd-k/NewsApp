package com.asgribovskaya.newsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.asgribovskaya.newsapp.databinding.ItemArticlePreviewBinding
import com.asgribovskaya.newsapp.models.Article
import com.bumptech.glide.Glide

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(val binding: ItemArticlePreviewBinding)
        : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean
            = oldItem.url == newItem.url

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean
            = oldItem == newItem
    }

    val listDiffer = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemArticlePreviewBinding.inflate(inflater, parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentArticle = listDiffer.currentList[position]
        holder.binding.apply {
            Glide.with(this.root).load(currentArticle.urlToImage).into(ivArticleCover)
            tvArticleDateTime.text = currentArticle.publishedAt
            tvArticleSource.text = currentArticle.source?.name
            tvArticleTitle.text = currentArticle.title
            tvArticleDescription.text = currentArticle.description
            root.setOnClickListener {
                onItemClickListener?.let { it(currentArticle) }
            }
        }
    }

    override fun getItemCount(): Int = listDiffer.currentList.size

    private var onItemClickListener: ((Article) -> Unit)? = null
    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }

}