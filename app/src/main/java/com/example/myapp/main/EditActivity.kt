package com.example.myapp.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapp.R
import com.example.myapp.databinding.ActivityEditAcitivtyBinding

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditAcitivtyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAcitivtyBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.cancel.setOnClickListener {
            onNavigateUp()
        }




    }
}