package com.example.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.navigation.navArgs
import com.example.myapp.databinding.ActivitySplashBinding
import com.example.myapp.main.HomeActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val navArgs: SplashActivityArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = navArgs.uid

        Handler(Looper.getMainLooper()).postDelayed( {
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("uid", id)
            startActivity(intent)
            finish()
        }, 2000)





    }
}