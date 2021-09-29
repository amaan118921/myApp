package com.example.myapp.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.myapp.R
import com.example.myapp.databinding.FragmentFullNewsBinding
import com.example.myapp.viewModel.AppViewModel
import com.example.myapp.viewModel.HomeViewModel
import com.squareup.picasso.Picasso

class FullNewsFragment : Fragment() {

        private lateinit var binding: FragmentFullNewsBinding
        private val model: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFullNewsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.title.text = model.getTitle()
        binding.content.text = model.getContent()
        binding.source.text = model.getSource()
        binding.timePublished.text = model.getTime()
        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }
        Picasso.get().load(model.getUri().toUri()).into(binding.image)

    }

}