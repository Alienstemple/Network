package com.example.network

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.network.databinding.ActivityMainBinding
import com.example.network.model.Picture
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var pictureAdapter: PictureAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)


        mainBinding.getBtn.setOnClickListener {
            val pictureList = NetworkService().getPictures()
            pictureAdapter = PictureAdapter(pictureList)

            mainBinding.pictureRecycle.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            mainBinding.pictureRecycle.adapter = pictureAdapter
        }
    }

    companion object {
        const val TAG = "MainActLog"
        const val requestUrl = "https://jsonplaceholder.typicode.com/albums/1/photos"
    }
}