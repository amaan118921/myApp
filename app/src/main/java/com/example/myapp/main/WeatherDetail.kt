package com.example.myapp.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.navArgs
import com.example.myapp.R
import com.example.myapp.databinding.ActivityWeatherDetailBinding
import com.squareup.picasso.Picasso

class WeatherDetail : AppCompatActivity() {
    private lateinit var binding: ActivityWeatherDetailBinding
    private val navArgs: WeatherDetailArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = navArgs.title
        val date = navArgs.date
        val url = navArgs.url

            binding.title.text = title
            binding.date.text = date

            Picasso.get().load(url).placeholder(R.drawable.loading_animation).into(binding.imgView)




        binding.cancel.setOnClickListener {
            this.finish()
        }




    }
}