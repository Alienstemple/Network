package com.example.network

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.network.databinding.ActivityMainBinding
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding

    private lateinit var httpClient: OkHttpClient

    private lateinit var request: Request

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mainBinding.getBtn.setOnClickListener {
        }
    }

    companion object {
        const val TAG = "MainActLog"
        const val requestUrl = "https://jsonplaceholder.typicode.com/albums/1/photos"
    }
}