package com.developer.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.RequestManager
import com.developer.presentation.MainActivity
import com.developer.presentation.databinding.ActivitySplashScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreen : AppCompatActivity() {
    private var _binding: ActivitySplashScreenBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val IMAGE_URL =
            "https://bobsburgers-api.herokuapp.com/images/characters/448.jpg"
        private const val TIME_DELAY = 1000L
    }

    @Inject
    lateinit var glide: RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        glide.load(IMAGE_URL).into(binding.imageView)

        lifecycleScope.launch {
            delay(TIME_DELAY)
            startActivity(Intent(this@SplashScreen, MainActivity::class.java))
            // close splash activity
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}