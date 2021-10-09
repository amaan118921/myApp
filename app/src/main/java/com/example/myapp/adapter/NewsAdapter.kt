package com.example.myapp.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.example.myapp.databinding.NewsItemBinding
import com.example.myapp.main.FullNewsActivity
import com.example.myapp.main.HomeActivity
import com.example.myapp.main.NewsFragment
import com.example.myapp.modelData.News
import com.example.myapp.modelData.Response
import com.example.myapp.viewModel.AppViewModel
import com.example.myapp.viewModel.HomeViewModel
import com.squareup.picasso.Picasso
import java.util.*

class NewsAdapter(val model: HomeViewModel, val fragment: Fragment): ListAdapter<News, NewsAdapter.NewsViewHolder>(DiffCallback) {

    class NewsViewHolder( binding: NewsItemBinding): RecyclerView.ViewHolder(binding.root) {
        val imgView = binding.imgView
        val title = binding.Title
        val timePublished = binding.timePublished
        val newsSource = binding.newsSource
        val desc = binding.desc
    }

    companion object DiffCallback: DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.title==newItem.title
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.content==newItem.content
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val adapter = NewsItemBinding.inflate(LayoutInflater.from(parent.context))
        return NewsViewHolder(adapter)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = getItem(position)
        NewsFragment.binding.pb.visibility = View.INVISIBLE
        holder.desc.setText(item.description)
        holder.title.setText(item.title)
        holder.newsSource.setText(item.source.name)
        holder.timePublished.setText(item.publishedAt)

        holder.itemView.setOnClickListener {
            model.setTitle(item.title)
            model.setTime(item.publishedAt)
            model.setContent(item.content)
            model.setUri(item.urlToImage)
            model.setSource(item.source.name)
            fragment.findNavController().navigate(R.id.action_newsFragment_to_fullNewsFragment)
        }

        Picasso.get().load(item.urlToImage).placeholder(R.drawable.loading_animation).into(holder.imgView)

    }
}