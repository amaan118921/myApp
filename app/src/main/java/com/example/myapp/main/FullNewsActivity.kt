package com.example.myapp.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.net.toUri
import com.example.myapp.R
import com.example.myapp.databinding.ActivityFullNewsBinding
import com.example.myapp.viewModel.AppViewModel
import com.example.myapp.viewModel.HomeViewModel
import com.squareup.picasso.Picasso

class FullNewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFullNewsBinding
    private val model: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.title.text = model.getTitle()
        binding.content.text = model.getContent()
        binding.source.text = model.getSource()
        binding.timePublished.text = model.getTime()

        Picasso.get().load(model.getUri().toUri()).into(binding.image)




    }
}