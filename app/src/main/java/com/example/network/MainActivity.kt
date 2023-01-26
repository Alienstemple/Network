package com.example.network

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.network.databinding.ActivityMainBinding
import com.example.network.model.Picture
import com.example.network.service.NetworkService

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var pictureAdapter: PictureAdapter

    private var pictureList: List<Picture> =
        listOf(
            Picture(0, 0, "Title1", "url1", "url1"),
            Picture(0, 0, "Title2 kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk", "url2", "url2"),
            Picture(0, 0, "Title3", "url3", "url3"))

    private val backgroundHandler: Handler
    private val uiHandler =
        Handler(Looper.getMainLooper())  // Основной поток уже запущен. Возьмем от него looper

    private val getPicturesFromNetwork = Runnable {
        pictureList = NetworkService().getPictures()
        uiHandler.post(updateUI)
    }

    private val updateUI = Runnable {
        updatePictureRecycler()
    }

    init {
        val backgroundHandlerThread = HandlerThread("background")
        backgroundHandlerThread.start()    // Запустим фоновый поток
        backgroundHandler =
            Handler(backgroundHandlerThread.looper)   // В конструктор передадим looper запущенного фонового потока
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mainBinding.getBtn.setOnClickListener {
            backgroundHandler.post(getPicturesFromNetwork)
        }

        mainBinding.postBtn.setOnClickListener {
            intent = Intent(this, PostActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        backgroundHandler.removeCallbacksAndMessages(null)
        uiHandler.removeCallbacksAndMessages(null)
    }

    fun upd() {
        mainBinding
    }

    private fun updatePictureRecycler() {
        pictureAdapter = PictureAdapter(pictureList)

        mainBinding.pictureRecycle.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mainBinding.pictureRecycle.adapter = pictureAdapter
    }

    companion object {
        const val TAG = "MainActLog"
        const val requestUrl = "https://jsonplaceholder.typicode.com/albums/1/photos"
    }
}
