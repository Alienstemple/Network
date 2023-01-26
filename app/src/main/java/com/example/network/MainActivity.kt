package com.example.network

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.network.databinding.ActivityMainBinding

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

    companion object MainCompanion : UiUpdater{
        const val TAG = "MainActLog"
        const val requestUrl = "https://jsonplaceholder.typicode.com/albums/1/photos"
        override fun updatePictureRecycler() {
            Log.d(TAG, "updatePicture called")
        }
    }

}